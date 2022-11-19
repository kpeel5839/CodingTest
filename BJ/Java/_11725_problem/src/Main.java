import java.util.*;
import java.io.*;

// 11725 : 트리의 부모찾기

/**
 * 예제
 * 7
 * 1 6
 * 6 3
 * 3 5
 * 4 1
 * 2 4
 * 4 7
 */
public class Main {

    static int N;
    static int[] parentOfThisNode;
    static List<ArrayList<Integer>> graph = new ArrayList<>();

    static void treeOrder(int parent, int current) { // parent 로만 안가면 절대 무한루프 안걸림
        parentOfThisNode[current] = parent;
        for (Integer nextVertex : graph.get(current)) {
            if (nextVertex != parent) {
                treeOrder(current, nextVertex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11725_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        // 트리를 만들고, 순회하면서 다른 정점으로 갈 때에 그 정점의 부모에다가 본인을 집어넣어버렷
        N = Integer.parseInt(br.readLine());
        parentOfThisNode = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int firstVertex = Integer.parseInt(st.nextToken());
            int secondVertex = Integer.parseInt(st.nextToken());

            graph.get(firstVertex).add(secondVertex);
            graph.get(secondVertex).add(firstVertex);
        }

        treeOrder(-1, 1); // 1번으로 가면서
        for (int vertex = 2; vertex <= N; vertex++) {
            bw.write(parentOfThisNode[vertex] + "\n");
        }

        bw.flush();
        bw.close();
    }
}
