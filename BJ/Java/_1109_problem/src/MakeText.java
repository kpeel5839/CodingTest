import java.util.*;
import java.io.*;

public class MakeText {
    static int N;
    static int M;
    static char[][] map;
    static StringBuilder text;

    static void makeRect(int y, int x, int size) {
        if (y + size < N && x + size < M) { // 이 경우에만 그릴 수 있음
            for (int i = y; i <= y + size; i++) {
                map[i][x] = 'x';
                map[i][x + size] = 'x';
            }

            for (int i = x; i <= x + size; i++) {
                map[y][i] = 'x';
                map[y + size][i] = 'x';
            }
        }
    }

    static void randomMake() {
        Random random = new Random();

        for (int i = 0; i < N; i++) {
            for (int j = 0;j  < M; j++) {
                map[i][j] = '.';
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (random.nextInt(100) == 1) {
                    makeRect(i, j, random.nextInt(8));
                }
            }
        }

        for (int i = 0; i < M; i++) {
            map[0][i] = 'x';
            map[M - 1][i] = 'x';
        }

        for (int i = 0; i < N; i++) {
            map[i][0] = 'x';
            map[i][N - 1] = 'x';
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                text.append(map[i][j]);
            }
            text.append("\n");
        }
    }

    static void noRandom() {
        for (int i = 0; i < N; i++) {
            for (int j = 0;j  < M; j++) {
                map[i][j] = '.';
            }
        }

        int y = N / 2;
        int x = M / 2;
        int size = 0;

        while (!(y < 0 && x < 0)) { // 범위를 벗어나지 않으면 계속 ㄲ
            makeRect(y, x, size);

            size += 4;
            y -= 2;
            x -= 2;

//            if (size == 16) {
//                break;
//            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                text.append(map[i][j]);
            }
            text.append("\n");
        }
    }

    static void maxX() {
        for (int i = 0; i < N; i++) {
            for (int j = 0;j  < M; j++) {
                map[i][j] = '.';
            }
        }

        for (int i = 0; i < N; i += 2) {
            for (int j = 0; j < M; j += 2) {
                map[i][j] = 'x';
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                text.append(map[i][j]);
            }
            text.append("\n");
        }
    }

    public static void main(String[] args) throws IOException {
        FileOutputStream fio = new FileOutputStream("./_1109_problem/src/sample_input.txt");
        text = new StringBuilder();

        N = 50;
        M = 50;
        text.append(N + " ").append(M + "\n");

        map = new char[N][M];
//        randomMake();
//        noRandom();
        maxX();

        byte[] bytes = text.toString().getBytes();

        for (int i = 0; i < bytes.length; i++) {
            fio.write((char) bytes[i]);
        }

        fio.flush();
        fio.close();
    }
}
