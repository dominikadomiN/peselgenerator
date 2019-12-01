package pl.domi.peselgenerator.model;

import pl.domi.peselgenerator.validator.exception.PeselValidatorException;

import java.util.Arrays;

public enum Month {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

    public static Month fromMonthNumber(String monthNumber) throws PeselValidatorException {
        return Arrays.stream(values())
                .filter(month -> month.ordinal() + 1 == Integer.parseInt(monthNumber))
                .findFirst()
                .orElseThrow(PeselValidatorException::new);
    }
}
