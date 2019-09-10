package com.javaschool.services;

import com.javaschool.exception.ResourceNotFoundException;
import com.javaschool.model.IncompatiblePromed;
import com.javaschool.model.Promed;
import com.javaschool.model.PromedDto;
import com.javaschool.repository.IncompatiblePromedRepository;
import com.javaschool.repository.PromedRepository;
import com.javaschool.telegram.TelegramBot;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class PromedServiceImpl implements PromedService {
    private static final int MIN_MEDIC_COUNT = 25;
    @Autowired
    private PromedRepository promedRepository;
    @Autowired
    private IncompatiblePromedRepository incompatiblePromedRepository;
    @Autowired
    private TelegramBot telegramBot;

    @Override
    @Transactional(readOnly = true)
    public List<Promed> getAllPromeds() {
        return promedRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PromedDto getPromedById(Integer promedId) throws ResourceNotFoundException {
        Promed promed = promedRepository.findById(promedId)
                .orElseThrow(() -> new ResourceNotFoundException("Promed not found for this id :: " + promedId));
        List<IncompatiblePromed> incompatiblePromeds = incompatiblePromedRepository.findAllByPromedOrIncompatiblePromed(promed, promed).orElse(new ArrayList<>());
        PromedDto promedDto = new PromedDto(promed, new ArrayList<>());
        if (!incompatiblePromeds.isEmpty()) {
            promedDto.setIncompatiblePromeds(incompatiblePromeds.stream()
                    .map(incompPromed -> incompPromed.getPromed().equals(promed) ?
                            incompPromed.getIncompatiblePromed() : incompPromed.getPromed())
                    .collect(Collectors.toList()));
        }
        return promedDto;
    }

    @Override
    @Transactional
    public Promed createPromed(Promed promed) {
        return promedRepository.save(promed);
    }

    @Override
    @Transactional
    public Promed updatePromed(Integer promedId, PromedDto promedDetails) throws ResourceNotFoundException {
        promedDetails.setIncompatiblePromeds(promedDetails.getIncompatiblePromeds().stream()
                .filter(promed -> promed.getName() != null).collect(Collectors.toList()));
        Promed promed = promedRepository.findById(promedId)
                .orElseThrow(() -> new ResourceNotFoundException("Promed not found for this id :: " + promedId));
        List<IncompatiblePromed> oldIncompatiblePromeds = incompatiblePromedRepository
                .findAllByPromedOrIncompatiblePromed(promed, promed)
                .orElse(new ArrayList<>());

        promed.setCount(promedDetails.getPromed().getCount());
        promed.setKind(promedDetails.getPromed().getKind());
        promed.setName(promedDetails.getPromed().getName());

        final Promed updatedPromed = promedRepository.save(promed);
        if (!oldIncompatiblePromeds.isEmpty()) {
            incompatiblePromedRepository.deleteAll(oldIncompatiblePromeds);
        }

        List<IncompatiblePromed> newIncompatiblePromeds = new ArrayList<>();
        for (Promed incompatiblePromed : promedDetails.getIncompatiblePromeds()) {
            Promed incPromed = promedRepository.findPromedByName(incompatiblePromed.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Promed not found for this id :: " + incompatiblePromed.getId()));
            newIncompatiblePromeds.add(new IncompatiblePromed(updatedPromed, incPromed));
        }

        incompatiblePromedRepository.saveAll(newIncompatiblePromeds);
        return updatedPromed;
    }

    @Override
    @Transactional
    public void deletePromed(Integer promedId) throws ResourceNotFoundException {
        Promed promed = promedRepository.findById(promedId)
                .orElseThrow(() -> new ResourceNotFoundException("Promed not found for this id :: " + promedId));

        List<IncompatiblePromed> incompatiblePromeds = incompatiblePromedRepository
                .findAllByPromedOrIncompatiblePromed(promed, promed)
                .orElse(new ArrayList<>());
        if (!incompatiblePromeds.isEmpty()) {
            incompatiblePromedRepository.deleteAll(incompatiblePromeds);
        }
        promedRepository.delete(promed);
    }

    @Override
    @Transactional
    public void subtractCount(String promedName, int count) {
        Optional<Promed> promed = promedRepository.findPromedByName(promedName);
        promed.get().setCount(promed.get().getCount() - count);
        promedRepository.save(promed.get());
        if (promed.get().getCount() < MIN_MEDIC_COUNT) {
            telegramBot.sendMsg("437815382", "You need to fill the following medicament of: " + promedName);
        }
    }
}
