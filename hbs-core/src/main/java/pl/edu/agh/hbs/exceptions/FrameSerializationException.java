package pl.edu.agh.hbs.exceptions;

public class FrameSerializationException extends SimulationException {

    public FrameSerializationException() {
    }

    public FrameSerializationException(String message) {
        super(message);
    }

    public FrameSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameSerializationException(Throwable cause) {
        super(cause);
    }

    public FrameSerializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
