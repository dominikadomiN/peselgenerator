package pl.domi.peselgenerator.generator;

import pl.domi.peselgenerator.generator.exception.PeselGeneratorException;
import pl.domi.peselgenerator.model.Gender;

import java.time.LocalDate;
import java.util.Random;

public class PeselGenerator implements IPeselGenerator {

    private static final String[] WOMAN = {"0", "2", "4", "6", "8"};
    private static final String[] MAN = {"1", "3", "5", "7", "9"};

    @Override
    public String generate(LocalDate dateOfBirth, Gender gender) throws PeselGeneratorException {
        if (dateOfBirth == null || gender == null) {
            throw new PeselGeneratorException();
        }


        String pesel = generateYearNumber(dateOfBirth.getYear())
                + generateMonthNumber(dateOfBirth.getMonthValue(), dateOfBirth.getYear())
                + generateDayNumber(dateOfBirth.getDayOfMonth())
                + generateRandomDigit()
                + generateRandomDigit()
                + generateRandomDigit()
                + genderNumberGenerator(gender);

        return pesel + generateControlDigit(pesel);
    }

    private String generateYearNumber(int year) {
        int yearForPesel = year % 100;
        return String.valueOf(yearForPesel);
    }

    private String generateMonthNumber(int month, int year) throws PeselGeneratorException {
        if (year < 1800 || year > 2099) {
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
        return String.format("%02d", monthNumber);
    }

    private String generateDayNumber(int day) {
        return String.format("%02d", day);
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