package be.vdab.proefpakket.controllers;

import be.vdab.proefpakket.domain.Bestelling;
import be.vdab.proefpakket.domain.Brouwer;
import be.vdab.proefpakket.forms.OndernemingsnummerForm;
import be.vdab.proefpakket.services.BestellingService;
import be.vdab.proefpakket.services.BrouwerService;
import be.vdab.proefpakket.services.GemeenteService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller("brouwer")
@SessionAttributes("bestelling")
public class BrouwerController {
    BrouwerService brouwerService;
    private final GemeenteService gemeenteService;
    private final BestellingService bestellingService;

    //CONSTRUCTORS
    BrouwerController(BrouwerService brouwerService, GemeenteService gemeenteService, BestellingService bestellingService)
    {
        this.brouwerService = brouwerService;
        this.gemeenteService = gemeenteService;
        this.bestellingService = bestellingService;
    }

    //MAPPINGS
    @GetMapping("brouwer/{id}")
    public ModelAndView getBrouwer(@PathVariable long id)
    {
        ModelAndView modelAndView = new ModelAndView("brouwer");
        modelAndView.addObject("brouwer", brouwerService.findById(id));
        return modelAndView;
    }

    @GetMapping("brouwer/{id}/ondernemingsnummer")
    public ModelAndView ondernemingsnummerForm(@PathVariable long id)
    {
        ModelAndView modelAndView = new ModelAndView("ondernemingsnummer");
        modelAndView.addObject("brouwer", brouwerService.findById(id));
        modelAndView.addObject(new OndernemingsnummerForm(brouwerService.findById(id).getOndernemingsNr()));
        return modelAndView;
    }

    @PostMapping("brouwer/{id}/ondernemingsnummer")
    public ModelAndView ondernemingsnummer(@Valid OndernemingsnummerForm form, Errors errors, @PathVariable long id)
    {
        ModelAndView modelAndView = new ModelAndView("ondernemingsnummer");
        Brouwer brouwer = brouwerService.findById(id);

        if (errors.hasErrors())
        {
            modelAndView.addObject("brouwer", brouwer);
            return modelAndView;
        }

        brouwer.setOndernemingsNr(form.getOndernemingsnummer());
        System.out.println(brouwer.getOndernemingsNr());
        brouwerService.update(brouwer);
        return new ModelAndView("redirect:/brouwer/" + id);
    }

    @GetMapping("brouwer/{optionalBrouwer}/proefpakket")
    public ModelAndView proefpakket(@PathVariable Optional<Brouwer> optionalBrouwer) {
        ModelAndView modelAndView = new ModelAndView("proefpakketstap1");
        optionalBrouwer.ifPresent(brouwer -> modelAndView.addObject(brouwer).addObject(new Bestelling()));
        return modelAndView;
    }

    @InitBinder("bestelling")
    void initBinder(DataBinder binder) {
        binder.initDirectFieldAccess();
    }

    @PostMapping(value = "brouwer/{optionalBrouwer}/proefpakket", params = "stap2")
    public ModelAndView proefpakketNaarStap2(@PathVariable Optional<Brouwer> optionalBrouwer, @Validated(Bestelling.Stap1.class) Bestelling bestelling, Errors errors) {
        if (optionalBrouwer.isPresent()) {
            Brouwer brouwer = optionalBrouwer.get();
            if (errors.hasErrors()) {
                return new ModelAndView("proefpakketstap1").addObject(brouwer);
            }
            return new ModelAndView("proefpakketstap2").addObject(brouwer).addObject("gemeenten", gemeenteService.findAll());
        }
        return new ModelAndView("proefpakketstap2");
    }

    @PostMapping(value = "brouwer/{optionalBrouwer}/proefpakket", params = "stap1")
    public ModelAndView proefpakketNaarStap1(@PathVariable Optional<Brouwer> optionalBrouwer, Bestelling bestelling) {
        ModelAndView modelAndView = new ModelAndView("proefpakketstap1");
        optionalBrouwer.ifPresent(brouwer -> modelAndView.addObject(brouwer));
        return modelAndView;
    }

    @PostMapping(value = "brouwer/{optionalBrouwer}/proefpakket", params = "opslaan")
    public ModelAndView proefpakketOpslaan(@PathVariable Optional<Brouwer> optionalBrouwer, @Validated(Bestelling.Stap2.class) Bestelling bestelling, Errors errors, SessionStatus session) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        optionalBrouwer.ifPresent(brouwer -> modelAndView.addObject(brouwer));
        if (errors.hasErrors()) {
            return new ModelAndView("proefpakketstap2").addObject("gemeenten", gemeenteService.findAll());
        }
        bestellingService.create(bestelling);
        session.setComplete();
        return modelAndView;
    }
}
