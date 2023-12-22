package com.oh.votingdatamanager.Model;

public class Resoult {
    private String szavazasId;
    private String error;

    public Resoult() {
    }

    public Resoult(String szavazasId, String error) {
        this.szavazasId = szavazasId;
        this.error = error;
    }

    public String getSzavazasId() {
        return szavazasId;
    }

    public void setSzavazasId(String szavazasId) {
        this.szavazasId = szavazasId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
