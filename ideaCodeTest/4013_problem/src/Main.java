import java.util.*;
import java.io.*;

// 4013 : ATM

/*
-- 전제조건
교차로 들이 존재하고
거기서 ATM 기는 모든 교차로에 설치되어 있다.
교차로 끼리 사이클이 형성되어 있고,
레스토랑이 있는데

아무 레스토랑 점에 들어가고 , 거까지 가는데 ATM 기에서 최대의 돈을 인출할 수 있는 알고리즘을 작성하고 , 그 값을 출력하라.

-- 틀설계
일단 , 사이클을 파악하는 SCC 집합을 결정해야한다.
그 다음에 , 그 SCC 들을 가지고 위상정렬을 진행할 것인데 , 여기서 가장 중요한 것은 SCC 의 가중치는 내부 정점들의 가중치의 합이라는 것이다.
SCC 들은 서로 사이클이 형성될 수 없다. 그것은 이전에 3977 (축구전술) 번을 풀면서 꺠달은 점이다.
그래서 , 확실하게 서로 사이클이 형성될 수 없다라는 것을 알게 되었으니까 , 이 점을 이용하여서 위상정렬을 진행하게 된다면,
순서대로 모든 SCC 들을 방문할 수 있다라는 것을 알 수 있다.

여기서 가장 중요한 점은 , 어떠한 지점에 들어섰을 때 , 그 지점이 SCC 집합이라면 무조건적으로 당연하게도 그 사이클 내의 정점들의 가중치값들은 확실하게 얻을 수가 있다.
그것을 보고 SCC 의 가중치들을 저장해야 한다라는 것을 깨달을 수 있다.

그러면 이제 SCC 들의 순서가 중요한데 , 서로 사이클이 형성이 되어 있지 않으니 , 무조건 적으로 해당 SCC 집합이 실행이 되려면 진입차선이 없어야 한다.
그러면 만일 진입차선이 원래 존재했더라면? 그 본인의 SCC 로 진입하던 SCC 의 값은 이미 dynamic programming 으로 인해서 값이 결정된 후일 것이고
그리고 , 본인의 값도 결정되어 있는 다음일 것이다.
(이전 진입 SCC 들은 서로 사이클이 형성되어 있지 않고 , 그렇기 때문에 서로의! 값에는 영향을 줄 수 없다 이전 진입차선들이 동일한 레벨이라고 했을 때이고 , 그리고 서로! 영향을 줄 수 없다라는 것에 주의)

그러면 그 점을 이용해서 위와 같은 결론을 얻을 수 있고,
이렇게 정해진 값들과 , 현재 내 SCC 내에 레스토랑이 포함되어 있다라는 것을 인지를 하면??
Queue 에서 해당 SCC 가 빠졌을 때 , 이 SCC 에 레스토랑이 포함이 되어 있다면 결과값으로 Math.max 하면 되는 것이다.

당연하게도 ATM 에는 음수값은 존재하지 않고 , 50만개에 각각의 ATM 에는 최대 4000의 값이 저장되어 있으니 , 20억이다.
즉 , 결과값은 overflow 될 수 없으니 , int로 진행해도 된다.

-- 해맸던 점
또 개같이.. id[next] 값과 min 값을 비교해줬었어야 했는데 , 그렇지 않아서 시간초과 났었음 , 틀렸다 나왔으면 조금 빨리 찾았을 텐데 , 틀렸음 나와가지고
입력 Stream 으로 바꾸고 생 조랄을 했음
그 다음에 , 시작정점을 기준으로 위상정렬을 했었어야 했는데 , 그렇지 못했어써
수정했었음 , 그래서 내 생각으로는 시작정점을 기준으로 위상정렬을 하려면 , 재귀방법으로 밖에 떠오르지 않아서 ,
해봤는데 시간초과 났음 , 그래서 어쩔 수 없이 결과를 본 결과 , 그냥 다익스트라 형식으로 하는데 , 중간에 끝내고 , 그런 과정이 없어서
그냥 , queue 로 진행했었음 , 시작정점이 있었기에 다익스트라 처럼 진행하였고 , 위상정렬 개같은 카테고리 때문에 오히려 더 헷갈렸음.
근데 분명히 위상정렬로 풀 수 있는데 오래걸린다... 내가 분명 재귀적으로 잘 못 구성한 것이긴 할 듯
그리고 다익스트라를 Queue 로 진행했는데 , 짜피 PriorityQueue 로 진행하는 이유자체가 , cost 높은 걸로 진행하는 건데,
일단 이거는 모든 정점을 다 갱신하고 거기서 최대값을 찾아내는 것이기 때문에 , 목표 정점이 없다라는 것 , 그리고 queue 같은 경우는 그냥 삽입이 상수시간이지만 PriorityQueue 는 logN 이기 때문에 오히려 시간이 손해일 것이다.
그리고 , 비교를 통해서 queue 에다가 add 하는 과정을 진행하기에 , 분명히 비효율적인 경우도 존재하겠지만 , PriorityQueue 로 진행할 이유가 없고 , 그리고 무엇보다 PrirorityQueue 는 해당 정점까지의 cost를 기준으로 하기에
요소도 2개넣어야 한다 , 귀찮다 , 그래서 이런식으로 해결하였음

그래서 결과적으로
SCC , 다익스트라로 해결한 것이다.
 */
public class Main {
//    public static int[] entry;
    public static int[] id , scc , vertex , dp , sccVertex;
    public static boolean[] resto , sccResto;
    public static int res = 0 , V , E , S , idValue = 0 , sccSize = 0;
    public static Stack<Integer> stack = new Stack<>();
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static List<ArrayList<Integer>> sccGraph = new ArrayList<>();

