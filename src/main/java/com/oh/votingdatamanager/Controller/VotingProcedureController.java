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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/szavazat")
    public ResponseEntity<VoteByIdAndKepviseloDTO> getVoteBySzavazasIdAndKepviselo(
            @RequestParam(name = "szavazas") String szavazasId,
            @RequestParam(name = "kepviselo") String kepviselo) {

        try {
            VoteByIdAndKepviseloDTO voteByIdAndKepviselo = voteService.findSzavazatByIdAndKepviselo(szavazasId, kepviselo);
            return new ResponseEntity<>(voteByIdAndKepviselo, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/eredmeny/{szavazasId}")
    public ResponseEntity<CalculatedVotingResoultDTO> getVotingResult(@PathVariable String szavazasId) {
        try {
            CalculatedVotingResoultDTO calculatedResoult = votingProcedureService.calculateVotingResult(szavazasId);
            return new ResponseEntity<>(calculatedResoult, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/napi-szavazasok/{day}")
    public ResponseEntity<VotingsByDayDTO> getVotingProceduresByDay(@PathVariable LocalDate day) {
        try {
            VotingsByDayDTO votingsByDay = votingProcedureService.getVotingProceduresByDay(day);
            return new ResponseEntity<>(votingsByDay, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            VotingsByDayDTO votingsByDay = new VotingsByDayDTO();
            return new ResponseEntity<>(votingsByDay, HttpStatus.OK);
        }
    }

    @GetMapping("/kepviselo-reszvetel-atlag")
    public ResponseEntity<AverageParticipationResoultDTO> getAverageParticipation(
            @RequestParam String kepviselo,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        AverageParticipationResoultDTO resoult = votingProcedureService.calculateAverageParticipationResoult(kepviselo, startDate, endDate);
        return new ResponseEntity<>(resoult, HttpStatus.OK);
    }
}
