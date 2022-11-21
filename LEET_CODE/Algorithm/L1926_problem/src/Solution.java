import java.util.*;
import java.io.*;

// 1926 : Nearest exit from entrance in maze

class Solution {
    static int H;
    public int W;
    public int answer = -1;
    public int[] dx = {0, 1, 0, -1};
    public int[] dy = {-1, 0, 1, 0};

    public boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    public void bfs(char[][] maze, int[] entrance) {
        H = maze.length;
        W = maze[0].length;
        boolean[][] visited = new boolean[H][W];
        Queue<int[]> queue = new LinkedList<>();
        visited[entrance[0]][entrance[1]] = true; // 방문 처리
        queue.add(new int[] {entrance[0], entrance[1], 0});

        while (!queue.isEmpty()) {
            int[] point = queue.poll();

            for (int i = 0; i < 4; i++) {
                int y = point[0] + dy[i];
                int x = point[1] + dx[i];

                if (outOfRange(y, x)) {
                    if (point[2] == 0) {
                        continue;
                    } else {
                        answer = point[2];
                        queue.clear();
                        break;
                    }
                }

                if (visited[y][x] || maze[y][x] == '+') {
                    continue;
                }

                visited[y][x] = true;
                queue.add(new int[]{y, x, point[2] + 1});
            }
        }
    }

    public int nearestExit(char[][] maze, int[] entrance) {
        bfs(maze, entrance);
        return answer;
    }
}