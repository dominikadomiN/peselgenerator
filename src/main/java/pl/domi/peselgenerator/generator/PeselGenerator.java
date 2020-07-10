package pl.domi.peselgenerator.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.domi.peselgenerator.generator.controldigit.ControlDigitGenerator;
import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.model.Gender;

import java.time.LocalDate;
import java.util.Random;

public class PeselGenerator implements IPeselGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeselGenerator.class);
    private static final String[] WOMAN = {"0", "2", "4", "6", "8"};
    private static final String[] MAN = {"1", "3", "5", "7", "9"};

    @Override
    public String generate(LocalDate dateOfBirth, Gender gender) throws PeselGeneratorException {
        if (dateOfBirth == null || gender == null) {
            LOGGER.warn("Invalid data of birth: {} , and gender: {} to generate PESEL", dateOfBirth, gender);
            throw new PeselGeneratorException();
        }

        String pesel = generateYearNumber(dateOfBirth.getYear())
                + generateMonthNumber(dateOfBirth.getMonthValue(), dateOfBirth.getYear())
                + generateDayNumber(dateOfBirth.getDayOfMonth())
                + generateRandomDigit()
                + generateRandomDigit()
                + generateRandomDigit()
                + genderNumberGenerator(gender);
        LOGGER.info("Pesel generated from date of birth {} and gender {} - {}", dateOfBirth, gender, pesel);
        return pesel + generateControlDigit(pesel);
    }

    private String generateYearNumber(int year) {
        int yearForPesel = year % 100;
        LOGGER.info("First two digit from year: {}", yearForPesel);
        return String.valueOf(yearForPesel);
    }

    private String generateMonthNumber(int month, int year) throws PeselGeneratorException {
        if (year < 1800 || year > 2099) {
            LOGGER.warn("Year {} is not supported");
            throw new PeselGeneratorException("Year is not valid to generate pesel");
        }
        int monthNumber = month;
        int firstPartOfYear = year / 100;
        switch (firstPartOfYear) {
            case 18:
                monthNumber += 80;
                break;
            case 20:
                monthNumber += 20;
                break;
        }
        LOGGER.info("Generated month number {}", monthNumber);
        return String.format("%02d", monthNumber);
    }

    private String generateDayNumber(int day) {
        String generatedDayNumber = String.format("%02d", day);
        LOGGER.info("Generated day number {}",generatedDayNumber);
        return generatedDayNumber;
    }

    private String generateRandomDigit() {
        int randomInt = new Random().nextInt(10);
        LOGGER.info("Random digit generated {}", randomInt);
        return String.valueOf(randomInt);
    }

    private String genderNumberGenerator(Gender gender) {
        int randomIndex = new Random().nextInt(5);
        if (gender.equals(Gender.WOMAN)) {
            LOGGER.info("Gender number generated for woman {}", randomIndex);
            return WOMAN[randomIndex];
        }
        LOGGER.info("Gender number generated for man {}", randomIndex);
        return MAN[randomIndex];
    }

    private String generateControlDigit(String pesel) throws PeselGeneratorException {
        int controlDigit = ControlDigitGenerator.generateControlDigit(pesel);
        LOGGER.info("Generated control digit {}", controlDigit);
        return String.valueOf(controlDigit);
    }
}