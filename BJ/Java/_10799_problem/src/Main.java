import java.util.*;
import java.io.*;

// 10799 : 쇠 막대기

/**
 * Example
 * ()(((()())(())()))(())
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10799_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();
        Queue<Character> q = new LinkedList<>();
        int a = 0;

        for (int i = 0; i < s.length() - 1; i++) {
            char c1 = s.charAt(i);
            char c2 = s.charAt(i + 1);

            if ((c1 + "" + c2).equals("()")) {
                a += q.size();
                i += 1;
            } else {
                if (c1 == '(') {
                    q.add(c1);
                } else {
                    q.poll();
                    a++;
                }
            }
        }

        a += q.size() == 0 ? 0 : 1;
        System.out.println(a);
    }
}