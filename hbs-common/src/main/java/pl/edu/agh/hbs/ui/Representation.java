package pl.edu.agh.hbs.ui;

import java.io.Serializable;

public interface Representation extends Serializable {
    String getConfig();

    String getIdentity();
}
