package com.company.view;

import com.company.model.CalmaList;
import com.company.model.Kullanici;
import com.company.service.DbHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class KullaniciKayitSayfa extends JFrame {
    public KullaniciKayitSayfa() {
        DbHelper dbHelper = new DbHelper();
        TextField isimText = new TextField();
        TextField sifreText = new TextField();
        TextField emailText = new TextField();
        TextField ulkeText = new TextField();
        AtomicReference<String> abonelik = new AtomicReference<>("Normal");
        JCheckBox checkBoxPremium = new JCheckBox("Premium");
        JCheckBox checkBoxNormal = new JCheckBox("Normal");
        JButton btnKayit = new JButton("Kayit");
        isimText.setBounds(90, 40, 200, 20);
        sifreText.setBounds(90, 120, 200, 20);
        emailText.setBounds(90, 200, 200, 20);
        ulkeText.setBounds(90, 280, 200, 20);
        checkBoxPremium.setBounds(100, 360, 100, 20);
        checkBoxNormal.setBounds(200, 360, 100, 20);
        btnKayit.setBounds(120, 440, 100, 20);
        add(setText("Isim: ", 40));
        add(setText("Sifre: ", 120));
        add(setText("Email: ", 200));
        add(setText("Ulke: ", 280));
        add(setText("Abonelik: ", 360));
        add(isimText);
        add(sifreText);
        add(emailText);
        add(ulkeText);
        add(checkBoxPremium);
        add(checkBoxNormal);
        add(btnKayit);
        add(new JLabel());
        checkBoxPremium.addItemListener(e -> {
            if (checkBoxPremium.isSelected()) {
                checkBoxNormal.setSelected(false);
                abonelik.set("Premium");
            }
        });
        checkBoxNormal.addItemListener(e -> {
            if (checkBoxNormal.isSelected()) {
                checkBoxPremium.setSelected(false);
                abonelik.set("Normal");
            }
        });
        btnKayit.addActionListener(e -> {
            if (isimText.getText().isEmpty() || emailText.getText().isEmpty() || sifreText.getText().isEmpty() || ulkeText.getText().isEmpty()) {
                JOptionPane.showMessageDialog(KullaniciKayitSayfa.this,
                        "Eksik bilgileri doldurunuz.", "Hata", JOptionPane.WARNING_MESSAGE);
            } else {
                Kullanici kullanici = new Kullanici(isimText.getText(), emailText.getText(), sifreText.getText(), abonelik.toString(),
                        ulkeText.getText(), 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                dbHelper.addCalmaList(new CalmaList(kullanici.getKullaniciAd() + "pop", new ArrayList<>()));
                dbHelper.addCalmaList(new CalmaList(kullanici.getKullaniciAd() + "klasik", new ArrayList<>()));
                dbHelper.addCalmaList(new CalmaList(kullanici.getKullaniciAd() + "jazz", new ArrayList<>()));
                ArrayList<Integer> calmaListId = dbHelper.getLast3CalmaListId();
                kullanici.setCalmaList(calmaListId);
                dbHelper.addKullanici(kullanici);
                super.dispose();
                new KullaniciGirisSayfa();
            }
        });
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 550);
    }

    private JLabel setText(String text, int y) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setBounds(20, y, 60, 20);
        return label;
    }
}
