import java.util.*;
import java.io.*;

// 13913 : 숨바꼭질 4
/*
-- 전제 조건
2배로 가는 것은 1초
그리고 그냥 바로 앞이나 뒤로 가는 것은 또 1초
시작점과 목적지가 주어졌을 때 , 최소 경로의 개수와
최소 경로를 출력하라
-- 틀 설계
그냥 bfs의 특성을 이용하여서 가장 처음에 도착하는 곳이
제일 빠른 경로 , 즉 visited 처리를 하여서
방문을 이미하였으면 다시 방문하지 못하도록 하고
그러고서 visited 처리는 그냥 0이 아니면 visited 한 걸로 하자.
그리고 방문처리는 그냥 배열에다가 진행하자.

그래서 parent 값 처리를 하고 객체를 이용하여서
도달하였을 떄의 결과 값은 그냥 얻어낸다. (ans)
 */
public class Main{
    public static int ans , N , K;
    public static final int SIZE = 100001;
    public static int[] map = new int[SIZE];
    public static Stack<Integer> stack = new Stack<>();
    public static void dfs(){
        /*
        bfs를 진행하면서 , N 에서 시작해서 K에 도달하였을 때 ,
        끝내면서 , ans 값을 저장한다.
         */
        Arrays.fill(map , -1);

        Queue<Point> queue = new LinkedList<>();
        map[N] = N;

        queue.add(new Point(N , 0));

        while(!queue.isEmpty()){
            Point point = queue.poll();

            if(point.idx == K){
                ans = point.value;
                break;
            }

            if(point.idx * 2 < SIZE && map[point.idx * 2] == -1){ // 방문 아직 안했을 떄
                map[point.idx * 2] = point.idx;
                queue.add(new Point(point.idx * 2 , point.value + 1));
            }

            if(point.idx != 0 && map[point.idx - 1] == -1){
                map[point.idx - 1] = point.idx;
                queue.add(new Point(point.idx - 1 , point.value + 1));
            }

            if(point.idx != SIZE - 1 && map[point.idx + 1] == -1){
                map[point.idx + 1] = point.idx;
                queue.add(new Point(point.idx + 1 , point.value + 1));
            }
        }
    }
    public static void pathTrace(int number){
        if(number == map[number]){
            stack.push(number);
            return;
        }
        else{
            stack.push(number);
            pathTrace(map[number]);
        }
    }
    public static class Point{
        int idx;
        int value;
        public Point(int idx, int value){
            this.idx = idx;
            this.value =value;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dfs();
        pathTrace(K);

        output.write(ans + "\n");

        while(!stack.isEmpty()){
            output.write(stack.pop() + " ");
        }

        output.flush();
        output.close();
    }
}