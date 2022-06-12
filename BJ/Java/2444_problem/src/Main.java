import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int index = 1;
        boolean crease = true;

        while (true) {
            for (int i = 0; i < N - index; i++) {
                System.out.print(" ");
            }

            for (int i = 0; i < index * 2 - 1; i++) {
                System.out.print("*");
            }

            if (crease) {
                index++;
            } else {
                index --;
            }

            if (index == N) {
                crease = false;
            }

            if (index == 0) {
                break;
            }

            System.out.println();
        }
    }
}

