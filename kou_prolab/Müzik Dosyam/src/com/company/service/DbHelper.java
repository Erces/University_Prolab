package com.company.service;

import com.company.model.*;

import java.sql.*;
import java.util.ArrayList;

public class DbHelper implements IDbHelper {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=prolab;user=prolab3;password=12345";
    private Statement st;
    private ResultSet rs;
    Connection con;

    public DbHelper() {
        ArrayList<String> allSql = SqlHelper.getAllSql();
        ArrayList<String> tableName = SqlHelper.getAllTableName();
        try {
            con = DriverManager.getConnection(URL);
            st = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < allSql.size(); i++) {
            try {
                st.executeQuery("select * from " + tableName.get(i));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                try {
                    st.executeUpdate(allSql.get(i));
                } catch (SQLException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    @Override
    public void addSanatci(Sanatci sanatci) {
        if (kayitKontrol("sanatci", "sanatci_ad", sanatci.sanatciAd)) {
            try {
                st.execute(String.format("insert  into sanatci(sanatci_ad, ulke) values ('%s', '%s')  ", sanatci.sanatciAd, sanatci.ulke));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void addSarki(Sarki sarki) {
        if (kayitKontrol("sarki", "sarki_ad", sarki.sarkiAd)) {
            try {
                st.execute(String.format("insert into  sarki(sarki_ad, tarih, album_id, tur, sure, ulke, dinlenme_sayisi)"
                        + " values( '%s', '%s', %d, '%s', '%s', '%s', %d )", sarki.sarkiAd, sarki.tarih, sarki.albumId, sarki.tur, sarki.sure, sarki.ulke, sarki.dinlenmeSayisi));
                addSarkiSanatci(sarki, getId("sarki"));
                ArrayList<Sarki> sarkiArrayList = getAllSarki();
                addAlbumSarkiSanatci(sarki.getAlbumId(), sarkiArrayList.get(sarkiArrayList.size() - 1).getSarkiId(), sarki.getSanatciIdList());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void addAlbum(Album album) {
        if (kayitKontrol("album", "album_ad", album.albumAd)) {
            try {
                st.execute(String.format("insert  into  album(album_ad, tarih, tur)"
                        + " values('%s', '%s', '%s')", album.albumAd, album.tarih, album.tur));
                addMultiAlbumSarkiSanatci(album, getId("album"));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void addCalmaList(CalmaList calmaList) {
        if (kayitKontrol("calma_list", "calma_list_ad", calmaList.calmaListAd)) {
            try {
                st.execute(String.format("insert into calma_list(calma_list_ad) values('%s')", calmaList.calmaListAd));
                addCalmaListSarkilar(getId("calma_list"), calmaList);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void addKullanici(Kullanici kullanici) {
        if (kayitKontrol("kullanici", "kullanici_ad", kullanici.kullaniciAd)) {
            try {
                st.execute(String.format("insert into kullanici(kullanici_ad, email, sifre, abonelik, ulke, odeme)" +
                        " values('%s', '%s', '%s', '%s', '%s', %d)", kullanici.kullaniciAd, kullanici.email, kullanici.sifre, kullanici.abonelik, kullanici.ulke, kullanici.odeme));
                addKullaniciMergeList(kullanici, getId("kullanici"));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void addCalmaListesiSarki(int calmaListId, int sarkiId) {
        try {
            st.execute(String.format("insert into calma_list_sarkilar(calma_list_id, sarki_id) " +
                    "values(%d, %d)", calmaListId, sarkiId));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void takipEt(int kullaniciId, int takipEdilenId) {
        try {
            st.execute(String.format("insert  into kullanici_takip_edilen(kullanici_id, kullanici_takip_edilen_id)" +
                    " values(%d, %d)", kullaniciId, takipEdilenId));
            st.execute(String.format("insert into kullanici_takipci(kullanici_id, kullanici_takipci_id) " +
                    "values(%d,%d)", takipEdilenId, kullaniciId));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void deleteSanatci(int id) {
        delete("sanatci", "sanatci_id", id);
    }

    @Override
    public void deleteSarki(int id) {
        delete("sarki", "sarki_id", id);
    }

    @Override
    public void deleteAlbum(int id) {
        delete("sarki", "album_id", id);
        delete("album", "album_id", id);
    }

    @Override
    public void deleteCalmaList(int id) {
        delete("calma_list", "calma_list_id", id);
    }

    @Override
    public void deleteTakipEdilen(int id) {
        delete("kullanici_takip_edilen", "kullanici_takip_edilen_id", id);
    }

    @Override
    public void deleteTakipci(int id) {
        delete("kullanici_takipci", "kullanici_takipci_id", id);
    }

    @Override
    public void deleteCalmaListSarki(int calmaListId, int sarkiId) {
        try {
            st.executeUpdate(String.format("delete from calma_list_sarkilar where calma_list_id=%d and sarki_id=%d", calmaListId, sarkiId));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Sanatci> getAllSanatci() {
        ArrayList<Sanatci> list = new ArrayList<>();
        Sanatci sanatci = new Sanatci();
        try {
            rs = st.executeQuery("select * from sanatci");
            while (rs.next()) {
                list.add(sanatci.getSanatciModel(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public ArrayList<Sarki> getAllSarki() {
        ArrayList<Sarki> list = new ArrayList<>();
        Sarki sarki = new Sarki();
        try {
            rs = st.executeQuery("select * from sarki");
            while (rs.next()) {
                list.add(sarki.getSarkiModel(rs, null));
            }
            for (Sarki value : list)
                value.setSanatciIdList(getIdList("sarki_sanatci", "sarki_id", "sanatci_id", value.getSarkiId()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public ArrayList<Album> getAllAlbum() {
        ArrayList<Album> list = new ArrayList<>();
        Album album = new Album();
        try {
            rs = st.executeQuery("select * from  album");
            while (rs.next()) {
                list.add(album.getAlbumModel(rs, null, null));
            }
            for (Album value : list) {
                value.setSarkiIdList(getIdList("album_sarki_sanatci", "album_id", "sarki_id", value.getAlbumId()));
                value.setSanatciIdList(getIdList("album_sarki_sanatci", "album_id", "sanatci_id", value.getAlbumId()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public ArrayList<Kullanici> getAllKullanici() {
        ArrayList<Kullanici> list = new ArrayList<>();
        Kullanici kullanici = new Kullanici();
        try {
            rs = st.executeQuery("select * from kullanici");
            while (rs.next()) {
                list.add(kullanici.getKullaniciModel(rs, null, null, null));
            }
            for (Kullanici value : list) {
                value.setTakipEdilenList(getIdList("kullanici_takip_edilen", "kullanici_id", "kullanici_takip_edilen_id", value.getKullaniciID()));
                value.setTakipciList(getIdList("kullanici_takipci", "kullanici_id", "kullanici_takipci_id", value.getKullaniciID()));
                value.setCalmaList(getIdList("kullanici_calma_list", "kullanici_id", "calma_list_id", value.getKullaniciID()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public ArrayList<CalmaList> getAllCalmaList() {
        ArrayList<CalmaList> list = new ArrayList<>();
        CalmaList calmaList = new CalmaList();
        try {
            rs = st.executeQuery("select * from calma_list");
            while (rs.next()) {
                list.add(calmaList.getCalmaListModel(rs, null));
            }
            for (CalmaList value : list)
                value.setSarkiIdList(getIdList("calma_list_sarkilar", "calma_list_id", "sarki_id", value.getCalmaListId()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public Sanatci getSanatci(int id) {
        Sanatci sanatci = new Sanatci();
        try {
            rs = st.executeQuery("select * from sanatci where sanatci_id=" + id);
            while (rs.next()) {
                sanatci = sanatci.getSanatciModel(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sanatci;
    }

    @Override
    public Sarki getSarki(int id) {
        Sarki sarki = new Sarki();
        try {
            ArrayList<Integer> sanatciIdList = getIdList("sarki_sanatci", "sarki_id", "sanatci_id", id);
            rs = st.executeQuery("select * from sarki where sarki_id=" + id);
            while (rs.next()) {
                sarki = sarki.getSarkiModel(rs, sanatciIdList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sarki;
    }

    @Override
    public Album getAlbum(int id) {
        Album album = new Album();
        try {
            ArrayList<Integer> sarkiIdList = getIdList("album_sarki_sanatci", "album_id", "sarki_id", id);
            ArrayList<Integer> sanatciIdList = getIdList("album_sarki_sanatci", "album_id", "sanatci_id", id);
            rs = st.executeQuery("select * from album where  album_id=" + id);
            while (rs.next()) {
                album = album.getAlbumModel(rs, sarkiIdList, sanatciIdList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return album;
    }

    @Override
    public Kullanici getKullanici(int id) {
        Kullanici kullanici = new Kullanici();
        try {
            ArrayList<Integer> takipEdilenIdList = getIdList("kullanici_takip_edilen", "kullanici_id", "kullanici_takip_edilen_id", id);
            ArrayList<Integer> takipciIdList = getIdList("kullanici_takipci", "kullanici_id", "kullanici_takipci_id", id);
            ArrayList<Integer> calmaListIdList = getIdList("kullanici_calma_list", "kullanici_id", "calma_list_id", id);
            rs = st.executeQuery("select  * from  kullanici where kullanici_id=" + id);
            while (rs.next()) {
                kullanici = new Kullanici(id, rs.getString("kullanici_ad"), rs.getString("email"),
                        rs.getString("sifre"), rs.getString("abonelik"), rs.getString("ulke"),
                        rs.getByte("odeme"), takipEdilenIdList, takipciIdList, calmaListIdList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return kullanici;
    }

    @Override
    public ArrayList<Kullanici> getTakipEdilen(int id) {
        ArrayList<Kullanici> takipEdilenList = new ArrayList<>();
        try {
            ArrayList<Integer> idList = getIdList("kullanici_takip_edilen", "kullanici_id", "kullanici_takip_edilen_id", id);
            for (int takipciId : idList)
                takipEdilenList.add(getKullanici(takipciId));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return takipEdilenList;
    }

    @Override
    public ArrayList<Kullanici> getTakipci(int id) {
        ArrayList<Kullanici> takipciList = new ArrayList<>();
        try {
            ArrayList<Integer> idList = getIdList("kullanici_takipci", "kullanici_id", "kullanici_takipci_id", id);
            for (int takipciId : idList)
                takipciList.add(getKullanici(takipciId));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return takipciList;
    }

    @Override
    public CalmaList getCalmaList(int id) {
        CalmaList calmaList = new CalmaList();
        try {
            ArrayList<Integer> sarkiIdList = getIdList("calma_list_sarkilar", "calma_list_id", "sarki_id", id);
            rs = st.executeQuery("select * from calma_list where calma_list_id=" + id);
            while (rs.next()) {
                calmaList = new CalmaList(id, rs.getString("calma_list_ad"), sarkiIdList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return calmaList;
    }

    @Override
    public ArrayList<Integer> getLast3CalmaListId() {
        ArrayList<CalmaList> calmaLists = getAllCalmaList();
        ArrayList<Integer> calmaListId = new ArrayList<>();
        for (int i = 1; i < 4; i++)
            calmaListId.add(calmaLists.get(calmaLists.size() - i).getCalmaListId());
        return calmaListId;
    }

    @Override
    public int getLastCalmaListId() {
        try {
            ArrayList<Integer> calmaListIdList = getIdList("calma_list_sarkilar", "calma_list_id", "calma_list_id", 0);
            return calmaListIdList.get(calmaListIdList.size() - 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public ArrayList<CalmaList> getKullaniciCalmaList(int id) {
        ArrayList<CalmaList> calmaLists = new ArrayList<>();
        try {
            ArrayList<Integer> idList = getIdList("kullanici_calma_list", "kullanici_id", "calma_list_id", id);
            for (int calmaListId : idList)
                calmaLists.add(getCalmaList(calmaListId));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return calmaLists;
    }

    @Override
    public ArrayList<Sarki> getTopOnList(String type1, String type2) {
        ArrayList<Sarki> sarkiList = new ArrayList<>();
        Sarki sarki = new Sarki();
        try {
            if (type1.equals("global")) {
                rs = st.executeQuery("SELECT * FROM  sarki ORDER BY dinlenme_sayisi DESC");
            } else {
                rs = st.executeQuery(String.format("SELECT * FROM  sarki WHERE %s='%s' ORDER BY dinlenme_sayisi DESC", type1, type2));
            }
            while (rs.next()) {
                sarkiList.add(sarki.getSarkiModel(rs, null));
            }
            for (Sarki value : sarkiList)
                value.setSanatciIdList(getIdList("sarki_sanatci", "sarki_id", "sanatci_id", value.getSarkiId()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sarkiList;
    }

    @Override
    public void updateSanatci(Sanatci sanatci) {
        try {
            st.executeUpdate(String.format("UPDATE sanatci SET sanatci_ad='%s', ulke='%s' WHERE sanatci_id=%d",
                    sanatci.getSanatciAd(), sanatci.getUlke(), sanatci.getSanatciId()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateSarki(Sarki sarki) {
        try {
            st.executeUpdate(String.format("UPDATE sarki SET sarki_ad='%s', tarih='%s', album_id=%d, tur='%s', sure='%s', ulke='%s'," +
                            "dinlenme_sayisi=%d WHERE sarki_id=%d", sarki.getSarkiAd(), sarki.getTarih(), sarki.getAlbumId(), sarki.getTur(),
                    sarki.getSure(), sarki.getUlke(), sarki.getDinlenmeSayisi(), sarki.getSarkiId()));
            delete("sarki_sanatci", "sarki_id", sarki.getSarkiId());
            addSarkiSanatci(sarki, sarki.getSarkiId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateAlbum(Album album) {
        try {
            st.executeUpdate(String.format("UPDATE album SET album_ad='%s', tarih='%s', tur='%s' WHERE album_id=%d",
                    album.getAlbumAd(), album.getTarih(), album.getTur(), album.getAlbumId()));
            delete("album_sarki_sanatci", "album_id", album.getAlbumId());
            addMultiAlbumSarkiSanatci(album, album.getAlbumId());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateKullanici(Kullanici kullanici) {
        try {
            st.executeUpdate(String.format("UPDATE kullanici SET kullanici_ad='%s', email='%s', sifre='%s', abonelik='%s'," +
                            "ulke='%s', odeme=%d WHERE kullanici_id=%d", kullanici.getKullaniciAd(), kullanici.getEmail(),
                    kullanici.getSifre(), kullanici.getAbonelik(), kullanici.getUlke(), kullanici.getOdeme(), kullanici.getKullaniciID()));
            delete("kullanici_calma_list", "kullanici_id", kullanici.getKullaniciID());
            delete("kullanici_takip_edilen", "kullanici_id", kullanici.getKullaniciID());
            delete("kullanici_takipci", "kullanici_id", kullanici.getKullaniciID());
            addKullaniciMergeList(kullanici, kullanici.getKullaniciID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDinlenmeSayisi(int id) {
        try {
            st.executeUpdate("UPDATE sarki SET dinlenme_sayisi=dinlenme_sayisi+1 WHERE sarki_id=" + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateOdeme(int id) {
        try {
            st.executeUpdate("UPDATE kullanici SET odeme=1 WHERE kullanici_id=" + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private ArrayList<Integer> getIdList(String table, String idColumn, String column, int id) throws SQLException {
        ArrayList<Integer> list = new ArrayList<>();
        if (id == 0)
            rs = st.executeQuery(String.format("select  %s from  %s ORDER By %s", column, table, idColumn));
        else
            rs = st.executeQuery(String.format("select  %s from  %s where %s=%d", column, table, idColumn, id));
        while (rs.next()) {
            list.add(rs.getInt(column));
        }
        return list;
    }

    private boolean kayitKontrol(String table, String column, String isim) {
        try {
            rs = st.executeQuery(String.format("select %s from %s", column, table));
            while (rs.next()) {
                if (rs.getString(column).equals(isim)) {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private int getId(String table) throws SQLException {
        int id = 0;
        String idName = table + "_id";
        rs = st.executeQuery(String.format("select %s from %s", idName, table));
        while (rs.next()) {
            id = rs.getInt(idName);
        }
        return id;
    }

    private void addSarkiSanatci(Sarki sarki, int sarkiId) throws SQLException {
        for (int sanatciId : sarki.sanatciIdList)
            st.execute(String.format("insert  into sarki_sanatci(sarki_id, sanatci_id) values(%d,%d)", sarkiId, sanatciId));
    }

    private void addMultiAlbumSarkiSanatci(Album album, int albumId) throws SQLException {
        for (int sarki_id : album.sarkiIdList) {
            for (int sanatciId : album.sanatciIdList) {
                st.execute(String.format("insert into album_sarki_sanatci(album_id, sarki_id, sanatci_id)" +
                        " values(%d, %d, %d)", albumId, sarki_id, sanatciId));
            }
        }
    }

    private void addAlbumSarkiSanatci(int albumId, int sarkiId, ArrayList<Integer> sanatciID) throws SQLException {
        for (int i : sanatciID)
            st.execute(String.format("insert into album_sarki_sanatci(album_id, sarki_id, sanatci_id)" +
                    " values(%d, %d, %d)", albumId, sarkiId, i));
    }

    private void addKullaniciMergeList(Kullanici kullanici, int kullaniciId) throws SQLException {
        if (kullanici.calmaList != null) {
            for (int calmaList : kullanici.calmaList) {
                st.execute(String.format("insert into kullanici_calma_list(kullanici_id, calma_list_id) values(%d, %d)", kullaniciId, calmaList));
            }
        }
        if (kullanici.takipEdilenList != null) {
            for (int takipEdilen : kullanici.takipEdilenList) {
                st.execute(String.format("insert into kullanici_takip_edilen(kullanici_id, kullanici_takip_edilen_id) values(%d, %d)", kullaniciId, takipEdilen));
            }
        }
        if (kullanici.takipciList != null) {
            for (int takipci : kullanici.takipciList) {
                st.execute(String.format("insert into kullanici_takipci(kullanici_id, kullanici_takipci_id) values(%d, %d)", kullaniciId, takipci));
            }
        }
    }

    private void addCalmaListSarkilar(int calmaListId, CalmaList calmaList) throws SQLException {
        for (int sarkiId : calmaList.getSarkiIdList()) {
            st.execute(String.format("insert into calma_list_sarkilar(calma_list_id, sarki_id) values(%d, %d)", calmaListId, sarkiId));
        }
    }

    private void delete(String table, String column, int id) {
        try {
            st.executeUpdate("delete from " + table + " where " + column + "=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}