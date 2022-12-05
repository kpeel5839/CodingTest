import java.util.*;
import java.io.*;

// 15090 : Hard Choice

/**
 * Example
 * 80 20 40
 * 45 23 48
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_15090_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int numberOfChickenNow = Integer.parseInt(st.nextToken());
        int numberOfBeefNow = Integer.parseInt(st.nextToken());
        int numberOfPastaNow = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int requestNumberOfChicken = Integer.parseInt(st.nextToken());
        int requestNumberOfBeef = Integer.parseInt(st.nextToken());
        int requestNumberOfPasta = Integer.parseInt(st.nextToken());

        int notReceiveTheirSelection = 0;
        notReceiveTheirSelection += Math.max(requestNumberOfChicken - numberOfChickenNow, 0);
        notReceiveTheirSelection += Math.max(requestNumberOfBeef - numberOfBeefNow, 0);
        notReceiveTheirSelection += Math.max(requestNumberOfPasta - numberOfPastaNow, 0);

        System.out.println(notReceiveTheirSelection);
    }
}
