package be.vdab.proefpakket.services;

import be.vdab.proefpakket.domain.Brouwer;
import be.vdab.proefpakket.repositories.BrouwerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly= false ,isolation= Isolation.READ_COMMITTED)
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

    @Override
    public Brouwer findById(long id) {
        return brouwerRepository.findById(id);
    }

    @Override
    public void update(Brouwer brouwer) {
        brouwerRepository.save(brouwer);
    }
}
