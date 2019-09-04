package net.guides.springboot2.springboot2jpacrudexample.model;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Data
@Entity
@Audited
@Table(name = "incompatible_promeds")
public class IncompatiblePromed {
    @Id
    @Column(name = "incompatible_promed_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "promed", referencedColumnName = "type_id")
    private Promed promed;
    @ManyToOne
    @JoinColumn(name = "incomp_promed", referencedColumnName = "type_id")
    private Promed incompatiblePromed;

    public IncompatiblePromed() {
    }

    public IncompatiblePromed(Promed promed, Promed incompatiblePromed) {
        this.promed = promed;
        this.incompatiblePromed = incompatiblePromed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Promed getPromed() {
        return promed;
    }

    public void setPromed(Promed promed) {
        this.promed = promed;
    }

    public Promed getIncompatiblePromed() {
        return incompatiblePromed;
    }

    public void setIncompatiblePromed(Promed incompatiblePromed) {
        this.incompatiblePromed = incompatiblePromed;
    }
}
