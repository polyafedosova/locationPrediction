package vsu.coursework.locationprediction.service;

import vsu.coursework.locationprediction.dto.CoordinateDto;
import vsu.coursework.locationprediction.entity.Coordinate;
import org.springframework.stereotype.Service;
import vsu.coursework.locationprediction.mapper.CustomCoordinateMapper;
import vsu.coursework.locationprediction.repository.CoordinateRepository;

import com.vividsolutions.jts.geom.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoordinateService {

    private final CoordinateRepository repository;
    private final CustomCoordinateMapper mapper;

    public CoordinateService(CoordinateRepository repository, CustomCoordinateMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void save(CoordinateDto coordinateDto) {
        Coordinate coordinate = mapper.toEntity(coordinateDto);
        repository.save(coordinate);
    }

    public List<CoordinateDto> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void update(Integer id, CoordinateDto coordinateDto) {
        Coordinate oldCoordinate = repository.findCoordinateById(id);
        Coordinate coordinate = mapper.toEntity(coordinateDto);
        coordinate.setId(oldCoordinate.getId());
        repository.save(coordinate);
    }

    public void delete(Integer id) {
        repository.delete(repository.findCoordinateById(id));
    }

    public CoordinateDto find(Integer id) {
        return mapper.toDto(repository.findCoordinateById(id));
    }
}
