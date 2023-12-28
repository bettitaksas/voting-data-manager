package com.oh.votingdatamanager.Repository;

import com.oh.votingdatamanager.Model.VotingProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface VotingProcedureRepository extends JpaRepository<VotingProcedure, Long> {
    Optional<VotingProcedure> findByIdopont(LocalDateTime idopont);

    Optional<VotingProcedure> findBySzavazasId(String szavazasId);

    //Set<VotingProcedure> findAllByIdopontBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT vp FROM VotingProcedure vp LEFT JOIN FETCH vp.szavazatok WHERE vp.idopont BETWEEN :startOfDay AND :endOfDay")
    Optional<Set<VotingProcedure>> findAllByIdopontBetween(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);


}
