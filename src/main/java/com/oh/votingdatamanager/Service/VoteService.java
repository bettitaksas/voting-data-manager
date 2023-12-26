package com.oh.votingdatamanager.Service;

import com.oh.votingdatamanager.Model.Vote;
import com.oh.votingdatamanager.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public void saveVote(Vote vote) {
        voteRepository.save(vote);
    }

    public String findSzavazatByIdAndKepviselo(String szavazasId, String kepviseloId) {
        try {
            Optional<Vote> voteOptional = voteRepository.findByVotingProcedure_SzavazasIdAndKepviselo(szavazasId, kepviseloId);
            return voteOptional.get().getSzavazat().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
