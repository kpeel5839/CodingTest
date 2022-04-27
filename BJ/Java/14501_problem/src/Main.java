import java.util.*;
import java.io.*;
// 14501 : 퇴사
/*
-- 전제조건
상담원으로 일하고 있는 백준이가 퇴사하려고 한다.
지금으로 부터 N + 1 일 전까지 열심히 상담을 하고 퇴사하려고 한다.
그래서 Ti , Pi 로 상담일 수와 상담을 하였을 때 받을 수 있는 금액이 주어진다.
백준이가 퇴사하기 전까지 많은 돈을 금액을 받을 수 있도록 상담 스케줄을 잡고 , 최대로 받을 수 있는 금액을 출력
-- 틀 설계
일단 cost 에다가 순서대로 입력을 받는다.(금액)
그리고 term 배열에다가도 걸리는 시간을 받는다.
dfs() 함수를 선언하고 , depth, value 를 선언한다.
ans(최대 금액)을 전역 변수로 하고 dfs를 돌면서 최대값을 저장한다.
 */
public class Main {
    public static int ans , n;
    public static int[] cost , term;
    public static void dfs(int depth , int value) {
        /*
        depth 는 현재의 인덱스를 의미한다 문제에서는 날짜를 의미하고
        해당 depth 의 term 이 1이다 무조건 진행한다.
        그렇지 않다. 그러면 진행하지 않는 것과 진행하는 것으로 분기한다.
        그리고 마지막으로 진행할때에는 항상 depth + term[depth] 가 꼭 n 보다 크지 않아야한다.
        n보다 크면 진행하지 않는 것으로 해야한다. 그러니까 depth + term[depth] <= n 이래야지 진행하는 것이다.
        이런식으로 계속 저장하면 답이 나올 듯
         */
        if(depth == n){
            ans = Math.max(ans , value);
            return;
        }

        for(int i = 0; i < 2; i++){
            if(term[depth] == 1){
                dfs(depth + term[depth] , value + cost[depth]);
                break;
            }
            if(i == 0 && depth + term[depth] <= n){ //선택 하는 경우 , 근데 depth + term[depth] <= n 이여야지만 실행한다.
                dfs(depth + term[depth] , value + cost[depth]);
            }
            if(i == 1){ //선택 하지 않는 경우
                dfs(depth + 1 , value);
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        cost = new int[n];
        term = new int[n];

        for(int i = 0; i < n; i++){
            st = new StringTokenizer(input.readLine());
            term[i] = Integer.parseInt(st.nextToken());
            cost[i] = Integer.parseInt(st.nextToken());
        }

        dfs(0 , 0);

        System.out.println(ans);
    }
}
