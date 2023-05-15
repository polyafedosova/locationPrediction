package vsu.coursework.locationprediction.mapper;

import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Component;
import vsu.coursework.locationprediction.dto.CoordinateDto;
import vsu.coursework.locationprediction.entity.Coordinate;
import vsu.coursework.locationprediction.dto.Point;

@Component
public class CustomCoordinateMapper implements CoordinateMapper {

    @Override
    public Coordinate toEntity(CoordinateDto dto) {
        Coordinate coordinate = new Coordinate();
        coordinate.setName(dto.getName());
        int srid = 4326;
        GeometryFactory geometryFactory = new GeometryFactory();
        org.locationtech.jts.geom.Point point = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(dto.getPoint().getX(), dto.getPoint().getY()));
        point.setSRID(srid);
        coordinate.setCoordinate(point);
        return coordinate;
    }

    @Override
    public CoordinateDto toDto(Coordinate coordinate) {
        CoordinateDto dto = new CoordinateDto();
        dto.setPoint(new Point(coordinate.getCoordinate().getCoordinate().x,
                coordinate.getCoordinate().getCoordinate().y));
        dto.setName(coordinate.getName());
        return dto;
    }
}