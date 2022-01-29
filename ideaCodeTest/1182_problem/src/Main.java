import java.util.*;
import java.io.*;

// 1182 : 부분수열의 합
/*
--전제조건
N개의 정수로 이루어진 수열이 있을 때 크기가 양수인 부분 수열 중에서
그 수열의 원소를 다 더한 값이 S가 되는 경우의 수를 구하는 프로그램 작성
--틀설계
그냥 하나하나 돌면서 dfs로 해결하면 될 듯, 백트래킹이라고 할게 있나?
 */
public class Main {
    public static int count = 0 , n , s;
    public static int[] number;
    public static void dfs(int depth , int sum){
        /*
        depth == n 이 되었을 때 , depth 는 0 부터 시작해서 number[depth]들을 탐색한다.
        그렇기 때문에 depth == n 이 되었을 때는 끝을 알리며 , 거기서 sum == s 인 경우
        그 경우에만 count를 증가시켜주면 되는 것이다.

        그리고 depth 하나의 두가지 경우로 분기하게 되는데 첫번째는 선택하지 않고 그냥 지나가는 것이고
        두번째는 선택하고 sum에다가 더해서 다음 것으로 넘어가게 되는 것이다.
        즉 두가지 분기는 선택하냐 , 안하냐이다.

        그렇게 해서 받은 수열인 number와 depth , sum을 잘 고려해주면 너무 쉽게 풀 수 있는 문제이다.
         */
        if(depth == n){
            if(sum == s) count++;
            return;
        }

        for(int i = 0; i < 2; i++){
            if(i == 0){ // 선택하지 않는 경우
                //현재 number[depth]를 선택하지 않아서 sum에다가 더하지 않으면서 넘어감
                dfs(depth + 1 , sum);
            }else{ // 선택하는 경우
                //현재 number[depth]를 선택해서 sum에다가 더하면서 넘어감
                dfs(depth + 1 , sum + number[depth]);
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());

        number = new int[n];

        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < n; i++){
            number[i] = Integer.parseInt(st.nextToken());
        }

        dfs(0 , 0);
        /*
        s == 0 인 경우는 선택했음에도 합이 0인 경우와 , 아얘 선택하지 않은 경우가 있다.
        s == 0 일 때에는 무조건 선택하지 않은 경우의 수가 하나 포함 되어 있기 때문에
        답을 출력할 때 1을 빼주고 그렇지 않다면 예외 사항은 없기 때문에 count 그대로 출력해준다.
         */
        System.out.println(s == 0 ? count - 1 : count);
    }
}