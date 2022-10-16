import java.util.*;
import java.io.*;

// 조별과제를 하려는 조장이 사라졌다.

/**
 * 예제
 * 12
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_15727_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        double a = Double.parseDouble(br.readLine());

        System.out.println((int) Math.ceil(a / 5));
    }
}
