import java.util.*;
import java.io.*;

// 1100 : 하얀칸

/**
 * Example
 * .F.F...F
 * F...F.F.
 * ...F.F.F
 * F.F...F.
 * .F...F..
 * F...F.F.
 * .F.F.F.F
 * ..FF..F.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1100_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numberOfHorseInWhiteCell = 0;

        for (int i = 0; i < 8; i++) {
            String chessLine = br.readLine();
            for (int j = 0; j < 8; j++) {
                char nowCellState = chessLine.charAt(j);
                if (nowCellState != 'F') {
                    continue;
                }

                if (i % 2 == 0 && j % 2 == 0) { // 짝수
                    numberOfHorseInWhiteCell++;
                }

                if (i % 2 != 0 && j % 2 != 0) { // 홀수
                    numberOfHorseInWhiteCell++;
                }
            }
        }

        System.out.println(numberOfHorseInWhiteCell);
    }
}
