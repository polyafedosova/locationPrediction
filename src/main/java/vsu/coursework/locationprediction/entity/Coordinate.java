package vsu.coursework.locationprediction.entity;

import org.locationtech.jts.geom.Geometry;
import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "coordinate")
public class Coordinate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Geometry coordinate;
    private String name;
}
