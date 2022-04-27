import java.util.*;
import java.io.*;

// 1981 : 배열에서 이동
/*
-- 전제조건
n * n짜리의 배열에서 1 , 1에서 n , n 까지 이동하려고 한다.
이동할 때에는 상 하 좌 우의 네 인접한 칸으로만 이동가능하다.

이와 같이 이동하다보면 , 배열에서 몇 개의 수를 거쳐서 이동하게 된다. 이동하기 위해 거쳐 간 수들 중 최댓값과
최솟값의 차이가 가장 작아지는 경우를 구하는 프로그램을 작성 , 즉 (1 , 1)부터 (n , n)까지 이동하는 데 거쳐간
숫자들의 모임에서 최댓값 - 최솟값이 가장 작아지는 경우로 가야하는 것이다 , 그리고서 값이 나오면 그 값을 출력한다.

n 이 100까지도 가능하고 , 배열의 각 값들은 200까지 가능하다.
-- 틀 설계
1 1 3 6 8
1 2 2 5 5
4 4 0 3 3
8 0 2 3 4
4 3 0 2 1

여기서는 1 , 1 , 2 , 2 , 0 , 2 , 0 , 2 , 1의 경우에서 가능하다.

그래서 전체적인 설계는 일단 배열에서 min , max 값을 찾아서 , 그 값을 기준으로 최대 , 최소를 찾는다.
예를 들어서 배열에 0 ~ 2 까지의 범위의 숫자밖에 없다하면, 본인이 n , n 까지 가면서 최대 - 최소 값의 최대는 2이다.
그렇기 때문에 0 , 2 까지 left , right 를 잡고서 , mid = right + left / 2 를 해서 mid 값으로 계속 탐색을 해야함
mid 값으로 탐색을 해서 n , n 까지 mid 값으로 갈 수 있었으면 값이 더 낮아질 수 있는 확률을 가진 거고
만일 가지 못했다면 ? 갈 확률이 전혀 없는 것이기 때문에 전자인 경우에는 right = mid - 1; 후자는 left = mid + 1; 로 정의할 수 있다.
그러고서 right + 1 값을 출력하면 된다.

쨋든 mid 값을 경로에서 최대 - 최소 라고 잡고
low , high 값을 잡는다 , 그래서 mid 값을 딱 받았다 , 에를 들어서 mid 값이 2라고 할 때
배열에서의 값의 범위가 0 ~ 3 이라고 하면 가능한 경우는 0 ~ 2 혹은 1 ~ 3 이다.
그렇기 때문에 for(int i = 0; i + mid <= max; i++) 이고 , 그리고 low = i; high = i + mid 이다.

이 상태로 bfs 조건을 low <= map[i][j] <= high 조건만 추가해서 넣어놓으면 그냥 바로 답을 구할 수가 있다.

-- 해맸던 점
bfs에서 불가능한 지점을 걸러내는 지점 , 조건문에서 visited == 1 이거나 , 혹은 ny , nx 가 범위를 넘었다거나 그러면 무조건 continue 를 해야하는데
뒤에다가 && 를 붙여놔서 이것들 중 하나이상이 해당이 되어도 실행이 되는 경우를 만들어버렸음 (왜 에러가 안난지는 모르곘다)
그래서 원래 &&에다가 !(low , high의 범위에 속하는 경우) 즉 꼭 범위에 속하지 않아야만 걸러내지는 조건으로 해놔서
|| 로 바꿨다.

그리고 범위에서 시작점을 포함하지 않아서 살짝 해맸음
 */
public class Main {
    public static int n , left , right , max = 0, min = Integer.MAX_VALUE;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static int[][] map , visited;
    public static class Point{
        int y;
        int x;
        public Point(int y, int x){
            this.x = x;
            this.y = y;
        }
    }
    public static boolean bfs(int diff){
        for(int i = 0; i + diff <= max; i++){
            int low = i;
            int high = i + diff;

            if(!(low <= map[0][0] && map[0][0] <= high)) continue;
            visited = new int[n][n];
            Queue<Point> queue = new LinkedList<>();
            queue.add(new Point(0 , 0));
            visited[0][0] = 1;

            while (!queue.isEmpty()) {
                Point point = queue.poll();

                for(int j = 0; j < 4; j++){
                    int ny = point.y + dy[j];
                    int nx = point.x + dx[j];
                    if(ny < 0 || ny >= n || nx < 0 || nx >= n || visited[ny][nx] == 1 || !(low <= map[ny][nx] && map[ny][nx] <= high)) continue;
                    if(ny == n - 1 && nx == n - 1) return true; // 이러면 바로 지금 현재 diff 로는 가능한 것이니 바로 return true를 해준다.
                    visited[ny][nx] = 1;
                    queue.add(new Point(ny , nx));
                }
            }
        }

        return false;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        map = new int[n][n];
        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                max = Math.max(map[i][j] , max);
                min = Math.min(map[i][j] , min);
            }
        }

        left = 0;
        right = max - min;

        while(left <= right){
            int mid = (right + left) / 2;
            if(bfs(mid)){ // mid로 찾는 것을 실패하면 ? 지금 값이 너무 작은 것이다 , 값을 올려야함 , 고로 left값을 올려야함
//                System.out.println("able" + mid);
                right = mid - 1;
            }else{
//                System.out.println("possible " + mid);
                left = mid + 1;
            }
        }

        System.out.println(right + 1);
    }
}

