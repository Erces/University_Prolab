package com.company.view;

import javax.swing.*;

public class GirisSayfa extends JFrame {
    public GirisSayfa() {
        JButton btnAdmin = new JButton("Admin");
        JButton btnKullanici = new JButton("Kullanici");
        btnAdmin.setBounds(20, 20, 200, 200);
        btnKullanici.setBounds(260, 20, 200, 200);
        add(btnAdmin);
        add(btnKullanici);
        add(new JLabel());
        btnAdmin.addActionListener(e -> {
            super.dispose();
            new AdminAnaSayfa();
        });
        btnKullanici.addActionListener(e -> {
            super.dispose();
            new KullaniciGirisSayfa();
        });
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
    }
}
