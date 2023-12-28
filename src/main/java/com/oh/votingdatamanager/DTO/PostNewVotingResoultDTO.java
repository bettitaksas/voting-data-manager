package com.oh.votingdatamanager.DTO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostNewVotingResoultDTO {
    private String szavazasId;
    private String error;

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
