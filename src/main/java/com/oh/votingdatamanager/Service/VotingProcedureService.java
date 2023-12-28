package com.oh.votingdatamanager.Service;

import com.oh.votingdatamanager.DTO.CalculatedVotingResoultDTO;
import com.oh.votingdatamanager.DTO.PostNewVotingResoultDTO;
import com.oh.votingdatamanager.Model.*;
import com.oh.votingdatamanager.Repository.VotingProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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

    public static double formatDouble(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedValue = df.format(value).replace(',', '.');
        return Double.parseDouble(formattedValue);
    }

    public PostNewVotingResoultDTO saveVotingProcedure(VotingProcedure votingProcedure) {
        PostNewVotingResoultDTO votingResoult = new PostNewVotingResoultDTO();

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

    public CalculatedVotingResoultDTO calculateVotingResult(String szavazasId) {
        VotingProcedure votingProcedure = votingProcedureRepository.findBySzavazasId(szavazasId)
                .orElse(null);

        int kepviselokSzama = votingProcedure.getSzavazatok().size();
        int igenekSzama = (int) votingProcedure.getSzavazatok().stream().filter(vote -> vote.getSzavazat() == VoteOption.i).count();
        int nemekSzama = (int) votingProcedure.getSzavazatok().stream().filter(vote -> vote.getSzavazat() == VoteOption.n).count();
        int tartozkodasokSzama = (int) votingProcedure.getSzavazatok().stream().filter(vote -> vote.getSzavazat() == VoteOption.t).count();

        CalculatedVotingResoultDTO calculatedResoult = new CalculatedVotingResoultDTO();
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

    public double calculateAverageParticipationResoultDTO(String kepviselo, LocalDate startDate, LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Set<VotingProcedure> votingProcedures = votingProcedureRepository.findAllByIdopontBetween(startDateTime, endDateTime);

        if (votingProcedures.isEmpty()) {
            return 0.00;
        }

        int kepviseloCounter = 0;

        for (VotingProcedure votingProcedure : votingProcedures) {
            for (Vote vote : votingProcedure.getSzavazatok()) {
                if (vote.getKepviselo().equals(kepviselo)) {
                    kepviseloCounter++;
                }
            }
        }

        double dKepviseloCounter = kepviseloCounter;
        double dszavazasokSzama = votingProcedures.size();

        System.out.println(dKepviseloCounter / dszavazasokSzama);
        double resoult = formatDouble(dKepviseloCounter / dszavazasokSzama);
        System.out.println(resoult);
        return resoult;

    }

}
