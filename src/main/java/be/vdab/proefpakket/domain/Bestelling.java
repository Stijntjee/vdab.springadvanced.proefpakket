package be.vdab.proefpakket.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name ="bestellingen")
public class Bestelling implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public interface Stap1 {}
    public interface Stap2 {}
    @NotNull(groups = Stap2.class)
    private LocalDate datum;
    @NotBlank(groups = Stap1.class)
    private String voornaam;
    @NotBlank(groups = Stap1.class)
    private String familienaam;
    @NotBlank(groups = Stap1.class)
    @Email(groups = Stap1.class)
    private String emailAdres;
    @NotNull(groups = Stap2.class)
    @Embedded
    private Adres adres;
    @NotNull(groups = Stap2.class)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "brouwerid")
    private Brouwer brouwer;

    //GETTERS
    public long getId() {
        return id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public String getEmailAdres() {
        return emailAdres;
    }

    public Adres getAdres() {
        return adres;
    }

    public Brouwer getBrouwer() {
        return brouwer;
    }
}
