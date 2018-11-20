package pl.edu.agh.hbs.exceptions;

public class SimulationExecutionException extends SimulationException {

    public SimulationExecutionException() {
    }

    public SimulationExecutionException(String message) {
        super(message);
    }

    public SimulationExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SimulationExecutionException(Throwable cause) {
        super(cause);
    }

    public SimulationExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
