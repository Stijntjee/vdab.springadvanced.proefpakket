package be.vdab.proefpakket.controllers;

import be.vdab.proefpakket.domain.Brouwer;
import be.vdab.proefpakket.forms.OndernemingsnummerForm;
import be.vdab.proefpakket.services.BrouwerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller("brouwer")
public class BrouwerController {
    BrouwerService brouwerService;

    //CONSTRUCTORS
    BrouwerController(BrouwerService brouwerService)
    {
        this.brouwerService = brouwerService;
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
}
