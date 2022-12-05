import java.util.*;
import java.io.*;

// 1917 : 정육면체 전개도

/**
 * Example
 * 0 0 0 0 0 0
 * 0 0 0 0 0 0
 * 0 0 1 0 0 0
 * 0 1 1 1 1 0
 * 0 0 1 0 0 0
 * 0 0 0 0 0 0
 * 0 1 1 0 0 0
 * 0 1 0 0 0 0
 * 0 1 0 0 0 0
 * 1 1 0 0 0 0
 * 0 0 0 0 0 0
 * 0 0 0 0 0 0
 * 0 0 0 0 0 0
 * 0 0 0 1 1 0
 * 0 0 1 1 0 0
 * 0 0 0 1 1 0
 * 0 0 0 0 0 0
 * 0 0 0 0 0 0
 */
public class Main {
    public static final int PAPER_SIZE = 6;
    public static final String POSSIBLE = "yes";
    public static final String IMPOSSIBLE = "no";
    public static int[] directionOfX = new int[] {0, 1, 0, -1};
    public static int[] directionOfY = new int[] {-1, 0, 1, 0};
    public static int[][] paperDirection = new int[][] {}; // 가장 중요한 배열, 이걸로 끝임
    public static StringBuilder paper = new StringBuilder();
    public static StringTokenizer stringTokenizer;

    public static String cutPaper() { // 결과를 뱉음, 6 * 6 으로 잘라서 봄, 즉 StringTokenizer 에서 6 * 6 개의 토큰을 빼먹음

        return foldingPaper(); // 여기서 yes or no 를 판단
    }

    public static String foldingPaper() {
        return null;
    }

    public static String validateCube(Boolean[] cube) {
        return Arrays.stream(cube).allMatch(face -> face) ? POSSIBLE : IMPOSSIBLE;
    } // 가능한 정육면체면 POSSIBLE, 아니면 IMPOSSIBLE 반환

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1917_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        boolean remainPaper = true;

        while (remainPaper) {
            for (int i = 0; i < PAPER_SIZE; i++) {
                String line = br.readLine();

                if (line == null) {
                    remainPaper = false;
                    break;
                }

                paper.append(line).append("\n");
            }
        }

        stringTokenizer = new StringTokenizer(paper.toString());

        while (stringTokenizer.hasMoreTokens()) {
            bw.write(cutPaper() + "\n");
        }

        bw.flush();
        bw.close();
    }
}
