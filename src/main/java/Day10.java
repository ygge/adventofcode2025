import util.Util;

import java.util.*;

public class Day10 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(solve1(input));
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
            int ans = solve2(goal, buttons, 0, Integer.MAX_VALUE);
            if (ans == Integer.MAX_VALUE) {
                throw new RuntimeException("No solution found!");
            }
            sum += ans;
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

    private static int solve2(List<Integer> state, List<List<Integer>> buttons, int steps, int best) {
        if (steps >= best) {
            return best;
        }
        var min = state.stream().mapToInt(Integer::intValue).min().orElseThrow();
        if (min < 0) {
            return best;
        }
        var max = state.stream().mapToInt(Integer::intValue).max().orElseThrow();
        if (max == 0) {
            return steps;
        }
        if (steps + max >= best) {
            return best;
        }
        var usableButtons = buttons.stream()
                .filter(button -> button.stream().allMatch(index -> state.get(index) > 0))
                .toList();
        if (usableButtons.size() < buttons.size()) {
            return solve2(state, usableButtons, steps, best);
        }
        for (int i = 0; i < state.size(); ++i) {
            final int index = i;
            if (state.get(i) > 0 && buttons.stream().noneMatch(button -> button.contains(index))) {
                return best;
            }
        }
        for (int i = 0; i < state.size(); ++i) {
            final int ii = i;
            for (int j = 0; j < state.size(); ++j) {
                final int jj = j;
                if (state.get(i) > state.get(j)) {
                    var goodButtons = buttons.stream().filter(button -> button.contains(ii) && !button.contains(jj)).toList();
                    if (goodButtons.isEmpty()) {
                        return best;
                    } else if (goodButtons.size() == 1) {
                        return solve2(press(state, goodButtons.getFirst()), buttons, steps + 1, best);
                    }
                }
            }
        }
        for (int i = 0; i < state.size(); ++i) {
            final int ii = i;
            if (state.get(i) > 0) {
                var goodButtons = buttons.stream().filter(button -> button.contains(ii)).toList();
                if (goodButtons.size() == 1) {
                    return solve2(press(state, goodButtons.getFirst()), buttons, steps + 1, best);
                }
            }
        }
        for (int i = 0; i < state.size(); ++i) {
            final int ii = i;
            for (int j = 0; j < state.size(); ++j) {
                final int jj = j;
                if (i != j && state.get(i) > state.get(j)) {
                    if (buttons.stream().allMatch(button -> !button.contains(ii) || button.contains(jj))) {
                        var requiredButtons = buttons.stream().filter(button -> button.contains(ii) || !button.contains(jj)).toList();
                        if (requiredButtons.size() < buttons.size()) {
                            return solve2(state, requiredButtons, steps, best);
                        }
                    }
                }
            }
        }
        var minButtons = buttons;
        for (int i = 0; i < state.size(); ++i) {
            final int ii = i;
            if (state.get(i) > 0) {
                var usefulButtons = buttons.stream().filter(button -> button.contains(ii)).toList();
                if (usefulButtons.size() < minButtons.size()) {
                    minButtons = usefulButtons;
                }
            }
        }
        var childButtons = new ArrayList<>(buttons);
        for (List<Integer> button : minButtons) {
            best = solve2(press(state, button), childButtons, steps + 1, best);
            childButtons.remove(button);
        }
        return best;
    }

    private static List<Integer> press(List<Integer> state, List<Integer> button) {
        var newState = new ArrayList<Integer>();
        for (int i = 0; i < state.size(); ++i) {
            newState.add(state.get(i) - (button.contains(i) ? 1 : 0));
        }
        return newState;
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
