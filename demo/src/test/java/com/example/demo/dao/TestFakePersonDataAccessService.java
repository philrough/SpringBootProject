package com.example.demo.dao;
import org.junit.Test;

import com.example.demo.model.Person;

import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class TestFakePersonDataAccessService {

    @Test
    public void insertPersonWithName() {
        // Arrange
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "Dummy");
        FakePersonDataAccessService fp = new FakePersonDataAccessService();
        
        // Act
        int rc = fp.insertPerson(id, person);

        // Assert 
        assertEquals("failed", 1, rc);
    }

    @Test
    public void insertPersonWithNullName() {
        // Arrange
        UUID id = UUID.randomUUID();
        Person person = new Person(id, null);
        FakePersonDataAccessService fp = new FakePersonDataAccessService();
        
        // Act
        int rc = fp.insertPerson(id, person);

        // Assert 
        assertEquals("failed", 1, rc);
    }

    @Test
    public void insertPersonWithEmptyName() {
        // Arrange
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "");
        FakePersonDataAccessService fp = new FakePersonDataAccessService();
        
        // Act
        int rc = fp.insertPerson(id, person);

        // Assert 
        assertEquals("failed", 1, rc);
    }
    
    @Test
    public void selectAllPeopleWithEmptyDB() {
        // Arrange
        FakePersonDataAccessService fp = new FakePersonDataAccessService();
        List<Person> db;

        // Act
        db = fp.selectAllPeople();

        // Assert 
        assertEquals(0, db.size());
    }

    @Test
    public void selectAllPeopleWithSingleName() {
        // Arrange
        List<Person> db;
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "Dummy");
        FakePersonDataAccessService fp = new FakePersonDataAccessService();
        fp.insertPerson(id, person);
   
        // Act
        db = fp.selectAllPeople();

        // Assert 
        assertEquals(1, db.size());
        assertEquals("Dummy", db.get(0).getName() );
    }

    @Test
    public void selectAllPeopleWithDoubleName() {
        // Arrange
        List<Person> db;
        UUID id = UUID.randomUUID();
        Person person1 = new Person(id, "DummyA");
        Person person2 = new Person(id, "DummyB");
        FakePersonDataAccessService fp = new FakePersonDataAccessService();
        fp.insertPerson(id, person1);
        fp.insertPerson(id, person2);

        // Act
        db = fp.selectAllPeople();

        // Assert
        assertEquals(db.size(), 2);
        assertEquals("DummyA", db.get(0).getName());
        assertEquals("DummyB", db.get(1).getName());
    }

    @Test
    public void selectPersonWithId() {
        // Arrange
        Optional<Person> db;
        UUID id = UUID.randomUUID();
        Person person = new Person(id, "Dummy");
        FakePersonDataAccessService fp = new FakePersonDataAccessService();
        fp.insertPerson(id, person);

        // Act
        db = fp.selectPersonById(id);

        // Assert 
        assertEquals("Dummy", db.get().getName());
    }

    @Test(expected = NoSuchElementException.class)
    public void selectPersonWithInvalidIdThrowsException() {
        // Arrange
        Optional<Person> db;
        UUID id = UUID.randomUUID();
        UUID invalidId = UUID.randomUUID();
        Person person = new Person(id, "Dummy");
        FakePersonDataAccessService fp = new FakePersonDataAccessService();
        fp.insertPerson(id, person);

        // Act
        db = fp.selectPersonById(invalidId);

        // Assert 
        db.get().getName();
        
    }
}
