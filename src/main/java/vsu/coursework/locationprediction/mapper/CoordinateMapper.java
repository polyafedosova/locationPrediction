package vsu.coursework.locationprediction.mapper;

import org.mapstruct.Mapper;
import vsu.coursework.locationprediction.dto.CoordinateDto;
import vsu.coursework.locationprediction.entity.Coordinate;

@Mapper(componentModel = "spring")
public interface CoordinateMapper  {

    Coordinate toEntity(CoordinateDto dto);

    CoordinateDto toDto(Coordinate coordinate);
}
