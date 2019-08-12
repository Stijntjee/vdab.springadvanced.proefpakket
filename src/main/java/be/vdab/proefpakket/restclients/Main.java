package be.vdab.proefpakket.restclients;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.math.BigDecimal;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Main {
    private BigDecimal temp;

    //GETTERS
    public BigDecimal getTemp() {
        return temp;
    }
}
