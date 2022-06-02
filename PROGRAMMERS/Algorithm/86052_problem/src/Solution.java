import java.util.*;
import java.io.*;

// 해맸던 점 : Grid 가 가로 세로 다른 길이를 가질 수 있다라는 것을 간과했음, 같다는줄...
class Solution {
    static int H;
    static int W;
    static boolean[][][] visited;
    static int[] dx;
    static int[] dy;

    static int cycle(String[] grid, int y, int x, int dir) {
        int cnt = 0;

        while (true) {
            if (visited[y][x][dir]) {
                break;
            }

            visited[y][x][dir] = true;
            char c = grid[y].charAt(x);

            switch (c) {
                case 'R' :
                    dir = (dir + 1) % 4;
                    break;
                case 'L' :
                    dir = (dir == 0) ? 3 : dir - 1;
                    break;
            }

            y = (y + dy[dir] + H) % H;
            x = (x + dx[dir] + W) % W; // 이렇게 하면 음수인 경우에도 다시 돌아오게 할 수 있음
            cnt++;
        }

        return cnt;
    }

    public int[] solution(String[] grid) {
        List<Integer> resList = new ArrayList<>();
        H = grid.length;
        W = grid[0].length();
        visited = new boolean[H][W][4];
        dx = new int[] {0, 1, 0, -1};
        dy = new int[] {-1, 0, 1, 0};

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                for (int dir = 0; dir < 4; dir++) {
                    if (!visited[i][j][dir]) {
                        resList.add(cycle(grid, i, j, dir));
                    }
                }
            }
        }

        Collections.sort(resList);
        return resList.stream().mapToInt(i -> i).toArray();
    }
}