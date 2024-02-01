package com.company.service;

import com.company.model.*;

import java.util.ArrayList;

interface IDbHelper {
    void addSanatci(Sanatci sanatci);

    void addSarki(Sarki sarki);

    void addAlbum(Album album);

    void addCalmaList(CalmaList calmaList);

    void addKullanici(Kullanici kullanici);

    void addCalmaListesiSarki(int calmaListId, int sarkiId);

    void takipEt(int kullaniciId, int takipEdilenId);

    void deleteSanatci(int id);

    void deleteSarki(int id);

    void deleteAlbum(int id);

    void deleteCalmaList(int id);

    void deleteTakipEdilen(int id);

    void deleteTakipci(int id);

    void deleteCalmaListSarki(int calmaListId, int sarkiId);

    ArrayList<Sanatci> getAllSanatci();

    ArrayList<Sarki> getAllSarki();

    ArrayList<Album> getAllAlbum();

    ArrayList<Kullanici> getAllKullanici();

    ArrayList<CalmaList> getAllCalmaList();

    Sanatci getSanatci(int id);

    Sarki getSarki(int id);

    Album getAlbum(int id);

    Kullanici getKullanici(int id);

    ArrayList<Kullanici> getTakipEdilen(int id);

    ArrayList<Kullanici> getTakipci(int id);

    CalmaList getCalmaList(int id);

    ArrayList<Integer> getLast3CalmaListId();

    int getLastCalmaListId();

    ArrayList<CalmaList> getKullaniciCalmaList(int id);

    ArrayList<Sarki> getTopOnList(String type1, String type2);

    void updateSanatci(Sanatci sanatci);

    void updateSarki(Sarki sarki);

    void updateAlbum(Album album);

    void updateKullanici(Kullanici kullanici);

    void updateDinlenmeSayisi(int id);


    void updateOdeme(int id);
}