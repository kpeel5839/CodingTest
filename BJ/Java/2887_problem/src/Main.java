import java.util.*;
import java.io.*;

// 2887 : 행성 터널

/*
-- 전제조건
왕국은 N 개의 행성으로 이루어져 있고,
이 행성을 효율적으로 지배하기 위해서 행성을 연결하는 터널을 만들려고 한다.

행성은 3차원 좌표위의 한 점으로 존재하고 , A(Xa, Ya, Za) 와 B(Xb, Yb , Zb) 를 터널로 연결할 떄 드는 비용은
min(|xA-xB|, |yA-yB|, |zA-zB|) 이다.

이렇게 해서 민혁이는 터널을 총 N - 1 개 건설해서 모든 행성이 서로 연결되게 하려고 한다.(무방향)
이 때 모든 행성을 터널로 연결하는데 필요한 최소비용을 구하시오.
-- 틀 설계
굉장히 쉬운 최소 스패닝 트리로 보인다.
하지만 여기서 하나의 오점이 있다.
행성의 개수가 10만 개가 주어진다라는 것이다.

여기서 무식하게 , 행성의 개수대로 , 모든 행성에서 행성을 연결하는 무식한 짓을 하게 되면
10만 * 10만의 간선의 개수가 나오게 된다.
그렇게 된다라면 당연히 간선을 구하는 과정에서 시간초과다.

그냥 내 생각은 서로간의 행성의 min 값을 구하는 것 이것에서 답이 있다고 본다.
그러니까 최소 스패닝 트리는 가장 낮은 간선으로부터 시작한다.

그렇다라는 것은 굉장히 효율적으로 낮은 간선을 취해야 한다.
간선들의 x 값 , y 값 , z 값의 차이로부터 발생하게 된다.

그러면 일단 내 생각으로 인하면 , x 값 , y 값 , z 값으로 모든 노드들을 정렬하면 되지 않을까?
결국 정렬하게 되면 순서대로 가장 차이가 적은 애들끼리 붙게 되니까,

근데 만일 이게 x 값 혹은 y 값 z 값 하나만 가지고 min 값을 구하게 되면 상관이 없다.
하지만 여기서 고려해야 할 사항은 3가지이다.

min 값을 순서대로 취할 수 있는 방법을 고안해낼 수 있다면 굉장히 쉬운 문제로 해결 될 것 같다.

일단 find , union 이 무조건 적으로 필요하니 함수를 만들어보자.

그럼 일단 한번 주어진 예제를 정렬해보면서 생각을 정렬해보자.
1 11 - 15 -15
2 14 -5 -15
3 -1 -1 -5
4 10 -4 -1
5 19 -4 19

- x 기준
3 -1 -1 -5
4 10 -4 -1
1 11 -15 -15
2 14 -5 -15
5 19 -4 19

- y 기준
1 11 -15 -15
2 14 -5 -15
4 10 -4 -1
5 -19 -4 19
3 -1 -1 -5

- z 기준
1 11 - 15 -15
2 14 -5 -15
3 -1 -1 -5
4 10 -4 -1
5 19 -4 19

마치 힌트를 주는 것 처럼 z 는 오름차순으로 주어져있다.

x 기준으로 차이가 11이 난다.
y 기준으로 차이가 10이 난다.
z 기준으로 차이가 0이 난다.

1 <-> 2(연결)

1 번과 2번이 연결되는 간선은 이제 처리하지 않는다.

x 기준으로 차이가 11이 난다.
y 기준으로 차이가 0이 난다.
z 기준으로 차이가 4가 난다.

y 기준으로 4 <-> 5 번이 연결되었다.

x 기준으로 차이가 1이 난다.
y 기준으로 차이가 3이 난다.
z 기준으로 차이가 4가 난다.

음 .. 이거는 절대 아닌 것 같다.
왜냐하면 -100만 0 1 2 ... 이렇게
되어있다고 했을 때 , 이렇게 하는 경우에는 100만의 가중치를 선택하게 된다.

이렇게 말고 행성의 거리를 오름차순으로 정렬해줄 무엇인가가 필요하다.

그럼 이렇게 하면 되지 않을까?
앞에처럼 정렬하는 건 맞고

정렬을 한 다음에 첫번째는 바로 앞에 있는 애들의 거리를 싹다 뽑아내는 것이다.
edge 들만
만일 근데도 못 구했다.
그러면 다음은 2
3
4 ...
구할 때까지 진행하게 되면
절대로 맨 마지막까지 갈때까지 , 진행이 되지는 않을 것이다.
왜냐?
간선이 주어지는 게 아니니까
좌표가 주어지는 것이니까 말이다.

그래서 결론은
일단 행성에 번호를 매기고
배열을 3개를 만든다.
Point 배열을
그래서 3개를 만든다음에 각각 x , y , z 값을 기준으로 행성을 정렬을 진행한다.
그 다음에 바로 for(int i = 0; i < list.size - 1; i++) 로 계속 Math.abs(list.get(0).x - list.get(0 + dif).x 로 계속 구하는 것이다.
y , z 도 똑같이
그렇게 해서 진행해서 edge 를 만들어준 다음에
최소 스패닝 트리를 만들기를 진행을 반복하는 것이다.
그리고 edge 는 계속해서 뽑아낼때마다 새로해서 정렬하고 새로하고 정렬하고 이런식으로 진행하면 될 것 같다.

맞았다!
처음에 생각했던 게 역시나 맞았다.
기모찌하다...
심지어 시간도 그렇게 느린편은 아니다. 나이스하다.
 */
