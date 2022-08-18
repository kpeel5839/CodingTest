import java.util.*;
import java.io.*;

class Solution2 {
    static int N;
    static boolean[][] pillar;
    static boolean[][] step;
    static int KI = 0; // 기둥
    static int BO = 1; // 보

    static boolean can(int y, int x, int kind) {
        if (kind == KI) {
            if (y == 0) {
                return true;
            }

            if (c(y, x, BO) || c(y, x - 1, BO)) {
                return true;
            }

            if (c(y - 1, x, KI)) {
                return true;
            }
        } else {
            if (c(y - 1, x, KI) || c(y - 1, x + 1, KI)) {
                return true;
            }

            if (c(y, x - 1, BO) && c(y, x + 1, BO)) {
                return true;
            }
        }

        return false;
    }

    static boolean c(int y, int x, int kind) {
        if (y < 0 || y >= N || x < 0 || x >= N) {
            return false;
        }

        if (kind == KI) {
            return pillar[y][x];
        } else {
            return step[y][x];
        }
    }

    static void insert(int y, int x, int kind) {
        if (can(y, x, kind)) { // 가능하다.
            if (kind == KI) {
                pillar[y][x] = true;
            } else {
                step[y][x] = true;
            }
        }
    }

    static void delete(int y, int x, int kind) {
        if (kind == KI) {
            pillar[y][x] = false;
        } else {
            step[y][x] = false;
        }

        boolean possible = true;

        Loop:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (pillar[i][j] && can(i, j, KI)) {
                    possible = false;
                    break Loop;
                }

                if (step[i][j] && can(i, j, BO)) {
                    possible = false;
                    break Loop;
                }
            }
        }

        if (!possible) {
            if (kind == KI) {
                pillar[y][x] = true;
            } else {
                step[y][x] = false;
            }
        }
    }

    public int[][] solution(int n, int[][] build_frame) {
        List<int[]> res = new ArrayList<>();
        N = n + 1;

        step = new boolean[N][N];
        pillar = new boolean[N][N];

        for (int i = 0; i < build_frame.length; i++) {
            if (build_frame[i][3] == 0) { // 삭제
                delete(build_frame[i][1], build_frame[i][0], build_frame[i][2]);
            } else { // 설치
                insert(build_frame[i][1], build_frame[i][0], build_frame[i][2]);
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (pillar[j][i]) {
                    res.add(new int[] {i, j, KI});
                }

                if (step[j][i]) {
                    res.add(new int[] {i, j, BO});
                }
            }
        }

        return res.toArray(int[][]::new);
    }
}