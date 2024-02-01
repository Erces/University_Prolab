package com.company.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Sanatci {
    public int sanatciId;
    public String sanatciAd, ulke;

    public Sanatci(String sanatciAd, String ulke) {
        this.sanatciAd = sanatciAd;
        this.ulke = ulke;
    }

    public Sanatci(int sanatciId, String sanatciAd, String ulke) {
        this.sanatciId = sanatciId;
        this.sanatciAd = sanatciAd;
        this.ulke = ulke;
    }

    public Sanatci() {
    }

    public int getSanatciId() {
        return sanatciId;
    }

    public String getSanatciAd() {
        return sanatciAd;
    }

    public void setSanatciId(int sanatciId) {
        this.sanatciId = sanatciId;
    }

    public void setSanatciAd(String sanatciAd) {
        this.sanatciAd = sanatciAd;
    }

    public String getUlke() {
        return ulke;
    }

    public void setUlke(String ulke) {
        this.ulke = ulke;
    }

    public Sanatci getSanatciModel(ResultSet rs) throws SQLException {
        return new Sanatci(rs.getInt("sanatci_id"), rs.getString("sanatci_ad"), rs.getString("ulke"));
    }

    public boolean emptyControl() {
        return !sanatciAd.isEmpty() && !ulke.isEmpty();
    }

    @Override
    public String toString() {
        return "Id= " + sanatciId +
                ", Isim= " + sanatciAd +
                ", Ulke= " + ulke;
    }
}
