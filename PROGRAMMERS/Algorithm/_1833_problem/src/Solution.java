import java.util.*;
import java.io.*;

class Solution {
    static HashSet<Integer> ySet;
    static HashSet<Integer> xSet;
    static HashMap<Integer, Integer> yMap;
    static HashMap<Integer, Integer> xMap;
    static int[][] dp;

    static boolean judge(int[] p1, int[] p2) { // 여기에서 가능하면 true, 아니면 false 를 반환할 것임
        int maxX = xMap.get(Math.max(p1[0], p2[0]));
        int maxY = yMap.get(Math.max(p1[1], p2[1]));
        int minX = xMap.get(Math.min(p1[0], p2[0]));
        int minY = yMap.get(Math.min(p1[1], p2[1])); // 바로 배열의 인덱스 값으로 변환

        // System.out.println(Arrays.toString(p1));
        // System.out.println(Arrays.toString(p2));
        // System.out.println("minX, minY : " + minX + " " + minY);
        // System.out.println("maxX, maxY : " + maxX + " " + maxY);

        if (maxX == minX || minY == maxY) { // 겹치는 좌표가 있기 때문에 직사각형의 넓이가 0이라 안됨
            // System.out.println("넓이 0 !");
            return false;
        }

        int res = dp[maxY - 1][maxX - 1] - dp[minY][maxX - 1] - dp[maxY - 1][minX] + dp[minY][minX];
        // System.out.println("이 두개 사이에 쐐기의 개수 : " + res);

        return res == 0; // res == 0 이면 가능한 것이니 true, 아니면 false
    }

    public int solution(int n, int[][] data) {
        ySet = new HashSet<>();
        xSet = new HashSet<>();
        xMap = new HashMap<>();
        yMap = new HashMap<>();
        int ans = 0;

        xSet.add(-1);
        ySet.add(-1); // 사이즈를 하나 더 늘리기 위함
        for (int i = 0; i < data.length; i++) {
            xSet.add(data[i][0]);
            ySet.add(data[i][1]);
        }

        List<Integer> yList = new ArrayList<>();
        List<Integer> xList = new ArrayList<>();
        int[] xArr = new int[xSet.size()];
        int[] yArr = new int[ySet.size()];

        int index = 0;
        for (Integer value : xSet) {
            xArr[index++] = value;
        }

        index = 0;
        for (Integer value : ySet) {
            yArr[index++] = value;
        }

        Arrays.sort(xArr);
        Arrays.sort(yArr); // 이제 xArr, yArr 순서대로 돌면서 HashMap 만들고 dp 에다가 1 씩만 더해주면 됨

        for (int i = 0; i < xArr.length; i++) {
            xMap.put(xArr[i], i); // 실제 값 -> 인덱스 맵핑 관계 형성
        }

        for (int i = 0; i < yArr.length; i++) {
            yMap.put(yArr[i], i);
        }

        dp = new int[yMap.size()][xMap.size()];
        for (int i = 0; i < data.length; i++) {
            int x = xMap.get(data[i][0]);
            int y = yMap.get(data[i][1]);

            dp[y][x]++;
        }

        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[i].length; j++) { // -1 을 채워놨기 때문에 가능함 1 부터 시작해도
                dp[i][j] += (dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1]);
            }
        } // dp 값까지 준비 완료

        // for (int i = 0; i < dp.length; i++) {
        //     for (int j = 0; j < dp[i].length; j++) {
        //         System.out.print(dp[i][j] + " ");
        //     }
        //     System.out.println();
        // }

        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (judge(data[i], data[j])) {
                    ans++;
                }
            }
        }

        return ans;
    }
}