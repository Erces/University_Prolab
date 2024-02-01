package com.company.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CalmaList {
    public int calmaListId;
    public String calmaListAd;
    public ArrayList<Integer> sarkiIdList;

    public CalmaList(int calmaListId, String calmaListAd, ArrayList<Integer> sarkiIdList) {
        this.calmaListId = calmaListId;
        this.calmaListAd = calmaListAd;
        this.sarkiIdList = sarkiIdList;
    }

    public CalmaList(String calmaListAd, ArrayList<Integer> sarkiId) {
        this.calmaListAd = calmaListAd;
        this.sarkiIdList = sarkiId;
    }

    public CalmaList() {
    }

    public int getCalmaListId() {
        return calmaListId;
    }

    public String getCalmaListAd() {
        return calmaListAd;
    }

    public void setCalmaListAd(String calmaListAd) {
        this.calmaListAd = calmaListAd;
    }

    public ArrayList<Integer> getSarkiIdList() {
        return sarkiIdList;
    }

    public void setSarkiIdList(ArrayList<Integer> sarkiIdList) {
        this.sarkiIdList = sarkiIdList;
    }

    public CalmaList getCalmaListModel(ResultSet rs, ArrayList<Integer> sarkiIdList) throws SQLException {
        return new CalmaList(rs.getInt("calma_list_id"), rs.getString("calma_list_ad"), sarkiIdList);
    }
}
