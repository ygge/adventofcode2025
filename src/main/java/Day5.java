import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(solve1(input));
        Util.submitPart2(solve2(input));
    }

    private static long solve2(List<String> input) {
        List<Range> ranges = new ArrayList<>();
        int index = 0;
        for (; index < input.size(); index++) {
            var row = input.get(index);
            if (row.isEmpty()) {
                break;
            }
            var range = Range.of(row);
            var overlapping = ranges.stream()
                    .filter(r -> isOverlapping(r, range))
                    .toList();
            if (overlapping.isEmpty()) {
                ranges.add(range);
            } else {
                var newRange = merge(overlapping, range);
                ranges = ranges.stream()
                        .filter(r -> !overlapping.contains(r))
                        .collect(Collectors.toList());
                ranges.add(newRange);
            }
        }
        long count = 0;
        for (Range range : ranges) {
            count += range.end - range.start + 1;
        }
        return count;
    }

    private static Range merge(List<Range> overlapping, Range range) {
        var start = Math.min(range.start, overlapping.stream().map(r -> r.start).min(Day5::compare).orElseThrow());
        var end = Math.max(range.end, overlapping.stream().map(r -> r.end).max(Day5::compare).orElseThrow());
        return new Range(start, end);
    }

    private static int compare(long a, long b) {
        if (a == b) {
            return 0;
        }
        return a < b ? -1 : 1;
    }

    private static boolean isOverlapping(Range r1, Range r2) {
        return (r1.start <= r2.start && r1.end >= r2.start)
                || (r2.start <= r1.start && r2.end >= r1.start);
    }

    private static int solve1(List<String> input) {
        var ranges = new ArrayList<Range>();
        int index = 0;
        for (; index < input.size(); index++) {
            var row = input.get(index);
            if (row.isEmpty()) {
                break;
            }
            ranges.add(Range.of(row));
        }
        int count = 0;
        for (++index; index < input.size(); index++) {
            long v = Long.parseLong(input.get(index));
            if (ranges.stream().anyMatch(r -> r.start <= v && r.end >= v)) {
                ++count;
            }
        }
        return count;
    }

    private record Range(long start, long end) {
        public static Range of(String row) {
            var s = row.split("-");
            return new Range(Long.parseLong(s[0]), Long.parseLong(s[1]));
        }
    }
}
