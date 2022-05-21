import java.util.*;
import java.io.*;

/**
 * -- 해맸던 점
 * 시작 지점, 즉 visited[y][x] = true 를 안해줘서 틀렸었음
 * 개같은 것
 */
class Solution {
    public static void main(String[] args) {
        List<Object[]> res = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Object[] obj = new Object[2];
            obj[0] = i +3;
            obj[1] = "fuck" + i;
        }

        System.out.println(res.get(0)[0]);
    }

    static char[][][] map;
    static int Z;
    static int H;
    static int W;
    static int[] dx;
    static int[] dy;
    static boolean[][] visited;
    static int[] answer;

    static boolean outOfRange(int y, int x) {
        if ((y < 0 || y >= H) || (x < 0 || x >= W)) {
            return true;
        } else {
            return false;
        }
    }

    static boolean bfs(int y, int x, int z) { // 맨채스터 거리로 안떨어져있으면 boolean true 를 보낸다.
        Queue<int[]> queue = new LinkedList<>();
        visited = new boolean[H][W];
        queue.add(new int[] {y, x, 0}); // 움직인 거리
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            int[] point = queue.poll(); // 지점을 빼서 진행

            for (int i = 0; i < 4; i++) {
                int ny = point[0] + dy[i];
                int nx = point[1] + dx[i];

                if (outOfRange(ny, nx) || visited[ny][nx] || map[z][ny][nx] == 'X') { // 이미 방문했거나, 혹은 범위를 넘어갔거나 아니면 X 를 만났을 때
                    continue;
                }

                if (map[z][ny][nx] == 'P') { // 현재 방문한 곳이 응시자가 앉아있는 자리일 때
                    return true;
                }

                if (point[2] >= 1) { // 애초에 여기서 2 로 만드는 경우를 없애야 함
                    visited[ny][nx] = true;
                    continue;
                }

                visited[ny][nx] = true;
                queue.add(new int[] {ny, nx, point[2] + 1});
            }
        }

        return false;
    }

    public int[] solution(String[][] places) {
        Z = 5;
        H = 5;
        W = 5;
        dx = new int[] {0, 1, 0, -1};
        dy = new int[] {-1, 0, 1, 0};
        map = new char[Z][H][W];
        answer = new int[Z];
        Arrays.fill(answer, 1); // 일단 다 정상적이라고 생각하고 진행

        for (int i = 0; i < Z; i++) {
            for (int j = 0; j < H; j++) {
                for (int c = 0; c < W; c++) {
                    map[i][j][c] = places[i][j].charAt(c);
                }
            }
        } // 맵 옮기기 완료

        mapPrint();

        Loop:
        for (int i = 0; i < Z; i++) {
            for (int j = 0; j < H; j++) {
                for (int c = 0; c < W; c++) {
                    if (map[i][j][c] == 'P') {
                        if (bfs(j, c, i)) {
                            answer[i] = 0; // 실패한 경우
                            continue Loop; // Loop 로 돌아가서 다음 번을 실행한다.
                        }
                    }
                }
            }
        }

        return answer;
    }

    static void mapPrint() {
        for (int c = 0; c < Z; c++) {
            System.out.println(c + "번째 맵");
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    System.out.print(map[c][i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}