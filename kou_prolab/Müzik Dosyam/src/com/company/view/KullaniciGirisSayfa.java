package com.company.view;

import com.company.model.Kullanici;
import com.company.service.DbHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class KullaniciGirisSayfa extends JFrame {
    public KullaniciGirisSayfa() {
        DbHelper dbHelper = new DbHelper();
        TextField isimText = new TextField();
        TextField sifreText = new TextField();
        JButton btnGiris = new JButton("Giris yap");
        JButton btnKayit = new JButton("Kaydol");
        isimText.setBounds(90, 40, 200, 20);
        sifreText.setBounds(90, 120, 200, 20);
        btnGiris.setBounds(80, 200, 90, 20);
        btnKayit.setBounds(200, 200, 90, 20);
        add(setText("Isim: ", 40));
        add(setText("Sifre: ", 120));
        add(isimText);
        add(sifreText);
        add(btnGiris);
        add(btnKayit);
        add(new JLabel());
        btnGiris.addActionListener(e -> {
            boolean control = true;
            ArrayList<Kullanici> kullaniciList = dbHelper.getAllKullanici();
            for (Kullanici kullanici : kullaniciList) {
                if (kullanici.getKullaniciAd().equals(isimText.getText())) {
                    if (kullanici.getSifre().equals(sifreText.getText())) {
                        control = false;
                        super.dispose();
                        new KullaniciAnaSayfa(kullanici);
                        break;
                    } else {
                        showMessage("Sifre Yanlis!");
                        control = false;
                    }
                }
            }
            if (control) showMessage("Kullanici bulunamadi");
        });
        btnKayit.addActionListener(e -> {
            super.dispose();
            new KullaniciKayitSayfa();
        });
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300);
    }

    private JLabel setText(String text, int y) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setBounds(20, y, 60, 20);
        return label;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(KullaniciGirisSayfa.this,
                message, "Hata", JOptionPane.WARNING_MESSAGE);
    }
}
