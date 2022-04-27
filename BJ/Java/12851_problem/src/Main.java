import java.util.*;
import java.io.*;

// 12851 : 숨바꼭질 2
/*
-- 전체 설계
수빈이는 n 지점에 있고 , 동생은 점 k 지점은 있다.
수빈이는 걷거나 순간이동을 할 수 있다 , 수빈이의 위치가 x 일 때 , 1초 후에 x - 1 , x + 1 ,
2 * x 로 갈 수도 있다 , 수빈이와 동생의 위치가 주어졌을 떄 , 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이
몇 초 후인지 그리고 , 가장 빠른시간으로 차즌ㄴ 방법이 몇 가지 인지 구하는 프로그램을 작성하시오.
-- 틀 설계
일단 그냥 배열은 들어온 200000 정도로 하고 , 어떻게든 해당 지점에 가장 도달을 먼저 한다?
그것은 bfs의 특성으로 미루어 보았을 때 , 가중치가 1만 존재하기 때문에 , 무조건적으로 최소값이다.
그렇기 떄문에 가장 처음에 해당 지점에 방문하게 되면 , 그것이 최소 값이고 ,
최소값이 갱신되게 되면 , 그 이상인 것들은 다 쳐내면서 , 해당 지점에 도달한 애들 count를 세면 된다.

-- 해맸던 점
이전에는 , 이 점을 고려하지 않았음
이런 경우는 많이 없겠지만, 하나의 점에서 움직이는 지점에 동일한 지점으로 가는 경우를 고려하지 못했음
예를 들어서 , 1 에서 시작하면 , 1 * 2 = 2 , 1 + 1 = 2 이다 , 이 경우 k == 2 라면 ,
2 1 이라는 답이 나올 것이다 , 하지만 그렇지 않고 , 정답은 2 2 여야 한다.
이런 경우를 고려하지 않아서 , 틀렸었고 ,
그리고 size = 10 만 으로 해서도 살짝해맸다 , 0 <= k <= 10만 인데 , 10만1로 설정해야하는 것이였다 , (인덱스를 생각해야 하니까)
그리고 , 내가 쓴 방식은 , 다른 곳은 고려하지 않고 , k 만 고려하는 것이다 , bfs 특성상 첫 방문이 무조건 최소 값이다,
이렇게 가중치가 1로만 이뤄진 경우는 말이다 , 그래서 , 그 점을 이용해서 다른 dist 값들은 정확하지 않더라도 , dist[k] 만 정확하면 되는 것이기에,
dist[k] 를 신경써서 , dist[k] 가 결정나면 , dist[k] + 1 의 깊이의 순회가 진행되고 있는 것이니까
이전에 들어가있던 queue 에 있던 값들만 신경쓰면 , 값을 구할 수 있는 것이다.
그래서 dist[k] 가 결정이 났을 때 , 그때부터 queue 에 있던 애들을 queue.isEmpty() 가 될 때까지 꺼내서
dist[k] == point.value && k == point.idx 일 때만 k 에 도착했으면서 value 도 똑같은 애들이니 count++ 을 해주어서
답을 도출해냈다.
 */
public class Main {
    public static int n , k , size = 100001 , count = 1;
    public static int[] dist;
    public static Point point = null;
    public static Queue<Point> queue = new LinkedList<>();
    public static class Point{
        int idx;
        int value;
        public Point(int idx , int value){
            this.idx = idx;
            this.value = value;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        dist = new int[size];

        Arrays.fill(dist , -1); // -1 로 초기화 , 도달하지 않은 곳은 -1로 초기화 되있는 것

        queue.add(new Point(n , 0));

        while(!queue.isEmpty()){ // dist[k] 에 값이 채워지면 그 때부터 그 이상의 값들은 다 쳐낸다.
            point = queue.poll();
            // dist 가 결정될 때 까지는 계속 추가하면서 , 진행한다.
            if(dist[k] != -1){
                if(point.idx == k && dist[k] == point.value) count++;
            }else{ // k 가 아직 결정되지 않았을 때 그때까지는 , 무조건 dist[k] 보다 낮다.
                // 하지만 k 가 결정되고 나서는 무조건 k와 value 가 같다 , 다음에 나오는 것들은
                // 왜냐하면 너비우선 탐색이니까 , 무조건 높거나 같은 것이다.
                // 그 점을 이용해서 , dist[k] 가 결정되고 나서는 , queue 가 empty 해질 때까지 다 뽑는 것이다.
                dist[point.idx] = point.value; // 무조건 point.idx에 오는 값들 , 이 값들은 처음 오는 값이 최소값이다.
                // 근데 물론 계속해서 , 갱신이 될 수도 있다 , 하지만 dist[k] 만 신경쓰면 되는 것이지 다른 것은 신경쓸 바가 없다,
                if(point.idx != 0 && dist[point.idx - 1] == -1){ // -1 로 갈 수 있는 상황
                    queue.add(new Point(point.idx - 1 , point.value + 1));
                }
                if(point.idx != size - 1 && dist[point.idx + 1] == -1){ // 1 로 갈 수 있는 상황
                    queue.add(new Point(point.idx + 1 , point.value + 1));
                }
                if(point.idx * 2 < size && dist[point.idx * 2] == -1){ // 2 배가 되는 칸을 넘어가도 되는 상황
                    queue.add(new Point(point.idx * 2 , point.value + 1));
                }
            }
        }

        System.out.println(dist[k] + "\n" + count);
    }
}