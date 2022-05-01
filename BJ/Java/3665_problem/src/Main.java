import java.util.*;
import java.io.*;
import java.util.function.Function;

// 3665 : 최종 순위

/**
 * -- 전제 조건
 * 여러가지의 테스트 케이스들이 주어지고,
 * 각 테스트 케이스들은 순위와 바뀐 순위들이 주어진다.
 * 그래서, 순위를 예측할 수 있는 경우는 바뀌어진 최종 순위를 출력하면 되고 (1등 부터)
 * 만일 최종순위를 예측할 수가 없다면 ? 를 출력하고, 데이터에 일관성이 없다면 IMPOSSIBLE 을 출력하면 된다.
 * -- 틀 설계
 * 일단, 위상정렬을 이용해야 한다.
 * 하나의 정점은 본인보다 낮은 순위들에게 간선을 연결해서
 * 얘내들은 나보다 낮은 애들이다, 즉 내가 순위가 먼저 발표되고 나서야 순위를 발표 가능하다라는 것을 명시해야 한다.
 * 그렇기 위해서는, 그래프를 사용해야 한다.
 *
 * 그리고, 이제 순위를 바꿀때에는, 간선의 방향만 바꾸면된다.
 * 두개의 정점이 이어진 간선만 바꾸면?
 *
 * 두개의 순위를 바꿀 수 있다.
 * 하지만, 여기서 문제가 발생할 수 있다.
 *
 * 첫번째, 순위가 바꾸려고 하는데 지금 데이터가 들어오는 것은 상대적인 등수가 바뀐 애들만 들어온다.
 * 하지만, 이미 이렇게 되어 있는, 즉 순위가 그대로인 데이터가 들어오면 바로 끝내고 IMPOSSIBLE 을 출력하면 된다.
 *
 * 그리고, 또 만일 모두 정리를 해서, 출력을 해놨는데,
 * 만일 담겨지지 않은 것이 있다면?
 * 즉 queue.isEmpty 하지만, 아직 방문하지 않은 정점이 존재한다면, ? 를 출력하면된다.
 *
 * 그리고, 이 모든 것은 visited, 배열을 만들고 Hash 로 간선을 잇는 것도 생각해보았지만, 그것은 말이 안되는 것 같고
 * 아얘 인접행렬로 관리하여서,
 * (2, 4) 가 입력 값으로 들어왔다고 가정하면 4 -> 2 의 간선을,
 * 2 -> 4 로 바꿔야 하니까, graph[4][2] 를 0으로 바꾸고, graph[2][4] 를 1로 바꾸어야 한다.
 * 이 과정에서, graph[2][4] 가 먼저 1이라든가, 아니면 graph[4][2] 가 먼저 0이라면?
 *
 * 바로 반복을 끝내고, IMPOSSIBLE 을 출력하면 된다.
 *
 * -- 결론
 * 뭐야 이거 왜 맞아?
 * 그냥 불 가능한 경우가 IMPOSSIBLE 이였던 듯, 순위 정하지 못하고 자시고 ? 나오는 거 없음
 *
 * 그래서 처음에 해맸던 점으로는 a, b 가 서로의 상대적인 등수가 바뀌는 것이 였는데,
 * 나는 무조건 a, b 가 들어오면 a 가 b 보다 상대적인 순위가 우세해진다라는 것으로 보았고,
 * 그게 아니라 그냥 a, b 가 순위가 바뀐다라는 것이였음
 *
 * 그리고, 이상하게 사이클이 생기는 경우가 ? 인 줄 알았는데 그게 IMPOSSIBLE 이었음, 그래서 그거를 바꾸니까
 * 맞았음
 */
public class Main {
    public static StringBuilder res;
    public static int T;
    public static int N;
    public static int K;
    public static Queue<Integer> queue;
    public static boolean[] visited;
    public static int[] sequence; // 순서들을 저장할 배열
    public static int[] entry; // 진입 차선을 정리
    public static int[][] graph; // 인접행렬 그래프

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        Function<String, Integer> fun = Integer:: parseInt;

        T = fun.apply(br.readLine());

