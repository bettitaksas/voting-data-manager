package com.oh.votingdatamanager.Repository;

import com.oh.votingdatamanager.Model.VotingProcedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VotingProcedureRepository extends JpaRepository<VotingProcedure, Long> {
    Optional<VotingProcedure> findByIdopont(LocalDateTime idopont);
    Optional<VotingProcedure> findBySzavazasId(String szavazasId);
}
