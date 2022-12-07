package md.jeltobriuh.springmvc.library.util;

import md.jeltobriuh.springmvc.library.dao.PersonDao;
import md.jeltobriuh.springmvc.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personDao.showPerson(person.getFirstLastName()).isPresent()) {
            errors.rejectValue("firstLastName", "", "This user is already registered");
        }
        if(person.getFirstLastName().isEmpty()) {
            errors.rejectValue("firstLastName", "", "This user has name");
        }
        if(person.getYearOfBirth() == 0 || person.getYearOfBirth() < 1900 || person.getYearOfBirth() > 2004) {
            errors.rejectValue("yearOfBirth", "", "The person should be born in one of the year");
        }
    }
}
