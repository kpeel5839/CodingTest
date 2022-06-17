import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int index = N;
        boolean crease = false;

        if (N == 1) {
            sb.append("*");
        } else {
            while (true) {
                for (int i = 0; i < N - index; i++) {
                    sb.append(" ");
                }

                for (int i = 0; i < index * 2 - 1; i++) {
                    sb.append("*");
                }

                if (crease) {
                    index++;
                } else {
                    index--;
                }

                if (index == 1) {
                    crease = true;
                }

                if (index == N + 1) {
                    break;
                }

                sb.append("\n");
            }
        }

        System.out.println(sb);
    }

}
