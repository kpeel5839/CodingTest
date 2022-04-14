import java.util.*;
import java.io.*;

// 3977 : 축구 전술

/*
-- 전제조건
전술을 만들고 , (A , B) 가 주어지면 a -> b 로 가는 것을 지시한다.
선수들에게 , 감독은 모든 구역을 갈 수 있는 곳을 시작지점으로 잡으라고 했다.
근데 , 이것을 감독이 알려줘야 하기 때문에 , 우리는 그것을 구해야 한다.

시작 지점이 될 수 있는 구역을 오름차순으로 출력하시오.
(테스트 케이스는 11개 이하이고 , 각 테스트 케이스는 빈줄로 구분이 되어 있다.
-- 틀설계
일단 이 문제는 굉장히 단순하게 다가가게 되면, 한 지점에서 모든 지점을 갈 수 있는지 다 확인하면 된다.
근데 , 문제는 N이 10만이다. 당연하게도 그러면 너무 오래걸리기에 , 시간초과가 난다.

그러면 어떻게 할 수 있을까..
일단 , 무엇보다 , 다른 모든 구역에 도달할 수 있는 최소의 집합은 강연결로 이루어진 집합이다.

그리고 , 이 SCC 들이 다른 SCC로 이동할 수 있어야 한다.
일단 서로 다른 SCC 가 있다고 가정하자.
얘내들이 같은 SCC 가 되지 못한 이유는?

당연하게도 SCC 사이에 있는 간선들이 단방향으로 이루어져 있기 때문이다.
만일 , SCC 간의 양방향으로 다닐 수 있는 간선이 존재하였다면 얘내들은 같은 SCC 집합이 될 것이다.

그러면 여기서 하나의 결론을 도출해낼 수 있다.
일단 , 진입차선이 존재하는 놈은? 다른 SCC 로 갈 수 있지만 , 적어도 모든 구역을 가지는 못한다.
왜냐하면? 다른 모든 구역을 갈 수 있으려면 , 본인이 SCC 위상정렬의 시작 지점이 되어야 한다라는 것이 내 생각이다.
왜냐하면 , SCC 가 나뉘었다라는 것은 , 즉 모든 정점이 하나의 SCC 가 아니라는 것은 , SCC 간의 사이클이 존재하지 않는다라는 것이고
크게 보았을 때에도 , SCC 들 간의 사이클도 존재하지 않는다 , 그렇기 때문에 , SCC 집합들 중 위상정렬 상에서,
진입차선이 0인 애들 , 걔내들이 곧 , 모든 정점을 갈 수 있는 시작 정점이다.

notion 에다가 그림들은 정리해서 올려놓아야 할 것 같다. 나의 머릿속의 생각은 일단 지금 거의 완벽한 상태이다.
두번째 테스트 케이스가 , confused 인 이유는 일단 SCC 집합이 각각의 정점이다.

그래서 각각의 정점들에 대해서 진입차선이 0인 정점에서 SCC 들을 다 방문하려고 해보면 , 방문이 불가하다.

그래서 , 내 생각은 , 진입차선이 1 이상인 SCC 들은 SCC 간의 사이클이 형성되어 있지 않음은 확실하다.
그렇기에 진입차선이 1 이상인 SCC 들은 절대로 모든 정점을 방문할 수 없다.

그렇기에 , 진입차선이 0인 SCC 들만 모든 SCC 들을 방문이 가능한지 , 체크하면 된다.

아.. 결국 이게 시간초과를 피할 수 있을까라는 생각에 , 답을 봤는데
결국 답은 하나였다 , 위상정렬상 , 진입차선이 0인 SCC 집합이 2개 이상 있으면 당연하게도
모든 정점을 가지 못한다 , 왜냐하면 SCC 에 진입차선이 0인 애들이 2개가 있다라는 것은 0인 애에서 시작하더라도 결국 다른
SCC 까지 도달 못하는 경우가 생기는 것이니까...

진짜 조오금만 더 생각했으면 생갈할 수 있었는데 다 생각해놓고서 너무 아쉽다...
카테고리 보지 말 걸 위상정렬 어느정도 맞았었는데 안써져있어가지고 아닌가 해서 답 봤는데

결국 내가 생각한 해답과 , 본 해답을 합치면 이러하다
SCC 집합들을 다 구한다음,
각 SCC 집합의 진입차선들을 구한다.
그 다음에 , SCC 간의 사이클은 절대로 존재하지 않는다는 점을 이용해서
진입차선이 있는 애들은 모든 정점을 방문할 수 없다라는 것을 인지하고 , 진입차선이 0 인 애들만 찾아서 출력한다.
근데 , 진입차선이 0인 SCC 집합이 2개 이상이면 결국 진입차선이 0 인 집합에서 시작하더라도 모든 정점을 가지 못한다라는 것을 증명할 수 있다.
그렇기에 진입차선이 0인 SCC 집합이 2개 이상이면 , confused 를 출력 , 아니라면 진입차선이 0인 애들의 집합을 오름차순으로 출력하면 된다.

-- 해맸던 점
일단 , 진짜 개 바보 같이 Stack , idValue , sccValue 를 계속 틀렸었고
진짜 출력초과라는 말에 혹해가지고 , 개행문자 틀린줄 알고 개삽질하다가 ,
최종 부모 구할 때 , 당연하게 방문한 순서를 가지고 반환을 해야되는데 진짜 개멍청이 같이
그냥 next 반환했음 원래는 id[next] , 현재 방문하는 정점의 id 값을 가져와야지 부모인지 아닌지 알 수 있는 건데
이러니까 틀리는데 , 자꾸 개같이 출력초과라고 나와서 시간 할애함
그래서 결국 이 문제들 때문에 해맸음 엄청
 */
