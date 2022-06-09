import java.util.*;
import java.io.*;

// 2440 : 별찍기 3
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());

        for (int i = N; i != 0; i--) {
            for (int j = i; j != 0; j--) {
                sb.append("*");
            }
            sb.append("\n");
        }

        System.out.println(sb);
    }
}
