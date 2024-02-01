package com.company.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Kullanici {
    public int kullaniciID;
    public String kullaniciAd, email, sifre, abonelik, ulke;
    public int odeme;
    public ArrayList<Integer> takipEdilenList;
    public ArrayList<Integer> takipciList;
    public ArrayList<Integer> calmaList;

    public Kullanici(int kullaniciID, String kullaniciAd, String email, String sifre, String abonelik, String ulke, int odeme,
                     ArrayList<Integer> takipEdilenList, ArrayList<Integer> takipciList, ArrayList<Integer> calmaList) {
        this.kullaniciID = kullaniciID;
        this.kullaniciAd = kullaniciAd;
        this.email = email;
        this.sifre = sifre;
        this.abonelik = abonelik;
        this.ulke = ulke;
        this.odeme = odeme;
        this.takipEdilenList = takipEdilenList;
        this.takipciList = takipciList;
        this.calmaList = calmaList;
    }

    public Kullanici(String kullaniciAdi, String email, String sifre, String abonelik, String ulke, int odeme,
                     ArrayList<Integer> takipEdilenList, ArrayList<Integer> takipciList, ArrayList<Integer> calmaList) {
        this.kullaniciAd = kullaniciAdi;
        this.email = email;
        this.sifre = sifre;
        this.abonelik = abonelik;
        this.ulke = ulke;
        this.odeme = odeme;
        this.takipEdilenList = takipEdilenList;
        this.takipciList = takipciList;
        this.calmaList = calmaList;
    }

    public Kullanici() {
    }

    public int getKullaniciID() {
        return kullaniciID;
    }

    public String getKullaniciAd() {
        return kullaniciAd;
    }

    public void setKullaniciAd(String kullaniciAd) {
        this.kullaniciAd = kullaniciAd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getAbonelik() {
        return abonelik;
    }

    public void setAbonelik(String abonelik) {
        this.abonelik = abonelik;
    }

    public String getUlke() {
        return ulke;
    }

    public void setUlke(String ulke) {
        this.ulke = ulke;
    }

    public int getOdeme() {
        return odeme;
    }

    public void setOdeme(int odeme) {
        this.odeme = odeme;
    }

    public ArrayList<Integer> getTakipEdilenList() {
        return takipEdilenList;
    }

    public void setTakipEdilenList(ArrayList<Integer> takipEdilenList) {
        this.takipEdilenList = takipEdilenList;
    }

    public ArrayList<Integer> getTakipciList() {
        return takipciList;
    }

    public void setTakipciList(ArrayList<Integer> takipciList) {
        this.takipciList = takipciList;
    }

    public ArrayList<Integer> getCalmaList() {
        return calmaList;
    }

    public void setCalmaList(ArrayList<Integer> calmaList) {
        this.calmaList = calmaList;
    }

    public Kullanici getKullaniciModel(ResultSet rs, ArrayList<Integer> takipEdilenIdList, ArrayList<Integer> takipciIdList, ArrayList<Integer> calmaListIdList) throws SQLException {
        return new Kullanici(rs.getInt("kullanici_id"), rs.getString("kullanici_ad"), rs.getString("email"),
                rs.getString("sifre"), rs.getString("abonelik"), rs.getString("ulke"),
                rs.getByte("odeme"), takipEdilenIdList, takipciIdList, calmaListIdList);
    }

    public void calmaListOlustur(int id) {
        calmaList.add(id);
    }

    @Override
    public String toString() {
        return "Id= " + kullaniciID +
                ", Isim= " + kullaniciAd +
                ", Email= " + email +
                ", Sifre= " + sifre +
                ", Abonelik= " + abonelik +
                ", Ulke= " + ulke +
                ", Odeme= " + odeme +
                ", Takip Edilen Id List= " + takipEdilenList +
                ", Takipci Id List= " + takipciList +
                ", Calma List Id= " + calmaList;
    }

    public String kullaniciToString() {
        return " Id: " + kullaniciID +
                ", Isim: " + kullaniciAd +
                ", Email: " + email +
                ", Abonelik: " + abonelik +
                ", Ulke: " + ulke + " ";
    }
}
