import java.util.*;
import java.io.*;

class Solution {
    // 진짜 간단한 bfs 이다.
    static int H;
    static int W;
    static int numberOfArea; // 영역의 개수
    static int maxSizeOfOneArea; // 가장 큰 영역의 크기
    static int[] dx;
    static int[] dy;
    static boolean[][] visited;

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    static void bfs(int y, int x, int target, int[][] picture) {
        Queue<int[]> queue = new LinkedList<>();
        visited[y][x] = true;
        numberOfArea++;

        int count = 0;
        queue.add(new int[] {y, x});

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            count++;

            for (int i = 0; i < 4; i++) {
                int ny = point[0] + dy[i];
                int nx = point[1] + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || picture[ny][nx] != target) {
                    continue;
                }

                queue.add(new int[]{ny, nx});
                visited[ny][nx] = true;
            }
        }

        maxSizeOfOneArea = Math.max(maxSizeOfOneArea, count);
    }

    public int[] solution(int m, int n, int[][] picture) {
        numberOfArea = 0;
        maxSizeOfOneArea = 0;
        H = picture.length;
        W = picture[0].length;
        visited = new boolean[H][W];
        dx = new int[] {0, 1, 0, -1};
        dy = new int[] {-1, 0, 1, 0};

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (!visited[i][j] && picture[i][j] != 0) {
                    bfs(i, j, picture[i][j], picture);
                }
            }
        }
        int[] answer = new int[] {numberOfArea, maxSizeOfOneArea};
        return answer;
    }
}