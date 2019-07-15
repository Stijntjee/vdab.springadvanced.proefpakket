package be.vdab.proefpakket.controllers;

import be.vdab.proefpakket.services.BrouwerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController
{
    BrouwerService brouwerService;
    //CONSTRUCTORS
    IndexController(BrouwerService brouwerService)
    {
        this.brouwerService = brouwerService;
    }

    //MAPPINGS
    @GetMapping
    public ModelAndView index()
    {
        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("alfabet", alfabet());
        return modelAndView;
    }

    @GetMapping("{letter}")
    public ModelAndView indexMetBrouwerij(@PathVariable String letter)
    {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("alfabet", alfabet());
        modelAndView.addObject("brouwers", brouwerService.findByFirstLetter(letter));
        return modelAndView;
    }

    //METHODS
    private char[] alfabet()
    {
        char[] alfabet = "ABCDEFGHIJKLMNOPQRSTUWXYZ".toCharArray();
        return alfabet;
    }
}
