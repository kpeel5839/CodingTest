import java.util.*;
import java.io.*;

class Solution {
    static int H;
    static int W;
    static List<Integer> answerList;
    static int[][] map;
    static int[] dx;
    static int[] dy;

    static void rotate(int y1, int x1, int y2, int x2) { // 여기서는 테두리 부분을 시계방향으로 회전시켜줄 수 있어야 한다.        
        int yMove = y2 - y1;
        int xMove = x2 - x1;
        int[] limit = {yMove, xMove, yMove, xMove};
        int y = y2;
        int x = x1; // 초기 위치 설정    
        int min = map[y][x]; // 초기값 넣어줌
        int prev = map[y][x];
        int now = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < limit[i]; j++) {
                y += dy[i];
                x += dx[i]; // 움직여줌

                min = Math.min(min, map[y][x]);
                now = map[y][x];
                map[y][x] = prev;
                prev = now; // swap 해주는 부분
            }
        }

        answerList.add(min);
    }

    static void init() {
        int val = 1;

        for (int i = 1; i <= H; i++) { // 맵 초기화 부분
            for (int j = 1; j <= W; j++) {
                map[i][j] = val++;
            }
        }
    }

    public int[] solution(int rows, int columns, int[][] queries) {
        answerList = new ArrayList<>();
        H = rows;
        W = columns;
        map = new int[H + 1][W + 1];

        dx = new int[] {0, 1, 0, -1};
        dy = new int[] {-1, 0, 1, 0};
        init(); // 맵 초기화

        for (int i = 0; i < queries.length; i++) {
            rotate(queries[i][0], queries[i][1], queries[i][2], queries[i][3]);
        }

        int[] answer = new int[answerList.size()];
        int i = 0;
        for (Integer number : answerList) {
            answer[i++] = number;
        }

        return answer;
    }
}