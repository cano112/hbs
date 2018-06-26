package pl.edu.agh.hbs.core.steps;

import java.io.Serializable;

public interface Step extends Serializable {
    void beforeStep(String areaId);
    void step(String areaId);
    void afterStep(String areaId);
}
