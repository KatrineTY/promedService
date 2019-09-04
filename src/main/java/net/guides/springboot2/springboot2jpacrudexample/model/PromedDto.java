package net.guides.springboot2.springboot2jpacrudexample.model;

import java.util.List;

public class PromedDto {
    private Promed promed;
    private List<Promed> incompatiblePromeds;

    public PromedDto(Promed promed, List<Promed> incompatiblePromeds) {
        this.promed = promed;
        this.incompatiblePromeds = incompatiblePromeds;
    }

    public Promed getPromed() {
        return promed;
    }

    public void setPromed(Promed promed) {
        this.promed = promed;
    }

    public List<Promed> getIncompatiblePromeds() {
        return incompatiblePromeds;
    }

    public void setIncompatiblePromeds(List<Promed> incompatiblePromeds) {
        this.incompatiblePromeds = incompatiblePromeds;
    }
}
