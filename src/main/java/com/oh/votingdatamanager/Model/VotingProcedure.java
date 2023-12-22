package com.oh.votingdatamanager.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    public LocalDateTime idopont;

    public String targy;

    public VotingType tipus;

    public String elnok;

    @OneToMany
    public Set<Vote> szavazatok;

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
}
