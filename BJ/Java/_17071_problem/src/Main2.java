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
public class Main2 {
    static int N;
    static int K;
    static int SIZE = 500001;
    static int[] visited = new int[SIZE]; // 0 ~ 500000
    static void bfs() {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {N, 0});
        visited[N] = 0;

        while (!queue.isEmpty()) {
            int[] p = queue.poll();

            // 2 를 건너 뛰는 것과
            if ((p[0] * 2) < SIZE && visited[p[0] * 2] == -1) {
                visited[p[0] * 2] = p[1] + 1;
                queue.add(new int[] {p[0] * 2, visited[p[0] * 2]});
            }

            if ((p[0] - 1) != -1 && visited[p[0] - 1] == -1) {
                visited[p[0] - 1] = p[1] + 1;
                queue.add(new int[] {p[0] - 1, visited[p[0] - 1]});
            }

            // 뒤, 앞을 계산한다.
            if ((p[0] + 1) < SIZE && visited[p[0] + 1] == -1) {
                visited[p[0] + 1] = p[1] + 1;
                queue.add(new int[] {p[0] + 1, visited[p[0] + 1]});
            }
        }
    }
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_17071_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 수빈이의 시작 위치
        K = Integer.parseInt(st.nextToken()); // 동생의 시작 위치
        Arrays.fill(visited, -1);

        bfs();

        int index = 1;
        int ans = -1;

        if (N == K) {
            ans = 0;
        } else {
            while (true) {
                K += index;

                if (SIZE <= K) { // K 가 SIZE 보다 크거나 같으면 종료
                    break;
                }

                if (index == visited[K]) { // 같은 경우 정답
                    ans = index;
                    break;
                }

                if (visited[K] < index) { // 미리 도달한 경우
                    // 같지 않은 경우 visited[K] 가 홀수인지 짝수인지 확인후, index 가 똑같이 홀수 혹은 짝수이면 성공
                    if ((index % 2) == (visited[K] % 2)) { // 둘이 같은 홀수 혹은 짝수인 경우
                        ans = index;
                        break;
                    } else {
                        // 그렇지도 않은 경우는 K - 1, K + 1, K / 2 (이건 K 가 짝수인 경우에만) 똑같이 홀수 인지 짝수인지 확인
                        if ((K + 1 < SIZE && (visited[K + 1] % 2 == visited[K] % 2))
                                || (K - 1 != -1 && (visited[K - 1] % 2 == visited[K] % 2))
                                || (K % 2 == 0 && (visited[K / 2] % 2 == visited[K] % 2))) {
                            ans = index;
                            break;
                        }
                    }
                }

                index++;
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(visited[i * 10 + j] + " ");
            }
            System.out.println();
        }

        System.out.print(ans);
    }
}
