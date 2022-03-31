import java.util.*;
import java.io.*;

// 2150 : Strongly Connected Component (강 연결)
/*
-- 전제 조건
방향 그래프가 주어졌을 때 , SCC 들로 나누는 프로그램을 작성하시오.
V, E 가 주어지고 간선들의 정보가 주어졌을 때 , SCC 들을 각각 출력하시오.
각각의 SCC 들은 정점의 번호 순서대로 정렬해서 출력하고 , SCC 들 끼리의 순서는 SCC 내의 가장 작은 정점들을 가지고 정렬하여 출력한다.
즉 출력은 SCC 의 개수를 출력하고 , SCC 의 집합들을 출력한다 , 끝은 -1 로 구분짓는다.
-- 틀 설계
일단 SCC 의 개념은 이러하다 , 모든 정점에서 , 모든 정점으로 이동하는 것이 가능할 때 (최대 부분집합 내에서)
서로 다른 임의의 정점 사이에 경로가 존재하면 , 그것은 강하게 연결이되었다고 한다.
그래서 이것을 찾는 방법은 이러하다.

dfs 와 stack 을 섞어서 사용하는 방법이 있는데,
일단 , 방문하는 노드를 모두 stack 에다가 순서대로 집어넣으면 된다.

그리고서 방문하는 노드들에 방문하는 순서대로 id값을 붙혀준다(방문 순서를 명시)
그렇게 되면 이제 id 값이 해당 노드의 id 값보다 , 낮다라는 것이 의미하는 것은 적어도 이전 정점이라는 것이다(현재 정점보다.)

그래서 그렇게 쭉 가면서 방문처리를 해준다.
그러다가 , 이제 중복 방문하게 되는 경우가 있다 , 이런 경우에 강연결을 당연하게도 의심할 수 있다.
왜냐하면 서로 연결된 정점이 있다라는 것은 두 정점간의 사이클이 존재한다라는 이야기이고 , 그렇다라는 것은
최소 얘내들은 강연결이 되어있다고 결론 지을 수 있다 , 하지만 여기서 주의해야할 점은 얘내들 뿐만 아니라
다른 애들도 같이 강연결 되어있을 수도 있다.

그래서 아까 부여한 고유 id 값을 가지고 min 값을 저장한다.
그러니 , 강 연결 집합에 아직 들어가지 않은 정점들은 , 언제든지 해당 정점과 강연결이 될 수 있으니 ,
min 값을 계속 저장하게 된다. (already 배열에다가 저장하면 될 듯 , 해당 정점이 이미 SCC 에 포함이 되어있는지)

그러고서 , 이제 그렇게 진행하고 , 본인과 연결된 모든 정점들을 다 검사하였을 때 , 만일 min == 본인 id 값
이 같다면 , 해당 정점은 부모와 연결이 되어있지 않거나 , 혹은 부모가 이미 SCC 에 포함이 되어있을 경우이다.(이거는 근데 정확히는 모르겠음)

그래서 해당 정점을 기준으로 이후에 id 값들을 가지고 있는 애들은 , (이미 SCC에 포함이 되어있는 애들은 제외)
모두 해당 SCC 에 포함이 된다.

그래서 결론은 이론 자체는 , 정점을 방문하고 , 본인의 부모 정점으로 돌아갈 수 없는 정점과 연결된 부분집합(이미 SCC 포함된 애들 제외)
그것이 SCC라고 결론 지을 수 있다.

-- 해맸던 점
이미 SCC 에 포함이 되었다는 already 를 갱신안해주었고,
딱 해당 정점에 도착하였을 때 , 해당 정점이 이미 방문한 상태이면 해당 정점이 이미 SCC 에 포함이 되어있는지 확인 후,
min 연산을 진행해야 하는데(이미 방문한 정점이라면 , 본인의 부모 정점임 , 즉 해당 정점이 SCC 에 포함이 안되어있고 , 본인 정점에서 갈 수 있다면 일단 둘은 서로 강연결임)
if(id[a] == 0) , else if 로 진행했어야 했는데 , 이 점을 간과했었고,
그리고 고치니까 dfs 를 돌릴 때 , if(id[i] == 0) 인 애들만 dfs 돌려야 하는데 (이미 방문한 정점이라면 본인과 연결된 애들도 이미 다 방문하였을 것임 , 그렇다라는 것은 id[a] == min 에서 무조건 true임)
그렇다라는 것은 res list 에 본인 하나만 포함이된다 , 그래서 터무니 없게 많은 SCC 들의 집합들이 나온다 , 하지만 if(id[i] == 0) dfs(i) 를 추가하니,
당연하게도 , 맞는 답이 나왔다.

그래서 결론적으로 already , 현재 가려는 정점이 부모 정점인지 체크 x , if(id[i] == 0) dfs(i) 체크 이 3가지를 안해서 살짝 해맸음
햐
 */
