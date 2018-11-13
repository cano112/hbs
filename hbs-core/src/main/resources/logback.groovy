import ch.qos.logback.classic.encoder.PatternLayoutEncoder

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%highlight(%.-1level) %green(%-40logger{39}) : %msg%n"
    }
}

logger("pl.edu.agh.hbs", DEBUG, ["CONSOLE"])
