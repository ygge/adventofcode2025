import util.MultiDimPos;
import util.Util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Day8 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(solve1(input, 1000));
        Util.submitPart2(solve2(input));
    }

    private static long solve2(List<String> input) {
        var boxes = parseBoxes(input);
        var connections = createConnections(boxes);
        int index = 0;
        while (true) {
            var conn = connections.get(index++);
            var box1 = conn.box1;
            var box2 = conn.box2;
            var old = box2.circuit;
            boolean allSame = true;
            for (var box : boxes) {
                if (box.circuit == old) {
                    box.circuit = box1.circuit;
                }
                if (box.circuit != box1.circuit) {
                    allSame = false;
                }
            }
            if (allSame) {
                return (long) box1.pos.dim[0] * box2.pos.dim[0];
            }
        }
    }

    private static int solve1(List<String> input, int turns) {
        var boxes = parseBoxes(input);
        var connections = createConnections(boxes);
        for (int i = 0; i < turns; ++i) {
            var conn = connections.get(i);
            var box1 = conn.box1;
            var box2 = conn.box2;
            var old = box2.circuit;
            for (var box : boxes) {
                if (box.circuit == old) {
                    box.circuit = box1.circuit;
                }
            }
        }
        var count = new HashMap<Integer, Integer>();
        for (Box box : boxes) {
            count.put(box.circuit, count.getOrDefault(box.circuit, 0) + 1);
        }
        var sorted = count.values().stream()
                .sorted((a, b) -> b - a)
                .toList();
        System.out.println(sorted);
        return sorted.get(0) * sorted.get(1) * sorted.get(2);
    }

    private static ArrayList<Connection> createConnections(List<Box> boxes) {
        var connections = new ArrayList<Connection>();
        for (int j = 0; j < boxes.size(); ++j) {
            var box1 = boxes.get(j);
            for (int k = j + 1; k < boxes.size(); ++k) {
                var box2 = boxes.get(k);
                var dist = box1.pos.straightDist(box2.pos);
                connections.add(new Connection(box1, box2, dist));
            }
        }
        connections.sort(Comparator.comparingDouble(c -> c.dist));
        return connections;
    }

    private static List<Box> parseBoxes(List<String> input) {
        var boxes = input.stream()
                .map(row -> row.split(","))
                .map(split -> new Box(new MultiDimPos(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]))))
                .toList();
        int index = 0;
        for (Box box : boxes) {
            box.circuit = ++index;
        }
        return boxes;
    }

    private record Connection(Box box1, Box box2, double dist) {
    }

    private static class Box {
        private final MultiDimPos pos;
        private int circuit;

        private Box(MultiDimPos pos) {
            this.pos = pos;
        }
    }
}
