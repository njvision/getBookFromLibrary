package md.jeltobriuh.springmvc.library.util;

import md.jeltobriuh.springmvc.library.dao.BookDao;
import md.jeltobriuh.springmvc.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    private final BookDao bookDao;

    @Autowired
    public BookValidator(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookDao.showBookByTitle(book.getTitle()).isPresent()) {
            errors.rejectValue("title", "", "The book exists in the library");
        }
        if (book.getTitle().isEmpty()) {
            errors.rejectValue("title", "", "The book should have a title");
        }
        if (book.getAuthor().isEmpty()) {
            errors.rejectValue("author", "", "The book should have an author");
        }
        if (book.getYear() == 0) {
            errors.rejectValue("year", "", "The book should have year raise");
        }
    }
}
