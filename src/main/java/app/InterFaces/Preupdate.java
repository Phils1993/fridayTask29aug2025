package app.InterFaces;

import jakarta.persistence.PreUpdate;

public interface Preupdate {
    @PreUpdate
    public void preUpdate();
}
