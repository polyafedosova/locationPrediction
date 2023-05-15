package vsu.coursework.locationprediction.controller;

import org.springframework.web.bind.annotation.*;
import vsu.coursework.locationprediction.dto.PathDto;
import vsu.coursework.locationprediction.dto.Point;
import vsu.coursework.locationprediction.service.PathService;
import vsu.coursework.locationprediction.suffixTree.Node;
import vsu.coursework.locationprediction.suffixTree.SuffixTree;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/{personID}")
public class PathController {

    private final PathService service;

    public PathController(PathService service) {
        this.service = service;
    }

//    @GetMapping()
//    public List<PathDto> getAll() {
//        return service.getAll();
//    }
    @PostMapping("/paths/new")
    public void save(@PathVariable Integer personID, @RequestBody List<Point> points) {
        service.save(personID, points);
    }
    @GetMapping("/paths")
    public List<PathDto> findAllByPerson(@PathVariable Integer personID) {
        return service.find(personID);
    }
    @PostMapping("/search")
    public List<Node> searchNextWay(@PathVariable Integer personID, @RequestBody List<Point> points) throws IOException {
        return service.searchNextPaths(personID, points);
    }
    @GetMapping("/suffixTree")
    public SuffixTree makeSuffixTree(@PathVariable Integer personID) throws IOException {
        return service.makeSearchTree(personID);
    }
    @PutMapping("/paths/{pathID}/update")
    public void updateCoordinate(@PathVariable Integer pathID, @RequestBody List<Point> points) {
        service.update(pathID, points);
    }
    @DeleteMapping("/paths/{pathID}/delete")
    public void deleteCoordinate(@PathVariable Integer pathID) {
        service.delete(pathID);
    }
}
