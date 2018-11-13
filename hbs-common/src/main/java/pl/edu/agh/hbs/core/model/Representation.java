package pl.edu.agh.hbs.core.model;

import java.io.Serializable;

public interface Representation extends Serializable {
    String getConfig();

    String getIdentity();
}
