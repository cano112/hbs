package pl.edu.agh.hbs.core.providers;

import java.io.Serializable;

public interface Representation extends Serializable {
    String getConfig();

    String getIdentity();
}
