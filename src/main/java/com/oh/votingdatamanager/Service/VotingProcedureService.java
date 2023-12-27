package com.oh.votingdatamanager.Service;

import com.oh.votingdatamanager.Model.*;
import com.oh.votingdatamanager.Repository.VotingProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Service
public class VotingProcedureService {

    private final VotingProcedureRepository votingProcedureRepository;
    private final VotingProcedureValidator votingProcedureValidator;


    @Autowired
    public VotingProcedureService(VotingProcedureRepository votingProcedureRepository, VotingProcedureValidator votingProcedureValidator) {
        this.votingProcedureRepository = votingProcedureRepository;
        this.votingProcedureValidator = votingProcedureValidator;
    }

    public Resoult saveVotingProcedure(VotingProcedure votingProcedure) {
        Resoult votingResoult = new Resoult();

        if (!votingProcedureValidator.elnokSzavazott(votingProcedure)) {
            votingResoult.setError("Az elnöknek kötelező szavaznia.");
        } else if (!votingProcedureValidator.kepviselokEgyszerSzavaztak(votingProcedure)) {
            votingResoult.setError("Minden képviselő maximum egyszer szavazhat.");
        } else if (!votingProcedureValidator.szabadIdopont(votingProcedure)) {
            votingResoult.setError("A megadott időpont már foglalt.");
        } else {
            votingProcedure.setSzavazasId(votingProcedure.generateUniqueId());

            for (Vote vote : votingProcedure.getSzavazatok()) {
                votingProcedure.addVote(vote);
            }

            votingProcedureRepository.save(votingProcedure);
            votingResoult.setSzavazasId(votingProcedure.getSzavazasId());
        }
        return votingResoult;
    }

    public CalculatedResoult calculateVotingResult(String szavazasId) {
        VotingProcedure votingProcedure = votingProcedureRepository.findBySzavazasId(szavazasId)
                .orElse(null);

        int kepviselokSzama = votingProcedure.getSzavazatok().size();
        int igenekSzama = (int) votingProcedure.getSzavazatok().stream().filter(vote -> vote.getSzavazat() == VoteOption.i).count();
        int nemekSzama = (int) votingProcedure.getSzavazatok().stream().filter(vote -> vote.getSzavazat() == VoteOption.n).count();
        int tartozkodasokSzama = (int) votingProcedure.getSzavazatok().stream().filter(vote -> vote.getSzavazat() == VoteOption.t).count();

        CalculatedResoult calculatedResoult = new CalculatedResoult();
        calculatedResoult.setEredmeny(calculateResoult(kepviselokSzama, igenekSzama));
        calculatedResoult.setKepviselokSzama(kepviselokSzama);
        calculatedResoult.setIgenekSzama(igenekSzama);
        calculatedResoult.setNemekSzama(nemekSzama);
        calculatedResoult.setTartozkodasokSzama(tartozkodasokSzama);

        return calculatedResoult;
    }

    private String calculateResoult(int kepviselokSzama, int igenekSzama) {
        if (kepviselokSzama == 0) {
            return "U";
        } else if (igenekSzama > kepviselokSzama / 2) {
            return "F";
        } else {
            return "U";
        }
    }

    public Set<VotingProcedure> getVotingProceduresByDay(LocalDate day) {

        LocalDateTime startOfDay = day.atStartOfDay();
        LocalDateTime endOfDay = day.atTime(LocalTime.MAX);

        return votingProcedureRepository.findAllByIdopontBetween(startOfDay, endOfDay);
    }
}
