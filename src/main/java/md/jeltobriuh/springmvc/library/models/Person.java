package md.jeltobriuh.springmvc.library.models;

public class Person {
    private int id;
    private String firstLastName;
    private int yearOfBirth;

    public Person() {
    }

    public Person(int id, String first_last_name, int age) {
        this.id = id;
        this.firstLastName = first_last_name;
        this.yearOfBirth = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
