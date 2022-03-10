import java.util.*;
import java.io.*;

// 5213 : 과외맨
/*
-- 전제 조건
타일은 크기가 == 2이다.
각 타일은 N 줄이 주어지고,
홀 수 줄은 N 개의 타일 , 짝수 줄은 N - 1 개의 타일이다.

항상 어떠한 타일로 이동하려면 , 그 타일은 변을 공유하는 타일과 , 같은 숫자가 있어야 한다.
즉 , 인접한 조각에 본인과 같은 숫자가 쓰여져 있는 타일이 있어야 한다.

그래서 그렇게 쭉 가서 , 가장 짧은 경로를 출력하면 된다.
여기서 문제의 목적은 가장 짧은 경로를 출력하면서 , 가장 마지막 타일에 도달하는 것이다.

여기서 주의해야 할 점은 , 가장 마지막 타일!에 도착하는 것이다.
그래서 마지막 타일에 도착하는 경우를 출력하면 되고 , 만일 마지막 타일에 도착하지 못하는 경우가 있다면?
가장 큰 타일 번호로 이동하는 경우의 경로를 출력하면 된다.

그리고 조각에 도착할 때 마다 , 그 경로를 출력하는 것이 아닌,
타일마다 경로를 출력해야 함에 주의하자
-- 틀 설계
일단 인접행렬로 맵을 받고

이전에 솔직히 6퍼센트에서 왜 틀린지를 잘 모르겠다.
아까는 그냥 바깥에다가 이미 넣은 값을 이용해서 maxValue = edge.value 이런식으로 진행했고,
지금은 그냥 edge.value + 1 로 진행한 것일 뿐인데
이렇게 했다고 틀렸다. 뭐가 다른 것일까
분명히 다른 게 있겠지 , 개인적으로 당연하게도 goal 에 도착하게 된다면 , 당연히 끝나야 한다,
bfs 특성상 가장 빨리 도착한 게 제일 빠른 길이니까 , 근데 왜 틀렸었던 걸까

아마 방문처리에 문제가 있지 않을까 싶다.
방문 처리를 큐에서 나오게 한 다음에 처리하였으니까,
근데 그게 뭐가 문제가 될까?

그러면 그 이후에 담긴 것들은 짜피 오지 못한다.

이것도 역시나 틀린게 아니였다.
무조건 방문처리가 문제였다.

아 이제 알았다
역시나 당연하게도 , 최대값은 달라지지 않는다 , 그럴 수 밖에 없다.
근데 , 달라질 수 있는 것이 존재한다.
그것은 바로 경로이다.
visited 처리를 늦게 하니까 , 이미 방문했던 것을 큐에다가 담게된다.
이것은 이미 방문을 했지만 아직 queue 에서 뽑히지 않아서 , 방문처리를 기다리고 있는 것이다.
근데 , 방문처리가 되기 전에 다른 노드를 parent 를 넣어버려서 , 틀린 parent 가 들어갈 수 도 있는 것이다.

이미 방문을 하였지만 , 나중에 더 효율이 안좋은 , 더 도달하는 데 오래걸린애가 parent로 들어가게 되면 틀린답이 될 수도 있는 것이다.
즉 , parent를 제대로 출력하기 위해서는 , 방문처리를 queue에다가 넣으면서 진행해주어야 한다.
 */
