import java.util.*;
import java.io.*;

class Solution2 {
    public int cnt = 0;
    public char[] friendList = new char[] {'A','C','F','J','M','N','R','T'};

    public void dfs(int depth, int[] friend, boolean[] visited, String[] data) {
        if (depth == 8) {
            if (checkCondition(friend, data)) {
                cnt++;
            }

            return;
        }

        for (int i = 0; i < 8; i++) {
            if (!visited[i]) {
                friend[depth] = i;
                visited[i] = true;
                dfs(depth + 1, friend, visited, data);
                visited[i] = false; // 다시 돌려놓는다.
            }
        }
    }

    public boolean checkCondition(int[] friend, String[] data) { // 조건에 맞춰서 (condition) 친구들이 얼마나 떨어져있는지 계산하고 맞는지 확인한다음에 틀리면 false 반환 끝까지 안틀리면 true 반환
        boolean res = true;

        for (String line : data) {
            char c1 = line.charAt(0);
            char c2 = line.charAt(2);

            int a = 0;
            int b = 0;

            for (int i = 0; i < friendList.length; i++) {
                if (friendList[i] == c1) {
                    a = i;
                } else if (friendList[i] == c2) {
                    b = i;
                }
            }

            // a, b 를 비교하여서 진행해야 한다.
            int c = line.charAt(4) - '0';
            char operation = line.charAt(3);
            int diff = Math.abs(friend[a] - friend[b]) - 1;

            switch (operation) {
                case '=':
                    if (diff != 0) { // 간격이 1이라면 맞는것임 붙어있는 것임
                        res = false;
                    }
                    break;
                case '>':
                    if (diff <= c) {
                        res = false;
                    }
                    break;
                case '<' :
                    if (diff >= c) {
                        res = false;
                    }
                    break;
            }
        }

        return res;
    }

    public int solution(int n, String[] data) {
        int answer = 0;
        int[] friend = new int[8];
        boolean[] visited = new boolean[8];
        cnt = 0;

        dfs(0, friend, visited, data);
        answer = cnt;

        return answer;
    }
}