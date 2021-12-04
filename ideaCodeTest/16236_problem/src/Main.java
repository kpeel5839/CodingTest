import java.util.*;
import java.io.*;

public class Main {
    public static int[][] eatMap , map , visited;
    public static int age = 2 , stack = 0 , n , r, c , alive = 0 , min , largeValue = 100000;
    public static Deque<Point> queue = new LinkedList<>();
    public static int[] dx = {0 , 1 , 0 , -1};
    public static int[] dy = {-1 , 0 , 1 , 0}; // 위 , 오른쪽 , 아래 , 왼쪽
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        eatMap = new int[n][n];
        map = new int[n][n];
        visited = new int[n][n]; //1이면 방문한 것 , 0이면 방문 x

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 9){
                    r = i;
                    c = j;
                }
            }
        }
        min = 0;
        while(min != largeValue) { //이제 더이상 distance가 안나오는 경우에 끝남
            min = largeValue; // bfs부르고
            bfs(r , c);
            Loop1:
            for(int i = 0; i < n; i++){ //다시 탐색을 하면서
                for(int j = 0; j < n; j++){
                    if(eatMap[i][j] == min){ //찾으면 해당 자리로 이동
                        stack++; //물고기 먹어서 큼
                        alive += min; //이만큼 더 살아있는다.
                        map[r][c] = 0;
                        map[i][j] = 9;
                        r = i; c = j;
                        clean();
                        break Loop1;
                    }
                }
            }
            if(age == stack){ //나이 먹으면 stack 초기화 이후 나이 증가
                stack = 0;
                age++;
            }
        }
        System.out.println(alive);
    }

    public static void bfs(int y, int x){ //이제 bfs만 정의하면 됨, bfs만 정의하면 답을 구할 수 있음 queue를 이용해서 해야함
        queue.add(new Point(y , x , 0));
        visited[y][x] = 1;
        while(!queue.isEmpty()){ //자 처음에 하고
            Point newP = queue.poll();
            int ny = newP.y;
            int nx = newP.x;
            int nowD = newP.distance; //지금 현재 정보들 받아놓고

            for(int i =0; i < 4; i++){ //뽑은 걸로
                int newY = ny; //현재 위치에 집어넣음
                int newX = nx;
                if((nx + dx[i] < 0 || nx + dx[i] >= n) || (ny + dy[i] < 0 || ny + dy[i] >= n) || (map[ny + dy[i]][nx + dx[i]] > age) || visited[ny + dy[i]][nx + dx[i]] == 1) {
                    continue; //일단 이것들 다 되는지 갈 수 있는지 확인함
                }
                newY += dy[i];
                newX += dx[i]; //갈 수 있으니 가고
                visited[newY][newX] = 1; //방문 표시하고
                queue.add(new Point(newY , newX , nowD + 1)); //큐에 추가
                if(map[newY][newX] != 0 && map[newY][newX] < age){ //근데 이거 큐에 추가한다음에 , 지금 현재 map에 현 위치에 있는게 age보다 작으면 거기다가 그냥 distance 집어넣어버림
                    eatMap[newY][newX] = nowD + 1; //eatMap에다가 distance 집어넣기
                    if(min > nowD + 1){ //그러면서 min 바꾸기 , 일단 distance집어 넣었으니까
                        min = nowD + 1;
                    }
                }
            }
        }
    }

    public static void clean(){
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                eatMap[i][j] = 0;
                visited[i][j] = 0;
            }
        }
    }
    public static class Point{
        int y;
        int x;
        int distance;
        public Point(int y, int x , int distance){
            this.y = y;
            this.x = x;
            this.distance = distance;
        }
    }
}

