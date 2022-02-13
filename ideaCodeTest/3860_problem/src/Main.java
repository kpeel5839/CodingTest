import java.util.*;
import java.io.*;

// 3860 : 할로윈 묘지
/*
--전제 조건
일단 할로윈 묘지에 있는데 , 웜홀같은 구멍들이 존재하고 , 그냥 걸어갈 수도 있다.
이 모든 게 주어졌을 때 가장 빠른 길을 택하시오.
--틀 설계
bfs 로 지점들을 직접 움직여서 가는 길을 구해서
edge 에다가 집어넣는다.
그 다음에 벨만 포드를 적용하면 될 듯하다.

--결론
결국 해답을 보았음 , 근데 나는 bfs로 갈 수 있는 지점까지의 거리를 구해서 갔는데 사람들은 그냥 바로바로 1칸씩만 이동하는 리스트를 만들었음
, 그리고 생각을 못했었지만 , 귀신구멍에서 출발하면 안되고 , 도착지에서도 출발하는 간선을 만들어서는 안됨 ,
그래서 결국은 1짜리 간선을 인접한 곳에서 그냥 이어주고 , bfs를 써서 시간 허비하지말고 ,
그래서 거기서 벨만포드를 적용하면 될 듯,
그리고 벨만포드 굳이 , visited 안해도 될듯 , 간선을 하나하나씩 선택한다는 개념이긴 하지만 , 사실 그냥 갱신되는 대로 선택해도 된다.
음의 사이클이 있다면 짜피 , 정확치 않은 답이니 , 딱 v - 1 개의 간선을 선택한다는 생각으로 굳이 visited 를 할 필요가 없다는 것이다.

그리고 , 귀신구멍에서 출발하면 안되는 이유가 귀신들어가는 구멍과 나오는 구멍이 겹칠 때에는 cost 가 음수이면 무조건 never 가 나오게된다.
그리고 , 뭣보다 귀신 구멍에서는 무조건 해당 구멍으로 이동한다고 생각해야한다 , 만일 귀신구멍이 묘지 왼쪽을 막고있고 , 아래에 묘비가 있다면 impossible 이 출력되야 할 것이다.
귀신 구멍으로 자꾸 빠져나오니까
 */
public class Main {
    public static int[][] map;
    public static int h , w;
    public static List<Point> pointList;
    public static List<Edge> edges;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static class Edge{
        int sta;
        int des;
        int cost;
        public Edge(int sta , int des , int cost){
            this.sta = sta;
            this.des = des;
            this.cost = cost;
        }

        @Override
        public String toString(){
            return "sta : " + sta + " des : " + des + " cost : " + cost;
        }
    }
    public static class Point{
        int y;
        int x;
        int value;
        public Point(int y, int x , int value){
            this.y = y;
            this.x = x;
            this.value = value;
        }
        public Point(int y, int x){
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString(){
            return "y : " + y + " x : " + x;
        }
    }
    public static void bfs(){
        /*
        그냥 전체 맵을 순환하면서,
        edges에다가 넣어줄 것인데 , 범위를 벗어나지 않으면서 도착지가 묘비가 아닌곳을 택한다.
        출발지는 도착지 , 귀신구멍 , 묘비가 아니여야한다.
         */
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                if(i == h - 1 && j == w - 1) continue;
                if(map[i][j] == -1 || map[i][j] == 1) continue;
                for(int c = 0; c < 4; c++){
                    int ny = i + dy[c];
                    int nx = j + dx[c];
                    if(ny < 0 || ny >= h || nx < 0 || nx >= w || map[ny][nx] == -1) continue;
                    edges.add(new Edge(i * w + j , ny * w + nx , 1));
                }
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        while(true){ // w , h == 0 이 들어오면 break;
            st = new StringTokenizer(input.readLine());

            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            if(w == 0 && h == 0) break;

            // 정점은 w * h 만큼 가지는 것이고 , (행 * 열) + 열 이것이 이 아이의 인덱스 값이 될 것이다.
            // 파싱할 때에는 그래서 index % 열 , index % 열 이런식으로 하면 된다.

            // dist = new int[w * h]
            long[] dist = new long[w * h]; // vertex
            map = new int[h][w]; // map
            edges = new ArrayList<>();
            pointList = new ArrayList<>();
            int g = Integer.parseInt(input.readLine());

            for(int i = 0; i < g; i++){ // 묘비의 개수
                // 묘비는 거리로 될 수 없는 수인 , -1을 집어넣어서 , 해당 지점까지 도착할 수 있는지 없는지를 확인한다.
                // bfs를 돌리고 났는데 pointList중 0인 지점이 있다면 거기는 도달하지 못하는 곳이다.
                st = new StringTokenizer(input.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                map[y][x] = -1;
            }

            int e = Integer.parseInt(input.readLine());
            for(int i = 0; i < e; i++){
                // 무덤을 표시하고 , x1 , y1 , x2 , y2 다 저장하고 , 해당 방향으로 향하는 edge 도 만든다.
                st = new StringTokenizer(input.readLine());
                int x1 = Integer.parseInt(st.nextToken());
                int y1 = Integer.parseInt(st.nextToken());
                int x2 = Integer.parseInt(st.nextToken());
                int y2 = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                map[y1][x1] = 1; // 귀신 구멍 표시
                edges.add(new Edge(y1 * w + x1 , y2 * w + x2 , cost));
            } // 귀신 구멍 표시했음 , edge 에 추가도하고 , pointList에도 담아서 위치 저장했음

            bfs(); //edge List 생성

            // 이제 여기서부터 벨만포드만 적용시키면 끝
            // 묘지 끝까지 이동하지 못하면 impossible , 그게 아니고 cycle 이 생기면 Never , 도달 할 수 있다면 값을 출력해라.
            // dist[w * h - 1] 값이 Integer.MAX_VALUE 이면 Impossible 출력하면 된다.

            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[0] = 0;

            boolean cycle = false;
            for(int i = 0; i < w * h; i++){
                for(Edge edge : edges){
                    if(dist[edge.sta] != Integer.MAX_VALUE && dist[edge.des] > edge.cost + dist[edge.sta]) {
                        dist[edge.des] = edge.cost + dist[edge.sta];
                        if (i == w * h - 1) cycle = true;
                    }
                }
            }

//            System.out.println(Arrays.toString(dist));

            if(cycle){
                output.write("Never" + "\n");
            }else{
                if(dist[h * w - 1] == Integer.MAX_VALUE) output.write("Impossible" + "\n");
                else output.write(dist[h * w - 1] + "\n");
            }
        }

        output.flush();
        output.close();
    }
}
