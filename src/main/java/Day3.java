import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Day3 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(calc1(input));
        Util.submitPart2(calc2(input));
    }

    private static long calc2(List<String> input) {
        long sum = 0;
        for (String row : input) {
            var current = new ValueIndex(0, -1);
            long v = 0;
            for (int i = 0; i < 12; ++i) {
                current = max(row, current.index, 11-i);
                v = v*10 + current.value;
            }
            sum += v;
        }
        return sum;
    }

    private static int calc1(List<String> input) {
        int sum = 0;
        for (String row : input) {
            var a = max(row, -1, 1);
            var b = max(row, a.index, 0);
            sum += a.value*10 + b.value;
        }
        return sum;
    }

    private static ValueIndex max(String row, int index, int left) {
        var max = new ValueIndex(0, index);
        for (int i = index + 1; i < row.length() - left; i++) {
            var v = row.charAt(i)-'0';
            if (v > max.value) {
                max = new ValueIndex(v, i);
            }
        }
        return max;
    }

    private record ValueIndex(int value, int index) {
    }
}
