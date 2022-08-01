import java.util.*;
import java.io.*;

// 티켓으로 갈 수 있는 방향대로만 갈 수 있다라는 것을 간과하고 있었음
// 그래서 dfs 로 이동하면서, res.add 방문하는 정점에 대해서 담아주고, 실패하는 경우, 즉 이 방향으로 오면 더 이상 갈 수 없는 경우에
// 체크 아웃을 해주며, res.remove 를 해주어서 답에서 빼주었고, 아예 끝난 경우에는 모두 end == true 가 되어서, 끝날 수 있도록 하였음

class Solution {
    static boolean end = false;
    static boolean[] visited;
    static List<String> res = new ArrayList<>();
    static HashMap<String, ArrayList<Integer>> map = new HashMap<>();

    static void dfs(String a, String[][] tickets, int count) { // a 는 vertex 의 이름
        if (end) {
            return;
        }

        res.add(a);

        if (count == tickets.length) {
            end = true;
            return;
        }

        for (Integer next : map.get(a)) {
            if (!visited[next]) {
                visited[next] = true;
                dfs(tickets[next][1], tickets, count + 1);
                visited[next] = false;
            }
        }

        if (!end) {
            res.remove(res.size() - 1);
        }
    }

    public String[] solution(String[][] tickets) {
        for (int i = 0; i < tickets.length; i++) {
            String a = tickets[i][0];
            String b = tickets[i][1];

            if (!map.containsKey(a)) {
                map.put(a, new ArrayList<>());
            }

            if (!map.containsKey(b)) {
                map.put(b, new ArrayList<>());
            }

            map.get(a).add(i);
        }

        visited = new boolean[tickets.length];

        for (String key : map.keySet()) {
            List<Integer> list = map.get(key);

            Collections.sort(list, (o1, o2) -> tickets[o1][1].compareTo(tickets[o2][1]));
        }

        dfs("ICN", tickets, 0);

        System.out.println(res);
        return res.stream().toArray(String[]::new);
    }
}