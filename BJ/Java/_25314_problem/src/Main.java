import java.util.*;
import java.io.*;

// 25314 : 코딩은 체육과목입니다.

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_25314_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N / 4; i++) {
            System.out.print("long ");
        }

        System.out.println("int");
    }
}