        while (T-- > 0) { // T번 만큼 반복
            N = fun.apply(br.readLine());

            res = new StringBuilder(); // 처음 초기화
            queue = new LinkedList<>();
            visited = new boolean[N + 1];
            sequence = new int[N];
            entry = new int[N + 1];
            graph = new int[N + 1][N + 1]; // 인접행렬 초기화

            String[] input = br.readLine().split(" ");
            for (int i = 0; i < N; i++) {
                sequence[i] = fun.apply(input[i]); // 입력 받고
            }

            // 여기서, 이제 graph 채워준다.
            for (int i = 0; i < N - 1; i++) {
                for (int j = i + 1; j < N; j++) {
                    // i -> j 를 해주어야 한다, 그리고 entry 도 높여줌
                    graph[sequence[i]][sequence[j]] = 1; // graph 이어주고
                    entry[sequence[j]]++; // j 노드는 진입차선이 늘은 것이니까, entry++
                }
            }

            K = fun.apply(br.readLine()); // 순위가 바뀐 쌍을 받을 차례

            for (int i = 0; i < K; i++) {
                input = br.readLine().split(" "); // 첫번째 요소가, 뒤에 요소보다 순위가 높은걸로 바뀌었다 라는 뜻

                if (res.length() != 0) { // 이미, res 가 차있으면, 즉 IMPOSSIBLE 로 차있는 경우
                    continue;
                }

                int a = fun.apply(input[0]);
                int b = fun.apply(input[1]);

                // b -> a 에서, a -> b 로 바뀌었다라는 것임, 그러면 이전에는 graph[a][b] 는 0, graph[b][a] 는 1 이 였어야 한다.
                if (graph[a][b] == 0 && graph[b][a] == 1) { // b -> a 였을 때
                    // 이 조건이 맞음 만약, 그러면 이제 entry 반대로 만들어줘야 함
                    graph[a][b] = 1;
                    graph[b][a] = 0;
                    entry[a]--; // a 로 들어오는 진입 차선은 없어지고
                    entry[b]++; // b 로 들어오는 진입 차선이 늘어난 것
                } else if (graph[a][b] == 1 && graph[b][a] == 0) { // a -> b 였을 때
                    // 이 조건이 맞음 만약, 그러면 이제 entry 반대로 만들어줘야 함
                    graph[a][b] = 0;
                    graph[b][a] = 1;
                    entry[a]++; // a 로 들어오는 진입 차선은 잇어지고
                    entry[b]--; // b 로 들어오는 진입 차선이 없어진 것
                } else { // 이 조건이 안맞으면
                    res.append("?"); // impossible 을 넣어놓는다.
                    continue;
                }
            }

            if (res.length() == 0) { // res 에 아직 아무것도 안들어있을 때, 즉 앞에서 걸리지 않았을 때
                for (int i = 1; i <= N; i++) { // 0 인애들 큐에다가 담아줌, 진입차선 0 인애
                    if (entry[i] == 0) {
                        queue.add(i);
                        visited[i] = true; // 방문 처리도
                    }
                }

                while (!queue.isEmpty()) {
                    int a = queue.poll(); // 현재 실행해야할, 정점 찾음
                    res.append(a + " "); // 출력에 추가

                    for (int i = 1; i <= N; i++) { // 본인과 연결되어 있던 애들, 즉 1인 애들 entry[next]-- 해줌, 그리고 0인 애들은 담음
                        if (graph[a][i] == 1) { // 연결되어 있는 애들만
                            if (--entry[i] == 0) { // entry[i] 가 0이 되는 애들은 queue 에다가 담아줌과 동시에, visit 처리를 해줌, 큐에다가 담을 때, visit 하는 것을 규칙으로함
                                queue.add(i);
                                visited[i] = true;
                            }
                        }
                    }
                }

                for (int i = 1; i <= N; i++) { // 정상적으로 visited 처리 다 되었나 확인
                    if (!visited[i]) { // 만약 방문 안된 애가 있었으면, 출력이 얘로 바뀜
                        res = new StringBuilder("IMPOSSIBLE");
                    }
                }
            }

            output.write(res + "\n");
        }

        output.flush();
        output.close();
    }
}
