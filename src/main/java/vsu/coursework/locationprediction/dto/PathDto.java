package vsu.coursework.locationprediction.dto;

import org.locationtech.jts.geom.LineString;
import lombok.Data;

@Data
public class PathDto {

    private LineString path;
}
