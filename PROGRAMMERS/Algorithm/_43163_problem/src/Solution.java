import java.util.*;
import java.io.*;

/**
 * 아이디어만 떠올린다면 정말 쉬운 문제이다.
 * 그냥 bfs 문제랑 다를 바가 없는 문제
 */
class Solution {
    static int S = 0;
    static int D = 0;
    static int SIZE;
    static int ans = 0;
    static String[] word;
    static boolean[] visited;
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    static void bfs() {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {0, 0}); // {해당 정점, 해당 지점까지 오는데 걸린 거리}
        visited[0] = true;

        while (!queue.isEmpty()) {
            int[] v = queue.poll();

            if (v[0] == D) {
                ans = v[1];
                return;
            }

            for (Integer next : graph.get(v[0])) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.add(new int[] {next, v[1] + 1});
                }
            }
        }
    }

    static void match(int v1, int v2) {
        // s1 -> s2 가 가능한지, 즉 한글자만 다른지를 보는 것이다.
        int count = 0;
        String s1 = word[v1];
        String s2 = word[v2];

        if (s1.length() != s2.length()) { // 애초에 길이가 같아야 함
            return;
        }

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                count++;
            }
        }

        if (count == 1) {
            graph.get(v1).add(v2);
            graph.get(v2).add(v1);
        }
    }

    public int solution(String begin, String target, String[] words) {
        /**
         1. words -> 새로운 array list begin 도 포함
         2. begin 을 1 로 고정, Start를 의미하는 S
         3. array list 에서 Destination 을 의미하는 D 를 찾는다. 즉, target 을 찾아
         4. target 없으면 return 0;
         5. 총 사이즈 words.size + begin 이니 array 선언후
         6. 순서대로 한 글자만 다른 경우가 있는지 확인하면서 간선을 이어나간다 (1글자만 다른지 비교하는 method 가 필요하다)
         7. 간선을 이었으면 그냥 bfs
         */
        SIZE = 1 + words.length;
        visited = new boolean[SIZE];

        for (int i = 0; i < SIZE; i++) {
            graph.add(new ArrayList<>());
        }

        word = new String[SIZE];
        word[0] = begin;

        for (int i = 0; i < words.length; i++) {
            word[i + 1] = words[i];

            if (words[i].equals(target)) {
                D = i + 1; // Destination 정점 번호를 저장
            }
        }

        if (D == 0) {
            return ans;
        }

        for (int i = 0; i < word.length - 1; i++) {
            for (int j = i + 1; j < word.length; j++) {
                match(i, j);
            }
        }

        bfs();

        return ans;
    }
}