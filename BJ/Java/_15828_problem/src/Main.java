import java.util.*;
import java.io.*;

// 15828 : Router

/**
 * 예제
 * 5
 * 1
 * 2
 * 0
 * 3
 * 4
 * 0
 * 5
 * 6
 * 0
 * 0
 * -1
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_15828_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        Queue<Integer> router = new LinkedList<>();

        while (true) {
            int command = Integer.parseInt(br.readLine());

            if (command == -1) {
                break;
            }

            if (command == 0) {
                router.poll();
            }

            if (command != 0 && router.size() < N) {
                router.add(command);
            }
        }

        if (router.isEmpty()) {
            bw.write("empty");
        } else {
            while (!router.isEmpty()) {
                bw.write(router.poll() + " ");
            }
        }

        bw.flush();
        bw.close();
    }
}
