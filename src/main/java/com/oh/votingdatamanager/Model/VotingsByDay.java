package com.oh.votingdatamanager.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class VotingsByDay {
    private Set<VotingProcedure> szavazatok;

    public Set<VotingProcedure> getSzavazatok() {
        return szavazatok;
    }

    public void setSzavazatok(Set<VotingProcedure> szavazatok) {
        this.szavazatok = szavazatok;
    }
}
