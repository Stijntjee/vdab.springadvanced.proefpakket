package be.vdab.proefpakket.services;

import be.vdab.proefpakket.domain.Brouwer;

import java.util.List;

public interface BrouwerService {
    List<Brouwer> findByFirstLetter(String letter);
    Brouwer findById(long id);
    void update(Brouwer brouwer);
}
