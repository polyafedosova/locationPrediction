package vsu.coursework.locationprediction.entity;

import org.locationtech.jts.geom.LineString;
import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "path")
public class Path implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "path", nullable = false, columnDefinition = "geometry(LineString, 4326)")
    private LineString path;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person")
    private Person person;
}