import java.util.*;
import java.io.*;
import java.util.function.Function;

// 20040 : 사이클 게임

/*
-- 전제조건
n 개의 점들이 주어지고, 게임을 하면서, 그 점들을 잇는 것이다.
n 개의 점들은 3개 이상이, 일직선에 위치하는 경우는 없다.
이 말은 즉슨, 한 점과, 한 점이 이어져서 선분이 형성이 될 때, 다른 한점에 걸치는 경우는 존재하지 않는다라는 것이다.

그래서, 게임을 하면서, 사이클이 형성이 되는 경우는, 사이클이 형성됐었던 번째 수를 출력하면 되고,
아니면, 0을 출력하면 된다
-- 틀설계
이 문제는, 그냥 find, union 을 두고, 진행하면서, 계속 find 하면서, 결국 같은 집합인지 아닌지를 확인하고,
정답을 출력하면 되는 것 같다.
단지, 조금 주의 해야 할 점 같은 것은, 입력을 다 받기 전에, 출력이 되어서 끝나면 혹시나 TLE 를 받을 수 있으니까,
아얘 입력을 다 받고, 진행하면 될 것 같다. 그거 이외에는 문제점은 전혀 없어 보인다.
 */
public class Main {
    // 각 정점이 속한 집합
    public static int[] parent;
    // 각 주어지는 선분들
    public static int[][] segment;
    public static int N;
    public static int M;
    public static int res = 0;

    public static int find(int a) {
        if (parent[a] == a) {
            // 최종적으로 부모 반환
            return a;
        }

        // 부모를 찾아가면서, 최종적으로 찾은 부모를 본인의 부모로 집어 넣음
        return parent[a] = find(parent[a]);
    }

    // 집합 a, b 를 합치는 method
    public static void union(int a, int b) {
        // b 의 부모로 a 를 집어넣음, 즉 넘길때에도, 최종적인 부모 정점의 번호를 넘겨야 함
        parent[b] = a;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        M = fun.apply(input[1]); // 각 N, M 값을 주입
        parent = new int[N + 1];
        segment = new int[M + 1][2]; // parent, segment 주입

        for (int i = 0; i < M; i++) {
            // 각 입력을 받고
            input = br.readLine().split(" " );

            segment[i][0] = fun.apply(input[0]);
            segment[i][1] = fun.apply(input[1]);
        } // segment 정보 받음

        // parent 갱신
        for (int i = 1; i <= N; i++) {
            // 본인 정점의 부모는 본인으로 초기화
            parent[i] = i;
        }

        for (int i = 0; i < M; i++) {
            int a = find(segment[i][0]);
            int b = find(segment[i][1]);

            // 둘의 집합이 같음, 그러면 바로 끝남 사이클임
            if (a == b) {
                // 정답을 저장하고, 끝냄
                res = i + 1;
                break;
            } else {
                // 아니면 union 을 함
                union(a, b);
            }
        }

        System.out.println(res);
    }
}
