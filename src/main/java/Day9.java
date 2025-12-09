import util.Pos;
import util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Day9 {

    public static void main(String[] args) {
        var input = Util.readIntLists();
        Util.submitPart1(solve1(input));
        Util.submitPart2(solve2(input));
    }

    private static long solve2(List<List<Integer>> input) {
        int[] xList = new int[input.size()];
        int[] yList = new int[input.size()];
        var posList = new ArrayList<Pos>();
        for (int i = 0; i < input.size(); i++) {
            List<Integer> row = input.get(i);
            xList[i] = row.get(0);
            yList[i] = row.get(1);
            posList.add(new Pos(row.get(0), row.get(1)));
        }
        var polygon = new Polygon(xList, yList, xList.length);
        long max = 0;
        for (int i = 0; i < posList.size(); i++) {
            var posA = posList.get(i);
            for (int j = i + 1; j < posList.size(); j++) {
                var posB = posList.get(j);
                int minX = Math.min(posA.x, posB.x);
                int minY = Math.min(posA.y, posB.y);
                int maxX = Math.max(posA.x, posB.x);
                int maxY = Math.max(posA.y, posB.y);
                if (polygon.contains(new Rectangle(minX + 1, minY + 1, maxX - minX - 1, maxY - minY -1 ))) {
                    long area = (long) (Math.abs(posA.x - posB.x) + 1) * (Math.abs(posA.y - posB.y) + 1);
                    if (area > max) {
                        max = area;
                    }
                }
            }
        }
        return max;
    }

    private static long solve1(List<List<Integer>> input) {
        var posList = input.stream()
                .map(a -> new Pos(a.getFirst(), a.get(1)))
                .toList();
        long max = 0;
        for (int i = 0; i < posList.size(); i++) {
            var posA = posList.get(i);
            for (int j = i + 1; j < posList.size(); j++) {
                var posB = posList.get(j);
                long area = (long) (Math.abs(posA.x - posB.x) + 1) * (Math.abs(posA.y - posB.y) + 1);
                if (area > max) {
                    max = area;
                }
            }
        }
        return max;
    }
}
