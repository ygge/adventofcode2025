import util.Util;

import java.util.*;

public class Day10 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        //Util.submitPart1(solve1(input));
        Util.submitPart2(solve2(input));
    }

    private static long solve2(List<String> input) {
        long sum = 0;
        for (String row : input) {
            var split = row.split(" ");
            var goal = Arrays.stream(split[split.length - 1].substring(1, split[split.length - 1].length() - 1).split(","))
                    .map(Integer::parseInt)
                    .toList();
            var buttons = new ArrayList<List<Integer>>();
            for (int i = 1; i < split.length - 1; ++i) {
                var button = Arrays.stream(split[i].substring(1, split[i].length() - 1).split(","))
                        .map(Integer::parseInt)
                        .toList();
                buttons.add(button);
            }
            sum += run2(goal, buttons);
            System.out.println("total: " + sum);
        }
        return sum;
    }

    private static int solve1(List<String> input) {
        int sum = 0;
        for (String row : input) {
            var split = row.split(" ");
            var goal = new ArrayList<Boolean>();
            for (int i = 1; i < split[0].length() - 1; ++i) {
                goal.add(split[0].charAt(i) == '#');
            }
            var buttons = new ArrayList<List<Integer>>();
            for (int i = 1; i < split.length - 1; ++i) {
                var button = Arrays.stream(split[i].substring(1, split[i].length() - 1).split(","))
                        .map(Integer::parseInt)
                        .toList();
                buttons.add(button);
            }
            sum += run(goal, buttons);
        }
        return sum;
    }

    private static int run2(List<Integer> goal, List<List<Integer>> buttons) {
        var start = new ArrayList<Integer>();
        for (int i = 0; i < goal.size(); ++i) {
            start.add(0);
        }
        var seen = new HashSet<List<Integer>>();
        var queue = new LinkedList<State2>();
        queue.add(new State2(start, 0));
        while (!queue.isEmpty()) {
            var state = queue.poll();
            if (state.buttons.equals(goal)) {
                return state.turns;
            }
            System.out.println(state);
            for (List<Integer> button : buttons) {
                var newState = apply2(state.buttons, button);
                if (seen.add(newState)) {
                    boolean ok = true;
                    for (int i = 0; i < goal.size(); ++i) {
                        if (goal.get(i) < newState.get(i)) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        queue.add(new State2(newState, state.turns + 1));
                    }
                }
            }
        }
        throw new IllegalStateException("No solution!");
    }

    private static int run(List<Boolean> goal, List<List<Integer>> buttons) {
        var start = new ArrayList<Boolean>();
        for (int i = 0; i < goal.size(); ++i) {
            start.add(false);
        }
        var seen = new HashSet<List<Boolean>>();
        var queue = new LinkedList<State>();
        queue.add(new State(start, 0));
        while (!queue.isEmpty()) {
            var state = queue.poll();
            if (state.buttons.equals(goal)) {
                return state.turns;
            }
            if (!seen.add(state.buttons)) {
                continue;
            }
            for (List<Integer> button : buttons) {
                var newState = apply(state.buttons, button);
                queue.add(new State(newState, state.turns + 1));
            }
        }
        throw new IllegalStateException("No solution!");
    }

    private static List<Integer> apply2(List<Integer> buttons, List<Integer> button) {
        var list = new ArrayList<>(buttons);
        for (Integer change : button) {
            list.set(change, list.get(change) + 1);
        }
        return list;
    }

    private static List<Boolean> apply(List<Boolean> buttons, List<Integer> button) {
        var list = new ArrayList<>(buttons);
        for (Integer change : button) {
            list.set(change, !list.get(change));
        }
        return list;
    }

    private record State(List<Boolean> buttons, int turns) {
    }

    private record State2(List<Integer> buttons, int turns) {
    }
}
