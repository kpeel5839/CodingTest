import java.util.*;
import java.io.*;

// 2442 : 별 찍기 - 5
// 하핫 재미졍
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int draw = 1;

        while (N > 0) {
            for (int i = 0; i < N - 1; i++) {
                sb.append(" ");
            }

            for (int i = 0; i < draw; i++) {
                sb.append("*");
            }

            sb.append("\n");
            draw += 2;
            N--;
        }

        System.out.println(sb);
    }
}
