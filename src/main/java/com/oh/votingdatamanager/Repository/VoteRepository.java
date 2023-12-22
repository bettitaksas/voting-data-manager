package com.oh.votingdatamanager.Repository;

import com.oh.votingdatamanager.Model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
