import java.util.*;
import java.io.*;

// 13511 : 트리와 쿼리 2

/*
-- 전제 조건
N개의 정점으로 이루어진 트리가 있다.
정점은 1번부터 N번까지 번호가 매겨져 있고 , 간선은 1번부터 N - 1 번까지 번호가 매겨져있다.

간선들의 정보가 주어지고 , 쿼리가 주어지면 해당 값을 출력하면 된다.

1 u v : u 에서 v 로 가는 경로의 비용을 출력한다.
2 u v k : u 에서 v 로 가는 경로에 존재하는 정점 중에서 k번째 정점을 출력한다. k 는 u 에서 v 로 가는 경로에 포함된 정점의 수보다 작거나 같다.
이 말은 즉 , k 가 u , v 경로 사이에 있는 정점의 개수보다 작다라는 것 , 그러니까 k 가 안나올 수 는 없다.

그렇게 했을 때 , 답을 구하시오.
-- 틀 설계
일단 LCA 알고리즘을 사용한다.
근데 , 이 경우는 일단 parent 도 지정하면서 , 해당하는 cost들도 저장해야한다.

일단 k는 0 부터 , 주어지는 정점의 개수가 있어야 한다.
예를 들어서 10만개가 주어지면 , 그냥 쭉 이어진 되게 밸런스하지 않은 트리일 수 도 있다.
그럴 경우 그냥 트리가 일자로 쭉 이어졌다고 가정하였을 때 , 10만 개가 있으면
처음에는 본인의 위, 그 다음에는 위위,
즉 log2(10만) 이 값이 k 값으로 지정할 수 있다 이 k 값은
parent[N + 1][k] 로 지정이 가능하다.

음 예를 들어서 정점이 두개라고 가정해보자.
정점이 두개라고 하였을 때 , 최대 정점이 가질 수 있는 정점은 0이다.
근데 log2(2) 는 1 이다 , 그러면 k == 1 이다.
그러면 맞다 그러면 범위를 좀 더 넓혀서 4 , 8 을 진행해보자.

4라면 k == 2 이다.

그럼 가장 마지막에 있는 애는
부모가 2개만 필요하다 이것도 맞다.

그러면 5 일때의 로그값을 한번 구해보자.
k == 2.xxxx 이다.

결국 열심히 짜봤지만
틀렸다 .. ㅠ

 */
public class Main3 {
    public static int N , height;
    public static List<ArrayList<Edge>> graph = new ArrayList<>();
    public static int[] depth;
    public static long[][][] parent; // parent[0][i][j] 는 조상이고 , parent[1][i][j] 는 해당 조상까지의 비용이다.
    public static void getDepth(int start , int depthValue){
        /*
        각 정점의 depth 를 구해야 한다.
        depth 는 1부터 시작하도록 한다.
        그리고 정점도 1번부터 시작하도록 한다.
         */
        boolean[] visited = new boolean[N + 1];
        Queue<int[]> queue = new LinkedList<>(); // arr[0] 은 현재 정점 , arr[1] 은 현재 depth

        visited[start] = true;
        queue.add(new int[]{start , depthValue}); // start 와 depth 를 추가해서

        // 1번이 루트가 아니라고 하더라도 , 그냥 트리 모양이니까 내가 1번을 루트 취급하면된다. 근데 사실 잘 모르겠다

        while (!queue.isEmpty()) { // bfs로 간다.
            int[] arr = queue.poll();
            depth[arr[0]] = arr[1]; // depth 등록

            for(Edge edge : graph.get(arr[0])){
                if(!visited[edge.idx]){ // 방문하지 않았을 때만 진행
                    visited[edge.idx] = true;
                    // 해당 정점의 부모는 현재 정점이라는 것도 등록
                    parent[0][edge.idx][0] = arr[0];
                    // 그리고 현재 바로 조상의 값도 등록
                    parent[1][edge.idx][0] = edge.cost;
                    queue.add(new int[]{edge.idx , arr[1] + 1});
                }
            }
        }
    }

