import java.util.*;
import java.io.*;

// 11378 : 열혈강호 4

/**
 * Example
 * 5 5 1
 * 5 1 2 3 4 5
 * 1 1
 * 1 1
 * 1 1
 * 2 1 5
 */
public class Main {
    static int N;
    static int M;
    static int K;
    static int[] occupy;
    static boolean[] visited;
    static List<ArrayList<Integer>> graph = new ArrayList<>();


    static boolean binaryMaxFlow(int current) {
        if (visited[current]) {
            return false;
        }

        visited[current] = true;

        for (Integer next : graph.get(current)) {
            if (occupy[next] == 0 || binaryMaxFlow(occupy[next])) {
                occupy[next] = current;
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_11378_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        occupy = new int[M + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int numberOfCanDoWork = Integer.parseInt(st.nextToken());

            for (int j = 0; j < numberOfCanDoWork; j++) {
                graph.get(i).add(Integer.parseInt(st.nextToken()));
            }
        }

        int numberOfPossibleDoItWork = 0;
        for (int i = 1; i <= N; i++) {
            visited = new boolean[N + 1];

            if (binaryMaxFlow(i)) {
                numberOfPossibleDoItWork++;
            }
        }

        int workerNumber = 1;
        while (workerNumber <= N && K != 0) {
            visited = new boolean[N + 1];
            if (binaryMaxFlow(workerNumber)) {
                numberOfPossibleDoItWork++;
                K--;
            } else {
                workerNumber++;
            }
        }

        System.out.println(numberOfPossibleDoItWork);
    }
}
