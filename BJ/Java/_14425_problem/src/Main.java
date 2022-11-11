import java.util.*;
import java.io.*;

// 14425 : 문자열 집합

/**
 * 예제
 * 5 11
 * baekjoononlinejudge
 * startlink
 * codeplus
 * sundaycoding
 * codingsh
 * baekjoon
 * codeplus
 * codeminus
 * startlink
 * starlink
 * sundaycoding
 * codingsh
 * codinghs
 * sondaycoding
 * startrink
 * icerink
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_14425_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int cnt = 0;
        Set<String> set = new HashSet<>();

        for (int i = 0; i < N; i++) {
            set.add(br.readLine());
        }

        for (int i = 0; i < M; i++) {
            if (set.contains(br.readLine())) {
                cnt++;
            }
        }

        System.out.println(cnt);
    }
}
