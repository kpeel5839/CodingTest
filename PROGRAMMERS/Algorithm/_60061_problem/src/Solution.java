import java.util.*;
import java.io.*;

class Solution {
    static int N;
    static boolean[][] pillar;
    static boolean[][] step;
    static int KI = 0; // 기둥
    static int BO = 1; // 보

    static void insert(int y, int x, int kind) {
        if (kind == KI) {
            if (y == 0) {
                pillar[y][x] = true;
            }

            if (c(y, x, BO) || c(y, x - 1, BO)) {
                pillar[y][x] = true;
            }

            if (c(y - 1, x, KI)) {
                pillar[y][x] = true;
            }
        } else {
            if (c(y - 1, x, KI) || c(y - 1, x + 1, KI)) {
                step[y][x] = true;
            }

            if (c(y, x - 1, BO) && c(y, x + 1, BO)) {
                step[y][x] = true;
            }
        }
    }

    static void delete(int y, int x, int kind) {
        if (kind == KI) {
            if (!c(y + 1, x - 1, BO) && !c(y + 1, x, BO) && c(y + 1, x, KI)) { // 나가리 데스네
                return;
            }

            if (!c(y + 1, x - 2, BO) && !c(y, x - 1, KI) && !c(y, x + 1, KI) && !c(y + 1, x + 1, BO)) { // 나가리 데스네
                return;
            }

            if ((c(y + 1, x + 1, BO) || c(y, x + 1, KI)) && (!c(y + 1, x - 2, BO) && !c(y, x - 1, KI))) { // 나가리 데스네
                return;
            }

            if ((c(y + 1, x - 2, BO) || c(y, x - 1, KI)) && (!c(y + 1, x + 1, BO) && !c(y, x + 1, KI))) { // 나가리 데스네
                return;
            }

            pillar[y][x] = false; // 모든 관문을 거쳐서 나가리 안했데스네
        } else {
            if (!c(y, x - 1, BO) && c(y, x, KI) && !c(y - 1, x, KI)) {
                return;
            }

            if (c(y, x - 1, BO) && !c(y - 1, x - 1, KI) && !c(y - 1, x, KI)) {
                return;
            }

            if (c(y, x + 1, BO) && !c(y - 1, x + 1, KI) && !c(y - 1, x + 2, KI)) {
                return;
            }

            step[y][x] = false;
        }
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