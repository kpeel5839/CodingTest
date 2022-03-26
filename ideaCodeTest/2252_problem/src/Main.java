import java.util.*;
import java.io.*;

// 2252 : 줄 세우기

/*
-- 전제 조건
N명의 학생들을 키 순서대로 세우고 싶다.
근데 전부 학생들을 정렬할 수는 없고,
어떤 학생이 어떤 학생보다 작다라는 정보의
A B 가 주어진다.
A 가 B보다 작기 때문에 A 앞에 서야 한다는 의미이다.
-- 틀 설계
그냥 기본적인 위상정렬 알고리즘 이며 받은 다음에 , 그냥
그렇게 진행하고 답이 여러가지 인 경우에는 그냥 출력하면 된다.
 */
public class Main {
    public static int V , E;
    public static int[] entry;
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static Queue<Integer> queue = new LinkedList<>();

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

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            entry[b]++; // b에 들어온 진입차선이 있는 것이다.
            graph.get(a).add(b);
        }

        for(int i = 1; i <= V; i++){
            if(entry[i] == 0) queue.add(i);
        }

        while(!queue.isEmpty()){
            int vertex = queue.poll();
            output.write(vertex + " ");

            for(int next : graph.get(vertex)){
                if(--entry[next] == 0) queue.add(next);
            }
        }

        output.flush();
        output.close();
    }
}
