import java.util.*;
import java.io.*;

// 16637 : 괄호 추가하기
/*
-- 전제조건
길이가 N인 수식이 있다 , 이 수식은 0보다 크거나 같고 9보다 작거나 같은 정수와 연산자이다.(+ , - , *) 로 이루어져 있다.
특별하게 여기서 연산자 우선순위는 모두 같다.
근데 꼭 왼쪽서부터 계산을 해야한다 , 근데 여기서 수식에 괄호를 추가하면 , 괄호 안에 들어있는 식은 먼저 계산해야 한다.
그래서 괄호를 알아서 적당히 쳐서 최대 값을 만든다.
중첩된 괄호는 안되니 굉장히 난이도가 쉬워진다고 할 수 있음
-- 틀 설계
수식이 들어오면 length 에 따라서 number , operation 배열을 만든다.
number 의 크기는 number / string.length() + 1  로 하고 operation / string.length 로 사이즈를 정한다.
그런 다음에 짝수는 Integer.parseInt 해서 index 에다가 집어넣고 , operation 즉 홀수는 operation 배열에다가 집어넣는다.
dfs 로 이제 operation 만큼 크기의 배열을 만들고 해당 배열에다가 1은 먼저계산 , 0은 나중에 계산으로 수식을 집어넣을 것이고
depth로 해당 operationList의 크기만큼 가면 끝내는 것으로 할 것이다.
-- 해맸던 점
ans 값을 0으로 설정해서 음수값에서 최고값이 나왔을 때를 고려하지 못했었음
calculate에서 numberList = inArr 해놓고서 inArr를 바꾸면서 게산해야 했었는데
그냥 numberList 보고 해버려서 다른 값 나왔었음
그리고 자꾸 범위 잘못 설정해서 조금 해맸음
그래도 시간은 되게 조금 걸려서 해결하였음 , 설계나 이런 부분은 만족
 */
public class Main {
    public static int n , ans = Integer.MIN_VALUE;
    public static String calStr;
    public static int[] numberList , arr;
    public static char[] operationList;
    public static void calculate(){
        /*
        arr 1이 처리 되어있는 것들을 먼저 계산하고
        그 다음에 0으로 처리 되어 있는 것을 순서대로 해서
        결과값을 도출해내서 Math.max(ans , result); 로 최대값을 도출해내면 된다.
        이제 계산하는 과정이 중요함 그러면
        [3,8,7,9,2]
        [+,*,-,*]이 있고
        [0,1,0,1]을 계산한다고 해보자
        내부에 해당 배열을 계산할 수 있도록 inArr를 선언해서
        [3,8,7,9,2]를 만들어놓는다.
        [3,56,56,9,2] -> [3,56,56,18,18] -> [59,59,59,18,18] -> [41,41,41,41,41] 이런식으로 만들어야 함 이런순서대로
        일단 1을 먼저 계산할 때에는 해당 인덱스 , 인덱스 + 1까지 바꿔준다 계산한 값으로 , 그렇게 1로 다 변경해주고
        그 다음에 0을 계산하는데 0을 계산할 때에는 해당 다음 인덱스가 1이면 다음 다음 값까지 지금 현재 계산한 값으로 바꿔주고 아니면 그냥 거기까지만
        그러면서 맨 마지막 값만 뽑아내면 그게 이 arr대로 게산하였을 때 답이다.
        원리는 괄호가 중첩되지 못하는 것과 , 왼쪽에서부터 계산하는 원리에서 구하였다.
         */
        int[] inArr = numberList.clone();

        for(int i = 0; i < (n / 2); i++){
            if(arr[i] == 1){
                int value = 0;
                if(operationList[i] == '*'){
                    value = inArr[i] * inArr[i + 1];
                }
                if(operationList[i] == '+'){
                    value = inArr[i] + inArr[i + 1];
                }
                if(operationList[i] == '-'){
                    value = inArr[i] - inArr[i + 1];
                }
                inArr[i] = value;
                inArr[i + 1] = value;
            }
        }

        for(int i = 0; i < (n / 2); i++){
            if(arr[i] == 0){
                int value = 0;
                if(operationList[i] == '*'){
                    value = inArr[i] * inArr[i + 1];
                }
                if(operationList[i] == '+'){
                    value = inArr[i] + inArr[i + 1];
                }
                if(operationList[i] == '-'){
                    value = inArr[i] - inArr[i + 1];
                }
                if(i != (n / 2) - 1 && arr[i + 1] == 1){
                    inArr[i] = value;
                    inArr[i + 1] = value;
                    inArr[i + 2] = value;
                }else{
                    inArr[i] = value;
                    inArr[i + 1] = value;
                }
            }
        }
//        System.out.println(Arrays.toString(numberList));
//        System.out.println(Arrays.toString(operationList));
//        System.out.println(Arrays.toString(arr));
//        System.out.println(Arrays.toString(inArr));
        ans = Math.max(inArr[(n / 2)] , ans);
    }
    public static void dfs(int depth){
        /*
        arr 에다가 1을 집어넣으면 짜피 다음칸은 1이 못들어가니까
        depth + 2 를 할 것인데 이러면 문제가 arr를 전역적으로 쓰고 있기 때문에 각자 가지고 있는 게 아니다.
        그렇기 때문에 이전에 집어넣은 것을 들리지 않을 수가 있음 그렇기 때문에 재귀적으로 호출하고 return 하고 자신으로 돌아올 때
        본인이 수정한 값을 0 으로 돌려놔야한다(원래 상태) 그래야지 규칙에 맞게 가능함
         */
        if(depth >= n / 2){
            calculate();
//            System.out.println(Arrays.toString(arr));
            return;
        }

        for(int i = 0; i < 2; i++){
            arr[depth] = i;
            if(i == 1){
                dfs(depth + 2);
            }else{
                dfs(depth + 1);
            }
            arr[depth] = 0;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(input.readLine());
        calStr = input.readLine();

        numberList = new int[n / 2 + 1];
        operationList = new char[n / 2];
        arr = new int[n / 2];

        int numberListIndex = 0;
        int operationListIndex = 0;
        for(int i = 0; i < n; i++){
            if(i % 2 == 0){
                numberList[numberListIndex++] = calStr.charAt(i) - '0';
            }else{
                operationList[operationListIndex++] = calStr.charAt(i);
            }
        }

//        System.out.println(Arrays.toString(numberList));
//        System.out.println(Arrays.toString(operationList));

        dfs(1);

        System.out.println(ans);
    }
}