package com.oh.votingdatamanager.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
public class VotingProcedure {

    public VotingProcedure(LocalDateTime idopont, String targy, String tipus, String elnok, Set<Vote> szavazatok) {
        this.idopont = idopont;
        this.targy = targy;
        this.tipus = tipus;
        this.elnok = elnok;
        this.szavazatok = szavazatok;
    }


    public VotingProcedure() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String szavazasId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public LocalDateTime idopont;

    @Column(name="TARGY", length=50, unique=false)
    private String targy;

    //@Enumerated(EnumType.STRING)
    //private VotingType tipus;
    private String tipus;

    /*
    @ManyToOne
    @JoinColumn(name = "elnok_id")
    private Delegate elnok;
    */

    private String elnok;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "votingProcedure")
    private Set<Vote> szavazatok;


    public String generateUniqueId() {
        szavazasId = UUID.randomUUID().toString();
        return szavazasId;
    }

    public String getSzavazasId() {
        return szavazasId;
    }

    public void setSzavazasId(String szavazasId) {
        this.szavazasId = szavazasId;
    }
}
