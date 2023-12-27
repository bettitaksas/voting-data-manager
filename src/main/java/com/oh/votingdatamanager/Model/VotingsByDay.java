package com.oh.votingdatamanager.Model;

import com.oh.votingdatamanager.DTO.VotingProcedureDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class VotingsByDay {
    private Set<VotingProcedureDTO> szavazatok;

    public Set<VotingProcedureDTO> getSzavazatok() {
        return szavazatok;
    }

    public void setSzavazatok(Set<VotingProcedureDTO> szavazatok) {
        this.szavazatok = szavazatok;
    }
}
