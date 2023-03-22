import java.util.*;
import java.io.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// 3029 : 경고

/**
 * Example
 * 20:00:00
 * 04:00:00
 */
public class Main {
    public static String convert(String number) {
        if (number.length() == 1) {
            number = "0" + number;
        }

        return number;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3029_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<Integer, Function<Integer, Integer>> f = Map.ofEntries(
                Map.entry(0, (o1) -> o1 * 3600),
                Map.entry(1, (o1) -> o1 * 60),
                Map.entry(2, (o1) -> o1)
        );

        int[] s = new int[3];
        for (int i = 0; i < 2; i++) {
            String[] b = br.readLine().split(":");
            for (int j = 0; j < 3; j++) {
                s[i] += f.get(j).apply(Integer.parseInt(b[j]));
            }
        }

        if (s[1] <= s[0]) {
            s[1] += 24 * 3600;
        }

        s[2] = s[1] - s[0];
        List<String> answer = new ArrayList<>();

        for (int i = 2; i != -1; i--) {
            answer.add(convert(Integer.toString(s[2] / (int) Math.pow(60, i))));
            s[2] %= Math.pow(60, i);
        }

        System.out.println(answer.stream().collect(Collectors.joining(":")));
    }
}