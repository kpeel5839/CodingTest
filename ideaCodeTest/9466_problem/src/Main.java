import java.util.*;
import java.io.*;

// 9466 : 텀 프로젝트

/*
-- 전제조건
각 프로젝트를 진행하는데
해당 프로젝트가 진행되면서 서로 함께하고 싶은 팀원을 정할 수 있다
혼자서 하고 싶어서 혼자하거나 , 혹은 서로가 서로를 결국 원해서 사이클을 이루는 형태가 되었을 때 ,
프로젝트 팀원을 이룰 수 있다.
-- 틀 설계
이게 처음에 무슨 개소리인가 했는데 , 그냥 위상정렬을 진행하면서
개수를 세는 거 였음
위상정렬은 사이클은 세지 못한다.
entry 를 기준으로 큐에다가 넣고
간선들을 제거하면서 진행하기 때문이다.
근데 , 사이클이 존재한다면 , entry 가 0 개가 될 수가 없다 서로가 서로를 가르키기 때문에,
그렇기 때문에 사이클의 존재를 확인하기 위해서 위상정렬을 이용해서 진행하면 된다 , 본인이 본인을 선택하는 것도 일종의 사이클이다.
 */
public class Main {
    public static int T , N , res;
    public static int[] entry;
    public static List<ArrayList<Integer>> graph;
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        T = Integer.parseInt(input.readLine());
        int index = 0;

        while(index++ < T){
            N = Integer.parseInt(input.readLine());
            entry = new int[N + 1];
            res = 0;
            graph = new ArrayList<>();

            for(int i = 0; i <= N; i++){
                graph.add(new ArrayList<>());
            }

            st = new StringTokenizer(input.readLine());
            for(int i = 1; i <= N; i++){
                int a = Integer.parseInt(st.nextToken());
                entry[a]++;
                graph.get(i).add(a);
            }

            Queue<Integer> queue = new LinkedList<>();

            for(int i = 1; i <= N; i++){
                if(entry[i] == 0) queue.add(i);
            }

            while(!queue.isEmpty()){
                int a = queue.poll();
                res++;
                for(Integer next : graph.get(a)){
                    if(--entry[next] == 0) queue.add(next);
                }
            }

            output.write(res + "\n");
        }

        output.flush();
        output.close();
    }
}
