import java.util.*;
import java.io.*;

class Solution {
    static int N;
    static int preIndex = 0;
    static int postIndex = 0;
    static int depth;
    static Info[] info;
    static int[] startDepth; // 해당 index depth 의 시작 노드를 가르킬 것임
    static int[][] answer;
    static List<Node> graph = new ArrayList<>();

    static class Node {
        int num;
        Node left;
        Node right;

        Node(int num) {
            this.num = num;
        }
    }

    static class Info {
        int y;
        int x;
        int index;

        Info(int y, int x, int index) {
            this.y = y;
            this.x = x;
            this.index = index;
        }

        @Override
        public String toString() {
            return "y : " + y + " x : " + x;
        }
    }

    static void makeTree(int d, int cur, int curX, int left, int right) {
        // 현재 depth + 1 부터 탐색함
        if (d == depth - 1) { // 현재 depth 가 depth - 1 즉, 마지막 depth 라면? 그러면 return 해준다
            return;
        }

        Node node = graph.get(cur);
        int start = startDepth[d + 1];
        int end = (d + 1) == (startDepth.length - 1) ? info.length - 1 : startDepth[d + 2] - 1; // end 도 지정해준다.

        for (int i = start; i <= end; i++) {
            Info inf = info[i];

            if (left <= inf.x && inf.x <= right) { // 일단 이 범위안에 들어오고
                if (inf.x < curX) {
                    // 현재 curX 노드의 왼쪽 자식으로
                    node.left = graph.get(inf.index); // left 로 현재 index 의 Node 를 넣어준다.
                    makeTree(d + 1, inf.index, inf.x, left, curX - 1);
                } else {
                    // 현재 curX 노드의 오른쪽 자식으로
                    node.right = graph.get(inf.index); // left 로 현재 index 의 Node 를 넣어준다.
                    makeTree(d + 1, inf.index, inf.x, curX + 1, right);
                }
            }
        }
    }

    static void order(int cur) {
        Node node = graph.get(cur);
        answer[0][preIndex] = cur;
        preIndex++;

        if (node.left != null) {
            order(node.left.num);
        }

        if (node.right != null) {
            order(node.right.num);
        }

        answer[1][postIndex] = cur;
        postIndex++;
    }

    public int[][] solution(int[][] nodeinfo) {
        N = nodeinfo.length; // 노드의 개수
        answer = new int[2][N]; // 답을 담을 answer 리스트    

        info = new Info[N];
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < N; i++) {
            set.add(nodeinfo[i][1]); // 높이를 알기 위해서 set 을 선언
            info[i] = new Info(nodeinfo[i][1], nodeinfo[i][0], i + 1);
        }

        Arrays.sort(info, (o1, o2) -> {
            if (o1.y == o2.y) { // y 값이 같은 경우 x 로 오름차순 정렬
                return o1.x - o2.x;
            }

            return o2.y - o1.y;
        });

        depth = set.size();
        startDepth = new int[depth];
        int index = 0;
        int pre = -1;
        for (int i = 0; i < N; i++) { // 여기서 이제 depth 의 레베루를 먹일 것이다.
            if (pre != info[i].y) {
                startDepth[index] = i; // i 번째에 있다.
                pre = info[i].y; // y 값을 넣어주고
                index++;
            }
        }

        for (int i = 0; i <= N; i++) {
            graph.add(new Node(i)); // 각 노드 다 선언해준다.
        }

        makeTree(0, info[0].index, info[0].x,  0, 100000); // 0 ~ 10 만의 범위로 넘겨준다.
        order(info[0].index);

        return answer;
    }
}