package com.oh.votingdatamanager.Repository;

import com.oh.votingdatamanager.Model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByVotingProcedure_SzavazasIdAndKepviselo(String szavazasId, String kepviselo);
}
