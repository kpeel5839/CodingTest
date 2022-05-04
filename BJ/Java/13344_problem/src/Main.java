import java.util.*;
import java.io.*;
import java.util.function.Function;

// 13344 : Chess Tournament

/**
 * -- 전제 조건
 * chess 한 사람들끼리의 순위가 주어지는데,
 * 전체적인 순위가 주어지는 것이 아닌, 상대적인 순위들만 주어진다.
 *
 * 그래서, 각 선수의 번호와 symbol 들이 주어지는데, 그 symbol 은 '=', '>' 밖에 없음
 * 이렇게 주어졌을 때, 주어진 것이 말이 안되면 inconsistent, 말이 되면 consistent 를 출력하면 된다.
 * -- 틀 설계
 * 일단, 먼저 == 들만 뽑아내서 집합들을 만든다.
 * 그 다음에, 대표 집합들로만 그래프를 그려낸다 (물론 다른 요소들도 있지만, 대표 들로만 graph 를 구성한다.)
 * 그리고서, compare 에 있는 요소는 0 번째 -> 1 번째 요소로 해서, 진입차선을 가지는 애가 더 순위가 낮은 걸로 구성을 한다.
 *
 * 일단, 입력을 받으면서 input[1] 이 '=' 이면, 바로 find, union 연산을 진행하면 된다.
 * 그리고서, 이제 다음에 compare 에 담아놓은 것을 순서대로 진행을 하게 되는데
 * 그 때, 같은 집합이 있다면? 바로 end 에다가 true 로 집어넣고, 끝낸다.
 *
 * 근데, 그게 아니라 잘 지나가서, 잘 되었다면
 * 이제 parent[i] == i 인 애들만 entry 0 인지 확인하고 queue 에다가 집어넣는다.
 *
 * 그 다음에 queue.isEmpty() 해질 때까지, 진행한다음에
 * visited 안된 애가 없다면 consistent
 * 있다면 inconsistent 하면 된다.
 */
public class Main {
    public static int N;
    public static int M;
    public static boolean end = false;
    public static int[] parent;
    public static int[] entry;
    public static List<HashSet<Integer>> graph = new ArrayList<>();
    public static List<int[]> compare = new ArrayList<>(); // compare 할 애들을 순서대로 담는다. [0] -> [1] 로 구성을 하면 된다.
    public static Queue<Integer> queue = new LinkedList<>();
    public static boolean[] visited;

    public static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            return parent[a] = find(parent[a]);
        }
    }

    public static void union(int a, int b) {
        parent[b] = a; // 부모로 만들어줌
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        M = fun.apply(input[1]);
        parent = new int[N];
        visited = new boolean[N];
        entry = new int[N];

        for (int i = 0; i < N; i++) {
            graph.add(new HashSet<>());
            parent[i] = i;
        } // 초기화 완료

        for (int i = 0; i < M; i++) {
            input = br.readLine().split(" ");
            if (input[1].equals(">")) { // compare 인 경우
                compare.add(new int[] {fun.apply(input[0]), fun.apply(input[2])});
            } else { // equals 인 경우
                int a = find(fun.apply(input[0]));
                int b = find(fun.apply(input[2])); // 변환과 동시에 바로 부모까지 찾음

                union(a, b);
            }
        }

        for (int[] edge : compare) { // 인접행렬로 하면 안됨... 생각해보니까, graph 를 인접행렬로 구현할 시에는, 25 억임.. HashMap으로 해야할 것 같은데?
            int a = find(edge[0]);
            int b = find(edge[1]); // 두 요소의 부모 집합을 찾음

            // a -> b 인데, 여기서 만약에 a -> b 가 이미 존재하면 상관이 없음, 근데 b -> a 가 존재하면 상관이 있음, 그러면 틀린 결과니까
            if (graph.get(b).contains(a)) {
                end = true;
                break;
            } // 위의 상황 이외의 상황은, 딱히 상관 없음, 왜냐하면 graph[a][b] 는 이미 존재해도 됨, 짜피 맞음

            if (!graph.get(a).contains(b)) { // 이미 1이라면? 그냥 안하면 됨
                graph.get(a).add(b);
                entry[b]++; // 진입차선 늘려줌
            }
        }

        if (!end) { // 끝난 것이 아닐 때, 진행
            for (int i = 0; i < N; i++) {
                if (parent[i] == i && entry[i] == 0) { // 집합의 주축이면서, entry가 0인 것
                    queue.add(i);
                }
            }

            while (!queue.isEmpty()) { // 위상정렬 순서대로 시작
                int a = queue.poll();
                visited[a] = true;

                for (Integer i : graph.get(a)) {
                    if (--entry[i] == 0) { // entry 를 감소시켜서 0이 되는 애들은
                        queue.add(i); // 담아줌
                    }
                }
            }

            for (int i = 0; i < N; i++) {
                if (parent[i] == i) {
                    if (!visited[i]) { // visited 안 찍힌애 있으면 끝난 것임
                        end = true;
                        break;
                    }
                }
            }
        }

//        System.out.println(Arrays.toString(parent));
//        System.out.println(Arrays.toString(visited));

        if (end) {
            System.out.println("inconsistent");
        } else {
            System.out.println("consistent");
        }
    }
}
