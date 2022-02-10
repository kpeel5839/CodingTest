import java.util.*;
import java.io.*;

// 11404 : 플로이드

/*
-- 전제조건
그냥 100개의 도시와 각각의 도로들이 주어지는데,
모든 도시들에서 다른 도시로 가는 최소비용들을 출력하라
-- 틀 설계
플로이드 와샬 알고리즘을 사용해도 되지만,
솔직히 다익스트라를 조금 더 확장해서 사용해도 된다.
두 가지 방법을 진행해볼것이다.

플로이드 워셜 알고리즘은 , 음의 사이클만 존재하지 않는다면 , 음의 가중치를 갖는 그래프여도 최단 거리를 구할 수 있다.

플로이드 워샬 알고리즘은 이러한 메커니즘을 갖는다.
임의의 정점 k를 들러서 갔을 때 그냥 바로 가는 것보다 더 빠르냐 이것을 보는 것임
완전 동적 프로그래밍 개념이다.

그래서 dist 배열들의 각각의 [a][b] a -> b 로 가는 비용 같은 것들을 받아놓는다.
근데 이 경우에서 중요한 점은 이 값을 최소값을 받는 것이 중요하다 , a -> b 가 한 경로에 여러개의 값이 들어올 수 있다고 가정하였을 때에는

그래서 각각의 배열들을 i -> j 라고 했을 때 이 비용을 받았다면 다 그렇게 초기화 되어있을 것이다.
그러면 이제 k 를 0부터 시작하면 된다.
0번 정점을 경유하였을 때 최소값 부터 시작해서
정점의 번호까지 간다.

그래서 계속 초기화 해준다 , 그리고 순서는 상관이 없는 것이 만약에
1 -> 0 -> 2 로 가는 것이 빠르다.
그럼 k는 0이 된다.
근데 만일 k를 2를 먼저 실행한다면?
1 -> 2 -> 0 이 최소 값이 될 것이다.

이렇기 때문에 k 를 0부터 , 즉 순서대로 실행해도 상관이 없다.

그래서 결론 적으로는 , dist를 초기화하고 , 인접행렬로다가 dist를 표기한다.
그 다음에 경유하는 곳을 0 ~ v 까지 진행하면서 , 값들을 계속 갱신해주면 된다.

-- 더 이해해보기
예를 들어서 정말 작은 3개짜리 정점의 그래프가 있다고 가정해보자

1 - 2
 \ /
  3

이런식으로 되어 있고 , 서로 양 방향이라고 가정하고
1 -> 2 cost : 5
2 -> 3 cost : 3
3 -> 1 cost : 1
이라고 해보자.
그러면 dist 배열은 처음에 이렇게 될 것이다.

0 5 1
5 0 3
1 3 0

이런식으로 초기화가 될 것이다 본인에서 본인으로 가는 것은 가장 최소가 되어야 하니까 0으로 초기화해준다 애초에 비용이 존재하지 않으니까,
근데 루프로 인해서 본인에게 가는 경우 , 음수가 되는 경우에는 구할 수도 있을 것 같다 , 근데 그렇다는 것은 음수 사이클이 생기는 것이니까 안될 것 같기도 하고

1번을 경유로 한다고 생각하고 진행해보자.
그러면

0 5 1
5 0 3 1번을 경유하는 경우에는 2 -> 3 이 , 2 -> 1 -> 3을 선택할 수 있는데 6 , 3 이기에 갱신되지 않는다.
1 3 0 1번을 경유해서 3 -> 2 번으로 가는 경우 3 -> 1 -> 2 가 될 수 있는데 이러면 3 , 6 이기 때문에 갱신 x

2번을 경유지로 선택한다고 생각해보자.
0 5 1 2번을 경유지로 선택하는 경우에서 1 -> 3이 1 -> 2 -> 3 이 될 수 있는데 이러면 5 + 3 이라서 8 , 1이기 때문에 갱신 x
5 0 3 본인을 경유지로 선택하니까 그대로
1 3 0 2번을 경유지로 선택하니까 3 -> 2 -> 2 를 할 수 있는데 이러면 3 , 3이니까 그대로이다.

3번을 경유지로 선택한다고 생각해보자.
0 4 1 1 -> 3 -> 2로 할 수 있는데 이러면 dist[1][3] + dist[3][2] 이다 그러면 1 + 4 ,니까 5보다 작다 갱신된다. 1 -> 3은 변경 될 수가 없다 , 경유지이니까
4 0 3 2 -> 3 -> 1 할 수 이쓴ㄴ데 그렇게 되면 3 + 1 이니 변경된다 (4, 5) 3은 변경되지 않는다.
1 3 0 얘는 본인이 경유지 이니까 변경 x


이렇게 보면 알 수 있듯이 , 어떠한 지점을 경유지로 차근차근 정해가면서 , 경유지로 선택해서 , 값을 갱신하게 되면 그 간선을 선택하게 된 것이다.
그래서 v 번 반복하게 되면 최대 본인으로 부터 상대 지점까지 갈 수 있는 가능한 간선들을 다 선택하게 되는 것이다.

그리고 경유지를 하나하나씩 정해주면서 , 한 정점이 모든 정점을 경유하였을 때의 경우들을 다 구해서 값들을 최소값으로 갱신이 가능하다.
 */
public class Main2 {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int v = Integer.parseInt(input.readLine());
        int e = Integer.parseInt(input.readLine());

        long[][] dist = new long[v + 1][v + 1];

        for(int i = 0; i <= v; i++){
            for(int j = 0; j <= v; j++){
                if(i == j) dist[i][j] = 0;
                else dist[i][j] = Integer.MAX_VALUE;
            }
        }
        for(int i = 0; i < e; i++){
            st = new StringTokenizer(input.readLine());
            int vertex1 = Integer.parseInt(st.nextToken());
            int vertex2 = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            dist[vertex1][vertex2] = Math.min(dist[vertex1][vertex2] , cost);
        }

        for(int k = 1; k <= v; k++){
            for(int i = 1; i <= v; i++){
                for(int j = 1; j <= v; j++){
                    dist[i][j] = Math.min(dist[i][j] , dist[i][k] + dist[k][j]);
                }
            }
        }

        for(int i = 1; i <= v; i++){
            for(int j = 1; j <= v; j++){
                if(dist[i][j] == Integer.MAX_VALUE) output.write(0 + " ");
                else  output.write(dist[i][j] + " ");
            }
            output.write("\n");
        }

        output.flush();
        output.close();
    }
}
