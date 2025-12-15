package com.marotech.vending.util;


public class SearchParams extends FetchParams {
    private String searchInput;

    public SearchParams(int currRow, int maxResults, String searchInput) {
        super(currRow, maxResults);
        this.searchInput = searchInput;
    }

    public String getSearchInput() {
        return searchInput;
    }

    @Override
    public String toString() {
        return "SearchParams [searchInput=" + searchInput + ", toString()="
                + super.toString() + "]";
    }
}
