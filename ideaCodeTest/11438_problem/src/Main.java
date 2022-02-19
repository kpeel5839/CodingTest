import java.util.*;
import java.io.*;

// 11438 : LCA 2
/*
--전제 조건
노드의 개수가 주어지고 ,
각 트리 상에서 연결된 두 정점이 주어진다.
가장 가까운 공통 조상을 알고싶은 개수 M이 주어지고 , 거기서부터 M을 얻고 , 두 정점의 공통조상을 출력하면 된다.
--틀 설계
트리의 경로는 정점 - 1 이다,
그리고 M개를 받아서 LCA 알고리즘으로 처리하면 된다.
일단 , 2차원 배열을 만들어서 , 각 정점들의 조상을 2 ^ i 번째 조상들을 정해야한다.
i는 parents[n][k] 에서 k가 i를 의미한다.
그 다음에 , LCA 알고리즘에서는 더 깊은 정점을 기준으로해서 , 계속 depth 를 더 얕은 정점과 맞춰준다.
그 다음에 , 트리의 공통조상 바로 아래까지 간다음에 , parents[a][0] 을 return 하면 된다.

-- 살짝 해맸던 점
이제 마지막으로 depth 를 똑같이 맞추고 , 공통조상을 찾을 때 , 2 ^ i 승씩 점프해야 하는데 , 그렇게 안해서 살짝해맸음

 */
public class Main {
    public static List<ArrayList<Integer>> graph = new ArrayList<>();
    public static int[] depth;
    public static int[][] parents;
    public static boolean[] visited;
    public static int n , m , height;
    public static int lca(int a , int b){
        // 일단 두개 중 깊이가 더 깊은 것을 , a로 할 것임
        if(depth[a] < depth[b]){
            int tmp = a;
            a = b;
            b = tmp;
        }

        // 두개의 높이를 맞춰줘야 함
        for(int i = height - 1; i != -1; i--){
            if(1 << i <= depth[a] - depth[b]){
                a = parents[a][i]; // 2 ^ i 가 depth[a] - depth[b] 보다 작을 때마다 , 이제 depth 를 줄이기가 가능한 것
                // 그래서 2 ^ i 번째 조상으로 (a의) 값을 수정해줌
                // 그럼 만약 처음에 depth 차이가 3 이라고하면 , i == 1 일 때 부터 높이의 수정이 일어나니까 , 3 -> 1 -> 0 이렇게 오게 되면서 , depth 가 같아지게 되는 것이다.
            }
        }

//        System.out.println(a);

        if(a == b) return a; // 만일 depth 를 동일하게 했는데 , b 와 같다면 a의 조상은 b인 것이므로 최소 공통 조상은 b이다.

        // 안됐다면 , 둘의 높이가 같아졌으니까 , 계속 같이 타고 올라감
        // 높이를 맞춰줄 때와 비슷한 느낌으로
        for(int i = height - 1; i != -1; i--){
            if(parents[a][i] != parents[b][i]){
                a = parents[a][i];
                b = parents[b][i];
            }
        } // 이렇게 하는 이유는 , 터무니 없이 뛰면 , 그 조상이 없으니 0으로 겹칠 것이고
        // 또 본인의 공통조상을 넘어서 뛰면 짜피 , 조상이 겹쳐서 점프를 안할 것이고,
        // 그러면 남은 것은 , 넘어서 뛰지 않는 경우만 존재하고 , 거기서 부터 차례대로 하면 , 공통 조상 바로 아래서 멈출 것임
        // 이것은 위에 depth 를 맞추는 과정과 굉장히 흡사한데 , parents[a][i] != parents[b][i] 를 추가하여서 , 공통 조상 바로 아래까지 올 수 있는 것이다.

        // 그러니까 , 트리의 특성을 이용해서 , 최소 공통조상을 넘어가면 어짜피 공통조상 겹치는 것과
        // 2 ^ i 조상이 존재하지 않을때에 0으로 초기화 한 값이 선택되는 것을 이용한 트릭이다.

//        System.out.println(a + " " + b);

        return parents[a][0];
    }
    public static void fillParents(){
        for(int i = 1; i < height; i++){
            for(int j = 1; j <= n; j++){
                parents[j][i] = parents[parents[j][i - 1]][i - 1];
            }
        }
    }
    public static void dfs(int vertex , int cnt){
        /*
        각 노드들의 depth 를 입력해야한다.
        방문 처리를 해주면서 , vertex 들의 각각의 depth 를 입력해주고 ,
        그 다음에 , 진행하면서 , parents[vertex][0] 들, 즉 바로 위의 조상 , 즉 본인과 연결되어 있는 조상들만
        처리를 해준다.
        그래야지 fillParents를 진행이 가능하다.
         */
        depth[vertex] = cnt;

        for(Integer number : graph.get(vertex)){
            if(!visited[number]){
                visited[number] = true;
                dfs(number , cnt + 1);
                parents[number][0] = vertex; // 루트노드에서 시작하니까 특성상 , 아래로 뻗어나가게 된다 , 그렇다는 것은 즉 , 이전 방문 노드가 다음 방문노드의 조상 노드이다.
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;


        n = Integer.parseInt(input.readLine());

        for(int i = 0; i <= n; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < n - 1; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());

            graph.get(vertex1).add(vertex2);
            graph.get(vertex2).add(vertex1);
        }

        depth = new int[n + 1];
        height = 0;
        int tmp = 1;
        while(tmp <= n){ // tmp == n 까지도 포함해주면서 , 16 , 32 와 같은 경계면에 걸려있는 정점도 신경써준다.
            tmp = tmp << 1;
//            System.out.println(tmp);
            height++;
        }

        parents = new int[n + 1][height];

        visited = new boolean[n + 1];
        visited[1] = true;
        dfs(1 , 1); // 루트노드로 시작하고 , 조상노드 없으니까 , 그냥 처리 안해도 됨
        fillParents();

        m = Integer.parseInt(input.readLine());
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(input.readLine());
            int lca = lca(Integer.parseInt(st.nextToken()) , Integer.parseInt(st.nextToken()));
            output.write(lca + "\n");
        }

        output.flush();
        output.close();
    }
}
