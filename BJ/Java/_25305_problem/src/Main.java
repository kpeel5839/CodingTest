import java.util.*;
import java.io.*;

// 25305 : 커트라인

/**
 * 예제
 * 5 2
 * 100 76 85 93 98
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_25305_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        Integer[] arr = new Integer[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr, (o1, o2) -> -1 * (o1 - o2));

        System.out.println(arr[K - 1]);
    }
}
