import java.util.*;
import java.io.*;

/**
 * 소수 찾기 알고리즘에서 해맨 점은 i 로 안나눠서 살짝 해맸었고
 * dfs 로 숫자들 만드는 거는 생각을 꽤 많이 했는데 한번에 성공해서 조금 놀랐음
 * 그리고 중복된 숫자가 많을 수도 있어서 set 을 사용했음
 */
class Solution {
    static int answer;
    static boolean[] visited;
    static int[] numList;
    static HashSet<Integer> set;

    static void judgeMino(int number) {
        if (number <= 1) {
            return;
        }

        boolean mino = true;
        int limit = (int) Math.sqrt(number);

        for (int i = 2; i <= limit; i++) {
            if (number % i == 0) {
                mino = false;
                break;
            }
        }

        if (mino) {
            // System.out.println(number);
            answer++;
        }
    }

    static void dfs(int depth, String num) {
        if (num.length() != 0) {
            set.add(Integer.parseInt(num));
        }

        if (depth == numList.length) {
            return;
        }

        for (int i = 0; i < numList.length; i++) {
            if (!visited[i]) {
                dfs(depth + 1, num); // 선택안하는 경우와

                visited[i] = true;
                dfs(depth + 1, Integer.toString(numList[i]) + num);
                visited[i] = false;
            }
        }
    }

    public int solution(String numbers) {
        // numbers 를 찢어서 각각의 숫자를 만들면 된다.
        answer = 0;
        set = new HashSet<>(); // 생성된 숫자를 저장할 set
        numList = new int[numbers.length()];
        visited = new boolean[numbers.length()];

        for (int i = 0; i < numbers.length(); i++) {
            numList[i] = numbers.charAt(i) - '0';
        }

        dfs(0, "");

        for (Integer number : set) {
            // System.out.println(number);
            judgeMino(number);
        }

        return answer;
    }
}