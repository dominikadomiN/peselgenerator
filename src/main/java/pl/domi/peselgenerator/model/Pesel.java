package pl.domi.peselgenerator.model;

import pl.domi.peselgenerator.validator.exception.PeselValidatorException;

public class Pesel {

    private final String pesel;

    public Pesel(String pesel) {
        this.pesel = pesel;
    }

    public int getControlDigit() {
        return Integer.parseInt(pesel.substring(10));
    }

    public Gender getGender() {
        int number = Integer.parseInt(pesel.substring(9, 10));

        if (number % 2 == 0) {
            return Gender.WOMAN;
        }
        return Gender.MAN;
    }

    public Month getMonth() throws PeselValidatorException {
        return Month.fromMonthNumber(pesel.substring(2, 4));
    }

    public String getYear() throws PeselValidatorException {
        String firstDigitOfMonth = pesel.substring(2, 3);

        if (isMonthEqualFor1800(firstDigitOfMonth)) {
            return 18 + pesel.substring(0, 2);
        } else if (isMonthEqualFor1900(firstDigitOfMonth)) {
            return 19 + pesel.substring(0, 2);
        } else if (isMonthEqualFor2000(firstDigitOfMonth)) {
            return 20 + pesel.substring(0, 2);
        }
        throw new PeselValidatorException();
    }

    private boolean isMonthEqualFor1800(String pesel) {
        return pesel.equals("8") || pesel.equals("9");
    }

    private boolean isMonthEqualFor1900(String pesel) {
        return pesel.equals("0") || pesel.equals("1");
    }

    private boolean isMonthEqualFor2000(String pesel) {
        return pesel.equals("2") || pesel.equals("3");
    }
}
