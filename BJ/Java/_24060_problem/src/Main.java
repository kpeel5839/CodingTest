import java.util.*;
import java.io.*;

// 24060 : 알고리즘 수업 - 병합 정렬 1

/**
 * 예제
 * 5 7
 * 4 5 1 3 2
 */
public class Main {
    static int N;
    static int K;
    static int cnt = 0;
    static int result = -1;

    static void mergeSort(int[] A, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(A, p, q);
            mergeSort(A, q + 1, r);
            merge(A, p, q, r);
        }
    }

    static void merge(int[] A, int p, int q, int r) {
        int i = p;
        int j = q + 1;
        int t = 0;
        int[] tmp = new int[r - p + 1];
        while (i <= q && j <= r) {
            if (A[i] <= A[j]) {
                tmp[t++] = A[i++];
                judgeToAnswer(t - 1, tmp);
            }
            else {
                tmp[t++] = A[j++];
                judgeToAnswer(t - 1, tmp);
            }
        }
        while (i <= q) {
            tmp[t++] = A[i++];
            judgeToAnswer(t - 1, tmp);
        }
        while (j <= r) {
            tmp[t++] = A[j++];
            judgeToAnswer(t - 1, tmp);
        }
        i = p;
        t = 0;
        while (i <= r) {
            A[i++] = tmp[t++];
        }
    }

    static void judgeToAnswer(int index, int[] tmp) {
        cnt++;

        if (cnt == K) {
            result = tmp[index];
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_24060_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        mergeSort(arr, 0, N - 1);
        System.out.println(result);
    }
}
