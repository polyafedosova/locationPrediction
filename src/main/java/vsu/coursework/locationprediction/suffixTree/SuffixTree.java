package vsu.coursework.locationprediction.suffixTree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import vsu.coursework.locationprediction.entity.Point;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class SuffixTree {
    @JsonProperty("root")
    private Node root;

    public SuffixTree(List<List<Point>> coordinates) {
        root = new Node();
        for (List<Point> coords : coordinates) {
            add(coords);
        }
    }

    public void add(List<Point> coordinates) {
        for (int i = 0; i < coordinates.size(); i++) {
            List<Point> suffix = coordinates.subList(i, coordinates.size());
            root.add(suffix);
        }
    }

    public void remove(List<Point> coordinates) {
        for (int i = 0; i < coordinates.size(); i++) {
            List<Point> suffix = coordinates.subList(i, coordinates.size());
            root.remove(suffix);
        }
    }

    public List<Node> search(List<Point> query) {
        List<Node> nodes = Collections.singletonList(root);

        for (Point coordinate : query) {
            List<Node> newNodes = new ArrayList<>();

            double maxProbability = 0.0;

            for (Node node : nodes) {
                for (Node child : node.getChildren()) {
                    if (child.getCoordinate().equals(coordinate)) {
                        for (Node childChild : child.getChildren()) {
                            double probability = childChild.getProbability();
                            if (probability > maxProbability) {
                                newNodes.clear();
                                newNodes.add(childChild);
                                maxProbability = probability;
                            } else if (probability == maxProbability) {
                                newNodes.add(childChild);
                            }
                        }
                    }
                }
            }

            nodes = newNodes;

            if (nodes.isEmpty()) {
                break;
            }
        }

        return nodes;
    }

    public String printTree() {
        return root.toString(0);
    }

    public void toDot(PrintWriter writer) {
        writer.println("digraph {");

        int id = 0;
        Map<Node, Integer> nodeIds = new HashMap<>();
        nodeIds.put(root, id++);

        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            int nodeId = nodeIds.get(node);

            writer.printf("  %d[label=\"%s\"];\n", nodeId, node.getCoordinate());

            for (Node child : node.getChildren()) {
                int childId = id++;
                nodeIds.put(child, childId);

                writer.printf("  %d -> %d[label=\"%.2f\"];\n", nodeId, childId, child.getProbability());

                stack.push(child);
            }
        }

        writer.println("}");
    }


    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public static void viz(SuffixTree suffixTree) throws IOException {
        String dotFilePath = "C:\\Users\\Polya\\IdeaProjects\\locationPrediction\\src\\main\\java\\vsu\\coursework\\locationprediction\\suffixTree\\vizualization\\tree.dot";
        String imageFilePath = "C:\\Users\\Polya\\IdeaProjects\\locationPrediction\\src\\main\\java\\vsu\\coursework\\locationprediction\\suffixTree\\vizualization\\tree.png";

        try (PrintWriter writer = new PrintWriter(dotFilePath)) {
            suffixTree.toDot(writer);
        }
        try {
            Process p = Runtime.getRuntime().exec("dot -Tpng " + dotFilePath + " -o " + imageFilePath);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error running dot command: " + e.getMessage());
        }
    }
}
