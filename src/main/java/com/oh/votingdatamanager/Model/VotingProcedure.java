package com.oh.votingdatamanager.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class VotingProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String szavazasId;

    private LocalDateTime idopont;

    private String targy;

    private VotingType tipus;

    @ManyToOne
    @JoinColumn(name = "elnok_id")
    private Delegate elnok;

    @OneToMany
    private Set<Vote> szavazatok;

    public VotingProcedure(LocalDateTime idopont, String targy, VotingType tipus, Delegate elnok, Set<Vote> szavazatok) {
        this.idopont = idopont;
        this.targy = targy;
        this.tipus = tipus;
        this.elnok = elnok;
        this.szavazatok = szavazatok;
    }

    public VotingProcedure() {

    }

    public String generateUniqueId() {
        return "OJ" + id.toString();
    }

    public String getSzavazasId() {
        return szavazasId;
    }

    public void setSzavazasId(String szavazasId) {
        this.szavazasId = szavazasId;
    }
}
