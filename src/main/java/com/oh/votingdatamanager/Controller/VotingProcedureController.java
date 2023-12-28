package com.oh.votingdatamanager.Controller;

import com.oh.votingdatamanager.DTO.*;
import com.oh.votingdatamanager.Model.VotingProcedure;
import com.oh.votingdatamanager.Service.VoteService;
import com.oh.votingdatamanager.Service.VotingProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/szavazasok")
public class VotingProcedureController {

    private final VoteService voteService;
    private final VotingProcedureService votingProcedureService;

    @Autowired
    public VotingProcedureController(VoteService voteService, VotingProcedureService votingProcedureService) {
        this.voteService = voteService;
        this.votingProcedureService = votingProcedureService;
    }

    @PostMapping(value = "/szavazas", produces = "application/json")
    @ResponseBody
    public ResponseEntity<PostNewVotingResoultDTO> saveVotingResoult(@RequestBody VotingProcedure votingProcedure) {
        try {

            votingProcedure.getSzavazatok().forEach(voteService::saveVote);

            PostNewVotingResoultDTO resoult = votingProcedureService.saveVotingProcedure(votingProcedure);
            return new ResponseEntity<>(resoult, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/szavazat")
    public ResponseEntity<Object> getVoteBySzavazasIdAndKepviselo(
            @RequestParam(name = "szavazas") String szavazasId,
            @RequestParam(name = "kepviselo") String kepviselo) {

        String szavazat = voteService.findSzavazatByIdAndKepviselo(szavazasId, kepviselo);
        VoteByIdAndKepviseloDTO voteByIdAndKepviselo = new VoteByIdAndKepviseloDTO();
        voteByIdAndKepviselo.setSzavazat(szavazat);
        return new ResponseEntity<>(voteByIdAndKepviselo, HttpStatus.OK);

    }

    @GetMapping("/eredmeny/{szavazasId}")
    public ResponseEntity<Object> getVotingResult(@PathVariable String szavazasId) {
        try {
            CalculatedVotingResoultDTO calculatedResoult = votingProcedureService.calculateVotingResult(szavazasId);
            return new ResponseEntity<>(calculatedResoult, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/napi-szavazasok/{day}")
    public ResponseEntity<VotingsByDayDTO> getVotingProceduresByDay(@PathVariable LocalDate day) {

        Set<VotingProcedure> szavazatok = votingProcedureService.getVotingProceduresByDay(day);

        Set<VotingProcedureDTO> result = szavazatok.stream()
                .map(vp -> {
                    VotingProcedureDTO dto = new VotingProcedureDTO();
                    dto.setIdopont(vp.getIdopont());
                    dto.setTargy(vp.getTargy());
                    dto.setTipus(vp.getTipus());
                    dto.setElnok(vp.getElnok());
                    try {
                        dto.setEredmeny(votingProcedureService.calculateVotingResult(vp.getSzavazasId()).getEredmeny());
                    } catch (ChangeSetPersister.NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        dto.setKepviselokSzama(votingProcedureService.calculateVotingResult(vp.getSzavazasId()).getKepviselokSzama());
                    } catch (ChangeSetPersister.NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    dto.setSzavazatok(vp.getSzavazatok().stream()
                            .map(vote -> new VoteDTO(vote.getKepviselo(), vote.getSzavazat().toString()))
                            .collect(Collectors.toSet())
                    );
                    return dto;
                })
                .collect(Collectors.toSet());

        VotingsByDayDTO votingsByDay = new VotingsByDayDTO();
        votingsByDay.setSzavazatok(result);

        return new ResponseEntity<>(votingsByDay, HttpStatus.OK);
    }

    @GetMapping("/kepviselo-reszvetel-atlag")
    public ResponseEntity<AverageParticipationResoultDTO> getAverageParticipation(
            @RequestParam String kepviselo,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        double atlag = votingProcedureService.calculateAverageParticipationResoultDTO(kepviselo, startDate, endDate);

        AverageParticipationResoultDTO resoult = new AverageParticipationResoultDTO();
        resoult.setAtlag(atlag);

        return new ResponseEntity<>(resoult, HttpStatus.OK);
    }
}