    // 강연결집합 찾기
    public static int scc(int a){
        id[a] = ++idValue;
        stack.push(a);

        int min = id[a];
        for(Integer next : graph.get(a)){
            // 다음 정점이 아직 방문치 않았으면
            if(id[next] == 0) min = Math.min(min , scc(next));
            // 다음 정점이 방문이 안되었고 , 아직 강연결 집합에 포함되지 않았으면
            else if(scc[next] == 0) min = Math.min(min , id[next]);
        }


        // 본인이 사이클의 최대 부모이면 stack 에서 뺌
        if(id[a] == min){
            sccSize++;
            while(true){
                int b = stack.pop();
                scc[b] = sccSize; // b 를 scc에 소속시킴
                if(a == b) break;
            }
        }

        // 이전 정점들에게 본인의 최대 부모를 알려주기 위해서
        return min;
    }

    // 위상 정렬
//    public static void stomachSort(int a){
//        for(Integer next : sccGraph.get(a)){
//            entry[next]++;
//            if(entry[next] != 2) // 아직 방문하기 직전의 next 만 진행
//                stomachSort(next);
//        }
//    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        String[] in = input.readLine().split(" ");
        V = Integer.parseInt(in[0]);
        E = Integer.parseInt(in[1]);

        id = new int[V + 1];
        scc = new int[V + 1];
        vertex = new int[V + 1];
        resto = new boolean[V + 1]; // 레스토랑인지 아닌지 판단

        for(int i = 0; i <= V; i++){
            graph.add(new ArrayList<>());
        } // graph 초기화

        for(int i = 0; i < E; i++){
            int[] edge = Arrays.stream(input.readLine().split(" "))
                            .mapToInt(Integer::parseInt).toArray();

            graph.get(edge[0]).add(edge[1]); // a -> b

        } // 입력 받기

        for(int i = 1; i <= V; i++){
            vertex[i] = Integer.parseInt(input.readLine());
        }

        S = Integer.parseInt(input.readLine().split(" ")[0]);
        resto = new boolean[V+1];

        Arrays.stream(input.readLine().split(" "))
                .mapToInt(Integer::parseInt).forEach(e->resto[e]=true);

        for(int i = 1; i <= V; i++){
            // 아직 방문안했으면
            if(id[i] == 0) scc(i);
        } // scc 집합 만듦

        // scc 집합 만들었으니까 , 위상정렬을 진행하면 됨
        // 일단 sccSize 에 따라서 graph 도 만들고 해야함

        sccVertex = new int[sccSize + 1];
        dp = new int[sccSize + 1];
        sccResto = new boolean[sccSize + 1];

        for(int i = 0;i <= sccSize; i++){
            sccGraph.add(new ArrayList<>());
        }

        for(int i = 1; i <= V; i++){ // 모든 정점을 돌아 다니며 , SCC의 위상정렬을 정리할 것임
            // sccVertex 에 해당 집합의 가중치 값을 저장
            sccVertex[scc[i]] += vertex[i];

            // scc에 지금 현재까지는 resto 가 있다라는 증거가 없을 때에만
            if(!sccResto[scc[i]])
                // 그러고서 현재 집합에 resto 가 포함이 되어 있나 안되어 있나를 집어넣고 , true 로 결정되면 이집합에 대해서는 다시 진행하지 않음
                sccResto[scc[i]] = resto[i];
            for(Integer next : graph.get(i)){
                // 같은 집합에 포함된 , SCC 면 안됨
                if(scc[next] != scc[i]){

                    // 이제 여기에서 발견하는 정점을 entry 를 추가하는 것과 동시에 graph 에 추가하자.
                    // SCC 사이에 간선이 두개 이상이 생길 수 있는데 , 짜피 결과값에는 영향을 주지 않을 것 같으니 , 넘어가자.

                    // sccGraph 에다가 집합과 집합의 간선을 생성해줌
                    sccGraph.get(scc[i]).add(scc[next]);
                } // graph 형성 해주고
            }
        }

        Queue<Integer> queue = new LinkedList<>();

        // 진입차선이 0 부터 시작하는 놈이 아니라 , 시작정점이 주어졌었음
//        stomachSort(scc[S]);
        queue.add(scc[S]);
        dp[scc[S]] = sccVertex[scc[S]];
        if(sccResto[scc[S]]) res = dp[scc[S]];


        while(!queue.isEmpty()){
            int a = queue.poll();

            for(Integer next : sccGraph.get(a)){
                if(dp[next] < dp[a] + sccVertex[next]){
                    dp[next] = dp[a] + sccVertex[next];
                    queue.add(next);

                    if(sccResto[next]) res = Math.max(res , dp[next]);
                }
            }
        }
//        System.out.println(Arrays.toString(scc));
//        System.out.println(Arrays.toString(entry));
//        System.out.println(Arrays.toString(sccVertex));
//        System.out.println(Arrays.toString(sccResto));

//        while (!queue.isEmpty()) {
//
//            // 해당 SCC 집합을 받아냄
//            Integer a = queue.poll();
//
//            // dp 에다가 값을 더해줄 것임 , 그래야지 이전 진입차선들이 정해놓은 값과 , 본인의 값을 더할 수 있음
//            dp[a] += sccVertex[a];
//
//            if(sccResto[a]) res = Math.max(res , dp[a]);
//
//            for(Integer next : sccGraph.get(a)){
//                // next 를 실행
//                if(--entry[next] == 0) // 0이 되면 집어넣어주고
//                    queue.add(next);
//
//                // 현재 본인의 값이 dp[next] 보다 크면 넣어줌
//                if(dp[next] < dp[a])
//                    dp[next] = dp[a];
//            }
//        }

        System.out.println(res);
    }
}
