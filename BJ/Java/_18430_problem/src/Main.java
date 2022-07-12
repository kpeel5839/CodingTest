import java.util.*;
import java.io.*;
import java.util.function.Function;

// 18430 : 무기 공학

/**
 * -- 전제조건
 * 나무 목재가 최대 5 * 5 가 주어진다.
 * 그 때, 부메랑을 만든다면?
 *
 * 최대로 만들 수 있는 부메랑의 합을 구하여라 (부메랑은 ㄱ, ㄴ 모양 중 하나로 가능하다.)
 *
 * -- 틀 설계
 * 부메랑의 모양을 처리할 수 있는 method 를 만들고
 * 만일 부메랑의 모양대로 해결할 수 있다면?
 *
 * 부메랑의 모양대로 처리하고 visited 한 뒤에 지나간다. (합을 계속 구해주면서)
 * y, x 로 계속 진행한다.
 *
 * -- 해맸던 점
 * 모양 잘못 알고 있었음
 * 가능한 모양 4가지 였는데
 * 그냥 ㄱ, ㄴ 만 하고 있었음 그러니까 당연히 안되지
 *
 * 그냥 노가다 성인 재미 없는 문제였음
 */
public class Main {
    static int H;
    static int W;
    static int ans = 0;
    static int[][] map;
    static boolean[][] visited;

    static void bruteForce(int y, int x, int sum) {
        // 부메랑을 만들지 않을 이유? 있을까, 있을 듯 하다.
        // 그러면 3가지 경우를 실시해야 할 듯, 부메랑을 ㄱ, ㄴ 자로 만드는 경우와, 부메랑을 만들지 않는 경우
        if (y == H - 1 && x == W - 1) { // 사실 y 가 H - 1 에 도달한 경우 끝내도 되지만 혹시 모르고 귀찮으니까 이렇게 하자.
//            visitedPrint();
            ans = Math.max(ans, sum);
            return;
        }

        for (int i = 0; i < 5; i++) { // 이렇게 안해도 되지만 나는 이 구조가 뭔가 심신에 안정을 준다.
            int nextY = y;
            int nextX = x;

            if (nextX == W - 1) {
                nextX = 0;
                nextY++;
            } else {
                nextX++;
            }

            if (i != 4) {
                int ret = boomerang(y, x, i);

                if (ret != 0) {
                    bruteForce(nextY, nextX, sum + ret);
                    restore(y, x, i);
                }
            } else {
                bruteForce(nextY, nextX, sum);
            }
        }
    }

    static int boomerang(int y, int x, int judge) {
        // 0 == ㄱ 자, 1 == ㄴ 자
        // 0 을 반환하면 부메랑 만들기 실패, 아니면 부메랑 만들기 성공, 근데 솔직히 얘는 상관없이 그냥 더해주면 된다.
        if (visited[y][x]) {
            return 0;
        }

        if (judge == 0) { // ㄱ 자
            if (!outOfRange(y - 1, x + 1) && !visited[y - 1][x + 1]
                    && !outOfRange(y, x + 1) && !visited[y][x + 1]) { // 성공한 경우
                visited[y][x] = true;
                visited[y][x + 1] = true;
                visited[y - 1][x + 1] = true;
                return map[y][x] + map[y - 1][x + 1] + (map[y][x + 1] * 2);
            }
        } else if (judge == 1) { // ㄴ 자
            if (!outOfRange(y - 1, x) && !visited[y - 1][x]
                    && !outOfRange(y - 1, x + 1) && !visited[y - 1][x + 1]) { // 성공한 경우
                visited[y][x] = true;
                visited[y - 1][x] = true;
                visited[y - 1][x + 1] = true;
                return map[y][x] + map[y - 1][x + 1] + (map[y - 1][x] * 2);
            }
        } else if (judge == 2) { // ㄱ 자 반대
            if (!outOfRange(y - 1, x) && !visited[y - 1][x]
                    && !outOfRange(y, x + 1) && !visited[y][x + 1]) { // 성공한 경우
                visited[y][x] = true;
                visited[y - 1][x] = true;
                visited[y][x + 1] = true;
                return (map[y][x] * 2) + map[y - 1][x] + map[y][x + 1];
            }
        } else { // ㄴ 자 반대
            if (!outOfRange(y - 1, x) && !visited[y - 1][x]
                    && !outOfRange(y - 1, x - 1) && !visited[y - 1][x - 1]) { // 성공한 경우
                visited[y][x] = true;
                visited[y - 1][x] = true;
                visited[y - 1][x - 1] = true;
                return map[y][x] + map[y - 1][x - 1] + (map[y - 1][x] * 2);
            }
        }

        return 0;
    }

    static void restore(int y, int x, int judge) {
        visited[y][x] = false;
        if (judge == 0) { // ㄱ 자
            visited[y][x + 1] = false;
            visited[y - 1][x + 1] = false;
        } else if (judge == 1) { // ㄴ 자
            visited[y - 1][x] = false;
            visited[y - 1][x + 1] = false;
        } else if (judge == 2) { // ㄱ 자 반대
            visited[y - 1][x] = false;
            visited[y][x + 1] = false;
        } else { // ㄴ 자 반대
            visited[y - 1][x] = false;
            visited[y - 1][x - 1] = false;
        }
    }

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= H || x < 0 || x >= W) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);
        map = new int[H][W];
        visited = new boolean[H][W];

        for (int i = 0; i < H; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < W; j++) {
                map[i][j] = fun.apply(input[j]);
            }
        }

        bruteForce(0, 0, 0);
        System.out.println(ans);
    }

    static void visitedPrint() {
        System.out.println("next!");
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (!visited[i][j]) {
                    System.out.print(0 + " ");
                } else {
                    System.out.print(1 + " ");
                }
            }
            System.out.println();
        }
    }
}
