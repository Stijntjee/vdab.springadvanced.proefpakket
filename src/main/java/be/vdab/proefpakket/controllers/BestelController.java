package be.vdab.proefpakket.controllers;

import be.vdab.proefpakket.domain.Bestelling;
import be.vdab.proefpakket.domain.Brouwer;
import be.vdab.proefpakket.services.BestellingenService;
import be.vdab.proefpakket.services.BrouwerService;
import be.vdab.proefpakket.services.GemeenteService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/bestellen")
@SessionAttributes("bestelling")
class BestelController {
    private final BrouwerService brouwerService;
    private final BestellingenService bestellingenService;
    private final GemeenteService gemeenteService;
    //CONSTRUCTORS
    BestelController(BrouwerService brouwerService, BestellingenService bestellingenService, GemeenteService gemeenteService)
    {
        this.brouwerService = brouwerService;
        this.bestellingenService = bestellingenService;
        this.gemeenteService = gemeenteService;
    }

    //MAPPINGS
    @GetMapping("{id}/stap1")
    public ModelAndView stap1(@PathVariable Optional<Brouwer> optionalBrouwer){
        ModelAndView modelAndView = new ModelAndView("bestellenStap1");
        optionalBrouwer.ifPresent(brouwer -> modelAndView.addObject(brouwer).addObject(new Bestelling()));
        return modelAndView;
    }

    @PostMapping(value = "{id}/stap2", params = "stap2")
    public ModelAndView controleer(@Validated(Bestelling.Stap1.class) Bestelling bestelling, Errors errors, @PathVariable Optional<Brouwer> optionalBrouwer)
    {
        if (optionalBrouwer.isPresent()) {
            Brouwer brouwer = optionalBrouwer.get();
            if (errors.hasErrors()) {
                return new ModelAndView("proefpakketstap1").addObject(brouwer);
            }
            return new ModelAndView("bestellenStap2").addObject(brouwer).addObject("gemeenten", gemeenteService.findAll());
        }
        return new ModelAndView("bestellenStap2");
    }

    @PostMapping(value = "{id}/stap2", params = "stap1")
    public ModelAndView naarStap1(Bestelling bestelling, @PathVariable Optional<Brouwer> optionalBrouwer) {
        ModelAndView modelAndView = new ModelAndView("bestellenStap1");
        optionalBrouwer.ifPresent(brouwer -> modelAndView.addObject(brouwer));
        return modelAndView;
    }

    @PostMapping(value = "{id}/stap2", params = "opslaan")
    public ModelAndView opslaan(@Validated(Bestelling.Stap2.class) Bestelling bestelling, Errors errors, SessionStatus session,@PathVariable Brouwer brouwer)
    {
        if (errors.hasErrors()) {
            return new ModelAndView("bestellenStap2")
                    .addObject("gemeenten", gemeenteService.findAll());
        }
        bestellingenService.create(bestelling);
        session.setComplete();
        return new ModelAndView("redirect:/");
    }

    //METHODS
    @InitBinder("bestelling")
    void initBinder(DataBinder binder)
    {
        binder.initDirectFieldAccess();
    }
}
