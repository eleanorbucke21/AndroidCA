package com.example.androidca.bag;

import org.json.JSONObject;

import java.util.ArrayList;

public class BagManager {
    private static BagManager instance;
    private ArrayList<JSONObject> bagItems;

    private BagManager() {
        bagItems = new ArrayList<>();
    }

    public static synchronized BagManager getInstance() {
        if (instance == null) {
            instance = new BagManager();
        }
        return instance;
    }

    public void addToBag(JSONObject product) {
        bagItems.add(product);
    }

    public ArrayList<JSONObject> getBagItems() {
        return bagItems;
    }

    public void clearBag() {
        bagItems.clear();
    }
}
