import java.util.*;
import java.io.*;

// 4196 : 도미노

/*
-- 전제조건
도미노를 쓰러뜨리는 데 , x , y 가 주어졌을 떄 , x 가 쓰러지면 , y 블록도 넘어진다고 하였을 때 ,
모든 도미노를 쓰러 뜨리려면 몇 개의 도미노들을 수동으로 넘어뜨려야 하는지 구하시오.
-- 틀 설계
일단 확실한 것은 , 진입 차선의 개념으로 보았을 때 ,
진입 차선이 아무것도 없는 아이를 넘어뜨려야 한다라는 것은 확실하다.

근데 이게 , 진입 차선이 없다라는 것은 , 무조건 수동으로 넘어뜨려야 한다라는 사실이 된다.
그래서 위상정렬의 진입 차선 개념을 이용하여서 , 진입차선이 0 인 아이들을 모두 수동으로 넘어뜨린다고 가정하자.

근데 뭐가 문제일까
일단 진입차선이 없는 애들을 넘어뜨리면 , 나머지 애들은 볼 필요도 없는 거 아닌가?
진입 차선이 있다라는 얘기는 누군가 본인을 쓰러 뜨릴 수 있다라는 것이고 ,
진입 차선이 없는 아이를 수동으로 쓰러 뜨리게 되었을 때 , 본인을 쓰러 뜨릴 수 있는 애가
수동으로 쓰러 뜨린 애 혹은 수동으로 쓰러 뜨린 애로 인해서 쓰러진 애가 될 것이다.

애초에 그러면 진입차선이 있는 애들은 무조건 쓰러질 수 밖에 없는 것이다.
진입차선이 0인 애들을 쓰러 뜨리게 되면 말이다.

근데 , 뭐가 문제일까? 과연 진입차선이 0 인 애들을 모두 쓰러 뜨리는 것이 , 최소의 도미노 블록 개수를 구하는 방법이 아닌 걸까?
만약에 아니라면 , 진입차선이 0인 애들 중에 , 수동으로 쓰러뜨리지 않아도 되는 애들이 있는 것일까?

기본적인 위상정렬을 한번 해보았지만 , 당연히 틀린다 플레 4 문제인데 호락호락할리가 없다.
근데 여기서 위상정렬 , 강한연결요소라는 힌트를 얻어냈으니 이것들을 왜 써야 하는지를 알아야 할 필요가 있다.

여기서 위상정렬을 왜 하게 되는 것일까
위상정렬은 순서도를 의미하게 된다. 즉 어떠한 두개의 노드간의 순서가 정해져있었을 때 , 그런 관계들이 여러개가 존재할 때에
노드들의 실행 순서를 예측할 수 있는 것 그것이 위상정렬이다.

하지만 , 도미노는 ? 하나 쓰러지면 그거에 연결된 진입차선이 몇개가 있든 , 그냥 넘어지는 것이다.
즉 , 앞에 있는 애들이 다 실행이 되어야 넘어지는 것이 아니란 말이다.

혹시나 내가 문제를 잘 못 읽은 것이 있나 한번 다시 문제를 살펴보도록 하자.

맞네 , 사이클이 존재하는 경우 , 그 경우에는 모든 진입차선이 1 이기에
0으로 시작되는 것이 없다.

즉 , SCC 가 존재하는 경우에는 어떠한 도미노를 쓰러 뜨려야지 , 가장 효율적인지 ,
알수가 없다라는 것이다.

그래서 사이클이 있는 경우를 고려하기 위해서 , SCC 집합으로 각 다들 만들어준다.
그리고 , SCC 집합의 번호를 매겨서 , SCC 의 부모를

그러면 이렇게 할 수 있을 것 같다.

각 SCC 집합을 만들고 다시 graph 를 싹 돌면서,
해당 SCC 에 진입차선이 몇개가 있는지 정하면 될 것 같고
그럴려면 각 정점이 어떤 SCC 에 속해있는지 정해야 할 것 같다.

그러면서 각각 하나씩의 정점을 돌아가면서 , 진입차선을 정하고
그 SCC 를 가지고 위상 정렬을 진행하면 될 것 같다
거기서 0 인 애들만 진행하면 된다.

-- 결과
내가 플레 4 문제를 드디어 맞췄다...
역시 내가 생각했던 방식은 맞았고 , 사이클이 존재할 때에 , 위상정렬을 할 수가 없다라는 사실을 망각하고 있었어서
생긴 해프닝이였다 , 그래서 이 부분이 해결되었을 때 ,비로소 문제를 해결할 수 있었다.

결국 내가 생각했다라는게 너무 기쁘다.
답 볼뻔 했는데 , 그래도 질문 검색 창에만 가서 다행이다. 그래도 문제를 맞추려고 하는 끈기가 조금 더 생긴 듯하다.

그래서 결국 사이클이 존재하기에 위상정렬을 할 수가 없기에 , SCC 를 구해야 하는 문제였다.
 */
public class Main {
    public static List<ArrayList<Integer>> graph;
    public static int[] sccEntry ,scc , id; // scc 에다가는 각 노드가 몇번째 scc 인지 , 그리고 scc entry 에는 해당 scc 에는 entry 가 몇개가 있는지
    public static int T , V , E , sccIndex , idIndex , res;
    public static Stack<Integer> stack = new Stack<>();

    public static int scc(int a){
        id[a] = ++idIndex;
        stack.push(a);

        int min = id[a];

        for(Integer next : graph.get(a)){
            // 방문한 경우
            if(id[next] == 0) min = Math.min(min , scc(next));
            // scc[i] 는 본인이 속해있는 집합을 의미하고 , 1부터 시작이니 0이라면 아직 속하지 않은 경우임
            // 해당 id 값으로 최종 부모인지 아닌지를 확인
            else if(scc[next] == 0) min = Math.min(min , id[next]);
        }

        // scc 집합들을 담을 것은 아니기에 list 를 생성하지는 않는다.
        if(id[a] == min){ // 본인이 사이클 집단에 최종 부모일 때
            sccIndex++;
            while(true){
                int b = stack.pop(); // b를 뽑고
                scc[b] = sccIndex; // scc 집단 속해주고
                if(a == b) break; // 최종 부모에 도달하면 break;
            }
        }

        return min;
    }
    public static void getEntry(){
        for(int i = 1; i <= V; i++){
            int sccNumber = scc[i]; // i의 scc 집합 번호
            for(Integer next : graph.get(i)){ // 각 다음으로 가는 집합을
                // 다음으로 가려는게 본인의 집합이 아니라면 , sccEntry 의 scc[next]의 집합의 진입차선 개수를 올려준다.
                if(scc[next] != sccNumber) sccEntry[scc[next]]++;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        T = Integer.parseInt(input.readLine());
        int index = 0;

        while(index++ != T){
            st = new StringTokenizer(input.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            res = 0;
            idIndex = 0;
            sccIndex = 0;
            sccEntry = new int[V + 1];
            scc = new int[V + 1];
            id = new int[V + 1];
            graph = new ArrayList<>();

            for(int i = 0; i <= V; i++){
                graph.add(new ArrayList<>());
            }
            for(int i = 0; i < E; i++){
                st = new StringTokenizer(input.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                graph.get(a).add(b);
            }

            for(int i = 1; i <= V; i++){
                if(id[i] == 0) scc(i);
            }

//            System.out.println(Arrays.toString(scc));

            getEntry();

            for(int i = 1; i <= sccIndex; i++){
                if(sccEntry[i] == 0) res++;
            }

            output.write(res + "\n");
        }
        output.flush();
        output.close();
    }
}
