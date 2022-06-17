import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            if (i % 2 != 0) {
                System.out.print(" ");
            }

            for (int j = 0; j < N; j++) {
                System.out.print("* ");
            }

            System.out.println();
        }
    }
}
