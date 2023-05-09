package vsu.coursework.locationprediction.repository;

import com.vividsolutions.jts.geom.Point;
import org.springframework.lang.NonNullApi;
import vsu.coursework.locationprediction.entity.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Integer> {

//    @Query(value = "SELECT id, name, coordinate FROM coordinate", nativeQuery = true)
//    List<Coordinate> findAll();
    Coordinate findCoordinateById(Integer id);
    @Query(value = "SELECT *\n" +
            "FROM coordinate\n" +
            "WHERE ST_DWithin(coordinate.coordinate, ?1 ::geography, 500)\n" +
            "ORDER BY ST_Distance(coordinate, ?1 ::geography) ASC\n" +
            "LIMIT 1;", nativeQuery = true)
    Coordinate findCoordinateByCoordinate(Point point);
}
