package com.oh.votingdatamanager.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
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

    public Long getId() {
        return id;
    }

    public VoteOption getSzavazat() {
        return szavazat;
    }

    public VotingProcedure getVotingProcedure() {
        return votingProcedure;
    }

    public void setKepviselo(String kepviselo) {
        this.kepviselo = kepviselo;
    }

    public void setSzavazat(VoteOption szavazat) {
        this.szavazat = szavazat;
    }

    public void setVotingProcedure(VotingProcedure votingProcedure) {
        this.votingProcedure = votingProcedure;
        if (votingProcedure != null) {
            votingProcedure.getSzavazatok().add(this);
        }
    }
}
