import java.util.*;
import java.io.*;

// 3108 : 로고
/*
-- 전제 조건
거북이로 연필을 내리고 , 올리고 하면서 선을 그을 수 있다.
거북이의 시작점은 초기에 (0 , 0) 이고 , 축에 평행한 직사각형들을 다 그려야 한다.
여기서 조건은 축에 평행한 직사각형들을 제외한 선들은 아무것도 그으면 안되고 ,
직사각형의 선이라면 여러번 그어도 상관없다.

FD x: 거북이를 x만큼 앞으로 전진
LT a: 거북이를 반시계 방향으로 a도 만큼 회전
RT a: 거북이를 시계 방향으로 a도 만큼 회전
PU: 연필을 올린다
PD: 연필을 내린다.

이러한 조건들이 존재하고 , 사각형들이 주어졌을 때 ,
PU 연산을 최소 몇번을 해야하는지 , 즉 PU 명령의 최속값을 반환하는 프로그램을 작성하라.
-- 틀 설계
사각형의 개수가 주어지면 , 직사각형의 좌표들이 주어지고,
직사각형은 왼쪽 아래와 오른쪽 위의 꼭짓점 좌표들이 주어진다.

내 생각에는 일단 직사각형이 서로 붙어있는 곳이 있다면?
닿아있는 접접이 있다면 , 연필을 올리지 않아도 된다.

즉 고려해야할 것은 두가지이다 , 거북이의 시작점의 위치에 직사각형의 한 점이 그려져야 하는가?
그리고 , 한번에 그릴 수 있는 직사각형이 몇 그룹이 있는가 , 즉 , 붙어있는 직사각형을 그룹화 시켜
해당 직사각형의 그룹들을 가지고 , 연필을 올리는 최소의 횟수를 구하면 될 것 같다.

직사각형이 서로 붙어있는지 어떻게 알 수 있을까?

그냥 주어진 직사각형들을 다 그린다음에 , map = 1000 * 1000 짜리로 만들어서
정확히는 1001 사이즈로 만들어야 한다. 그래야지 인덱스가 1000까지 존재하고 -500 = 0 , 0 == 500
500 = 1000 으로 갈 수 있다.

그냥 직사각형으로 for 문 반복해서 직사각형 그려준다음에
bfs로 쫙 돌면서 , 같은 집단인 것들의 개수를 촤르륵 구해주고
map[500][500] = 1 이면 - 1 아니라면 그대로 출력한다.

bfs 로는 안된다 , 인접한 것으로 가면 안되고 , 겹치는 것들만이다.
그러면 사각형을 그릴 때에 다른 숫자를 마주치면 parent 로 등록하는 것은 어떨까?

-- 해맸던 점
처음에는 dfs 로 해결하려고 했었는데 , 인접한 사각형에 대한 처리를 할 수가 없었다
그래서 * 2 로 처리할 까 했는데 너무 비효율 적인 것 같아서 , 그냥 find , union 으로 해결하려고 했다.
나의 가정 자체는 이러하다 , 그냥 사각형 그리고 있는데 이미 그려진 사각형 있으면 그거 부모 체크해서
같은 집합이면 , 그냥 냅두고 , 아니면 union 하는 것이 였다.
근데 뒤지게 안맞는 거다.
알고보니까 , 내가 사각형을 그리는 방법이
제일 위에 점 , 즉 (y2, x2) 를 그리지 못하는 단점이 있었는데

그거를 원래 그릴 떄에도 , 그냥 하드하게 한라인을 더 써서 처리했었다.
근데 그것을 생각을 안했었고 (거기서 겹쳐서 union 해야 될 수도 있는데)
그리고 또 map[500][500] 이 0이 아닌 경우로 체크했었어야 했는데 , 처음에 dfs 가지고 했던게
1로 사각형을 표시했으니까 , map[500][500] == 1 인 경우에서는 ans-- 를 한 것을
모르고 안지웠었다.
그래서 (y2 , x2) 처리 못한 거랑 , map[500][500] == 1 을 map[500][500] != 0 으로 안바꿔서
뒤지게 해맸다. (30 분동안 해맴)
 */
public class Main {
    public static int N , ans = 0;
    public static final int SIZE = 1001;
    public static int[][] map = new int[SIZE][SIZE];
    public static int[] parent;

