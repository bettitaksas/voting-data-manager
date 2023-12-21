package com.oh.votingdatamanager.Model;

import jakarta.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delegate_id")
    private Delegate képviselő;

    private VoteOption szavazat;

    @ManyToOne
    @JoinColumn(name = "szavazás_id")
    private VotingProcedure szavazás;

    public Vote(Long id, Delegate képviselő, VoteOption szavazat) {
        this.id = id;
        this.képviselő = képviselő;
        this.szavazat = szavazat;
    }

    public Vote() {

    }
}
