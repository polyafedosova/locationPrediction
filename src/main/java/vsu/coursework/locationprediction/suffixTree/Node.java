package vsu.coursework.locationprediction.suffixTree;

import com.fasterxml.jackson.annotation.*;
import vsu.coursework.locationprediction.entity.Point;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
    @JsonProperty("probability")
    private double probability;
    @JsonProperty("coordinate")
    private Point coordinate;
    @JsonIgnore
    private Node parent;
    @JsonProperty("children")
    private Map<Point, Node> children;

    public Node() {
        this.coordinate = null;
        this.parent = null;
        this.children = new HashMap<>();
        this.probability = 0.0;
    }

    public Node(Point coordinate, Node parent) {
        this.coordinate = coordinate;
        this.parent = parent;
        this.children = new HashMap<>();
        this.probability = 0.0;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public void add(List<Point> coordinates) {
        if (coordinates.isEmpty()) {
            return;
        }
        Point coordinate = coordinates.get(0);
        Node child = children.get(coordinate);
        if (child == null) {
            child = new Node(coordinate, this);
            children.put(coordinate, child);
        }
        child.probability += 1.0;
        child.add(coordinates.subList(1, coordinates.size()));
    }

    public void remove(List<Point> coordinates) {
        if (coordinates.isEmpty()) {
            return;
        }
        Point coordinate = coordinates.get(0);
        Node child = children.get(coordinate);
        if (child == null) {
            return;
        }
        child.remove(coordinates.subList(1, coordinates.size()));
        if (child.children.isEmpty()) {
            children.remove(coordinate);
        }
    }

    public Collection<Node> getChildren() {
        return children.values();
    }

    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t".repeat(Math.max(0, depth)));
        sb.append(coordinate);
        sb.append(" (prob = ");
        sb.append(probability);
        sb.append(")\n");
        for (Node child : children.values()) {
            sb.append(child.toString(depth + 1));
        }
        return sb.toString();
    }
    public double getProbability() {
        return probability;
    }
}
