import java.util.*;
import java.io.*;

// 1517 : 버블 소트

/**
 * Example
 * 7
 * 1 5 2 3 6 2 3
 */
public class Main2 {
    public static int N;
    public static int[] number;

    public static long merge(int left, int right) {
        if (right <= left) {
            return 0;
        }

        int mid = (left + right) / 2;
        long count = merge(left, mid) + merge(mid + 1, right);
        count += mergeSort(left, right, mid);

        return count;
    }

    public static long mergeSort(int left, int right, int mid) {
        int leftIndex = left;
        int rightIndex = mid + 1;
        int[] tempNumber = new int[right - left + 1];
        int tempIndex = 0;
        long count = 0;

        while (leftIndex <= mid && rightIndex <= right) {
            if (number[leftIndex] > number[rightIndex]) {
                count += (rightIndex - tempIndex);
                tempNumber[tempIndex++] = number[rightIndex++];
            } else {
                tempNumber[tempIndex++] = number[leftIndex++];
            }
        }

        while (leftIndex <= mid) {
            tempNumber[tempIndex++] = number[leftIndex++];
        }

        while (rightIndex <= right) {
            tempNumber[tempIndex++] = number[rightIndex++];
        }

        tempIndex = 0;
        for (int i = left; i <= right; i++) {
            number[i] = tempNumber[tempIndex++];
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1517_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        number = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            number[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(merge(0, N - 1));
    }
}
