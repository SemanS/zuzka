package com.webinson.clickablebudget.assembler;

import com.webinson.clickablebudget.dto.VykazRadekDto;
import com.webinson.clickablebudget.entity.Income;
import com.webinson.clickablebudget.entity.Outcome;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Slavo on 10/3/2016.
 */

@Component
public class VykazRadekOutcomeAssembler {

    public VykazRadekDto convertToDto(Outcome model, VykazRadekDto dto) {
        dto.setParagraf(model.getParagraf());
        dto.setApprovedBudget(model.getApprovedBudget());
        dto.setAdjustedBudget(model.getAdjustedbudget());
        dto.setSpentBudget(model.getSpentBudget());
        return dto;
    }

    public List<VykazRadekDto> toDtos(final Collection<Outcome> models) {
        final List<VykazRadekDto> dtos = new ArrayList<>();
        if (isNotEmpty(models)) {
            for (final Outcome model : models) {
                dtos.add(convertToDto(model, new VykazRadekDto()));
            }
        }
        return dtos;
    }

    public VykazRadekDto dtosToDto(final Collection<Outcome> models) {

        VykazRadekDto vykazRadekDto = new VykazRadekDto();
        double approvedBudget = 0;
        double adjustedBudget = 0;
        double spentBudget = 0;
        String name = null;
        String paragraf = null;

        if (isNotEmpty(models)) {
            for (final Outcome outcome : models) {
                approvedBudget = approvedBudget + outcome.getApprovedBudget();
                adjustedBudget = adjustedBudget + outcome.getAdjustedbudget();
                spentBudget = spentBudget + outcome.getSpentBudget();
                paragraf = outcome.getParagraf();
            }
            vykazRadekDto.setApprovedBudget(approvedBudget);
            vykazRadekDto.setAdjustedBudget(adjustedBudget);
            vykazRadekDto.setSpentBudget(spentBudget);
            vykazRadekDto.setParagraf(paragraf);
            vykazRadekDto.setName(name);

        }
        return vykazRadekDto;
    }

    public boolean isNotEmpty(final Collection<?> col) {
        return !isEmpty(col);
    }

    public boolean isEmpty(final Collection<?> col) {
        return col == null || col.isEmpty();
    }
}
