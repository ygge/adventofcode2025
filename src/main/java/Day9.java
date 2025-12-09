import util.Pos;
import util.Util;

import java.util.Arrays;
import java.util.List;

public class Day9 {

    public static void main(String[] args) {
        Util.verifySubmission();
        //var input = Util.readIntLists();
        var input = Arrays.asList(
                Arrays.asList(7, 1),
                Arrays.asList(11, 1),
                Arrays.asList(11, 7),
                Arrays.asList(9, 7),
                Arrays.asList(9, 5),
                Arrays.asList(2, 5),
                Arrays.asList(2, 3),
                Arrays.asList(7, 3));
        //Util.submitPart1(solve1(input));
        Util.submitPart2(solve2(input));
    }

    private static long solve2(List<List<Integer>> input) {
        var posList = input.stream()
                .map(a -> new Pos(a.getFirst(), a.get(1)))
                .toList();

    }

    private static long solve1(List<List<Integer>> input) {
        var posList = input.stream()
                .map(a -> new Pos(a.getFirst(), a.get(1)))
                .toList();
        long max = 0;
        for (int i = 0; i < posList.size(); i++) {
            var posA = posList.get(i);
            for (int j = i + 1; j < posList.size(); j++) {
                var posB = posList.get(j);
                long area = (long) (Math.abs(posA.x - posB.x) + 1) * (Math.abs(posA.y - posB.y) + 1);
                if (area > max) {
                    max = area;
                }
            }
        }
        return max;
    }
}
