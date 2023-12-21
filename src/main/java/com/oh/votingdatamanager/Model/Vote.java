package com.oh.votingdatamanager.Model;

import jakarta.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kepviselo;

    private String szavazat;

    @ManyToOne
    @JoinColumn(name = "voting_procedure_id")
    private VotingProcedure votingProcedure;

    public Vote(String kepviselo, String szavazat) {
        this.kepviselo = kepviselo;
        this.szavazat = szavazat;
    }

    public Vote() {

    }
}
