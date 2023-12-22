package com.oh.votingdatamanager.Service;

import com.oh.votingdatamanager.Model.VotingProcedure;
import com.oh.votingdatamanager.Repository.VotingProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotingProcedureValidator {

    private final VotingProcedureRepository votingProcedureRepository;

    @Autowired
    public VotingProcedureValidator(VotingProcedureRepository votingProcedureRepository) {
        this.votingProcedureRepository = votingProcedureRepository;
    }

    public boolean elnokSzavazott(VotingProcedure votingProcedure) {
        return votingProcedure.elnokSzavazatainakSzama() > 0;
    }

    public boolean kepviselokEgyszerSzavaztak(VotingProcedure votingProcedure) {
        return votingProcedure.kepviselokEgyszerSzavaztak();
    }

    public boolean szabadIdopont(VotingProcedure votingProcedure) {
        Optional<VotingProcedure> existingVoting = votingProcedureRepository.findByIdopont(votingProcedure.getIdopont());
        return existingVoting.isEmpty();
    }
}
