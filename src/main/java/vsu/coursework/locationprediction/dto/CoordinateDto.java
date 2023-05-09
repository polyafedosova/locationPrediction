package vsu.coursework.locationprediction.dto;

import lombok.Data;
import vsu.coursework.locationprediction.entity.Point;

@Data
public class CoordinateDto {

    private Point point;
    private String name;
}
