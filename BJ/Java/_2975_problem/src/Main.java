import java.util.*;
import java.io.*;

// 2975 : transaction

/**
 * example
 * 10000 W 10
 * -200 D 300
 * 50 W 300
 * 0 W 0
 */
public class Main {
    static final char WITHDRAW = 'W';
    static final char DEPOSIT = 'D';

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2975_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());

            // W == withdrawal, D == Deposit
            int depositMoney = Integer.parseInt(st.nextToken());
            char action = st.nextToken().charAt(0);
            int moneyOfAction = Integer.parseInt(st.nextToken());

            if (action == WITHDRAW) {
                if (depositMoney == 0 && moneyOfAction == 0) { // exit
                    break;
                }

                depositMoney -= moneyOfAction;

                if (depositMoney < -200) {
                    bw.write("Not allowed\n");
                } else {
                    bw.write(depositMoney + "\n");
                }
            }

            if (action == DEPOSIT) {
                bw.write((depositMoney + moneyOfAction) + "\n");
            }
        }

        bw.flush();
        bw.close();
    }
}
