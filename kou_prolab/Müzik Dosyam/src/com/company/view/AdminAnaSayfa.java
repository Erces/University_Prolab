package com.company.view;

import com.company.model.Album;
import com.company.model.Kullanici;
import com.company.model.Sanatci;
import com.company.model.Sarki;
import com.company.service.DbHelper;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class AdminAnaSayfa extends JFrame {
    DbHelper dbHelper;
    ArrayList<Sarki> sarkiList;
    ArrayList<Sanatci> sanatciList;
    ArrayList<Album> albumList;
    String[] turler;
    JPanel pnlButton = new JPanel();
    JButton btn = new JButton("Sarki Ekle");
    JButton btn2 = new JButton("Sanatci Ekle");
    JButton btn3 = new JButton("Album Ekle");
    JList<String> jSarkiList;
    JList<String> jSanatciList;
    JList<String> jAlbumList;
    DefaultListModel<String> listModelSarki;
    DefaultListModel<String> listModelSanatci;
    DefaultListModel<String> listModelAlbum;
    DefaultListModel<String> listModelSarkiEkleme1;
    DefaultListModel<String> listModelAlbumSanatciEkleme;
    DefaultListModel<String> listModelAlbumSarkiEkleme;
    JTextField textSarkiUlke;
    JTextField textSarkiSure;
    JTextField textSarkiIsim;
    JTextField textSarkiTarih;
    JTextField textFieldSarkiDinlenmeS;
    JTextField textSanatciIsim;
    JTextField textSanatciUlke;
    JTextField textAlbumIsim;
    JTextField textAlbumTarih;
    boolean sarkiUpdateControl;
    boolean sanatciUpdateControl;
    boolean albumUpdateControl;
    String[] albumArray;
    public AdminAnaSayfa() {
        dbHelper = new DbHelper();
        turler = new String[]{"jazz", "klasik", "pop"};
        sarkiList = dbHelper.getAllSarki();
        sanatciList = dbHelper.getAllSanatci();
        albumList = dbHelper.getAllAlbum();
        listModelSarki = new DefaultListModel<>();
        listModelSanatci = new DefaultListModel<>();
        listModelAlbum = new DefaultListModel<>();
        sarkiUpdateControl = false;
        sanatciUpdateControl = false;
        albumUpdateControl = false;
        for (Sarki sarki : sarkiList)
            listModelSarki.addElement(sarki.toString());
        for (Sanatci sanatci : sanatciList)
            listModelSanatci.addElement(sanatci.toString());
        for (Album album : albumList)
            listModelAlbum.addElement(album.toString());
        sarkiEklemeMenu(btn);
        sanatciEklemeMenu(btn2);
        albumEklemeMenu(btn3);
        showList();
        showKullaniciList();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
    }

    private void showList() {
        jSarkiList = new JList<>(listModelSarki);
        jSanatciList = new JList<>(listModelSanatci);
        jAlbumList = new JList<>(listModelAlbum);
        JScrollPane listScroller = new JScrollPane();
        JScrollPane listScroller2 = new JScrollPane();
        JScrollPane listScroller3 = new JScrollPane();
        JPopupMenu popupMenuSarki = new JPopupMenu();
        JPopupMenu popupMenuSanatci = new JPopupMenu();
        JPopupMenu popupMenuAlbum = new JPopupMenu();
        JMenuItem deleteSarki = new JMenuItem("Sil");
        JMenuItem updateSarki = new JMenuItem("Guncelle");
        JMenuItem deleteSanatci = new JMenuItem("Sil");
        JMenuItem updateSanatci = new JMenuItem("Guncelle");
        JMenuItem deleteAlbum = new JMenuItem("Sil");
        JMenuItem updateAlbum = new JMenuItem("Guncelle");

        popupMenuSarki.add(deleteSarki);
        popupMenuSarki.add(updateSarki);
        popupMenuSanatci.add(deleteSanatci);
        popupMenuSanatci.add(updateSanatci);
        popupMenuAlbum.add(deleteAlbum);
        popupMenuAlbum.add(updateAlbum);
        listScroller.setViewportView(jSarkiList);
        listScroller2.setViewportView(jSanatciList);
        listScroller3.setViewportView(jAlbumList);
        jSanatciList.setLayoutOrientation(JList.VERTICAL);
        jSarkiList.setLayoutOrientation(JList.VERTICAL);
        jAlbumList.setLayoutOrientation(JList.VERTICAL);

        listScroller.setBounds(40, 450, 200, 300);
        listScroller2.setBounds(340, 450, 200, 300);
        listScroller3.setBounds(640, 450, 200, 300);
        add(listScroller);
        add(listScroller2);
        add(listScroller3);
        jSarkiList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuSarki.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        jSanatciList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuSanatci.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        jAlbumList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenuAlbum.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        deleteSarki.addActionListener(e -> {
            int index = jSarkiList.getSelectedIndex();
            dbHelper.deleteSarki(sarkiList.get(index).getSarkiId());
            listModelSarki.remove(index);
        });
        deleteSanatci.addActionListener(e -> {
            int index = jSanatciList.getSelectedIndex();
            dbHelper.deleteSanatci(sanatciList.get(index).getSanatciId());
            listModelSanatci.remove(index);
        });
        deleteAlbum.addActionListener(e -> {
            int index = jAlbumList.getSelectedIndex();
            dbHelper.deleteAlbum(albumList.get(index).getAlbumId());
            listModelAlbum.remove(index);
        });
        updateSarki.addActionListener(e -> {
            Sarki sarki = sarkiList.get(jSarkiList.getSelectedIndex());
            textSarkiIsim.setText(sarki.getSarkiAd());
            textSarkiSure.setText(sarki.getSure());
            textSarkiTarih.setText(sarki.getTarih());
            textSarkiUlke.setText(sarki.getUlke());
            textFieldSarkiDinlenmeS.setText(String.valueOf(sarki.getDinlenmeSayisi()));
            sarkiUpdateControl = true;
        });
        updateSanatci.addActionListener(e -> {
            Sanatci sanatci = sanatciList.get(jSanatciList.getSelectedIndex());
            textSanatciIsim.setText(sanatci.getSanatciAd());
            textSanatciUlke.setText(sanatci.getUlke());
            sanatciUpdateControl = true;
        });
        updateAlbum.addActionListener(e -> {
            Album album = albumList.get(jAlbumList.getSelectedIndex());
            textAlbumIsim.setText(album.getAlbumAd());
            textAlbumTarih.setText(album.getTarih());
            albumUpdateControl = true;
        });
    }


    private void sarkiEklemeMenu(JButton btn) {
        ArrayList<Integer> sanatciIdList = new ArrayList<>();

        JComboBox<String> boxTur = new JComboBox<>(turler);
       albumArray = new String[albumList.size()];
        for (int i = 0; i < albumList.size(); i++) {
            albumArray[i] = albumList.get(i).getAlbumAd();
        }
        JComboBox<String> boxAlbum = new JComboBox<>(albumArray);
        textSarkiUlke = new JTextField();
        textSarkiSure = new JTextField();
        textSarkiIsim = new JTextField();
        textSarkiTarih = new JTextField();
        textFieldSarkiDinlenmeS = new JTextField();
        JScrollPane scrollList = new JScrollPane();
        JList<String> jSanatciList = new JList<>(listModelSanatci);
        listModelSarkiEkleme1 = new DefaultListModel<>();
        JList<String> jEklenenSanatci = new JList<>(listModelSarkiEkleme1);
        JScrollPane scrollListEklenen = new JScrollPane();
        scrollListEklenen.setViewportView(jEklenenSanatci);
        jEklenenSanatci.setLayoutOrientation(JList.VERTICAL);

        scrollList.setViewportView(jSanatciList);
        jSanatciList.setLayoutOrientation(JList.VERTICAL);

        scrollList.setBounds(120, 280, 100, 120);
        scrollListEklenen.setBounds(240, 280, 100, 120);
        textSarkiIsim.setBounds(120, 45, 100, 15);
        textSarkiUlke.setBounds(120, 65, 100, 15);

        textSarkiSure.setBounds(120, 160, 100, 20);
        textSarkiTarih.setBounds(120, 200, 100, 20);
        textFieldSarkiDinlenmeS.setBounds(120, 240, 100, 20);
        jSanatciList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Sanatci sanatci = sanatciList.get(jSanatciList.getSelectedIndex());
                    if (!sanatciIdList.contains(sanatci.getSanatciId())) {
                        listModelSarkiEkleme1.addElement(sanatci.toString());
                        sanatciIdList.add(sanatci.getSanatciId());
                    } else
                        showMessage("Bu sanatci zaten ekli.");
                }
            }
        });

        btn.addActionListener(e -> {
            if (textFieldSarkiDinlenmeS.getText().isEmpty())
                textFieldSarkiDinlenmeS.setText("0");
            Sarki sarki = new Sarki(textSarkiIsim.getText(), textSarkiTarih.getText(), albumList.get(boxAlbum.getSelectedIndex()).getAlbumId(),
                    turler[boxTur.getSelectedIndex()], textSarkiSure.getText(), textSarkiUlke.getText(), Integer.parseInt(textFieldSarkiDinlenmeS.getText()), sanatciIdList);
            if (sarki.emptyControl()) {
                if (sarkiUpdateControl) {
                    sarki.setSarkiId(sarkiList.get(jSarkiList.getSelectedIndex()).getSarkiId());
                    dbHelper.updateSarki(sarki);
                    sarkiList = dbHelper.getAllSarki();
                    listModelSarki.clear();
                    for (Sarki sarki1 : sarkiList)
                        listModelSarki.addElement(sarki1.toString());
                    sarkiUpdateControl = false;
                } else {
                    dbHelper.addSarki(sarki);
                    sarkiList = dbHelper.getAllSarki();
                    listModelSarki.addElement(sarkiList.get(sarkiList.size() - 1).toString());
                }
            } else {
                showMessage("Verileri doldurunuz");
            }
            sanatciIdList.clear();
            textSarkiIsim.setText("");
            textSarkiTarih.setText("");
            textSarkiSure.setText("");
            textSarkiUlke.setText("");
            textFieldSarkiDinlenmeS.setText("");
            listModelSarkiEkleme1.clear();
        });

        boxTur.setSelectedIndex(0);
        boxTur.setBounds(120, 90, 100, 20);
        boxAlbum.setBounds(120, 120, 100, 20);

        JLabel label1 = new JLabel("Sarki Tur");
        JLabel label8 = new JLabel("Sarki Ulke");
        JLabel label2 = new JLabel("Sarki Sanatci");
        JLabel label3 = new JLabel("Sarki Album");
        JLabel label4 = new JLabel("Sarki Suresi");
        JLabel label5 = new JLabel("Sarki Tarih");
        JLabel label6 = new JLabel("Dinlenme Sayisi");
        JLabel label7 = new JLabel("Sarki ad");
        label7.setBounds(20, 0, 100, 100);
        label8.setBounds(20, 20, 100, 100);
        label1.setBounds(20, 50, 100, 100);
        label3.setBounds(20, 80, 100, 100);
        label4.setBounds(20, 120, 100, 100);
        label5.setBounds(20, 160, 100, 100);
        label6.setBounds(20, 200, 100, 100);
        label2.setBounds(20, 240, 100, 100);


        btn.setBounds(60, 400, 150, 30);
        btn2.setBounds(360, 400, 150, 30);
        btn3.setBounds(660, 400, 150, 30);

        pnlButton.setBounds(800, 800, 200, 100);
        pnlButton.setLayout(null);
        pnlButton.add(btn);
        pnlButton.add(btn2);
        pnlButton.add(btn3);
        add(btn);
        add(btn2);
        add(btn3);
        add(boxTur);
        add(scrollListEklenen);
        add(boxAlbum);
        add(scrollList);
        add(textSarkiIsim);
        add(textSarkiUlke);
        add(textSarkiSure);
        add(textSarkiTarih);
        add(textFieldSarkiDinlenmeS);
        add(label1);
        add(label8);
        add(label7);
        add(label2);
        add(label3);
        add(label4);
        add(label5);
        add(label6);
        add(pnlButton);
    }

    private void sanatciEklemeMenu(JButton btn2) {
        setLayout(null);
        textSanatciIsim = new JTextField();
        textSanatciUlke = new JTextField();

        textSanatciIsim.setBounds(380, 80, 100, 20);
        textSanatciUlke.setBounds(380, 120, 100, 20);
        JLabel labelIsim = new JLabel("Ad");
        JLabel labelUlke = new JLabel("Ulke");
        labelIsim.setBounds(350, 80, 100, 20);
        labelUlke.setBounds(350, 120, 100, 20);
        btn2.addActionListener(e -> {
            Sanatci sanatci = new Sanatci(textSanatciIsim.getText(), textSanatciUlke.getText());
            if (sanatci.emptyControl()) {
                if (sanatciUpdateControl) {
                    sanatci.setSanatciId(sanatciList.get(jSanatciList.getSelectedIndex()).getSanatciId());
                    dbHelper.updateSanatci(sanatci);
                    sanatciList = dbHelper.getAllSanatci();
                    listModelSanatci.clear();
                    for (Sanatci sanatci1 : sanatciList)
                        listModelSanatci.addElement(sanatci1.toString());
                    sanatciUpdateControl = false;
                } else {
                    dbHelper.addSanatci(sanatci);
                    sanatciList = dbHelper.getAllSanatci();
                    listModelSanatci.addElement(sanatciList.get(sanatciList.size() - 1).toString());
                }
            } else {
                showMessage("Verileri doldurunuz");
            }
            textSanatciIsim.setText("");
            textSanatciUlke.setText("");
        });
        add(labelUlke);
        add(labelIsim);
        add(textSanatciIsim);
        add(textSanatciUlke);
    }

    private void albumEklemeMenu(JButton btn3) {
        ArrayList<Integer> sanatciIdList = new ArrayList<>();
        ArrayList<Integer> sarkiIdList = new ArrayList<>();
        setLayout(null);
        textAlbumIsim = new JTextField();
        textAlbumTarih = new JTextField();
        JComboBox<String> box1 = new JComboBox<>(turler);
        JList<String> jAlbumSanatciList = new JList<>(listModelSanatci);
        JScrollPane scrollPaneSanatci = new JScrollPane();
        scrollPaneSanatci.setViewportView(jAlbumSanatciList);
        jAlbumSanatciList.setLayoutOrientation(JList.VERTICAL);
        listModelAlbumSanatciEkleme = new DefaultListModel<>();
        JList<String> JEklenenSanatci = new JList<>(listModelAlbumSanatciEkleme);
        JScrollPane scrollPaneEklenenSanatci = new JScrollPane();
        scrollPaneEklenenSanatci.setViewportView(JEklenenSanatci);
        JEklenenSanatci.setLayoutOrientation(JList.VERTICAL);

        JList<String> jAlbumSarkiList = new JList<>(listModelSarki);
        JScrollPane scrollPaneSarki = new JScrollPane();
        scrollPaneSarki.setViewportView(jAlbumSarkiList);
        jAlbumSarkiList.setLayoutOrientation(JList.VERTICAL);
        listModelAlbumSarkiEkleme = new DefaultListModel<>();
        JList<String> jEklenenSarkiList = new JList<>(listModelAlbumSarkiEkleme);
        JScrollPane scrollPaneEklenenSarki = new JScrollPane();
        scrollPaneEklenenSarki.setViewportView(jEklenenSarkiList);
        JEklenenSanatci.setLayoutOrientation(JList.VERTICAL);


        scrollPaneSanatci.setBounds(680, 200, 150, 100);
        scrollPaneEklenenSanatci.setBounds(850, 200, 150, 100);
        scrollPaneSarki.setBounds(680, 300, 150, 100);
        scrollPaneEklenenSarki.setBounds(850, 300, 150, 100);

        JLabel labelIsim = new JLabel("Ad");
        JLabel labelTarih = new JLabel("Tarih");
        JLabel labelTur = new JLabel("Tur");
        JLabel labelSanatci = new JLabel("Sanatci");
        JLabel labelSarki = new JLabel("Sarki");
        textAlbumIsim.setBounds(680, 80, 100, 20);
        textAlbumTarih.setBounds(680, 120, 100, 20);
        box1.setBounds(680, 160, 100, 20);

        labelIsim.setBounds(630, 80, 100, 20);
        labelTarih.setBounds(630, 120, 100, 20);
        labelTur.setBounds(630, 160, 100, 20);
        labelSanatci.setBounds(630, 200, 100, 20);
        labelSarki.setBounds(630, 300, 100, 20);
        jAlbumSanatciList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Sanatci sanatci = sanatciList.get(jAlbumSanatciList.getSelectedIndex());
                    if (!sanatciIdList.contains(sanatci.getSanatciId())) {
                        listModelAlbumSanatciEkleme.addElement(sanatci.toString());
                        sanatciIdList.add(sanatci.getSanatciId());
                    } else
                        showMessage("Bu sanatci zaten ekli.");
                }
            }
        });
        jAlbumSarkiList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Sarki sarki = sarkiList.get(jAlbumSarkiList.getSelectedIndex());
                    if (!sarkiIdList.contains(sarki.getSarkiId())) {
                        listModelAlbumSarkiEkleme.addElement(sarki.toString());
                        sarkiIdList.add(sarki.getSarkiId());
                    } else
                        showMessage("Bu sarki zaten ekli");
                }
            }
        });
        btn3.addActionListener(e -> {
            Album album = new Album(textAlbumIsim.getText(), textAlbumTarih.getText(), turler[box1.getSelectedIndex()], sarkiIdList, sanatciIdList);
            if (album.emptyControl()) {
                if (albumUpdateControl) {
                    album.setAlbumId(albumList.get(jAlbumList.getSelectedIndex()).getAlbumId());
                    dbHelper.updateAlbum(album);
                    albumUpdateControl = false;
                } else {
                    dbHelper.addAlbum(album);
                    albumList = dbHelper.getAllAlbum();
                    listModelAlbum.addElement(albumList.get(albumList.size() - 1).toString());
                }
            } else {
                showMessage("Verileri doldurunuz");
            }
            sarkiIdList.clear();
            sanatciIdList.clear();
            listModelAlbumSarkiEkleme.clear();
            listModelAlbumSanatciEkleme.clear();
            textAlbumIsim.setText("");
            textAlbumTarih.setText("");
        });
        add(scrollPaneSanatci);
        add(scrollPaneEklenenSanatci);
        add(scrollPaneEklenenSarki);
        add(scrollPaneSarki);
        add(labelIsim);
        add(labelTarih);
        add(labelTur);
        add(labelSanatci);
        add(labelSarki);
        add(textAlbumIsim);
        add(textAlbumTarih);
        add(box1);
    }

    private void showKullaniciList() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        ArrayList<Kullanici> kullaniciList = dbHelper.getAllKullanici();
        JLabel label = new JLabel("Kullanicilar");
        for (Kullanici kullanici : kullaniciList)
            listModel.addElement(kullanici.toString());
        JList<String> jList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(jList);
        jList.setLayoutOrientation(JList.VERTICAL);
        label.setBounds(1120, 30, 100, 20);
        scrollPane.setBounds(1050, 50, 200, 700);
        add(label);
        add(scrollPane);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(AdminAnaSayfa.this,
                message, "Hata", JOptionPane.WARNING_MESSAGE);
    }
}
