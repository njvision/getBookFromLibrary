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
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople() {
        return jdbcTemplate.query("SELECT * FROM Person", new PersonMapper());
    }

    public Optional<Person> showPerson(String firstLastName) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE first_last_name=?", new Object[]{firstLastName}, new PersonMapper())
                .stream().findAny();
    }

    public Person showPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{id}, new PersonMapper())
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(first_last_name, year_birth) VALUES(?, ?)",
                person.getFirstLastName(), person.getYearOfBirth());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET first_last_name=?, year_birth=? WHERE person_id=?", person.getFirstLastName(), person.getYearOfBirth(), id);
    }

    public void delete(int id) throws SQLException {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?", id);
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id=?", new Object[]{id}, new BookMapper());
    }
}
