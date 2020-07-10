package pl.domi.peselgenerator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.domi.peselgenerator.generator.controldigit.ControlDigitGenerator;
import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.validator.exception.PeselValidatorException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pesel {
    private static final Logger LOGGER = LoggerFactory.getLogger(Pesel.class);
    private static final Pattern PATTERN = Pattern.compile("^\\d{11}$");
    private final String pesel;
    private final LocalDate dateOfBirth;

    public Pesel(String pesel) throws PeselValidatorException {
        this.pesel = pesel;
        try {
            LOGGER.debug("Validating pesel {} length", pesel);
            validatePesel();
        } catch (PeselGeneratorException e) {
            LOGGER.warn("Pesel {] is not valid", pesel);
            e.printStackTrace();
        }
        this.dateOfBirth = generateDateOfBirth();
    }

    public int getControlDigit() {
        int controlDigit = Integer.parseInt(pesel.substring(10));
        LOGGER.info("Control digit {}", controlDigit);
        return controlDigit;
    }

    public Gender getGender() {
        int number = Integer.parseInt(pesel.substring(9, 10));
        LOGGER.info("Gender number {}", number);

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
            LOGGER.info("Generating date of birth with month {} , day {} , and year {}", month, day, generatedYear);
            return LocalDate.of(generatedYear, month % 20, day);
        } catch (DateTimeException e) {
            LOGGER.warn("Invalid values {} {} {} to generate Pesel", generatedYear, month, day);
            throw new PeselValidatorException(e.getMessage());
        }
    }

    private String generateYear() throws PeselValidatorException {
        String firstDigitOfMonth = pesel.substring(2, 3);
        LOGGER.info("First digit of the month {}", firstDigitOfMonth);

        if (isMonthEqualFor1800(firstDigitOfMonth)) {
            return 18 + pesel.substring(0, 2);
        } else if (isMonthEqualFor1900(firstDigitOfMonth)) {
            return 19 + pesel.substring(0, 2);
        } else if (isMonthEqualFor2000(firstDigitOfMonth)) {
            return 20 + pesel.substring(0, 2);
        }
        LOGGER.warn("Year not supported");
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

    private boolean hasCorrectLengthWithOnlyDigits() {
        Matcher matcher = PATTERN.matcher(pesel);
        return matcher.matches();
    }

    private void validatePesel() throws PeselValidatorException, PeselGeneratorException {
        if (pesel == null) {
            LOGGER.warn("Pesel is null");
            throw new PeselValidatorException("Pesel cannot be null ");
        }
        if (!hasCorrectLengthWithOnlyDigits()) {
            LOGGER.warn("Invalid for pesel: {}", pesel);
            throw new PeselValidatorException("Pesel '" + pesel + "' is invalid");
        }
        int controlDigit = ControlDigitGenerator.generateControlDigit(pesel);

        if (controlDigit != getControlDigit()) {
            LOGGER.warn("Control digit {} is incorrect", controlDigit);
            throw new PeselValidatorException("Control digit is incorrect");
        }
    }
}
