package pl.domi.peselgenerator.validator;

import pl.domi.peselgenerator.model.Pesel;

public class PeselValidator implements IPeselValidator {

    public boolean isValid(String pesel) {
        if (!hasCorrectLength(pesel)) {
            return false;
        }

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

        Pesel p = new Pesel(pesel);
        int eleventhDigit = p.getControlDigit();

        int sum = firstDigitCheck + secondDigitCheck + thirdDigitCheck + forthDigitCheck + fifthDigitCheck +
                sixthDigitCheck + seventhDigitCheck + eighthDigitCheck + ninthDigitCheck + tenthDigitCheck;

        return sum % 10 == eleventhDigit;
    }

    private boolean hasCorrectLength(String pesel) {
        return pesel != null && !pesel.isEmpty() && pesel.length() == 11;
    }
}
