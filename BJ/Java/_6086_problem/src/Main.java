import java.util.*;
import java.io.*;
import java.util.function.Function;

// 6086 : 최대 유량

/**
 * -- 전제 조건
 * 각 알파벳이 노드 이름이고 알파벳 사이의 가능한 유량이 뒤에 Cost 로 주어진다.
 * 그래서 A to Z 의 최대 유량을 구하라.
 *
 * -- 틀 설계
 * 그냥 최대유량 알고리즘 사용하면 된다.
 *
 * 근데 유의할 점은 평행 간선들이 존재할 수 있고
 * 또 양방향이기 때문에, 역방향 간선들이 존재한다..
 *
 * 계속 정점들을 하나를 더 두어서 봐야하는 것일까?
 *
 * 일단 뭐 HashMap<Character, HashMap> 의 형태로 그래프를 관히할 수 있을 것 같다.
 * 그래서 같은 방향의 간선이 또 나오는 경우에는 더해줄 수 있다 그러면
 *
 * 그리고 반복문 같은 경우는 KeySet 을 통해서 key 를 뽑아낸 뒤에 진행하면 된다.
 *
 * 근데 일단 가장 걸리는 것이 양방향으로 파이프가 흐를 수 있다라는 것이다.
 *
 * 인터넷에 쳐보니까 그냥 양쪽으로 용량 설정해주면 된다라고 하는데
 * 한번 그렇게 해보자.
 *
 * Ascii Code = A ~ Z = 65 ~ 90, a ~ z = 122
 * 그냥 이걸로 하는 게 편할 것 같다.
 * 그렇지 않으면, HashMap 으로 관리하는 거 너무 귀찮앙, 걍 character 로 뽑아내서 - 65 해주면 됨
 *
 * -- 결과
 * 생각보다 그냥 맞았음
 * 이해한게 맞았나봄
 *
 * 이해한대로 그대로 코드를 구성했더니 바로 맞았음
 * 여기서 걸렸던 것은 후진 간선을 선택하게 되는 경우 (양방향이 아닌 경우) map[v][i] == 0 일 수 밖에 없는데 그렇게 하는 게 살짝 걸렸음
 * 근데 그렇다고 List 로 다시 처리하기에는 너무 귀찮았음
 * 근데 결국 맞았으니 개꿀
 *
 * 사실 이 문제 풀고 Test 로 다시 돌려볼려고 했는데
 * 풀다가 이해해버려서 안해도 될 것 같다.
 */
public class Main {
    static int N;
    static int ans = 0;
    static int S = 'A' - 65;
    static int D = 'Z' - 65;
    static int size = 122 - 65 + 1;
    static int[] parent;
    static int[][] map;
    static int[][] flow;

    static void maxFlow() {
        while (true) {
            Queue<Integer> queue = new LinkedList<>();
            Arrays.fill(parent, -1);
            queue.add(S);

            while (!queue.isEmpty() && parent[D] == -1) { // 큐가 비거나, 목적지에 아직 도달하지 못한 경우
                int v = queue.poll();

                for (int i = 0; i < map.length; i++) {
                    if (i != v && map[v][i] != 0 && map[v][i] - flow[v][i] > 0 && parent[i] == -1) {
                        parent[i] = v;
                        queue.add(i);
                    }
                }
            }

            if (parent[D] == -1) {
                break;
            }

            int min = Integer.MAX_VALUE;
            for (int i = D; i != S; i = parent[i]) {
                min = Math.min(min, map[parent[i]][i] - flow[parent[i]][i]);
            }

            for (int i = D; i != S; i = parent[i]) {
                flow[parent[i]][i] += min;
                flow[i][parent[i]] -= min;
            }

            ans += min;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        map = new int[size][size];
//        capacity = new int[size][size];
        flow = new int[size][size];
        parent = new int[size];

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            int a = input[0].charAt(0) - 65;
            int b = input[1].charAt(0) - 65;
            int cost = fun.apply(input[2]);

            map[a][b] += cost;
            map[b][a] += cost;
        }

        maxFlow();
        System.out.println(ans);
    }
}
