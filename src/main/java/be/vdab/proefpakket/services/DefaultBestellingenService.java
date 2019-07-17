package be.vdab.proefpakket.services;

import be.vdab.proefpakket.domain.Bestelling;
import be.vdab.proefpakket.repositories.BestellingenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
public class DefaultBestellingenService implements BestellingenService {
    BestellingenRepository bestellingenRepository;

    //CONSTRUCTORS
    DefaultBestellingenService(BestellingenRepository bestellingenRepository)
    {
        this.bestellingenRepository = bestellingenRepository;
    }

    //METHODS
    @Override
    public void create(Bestelling bestelling) {
        bestellingenRepository.save(bestelling);
    }
}
