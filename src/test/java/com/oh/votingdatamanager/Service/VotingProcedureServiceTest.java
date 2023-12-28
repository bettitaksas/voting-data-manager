package com.oh.votingdatamanager.Service;

import com.oh.votingdatamanager.DTO.AverageParticipationResoultDTO;
import com.oh.votingdatamanager.Model.VotingProcedure;
import com.oh.votingdatamanager.Repository.VotingProcedureRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class VotingProcedureServiceTest {

    @Test
    void testCalculateResoult() {
        VotingProcedureService service = new VotingProcedureService(null, null);
        assertEquals("U", service.calculateResoult(0, 0));
        assertEquals("F", service.calculateResoult(5, 3));
    }

    @Test
    void testCalculateAverageParticipationResoult() {
        VotingProcedureRepository repository = Mockito.mock(VotingProcedureRepository.class);
        VotingProcedureValidator validator = Mockito.mock(VotingProcedureValidator.class);

        VotingProcedureService service = new VotingProcedureService(repository, validator);

        LocalDateTime startDate = LocalDateTime.of(2023, 1, 1, 1, 1, 1, 1);
        LocalDateTime endDate = LocalDateTime.of(2023, 1, 5, 1, 1, 1, 1);

        Set<VotingProcedure> votingProcedures = new HashSet<>();

        when(repository.findAllByIdopontBetween(startDate, endDate)).thenReturn(Optional.of(votingProcedures));

        AverageParticipationResoultDTO result = service.calculateAverageParticipationResoult("Kepviselo1", startDate.toLocalDate(), endDate.toLocalDate());

        assertEquals(0.00, result.getAtlag());
    }

}
