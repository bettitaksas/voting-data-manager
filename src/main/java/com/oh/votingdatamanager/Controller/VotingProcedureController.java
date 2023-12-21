package com.oh.votingdatamanager.Controller;

import com.oh.votingdatamanager.Model.Resoult;
import com.oh.votingdatamanager.Model.VotingProcedure;
import com.oh.votingdatamanager.Service.VotingProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/szavazasok")
public class VotingProcedureController {

    private final VotingProcedureService votingProcedureService;

    @Autowired
    public VotingProcedureController(VotingProcedureService votingProcedureService) {
        this.votingProcedureService = votingProcedureService;
    }

    @PostMapping(value = "/szavazas", produces = "application/json")
    @ResponseBody
    public ResponseEntity<VotingProcedure> saveVotingResoult(@RequestBody VotingProcedure votingProcedure) {
        try {
            //final HttpHeaders httpHeaders= new HttpHeaders();
            //httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            System.out.println(votingProcedure.getClass());
            System.out.println("HELOOOOO");
            Resoult votingResoult = votingProcedureService.saveVotingResoult(votingProcedure);
            return new ResponseEntity<>(votingProcedure, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            //egyéb hibakezelés
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
