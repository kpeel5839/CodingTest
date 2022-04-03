import java.util.*;
import java.io.*;

// 11967 : 불 켜기
/*
-- 전제 조건
(1 , 1) 만 불이켜져있고 , 나머지는 불이 꺼져있고,
정보로 어떤 칸에서 몇번 째 칸의 불을 킬 수 있는지 주어지고,
거기로 가면 해당 위치의 불을 킬 수 있다.

이러할 때 , 최대로 불을 킬 수 있는 방의 최대 개수를 구하시오.
-- 틀 설계
그냥 각각 받아서 , List 배열을 만들어서 , 해당 위치에 가면 해당 List 에 있는 map을 다 1로 만든다.
그러고서 계속 이동하면서 불을 키고 , 끝났을 때,
맵에 1이 몇개가 있는지 확인하면 된다.

그래서 , 결국은 , List[] light 배열과,
실질적으로 1 , 0 으로 갈 수 있는 곳인지 아닌지를 밝히는 map
그리고 한번 갔던 곳은 다시 가지 않는 visited 배열을 만들고
다 이동한다음 , 1의 개수를 세주는 getRes 함수도 만들어주면 될 것 같다.

아 그냥 막 키기만 해서는 안된다,
키면은 다시 갈 수 있나 한번 가봐야 함

이러면 그냥 bfs 로 하는 것이 아닌 , 그냥 주변에 현재 visited 된 곳이 있다면?
그러면 추가해주는 것도 나쁘지 않을 듯

-- 해맸던 점
역시나 주변에 방문한 곳이 있으면 불을 킨곳을 추가해주는 것은 틀리지 않았아므
하지만 내가 간과했던 점이 있었음 해당 지점을 visited 처리하지 않았던 것이였음
그렇게 되면 여기서 추가되고 , 직접적으로 갔을 때도 추가되고 , 이렇게 하다보면 분명히 오류가 있을 거라는 것이
내 생각이다.

그래서 , 원래는
for(int[] on : light[point[0]][point[1]]){
    map[on[0]][on[1]] = 1;
    for(int i = 0; i < 4; i++){
        int ny = on[0] + dy[i];
        int nx = on[1] + dx[i];
        if(outOfRange(ny , nx)) continue;
        if(visited[ny][nx]){
            queue.add(new int[]{on[0] , on[1]});
            break;
        }
    }
}
코드가 이러하였는데,

바꾼 코드는

for(int[] on : light[point[0]][point[1]]){
    if(visited[on[0]][on[1]]) continue; // 이미 방문한 적 있으면 아웃
    map[on[0]][on[1]] = 1;
    for(int i = 0; i < 4; i++){
        int ny = on[0] + dy[i];
        int nx = on[1] + dx[i];
        if(outOfRange(ny , nx)) continue;
        if(visited[ny][nx]){
            queue.add(new int[]{on[0] , on[1]});
            visited[on[0]][on[1]] = true;
            break;
        }
    }
}

이렇게 변경하였음.
이거를 이미 알바하면서 생각했었는데 , 거기서 visited[ny][nx] 에다가 true 집어넣는 바보같은 행동했음

그래서 이렇게 여기서 queue 에다가 추가해주더라도,
visited 처리가 가능하도록 하고,
그래서 queue 에서 poll 될 때 이미 visited 된 상태로 나오게 된다,
즉 불필요한 반복횟수와 , 그로 인해서 발생하는 오류에서 벗어날 수 있음
근데 , 그 오류가 무엇인지는 솔직히 잘 모르겠음...

근데 하나 확실한 건 굳이 다른 사람이 한 것처럼 비효율적인 방법을 사용하지 않고 , 해결하였음
 */
public class Main {
    public static List<int[]>[][] light;
    public static int[][] map;
    public static boolean[][] visited;
    public static int N , M , res = 0;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};

    public static void getRes(){
        int count = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(map[i][j] == 1) count++;
            }
        }
        res = count;
    }

    public static void dfs(int y, int x){
        /*
        여기서는 도착한 곳의 list를 이용해서 map[y][x] 를 1로 만든다.

        그 다음에 , 갈 수 있는 곳을 탐색한다. , 이미 visited 된 곳과 , 0인 곳은 가지 못한다.
        당연히 범위를 넘어가는 것도 안된다.
         */

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{y , x});

        while (!queue.isEmpty()) {
            int[] point = queue.poll();

            // 불을 켰는데 이미 주변에 visited 한 것이 있으면 , 다시 와서 여기로 올 수 있으니까 , 추가하고 , 만일 추후에 방문할 곳에 불이 켜진 것이라면 더 상관 x
            for(int[] on : light[point[0]][point[1]]){
                if(visited[on[0]][on[1]]) continue; // 이미 방문한 적 있으면 아웃
                map[on[0]][on[1]] = 1;
                for(int i = 0; i < 4; i++){
                    int ny = on[0] + dy[i];
                    int nx = on[1] + dx[i];
                    if(outOfRange(ny , nx)) continue;
                    if(visited[ny][nx]){
                        queue.add(new int[]{on[0] , on[1]});
                        visited[on[0]][on[1]] = true;
                        break;
                    }
                }
            }

            for(int i = 0; i < 4; i++){
                int ny = point[0] + dy[i];
                int nx = point[1] + dx[i];

                // 범위 , 이미 방문 , 1이 아닐 경우 방문 x
                if(outOfRange(ny , nx) || visited[ny][nx] || map[ny][nx] != 1) continue;

                visited[ny][nx] = true;
                queue.add(new int[]{ny , nx});
            }
        }
    }

    public static boolean outOfRange(int y, int x){
        if(y < 0 || y >= N || x < 0 || x >= N) return true;
        return false;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        light = new ArrayList[N][N];
        map = new int[N][N];
        visited = new boolean[N][N];

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                light[i][j] = new ArrayList<>();
            }
        }

        // 불이 켜지지 않은 곳은 0이고 , 켜진 곳은 1이다.
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(input.readLine());

            int sY = Integer.parseInt(st.nextToken()) - 1;
            int sX = Integer.parseInt(st.nextToken()) - 1;
            int dY = Integer.parseInt(st.nextToken()) - 1;
            int dX = Integer.parseInt(st.nextToken()) - 1;

            light[sY][sX].add(new int[]{dY, dX}); // light[sY][sX] 에다가 추가
        }

        visited[0][0] = true;
        map[0][0] = 1;

        dfs(0 , 0);

        getRes();

        System.out.println(res);
    }
}