public class Main {
    // scc 가 속한 집합
    public static int T , V , E;
    public static int[] id , scc , entry;
    // sccValue == scc 집합의 개수
    public static int idValue = 0 , sccValue = 0;
    public static List<ArrayList<Integer>> graph;
    public static Stack<Integer> stack;

    public static int scc(int a){
        id[a] = ++idValue;
        stack.push(a);

        int min = id[a];
        for(Integer next : graph.get(a)){
            // 방문하지 않은 곳은 scc 로 계속 진행해서 , 본인에서 시작해서 갈 수 있는 최종 부모노드를 찾는다.
            if(id[next] == 0) min = Math.min(min , scc(next));
            // 방문은 하였지만 , 아직 scc 에 속하지 않은 정점을 반환 , 최종 부모노드를 찾는 핵심 키워드임
            else if(scc[next] == 0) min = Math.min(min , id[next]);
        }

        // 최종 부모 노드가 본인이면
        if(min == id[a]){
            sccValue++; // 집합 수 증가시켜주고
            while(true){
                int b = stack.pop();
                scc[b] = sccValue; // 해당 집합에 포함시킴
                if(a == b) break; // 최종 부모에 도달하면 SCC 집합을 뽑아내는 행위를 멈춰!
            }
        }

        // 본인으로서 갈 수 있는 최종 부모노드를 반환 (dfs 보다 , scc[next] == 0 이거에서 찾은 부모노드를 반환하기 위해서 반환하는 것임)
        // 즉 , scc[next] == 0 이 핵심코드이지만 , 이 값을 반환하지 않으면 의미가 없으니 이렇게 해주는 것
        return min;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        T = Integer.parseInt(input.readLine());
        int initT = T;

        while(T-- != 0){
            if(T + 1 != initT) input.readLine();
            st = new StringTokenizer(input.readLine());

            V = Integer.parseInt(st.nextToken()); // 정점의 개수
            E = Integer.parseInt(st.nextToken()); // 간선의 개수

            scc = new int[V];
            id = new int[V]; // 0번 부터 시작하기에 , 그냥 V로 선언
            graph = new ArrayList<>();
            stack = new Stack<>();
            idValue = 0;
            sccValue = 0;

            for(int i = 0; i < V; i++){
                graph.add(new ArrayList<>());
            }

            for(int i = 0; i < E; i++){
                st = new StringTokenizer(input.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                graph.get(a).add(b); // 간선 추가
            }

            for(int i = 0; i < V; i++){
                // 강연결 집합들을 만들어준다.
                if(id[i] == 0) scc(i);
            }

            entry = new int[sccValue + 1]; // 각 집합의 진입차선을 관리

            // 모든 edge 를 실행해야 한다.
            for(int i = 0; i < graph.size(); i++){
                for(Integer next : graph.get(i)){
                    // 여기서 잘 못하고 있었음
                    // 왜 실수 했는지는 모르겠지만 , next 가 같은 scc 집합내의 scc 이면 안됨
                    // scc[i] == scc[next] , 시작점과 , 끝점이 같은 집합이여서는 안된다라는 것이다.
                    if(scc[next] != scc[i]) entry[scc[next]]++; // edge 의 끝점을 이용하여 해당 정점의 scc 집합의 진입차선을 증가시킴
                }
            }

            int tag = 0;
            int cnt = 0;

            for(int i = 1; i <= sccValue; i++){
                if(entry[i] == 0){
                    // 진입차선이 0인 SCC 집합의 번호를 알아낸다.
                    tag = i;
                    // 진입차선이 0인 SCC 집합의 개수를 알아낸다.
                    cnt++;
                }
            }

            if(cnt >= 2){
                output.write("Confused\n");
            }
            else{
                // 진입차선이 0인 SCC 의 정점들을 오름차순으로  출력
                for(int i = 0; i < V; i++){
                    // 진입차선이 0인 집합이면 출력
                    if(scc[i] == tag) output.write(i + "\n");
                }
            }

            output.write("\n");
        }

        output.flush();
        output.close();
    }
}
