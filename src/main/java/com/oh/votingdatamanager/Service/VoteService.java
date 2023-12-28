package com.oh.votingdatamanager.Service;

import com.oh.votingdatamanager.DTO.VoteByIdAndKepviseloDTO;
import com.oh.votingdatamanager.Model.Vote;
import com.oh.votingdatamanager.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    public VoteByIdAndKepviseloDTO findSzavazatByIdAndKepviselo(String szavazasId, String kepviselo) throws ChangeSetPersister.NotFoundException {

        Optional<Vote> optionalVote = voteRepository.findByVotingProcedure_SzavazasIdAndKepviselo(szavazasId, kepviselo);
        if (optionalVote.isPresent()){
            String szavazat = optionalVote.get().getSzavazat().toString();
            VoteByIdAndKepviseloDTO voteByIdAndKepviselo = new VoteByIdAndKepviseloDTO();
            voteByIdAndKepviselo.setSzavazat(szavazat);
            return voteByIdAndKepviselo;
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }

    }


}
