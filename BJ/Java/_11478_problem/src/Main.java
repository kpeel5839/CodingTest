import java.util.*;
import java.io.*;

// 11478 : 서로 다른 부분 문자열

/**
 * 예제
 * ababc
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11478_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String problem = br.readLine();
        Set<String> count = new HashSet<>();

        for (int i = 1; i <= problem.length(); i++) {
            for (int j = 0; j + i <= problem.length(); j++) {
                count.add(problem.substring(j, j + i));
            }
        }

        System.out.println(count.size());
    }
}
