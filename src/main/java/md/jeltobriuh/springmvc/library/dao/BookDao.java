package md.jeltobriuh.springmvc.library.dao;

import md.jeltobriuh.springmvc.library.models.Book;
import md.jeltobriuh.springmvc.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBook() {
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public Book showBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id=?", new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public Optional<Book> showBookByTitle(String title) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE title=?", new Object[]{title}, new BookMapper())
                .stream().findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(person_id, title, author, published_year) VALUES(null, ?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET title=?, author=?, published_year=? WHERE book_id=?", updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear(), id);
     }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", id);
    }

    public void addUser(int bookId, Person person) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", person.getId(), bookId);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT person.* FROM Book JOIN person ON book.person_id = person.person_id WHERE book_id=?", new Object[]{id}, new PersonMapper())
                .stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", id);
    }

    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", selectedPerson.getId(), id);
    }
}
