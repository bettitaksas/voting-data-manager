package com.oh.votingdatamanager.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class VotingsByDayDTO {
    private Set<VotingProcedureDTO> szavazatok;

    public Set<VotingProcedureDTO> getSzavazatok() {
        return szavazatok;
    }

    public void setSzavazatok(Set<VotingProcedureDTO> szavazatok) {
        this.szavazatok = szavazatok;
    }
}
