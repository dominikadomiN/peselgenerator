package pl.domi.peselgenerator.validator.exception;

public class PeselValidatorException extends Exception {
    private static final String DEFAULT_MESSAGE = "Invalid value for PESEL";

    public PeselValidatorException() {
        this(DEFAULT_MESSAGE);
    }

    public PeselValidatorException(String message) {
        super(message);
    }
}
