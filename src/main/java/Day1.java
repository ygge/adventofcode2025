import util.Util;

import java.util.List;

public class Day1 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        part1(input);
        part2(input);
    }

    private static void part2(List<String> input) {
        int v = 50;
        int count = 0;
        for (String row : input) {
            var add = row.charAt(0) == 'R';
            int num = Integer.parseInt(row.substring(1));
            for (int i = 0; i < num; i++) {
                v += (add ? 1 : -1);
                if (v == -1) {
                    v = 99;
                }
                if (v == 100) {
                    v = 0;
                }
                if (v == 0) {
                    ++count;
                }
            }
        }
        Util.submitPart2(count);
    }

    private static void part1(List<String> input) {
        int v = 50;
        int count = 0;
        for (String row : input) {
            var add = row.charAt(0) == 'R';
            int num = Integer.parseInt(row.substring(1));
            v += (add ? num : -num);
            while (v < 0) {
                v += 100;
            }
            while (v >= 100) {
                v -= 100;
            }
            if (v == 0) {
                ++count;
            }
        }
        Util.submitPart1(count);
    }
}
