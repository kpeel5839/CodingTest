import java.util.*;
import java.io.*;

// 10815 : 숫자 카드

/**
 * 예제
 * 5
 * 6 3 2 10 -10
 * 8
 * 10 9 -5 2 3 4 5 -10
 */
public class Main {
    static int N;
    static int K;
    static Set<Integer> card = new HashSet<>();

    static int isContains(int findNumber) {
        if (card.contains(findNumber)) {
            return 1;
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_10815_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            card.add(Integer.parseInt(st.nextToken()));
        }

        K = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            bw.write(isContains(Integer.parseInt(st.nextToken())) + " ");
        }

        bw.flush();
        bw.close();
    }
}
