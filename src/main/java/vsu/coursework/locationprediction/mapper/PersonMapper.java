package vsu.coursework.locationprediction.mapper;

import vsu.coursework.locationprediction.dto.PersonDto;
import vsu.coursework.locationprediction.entity.Person;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person toEntity(PersonDto personDto);
    PersonDto toDto(Person person);
}
