import java.util.*;
import java.io.*;

// 2743 : 단어 길이 재기

/**
 * 예제
 * pulljima
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2743_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(br.readLine().length());
    }
}
