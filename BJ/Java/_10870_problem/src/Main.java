import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int[] arr = new int[N];

        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }

        int find = sc.nextInt();
        int ans = 0;

        for (int i = 0; i < N; i++) {
            if (arr[i] == find) {
                ans++;
            }
        }

        System.out.println(ans);
    }
}
