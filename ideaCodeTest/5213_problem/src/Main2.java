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
순서대로 진행했는데 또 틀렸다.

-- 해맸던 점
일단 , ans == 0 으로 초기값을 넣어서 , ans = 0 일 때 , 즉 그냥 처음부터
바로 끝 타일일 때 , parent 를 , 즉 제대로된 경로를 출력하지 못하여서,
틀렸었고,

그리고 여기서는 그냥 tile 을 기준으로
tile 대 tile로 갈 수 있는 경우들을 graph화 해서 넣어주었고
그 다음에 bfs를 돌려주어서,
끝까지 도달하는 경우 , 혹은 가장 높은 타일로 이동하는 경우를 그려냈다.
일단 한번 방문한데는 다시 방문할 수 없기 때문에 , 중간에 경로가 바뀐다거나 그런일은 없다.
왜냐하면 이미 방문한 정점은 다시 방문하지 못하니 즉 , parent[nx] = x 가 될 일이 없는 것이다.

그렇기 때문에 더더욱이 내가 가는 정점에 부모로 시작한 정점을 넣어줘야 한다.
그래서 이렇게해서 , maxValue , maxTile 을 저장해놓기 보다는 , pathTrace 를 해서,
경로를 추적하고 , 그 경로의 사이즈를 출력하면 답을 구할 수 있다.
 */
public class Main2 {
    public static int N , H , W , TOTAL , ans = 1;
    public static int[][] a;
    public static int[] parent;
    public static boolean[] visited;
    public static Stack<Integer> stack = new Stack<>();
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
                    if(outOfRange(ny , nx)) continue;
                    int newTile = judgeTileNumber(ny , nx);
                    if(nowTile != newTile && a[i][j] == a[ny][nx]){
//                        System.out.println("nowTile : " + nowTile + " newTile : " + newTile);
                        graph.get(nowTile).add(newTile); // 간선 연결
                    }
                }
            }
        }

    }

    public static void bfs(){
        /*
        계속 탐색하면서 , 가장 큰 타일 넘버 기록하면서,
        그에 맞게 maxValue 도 기록한다.
         */
        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        visited[1] = true;

        while(!queue.isEmpty()){
            Integer x = queue.poll();
//            System.out.println(x);
            for(Integer nx : graph.get(x)){
                if(!visited[nx]){
                    if(ans < nx){
                        ans = nx;
                    }
                    visited[nx] = true;
                    parent[nx] = x;
                    queue.add(nx);
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
            stack.add(vertex);
            return;
        }else{
            stack.add(vertex);
            pathTrace(parent[vertex]);
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
        pathTrace(ans);

        output.write(stack.size() + "\n");
        while(!stack.isEmpty()){
            output.write(stack.pop() + " ");
        }

        output.flush();
        output.close();
    }
}
