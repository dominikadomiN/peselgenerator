package pl.domi.peselgenerator.generator.exception;

public class PeselGeneratorException extends Exception {
    private static final String DEFAULT_MESSAGE = "Invalid values to create PESEL";

    public PeselGeneratorException() {
        this(DEFAULT_MESSAGE);
    }

    public PeselGeneratorException(String message) {
        super(message);
    }
}
