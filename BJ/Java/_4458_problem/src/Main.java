import java.util.*;
import java.io.*;

// 4458 : 첫 글자를 대문자

/**
 * Example
 * 5
 * powdered Toast Man
 * skeletor
 * Electra Woman and Dyna Girl
 * she-Ra Princess of Power
 * darth Vader
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_4458_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String s = br.readLine();

            System.out.print(Character.toUpperCase(s.charAt(0)));
            System.out.println(s.substring(1));
        }
    }
}