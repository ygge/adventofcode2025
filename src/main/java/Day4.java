import util.Pos;
import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Day4 {

    public static void main(String[] args) {
        var input = Util.readBoard('@', '.');
        Util.submitPart1(calc1(input));
        Util.submitPart2(calc2(input));
    }

    private static int calc2(boolean[][] board) {
        int sum = 0;
        while (true) {
            var rolls = findRolls(board);
            if (rolls.isEmpty()) {
                break;
            }
            for (Pos roll : rolls) {
                board[roll.y][roll.x] = false;
            }
            sum += rolls.size();
        }
        return sum;
    }

    private static int calc1(boolean[][] board) {
        return findRolls(board).size();
    }

    private static List<Pos> findRolls(boolean[][] board) {
        var rolls = new ArrayList<Pos>();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x]) {
                    int count = 0;
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            if ((dy != 0 || dx != 0) && y + dy >= 0 && y + dy < board.length && x + dx >= 0 && x + dx < board[y].length && board[y + dy][x + dx]) {
                                ++count;
                            }
                        }
                    }
                    if (count < 4) {
                        rolls.add(new Pos(x, y));
                    }
                }
            }
        }
        return rolls;
    }
}
