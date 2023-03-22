import java.util.*;
import java.io.*;
import java.util.function.Predicate;

// 4493 : 가위 바위 보 ?

/**
 * Example
 * 3
 * 2
 * R P
 * S R
 * 3
 * P P
 * R S
 * S R
 * 1
 * P R
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_4493_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine());

            int player1 = 0;
            int player2 = 0;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                String p1 = st.nextToken();
                String p2 = st.nextToken();

                if (p1.equals("R")) {
                    if (p2.equals("S")) {
                        player1++;
                    } else if (p2.equals("P")) {
                        player2++;
                    }
                } else if (p1.equals("S")) {
                    if (p2.equals("P")) {
                        player1++;
                    } else if (p2.equals("R")) {
                        player2++;
                    }
                } else {
                    if (p2.equals("S")) {
                        player2++;
                    } else if (p2.equals("R")) {
                        player1++;
                    }
                }
            }

            if (player1 == player2) {
                System.out.println("TIE");
            } else if (player1 > player2) {
                System.out.println("Player 1");
            } else {
                System.out.println("Player 2");
            }
        }
    }
}