import java.util.*;
import java.io.*;

// 2467 : 용액
/*
-- 전제 조건
용액들의 리스트가 주어졌을 때 , 해당 용액 두개를 합쳐서 섞어서 특성값이 가장 작은 값을 만들어라.
즉 , 순열이 오름차순으로 주어지고 , 거기서 두개를 더해서 가장 작은 값을 구하면 됨
-- 틀 설계
당연하게도 N이 10만이다. 10_000_000_000 그냥 노가다식으로 하면 당연하게도 이러한 연산을 진행해야 한다.
100억번이다 , 시간안에 절대 해결 못한다 이렇게 하면

일단 오름차순으로 주어진다는 것을 이용할 수 있을 것 같다.
그리고 문제에서 양수와 음수를 구분 지었다 , 그렇다라는 것은 여기에 힌트가 있을 확률도 매우 크다.

이전에 했던 도서관 문제와 비슷한 것일까?
일단 일반적으로는 무조건 적으로 양수가 있는 경우는 음수 , 양수를 서로 더하는 것이 좋다.

여기서 하나의 전제조건이 나온다 , 양수 혹은 음수가 없다라면?
그냥 순열에서 주어진 양수 리스트는 첫번째 두번쨰 값
음수리스트는 제일 마지막 값과 , 마지막에서 두번째 값을 더하면 된다.

이제 둘다 주어지는 경우를 생각해보자.
예제만을 생각하면

-99 -2 -1
4 98

딱봐도 음수는 첫번째부터가 가장 크고
양수는 마지막부터가 절대값이 가장 크다.
그렇다라는 것은 특성값이 0에 가깝게 되려면 급이 맞아야 하는데 , 양쪽 끝에서 시작해야지
조금 급이 맞을 수 있다라는 것을 이 예제에서는 보여준다.

근데 이 예제에서
-100 -2 -1
103
이 예제에서 딱히 양수 , 음수 배열로 나누면 안될 것 같다라는 느낌을 팍 준다.

만일
-100 -2 -1
104 였다면
정답은 -2 -1 하나이다.

아니면 이렇게 하면 어떨까?
일단 , 가장 작은 값에 도달해야 하는 것이고
양쪽에다가 포인터를 놨을 떄 , 이렇나 결론을 얻을 수 있다.
왼쪽 포인터를 올리면 현재 특성값이 높아지고
오른쪽 포인터를 내리면 현재 특성값이 낮아진다.
즉 , 0에 가깝게 하려면
일단 양쪽 끝을 지정한 뒤
서로 더해보고 , ans에 Math.min 으로 지정하고
계속 음수이면 , 왼쪽 포인터를 올리고 (그래야지 0에 더 가까운 답을 만드니까)
아니면 오른쪽 포인터를 내리면 되는 것이다.

만일 0이 나오면 바로 끝내고
쨋든 뭐 res 를 배열로 관리하든 어떻게 하든 해서
진행하면 될 것 같다.

일단 확실한 것은 양쪽에다가 포인터를 두고 , 각각 포인터를 조정하면서
특성값을 0에 가깝도록 , 왼쪽 포인터를 올리거나 , 내리거나 하는 것이 가장 좋은 선택인 것 같다.
왜냐하면 정렬이 되어 있으니 , 포인터를 올리거나 , 내리면 값의 방향은 확실하기 때문이다.

-- 결론
그리디 하게 풀긴했는데 , 왜 생각보다 이렇게 오래걸리는지는 이해가 가질 않는다.
 */
public class Main {
    public static int N , min = Integer.MAX_VALUE;
    public static int[] arr;
//    public static String res = "";
    public static int[] res = new int[2];
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());
        arr = new int[N];

        int lp = 0; // left point
        int rp = N - 1; // right point

        st = new StringTokenizer(input.readLine());

        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        while(lp != rp){ // lp , rp 같아지는 순간 엔딩
            int cur = arr[lp] + arr[rp];

            if(cur == 0){
//                res = arr[lp] + " " + arr[rp]; // 문자열로 만들어서 뺴줄 것임
                res[0] = arr[lp];
                res[1] = arr[rp];
                break;
            }

            if(min > Math.abs(cur)){ // min 보다 cur 값이 0에 가까우면 저장한다.
                min = Math.abs(cur);
//                res = arr[lp] + " " + arr[rp];
                res[0] = arr[lp];
                res[1] = arr[rp];
            }

            // cur이 음수일 때 , 왼쪽 포인터를 올려서 값을 수정해주어야 한다.
            if(cur < 0) lp++;

            // cur 값이 양수일 때 , 오른쪽 포인터를 내려서 값을 수정해주어야 한다.
            else rp--;
        }

//        System.out.println(res);
        System.out.println(res[0] + " " + res[1]);
    }
}

