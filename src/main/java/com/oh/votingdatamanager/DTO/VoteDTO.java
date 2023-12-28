package com.oh.votingdatamanager.DTO;

public class VoteDTO {
    private String kepviselo;
    private String szavazat;

    public VoteDTO(String kepviselo, String szavazat) {
        this.kepviselo = kepviselo;
        this.szavazat = szavazat;
    }

    public String getKepviselo() {
        return kepviselo;
    }

    public void setKepviselo(String kepviselo) {
        this.kepviselo = kepviselo;
    }

    public String getSzavazat() {
        return szavazat;
    }

    public void setSzavazat(String szavazat) {
        this.szavazat = szavazat;
    }
}
