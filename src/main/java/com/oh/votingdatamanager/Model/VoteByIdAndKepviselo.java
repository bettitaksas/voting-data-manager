package com.oh.votingdatamanager.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class VoteByIdAndKepviselo {
    private String szavazat;

    public String getSzavazat() {
        return szavazat;
    }

    public void setSzavazat(String szavazat) {
        this.szavazat = szavazat;
    }
}
