import java.util.*;
import java.io.*;

// 15681 : 트리와 쿼리

/*
-- 전제조건
간선에 가중치와 방향성이 없는 임의의 루트 있는 트리가 주어졌을 떄 , 아래의 쿼리에 답해보록 하자
- 정점 U를 루트로 하는 서브트리에 속한 정점의 수를 출력한다.
그러니까 정점을 정해놓고 , 그것을 트리로 보고서 ,
그 다음에 쿼리로 주어지는 것들을 정점으로 하는 서브트리의 개수를 반환하라는 것이다.
-- 틀설계
굉장히 쉽다.
그냥 dfs 로 진행하면서 , parent 에는 접근 하지 못하게 진행하고
본인의 자식들을 Top-down 형식으로 받아오면 될 것 같다.
즉 , parent 를 제외한 노드가 하나도 없는 노드는 return 1을 진행하면 된다.

그래서 res 를 만들어놓고서,
visited 처리를 하면서 , 진행하면 , 될 것 같다.
그냥 모든 res 를 1로 초기화하고서 , visited 도 필요없고 부모 방문 못하게 하면서
본인의 서브트리의 개수들을 역으로 받아오기만 하면? 그럼 쉽게 진행 가능할 듯 하다.

visited 를 하지 않아도 되는 이유는 , 해당 정점까지 경로는 유일하고 , 그렇기 때문에
부모로 거슬러 올라가지만 않으면 되기 때문이다.

-- 결론
처음에 왜 이렇게 시간이 많이 걸렸었던 건지는
모르겠지만 , 맞았음
 */
public class Main {
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static int[] res;
    public static int N , R , Q;

    public static int dfs(int par , int cur){
        /*
        par = 부모
        cur = 현재 탐색하는 정점

        현재 탐색하는 정점에서 이제 하위 노드들로 진행할 것인데,
        이 경우에 par == next 면 안된다.
        왜냐하면 부모노드로 가는 것이니까
        그리고 , 계속 해서 next 로 재귀 호출해주고
        그 다음에 마지막에 return res[cur] 해주면 된다.
         */

        for(Integer next : graph.get(cur)){
            // 부모 정점으로 가려고 하면 continue;
            if(par == next) continue;

            // dfs 로 다음 노드로부터 출발
            res[cur] += dfs(cur , next);
        }

        // 더 이상 next 가 없던 애들은 그냥 res[cur] 에 더해지는 것이 없어서 , 본인의 개수인 1을 반환
        return res[cur];
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        N = Integer.parseInt(st.nextToken()); // 노드의 개수
        R = Integer.parseInt(st.nextToken()); // 루트노드 번호
        Q = Integer.parseInt(st.nextToken()); // 쿼리의 수

        res = new int[N + 1];
        for(int i = 0; i <= N; i++){
            graph.add(new ArrayList<>());
        }

        // 이제 입력을 받자 , 트리의 특성에 따라서
        for(int i = 0; i < N - 1; i++){
            st = new StringTokenizer(input.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        // 이미 본인을 다 가지고 있으니까 res 배열은 모두 1로 초기화를 진행한다.
        Arrays.fill(res , 1);

        // 루트노드를 시작 정점으로 넘기고 , 부모노드는 없으니까 -1로 진행
        dfs(-1 , R);

        for(int i = 0; i < Q; i++){
            // 들어오는 입력들로 쿼리 결과를 바로 출력
            output.write(res[Integer.parseInt(input.readLine())] + "\n");
        }

        output.flush();
        output.close();
    }
}
