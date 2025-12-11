import util.Util;

import java.util.*;

public class Day11 {

    public static void main(String[] args) {
        Util.verifySubmission();
        var input = Util.readStrings();
        //Util.submitPart1(calc1(input));
        Util.submitPart2(calc2(input));
    }

    private static long calc2(List<String> input) {
        var map = parseMap(input);
        var cache = new HashMap<State, Long>();
        return calc2(new State("svr", false, false), map, cache);
    }

    private static long calc2(State state, HashMap<String, Node> map, Map<State, Long> cache) {
        if (cache.containsKey(state)) {
            return cache.get(state);
        }
        if (state.node.equals("out")) {
            return state.seenDAC && state.seenFFT ? 1 : 0;
        }
        long sum = 0;
        for (String neigh : map.get(state.node).out) {
            var seenDAG = state.seenDAC || neigh.equals("dac");
            var seenFFT = state.seenFFT || neigh.equals("fft");
            sum += calc2(new State(neigh, seenDAG, seenFFT), map, cache);
        }
        cache.put(state, sum);
        return sum;
    }

    private static int calc1(List<String> input) {
        var map = parseMap(input);
        int count = 0;
        var queue = new LinkedList<Node>();
        queue.add(map.get("you"));
        while (!queue.isEmpty()) {
            var node = queue.poll();
            for (var neigh : node.out) {
                if (Objects.equals(neigh, "out")) {
                    ++count;
                } else {
                    queue.add(map.get(neigh));
                }
            }
        }
        return count;
    }

    private static HashMap<String, Node> parseMap(List<String> input) {
        var map = new HashMap<String, Node>();
        for (String row : input) {
            var split = row.split(": ");
            var name = split[0];
            var out = Arrays.stream(split[1].split(" ")).toList();
            map.put(name, new Node(name, out));
        }
        return map;
    }

    private record Node(String name, List<String> out) {
    }

    private record State(String node, boolean seenDAC, boolean seenFFT) {
    }
}
