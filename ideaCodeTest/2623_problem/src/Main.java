import java.util.*;
import java.io.*;

// 2623 : 음악 프로그램
/*
-- 전제 조건
각 pd 들과 , 가수들이 주어질 때 , pd 가 준
순서를 맞춰서 출력하고 , 그리고 만일 해당 순서대로 구성할 수 없는 경우에는
첫째줄에 0을 출력하면 된다.
-- 틀 설계
그냥 일반적인 위상 정렬에다가 visited 만 추가해서
모든 queue 를 끝내고서 , 실행을 마치게 되었는데 , 아직 visited 안한 정점이 남아있으면 그냥 0 을 출력하면 된다.

-- 해맸던 점
아무리 봐도 맞는데 왜 틀리지 하고 계속 보고있었는데 알고보니까 , 가수들 주어지기 이전에 가수들 몇명 줄지 사이즈 주고 있었음
그래서 그냥 그 숫자를 버리고 진행하니까 바로 맞았음
 */
public class Main {
    public static int N , M;
    public static int[] entry;
    public static boolean[] visited;
    public static List<ArrayList<Integer>> graph = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new boolean[N + 1];
        entry = new int[N + 1];

        for(int i = 0; i <= N; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(input.readLine());

            st.nextToken(); // 앞에 size 그냥 버림

            int start = Integer.parseInt(st.nextToken());
            int des;

            while(st.hasMoreTokens()){
                des = Integer.parseInt(st.nextToken()); // des 받고

                entry[des]++;
                graph.get(start).add(des);

                start = des; // 다시 바꿔주고
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        List<Integer> res = new ArrayList<>();

//        System.out.println(Arrays.toString(entry));

        for(int i = 1; i <= N; i++){
            if(entry[i] == 0) queue.add(i); // 진입차선 없는 애들 시작해주고
        }

        while(!queue.isEmpty()){
            int singer = queue.poll();

            visited[singer] = true; // 방문처리해주고
            res.add(singer); // 답에 추가

            for(Integer next : graph.get(singer)){
                if(--entry[next] == 0) queue.add(next);
            }
        }

        boolean cycle = false;
//        System.out.println(Arrays.toString(visited));

        for(int i = 1; i <= N; i++){
            if(!visited[i]) cycle = true;
        }

        if(cycle) output.write(0 + "\n");
        else{
            for(Integer singer : res) output.write(singer + "\n");
        }

        output.flush();
        output.close();
    }
}
