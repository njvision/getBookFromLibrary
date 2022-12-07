package md.jeltobriuh.springmvc.library.dao;

import md.jeltobriuh.springmvc.library.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("person_id"));
        person.setFirstLastName(rs.getString("first_last_name"));
        person.setYearOfBirth(rs.getInt("year_birth"));

        return person;
    }
}
