package be.vdab.proefpakket.services;

import be.vdab.proefpakket.domain.Brouwer;
import be.vdab.proefpakket.repositories.BrouwerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultBrouwerService implements BrouwerService {

    BrouwerRepository brouwerRepository;
    //CONSTRUCTORS
    DefaultBrouwerService(BrouwerRepository brouwerRepository)
    {
        this.brouwerRepository = brouwerRepository;
    }
    @Override
    public List<Brouwer> findByFirstLetter(String letter) {
        return brouwerRepository.findByNaamIsStartingWithOrderByNaam(letter);
    }
}
