import java.util.*;
import java.io.*;

// 11365 : !밀비 급일

/**
 * Example
 * !edoc doog a tahW
 * noitacitsufbo
 * erafraw enirambus detcirtsernu yraurbeF fo tsrif eht no nigeb ot dnetni eW
 * lla sees rodroM fo drol eht ,ssertrof sih nihtiw delaecnoC
 * END
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11365_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            String line = br.readLine();

            if (line.equals("END")) {
                break;
            }

            for (int i = line.length() - 1; i != -1; i--) {
                bw.write(line.charAt(i));
            }

            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }
}
