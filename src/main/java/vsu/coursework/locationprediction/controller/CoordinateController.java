package vsu.coursework.locationprediction.controller;

import vsu.coursework.locationprediction.dto.CoordinateDto;
import org.springframework.web.bind.annotation.*;
import vsu.coursework.locationprediction.service.CoordinateService;
import java.util.List;

@RestController
@RequestMapping("/coordinates")
public class CoordinateController {

    private final CoordinateService service;

    public CoordinateController(CoordinateService service) {
        this.service = service;
    }
    @GetMapping()
    public List<CoordinateDto> getAll() {
        return service.getAll();
    }
    @PostMapping("/new")
    public void saveNewCoordinate(@RequestBody CoordinateDto coordinate) {
        service.save(coordinate);
    }
    @PutMapping("/{id}/update")
    public void updateCoordinate(@PathVariable Integer id, @RequestBody CoordinateDto coordinate) {
        service.update(id, coordinate);
    }
    @DeleteMapping("/{id}/delete")
    public void deleteCoordinate(@PathVariable Integer id) {
        service.delete(id);
    }
}
