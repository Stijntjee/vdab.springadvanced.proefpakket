package be.vdab.proefpakket.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "gemeenten")
public class Gemeente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int postcode;
    private String naam;

    //GETTERS
    public long getId() {
        return id;
    }

    public int getPostcode() {
        return postcode;
    }

    public String getNaam() {
        return naam;
    }
}