public class Main4 {
    public static int N , H , W , TOTAL , maxTile = 1 , maxValue = 1;
    public static int[][] a;
    public static int[] parent;
    public static boolean[] visited;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));

    public static boolean outOfRange(int y, int x){
        if(y < 0 || y >= H || x < 0 || x >= W) return true;
        return false;
    }
    public static int judgeTileNumber(int y, int x){
        /*
        말 그대로 y , x 를 주면 H , W 를 이용해서 이게 몇번째 타일인지 계산을 해볼 수 있다.
        그래서 이게 몇번째 타일인지를 넘기는 것이다.
         */
        int result;

        if(y % 2 == 0){ // 짝수이면 나중에 딱 맞아 떨어짐
            result = x / 2 + 1;
        }
        else{ // 홀 수이면 나중에 계산할 때 N칸 한개 남음
            result = (x - 1) / 2 + 1;
            result += N;
        }

        result += y / 2 * (N + N - 1);

        return result;
    }

    public static void makeGraph(){
        /*
        여기서 인접한 애들 그러니까 , 갈 수 있는 애들을 보고 , graph 를 형성해준다.
        그냥 순서대로 배열을 돌면서 , 갈 수 있는 타일을 택하는데 , 이 경우에는 무조건
        타일이 달라야하고 , 그리고 a[i][j] == 가려고 하는 곳 이여야 한다.
         */

        // 이미 연결된 애들은 제거해줄려면 connet 배열을 이용할 수 있을 것 같다 2차원 배열로

        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                int nowTile = judgeTileNumber(i , j);
                for(int c = 0; c < 4; c++){
                    int ny = i + dy[c];
                    int nx = j + dx[c];
                    // 범위를 벗어나지 않는다면?
                    if(outOfRange(ny , nx) || a[ny][nx] == 0) continue;
                    int newTile = judgeTileNumber(ny , nx);
//                    System.out.println("nowTile : " + nowTile + " newTile : " + newTile);
//                    System.out.println("a[i][j] : " + a[i][j] + " a[ny][nx] : " + a[ny][nx]);
                    if(nowTile != newTile && a[i][j] == a[ny][nx]){
//                        System.out.println("nowTile : " + nowTile + " newTile : " + newTile);
                        graph.get(nowTile).add(newTile); // 간선 연결
                    }
                }
            }
        }

    }

    public static class Edge{
        int idx;
        int value;
        public Edge(int idx , int value){
            this.idx = idx;
            this.value = value;
        }
    }
    public static void bfs(){
        /*
        계속 탐색하면서 , 가장 큰 타일 넘버 기록하면서,
        그에 맞게 maxValue 도 기록한다.
         */
        Queue<Edge> queue = new LinkedList<>();
        queue.add(new Edge(1 , 1));
        visited[1] = true;

        Loop:
        while(!queue.isEmpty()){
            Edge edge = queue.poll();

            if(maxTile < edge.idx){
                maxTile = edge.idx;
                maxValue = edge.value;
            }
//            System.out.println(edge.idx + " " + edge.value);
            if(TOTAL == edge.idx) break;
            for(Integer number : graph.get(edge.idx)){
                if(!visited[number]){
                    visited[number] = true;
                    parent[number] = edge.idx;
                    queue.add(new Edge(number , edge.value + 1));
                }
            }
        }
    }

    /*
    1 2 2 3 6 6
      2 4 3 5
    6 6 4 5 5 6
     */

    public static void pathTrace(int vertex) throws IOException{
        if(vertex == parent[vertex]){
            output.write(vertex + " ");
            return;
        }else{
            pathTrace(parent[vertex]);
            output.write(vertex + " ");
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        H = N;
        W = 2 * N;
        TOTAL = N * N - N / 2;

        a = new int[H][W];
//        b = new int[H][W];
        parent = new int[TOTAL + 1];
        visited = new boolean[TOTAL + 1];

        for(int i = 0; i <= TOTAL; i++){
            graph.add(new ArrayList<>());
            parent[i] = i;
        }

        int y = 0;
        int x = 0;
        int count = 0;
        for(int i = 0; i < N * N - N / 2; i++){
            st = new StringTokenizer(input.readLine());
            if(y % 2 == 0){ // 홀수 줄
                a[y][x++] = Integer.parseInt(st.nextToken());
                a[y][x++] = Integer.parseInt(st.nextToken());
                count++;
                if(count == N){
                    y++;
                    x = 1;
                    count = 0;
                }
            }
            else{ // 짝수 줄
                a[y][x++] = Integer.parseInt(st.nextToken());
                a[y][x++] = Integer.parseInt(st.nextToken());
                count++;
                if(count == N - 1){
                    y++;
                    x = 0;
                    count = 0;
                }
            }
        }

//        for(int i = 0; i < H; i++) System.out.println(Arrays.toString(a[i]));

        makeGraph();

        bfs();
//        System.out.println(maxTile);
//        System.out.println(Arrays.toString(parent));
        output.write(maxValue + "\n");

        pathTrace(maxTile);

        output.flush();
        output.close();
    }
}

