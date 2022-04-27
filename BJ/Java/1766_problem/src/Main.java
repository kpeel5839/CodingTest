import java.util.*;
import java.io.*;

// 1766 : 문제집
/*
-- 전제 조건
N개의 문제가 주어지고 , 우선순위는 번호가 낮은 순으로 진행이 되어야 하고
그리고 순서가 주어지는데 , 이 순서는 A , B로 나타냈을 떄 , A번 문제를 B번 문제보다 먼저 풀어야한다라는 의미다.
그래서 출력을 쫘르륵 하면 된다.
-- 틀 설계
위상정렬과 , priorityQueue 를 이용하여서 굉장히 쉽게 풀 수 있을 것 같다.
 */
public class Main{
    public static int V , E;
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static PriorityQueue<Integer> queue = new PriorityQueue<>(); //default 가 오름차순이다.
    public static int[] entry;

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        entry = new int[V + 1];

        for(int i = 0; i <= V; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < E; i++){
            st = new StringTokenizer(input.readLine());

            int sta = Integer.parseInt(st.nextToken());
            int des = Integer.parseInt(st.nextToken());

            graph.get(sta).add(des);
            entry[des]++;
        }

        for(int i = 1; i <= V; i++){
            if(entry[i] == 0){
                queue.add(i);
            }
        }

        // 이제 여기서 하나를 뽑으면 그 정점과 연결된 애들의 진입차선을 -1 씩 하면서 0이 되는 애들은 priorityQueue 에다가 담아준다.
        // 그러면서 뽑으면서 계속 출력해주면 된다.

        while(!queue.isEmpty()){
            Integer v1 = queue.poll();
            output.write(v1 + " ");
            for(Integer v2 : graph.get(v1)){
                if(--entry[v2] == 0) queue.add(v2);
                // entry 가 음수 가 되는 일은 절대 없음 , 중복으로 정점을 방문하는 경우도 없고
                // 왜냐하면 , entry 가 0 이 되는 경우에 집어넣기 때문에
                // 그 다음에 entry를 애초에 간선들을 가지고 entry 를 setting 했기 때문에도 그렇다.
            }
        }

        // queue 가 empty 가 되었는데 , 방문하지 않은 정점이 남아있다면 정상적이지 않은 위상정렬이라는 것을 판단 가능하지만
        // 여기서는 되는 경우만 주어지니 그럴 필요 x
        output.flush();
        output.close();
    }
}