public class Main {
    public static int[] parent;
    public static List<Edge> edges;
    public static Point[] x , y , z;
    public static int N , count;
    public static long res = 0;
    public static class Edge{
        int sta;
        int des;
        long cost;
        public Edge(int sta , int des , long cost){
            this.sta = sta;
            this.des = des;
            this.cost = cost;
        }
    }
    public static class Point{
        int idx;
        int x;
        int y;
        int z;
        public Point(int idx , int x , int y , int z){
            this.idx = idx;
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public String toString(){
            return "idx : " + idx + " x : " + x +  " y: " + y +  " z : " + z;
        }
    }
    public static int find(int a){
        if(parent[a] == a) return a;
        return parent[a] = find(parent[a]);
    }

    public static void union(int a , int b){
        parent[b] = a;
    }

    public static void getEdge(int dif){
        /*
        x , y , z 들을 차이들을 다 구해주고
        edge 에다가 넣어준다음에 정렬을 해서 넘겨준다.
         */
        edges = new ArrayList<>();
        for (int i = 0; i < x.length - dif; i++) {
            edges.add(new Edge(x[i].idx, x[i + dif].idx, Math.abs(x[i].x - x[i + dif].x)));
        }

        for (int i = 0; i < y.length - dif; i++) {
            edges.add(new Edge(y[i].idx, y[i + dif].idx, Math.abs(y[i].y - y[i + dif].y)));
        }

        for (int i = 0; i < z.length - dif; i++) {
            edges.add(new Edge(z[i].idx, z[i + dif].idx, Math.abs(z[i].z - z[i + dif].z)));
        }

        Collections.sort(edges , (o1 , o2) -> (int)(o1.cost - o2.cost));
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        parent = new int[N + 1];

        for(int i = 0; i <= N; i++) parent[i] = i;

        x = new Point[N];
        y = new Point[N];
        z = new Point[N];

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(input.readLine());

            x[i - 1] = new Point(i , Integer.parseInt(st.nextToken()) ,Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken()));
        }

        y = x.clone();
        z = x.clone(); // y , z 에 x 복사

        Arrays.sort(x , (o1 ,o2) -> o1.x - o2.x);
        Arrays.sort(y , (o1 ,o2) -> o1.y - o2.y);
        Arrays.sort(z , (o1 ,o2) -> o1.z - o2.z);
        int dif = 1;

        while(true){
            getEdge(dif++);

            for(Edge edge : edges){
                int a = find(edge.sta);
                int b = find(edge.des); // 두개의 부모를 찾아줌

                if(a == b) continue; // a , b 가 같은 집합이면 넘어가고 , 이미 선택됬다라면 당연하게도 같은 집합임

                union(a , b);
                count++;
                res += edge.cost;
            }

            if(count == N - 1) break; // N - 1 개의 간선을 선택하면 brea
        }

        System.out.println(res);
    }
}
