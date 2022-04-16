import java.util.*;
import java.io.*;

// 1516 : 게임 개발

/*
-- 전제조건
게임의 건물을 짓는데는 순서가 존재하고
그 순서대로 건물을 지었을 때 , 걸리는 최종시간을 구해라.

-- 틀설계
그냥 간단하게 위상정렬과 dp 를 이용한 문제인 것 같다.
이전에 엄청나게 비슷한 ACM CRAFT? 문제를 푼 것 같다.

일단 각각 줄이 각 건물의 정보인데
첫번째 값은 건물을 짓는데 걸리는 시간이고
나머지는 이 건물을 짓기 전에 먼저 지어야 하는 건물들이다.

일단 위상정렬은 당연히 사용하는 부분이고,
dp적인 부분을 이용하여야 하는데

음 일단 , 해당 건물을 짓기 까지는 앞의 건물들이 선수로 먼저 지어져야 한다.
그리고 , 그 건물 중 가장 늦게 지어지는 건물을 기준으로 한다.
이것을 처음부터의 개념으로 진행을 한다.
그러면서 해당 인덱스에다가 그 값을 집어넣는다.
이러면 된다.

예제를 진행해보자.
1번째 10초가 걸린다.
2번째 1번째를 선수로 한다.
10 + 10 = 20 초가 걸린다.
3 번째 1번째를 선수로 한다. 10 + 4 = 14 로한다.
4 번째 3 번째와 1번째를 선수로 한다. 여기서 큰 값을 선택해서
14 + 4 = 18 을 진행한다.
나머지도 동일하다.

그냥 위상정렬이다.
일단 진입차선이 존재하지 않는다는 것들은 그냥 바로 인덱스에다가 값을 집어넣으면 된다.
그리고 진입차선이 존재한다라는 것은 , 일단 해당 진입차선들이 다 지워지기 전까지는 진행되지 않고
다 지워졌다라는 것은 이전 나와 연결되어 있는 값들은 이미 채워져있을 것이다.
그래서 거기서 가장 큰 값과 본인의 값을 더해서 계속 갱신해나가면 된다.

즉 규칙은 이거 하나이다.
진입차선이 0인 애를 골라서 큐에다가 넣는다.
그러면서 바로 dp 값들을 초기화한다.

그 다음에 진입차선이 0이 되는 애들을 담는다.
걔내들이 실행되면서 바로바로 본인과 연결되어 있는 값들에 대해서 가장 최댓값을 담는다.
근데 , 본인과 연결된 애들의 값을 넣어야 하기 때문에 , 본인으로 부터 시작하면 안된다.
그 의미는 즉 , 위상정렬을 진행하면서 , 얘내들이 지들이랑 이어져 있는 놈들 중에서 본인보다 작은 값을 가지고 있으면
값을 업그레이드 시켜주는 것이다.

그래서 사실 queue 에서 나오면서 본인 값과 본인이 가지고 있는 가중치 값을 더하면 될 것 같다.

그렇게 되면 , 일단 arr 로 값들을 관리하고
graph 로 간선을 유지하며
entry , dp 로 위상정렬을 진행하면 될 것 같다.
 */
public class Main {
    public static Queue<Integer> queue = new LinkedList<>();
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static int N;
    public static int[] dp , arr , entry;

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        dp = new int[N + 1];
        entry = new int[N + 1];
        arr = new int[N + 1];

        for(int i = 0; i <= N; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(input.readLine());
            arr[i] = Integer.parseInt(st.nextToken());
            while(true){
                int a = Integer.parseInt(st.nextToken());
                if(a == -1) break;
                // a 에서 시작해서 i 로 들어오는 간선임
                graph.get(a).add(i);
                entry[i]++;
            }
        }

        for(int i = 1; i <= N; i++){
            // 위상정렬을 시작할 노드
            if(entry[i] == 0) queue.add(i);
        }

        while (!queue.isEmpty()) {
            Integer a = queue.poll();

            // 본인 값을 갱신 , 진입차선이 0이라서 나온 것이니까 그냥 본인 값에다가 추가시켜주면 됨
            dp[a] += arr[a];

            // 이제 본인 값과 본인과 이어진 애들을 검사하면서 , 본인보다 작은 값이 존재하면 거기다가 채워넣어줄 것임
            for(Integer next : graph.get(a)){
                if(dp[a] > dp[next]) dp[next] = dp[a];

                if(--entry[next] == 0) queue.add(next);
            }
        }

        for(int i = 1; i <= N; i++){
            output.write(dp[i] + "\n");
        }

        output.flush();
        output.close();
    }
}