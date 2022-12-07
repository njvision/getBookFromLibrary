package md.jeltobriuh.springmvc.library.controllers;

import md.jeltobriuh.springmvc.library.dao.BookDao;
import md.jeltobriuh.springmvc.library.dao.PersonDao;
import md.jeltobriuh.springmvc.library.models.Book;
import md.jeltobriuh.springmvc.library.models.Person;
import md.jeltobriuh.springmvc.library.util.BookValidator;
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

import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDao bookDao;
    private final PersonDao personDao;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao, BookValidator bookValidator) {
        this.bookDao = bookDao;
        this.personDao = personDao;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDao.getBook());
        return "book/index";
    }

    @GetMapping("/new")
    public String creatBookForm(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "book/new";
        }

        bookDao.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int bookId, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDao.showBook(bookId));

        Optional<Person> bookOwner = bookDao.getBookOwner(bookId);
        if(bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personDao.getPeople());
        }

        return "book/show";
    }

    @GetMapping("/{id}/edit")
    public String updateBookForm(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", bookDao.showBook(bookId));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int bookId, @ModelAttribute("book") Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookDao.update(bookId, book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int bookId) {
        bookDao.delete(bookId);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/add")
    public String addUser(@PathVariable("id") int bookId, @ModelAttribute("person") Person person) {
        System.out.println(bookId);
        System.out.println(person.getFirstLastName());
        bookDao.addUser(bookId, person);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int bookId) {
        bookDao.release(bookId);
        return "redirect:/book/" + bookId;
    }

    @PatchMapping("/{id}/assign")
    public String release(@PathVariable("id") int bookId, @ModelAttribute("person") Person selectPerson) {
        bookDao.assign(bookId, selectPerson);
        return "redirect:/book/" + bookId;
    }
}
