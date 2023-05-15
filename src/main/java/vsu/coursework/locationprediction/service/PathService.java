package vsu.coursework.locationprediction.service;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import vsu.coursework.locationprediction.suffixTree.*;
import vsu.coursework.locationprediction.dto.PathDto;
import vsu.coursework.locationprediction.entity.Path;
import vsu.coursework.locationprediction.dto.Point;
import vsu.coursework.locationprediction.mapper.PathMapper;
import org.springframework.stereotype.Service;
import vsu.coursework.locationprediction.repository.PathRepository;
import vsu.coursework.locationprediction.repository.PersonRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PathService {

    private final PathRepository repository;
    private final PersonRepository personRepository;
    private final PathMapper mapper;

    public PathService(PathRepository repository, PersonRepository personRepository, PathMapper mapper) {
        this.repository = repository;
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    public void save(Integer id, List<Point> points) {
        Path path = new Path();
        path.setPath(createLineString(points));
        path.setPerson(personRepository.findPersonById(id));
        repository.save(path);
    }

    public List<PathDto> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void update(Integer id, List<Point> points) {
        Path oldPath = repository.findPathById(id);
        Path path = new Path(id, createLineString(points), oldPath.getPerson());
        repository.save(path);
    }

    public void delete(Integer id) {
        repository.delete(repository.findPathById(id));
    }

    public List<PathDto> find(Integer personID) {
        return repository.findAllByPerson_Id(personID)
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public SuffixTree makeSearchTree(Integer personID) throws IOException {
        List<Path> paths = repository.findAllByPerson_Id(personID);
        List<List<Point>> listPaths = new ArrayList<>();
        for (Path path : paths) {
            Coordinate[] coordinates = path.getPath().getCoordinates();
            List<Point> points = toListPoint(coordinates);
            listPaths.add(points);
        }
        SuffixTree suffixTree = new SuffixTree(listPaths);
        SuffixTree.viz(suffixTree);
        return suffixTree;
    }
    public List<Node> searchNextPaths(Integer personID, List<Point> pathPrev) throws IOException {
        SuffixTree suffixTree = makeSearchTree(personID);
        return suffixTree.search(pathPrev);
    }

    private List<Point> toListPoint(Coordinate[] coordinates) {
        List<Point> points = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            double x = coordinate.getX();
            double y = coordinate.getY();
            Point point = new Point(x, y);
            points.add(point);
        }
        return points;
    }

    private LineString createLineString(List<Point> points) {
        GeometryFactory geometryFactory = new GeometryFactory();
        for (int i = 1; i < points.size(); i++) {
            Point prevPoint = points.get(i - 1);
            Point currPoint = points.get(i);
            if (prevPoint.equals(currPoint)) {
                points.remove(i);
                i--;
            }
        }
        Coordinate[] coordinates = new Coordinate[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            coordinates[i] = new Coordinate(point.getX(), point.getY());
        }
        if (coordinates.length > 1) {
            LineString lineString = geometryFactory.createLineString(coordinates);
            lineString.setSRID(4326);
            return lineString;
        } else {
            throw new RuntimeException("Path must contain more than two points");
        }
    }
}