    public static void instructDraw(int y1 , int x1 , int y2 , int x2 , int index){
        /*
        두 지점을 받으면 그냥 직사각형을 그리기만 하면 된다.
        이미 그려져 있던 거 상관할 바 없다.
        그냥 y1 , x1 좌표부터 y2 , x2 좌표까지 그냥 긋기만 하면 된다.

        4개의 변을 그려야 한다.

        일단

        (y1 , x1) -> (y2 , x1)
        (y1 , x1) -> (y1 , x2)
        (y2 , x1) -> (y2 , x2)
        (y1 , x2) -> (y2 , x2)

        이렇게 그려주면 된다.
        그냥 노가다로 그려주는 게 편할 것 같다.

        실질적으로 draw 해주는 method 만들고 그냥 4개 호출해주면 된다.
         */

        draw(y1 , x1 , y2 , x1 , index);
        draw(y1 , x1 , y1 , x2 , index);
        draw(y2 , x1 , y2 , x2 , index);
        draw(y1 , x2 , y2 , x2 , index);
    }

    public static void draw(int y1 , int x1 , int y2 , int x2 , int index){
        /*
        좌표를 받으면 시작 지점부터 목표지점까지의 쭉 그으면 된다.
        1 은 2보다 무조건 작다 , 그렇다라는 것은 무조건 증가하는 방향으로 주어진다는 얘기이다.
        그것을 이용해서 증가하면서 채워넣으면 될 것 같다.
         */

        while(!(y1 == y2 && x1 == x2)){ // 같으면 끝
            if(map[y1][x1] != index && map[y1][x1] != 0){ // 다른놈을 만난 것임 , 근데 0은 아니여야함
                int a = find(map[y1][x1]);
                int b = find(index); // 현재 부모를 설정해야함 부모가 다르다면 union 아니면 그냥 넘어감

                if(a != b) union(a , b); // 아니라면 넘어감
            }
            map[y1][x1] = index;

            if(y1 != y2) y1++;
            if(x1 != x2) x1++; // 게속 증가시켜준다. 무조건 시작점은 y1 , x1 이기 때문에 그것을 이용해서 그려준다.
        }

        if(map[y1][x1] != index && map[y1][x1] != 0){
            int a = find(map[y1][x1]);
            int b = find(index);

            if(a != b) union(a , b);
        }

        map[y1][x1] = index;
    }

    public static int find(int vertex){
        if(vertex == parent[vertex]){
            return vertex;
        } // 부모를 찾으면 vertex에 반환

        return parent[vertex] = find(parent[vertex]); // 본인의 부모로 올라가면서 본인의 부모를 최상위 노드를 등록
    }

    public static void union(int a , int b){
        /*
        두개의 집합을 합칠 것이다.
         */

        a = find(a); // a를 a의 부모로 갱신
        b = find(b); // b를 b의 부모로 갱신

        parent[b] = a; // b의 부모를 a로 하면서 union 진행
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        N = Integer.parseInt(input.readLine());
        parent = new int[N + 1];

        for(int i = 0; i <= N; i++){
            parent[i] = i;
        }

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(input.readLine());

            instructDraw(toInt(st.nextToken()) , toInt(st.nextToken()) , toInt(st.nextToken()) , toInt(st.nextToken()) , i);
        }

        // 여기서 집합의 개수를 정의해야 한다.
        // 어떻게 정의할 수 있을까?

        // 그냥 visited[N + 1] 개 짜리 만들어서
        // 1 번째부터 모든 노드들 순서대로 돌아가면서 find 통해서 부모 찾으면서
        // 찾은게 새로운 부모이면 ans + 1 해주고 visited 처리하고
        // 아니면 진행하면 될 듯

        boolean[] visited = new boolean[N + 1];
//        System.out.println(Arrays.toString(parent));
//        mapPrint();

        for(int i = 1; i <= N; i++){
            int a = find(i);

//            System.out.println(a);

            if(!visited[a]){ // 아직 방문하지 않았다면 , 아직 부모로 선택되지 않았다면
                ans++;
                visited[a] = true; // 방문처리
            }
        }

        if(map[500][500] != 0) ans--;

        System.out.println(ans);
    }

    public static int toInt(String number){
        return Integer.parseInt(number) + 500;
    }

    public static void mapPrint(){
        for(int i = 500; i < 511; i++){
            for(int j = 500; j < 511; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}

