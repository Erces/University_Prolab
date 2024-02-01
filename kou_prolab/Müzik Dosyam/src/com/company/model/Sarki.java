package com.company.model;

import com.company.service.DbHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Sarki {
    public int SarkiId;
    public String sarkiAd, tarih, tur, sure, ulke;
    public int dinlenmeSayisi;
    public int albumId;
    public ArrayList<Integer> sanatciIdList;

    public Sarki(int sarkiId, String sarkiAd, String tarih, String tur, String sure, String ulke, int dinlenmeSayisi, int albumId, ArrayList<Integer> sanatciIdList) {
        SarkiId = sarkiId;
        this.sarkiAd = sarkiAd;
        this.tarih = tarih;
        this.tur = tur;
        this.sure = sure;
        this.ulke = ulke;
        this.dinlenmeSayisi = dinlenmeSayisi;
        this.albumId = albumId;
        this.sanatciIdList = sanatciIdList;
    }

    public Sarki(String sarkiAd, String tarih, int albumId, String tur, String sure, String ulke, int dinlenmeSayisi, ArrayList<Integer> sanatciIdList) {
        this.sarkiAd = sarkiAd;
        this.tarih = tarih;
        this.albumId = albumId;
        this.tur = tur;
        this.sure = sure;
        this.ulke = ulke;
        this.dinlenmeSayisi = dinlenmeSayisi;
        this.sanatciIdList = sanatciIdList;
    }

    public Sarki() {
    }

    public int getSarkiId() {
        return SarkiId;
    }

    public void setSarkiId(int sarkiId) {
        SarkiId = sarkiId;
    }

    public String getSarkiAd() {
        return sarkiAd;
    }

    public void setSarkiAd(String sarkiAd) {
        this.sarkiAd = sarkiAd;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getSure() {
        return sure;
    }

    public void setSure(String sure) {
        this.sure = sure;
    }

    public String getUlke() {
        return ulke;
    }

    public void setUlke(String ulke) {
        this.ulke = ulke;
    }

    public int getDinlenmeSayisi() {
        return dinlenmeSayisi;
    }

    public void setDinlenmeSayisi(int dinlenmeSayisi) {
        this.dinlenmeSayisi = dinlenmeSayisi;
    }

    public ArrayList<Integer> getSanatciIdList() {
        return sanatciIdList;
    }

    public void setSanatciIdList(ArrayList<Integer> sanatciIdList) {
        this.sanatciIdList = sanatciIdList;
    }

    public Sarki getSarkiModel(ResultSet rs, ArrayList<Integer> sanatciIdList) throws SQLException {
        return new Sarki(rs.getInt("sarki_id"), rs.getString("sarki_ad"), rs.getString("tarih"),
                rs.getString("tur"), rs.getString("sure"), rs.getString("ulke"),
                rs.getInt("dinlenme_sayisi"), rs.getInt("album_id"), sanatciIdList);
    }

    public boolean emptyControl() {
        return !sarkiAd.isEmpty() && !tarih.isEmpty() && !tur.isEmpty() && !sure.isEmpty() && !ulke.isEmpty()
                && dinlenmeSayisi != 0 && !sanatciIdList.isEmpty() && albumId != 0;
    }

    @Override
    public String toString() {
        DbHelper dbHelper = new DbHelper();
        StringBuilder sanatcilar = new StringBuilder();
        for (int id : sanatciIdList) {
            if (!sanatcilar.toString().contains(dbHelper.getSanatci(id).getSanatciAd()))
                sanatcilar.append(dbHelper.getSanatci(id).getSanatciAd()).append(", ");
        }
        return " Id: " + SarkiId +
                ", Isim: " + sarkiAd +
                ", Tarih: " + tarih +
                ", Tur: " + tur +
                ", Sure: " + sure +
                ", Ulke: " + ulke +
                ", Dinlenme sayisi: " + dinlenmeSayisi +
                ", Album: " + dbHelper.getAlbum(albumId).getAlbumAd() +
                ", Sanatcilar: " + sanatcilar + " ";
    }

    public String topOnToString() {
        return "Sarki: " + sarkiAd + ", Dinlenme Sayisi: " + dinlenmeSayisi;
    }
}