    public static void fillParent(){
        /*
        각 정점들의 parent 들을 2 ^ n 꼴로 정리해주어야 한다.
        그래서 현재 parent[0 ~ 1][i][0] 까지는 채워놓은 상태이니까
        해당 것들을 이용해서 cost 와 그런 것들을 다 정리하면 된다.

        i 가 높이이고 (height) 까지
        j 가 해당 정점이다.

        그래서 일단 이용할 수 있는 점은
        parent[i][j]의 는?
        parent[parent[i][j]][j - 1] 이라 는 사실을 이용할 수 있다.

        그리고 동시에 해당 값을 이용해서 순차적으로 해당 정점까지의 비용도 구할 수가 있다.
         */

        for(int i = 1; i < height; i++){ // height 값을 이용한다. 0 은 이미 구했으니까 1번째부터 시작한다.
            for(int j = 1; j <= N; j++){ // j는 N까지 구하면 된다.
                parent[0][j][i] = parent[0][(int)parent[0][j][i - 1]][i - 1];
                parent[1][j][i] = parent[1][j][i - 1] + parent[1][(int)parent[0][j][i - 1]][i - 1];
                // 이런식으로 비용까지 구하기가 가능하다.
            }
        }

    }

    public static long lca(int a , int b , int k){
        /*
        k == -1 이면
        그냥 해당 같은 지점까지의 그냥 경로 비용만 출력하면 된다.

        만약 k 에 값이 있다면 , 해당 경로 중 몇 번 째 정점을 찾아야한다. (그래서 그것을 반환해야 한다.)

        비용을 출력하는 것은 됐음
        이제 몇 번째 정점에 있는 것을 고르는 알고리즘만 진행하면 된다.
         */

        // 일단 해당 두 정점까지의 비용만 출력해보자.
        if(depth[a] < depth[b]){ // a 를 올릴 것이기 때문에 b가 만약에 더 크다면 스왑 진행
            int tmp = b;
            b = a;
            a = tmp;
        }

        long res = 0;

        for (int i = height - 1; i != -1; i--) {
            if (1 << i <= depth[a] - depth[b]) { // 이전에 했던 방법으로 하면 안된다.
                res += parent[1][a][i];
                a = (int) parent[0][a][i];
            }
        }

        if (a == b && k == 1) { // 애초에 a의 조상이 b 였을 때
            return res;
        }

        for (int i = height - 1; i != -1; i--) {
            if (parent[0][a][i] != parent[0][b][i]) { // 공통 조상 위에는 조상 다 같을 수 밖에 없음 , 그 같지 않을 때까지 진행한다.
                res += parent[1][a][i] + parent[1][b][i];
                a = (int) parent[0][a][i];
                b = (int) parent[0][b][i];
            }
        }

        if(k == 1) {
            return res + parent[1][a][0] + parent[1][b][0];
        }

        else {
            return parent[0][a][0];
        }
    }

    public static class Edge{
        int idx;
        int cost;
        public Edge(int idx , int cost){
            this.idx = idx;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        height = (int)(Math.ceil(Math.log(N) / Math.log(2)));

        parent = new long[2][N + 1][height]; // [0][i][j] 에서 i 는 해당 정점을 나타내고 , j 는 2 ^ j 의 조상을 나타낸다.
        depth = new int[N + 1];

        for(int i = 0; i <= N; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < N - 1; i++){
            st = new StringTokenizer(input.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph.get(a).add(new Edge(b , cost));
            graph.get(b).add(new Edge(a , cost));
        } // 다 받음

        getDepth(1 , 1);
        fillParent();

        int T = Integer.parseInt(input.readLine());

        for(int i = 0; i < T; i++){
            st = new StringTokenizer(input.readLine());

            int judge = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int k = 1;

            if(judge == 2){ // 해당 정점까지의 비용
                k = Integer.parseInt(st.nextToken());
            }

            if(k == 1){
                output.write(lca(a , b , k) + "\n");
            }

            else{
                long root = lca(a , b , k);

                int cnt = depth[a] - depth[(int)root] + 1;

                if(cnt == k){ // 같을 때에는 바로 반환
                    output.write(root + "\n");
                }

                else if(cnt < k){ // k 가 더 클 때에는 k를 다시 계산해주어야함
                    k = cnt + depth[b] - depth[(int)root] - k + 1;
                    k--; // k-- 를 하면서 시작

                    for(int j = height - 1; j != -1; j--){
                        if(1 << j <= k){
                            k -= 1 << j;
                            b = (int)parent[0][b][j];
                        }
                    }

                    output.write(b + "\n");
                }

                else{
                    k--;
                    for(int j = height - 1; j != -1; j--){
                        if(1 << j <= k){
                            k -= 1 << j;
                            a = (int)parent[0][a][j];
                        }
                    }

                    output.write(a + "\n");
                }
            }
        }

        output.flush();
        output.close();
    }

}