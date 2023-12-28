package com.oh.votingdatamanager.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kepviselo;
    @Enumerated(EnumType.STRING)
    private VoteOption szavazat;
    @ManyToOne
    public VotingProcedure votingProcedure;

    public String getKepviselo() {
        return kepviselo;
    }

    public void setKepviselo(String kepviselo) {
        this.kepviselo = kepviselo;
    }

    public Long getId() {
        return id;
    }

    public VoteOption getSzavazat() {
        return szavazat;
    }

    public void setSzavazat(VoteOption szavazat) {
        this.szavazat = szavazat;
    }

    public VotingProcedure getVotingProcedure() {
        return votingProcedure;
    }

    public void setVotingProcedure(VotingProcedure votingProcedure) {
        this.votingProcedure = votingProcedure;
        if (votingProcedure != null) {
            votingProcedure.getSzavazatok().add(this);
        }
    }
}
