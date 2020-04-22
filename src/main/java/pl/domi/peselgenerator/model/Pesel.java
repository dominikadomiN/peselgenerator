package pl.domi.peselgenerator.model;

import pl.domi.peselgenerator.generator.controldigit.ControlDigitGenerator;
import pl.domi.peselgenerator.validator.exception.PeselValidatorException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pesel {
    private static final Pattern PATTERN = Pattern.compile("^\\d{11}$");
    private final String pesel;
    private final LocalDate dateOfBirth;

    public Pesel(String pesel) throws PeselValidatorException {
        this.pesel = pesel;
        validatePesel();
        this.dateOfBirth = generateDateOfBirth();
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    private LocalDate generateDateOfBirth() throws PeselValidatorException {
        int generatedYear = Integer.parseInt(generateYear());
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));
        try {
            return LocalDate.of(generatedYear, month % 20, day);
        } catch (DateTimeException e) {
            throw new PeselValidatorException(e.getMessage());
        }
    }

    private String generateYear() throws PeselValidatorException {
        String firstDigitOfMonth = pesel.substring(2, 3);

        if (isMonthEqualFor1800(firstDigitOfMonth)) {
            return 18 + pesel.substring(0, 2);
        } else if (isMonthEqualFor1900(firstDigitOfMonth)) {
            return 19 + pesel.substring(0, 2);
        } else if (isMonthEqualFor2000(firstDigitOfMonth)) {
            return 20 + pesel.substring(0, 2);
        }
        throw new PeselValidatorException("Invalid date");
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

    private boolean hasCorrectLength() {
        if (pesel != null) {
            Matcher matcher = PATTERN.matcher(pesel);
            return matcher.matches();
        }
        return false;
    }

    private void validatePesel() throws PeselValidatorException{
        if (!hasCorrectLength()) {
            throw new PeselValidatorException("Pesel is null or has incorrect length");
        }
        int controlDigit = ControlDigitGenerator.generateControlDigit(pesel);

        if(controlDigit != getControlDigit()){
            throw new PeselValidatorException("Control digit is incorrect");
        }
    }
}
