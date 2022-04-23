import java.util.*;
import java.io.*;

// 6593 : 상범 빌딩

/*
-- 전제조건
상범 빌딩은 , 층이 있고 , 그리고 각 행과 열이 있다.
격자로 나누어져 있어서 , 접근이 허락된 격자가 아니면 갈 수가 없다.
그래서 상하좌우와 남북 이렇게 6개의 방향이 있다.
S가 시작 위치이고 , E가 탈출지점일 때 , 탈출까지 걸리는 시간을 표시하여라

탈출에 성공하면 Escaped in (res) minute(s). 을 출력하면 되고,
안되면 Trapped! 를 출력하면 된다.
-- 틀설계
그냥 배열을 3차원 배열로 만들어서
진행한다. 처음에 배열의 사이즈는
H , R , C 이렇게 순서대로 주어진다.
그래서 여기서 그냥 쉽다
그냥 bfs 에서 상하의 방향만 추가해주고 , 지점을 정확히만 캐치해내면 된다.
즉 int ny = point[0] + dy[i];
int nx = point[1] + dx[i]; 이렇게 했던 것이
그냥 하나가 더 늘어나는 것이다.
그냥 쉬우니까 괜찮다.

ny , nx 배열을 그냥 두칸을 더 늘리고
0 ~ 3 은 상하좌우 4 ~ 5 는 남북으로 나누고 한다고 하자.

-- 해맸던 점
큐 초기화 안해주고,
출력문 in 인데 int라고 해서 틀렸었음 ㅋㅋ

 */
public class Main {
    public static int[] dz = {0 , 1 , 0 , -1 , 0 , 0} , dy = {-1 , 0 , 1 , 0 , 0 , 0} , dx = {0 , 0 , 0 , 0 , 1 , -1};
    public static char[][][] map;
    public static boolean[][][] visited;
    public static int[] start = new int[3], end = new int[3];
    public static int H , R , C , res;
    public static Queue<int[]> queue = new LinkedList<>();

    public static boolean outOfRange(int h , int y , int x){
        if (h < 0 || h >= H || y < 0 || y >= R || x < 0 || x >= C) {
            return true;
        }
        return false;
    }

    public static void bfs(){
        /*
        end 지점에 도착하면 끝이다.

        일단 큐에다가 넣고 시작하고,
        그냥 for(int i = 0; i < 6; i++) 를 해주고
        추가적으로 해줄 것은 그냥
        end point 에 도달한 pointer 를 끝내면 끝나는 것이다.
         */

        queue = new LinkedList<>();

        queue.add(new int[]{start[0] , start[1] , start[2] , 0});
        visited[start[0]][start[1]][start[2]] = true;

        while(!queue.isEmpty()){
            int[] point = queue.poll();

            if(point[0] == end[0] && point[1] == end[1] && point[2] == end[2]){
                res = point[3];
                break;
            }
            for(int i = 0 ; i < 6; i++){
                int nz = point[0] + dz[i];
                int ny = point[1] + dy[i];
                int nx = point[2] + dx[i];

                if(outOfRange(nz , ny , nx) || visited[nz][ny][nx] || map[nz][ny][nx] == '#') continue;

                visited[nz][ny][nx] = true;
                queue.add(new int[]{nz , ny , nx , point[3] + 1});
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        while(true) {
            st = new StringTokenizer(input.readLine());


            H = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            if(H == 0 && R == 0 && C == 0) break;

            res = 0;

            visited = new boolean[H][R][C];
            map = new char[H][R][C];

            for (int i = 0; i < H; i++) {
                for (int j = 0; j < R; j++) {
                    String string = input.readLine();
                    for (int c = 0; c < C; c++) {
                        map[i][j][c] = string.charAt(c);
                        if(map[i][j][c] == 'S') start = new int[]{i , j , c};
                        else if(map[i][j][c] == 'E') end = new int[]{i , j , c}; // 시작 , 끝 위치 받음
                    }
                }
                input.readLine(); // 공백을 받음
            }

            bfs();


            if (res == 0) System.out.println("Trapped!");
            else System.out.println("Escaped in " + res + " minute(s).");
        }
    }
}