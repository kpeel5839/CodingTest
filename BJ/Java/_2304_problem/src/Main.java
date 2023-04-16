import java.util.*;
import java.io.*;

// 2304 : 창고 다각형

/**
 * Example
 * 7
 * 2 4
 * 11 4
 * 15 8
 * 4 6
 * 5 3
 * 8 10
 * 13 6
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_2304_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int[][] arr = new int[N][2];
        int max = 0;
        int maxIndex = 0;

        for (int i = 0; i < arr.length; i++) {
            st = new StringTokenizer(br.readLine());

            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr, (o1, o2) -> o1[0] - o2[0]);

        for (int i = 0; i < arr.length; i++) {
            if (max <= arr[i][1]) {
                max = arr[i][1];
                maxIndex = i;
            }
        }

        Stack<int[]> stack = new Stack<>();
        int ans = 0;

        for (int i = 0; i < maxIndex; i++) {
            if (!stack.isEmpty()) {
                int[] v = stack.peek();

                if (v[1] <= arr[i][1]) {
                    stack.push(arr[i]);
                }
            } else {
                stack.add(arr[i]);
            }
        }

        int pre = arr[maxIndex][0];
        while (!stack.isEmpty()) {
            int[] pop = stack.pop();
//            System.out.println(Arrays.toString(pop));
            ans += (pre - pop[0]) * pop[1];
            pre = pop[0];
//            System.out.println(ans);
        }

        for (int i = arr.length - 1; i != maxIndex; i--) {
            if (!stack.isEmpty()) {
                int[] v = stack.peek();

                if (v[1] <= arr[i][1]) {
                    stack.push(arr[i]);
                }
            } else {
                stack.add(arr[i]);
            }
        }

        pre = arr[maxIndex][0];
        while (!stack.isEmpty()) {
            int[] pop = stack.pop();
//            System.out.println(Arrays.toString(pop));
            ans += (pop[0] - pre) * pop[1];
            pre = pop[0];
//            System.out.println(ans);
        }

//        System.out.println(ans);

        System.out.println(ans + arr[maxIndex][1]);
    }
}