package com.oh.votingdatamanager.Service;

import com.oh.votingdatamanager.DTO.*;
import com.oh.votingdatamanager.Model.Vote;
import com.oh.votingdatamanager.Model.VoteOption;
import com.oh.votingdatamanager.Model.VotingProcedure;
import com.oh.votingdatamanager.Repository.VotingProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public CalculatedVotingResoultDTO calculateVotingResult(String szavazasId) throws ChangeSetPersister.NotFoundException {
        Optional<VotingProcedure> optionalVotingProcedure = votingProcedureRepository.findBySzavazasId(szavazasId);

        if (optionalVotingProcedure.isPresent()) {
            VotingProcedure votingProcedure = optionalVotingProcedure.get();

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
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
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

    public VotingsByDayDTO getVotingProceduresByDay(LocalDate day) throws ChangeSetPersister.NotFoundException {

        LocalDateTime startOfDay = day.atStartOfDay();
        LocalDateTime endOfDay = day.atTime(LocalTime.MAX);

        Optional<Set<VotingProcedure>> optionalVotingProcedures = votingProcedureRepository.findAllByIdopontBetween(startOfDay, endOfDay);

        if (optionalVotingProcedures.isPresent()) {
            Set<VotingProcedure> szavazatok = optionalVotingProcedures.get();

            Set<VotingProcedureDTO> result = szavazatok.stream()
                    .map(vp -> {
                        VotingProcedureDTO dto = new VotingProcedureDTO();
                        dto.setIdopont(vp.getIdopont());
                        dto.setTargy(vp.getTargy());
                        dto.setTipus(vp.getTipus());
                        dto.setElnok(vp.getElnok());
                        try {
                            dto.setEredmeny(calculateVotingResult(vp.getSzavazasId()).getEredmeny());
                        } catch (ChangeSetPersister.NotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            dto.setKepviselokSzama(calculateVotingResult(vp.getSzavazasId()).getKepviselokSzama());
                        } catch (ChangeSetPersister.NotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        dto.setSzavazatok(vp.getSzavazatok().stream()
                                .map(vote -> new VoteDTO(vote.getKepviselo(), vote.getSzavazat().toString()))
                                .collect(Collectors.toSet())
                        );
                        return dto;
                    })
                    .collect(Collectors.toSet());

            VotingsByDayDTO votingsByDay = new VotingsByDayDTO();
            votingsByDay.setSzavazatok(result);
            return votingsByDay;

        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    public AverageParticipationResoultDTO calculateAverageParticipationResoult(String kepviselo, LocalDate startDate, LocalDate endDate) {

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Optional<Set<VotingProcedure>> optionalVotingProcedures = votingProcedureRepository.findAllByIdopontBetween(startDateTime, endDateTime);

        if (optionalVotingProcedures.isEmpty() || optionalVotingProcedures.get().isEmpty()) {
            AverageParticipationResoultDTO resoult = new AverageParticipationResoultDTO();
            resoult.setAtlag(0.00);
            return resoult;
        }

        int kepviseloCounter = 0;

        for (VotingProcedure votingProcedure : optionalVotingProcedures.get()) {
            for (Vote vote : votingProcedure.getSzavazatok()) {
                if (vote.getKepviselo().equals(kepviselo)) {
                    kepviseloCounter++;
                }
            }
        }

        double dKepviseloCounter = kepviseloCounter;
        double dszavazasokSzama = optionalVotingProcedures.get().size();

        double atlag = formatDouble(dKepviseloCounter / dszavazasokSzama);

        AverageParticipationResoultDTO resoult = new AverageParticipationResoultDTO();
        resoult.setAtlag(atlag);
        return resoult;
    }
}
