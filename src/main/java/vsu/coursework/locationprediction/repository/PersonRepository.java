package vsu.coursework.locationprediction.repository;

import vsu.coursework.locationprediction.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findPersonById(Integer id);
}
