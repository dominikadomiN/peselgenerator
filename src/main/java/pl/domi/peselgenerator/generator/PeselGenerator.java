package pl.domi.peselgenerator.generator;

import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.model.Gender;
import pl.domi.peselgenerator.model.Month;
import pl.domi.peselgenerator.validator.IPeselValidator;

import java.util.Random;

public class PeselGenerator implements IPeselGenerator {

    private static final String[] WOMAN = {"0", "2", "4", "6", "8"};
    private static final String[] MAN = {"1", "3", "5", "7", "9"};

    private final IPeselValidator peselValidator;

    public PeselGenerator(IPeselValidator peselValidator) {
        this.peselValidator = peselValidator;
    }

    @Override
    public String generate(String year, Month month, String day, Gender gender) throws PeselGeneratorException {
        if (year == null || month == null || day == null || gender == null) {
            throw new PeselGeneratorException();
        }

        String pesel = generateYearNumber(year)
                + generateMonthNumber(month, year)
                + generateDayNumber(day)
                + generateRandomDigit()
                + generateRandomDigit()
                + generateRandomDigit()
                + genderNumberGenerator(gender);

        pesel = pesel + generateControlDigit(pesel);

        boolean isPeselValid = peselValidator.isValid(pesel);

        if (isPeselValid) {
            return pesel;
        }
        throw new PeselGeneratorException("PESEL cannot be generated");
    }

    private String generateYearNumber(String year) {
        return year.substring(2, 4);
    }

    private String generateMonthNumber(Month month, String year) throws PeselGeneratorException {
        if (year.substring(0, 2).isEmpty()) {
            throw new PeselGeneratorException("Year is not valid to generate pesel");
        }
        int monthNumber = 0;
        String century = year.substring(0, 2);
        switch (century) {
            case "18":
                monthNumber = 80;
                break;
            case "19":
                monthNumber = 0;
                break;
            case "20":
                monthNumber = 20;
                break;
        }
        monthNumber += month.ordinal() + 1;
        return String.format("%02d", monthNumber);
    }

    private String generateDayNumber(String day) {
        return day;
    }

    private String generateRandomDigit() {
        int randomInt = new Random().nextInt(10);
        return String.valueOf(randomInt);
    }

    private String genderNumberGenerator(Gender gender) {
        int randomIndex = new Random().nextInt(5);
        if (gender.equals(Gender.WOMAN)) {
            return WOMAN[randomIndex];
        }
        return MAN[randomIndex];
    }

    private String generateControlDigit(String pesel) {
        int firstDigitCheck = 9 * Integer.parseInt(pesel.substring(0, 1));
        int secondDigitCheck = 7 * Integer.parseInt(pesel.substring(1, 2));
        int thirdDigitCheck = 3 * Integer.parseInt(pesel.substring(2, 3));
        int forthDigitCheck = Integer.parseInt(pesel.substring(3, 4));
        int fifthDigitCheck = 9 * Integer.parseInt(pesel.substring(4, 5));
        int sixthDigitCheck = 7 * Integer.parseInt(pesel.substring(5, 6));
        int seventhDigitCheck = 3 * Integer.parseInt(pesel.substring(6, 7));
        int eighthDigitCheck = Integer.parseInt(pesel.substring(7, 8));
        int ninthDigitCheck = 9 * Integer.parseInt(pesel.substring(8, 9));
        int tenthDigitCheck = 7 * Integer.parseInt(pesel.substring(9, 10));

        int sum = firstDigitCheck + secondDigitCheck + thirdDigitCheck + forthDigitCheck + fifthDigitCheck +
                sixthDigitCheck + seventhDigitCheck + eighthDigitCheck + ninthDigitCheck + tenthDigitCheck;

        return String.valueOf(sum);
    }
}