import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(solve1(input));
        Util.submitPart2(solve2(input));
    }

    private static long solve2(List<String> input) {
        long sum = 0;
        int index = input.getFirst().length() - 1;
        List<Long> values = new ArrayList<>();
        while (true) {
            int num = 0;
            for (int i = 0; i < input.size() - 1; ++i) {
                var c = input.get(i).charAt(index);
                if (c != ' ') {
                    num = 10 * num + c - '0';
                }
            }
            values.add((long)num);
            var operation = input.getLast().charAt(index);
            --index;
            if (operation == ' ') {
                continue;
            }
            if (operation == '+') {
                sum += values.stream().reduce(0L, Long::sum);
            } else if (operation == '*') {
                sum += values.stream().reduce(1L, (a, b) -> a * b);
            }
            values.clear();
            if (index < 0) {
                break;
            }
            --index;
        }
        return sum;
    }

    private static long solve1(List<String> input) {
        List<List<Integer>> numbers = new ArrayList<>();
        for (int i = 0; i < input.size() - 1; i++) {
            numbers.add(Arrays.stream(input.get(i).split(" +"))
                    .map(Integer::parseInt)
                    .toList());
        }
        var operations = Arrays.stream(input.get(input.size() - 1).split(" +"))
                .map(s -> s.equals("+"))
                .toList();
        long sum = 0;
        for (int i = 0; i < numbers.get(0).size(); i++) {
            var operation = operations.get(i);
            long r = operation ? 0 : 1;
            for (int j = 0; j < numbers.size(); ++j) {
                var v = numbers.get(j).get(i);
                if (operation) {
                    r += v;
                } else {
                    r *= v;
                }
            }
            sum += r;
        }
        return sum;
    }
}
