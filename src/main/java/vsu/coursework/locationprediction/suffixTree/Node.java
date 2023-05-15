package vsu.coursework.locationprediction.suffixTree;

import vsu.coursework.locationprediction.dto.Point;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    private double probability;
    private Point coordinate;
    private Node parent;
    private Map<Point, Node> children;

    public Node() {
        this.coordinate = null;
        this.parent = null;
        this.children = new HashMap<>();
        this.probability = 0.0;
    }

    public Node(Node node) {
        this.probability = node.probability;
        this.coordinate = node.coordinate;
        this.parent = node.parent;
        this.children = new HashMap<>(node.children);
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
        Point coord = coordinates.get(0);
        Node child = children.get(coord);
        if (child == null) {
            child = new Node(coord, this);
            children.put(coord, child);
        }
        child.probability += 1.0;
        child.add(coordinates.subList(1, coordinates.size()));
    }

    public void remove(Node node) {
        if (node == null) {
            return;
        }
        Point coord = node.getCoordinate();
        Node child = children.get(coord);
        if (child != null && child.equals(node)) {
            child.setParent(null);
            children.remove(coord);
        }
    }

    public void remove(List<Point> coordinates) {
        if (coordinates.isEmpty()) {
            return;
        }
        Point coord = coordinates.get(0);
        Node child = children.get(coord);
        if (child == null) {
            return;
        }
        child.remove(coordinates.subList(1, coordinates.size()));
        if (child.children.isEmpty()) {
            children.remove(coord);
        }
    }

    public Node getParent() {
        return parent;
    }

    public Collection<Node> getChildren() {
        return children.values();
    }

    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append("\t");
        }
        sb.append(coordinate);
        sb.append(" (prob = ");
        sb.append(probability);
        sb.append(")\n");
        for (Node child : children.values()) {
            sb.append(child.toString(depth + 1));
        }
        return sb.toString();
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildren(Map<Point, Node> children) {
        this.children = children;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public Node clone() {
        Node clone = new Node();
        clone.probability = this.probability;
        clone.coordinate = this.coordinate;

        for (Node child : this.children.values()) {
            Node childClone = child.clone();
            childClone.setParent(clone);
            clone.children.put(childClone.getCoordinate(), childClone);
        }

        return clone;
    }
}
