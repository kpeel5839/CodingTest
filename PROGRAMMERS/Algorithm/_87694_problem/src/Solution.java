import java.util.*;
import java.io.*;

// 실제로는 떨어져있지만, 거리가 1 차이나서, 붙어있는 것처럼 처리되는 아이들을 좌표들을 전부 2를 곱합으로써
// 실제로 떨어져있는 것과 같이 처리함
class Solution {
    static int N = 101;
    static boolean[][] map = new boolean[N][N];
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0};

    static void drawRect(int[][] rect) { // 테두리에 대한 그리기
        for (int k = 0; k < rect.length; k++) {
            int x1 = rect[k][0] * 2;
            int y1 = rect[k][1] * 2;
            int x2 = rect[k][2] * 2;
            int y2 = rect[k][3] * 2;

            for (int i = y1; i <= y2; i++) {
                map[i][x1] = true;
                map[i][x2] = true;
            }

            for (int i = x1; i <= x2; i++) {
                map[y1][i] = true;
                map[y2][i] = true;
            }
        }
    }

    static void removeIn(int[][] rect) {
        for (int k = 0; k < rect.length; k++) {
            int x1 = rect[k][0] * 2;
            int y1 = rect[k][1] * 2;
            int x2 = rect[k][2] * 2;
            int y2 = rect[k][3] * 2;

            for (int i = y1 + 1; i <= y2 - 1; i++) {
                for (int j = x1 + 1; j <= x2 - 1; j++) {
                    map[i][j] = false;
                }
            }
        }
    }

    static int bfs(int cY, int cX, int iY, int iX) {
        // bfs 로 가장 짧은 거리에 (iY, iX) 에 도달한다.
        // cY, cX 를 시작지점으로 잡고 map true 인 부분만 간다.
        // 그리고 이미 방문한 곳은 방문하지 않기 위해, map 을 걍 변경하자, 해당 지점으로 가면서 그 지점을 false 로 만들면 된다.    
        Queue<int[]> queue = new LinkedList<>(); // (y, x, cost)
        queue.add(new int[] {cY, cX, 0});
        map[cY][cX] = false;

        int ans = 0;

        while (!queue.isEmpty()) {
            int[] p = queue.poll();

            if (p[0] == iY && p[1] == iX) {
                ans = p[2];
                break;
            }

            for (int i = 0; i < 4; i++) {
                int ny = p[0] + dy[i];
                int nx = p[1] + dx[i];

                if (outOfRange(ny, nx) || !map[ny][nx]) {
                    continue;
                }

                map[ny][nx] = false;
                queue.add(new int[] {ny, nx, p[2] + 1});
            }
        }

        return (ans / 2);
    }

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= N || x < 0 || x >= N) {
            return true;
        } else {
            return false;
        }
    }

    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        drawRect(rectangle);
        // System.out.println("just draw rect");
        // for (int i = 0; i < 30; i++) {
        //     for (int j = 0; j < 30; j++) {
        //         if (map[i][j]) {
        //             System.out.print(1 + " ");
        //         } else {
        //             System.out.print(0 + " ");
        //         }            
        //     }
        //     System.out.println();
        // }

        removeIn(rectangle);
        // System.out.println("just remove in");
        // for (int i = 0; i < 30; i++) {
        //     for (int j = 0; j < 30; j++) {
        //         if (map[i][j]) {
        //             System.out.print(1 + " ");
        //         } else {
        //             System.out.print(0 + " ");
        //         }            
        //     }
        //     System.out.println();
        // }
        return bfs(characterY * 2, characterX * 2, itemY * 2, itemX * 2);
    }
}