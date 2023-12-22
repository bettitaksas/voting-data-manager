package com.oh.votingdatamanager.Service;

import com.oh.votingdatamanager.Model.Resoult;
import com.oh.votingdatamanager.Model.VotingProcedure;
import com.oh.votingdatamanager.Repository.VotingProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotingProcedureService {

    private final VotingProcedureRepository votingProcedureRepository;
    private final VotingProcedureValidator votingProcedureValidator;

    @Autowired
    public VotingProcedureService(VotingProcedureRepository votingProcedureRepository, VotingProcedureValidator votingProcedureValidator) {
        this.votingProcedureRepository = votingProcedureRepository;
        this.votingProcedureValidator = votingProcedureValidator;
    }

    public Resoult saveVotingProcedure(VotingProcedure votingProcedure) {
        Resoult votingResoult = new Resoult();

        if(!votingProcedureValidator.elnokSzavazott(votingProcedure)) {
            votingResoult.setError("Az elnöknek kötelező szavaznia.");
        } else if (!votingProcedureValidator.kepviselokEgyszerSzavaztak(votingProcedure)) {
            votingResoult.setError("Minden képviselő maximum egyszer szavazhat.");
        } else if (!votingProcedureValidator.szabadIdopont(votingProcedure)) {
            votingResoult.setError("A megadott időpont már foglalt.");
        } else {
            votingProcedure.setSzavazasId(votingProcedure.generateUniqueId());
            votingProcedureRepository.save(votingProcedure);
            votingResoult.setSzavazasId(votingProcedure.generateUniqueId());
        }
        return votingResoult;
    }
}
