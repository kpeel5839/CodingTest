import java.util.*;
import java.io.*;

// 1261 : 알고스팟
/*
-- 전제조건
1. 알고스팟 운영진이 모두 미로에 갇혀있다
2. 벽은 부수지 않으면 이동할 수가 없다 (이동하려면 부셔야 된다는 말)
3. 모든 운영진이 같은방에 존재함
4. 상하좌우 밖에 못움직임
5. 벽을 부수면 이동할 수 있다 , 빈방과 같아짐
6. (0, 0)에 있는 운영진이 (r , c)로 가려면 최소 벽을 몇개 부수어야 하는지 프로그램을 작성하시오
-- 틀 설계
1. 4 방향으로 움직일 수 있도록 dx , dy를 선언한다.
2. map을 받아준다.
3. 이제 1 , 1에서 시작한다 n , m 까지 가는 것이 목적이다.
4. 한 지점에서 시작하며 , 그 지점은 0 , 0 이다 그리고 r - 1 , c - 1까지 가는 것이 목표이다
5. 일단 Point 클래스를 선언하여서 queue 에다가 처음에는 queue.add(new Point(0 , 0 , 0)) 을 추가한다
6. 그러고서 일단 그냥 주변에 있는 것들을 다 집어넣는데 , 여기서 비용이 0인 칸을 queue.addFirst로 집어넣는다 (높은 우선순위를 보장)
7. 그리고 한번 갔던 위치는 갈 수 없도록 visited 도 선언하여서 관리한다.
8. 그래서 Point 를 뽑았을 때 point.y == r - 1 && point.x == c - 1 이면 현재 값을 출력한다.
-- 0-1BFS 원리
1. 그냥 0 , 1로 가중치들이 이루어져 있기 때문에
2. 낮은 가중치를 먼저 처리하는 것임
3. 예로 4개의 정점에 0 , 1의 가중치를 가진 간선들로 이루어져 있는 그래프를 생각해보면 쉬움
4. 이것은 그리고 성능 향상을 위해서 짜피 0 , 1 로 이루어져있으니까 , map 자체를 지나온곳을 -1로 처리하는 방법이 존재함
5. 그러고서 0 , 1 만 queue에다가 집어넣으면 되는 것
6. 이렇게 visited 처리를 하는 것이고 다익스트라 우선순위 큐 방법도 결국은 if(dist[edge.idx] < cost) 면 continue 하는 방법에서
7. 어떻게 보면 다시 여기를 방문하지 않도록 방문처리를 하는 것과 굉장히 흡사하다고 할 수 있음 , 근데 완전히 접근 못하는 것은 아니고 가중치가 더 낮은 경우에는 접근이 가능해
8. visited 사용과 완전히 같은 것은 아니다.
 */
public class Main {
    public static class Point{
        int y;
        int x;
        int value;
        public Point(int y, int x, int value){
            this.y = y;
            this.x = x;
            this.value = value;
        }
    }
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int c = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        int[][] map = new int[r][c];

        for(int i = 0; i < r; i++){
            String string = input.readLine();
            for(int j = 0; j < c; j++){
                map[i][j] = string.charAt(j) - '0';
            }
        }

        LinkedList<Point> queue = new LinkedList<>();
        queue.add(new Point(0 , 0 , 0));

        while(!queue.isEmpty()){
            Point point = queue.poll();
            int value = point.value;
            if(point.y == r - 1 && point.x == c - 1){
                System.out.println(value);
                break;
            }
            for(int i = 0; i < 4; i++){
                int ny = point.y + dy[i];
                int nx = point.x + dx[i];
                if(ny < 0 || ny >= r || nx < 0 || nx >= c || map[ny][nx] == -1){
                    continue;
                }

                if(map[ny][nx] == 0){
                    queue.addFirst(new Point(ny , nx , value));
                } else if(map[ny][nx] == 1){
                    queue.add(new Point(ny , nx , value + 1));
                }

                map[ny][nx] = -1;
            }
        }
    }
}
