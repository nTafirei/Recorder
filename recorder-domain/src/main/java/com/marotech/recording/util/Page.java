package com.marotech.recording.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Page {
    private int currPage = 1;         // current page number, 1-based
    private int itemsPerPage = 10;    // items per page
    private long totalItemsFound = 0; // total items available
    private long numItemsShowing;

    public int getFirstResultIndex() {
        if(currPage == 0){
            currPage = 1;
        }
        if(itemsPerPage == 0){
            itemsPerPage = 1;
        }
        return (currPage - 1) * itemsPerPage;
    }

    public long getTotalPages() {
        if(itemsPerPage == 0){
            itemsPerPage = 1;
        }
        return (totalItemsFound + itemsPerPage - 1) / itemsPerPage;
    }

    public List<Integer> getPageNumbers() {
        List<Integer> list = new ArrayList<>();
        long totalPages = getTotalPages();
        for (int i = 1; i <= totalPages; i++) {
            list.add(i);
        }
        return list;
    }
}

