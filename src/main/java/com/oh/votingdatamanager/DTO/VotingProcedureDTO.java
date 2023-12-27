package com.oh.votingdatamanager.DTO;

import com.oh.votingdatamanager.Model.VotingType;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class VotingProcedureDTO {
    private LocalDateTime idopont;
    private String targy;
    private VotingType tipus;
    private String elnok;
    private String eredmeny;
    private int kepviselokSzama;
    private Set<VoteDTO> szavazatok;

    public VotingProcedureDTO() {
        this.szavazatok = new HashSet<>();
    }

    public LocalDateTime getIdopont() {
        return idopont;
    }

    public void setIdopont(LocalDateTime idopont) {
        this.idopont = idopont;
    }

    public String getTargy() {
        return targy;
    }

    public void setTargy(String targy) {
        this.targy = targy;
    }

    public VotingType getTipus() {
        return tipus;
    }

    public void setTipus(VotingType tipus) {
        this.tipus = tipus;
    }

    public String getElnok() {
        return elnok;
    }

    public void setElnok(String elnok) {
        this.elnok = elnok;
    }

    public String getEredmeny() {
        return eredmeny;
    }

    public void setEredmeny(String eredmeny) {
        this.eredmeny = eredmeny;
    }

    public int getKepviselokSzama() {
        return kepviselokSzama;
    }

    public void setKepviselokSzama(int kepviselokSzama) {
        this.kepviselokSzama = kepviselokSzama;
    }

    public Set<VoteDTO> getSzavazatok() {
        return szavazatok;
    }

    public void setSzavazatok(Set<VoteDTO> szavazatok) {
        this.szavazatok = szavazatok;
    }
}
