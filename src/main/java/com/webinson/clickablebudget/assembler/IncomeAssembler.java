package com.webinson.clickablebudget.assembler;

import com.webinson.clickablebudget.dto.IncomeAndOutcomeDto;
import com.webinson.clickablebudget.entity.Income;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Slavo on 13.09.2016.
 */
@Component
public class IncomeAssembler {

    public IncomeAndOutcomeDto convertToDto(Income model, IncomeAndOutcomeDto dto) {
        dto.setKlass(model.getPolozka());
        return dto;
    }

    public List<IncomeAndOutcomeDto> toDtos(final Collection<Income> models) {
        final List<IncomeAndOutcomeDto> dtos = new ArrayList<>();
        if (isNotEmpty(models)) {
            for (final Income model : models) {
                dtos.add(convertToDto(model, new IncomeAndOutcomeDto()));
            }
        }
        return dtos;
    }

    public boolean isNotEmpty(final Collection<?> col) {
        return !isEmpty(col);
    }

    public boolean isEmpty(final Collection<?> col) {
        return col == null || col.isEmpty();
    }

}
