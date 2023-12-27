package com.oh.votingdatamanager.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotingProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String szavazasId;

    private LocalDateTime idopont;

    private String targy;

    @Enumerated(EnumType.STRING)
    private VotingType tipus;

    private String elnok;

    @JsonBackReference
    @OneToMany
    private Set<Vote> szavazatok;

    public String generateUniqueId() {
        szavazasId = UUID.randomUUID().toString();
        return szavazasId;
    }

    public Long getId() {
        return id;
    }

    public String getTargy() {
        return targy;
    }

    public VotingType getTipus() {
        return tipus;
    }

    public String getElnok() {
        return elnok;
    }

    public String getSzavazasId() {
        return szavazasId;
    }

    public void setSzavazasId(String szavazasId) {
        this.szavazasId = szavazasId;
    }

    public Set<Vote> getSzavazatok() {
        return szavazatok;
    }

    public LocalDateTime getIdopont() {
        return idopont;
    }

    public long elnokSzavazatainakSzama() {
        return szavazatok
                .stream()
                .filter(szavazat -> szavazat.getKepviselo().equals(elnok)).count();
    }

    public boolean kepviselokEgyszerSzavaztak() {
        Set<String> szavazoKepviselok = new HashSet<>();
        return szavazatok.stream()
                .allMatch(szavazat -> szavazoKepviselok.add(szavazat.getKepviselo()));
    }

    public void addVote(Vote vote) {
        this.szavazatok.add(vote);
        vote.setVotingProcedure(this);
    }
}
