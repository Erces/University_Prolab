package com.company.view;

import com.company.model.CalmaList;
import com.company.model.Kullanici;
import com.company.model.Sarki;
import com.company.service.DbHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class KullaniciProfilSayfa extends JFrame {
    final Kullanici kullanici;
    DbHelper dbHelper;
    JLabel jLabel;
    JScrollPane listScrollerSarkilar;
    JLabel textSarkilar;
    DefaultListModel<String> listModelTakipEdilen;
    DefaultListModel<String> listModelCalmaList;
    ArrayList<Sarki> calmaSarkiList;
    ArrayList<Kullanici> takipEdilenList;
    ArrayList<Kullanici> takipciList;

    public KullaniciProfilSayfa(Kullanici kullanici) {
        dbHelper = new DbHelper();
        jLabel = new JLabel();
        this.kullanici = kullanici;
        takipEdilenList = dbHelper.getTakipEdilen(this.kullanici.getKullaniciID());
        takipciList = dbHelper.getTakipci(this.kullanici.getKullaniciID());
        showKullaniciBilgi();
        showTakipEdilen();
        showTakipci();
        showKullaniclar();
        showCikisButton();
        if (this.kullanici.getAbonelik().equals("Premium"))
            showCalmaListBtn();
        add(jLabel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
    }

    private void showKullaniciBilgi() {
        ImageIcon userIcon = new ImageIcon("src/com/company/images/user.png");
        ImageIcon backIcon = new ImageIcon("src/com/company/images/back_arrow.png");
        JLabel labelIcon = new JLabel(userIcon);
        JButton btnBack = new JButton();
        String odeme = "Odenmedi";
        calmaSarkiList = new ArrayList<>();
        labelIcon.setBounds(32, 64, 64, 64);
        add(labelIcon);
        add(setText(kullanici.getKullaniciAd(), 32, 200));
        add(setText(kullanici.getEmail(), 32, 300));
        add(setText(kullanici.getAbonelik(), 32, 400));
        if (kullanici.getOdeme() == 1) odeme = "Odendi";
        if (kullanici.getAbonelik().equals("Premium")) {
            JButton btnOdeme = new JButton(odeme);
            btnOdeme.setBounds(16, 500, 100, 20);
            add(btnOdeme);
            btnOdeme.addActionListener(e -> {
                if (kullanici.getOdeme() != 1) {
                    dbHelper.updateOdeme(kullanici.getKullaniciID());
                    btnOdeme.setText("Odendi");
                }
            });
        }
        btnBack.setIcon(backIcon);
        btnBack.setBackground(Color.white);
        btnBack.setBounds(32, 600, 32, 32);
        add(btnBack);
        btnBack.addActionListener(e -> {
            super.dispose();
            new KullaniciAnaSayfa(kullanici);
        });

    }

    private void showTakipEdilen() {
        listModelTakipEdilen = new DefaultListModel<>();
        JScrollPane listScroller = new JScrollPane();
        for (Kullanici takipEdilen : takipEdilenList)
            listModelTakipEdilen.addElement(takipEdilen.kullaniciToString());
        JList<String> jTakipEdilenList = new JList<>(listModelTakipEdilen);
        listScroller.setViewportView(jTakipEdilenList);
        jTakipEdilenList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(200, 80, 300, 150);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Sil");
        popupMenu.add(delete);
        add(setText("Takip Edilenler", 310, 64));
        add(listScroller);
        jTakipEdilenList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        delete.addActionListener(actionEvent -> {
            dbHelper.deleteTakipEdilen(takipEdilenList.get(jTakipEdilenList.getSelectedIndex()).getKullaniciID());
            listModelTakipEdilen.remove(jTakipEdilenList.getSelectedIndex());
        });
    }

    private void showTakipci() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JScrollPane listScroller = new JScrollPane();
        for (Kullanici takipci : takipciList)
            listModel.addElement(takipci.kullaniciToString());
        JList<String> jTakipciList = new JList<>(listModel);
        listScroller.setViewportView(jTakipciList);
        jTakipciList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(550, 80, 300, 150);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Sil");
        popupMenu.add(delete);
        add(setText("Takipciler", 680, 64));
        add(listScroller);
        jTakipciList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    System.out.println("index: " + jTakipciList.getSelectedIndex());
                    System.out.println(takipciList.get(jTakipciList.getSelectedIndex()).getKullaniciAd());
                }
            }
        });
        delete.addActionListener(actionEvent -> {
            System.out.println("takipci size: " + takipciList.size());
            dbHelper.deleteTakipci(takipciList.get(jTakipciList.getSelectedIndex()).getKullaniciID());
            listModel.remove(jTakipciList.getSelectedIndex());
        });
    }

    private void showKullaniclar() {
        ArrayList<Kullanici> kullaniciList = dbHelper.getAllKullanici();
        kullaniciList.removeIf(item -> kullanici.getKullaniciID() == item.getKullaniciID());
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JScrollPane listScroller = new JScrollPane();
        for (Kullanici item : kullaniciList) {
            listModel.addElement(item.kullaniciToString());
        }
        JList<String> jKullaniciList = new JList<>(listModel);
        listScroller.setViewportView(jKullaniciList);
        jKullaniciList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(900, 80, 300, 150);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem takipEt = new JMenuItem("Takip Et");
        popupMenu.add(takipEt);
        add(setText("Kullanicilar", 1010, 64));
        add(listScroller);
        jKullaniciList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        takipEt.addActionListener(actionEvent -> {
            Kullanici takip = kullaniciList.get(jKullaniciList.getSelectedIndex());
            boolean control = true;
            for (Kullanici takipEdilen : takipEdilenList) {
                if (takipEdilen.getKullaniciID() == takip.getKullaniciID()) {
                    control = false;
                    break;
                }
            }
            if (control) {
                dbHelper.takipEt(kullanici.getKullaniciID(), takip.getKullaniciID());
                takipEdilenList.add(takip);
                listModelTakipEdilen.addElement(takip.kullaniciToString());
            } else {
                showMessage("Bu kullaniciyi zaten takip ediyorsunuz.");
            }
        });
    }


    private void showCalmaListBtn() {
        JButton btn = new JButton("Calma Listesi Olustur");
        btn.setBounds(570, 650, 200, 20);
        add(btn);
        btn.addActionListener(e -> {
            showCalmaList();
            showSarkilar();
        });
    }

    private void showCalmaList() {
        JTextField textField = new JTextField();
        JLabel text = setText("Calma Listesi", 420, 300);
        listModelCalmaList = new DefaultListModel<>();
        JScrollPane listScroller = new JScrollPane();
        JButton btnKaydet = new JButton("Kaydet");
        JButton btnKapat = new JButton("Kapat");
        btnKapat.setBounds(620, 520, 100, 20);
        textField.setBounds(380, 320, 150, 20);
        btnKaydet.setBounds(400, 520, 100, 20);
        JList<String> jSarkiList = new JList<>(listModelCalmaList);
        listScroller.setViewportView(jSarkiList);
        jSarkiList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(300, 350, 300, 150);
        add(listScroller);
        add(text);
        add(textField);
        add(btnKaydet);
        add(btnKapat);
        add(jLabel);

        btnKaydet.addActionListener(e -> {
            if (!textField.getText().isEmpty()) {
                ArrayList<Integer> idList = new ArrayList<>();
                for (Sarki item : calmaSarkiList)
                    idList.add(item.getSarkiId());
                CalmaList calmaList = new CalmaList(textField.getText(), idList);
                dbHelper.addCalmaList(calmaList);
                kullanici.calmaListOlustur(dbHelper.getLastCalmaListId());
                dbHelper.updateKullanici(kullanici);
                kapat(btnKapat, listScroller, textField, text, btnKaydet);
            } else {
                showMessage("Isimsiz calma listesi olusturulamaz.");
            }
        });
        btnKapat.addActionListener(e -> kapat(btnKapat, listScroller, textField, text, btnKaydet));
    }

    private void kapat(JButton btnKapat, JScrollPane listScroller, JTextField textField, JLabel text, JButton btnKaydet) {
        remove(btnKapat);
        remove(listScroller);
        remove(listScrollerSarkilar);
        remove(textField);
        remove(textSarkilar);
        remove(text);
        remove(btnKaydet);
        repaint();
        validate();
    }

    private void showSarkilar() {
        ArrayList<Sarki> sarkiList = dbHelper.getAllSarki();
        listScrollerSarkilar = new JScrollPane();
        textSarkilar = setText("Sarkilar", 870, 300);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Sarki item : sarkiList)
            listModel.addElement(item.toString());
        JList<String> jSarkiList = new JList<>(listModel);
        listScrollerSarkilar.setViewportView(jSarkiList);
        jSarkiList.setLayoutOrientation(JList.VERTICAL);
        listScrollerSarkilar.setBounds(750, 350, 300, 150);
        add(listScrollerSarkilar);
        add(textSarkilar);
        add(jLabel);
        repaint();
        validate();
        jSarkiList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Sarki sarki = sarkiList.get(jSarkiList.getSelectedIndex());
                    if (!listModelCalmaList.contains(sarki.toString())) {
                        calmaSarkiList.add(sarki);
                        listModelCalmaList.addElement(sarki.toString());
                    }
                }
            }
        });
    }

    private void showCikisButton() {
        JButton btnCikis = new JButton("Cikis");
        btnCikis.setBounds(1080, 650, 100, 20);
        add(btnCikis);
        btnCikis.addActionListener(e -> {
            super.dispose();
            new KullaniciGirisSayfa();
        });
    }


    private JLabel setText(String text, int x, int y) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setBounds(x, y, 100, 20);
        return label;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(KullaniciProfilSayfa.this,
                message, "Hata", JOptionPane.WARNING_MESSAGE);
    }
}
