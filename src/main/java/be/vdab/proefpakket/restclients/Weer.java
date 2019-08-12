package be.vdab.proefpakket.restclients;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Weer {
    private Main main;

    //GETTERS
    public Main getMain() {
        return main;
    }
}
