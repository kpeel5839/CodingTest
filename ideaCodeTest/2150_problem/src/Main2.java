import java.util.*;
import java.io.*;

public class Main2{
    public static int V , E , idValue = 1; // 경로마다 특정한 id 값을 부여하기 위한 value;
    public static List<ArrayList<Integer>> graph = new ArrayList<>(), scc = new ArrayList<>(); // 그래프를 등록할 graph 와 , 각각의 SCC 들을 저장할 scc
    public static int[] id;
    public static boolean[] already;
    public static Stack<Integer> stack = new Stack<>();

    public static int dfs(int a){
        // a == 현재 방문하고 있는 vertex number

        id[a] = idValue++; // 현재 방문한 정점에게 너는 몇번째 방문이다라는 것을 인식
        stack.push(a); // 방문하였으니까 , stack 에다가 집어넣음

        int min = id[a]; // 나중에 본인이 최종 부모인지 확인하기 위해서 , 해당 사이클의 최종 부모이면 , id 값을 기준으로 stack 에서 빼면서 , SCC 에 등록

        // 방문한 정점의 번호를 부여하는 이유도 , 나중에 id == min 즉 , 본인이 집합의 최종 부모 노드라는 것을 의미한다.
        // 예를 들어서 해당 그림에서 처럼 4번 으로 시작한다고 가정했을 때 , 돌아오는 최종 부모 값들은 5이다. 근데 , 본인으 id 값이 4이다 , 그러면 해당 집합에 속하지 않았다는 것을 알 수 있다.
        // 왜냐하면 해당 진행된 최고 id 값 부터 넘어온 부모 id 값까지만 , SCC 에 포함이 되기 때문이다 , 그렇게 방문을 하였으니까 , 즉 id 값은 어디서 부터 어디까지가 SCC 집합이라는 것을 알 수 있는 방법이다.

        // 그래서 4 , 5 , 6 , 7 이렇게 진행되었다 라고 가정해보자 , 그림처럼
        // 그러면 4 에서 시작해서 5 -> 6 -> 7 (id 값 기준) 이였을 때 , 최종적으로 돌아온 부모의 값은 5이다 , 그러면 일단 재귀 특성상 , 7 , 6 , 5 이렇게 돌아갈 것이다.
        // 그래서 5 가 일단 id[a] == min 이기 때문에 이 집합의 부모는 본인이구나라는 사실을 알 수 있었기 때문에 stack 에서 집합들을 다 빼서 , SCC 집합에 속하게 되었을 것이다.
        // 이제 4 번도 본인 보다 , 상위 부모가 없네? 그러면 내가 최종 부모구나 하고서 stack 에 있는 값들을 빼면서 진행하는데 5 , 6 , 7 은 이미 SCC 에 속하게 되어서 4번 본인만 속하게 되는 것이다.
        // 이런식으로 id 값을 순서대로 부여하게 되면 , 혹은 그냥 어떠한 특정 순서로 id 값을 배당하게 되면 , 이런식으로 SCC 집합들을 나눌 수가 있다.
        // 즉 SCC 집합을 결정하는 최종적인 조건은 id[a] == min , 즉 본인이 해당 집합에서 최종 부모일 경우 , 즉 본인은 돌아갈 부모가 없는 경우!(이게 핵심) SCC 집합을 본인 기점으로 나눌 수가 있게 되는 것이다 , 다시 코드로 표현해보자.
        for(Integer next : graph.get(a)){
            // 아직 방문한 적이 없는 노드인 경우
            if(id[next] == 0) min = Math.min(min , dfs(next));

                // 방문한 적 있는데 , 아직 SCC 집합에 속하지 않은 경우 , 최종 부모 노드가 될 수 있다
                // 이것을 하지 않으면 이미 SCC 집합에 포함된 , 이미 사이클이 있어서 포함된 애를 여기다가 포함하게 되어서 미궁에 빠질 수가 있음
            else if(!already[next]) min = Math.min(min , id[next]); // 지금까지 경로 내에서 , 최종 부모를 결정하기 위해서 min 을 계속 반환함
        }

        ArrayList<Integer> result = new ArrayList<>(); // 답을 담을 ArrayList 선언

        if(id[a] == min){
            while(true){
                Integer b = stack.pop();
                result.add(b);
                already[b] = true; // SCC 집합에 들어갔다고 표기
                if(b == a) break; // 최종 부모까지 빼면 , 즉 본인이 최종 부모이니 본인까지 빼면 SCC 집합 결정
            }
            scc.add(result); // 본인이 최종 부모가 아닌 경우 , 즉 SCC 의 기점이 아닌 경우에는 답에 추가하지 않기 위해서 , if문 안에서 해결
        }

        return min; // 본인이 찾은 본인의 최종 부모를 반환 , 가장 상위 부모를 반환 , 그럼 알아서 , 사이클 내의 가장 시작 부모를 알게 되고 그것을 계속 반환하면 , 최종 부모는 그 값을 받아서 본인이 최종 부모임을 알 수 있다.
    }
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        V = input.nextInt();
        E = input.nextInt(); // 정점의 개수 , 간선의 개수들을 입력으로 받아놓음

        id = new int[V + 1];
        already = new boolean[V + 1];

        for(int i = 0; i <= V; i++){
            graph.add(new ArrayList<>());
        } // graph 일단 초기화

        for(int i = 0; i < E; i++){
            int a = input.nextInt(); // 부모 정점
            int b = input.nextInt(); // 자식 정점

            graph.get(a).add(b); // 간선 추가
        }

        for(int i = 1; i <= V; i++){ // 정점 하나하나에서 , 시작해봄 , 해당 정점들을 기준으로 SCC 를 찾음
            // 아직 방문이 되지 않아서 idValue 값을 할당 받지 못한 애들은 SCC 집합에 포함이 되어 있지 않으니 , 해당 값을 기준으로 dfs 를 실행
            if(id[i] == 0) dfs(i);
        }

        for(ArrayList list : scc){
            System.out.println(list); // 구한 SCC 들 출력
        }
    }
}