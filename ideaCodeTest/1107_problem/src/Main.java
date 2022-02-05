import java.util.*;
import java.io.*;

// 1107 : 리모컨
/*
-- 전제조건
리모컨의 버튼이 고장나서 , 0 ~ 9 번까지 숫자 중 몇개를 못누르게 되고 ,
그 다음에 + , - 버튼이 있어서 버튼을 누를 때 마다 , 채널을 옮길 수 있다.
가고 싶은 채널이 주어졌을 때 , 그 채널까지 이동하기 위해서 최소의 버튼을 누르는 횟수를 구해라.
-- 틀 설계
100인 경우만 , 고려하고 , 나머지는 번호로 이동을 한다.
그 다음에 Math.abs(목적 번호 - 현재 번호) 하고 , ans와 비교해서 min 값을 저장한다.
입력을 받고 , 안되는 번호를 받는다.
그 다음에 그 번호를 -1 처리하고 dfs 로 만들 때 -1로 되어있는 것들은 number에다가 조합을 넣지 않는다.
그러고서 dfs로 다 돌려보면서 min 출력하면 됨

-- 해맸던 점
처음에 0을 못누르는 상황에 대해서 생각을 하지 않아서
0이 못누르는 버튼으로 주어졌을 때 n자리 수만 가능한 결과가 나왔었음
그래서 if(able[0] == -1 && number.length() != 0) 일 때에도 그냥
check로 넘기는 작업을 진행하였다.

그리고 이 상황은 제출하기 전에 , 풀기 전에도 우려하긴 했는데 , n이 한자리라고 쳤을 때의 상황을 예로 들면
9인 경우에 1만 버튼으로 누를 수 있다고 가정하면
11 혹은 1 혹은 100 에서 시작하는 경우가 있다 , 근데 11이 가장 빠른 경우이다 , 4번으로
근데 이전에는 depth == len 과정에서 len = n.length() 였기 때문에
1 혹은 100의 경우밖에 구하지 못했다 , 그렇기 때문에 len = n + 1을 하여서
n의 자리 수보다 더 높은 자리수를 가져야만 최소로 버튼을 누르기가 가능할 때의 경우를 해결하였음
 */
public class Main {
    public static int min = Integer.MAX_VALUE , n , m , len;
    public static int[] able = new int[10];
    public static void check(String number){
        /*
        일단 받은 숫자를 Integer로 변경해서 저장하고 다시 string으로 변경하는 작업을 진행한다.
        그 다음에 이 숫자가 만약에 100이다? 그러면 그냥으로 Math.abs(n - 100) 하고
        아니면  Math.abs(n - number) + number.length() 하면 된다.
         */
        int num = Integer.parseInt(number);
        String stNum = Integer.toString(num);
        int result = stNum.length();
        result += Math.abs(n - num);
        min = Math.min(min ,result);
    }
    public static void dfs(int depth , String number){
        /*
        여기서는 depth == len 이 되면 check에다가 넘긴다.
        그리고서 그게 아닐 때에는 for(int i = 0; i <= 9; i++) 까지 돌면서
        number에다가 한문자씩 추가해준다 , 근데 그 때 if(able[i] != -1) 이여야 함
        그리고 number는 지금 레퍼런스로 넘기기 때문에 , 값을 잘 컨트롤 하기 위해서는
        dfs를 호출할 때 i 를 붙혀주고 , 돌아오면 때 주어야 한다.

        이러면 0버튼을 못누를 때 한자리 , 두자리 수 이런 것들을 표현하지 못함..
        무조건 len 자리 수만 표현이 가능하다.
        이것을 예외 상황으로 여겨서 , able[0] == -1 이라면 ?
         */
        if(depth == len){
//            System.out.println(number);
            check(number);
            return;
        }
        else if(able[0] == -1 && number.length() != 0) check(number);

        for(int i = 0; i <= 9; i++){
            if(able[i] != -1){
                number = number + i; //한문자 붙혀주고
                dfs(depth + 1 , number);
                number = number.substring(0 , number.length() - 1); // 한문자 떄주고
            }
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        n = Integer.parseInt(input.readLine());
        m = Integer.parseInt(input.readLine());
        len = Integer.toString(n).length() + 1;

        if(m != 0) st = new StringTokenizer(input.readLine());

        for(int i = 0; i < m; i++){
            able[Integer.parseInt(st.nextToken())] = -1; // 불가능한 버튼으로 처리
        }

        min = Math.abs(n - 100);
        dfs(0 , "");

        System.out.println(min);
    }
}