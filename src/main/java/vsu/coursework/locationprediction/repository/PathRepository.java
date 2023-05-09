package vsu.coursework.locationprediction.repository;

import vsu.coursework.locationprediction.entity.Path;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathRepository extends JpaRepository<Path, Integer> {

    Path findPathById(Integer id);
    List<Path> findAllByPerson_Id(Integer id);
}
