import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.BiFunction;

// 2985 : 세 수

/**
 * Example
 * 5 15 3
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2985_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] n = new int[3];
        for (int i = 0; i < n.length; i++) {
            n[i] = Integer.parseInt(st.nextToken());
        }

        BiFunction<Integer, Integer, Integer> add = Integer::sum;
        BiFunction<Integer, Integer, Integer> sub = (o1, o2) -> o1 - o2;
        BiFunction<Integer, Integer, Integer> mul = (o1, o2) -> o1 * o2;
        BiFunction<Integer, Integer, Integer> div = (o1, o2) -> o1 / o2;

        Map<Integer, BiFunction<Integer, Integer, Integer>> map = Map.ofEntries(
                Map.entry(0, add),
                Map.entry(1, sub),
                Map.entry(2, mul),
                Map.entry(3, div)
        );

        Map<Integer, String> map2 = Map.ofEntries(
                Map.entry(0, "+"),
                Map.entry(1, "-"),
                Map.entry(2, "*"),
                Map.entry(3, "/")
        );

        Loop:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0) {
                    if (map.get(j).apply(n[0], n[1]) == n[2]) {
                        System.out.println(n[0] + map2.get(j) + n[1] + "=" + n[2]);
                        break Loop;
                    }
                } else {
                    if (map.get(j).apply(n[1], n[2]) == n[0]) {
                        System.out.println(n[0] + "=" + n[1] + map2.get(j) + n[2]);
                        break Loop;
                    }
                }
            }
        }
    }
}