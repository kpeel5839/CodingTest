import java.util.*;
import java.io.*;
import java.util.function.Function;

// 3025 : 돌 던지기

/**
 * 예제
 * 7 6
 * ......
 * ......
 * ...XX.
 * ......
 * ......
 * .XX...
 * ......
 * 6
 * 1
 * 4
 * 4
 * 6
 * 4
 * 4
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3025_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;
    }
}
