package com.company;

import com.company.service.DbHelper;
import com.company.view.GirisSayfa;

public class Main {
    public static void main(String[] args) {
        new DbHelper();
        new GirisSayfa();
    }
}
