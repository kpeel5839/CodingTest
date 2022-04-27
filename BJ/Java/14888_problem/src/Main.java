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
일단 수의 배열을 만든다. 순서대로 들어온 것
그리고 calculate 즉 해당 배열과 , 숫자가 들어오면 계산해주는 함수를 작성한다.
그리고 연산 순서를 만들어주는 dfs() 함수를 만든다.
depth 는 수식의 n - 1이고 해당 oper 배열을 사용할 것이다.
oper 배열은 oper[0] ... oper[3]까지 존재하며 각각 덧셈 , 뺄셈 , 곱셉 , 나눗셈의 개수이다.
그리고 전역 변수로 max , min 을 선언하여 max = Integer.MIN_VALUE , min = Integer.MAX_VALUE 로 각각 한다.
oper은 전역변수이면 안된다. 수의 배열과 , sequence는 전역으로 선언하고 sequence 의 각각의 인덱스의 값은 0 , 1 , 2 , 3으로 이루어져 있으며
해당값은 oper의 인덱스가 의미하는 값과 같다.
 */
public class Main {
    public static int n , min = Integer.MAX_VALUE , max = Integer.MIN_VALUE;
    public static int[] numberList;
    public static int[] sequence;
    public static void calculate(){
        /*
        여기서 min , max를 추출하는 역할을 한다.
         */
        int value = numberList[0];
        for(int i = 0; i < sequence.length; i++){
            if(sequence[i] == 0){
                value += numberList[i + 1];
            }else if(sequence[i] == 1){
                value -= numberList[i + 1];
            }else if(sequence[i] == 2){
                value *= numberList[i + 1];
            }else{
                value = value / numberList[i + 1];
            }
        }

        if(min > value){
            min = value;
        }
        if(max < value){
            max = value;
        }
    }
    public static void dfs(int depth , int[] oper){
        /*
        여기는 sequence 를 만드는 역할을 한다.
        아니다 oper도 공유하자
        해당 i로 호출을 하면 해당 oper[i]-- 하면 된다.
        그리고 sequence 를 변경하면 된다.
        그래서 for문으로 호출하면서 해당 oper에 값이 0이 아닌지 확인하고
        0이 아니라면 dfs를 호출한다
        그리고 다시 돌아왔을 때 oper[i]++만 해주면 된다.
        sequence[depth] = 0 으로 만들어주지 않아도 되는 이유는
        짜피 해당 인덱스를 지나쳐서 이전으로 돌아갔다고 하더라도
        짜피 다시 들려서 알맞는 값으로 변경된다 , oper 배열만 정상적이라면
        oper배열도 그냥 다 공유해도 되는 이유가 , 짜피 내가 oper[i]++ , oper[i]-- 를 해주기에
        해당 상황에 맞춰서 oper의 값이 존재하기 때문이다.
         */
        if(depth == n - 1){
            calculate();
            return;
        }

        for(int i = 0; i < 4; i++){
            if(oper[i] != 0){
                oper[i]--;
                sequence[depth] = i;
                dfs(depth + 1 , oper);
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
        int[] oper = new int[4];
        sequence = new int[n - 1];

        for(int i = 0; i < n; i++){
            numberList[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(input.readLine());

        for(int i = 0; i < 4; i++){
            oper[i] = Integer.parseInt(st.nextToken());
        }

        dfs(0 , oper);
        System.out.println(max);
        System.out.println(min);
    }
}
