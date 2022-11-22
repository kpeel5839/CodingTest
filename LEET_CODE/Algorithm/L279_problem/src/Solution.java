import java.util.*;

// 279 : Perfect Squares

class Solution {
    public int numSquares(int n) {
        // 일단 N 을 보고 Perfect Square 로 n 이하까지는 다 채워주어야함
        // 그 이후에는 그냥 그것들을 가지고 ++ 를 해주고, 진행하면 된다.

        // Fill Perfect Square Index
        int number = 1;
        int[] dynamic = new int[n + 1];
        Arrays.fill(dynamic, Integer.MAX_VALUE);

        while (true) {
            int perfectSquareNumber = (int) Math.pow(number, 2);
            if (n < perfectSquareNumber) {
                break;
            }

            dynamic[perfectSquareNumber] = 1;
            number++;
        }

        dynamic[0] = 0;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= i / 2; j++) {
                dynamic[i] = Math.min(dynamic[i], dynamic[j] + dynamic[i - j]);
            }
        }

        return dynamic[n];
    }
}