package com.oh.votingdatamanager.Repository;

import com.oh.votingdatamanager.Model.VotingProcedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingProcedureRepository extends JpaRepository<VotingProcedure, Long> {
}
