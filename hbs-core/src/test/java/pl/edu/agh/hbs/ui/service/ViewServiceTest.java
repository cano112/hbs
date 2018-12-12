package pl.edu.agh.hbs.ui.service;

import org.assertj.core.groups.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.edu.agh.hbs.model.ModifierBuffer;
import pl.edu.agh.hbs.model.Vector;
import pl.edu.agh.hbs.model.skill.basic.modifier.ModPosition;
import pl.edu.agh.hbs.model.skill.basic.modifier.ModRepresentation;
import pl.edu.agh.hbs.model.skill.common.modifier.ModVelocity;
import pl.edu.agh.hbs.simulation.agent.BirdAgent;
import pl.edu.agh.hbs.simulation.shape.BoxShape;
import pl.edu.agh.hbs.state.SimulationStateProvider;
import pl.edu.agh.hbs.ui.dto.Colour;
import pl.edu.agh.hbs.ui.dto.ViewFrame;
import pl.edu.agh.hbs.ui.dto.ViewPosition;
import scala.collection.JavaConverters;

import java.util.Arrays;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ViewServiceTest {

    @Mock
    private SimulationStateProvider stateProvider;

    @InjectMocks
    private ViewService viewService;

    @Test
    public void shouldPrepareCorrectFrame() {
        // given
        final BirdAgent birdA = prepareBirdWithBoxShape(3, 3, 5);
        final BirdAgent birdB = prepareBirdWithBoxShape(1, 3, 5);
        final BirdAgent birdC = prepareBirdWithBoxShape(2, 6, 1);
        given(stateProvider.getAllAgents()).willReturn(Arrays.asList(birdA, birdB, birdC));

        // when
        final ViewFrame frame = viewService.prepareViewFrame();

        // then
        then(frame.getBodies())
                .extracting("position", "kind")
                .contains(
                        Tuple.tuple(toViewPosition(birdA.position()), birdA.representation().getIdentity()),
                        Tuple.tuple(toViewPosition(birdB.position()), birdB.representation().getIdentity()),
                        Tuple.tuple(toViewPosition(birdC.position()), birdC.representation().getIdentity()));
    }

    private BirdAgent prepareBirdWithBoxShape(int factor, int posX, int posY) {
        return new BirdAgent(JavaConverters.asScalaBuffer(
                Arrays.asList(
                        new ModRepresentation(new BoxShape(factor), Colour.BLUE),
                        new ModPosition(Vector.of(posX, posY)),
                        new ModVelocity(Vector.of(3, 3), "standard"))), new ModifierBuffer());
    }

    private ViewPosition toViewPosition(Vector position) {
        double[] arrayPosition = position.toArray();
        return new ViewPosition((int) arrayPosition[0], (int) arrayPosition[1]);
    }
}