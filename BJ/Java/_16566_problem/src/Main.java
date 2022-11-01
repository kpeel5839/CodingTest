import java.util.*;
import java.io.*;
import java.util.function.Function;

// 16566 : 카드 게임

/**
 * 예제
 * 10 7 5
 * 2 5 3 7 8 4 9
 * 4 1 1 3 8
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_16566_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;
    }
}