public class Main {
    public static int V , E , idValue = 0;
    public static List<List<Integer>> res = new ArrayList<>() , graph = new ArrayList<>();
    public static Stack<Integer> stack = new Stack<>();
    public static int[] id;
    public static boolean[] already;

    public static int dfs(int a){
        /*
        그래프를 돌면서 , SCC를 찾기 위한 함수이다.
        일단 먼저 방문한 해당 정점의 순서를 나타내기 위해서 id 값을 부여하고
        해당 id 값을 min 값에 넣는다(이것을 이용해서 어디까지가 SCC 인지를 확인할 것이다 , min == id[a] 면 SCC 기준을 찾은것)

        그리고서 마지막에 본인의 min 값을 반환할 것인데 , 이것은 본인이 갈 수 있는 가장 먼저 방문했던 정점 , 즉 본인의 집합이 포함되어 있는 최상위 정점이다.
        그래서 이 값을 계속 dfs 를 진행하면서 값을 받을 것이고
        1차적으로 본인과 연결되어 있는 정점중에 본인보다 id 값이 낮은 애들 (SCC 포함이 안되어 있을 경우)
        걔내들을 또 min 값에다가 넣어준다.

        그런식으로 계속 연산을 진행해주면서 결국 찾으면
        해당 정점까지 stack 에서 정점들을 빼고 , 그 다음에 list 를 정렬해서 res에다가 삽입할 것임
         */

        id[a] = ++idValue;
        stack.add(a);
        int min = id[a];

        for(Integer next : graph.get(a)){
            // 해당 정점과 연결되어 있는 애들을 다 방문해볼 것임
            // 아직 방문하지 않은 상황
            if(id[next] == 0) min = Math.min(min , dfs(next));

            // 방문은 하였지만 아직 SCC 집합에 포함이 되어있지 않은 상황
            else if(!already[next]) min = Math.min(min , id[next]);
        }

        List<Integer> list = new ArrayList<>();

        // 이제 본인이 현재 집합에서 가장 최대 부모 정점인지 확인한다 , 맞으면 본인을 기준으로 id 값 높은 애들 다 SCC 포함 (이미 포함된 애들 제외)
        if(id[a] == min){
            while(true){
                int b = stack.pop(); // SCC 에 추가될 정점
                list.add(b);
                already[b] = true; // SCC 에 포함되었음
                if(b == a) break;
            }
            Collections.sort(list);
            res.add(list);
        }

        // 본인 정점에서 방문 가능한 최대 정점 반환 (이미 방문한 정점들을 거슬러서 올라가지는 못함 , 그러니까 직접적으로 본인이 갈 수 있는 정점과 연결되어 있는 정점임)
        return min;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        id = new int[V + 1];
        already = new boolean[V + 1];

        for(int i = 0; i <= V; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < E; i++){
            st = new StringTokenizer(input.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph.get(a).add(b); // graph 에 추가 a -> b
        }

        // 아무 정점과 연결이 되지 않은 정점이 있을 수도 있으니까 , 모든 정점을 dfs를 호출해준다.
        for(int i = 1; i <= V; i++){
            if(id[i] == 0) dfs(i); // 그렇기 때문에 아직 방문하지 않은 애들만 dfs 시작
        }

        Collections.sort(res , (o1 , o2) -> o1.get(0) - o2.get(0));

        output.write(res.size() + "\n");

        for(List list : res){
            for(Object vertex : list){
                output.write(vertex + " ");
            }
            output.write(-1 + "\n");
        }

        output.flush();
        output.close();
    }
}
