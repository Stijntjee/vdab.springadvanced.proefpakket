package be.vdab.proefpakket.restclients;

import be.vdab.proefpakket.exceptions.KanTemperatuurNietLezenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;

@Component
public class OpenWeatherMapClient implements WeerClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String uriTemplate;
    private final RestTemplate restTemplate;

    OpenWeatherMapClient(@Value("${openWeatherMapURL}") String uriTemplate, RestTemplateBuilder restTemplateBuilder) {
        this.uriTemplate = uriTemplate;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public BigDecimal getTemperatuur(String plaats) {
        try
        {
            return restTemplate.getForObject(uriTemplate, Weer.class, plaats).getMain().getTemp();
        }
        catch (Exception ex)
        {
            logger.error("kan temperatuur niet lezen", ex);
            throw new KanTemperatuurNietLezenException();
        }
    }
}
