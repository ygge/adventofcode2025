import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(solve1(input));
    }

    private static long solve1(List<String> input) {
        var presents = new ArrayList<Present>();
        int index = 0;
        int presentIndex = 0;
        var currentPresent = new ArrayList<String>();
        for (; index < input.size(); index++) {
            String row = input.get(index);
            if (row.contains(": ")) {
                break;
            }
            if (row.isEmpty()) {
                presents.add(new Present(presentIndex, currentPresent));
                currentPresent.clear();
            }
            if (row.contains(":")) {
                presentIndex = Integer.parseInt(row.substring(0, row.indexOf(":")));
            } else {
                currentPresent.add(row);
            }
        }
        var ok = 0;
        for (; index < input.size(); index++) {
            String row = input.get(index);
            var split = row.split(": ");
            var sizes = split[0].split("x");
            var toFit = Arrays.stream(split[1].split(" "))
                    .map(Integer::parseInt)
                    .toList();
            var board = new boolean[Integer.parseInt(sizes[1])][Integer.parseInt(sizes[0])];
            if (isOk(board, toFit, presents)) {
                ++ok;
            }
        }
        return ok;
    }

    private static boolean isOk(boolean[][] board, List<Integer> toFit, List<Present> presents) {
        var size = board.length * board[0].length;
        int presentSize = 0;
        for (int i = 0; i < toFit.size(); ++i) {
            presentSize += toFit.get(i) * 9;
        }
        return presentSize <= size;
    }

    private static class Present {
        private final int index, size;
        private final boolean[][] shape;

        public Present(int presentIndex, List<String> currentPresent) {
            index = presentIndex;
            shape = new boolean[currentPresent.size()][currentPresent.getFirst().length()];
            int s = 0;
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[i].length; j++) {
                    shape[i][j] = currentPresent.get(i).charAt(j) == '#';
                    if (shape[i][j]) {
                        ++s;
                    }
                }
            }
            size = s;
        }
    }
}
