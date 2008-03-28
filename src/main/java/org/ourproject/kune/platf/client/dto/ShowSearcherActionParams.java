package org.ourproject.kune.platf.client.dto;

public class ShowSearcherActionParams {

    private final String termToSearch;
    private final int typeOfSearch;

    public ShowSearcherActionParams(final String termToSearch, final int typeOfSearch) {
        this.termToSearch = termToSearch;
        this.typeOfSearch = typeOfSearch;
    }

    public int getTypeOfSearch() {
        return typeOfSearch;
    }

    public String getTermToSearch() {
        return termToSearch;
    }

}
