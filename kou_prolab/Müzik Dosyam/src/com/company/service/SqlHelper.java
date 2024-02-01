package com.company.service;

import java.util.ArrayList;

class SqlHelper {
    private static final String sqlSanatci = "CREATE TABLE sanatci " +
            "(sanatci_id INTEGER IDENTITY(1,1), sanatci_ad VARCHAR(255), " +
            "ulke VARCHAR(255), PRIMARY KEY ( sanatci_id ))";

    private static final String sqlSarki = "CREATE TABLE sarki " +
            "(sarki_id INTEGER IDENTITY(1,1), sarki_ad VARCHAR(255), " +
            "tarih VARCHAR(255), album_id INTEGER, tur VARCHAR(255), " +
            "sure VARCHAR(255), dinlenme_sayisi INTEGER, ulke VARCHAR(255), PRIMARY KEY ( sarki_id ))";

    private static final String sqlSarkiSanatci = "CREATE TABLE sarki_sanatci " +
            "(sarki_id INTEGER FOREIGN KEY REFERENCES sarki(sarki_id) ON DELETE CASCADE," +
            "sanatci_id INTEGER FOREIGN KEY REFERENCES sanatci(sanatci_id) ON DELETE CASCADE)";


    private static final String sqlAlbum = "CREATE TABLE album (album_id INTEGER IDENTITY(1,1), " +
            "album_ad VARCHAR(255), tarih VARCHAR(255), tur VARCHAR(255), PRIMARY KEY ( album_id ))";

    private static final String sqlAlbumSarkiSanatci = "CREATE TABLE album_sarki_sanatci " +
            "(album_id INTEGER FOREIGN KEY REFERENCES album(album_id) ON DELETE CASCADE," +
            " sarki_id INTEGER FOREIGN KEY REFERENCES sarki(sarki_id) ON DELETE CASCADE, " +
            "sanatci_id INTEGER FOREIGN KEY REFERENCES sanatci(sanatci_id) ON DELETE CASCADE)";

    private static final String sqlCalmaList = "CREATE TABLE calma_list (calma_list_id INTEGER IDENTITY(1,1), " +
            "calma_list_ad VARCHAR(255), PRIMARY KEY ( calma_list_id ))";

    private static final String sqlCalmaListSarkilar = "CREATE TABLE calma_list_sarkilar " +
            "(calma_list_id INTEGER FOREIGN KEY REFERENCES calma_list(calma_list_id) ON DELETE CASCADE, " +
            " sarki_id INTEGER FOREIGN KEY REFERENCES sarki(sarki_id) ON DELETE CASCADE)";

    private static final String sqlKullanici = "CREATE TABLE kullanici (kullanici_id INTEGER IDENTITY(1,1), " +
            "kullanici_ad VARCHAR(255), email VARCHAR(255), sifre VARCHAR(255), " +
            "abonelik VARCHAR(255), ulke VARCHAR(255), odeme bit NOT NULL DEFAULT (0), PRIMARY KEY ( kullanici_id ))";

    private static final String sqlKullaniciCalmaList = "CREATE TABLE kullanici_calma_list " +
            "(kullanici_id INTEGER FOREIGN KEY REFERENCES kullanici(kullanici_id) ON DELETE CASCADE," +
            " calma_list_id INTEGER FOREIGN KEY REFERENCES calma_list(calma_list_id) ON DELETE CASCADE)";

    private static final String sqlKullaniciTakipEdilen = "CREATE TABLE kullanici_takip_edilen " +
            "(kullanici_id INTEGER FOREIGN KEY REFERENCES kullanici(kullanici_id) ," +
            " kullanici_takip_edilen_id INTEGER FOREIGN KEY REFERENCES kullanici(kullanici_id) ON DELETE CASCADE)";

    private static final String sqlKullaniciTakipci = "CREATE TABLE kullanici_takipci " +
            "(kullanici_id INTEGER FOREIGN KEY REFERENCES kullanici(kullanici_id) ," +
            " kullanici_takipci_id INTEGER FOREIGN KEY REFERENCES kullanici(kullanici_id) ON DELETE CASCADE)";

    public static ArrayList<String> getAllSql() {
        ArrayList<String> list = new ArrayList<>();
        list.add(sqlSanatci);
        list.add(sqlAlbum);
        list.add(sqlSarki);
        list.add(sqlSarkiSanatci);
        list.add(sqlAlbumSarkiSanatci);
        list.add(sqlCalmaList);
        list.add(sqlCalmaListSarkilar);
        list.add(sqlKullanici);
        list.add(sqlKullaniciCalmaList);
        list.add(sqlKullaniciTakipEdilen);
        list.add(sqlKullaniciTakipci);
        return list;
    }

    public static ArrayList<String> getAllTableName() {
        ArrayList<String> list = new ArrayList<>();
        list.add("sanatci");
        list.add("album");
        list.add("sarki");
        list.add("sarki_sanatci");
        list.add("album_sarki_sanatci");
        list.add("calma_list");
        list.add("calma_list_sarkilar");
        list.add("kullanici");
        list.add("kullanici_calma_list");
        list.add("kullanici_takip_edilen");
        list.add("kullanici_takipci");
        return list;
    }
}
