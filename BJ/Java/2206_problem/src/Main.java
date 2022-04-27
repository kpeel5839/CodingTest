import java.util.*;
import java.io.*;

// 2206 : 벽 부수고 이동하기
/*
--전제 조건
N * M 의 행렬로 표현되는 맵이 있고 , 맵에서 0은 이동할 수 있는 곳을 나타내고,
1은 이동할 수 없는 곳을 나타낸다,
만약에 이동하는 도중에 한개의 벽을 부수고 이동하는 것이 좀 더 경로가 짧아 진다면,
벽을 한칸 부셔서 이동할 수 있다.
이 경우 가장 짧은 경로로 이동하였을 때의 최단 거리를 구하라
--틀 설계
일단 bfs의 특성을 이해하고 가야한다.
bfs의 특성상 , bfs는 너비우선 탐색이다 , 너비 우선탐색의 성질은
너비를 distance로 따졌을 때 , 진행되는 distance가 무조건 오름차순이라는 것이다.
이 점을 이용해서 하면 된다.
벽을 한번 부시고 이동했다 , 그래서 벽을 부시지 않은 경우보다 해당 지점에 빨리 도착하였다.
이때 그러면 벽을 부시지 않은애의 경로를 무시해야 할까?
절대 아니다 , 앞으로 벽이 존재할 수 있기 때문에 아직까지 벽을 부시지 않은 애는 남겨둔다.
짜피 나중에 ny == h - 1 && nx == w - 1 일 때 빼면 , 짜피 가장 작은 값이 return 이 되니까
그래서 여기서 가장 큰 특성을 이용해서 아주 쉽게 풀수가 있다.
일단 어떤 지점이든 먼저 도달하게 된다면 일단 그 놈은 더 빠른놈이다 , 이 경우를 감안하여서 두가지의 조건만 고려하면 된다.
같거나 , 작은 , 지금까지 부신 벽의 개수가 같거나 큰애는 다음에 해당 지점에 방문하였을 때 , 살려둘 필요가 없다.
더 느리게 왔고 , 벽도 똑같이 부시거나 더 부셨는데 살려둘 이유가 없다.
그래서 이 점을 이용해서 걔내들은 쳐내고 , 가장 먼저 h - 1 , w - 1 에 도달하는 애의 distance를 반환하면 답을 도출해낼 수 있다.
 */
public class Main {
    public static class Point{
        int y;
        int x;
        int value;
        int con;
        public Point(int y , int x , int value , int con){
            this.y = y;
            this.x = x;
            this.value = value;
            this.con = con;
        }
    }
    public static int[][] map;
    public static int w , h , ans = 0;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());

        map = new int[h][w];

        for(int i = 0; i < h; i++){
            String string = input.readLine();
            for(int j = 0; j < w; j++){
                map[i][j] = string.charAt(j) - '0';
            }
        }

        int[][] visited = new int[h][w];
        int INF = Integer.MAX_VALUE;

        for(int i = 0; i < h; i++){
            Arrays.fill(visited[i] , INF);
        }

//        for(int i = 0; i < h; i++) System.out.println(Arrays.toString(map[i]));
//        for(int i = 0; i < h; i++) System.out.println(Arrays.toString(visited[i]));

        Queue<Point> queue = new LinkedList<>();
        visited[0][0] = 0;
        queue.add(new Point(0 , 0 , 1 , 0)); // 아직까지 부신 벽 0개

        // 여기서부터가 중요하다 , visited 가 아직 INF 라면 그냥 방문하는데 , 만약에 아니라면 값을 비교하고
        // 내가 만약 point.con 이 1인데 , 앞에 벽이 있다 , 그러면 못가

        while(!queue.isEmpty()){
            Point point = queue.poll();
            if(point.y == h - 1 && point.x == w - 1){
                ans = point.value;
                break;
            }
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= h || nx < 0 || nx >= w) continue;
                if(map[ny][nx] == 1){ // 벽일 떄
                    if(point.con == 0 && visited[ny][nx] == INF){ // 내가 아직 부순 벽이 없고 , 부서진 벽이 아닐 떄 (이미 일전에)
                        queue.add(new Point(ny , nx , point.value + 1 , point.con + 1));
                        visited[ny][nx] = point.con + 1;
                    }
                }else{ // 벽이 아닐 떄
                    if(point.con < visited[ny][nx]){
                        queue.add(new Point(ny , nx , point.value + 1 , point.con));
                        visited[ny][nx] = point.con;
                    }
                }
            }
        }

//        for(int i = 0; i < h; i++) System.out.println(Arrays.toString(visited[i]));
        System.out.println(ans == 0 ? -1 : ans);
    }
}

