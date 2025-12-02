import util.Util;

public class Day2 {

    public static void main(String[] args) {
        var input = Util.readString();
        Util.submitPart1(solve1(input));
        Util.submitPart2(solve2(input));
    }

    private static long solve2(String input) {
        var split = input.split(",");
        long sum = 0;
        for (String row : split) {
            var range = row.split("-");
            var from = Long.parseLong(range[0]);
            var to = Long.parseLong(range[1]);
            for (long i = from; i <= to; i++) {
                var str = Long.toString(i);
                for (int j = 1; j <= str.length()/2; j++) {
                    if (str.length() % j != 0) {
                        continue;
                    }
                    boolean ok = true;
                    var s = str.substring(0, j);
                    for (int k = j; k+j <= str.length(); k += j) {
                        if (!str.substring(k, k+j).equals(s)) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        sum += i;
                        break;
                    }
                }
            }
        }
        return sum;
    }

    private static long solve1(String input) {
        var split = input.split(",");
        long sum = 0;
        for (String row : split) {
            var range = row.split("-");
            var from = Long.parseLong(range[0]);
            var to = Long.parseLong(range[1]);
            for (long i = from; i <= to; i++) {
                var str = Long.toString(i);
                if (str.length()%2 == 0 && str.substring(0, str.length() / 2).equals(str.substring(str.length() / 2))) {
                    sum += i;
                }
            }
        }
        return sum;
    }
}
