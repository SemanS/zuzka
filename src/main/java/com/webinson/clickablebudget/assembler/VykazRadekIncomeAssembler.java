package com.webinson.clickablebudget.assembler;

import com.webinson.clickablebudget.dto.VykazRadekDto;
import com.webinson.clickablebudget.entity.Income;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
@Component
public class VykazRadekIncomeAssembler {

    public VykazRadekDto convertToDto(Income model, VykazRadekDto dto) {
        dto.setPolozka(model.getPolozka());
        dto.setApprovedBudget(model.getApprovedBudget());
        dto.setAdjustedBudget(model.getAdjustedbudget());
        dto.setSpentBudget(model.getSpentBudget());
        return dto;
    }

    public List<VykazRadekDto> toDtos(final Collection<Income> models) {
        final List<VykazRadekDto> dtos = new ArrayList<>();
        if (isNotEmpty(models)) {
            for (final Income model : models) {
                dtos.add(convertToDto(model, new VykazRadekDto()));
            }
        }
        return dtos;
    }

    public VykazRadekDto dtosToDto(final Collection<Income> models) {

        VykazRadekDto vykazRadekDto = new VykazRadekDto();
        double approvedBudget = 0;
        double adjustedBudget = 0;
        double spentBudget = 0;
        String name = null;
        String polozka = null;

        if (isNotEmpty(models)) {
            for (final Income income : models) {
                approvedBudget = approvedBudget + income.getApprovedBudget();
                adjustedBudget = adjustedBudget + income.getAdjustedbudget();
                spentBudget = spentBudget + income.getSpentBudget();
                polozka = income.getPolozka();
            }
            vykazRadekDto.setApprovedBudget(approvedBudget);
            vykazRadekDto.setAdjustedBudget(adjustedBudget);
            vykazRadekDto.setSpentBudget(spentBudget);
            vykazRadekDto.setPolozka(polozka);
            vykazRadekDto.setName(name);

        }
        return vykazRadekDto;
    }

    /*public VykazRadekDto dtosToDto2(final Collection<VykazRadekDto> models) {

        VykazRadekDto vykazRadekDto = new VykazRadekDto();
        double approvedBudget = 0;

        if (isNotEmpty(models)) {
            for (final VykazRadekDto vykaz : models) {
                approvedBudget = approvedBudget + vykaz.getApprovedBudget();
            }
            vykazRadekDto.setApprovedBudget(approvedBudget);
        }
        return vykazRadekDto;
    }*/

    public boolean isNotEmpty(final Collection<?> col) {
        return !isEmpty(col);
    }

    public boolean isEmpty(final Collection<?> col) {
        return col == null || col.isEmpty();
    }
}
