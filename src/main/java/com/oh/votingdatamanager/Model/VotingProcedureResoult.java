package com.oh.votingdatamanager.Model;

import java.time.LocalDateTime;
import java.util.HashSet;

public class VotingProcedureResoult {
    private LocalDateTime idopont;
    private String targy;
    private VotingType tipus;
    private Delegate elnok;
    private HashSet<Vote> szavazatok;

    public VotingProcedureResoult(LocalDateTime idopont, String targy, VotingType tipus, Delegate elnok, HashSet<Vote> szavazatok) {
        this.idopont = idopont;
        this.targy = targy;
        this.tipus = tipus;
        this.elnok = elnok;
        this.szavazatok = szavazatok;
    }
}
