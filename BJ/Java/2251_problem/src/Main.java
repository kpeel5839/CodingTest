import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2251 : 물통

/**
 * -- 전제조건
 * a, b, c 물통이 존재하고 각각 들어갈 수 있는 물의 양이 정해져있다.
 * 그럴 때 a 가 비어있을 때, c 의 물통에 들어있는 물의 양을 모두 구해내라
 *
 * -- 틀 설계
 * 그냥 물의 양은 hashSet 으로 하면 될 것 같고
 * 나머지는 dfs 로 하되, 방문처리를 하면 될 것 같다.
 * a, b, c 가 주어지면, 해당 배열을 그 크기로 선언하고
 * 만일 똑같은 물의 양인 경우가 있으면 그냥 return 해준다.
 *
 * 즉, 이미 방문했던 것은 또 볼필요가 없다.
 * dfs 랑 비슷한 느낌인 듯
 *
 * -- 해결
 * 진짜 쉽게 풀었는데
 * 너무 쌉 하드코딩 한 것 같은 느낌에 조금 기분 더러움
 */
public class Main {
    static boolean[][][] visited;
    static HashSet<Integer> set = new HashSet<>();
    static List<Integer> res = new ArrayList<>();
    static int A;
    static int B;
    static int C; // 각 용량

    static void dfs(int a, int b, int c) {
        if (visited[a][b][c]) {
            return;
        }

        if (a == 0) {
            if (!set.contains(c)) { // 이미 들어있지 않은 경우만 추가
                set.add(c);
                res.add(c); // 답으로도 추가
            }
        }

        visited[a][b][c] = true;

        // 0 이 아니라면 모두 부어야 함 a, b, c를 다 검사해본다.
        if (a != 0) {
            if (b != B) { // 가득 차지 않은 경우
                // 들어갈 수 있는 용량 계산
                int remain = B - b; // 이만큼 들어갈 수 있음
                if (remain <= a) { // b 를 가득 채울 수 있는 상황
                    dfs(a - remain, B, c);
                } else { // 남은 용량이 a 보다 큰 상황
                    dfs(0, b + a, c);
                }
            }

            if (c != C) { // 가득 차지 않은 경우
                // 들어갈 수 있는 용량 계산
                int remain = C - c;
                if (remain <= a) {
                    dfs(a - remain, b, C);
                } else {
                    dfs(0, b, c + a);
                }
            }
        }

        if (b != 0) {
            if (a != A) { // 가득 차지 않은 경우
                // 들어갈 수 있는 용량 계산
                int remain = A - a; // 이만큼 들어갈 수 있음
                if (remain <= b) { // b 를 가득 채울 수 있는 상황
                    dfs(A, b - remain, c);
                } else { // 남은 용량이 a 보다 큰 상황
                    dfs(a + b, 0, c);
                }
            }

            if (c != C) { // 가득 차지 않은 경우
                // 들어갈 수 있는 용량 계산
                int remain = C - c;
                if (remain <= b) {
                    dfs(a, b - remain, C);
                } else {
                    dfs(a, 0, c + b);
                }
            }
        }

        if (c != 0) {
            if (a != A) { // 가득 차지 않은 경우
                // 들어갈 수 있는 용량 계산
                int remain = A - a;
                if (remain <= c) {
                    dfs(A, b, c - remain);
                } else {
                    dfs(a + c, b, 0);
                }
            }
            if (b != B) { // 가득 차지 않은 경우
                // 들어갈 수 있는 용량 계산
                int remain = B - b; // 이만큼 들어갈 수 있음
                if (remain <= c) { // b 를 가득 채울 수 있는 상황
                    dfs(a, B, c - remain);
                } else { // 남은 용량이 a 보다 큰 상황
                    dfs(a, b + c, 0);
                }
            }

        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;
        StringBuilder sb = new StringBuilder();

        String[] input = br.readLine().split(" ");
        A = fun.apply(input[0]);
        B = fun.apply(input[1]);
        C = fun.apply(input[2]);

        visited = new boolean[A + 1][B + 1][C + 1]; // 물이 없는 경우와 가득 찬 경우를 고려해야 해서 + 1로
        dfs(0, 0, C);

        Collections.sort(res);
        for (Integer number : res) {
            sb.append(number).append(" ");
        }

        System.out.println(sb);
    }
}
