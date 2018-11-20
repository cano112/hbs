package pl.edu.agh.hbs.api.ui;

import java.io.Serializable;

public interface Representation extends Serializable {
    String getConfig();

    String getIdentity();
}
