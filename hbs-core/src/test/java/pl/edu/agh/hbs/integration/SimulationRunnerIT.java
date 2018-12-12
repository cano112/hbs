package pl.edu.agh.hbs.integration;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.hazelcast.core.HazelcastInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.agh.age.compute.api.DistributionUtilities;
import pl.edu.agh.age.compute.api.ThreadPool;
import pl.edu.agh.age.services.worker.internal.DefaultThreadPool;
import pl.edu.agh.hbs.bootstrap.HerdBehaviourSimulationRunner;
import pl.edu.agh.hbs.integration.fake.TestDistributionUtilities;
import pl.edu.agh.hbs.simulation.generic.GenericAreaStep;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SimulationRunnerIT.TestContextConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SimulationRunnerIT {

    @Autowired
    private HerdBehaviourSimulationRunner runner;

    @Test
    public void shouldCompleteSimulationStepsOnTwoAreasInProperOrder() {
        // given
        ListAppender<ILoggingEvent> appender = new ListAppender<>();
        Logger logger = (Logger) LoggerFactory.getLogger(GenericAreaStep.class);
        logger.setLevel(Level.DEBUG);
        logger.addAppender(appender);
        appender.start();

        // when
        runner.run();

        // then
        List<ILoggingEvent> logs = appender.list;
        Map<String, List<ILoggingEvent>> eventsGroupedByThread = logs.stream()
                .collect(Collectors.groupingBy(ILoggingEvent::getThreadName));

        assertThat(eventsGroupedByThread.values())
                .allMatch(events -> events.size() == 20);
    }

    @Configuration
    @ImportResource({"classpath:spring-node.xml", "classpath:simulation-config-bird-test.xml"})
    public static class TestContextConfig {

        @Autowired
        private HazelcastInstance hazelcastInstance;

        @Bean
        public DistributionUtilities distributionUtilities() {
            return new TestDistributionUtilities(hazelcastInstance);
        }

        @Bean
        public ThreadPool threadPool() {
            return new DefaultThreadPool();
        }
    }
}
