import util.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Day7 {

    public static void main(String[] args) {
        var board = Util.readBoard();
        Util.submitPart1(solve1(board));
        Util.submitPart2(solve2(board));
    }

    private static long solve2(char[][] board) {
        int start = -1;
        for (int x = 0; x < board[0].length; x++) {
            if (board[0][x] == 'S') {
                start = x;
                break;
            }
        }
        if (start == -1) {
            throw new IllegalArgumentException("No start found");
        }
        var beams = new HashMap<Integer, Long>();
        beams.put(start, 1L);
        int y = 0;
        while (true) {
            var newBeams = new HashMap<Integer, Long>();
            for (Map.Entry<Integer, Long> entry : beams.entrySet()) {
                if ((entry.getKey() < 0 || entry.getKey() == board[0].length)) {
                    newBeams.put(entry.getKey(), entry.getValue());
                }
                if (board[y][entry.getKey()] == '^') {
                    newBeams.put(entry.getKey() - 1, newBeams.getOrDefault(entry.getKey() - 1, 0L) + entry.getValue());
                    newBeams.put(entry.getKey() + 1, newBeams.getOrDefault(entry.getKey() + 1, 0L) + entry.getValue());
                } else {
                    newBeams.put(entry.getKey(), newBeams.getOrDefault(entry.getKey(), 0L) + entry.getValue());
                }
            }
            beams = newBeams;
            ++y;
            if (y == board.length - 1) {
                break;
            }
        }
        return beams.values().stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    private static int solve1(char[][] board) {
        int start = -1;
        for (int x = 0; x < board[0].length; x++) {
            if (board[0][x] == 'S') {
                start = x;
                break;
            }
        }
        if (start == -1) {
            throw new IllegalArgumentException("No start found");
        }
        var beams = new HashSet<Integer>();
        beams.add(start);
        int y = 0;
        int count = 0;
        while (true) {
            var newBeams = new HashSet<Integer>();
            for (int x : beams) {
                if (x < 0 || x == board[0].length) {
                    continue;
                }
                if (board[y][x] == '^') {
                    newBeams.add(x - 1);
                    newBeams.add(x + 1);
                    ++count;
                } else {
                    newBeams.add(x);
                }
            }
            beams = newBeams;
            ++y;
            if (y == board.length - 1) {
                break;
            }
        }
        return count;
    }
}
