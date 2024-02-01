package com.company.view;

import com.company.model.CalmaList;
import com.company.model.Kullanici;
import com.company.model.Sarki;
import com.company.service.DbHelper;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class KullaniciAnaSayfa extends JFrame {
    DbHelper dbHelper;
    final Kullanici kullanici;
    JLabel jLabel;
    ArrayList<CalmaList> kullaniciCalmaListeleri;
    JLabel sarkiText;
    ArrayList<String> turList;

    public KullaniciAnaSayfa(Kullanici kullanici) {
        jLabel = new JLabel();
        dbHelper = new DbHelper();
        this.kullanici = kullanici;
        kullaniciCalmaListeleri = dbHelper.getKullaniciCalmaList(kullanici.getKullaniciID());
        turList = new ArrayList<>();
        turList.add("pop");
        turList.add("jazz");
        turList.add("klasik");
        showCalmaList();
        showTopOnList();
        showTakipEdilenList();
        showKullaniciButton();
        add(jLabel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 800);
    }

    private void showCalmaList() {
        kullaniciCalmaListeleri = dbHelper.getKullaniciCalmaList(kullanici.getKullaniciID());
        add(setText("Çalma Listeleri", 25, 8, 100));
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (CalmaList item : kullaniciCalmaListeleri) {
            listModel.addElement(item.getCalmaListAd().replace(kullanici.getKullaniciAd(), ""));
        }
        JList<String> jCalmaList = new JList<>(listModel);
        JScrollPane listScroller = new JScrollPane();
        listScroller.setViewportView(jCalmaList);
        jCalmaList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(8, 30, 120, 720);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Sil");
        popupMenu.add(delete);
        add(listScroller);
        jCalmaList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showCalmaListSarkilar(kullaniciCalmaListeleri.get(jCalmaList.getSelectedIndex()));
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        delete.addActionListener(actionEvent -> {
            int index = jCalmaList.getSelectedIndex();
            dbHelper.deleteCalmaList(kullaniciCalmaListeleri.get(index).getCalmaListId());
            listModel.remove(index);
            kullaniciCalmaListeleri.remove(index);
        });
    }

    private void showCalmaListSarkilar(CalmaList calmaList) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JButton btnKapat = new JButton("Kapat");
        JLabel calmaListText = setText(calmaList.getCalmaListAd().replace(kullanici.getKullaniciAd(), ""), 460, 250, 200);
        ArrayList<Integer> sarkiIdList = calmaList.getSarkiIdList();
        ArrayList<Sarki> sarkiList = new ArrayList<>();
        JScrollPane listScroller = new JScrollPane();
        for (int i : sarkiIdList) {
            sarkiList.add(dbHelper.getSarki(i));
        }
        for (Sarki item : sarkiList)
            listModel.addElement(item.toString());
        JList<String> jSarkiList = new JList<>(listModel);
        listScroller.setViewportView(jSarkiList);
        jSarkiList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(330, 270, 300, 150);
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Sil");
        JMenuItem add = new JMenuItem("Ekle");
        if (kullaniciCalmaListeleri.contains(calmaList))
            popupMenu.add(delete);
        popupMenu.add(add);
        btnKapat.setBounds(560, 250, 70, 20);
        add(listScroller);
        add(calmaListText);
        add(btnKapat);
        add(jLabel);
        repaint();
        validate();
        jSarkiList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (kullaniciCalmaListeleri.contains(calmaList)) {
                        int index = jSarkiList.getSelectedIndex();
                        sarkiList.get(index).setDinlenmeSayisi(sarkiList.get(index).dinlenmeSayisi + 1);
                        listModel.set(index, sarkiList.get(index).toString());
                        showSarkiCal(sarkiList.get(index));
                    }
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        delete.addActionListener(actionEvent -> {
            int index = jSarkiList.getSelectedIndex();
            dbHelper.deleteCalmaListSarki(calmaList.getCalmaListId(), sarkiIdList.get(index));
            listModel.remove(index);
            sarkiIdList.remove(index);
        });
        add.addActionListener(actionEvent -> showCalmaListEkle(sarkiIdList.get(jSarkiList.getSelectedIndex())));
        btnKapat.addActionListener(e -> {
            remove(btnKapat);
            remove(listScroller);
            remove(calmaListText);
            repaint();
            validate();
        });
    }

    private void showSarkiCal(Sarki sarki) {
        if (sarkiText != null)
            remove(sarkiText);
        dbHelper.updateDinlenmeSayisi(sarki.getSarkiId());
        sarkiText = setText(sarki.getSarkiAd() + " Caliyor", 480, 700, 200);
        add(sarkiText);
        add(jLabel);
        repaint();
        validate();
    }

    private void showCalmaListEkle(int sarkiId) {
        Sarki sarki = dbHelper.getSarki(sarkiId);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (CalmaList item : kullaniciCalmaListeleri)
            listModel.addElement(item.getCalmaListAd().replace(kullanici.getKullaniciAd(), ""));
        JList<String> jCalmaList = new JList<>(listModel);
        JScrollPane listScroller = new JScrollPane();
        listScroller.setViewportView(jCalmaList);
        jCalmaList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(650, 270, 120, 200);
        add(listScroller);
        add(jLabel);
        repaint();
        validate();
        jCalmaList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = jCalmaList.getSelectedIndex();
                    CalmaList calmaList = kullaniciCalmaListeleri.get(index);
                    if (!calmaList.getSarkiIdList().contains(sarki.getSarkiId())) {
                        boolean control = true;
                        String tur = "";
                        for (String value : turList) {
                            if (calmaList.getCalmaListAd().contains(value)) {
                                tur = value;
                                control = false;
                                break;
                            }
                        }
                        if (control) {
                            dbHelper.addCalmaListesiSarki(calmaList.getCalmaListId(), sarki.getSarkiId());
                        } else {
                            if (sarki.getTur().equals(tur)) {
                                dbHelper.addCalmaListesiSarki(calmaList.getCalmaListId(), sarki.getSarkiId());
                            } else {
                                showMessage("Farkli turden sarki ekleyemezsiniz.");
                            }
                        }
                    } else {
                        showMessage("Bu sarki zaten listede bulunmaktadir.");
                    }
                    remove(listScroller);
                    repaint();
                    validate();
                }
            }
        });
    }


    private void showTopOnList() {
        getTopOnLists("ulke", kullanici.getUlke(), 170, 150);
        getTopOnLists("tur", "pop", 330, 300);
        getTopOnLists("global", "", 470, 450);
        getTopOnLists("tur", "jazz", 620, 600);
        getTopOnLists("tur", "klasik", 770, 750);
    }

    private void getTopOnLists(String type1, String type2, int labelX, int panelX) {
        ArrayList<Sarki> topOnList = dbHelper.getTopOnList(type1, type2);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JScrollPane listScroller = new JScrollPane();
        String listName;
        if (type2.isEmpty())
            listName = "Global";
        else
            listName = type2.substring(0, 1).toUpperCase() + type2.substring(1);
        add(setText(listName + " Top 10", labelX, 8, 100));
        int i = 0;
        for (Sarki item : topOnList) {
            if (i < 10)
                listModel.addElement(item.topOnToString());
            else break;
            i++;
        }

        JList<String> jTopOnList = new JList<>(listModel);
        listScroller.setViewportView(jTopOnList);
        jTopOnList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(panelX, 30, 120, 200);
        add(listScroller);
    }

    private void showTakipEdilenList() {
        ArrayList<Kullanici> takipEdilenList = dbHelper.getTakipEdilen(kullanici.getKullaniciID());
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JScrollPane listScroller = new JScrollPane();
        for (Kullanici item : takipEdilenList)
            listModel.addElement(item.getKullaniciAd());
        JList<String> jTakipEdilenList = new JList<>(listModel);
        listScroller.setViewportView(jTakipEdilenList);
        jTakipEdilenList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(900, 60, 120, 690);
        add(listScroller);
        add(setText("Takip Edilenler", 930, 40, 100));
        jTakipEdilenList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showTakipEdilenCalmaList(takipEdilenList.get(jTakipEdilenList.getSelectedIndex()));
                }
            }
        });
    }

    private void showTakipEdilenCalmaList(Kullanici kullanici) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JScrollPane listScroller = new JScrollPane();
        JLabel takipEdilenText = setText(kullanici.getKullaniciAd(), 450, 450, 200);
        JButton btnClose = new JButton("Kapat");
        ArrayList<CalmaList> calmaLists = dbHelper.getKullaniciCalmaList(kullanici.getKullaniciID());
        for (CalmaList calmaList : calmaLists)
            listModel.addElement(calmaList.getCalmaListAd().replace(kullanici.getKullaniciAd(), ""));
        JList<String> jCalmaList = new JList<>(listModel);
        listScroller.setViewportView(jCalmaList);
        jCalmaList.setLayoutOrientation(JList.VERTICAL);
        listScroller.setBounds(370, 470, 200, 150);
        btnClose.setBounds(500, 450, 70, 20);
        add(listScroller);
        add(takipEdilenText);
        add(btnClose);
        add(jLabel);
        repaint();
        validate();
        jCalmaList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showCalmaListSarkilar(calmaLists.get(jCalmaList.getSelectedIndex()));
                }
            }
        });
        btnClose.addActionListener(e -> {
            remove(btnClose);
            remove(listScroller);
            remove(takipEdilenText);
            repaint();
            validate();
        });
    }

    private void showKullaniciButton() {
        JButton btnKullanici = new JButton(kullanici.getKullaniciAd());
        btnKullanici.setBounds(910, 8, 100, 20);
        add(btnKullanici);
        btnKullanici.addActionListener(e -> {
            super.dispose();
            new KullaniciProfilSayfa(kullanici);
        });
    }

    private JLabel setText(String text, int x, int y, int width) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setBounds(x, y, width, 20);
        return label;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(KullaniciAnaSayfa.this,
                message, "Hata", JOptionPane.WARNING_MESSAGE);
    }
}
