package be.vdab.proefpakket.forms;

import be.vdab.proefpakket.constraints.OndernemingsNummer;

import javax.validation.constraints.NotNull;

public class OndernemingsnummerForm {
    @NotNull
    @OndernemingsNummer
    private final Long ondernemingsnummer;

    //CONSTRUCOTRS


    public OndernemingsnummerForm(@NotNull /*@Ondernemingsnummer*/ Long ondernemingsnummer) {
        this.ondernemingsnummer = ondernemingsnummer;
    }

    //GETTERS
    public Long getOndernemingsnummer() {
        return ondernemingsnummer;
    }
}
