package pl.domi.peselgenerator.validator;

import pl.domi.peselgenerator.generator.controldigit.ControlDigitGenerator;
import pl.domi.peselgenerator.model.Pesel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PeselValidator implements IPeselValidator {
    private static final Pattern PATTERN = Pattern.compile("^\\d{11}$");

    private ControlDigitGenerator controlDigitGenerator;

    public PeselValidator(ControlDigitGenerator controlDigitGenerator) {
        this.controlDigitGenerator = controlDigitGenerator;
    }

    public boolean isValid(String pesel) {
        if (!hasCorrectLength(pesel)) {
            return false;
        }

        int sum = controlDigitGenerator.generateControlDigit(pesel);

        Pesel p = new Pesel(pesel);
        int eleventhDigit = p.getControlDigit();

        return sum % 10 == eleventhDigit;
    }

    private boolean hasCorrectLength(String pesel) {
        if (pesel != null) {
            Matcher matcher = PATTERN.matcher(pesel);
            return matcher.matches();
        }
        return false;
    }

}
