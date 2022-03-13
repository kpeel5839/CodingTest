import java.util.*;
import java.io.*;

// 3980 : 선발 명단

/*
-- 전제 조건
테스트 케이스가 주어지고 ,
각각의 선수들의 포지션당 능력치가 주어진다.
이렇게 주어졌을 때 , 선수들을 적절히 배치하여서 , 얻을 수 있는 능력치의 최대 값을 구하시오
-- 틀 설계
Test case 의 수를 받고
테스트 케이스 만큼 반복한다.

그 다음에 , dfs를 실시해야 하는데
일단 선택한 포지션은 절대 선택하지 못하게 진행하여야 하고
계속 선택하면서 진행하면 될 것 같다.

근데 이제 0이면 선택 안하는 방향으로 가면서 , 쓸데 없는 부분을 제거 할 수 있을 것 같다.
그래서 , 최종적으로 선택을 다하였을 떄 remain 즉 남은 포지션이 더 없을 때 , 
그때만 ans 를 갱신하면 된다.

-- 해맸던 점
visited 배열 당연하게 1차원 배열로 만들어서 해당 포지션에 들어갔냐 안들어갔냐만 체크했었어야 했는데
이상하게 2차원 배열로 선언해서 조금 해맸었고
Math.max(remain , sum) 을 비교해서 살짝해맸었음
그것 이외에는 굉장히 빨리 풀음
 */
public class Main {
    public static int T , N = 11, ans = 0;
    public static int[][] ability;
    public static boolean[] visited;
    public static void dfs(int sum , int idx , int remain){
        if(idx == N){
            if(remain == 0){
                ans = Math.max(ans , sum);
            }
            return;
        }
        else{
            /*
            여기서 이제 , 해당 자리의 선택할 수 있는 선수들을 선택하면 된다.
            선택할 수 있는 경우가 없으면 ? 그냥 끝나는 것 그러니까 결국 0이면 continue 하면 된다.
             */
            for(int i = 0; i < N; i++){
                if(ability[idx][i] == 0 || visited[i]) continue;
                visited[i] = true; // 선택하고
                dfs(ability[idx][i] + sum , idx + 1 , remain - 1);
                visited[i] = false;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        T = Integer.parseInt(input.readLine());
        int count = 0;
        
        while(count++ < T){
            ability = new int[N][N];
            visited = new boolean[N];
            ans = 0;
            
            for(int i = 0; i < N; i++){
                st = new StringTokenizer(input.readLine());
                for(int j = 0; j < N; j++){
                    ability[i][j] = Integer.parseInt(st.nextToken());    
                }
            }
            
            dfs(0 , 0 , N);
            output.write(ans + "\n");
        }
        
        output.flush();
        output.close();
    }
}