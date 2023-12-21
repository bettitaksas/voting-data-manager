package com.oh.votingdatamanager.Service;

import com.oh.votingdatamanager.Model.Resoult;
import com.oh.votingdatamanager.Model.VotingProcedure;
import com.oh.votingdatamanager.Repository.VotingProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotingProcedureService {
    private final VotingProcedureRepository votingProcedureRepository;

    @Autowired
    public VotingProcedureService(VotingProcedureRepository votingProcedureRepository) {
        this.votingProcedureRepository = votingProcedureRepository;
    }

    public Resoult saveVotingResoult(VotingProcedure voting) {
        voting.setSzavazasId(voting.generateUniqueId());

        //ellenőrzések: időpont, képviselők csak egyszer szavazzanak

        VotingProcedure savedVoting = votingProcedureRepository.save(voting);

        Resoult votingResoult = new Resoult();
        votingResoult.setSzavazasId(savedVoting.getSzavazasId());

        return votingResoult;
    }
}
