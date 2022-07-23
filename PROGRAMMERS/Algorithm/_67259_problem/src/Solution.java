import java.util.*;
import java.io.*;

/**
 * 코너 건설이 600 이라는 점과
 * value 범위를 더 좁은 범위의 지역변수로 했었어야 했는데 그 부분을 실수해서
 * 한 10분 정도 더 소요됨
 */
class Solution {
    int dirSize = 4;
    int[][][] visited;

    class Road { // road 를 이동하는 형식
        int y;
        int x;
        int dir;
        int value;

        Road(int y, int x, int dir, int value) {
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.value = value;
        }
    }

    public int solution(int[][] board) {
        int ans = Integer.MAX_VALUE;
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        int H = board.length;
        int W = board[0].length;
        visited = new int[H][W][4];

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                Arrays.fill(visited[i][j], Integer.MAX_VALUE);
            }
        }
        // visited 를 이용해서 그냥 bfs 를 활용하면 된다.
        Queue<Road> queue = new LinkedList<>();
        queue.add(new Road(0, 0, -1, 0));

        while (!queue.isEmpty()) {
            Road point = queue.poll();
            // System.out.println(point.dir + " " + point.value);
            // System.out.println("y : " + point.y + " x : " + point.x);

            if (point.y == H - 1 && point.x == W - 1) { // 끝 점에 도달한 경우
                ans = Math.min(ans, point.value);
            }

            for (int i = 0; i < 4; i++) {
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                int value = point.value;

                if (ny < 0 || ny >= H || nx < 0 || nx >= W || board[ny][nx] == 1) {
                    continue;
                }

                if (point.dir == -1) { // 첫 시작이 아닐 때
                    value += 100; // 무조건 100 이 추가
                } else { // dir == -1 즉 처음 시작일 때
                    if (point.dir != i) { // 즉 이전에 가던 방향과 현재 가려는 방향이 다를 때
                        value += 600; // 사실 코너는 100 + 500, 코너 + 직선이기 때문이다.
                    } else {
                        value += 100;
                    }
                }

                if (value < visited[ny][nx][i]) { // 현재 value 가 visited[ny][nx][i] 보다 작아야지 들어올 수 있음, i 는 이 격자로 들어올 떄의 방향
                    visited[ny][nx][i] = value;
                    queue.add(new Road(ny, nx, i, value));
                }
            }
        }

        return ans; // 항상 경주로가 건설될 형태이기 떄문에 상관없다.
    }
}