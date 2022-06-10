import java.util.*;
import java.io.*;

class Solution {
    static int[][] condition; // 조건들
    static int[] friend; // 친구들 선 순서
    static char[] friendList;
    static boolean[] visited; // 이미 할당된 숫자
    static int count;

    static void dfs(int depth) {
        if (depth == 8) {
            // count++;
            // System.out.println(Arrays.toString(friend));
            if (checkCondition()) {
                count++;
            }

            return;
        }

        for (int i = 0; i < 8; i++) {
            if (!visited[i]) {
                friend[depth] = i;
                visited[i] = true;
                dfs(depth + 1);
                friend[depth] = 0;
                visited[i] = false; // 다시 돌려놓는다.
            }
        }
    }

    static boolean checkCondition() { // 조건에 맞춰서 (condition) 친구들이 얼마나 떨어져있는지 계산하고 맞는지 확인한다음에 틀리면 false 반환 끝까지 안틀리면 true 반환
        // 가공해놓은 condition 을 가지고 연산을 진행할 것이다.
        for (int i = 0; i < condition.length; i++) {
            int a = condition[i][0];
            int b = condition[i][1];

            // a, b 를 비교하여서 진행해야 한다.
            int c = condition[i][3] - '0';
            int operation = condition[i][2];
            int diff = Math.abs(friend[a] - friend[b]) - 1;

            if (operation == '=') { // 둘의 간격이 c 와 같아야한다.
                if (!(diff == 0)) { // 간격이 1이라면 맞는것임 붙어있는 것임
                    return false;
                }
            } else if (operation == '>') { // 둘의 간격이 c 초과여야함
                if (!(diff > c)) {
                    return false;
                }
            } else { // 둘의 간격이 c 미만이여야함
                if (!(diff < c)) {
                    return false;
                }
            }
        }

        return true;
    }

    public int solution(int n, String[] data) {
        friendList = new char[] {'A','C','F','J','M','N','R','T'};
        condition = new int[n][4]; // 두 사람, 조건, 숫자
        friend = new int[8];
        visited = new boolean[8];
        count = 0;

        for (int i = 0; i < n; i++) {
            char a = data[i].charAt(0);
            char b = data[i].charAt(2);

            for (int j = 0; j < 8; j++) {
                if (friendList[j] == a) { // 두 개가 무조건 다르니까 이게 가능하다.
                    a = (char) j; // char 로 변경
                } else if (friendList[j] == b) {
                    b = (char) j;
                }
            }

            condition[i][0] = a;
            condition[i][1] = b;
            condition[i][2] = data[i].charAt(3);
            condition[i][3] = data[i].charAt(4);
        }

        System.out.println(condition.length);
        dfs(0);
        int answer = count;

        return answer;
    }
}