import util.MultiDimPos;
import util.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day8 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readStrings();
        Util.submitPart1(solve1(input, 1000));
    }

    private static int solve1(List<String> input, int turns) {
        var boxes = input.stream()
                .map(row -> row.split(","))
                .map(split -> new Box(new MultiDimPos(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]))))
                .toList();
        var nextCircuit = 1;
        var seen = new HashSet<Connection>();
        for (int i = 0; i < turns; ++i) {
            double closest = Double.MAX_VALUE;
            var bestA = -1;
            var bestB = -1;
            for (int j = 0; j < boxes.size(); ++j) {
                var box1 = boxes.get(j);
                for (int k = j + 1; k < boxes.size(); ++k) {
                    var box2 = boxes.get(k);
                    if (!seen.contains(new Connection(box1.pos, box2.pos))) {
                        var dist = box1.pos.straightDist(box2.pos);
                        if (dist < closest) {
                            closest = dist;
                            bestA = j;
                            bestB = k;
                        }
                    }
                }
            }
            var box1 = boxes.get(bestA);
            var box2 = boxes.get(bestB);
            seen.add(new Connection(box1.pos, box2.pos));
            if (box1.circuit == null && box2.circuit == null) {
                box1.circuit = nextCircuit;
                box2.circuit = nextCircuit;
                ++nextCircuit;
            } else if (box1.circuit != null && box2.circuit != null) {
                var n = Math.min(box1.circuit, box2.circuit);
                var o = Math.max(box1.circuit, box2.circuit);
                for (var box : boxes) {
                    if (box.circuit != null && box.circuit == o) {
                        box.circuit = n;
                    }
                }
            } else if (box1.circuit != null) {
                box2.circuit = box1.circuit;
            } else {
                box1.circuit = box2.circuit;
            }
        }
        var count = new HashMap<Integer, Integer>();
        for (Box box : boxes) {
            if (box.circuit != null) {
                count.put(box.circuit, count.getOrDefault(box.circuit, 0) + 1);
            }
        }
        var sorted = count.values().stream()
                .sorted((a, b) -> b - a)
                .toList();
        System.out.println(sorted);
        return sorted.get(0) * sorted.get(1) * sorted.get(2);
    }

    private record Connection(MultiDimPos pos1, MultiDimPos pos2) {
    }

    private static class Box {
        private final MultiDimPos pos;
        private Integer circuit;

        private Box(MultiDimPos pos) {
            this.pos = pos;
        }
    }
}
