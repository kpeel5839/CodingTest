import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int index = 1;
        boolean crease = true;

        if (N == 1) {
            sb.append("*");
        } else {
            while (true) {
                // index 가 N 까지 도달하고, 다시 내려오는 형식으로
                for (int i = 0; i < index; i++) {
                    sb.append("*");
                }


                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < N - index; j++) {
                        sb.append(" ");
                    }
                }

                for (int i = 0; i < index; i++) {
                    sb.append("*");
                }

                sb.append("\n");

                if (crease) {
                    index++;
                } else {
                    index--;
                }

                if (index == N) {
                    crease = false;
                }

                if (index == 0) {
                    break;
                }
            }
        }

        System.out.println(sb);
    }
}
