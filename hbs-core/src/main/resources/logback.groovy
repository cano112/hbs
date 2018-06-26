import ch.qos.logback.classic.filter.ThresholdFilter
import pl.edu.agh.age.util.NodeSystemProperties

appender("CONSOLE", ConsoleAppender) {
    filter(ThresholdFilter) {
        level = NodeSystemProperties.LOG_CONSOLE_LEVEL.get()
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%highlight(%.-1level) %green(%-40logger{39}) : %msg%n"
    }
}

logger("pl.edu.agh.age", DEBUG, ["CONSOLE"])
logger("com.hazelcast", INFO)
logger("org.springframework", INFO)
logger("pl.edu.agh.hbs.core", DEBUG, ["CONSOLE"])