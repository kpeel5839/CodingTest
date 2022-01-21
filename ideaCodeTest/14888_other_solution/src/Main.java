import java.util.*;
import java.io.*;

// 14888 : 연산자 끼워넣기
/*
-- 전제조건
N개의 수로 이루어진 수열 A1, A2 ... An 이 주어진다, 수와 수 사이에 끼워 넣을 수 있는 연산자들도 주어진다.
주어진 수의 순서를 바꾸지 않으면서 수와 수 사이에 연산자를 하나씩 넣어서 수식을 하나 만들 수 있다.
연산자 우선순위를 무시하고 앞에서부터 진행해야 하고 , 또 나눗셈은 정수 나눗셈으로 즉 몫만 취급한다.
쨋든 그래서 결국은 6개의 이루어진 숫자가 주어지고 해당 연산자의 개수가 주어지며 거기서 최대인 것과 최소 값인 것을 구하는
프로그램을 작성하시오.
-- 틀 설계
dfs 에서 해당 인덱스에 맞는 연산자를 score와 numberList[depth + 1]에다가 적용한다.
해당 인덱스에 맞는 연산자는 for 문으로 돌리면서 순서대로 넣어주는 것이고 , oper[i] 의 값을 게속 검사하면 찾아줄 수 있다.
 */
public class Main {
    public static int n , min = Integer.MAX_VALUE , max = Integer.MIN_VALUE;
    public static int[] numberList , oper = new int[4];
    public static void dfs(int depth , int score ){
        /*
        depth == n - 1이 되면 결과가 나온 것이니까 min , max 값을 새로 구해주고
        재귀 호출 부문에서는 oper 값을 확인하고 oper[i] == 0 이 아니라면
        재귀 호출을 해주는데 , 이 때 i 값에 맞게 oper[i]-- 를 해주고 그 다음에 dfs 를 depth +  1 호출하면서 score 값도 알맞게 변경해준다.
        그러면서 이제 재귀호출이 끝나고 돌아왔을 때 oper[i]++ 를 해주면서 원래 상태로 복구 시킨다.
         */
        if(depth == n - 1){
            min = Math.min(min , score);
            max = Math.max(max , score);
            return;
        }

        for(int i = 0; i < 4; i++){
            if(oper[i] != 0){
                oper[i]--;
                if(i == 0){
                    dfs(depth + 1 , score + numberList[depth + 1]);
                }else if(i == 1){
                    dfs(depth + 1 , score - numberList[depth + 1]);
                }else if(i == 2){
                    dfs(depth + 1 , score * numberList[depth + 1]);
                }else{
                    dfs(depth + 1 , score / numberList[depth + 1]);
                }
                oper[i]++;
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(input.readLine());
        st = new StringTokenizer(input.readLine());

        numberList = new int[n];

        for(int i = 0; i < n; i++){
            numberList[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(input.readLine());

        for(int i = 0; i < 4; i++){
            oper[i] = Integer.parseInt(st.nextToken());
        }

        dfs(0 , numberList[0]);
        System.out.println(max);
        System.out.println(min);
    }
}