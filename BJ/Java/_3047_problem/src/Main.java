import java.util.*;
import java.io.*;

// 3047 : ABC

/**
 * Example
 * 6 4 2
 * CAB
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_3047_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] a = new int[3];

        for (int i = 0; i < 3; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(a);
        Map<Character, Integer> map = Map.of('A', 0, 'B', 1, 'C', 2);

        String b = br.readLine();
        for (int i = 0; i < b.length(); i++) {
            System.out.print(a[map.get(b.charAt(i))] + " ");
        }
    }
}