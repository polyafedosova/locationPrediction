package vsu.coursework.locationprediction.mapper;

import vsu.coursework.locationprediction.dto.PathDto;
import vsu.coursework.locationprediction.entity.Path;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PathMapper {

    Path toEntity(PathDto pathDto);
    PathDto toDto(Path path);
}

