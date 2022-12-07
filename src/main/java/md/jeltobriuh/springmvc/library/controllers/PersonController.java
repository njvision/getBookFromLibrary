package md.jeltobriuh.springmvc.library.controllers;

import md.jeltobriuh.springmvc.library.dao.PersonDao;
import md.jeltobriuh.springmvc.library.models.Person;
import md.jeltobriuh.springmvc.library.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDao personDao;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDao personDao, PersonValidator personValidator) {
        this.personDao = personDao;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDao.getPeople());
        return "people/index";
    }

    @GetMapping("/new")
    public String addPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int personId, Model model) {
        model.addAttribute("person", personDao.showPerson(personId));
        model.addAttribute("books", personDao.getBooksByPersonId(personId));
        return "people/show";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int personId) {
        model.addAttribute("person", personDao.showPerson(personId));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatedPerson(@PathVariable("id") int personId, @ModelAttribute("person") Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if(bindingResult.hasErrors()) {
            return "people/edit";
        }

        personDao.update(personId, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int person_id) throws SQLException {
        personDao.delete(person_id);
        return "redirect:/people";
    }
}
