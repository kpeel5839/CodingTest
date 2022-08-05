import java.util.*;
import java.io.*;

// 17071 : 숨바꼭질 5

/**
 * 예제
 *
 * 17 8
 *
 * 답은 3 이여야함
 */
public class Main {
    static int N;
    static int K;
    static int SIZE = 500001;
    static boolean[][] visited = new boolean[SIZE][2]; // 0 ~ 500000
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_17071_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 수빈이의 시작 위치
        K = Integer.parseInt(st.nextToken()); // 동생의 시작 위치

        int time = 1;
        int ans = -1;

        if (N == K) {
            ans = 0;
        } else {
            Queue<Integer> queue = new LinkedList<>();
            queue.add(N);
            visited[N][0] = true; // N 이 시작 위치에 있을 때에는 0 번째라서 짝수임

            while (true) {
                K += time;

                if (SIZE <= K) { // K 가 SIZE 보다 크거나 같으면 종료
                    break;
                }

                // bfs 해주어야함
                int size = queue.size(); // 현재 time 만큼만 실행하기 위한 트릭

                for (int i = 0; i < size; i++) {
                    Integer p = queue.poll();

                    if ((p * 2) < SIZE && !visited[p * 2][time % 2]) {
                        visited[p * 2][time % 2] = true;
                        queue.add(p * 2);
                    }

                    if ((p - 1) != -1 && !visited[p - 1][time % 2]) {
                        visited[p - 1][time % 2] = true;
                        queue.add(p - 1);
                    }

                    // 뒤, 앞을 계산한다.
                    if ((p + 1) < SIZE && !visited[p + 1][time % 2]) {
                        visited[p + 1][time % 2] = true;
                        queue.add(p + 1);
                    }
                }

                if (visited[K][time % 2]) {
                    ans = time;
                    break;
                }

                time++;
            }
        }

        System.out.print(ans);
    }
}
