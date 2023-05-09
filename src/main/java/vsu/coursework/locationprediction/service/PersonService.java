package vsu.coursework.locationprediction.service;

import vsu.coursework.locationprediction.dto.PersonDto;
import vsu.coursework.locationprediction.entity.Person;
import vsu.coursework.locationprediction.mapper.PersonMapper;
import org.springframework.stereotype.Service;
import vsu.coursework.locationprediction.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    public PersonService(PersonRepository repository, PersonMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PersonDto> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void save(PersonDto personDto) {
        Person person = mapper.toEntity(personDto);
        repository.save(person);
    }

    public PersonDto find(Integer id) {
        return mapper.toDto(repository.findPersonById(id));
    }
}
