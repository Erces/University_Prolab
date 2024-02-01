package com.company.model;

import com.company.service.DbHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Album {
    public int albumId;
    public String albumAd, tarih, tur;
    public ArrayList<Integer> sarkiIdList;
    public ArrayList<Integer> sanatciIdList;

    public Album(int albumId, String albumAd, String tarih, String tur, ArrayList<Integer> sarkiIdList, ArrayList<Integer> sanatciIdList) {
        this.albumId = albumId;
        this.albumAd = albumAd;
        this.tarih = tarih;
        this.tur = tur;
        this.sarkiIdList = sarkiIdList;
        this.sanatciIdList = sanatciIdList;
    }

    public Album(String albumIsim, String tarih, String tur, ArrayList<Integer> sarkiId, ArrayList<Integer> sanatciId) {
        this.albumAd = albumIsim;
        this.tarih = tarih;
        this.tur = tur;
        this.sarkiIdList = sarkiId;
        this.sanatciIdList = sanatciId;
    }

    public Album() {
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getAlbumAd() {
        return albumAd;
    }

    public void setAlbumAd(String albumAd) {
        this.albumAd = albumAd;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getTur() {
        return tur;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public ArrayList<Integer> getSarkiIdList() {
        return sarkiIdList;
    }

    public void setSarkiIdList(ArrayList<Integer> sarkiIdList) {
        this.sarkiIdList = sarkiIdList;
    }

    public ArrayList<Integer> getSanatciIdList() {
        return sanatciIdList;
    }

    public void setSanatciIdList(ArrayList<Integer> sanatciIdList) {
        this.sanatciIdList = sanatciIdList;
    }

    public Album getAlbumModel(ResultSet rs, ArrayList<Integer> sarkiIdList, ArrayList<Integer> sanatciIdList) throws SQLException {
        return new Album(rs.getInt("album_id"), rs.getString("album_ad"),
                rs.getString("tarih"), rs.getString("tur"), sarkiIdList, sanatciIdList);
    }

    public boolean emptyControl() {
        return !albumAd.isEmpty() && !tarih.isEmpty() && !tur.isEmpty() && !sanatciIdList.isEmpty();
    }

    @Override
    public String toString() {
        DbHelper dbHelper = new DbHelper();
        StringBuilder sarki = new StringBuilder();
        StringBuilder sanatci = new StringBuilder();
        for (int id : sarkiIdList) {
            if (!sarki.toString().contains(dbHelper.getSarki(id).getSarkiAd()))
                sarki.append(dbHelper.getSarki(id).getSarkiAd()).append(", ");

        }
        for (int id : sanatciIdList) {
            if (!sanatci.toString().contains(dbHelper.getSanatci(id).getSanatciAd()))
                sanatci.append(dbHelper.getSanatci(id).getSanatciAd()).append(", ");
        }
        return "Id= " + albumId +
                ", Isim= " + albumAd +
                ", Tarih= " + tarih +
                ", Tur= " + tur + '\'' +
                ", Sarki Isim= { " + sarki + " }" +
                ", sanatciIdList= { " + sanatci + " }";
    }
}
