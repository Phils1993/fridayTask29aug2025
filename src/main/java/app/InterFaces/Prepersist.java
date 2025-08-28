package app.InterFaces;

import jakarta.persistence.PreUpdate;

public interface Prepersist {
    @PreUpdate
    public void prePersist();
}
