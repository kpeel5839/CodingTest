import java.util.*;
import java.io.*;

// 1598 : 꼬리를 무는 숫자나열

/**
 * Example
 * 11 33
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1598_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        double first = Double.parseDouble(st.nextToken());
        double second = Double.parseDouble(st.nextToken());

        int widthDiff = (int) Math.abs(Math.ceil(first / 4) - Math.ceil(second / 4));
        int firstHeight = first - (Math.floor(first / 4) * 4) == 0 ? 4 : (int) (first - (Math.floor(first / 4) * 4));
        int secondHeight = second - (Math.floor(second / 4) * 4) == 0 ? 4 : (int) (second - (Math.floor(second / 4) * 4));

        System.out.println(widthDiff + Math.abs(firstHeight - secondHeight));
        Set<Integer>[] hash = new HashSet[3];
        Set<Integer> set2 = new HashSet<>();
        set2.add(1);
        Set<Integer> set = new HashSet<>(set2);
    }
}
