import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2712 : 미국 스타일

/**
 * Example
 * 5
 * 1 kg
 * 2 l
 * 7 lb
 * 3.5 g
 * 0 l
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2712_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        Map<String, String> word = new HashMap<>(){{
            put("kg", "lb");
            put("lb", "kg");
            put("l", "g");
            put("g", "l");
        }};

        Map<String, Function<Double, Double>> calculate = new HashMap<>(){{
            put("kg", i -> (i * 2.2046));
            put("lb", i -> (i * 0.4536));
            put("l", i -> (i * 0.2642));
            put("g", i -> (i * 3.7854));
        }};

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            double number = Double.parseDouble(st.nextToken());
            String dan = st.nextToken();

            Double cal = Math.round(calculate.get(dan).apply(number) * 10000) / 10000d;
            System.out.println(String.format("%.4f", cal) + " " + word.get(dan));
        }
    }
}