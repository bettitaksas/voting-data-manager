package com.oh.votingdatamanager.Controller;

import com.oh.votingdatamanager.Model.*;
import com.oh.votingdatamanager.Service.VoteService;
import com.oh.votingdatamanager.Service.VotingProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Set;

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
    public ResponseEntity<Resoult> saveVotingResoult(@RequestBody VotingProcedure votingProcedure) {
        try {

            votingProcedure.getSzavazatok().forEach(voteService::saveVote);

            Resoult resoult = votingProcedureService.saveVotingProcedure(votingProcedure);
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
        VoteByIdAndKepviselo voteByIdAndKepviselo = new VoteByIdAndKepviselo();
        voteByIdAndKepviselo.setSzavazat(szavazat);
        return new ResponseEntity<>(voteByIdAndKepviselo, HttpStatus.OK);

    }

    @GetMapping("/eredmeny/{szavazasId}")
    public ResponseEntity<Object> getVotingResult(@PathVariable String szavazasId) {
        CalculatedResoult calculatedResoult = votingProcedureService.calculateVotingResult(szavazasId);
        return new ResponseEntity<>(calculatedResoult, HttpStatus.OK);
    }

    @GetMapping("/napi-szavazasok/{day}")
    public ResponseEntity<VotingsByDay> getVotingProceduresByDay(@PathVariable LocalDate day) {

        System.out.println(day);

        System.out.println(votingProcedureService.getVotingProceduresByDay(day));

        Set<VotingProcedure> szavazatok = votingProcedureService.getVotingProceduresByDay(day);
        VotingsByDay votingsByDay = new VotingsByDay();
        votingsByDay.setSzavazatok(szavazatok);

        return new ResponseEntity<>(votingsByDay, HttpStatus.OK);
    }
}
