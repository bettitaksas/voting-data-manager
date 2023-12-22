package com.oh.votingdatamanager.Controller;

import com.oh.votingdatamanager.Model.VotingProcedure;
import com.oh.votingdatamanager.Service.VoteService;
import com.oh.votingdatamanager.Service.VotingProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<VotingProcedure> saveVotingResoult(@RequestBody VotingProcedure votingProcedure) {
        try {

            votingProcedure.getSzavazatok().forEach(voteService::saveVote);

            votingProcedureService.saveVotingProcedure(votingProcedure);
            return new ResponseEntity<>(votingProcedure, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            //egyéb hibakezelés
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
