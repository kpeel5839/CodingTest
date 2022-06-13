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
                for (int i = 0; i < N - index; i++) {
                    sb.append(" ");
                }

                for (int i = 0; i < index; i++) {
                    sb.append("*");
                }

                if (crease) {
                    index++;
                } else {
                    index--;
                }

                sb.append("\n");

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
