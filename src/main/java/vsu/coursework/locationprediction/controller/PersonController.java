package vsu.coursework.locationprediction.controller;

import org.springframework.web.bind.annotation.*;
import vsu.coursework.locationprediction.dto.PersonDto;
import vsu.coursework.locationprediction.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<PersonDto> get() {
        return service.getAll();
    }
    @GetMapping("/{id}")
    public PersonDto find(@PathVariable Integer id) {
        return service.find(id);
    }
    @PostMapping("/new")
    public void save(@RequestBody PersonDto personDto) {
        service.save(personDto);
    }
}
