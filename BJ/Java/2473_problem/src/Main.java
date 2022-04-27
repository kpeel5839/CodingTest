import java.util.*;
import java.io.*;

// 2473 : 세 용액
/*
-- 전제 조건
이전 용액 문제와 같이 각 , 특성을 담은 정수들이 주어지고
거기서 3개의 용액을 골랐을 때 , 특성값이 0 에 제일 가까운 것이 무엇인지 출력하고,
답을 출력할 때에는 오름차순으로 출력한다.
N 의 값은 5000 이하의 정수가 주어지며 각각 주어지는 값은 절댓값으로 10억 이하이다.
이전 문제와 다르게 오름차순으로 주어지지 않는다 , 그리고 3개의 용액을 골라야한다는 게 다른 특징이다.
-- 틀 설계
일단 내가 한 생각은 이러하다,
일단 두개의 용액은 지정하고 , 하나의 용액을 이제 이분탐색으로 찾는 방법이다.
그럴려면 일단 , 2개의 용액은 모든 것을 돌아야 하고 , 하나의 용액을 찾을 때에는 이미 찾은 용액은 넘겨두고
선택을 해야한다.
그리고 답을 찾았다고 하더라도 , 답을 정렬을 해주어야 한다,
내가 생각한 방법은 이러하다 , 5000 * 5000 == 25000000 이다 , 근데 이것을 일단 중복없이 i , j 를 지정하게 되면
횟수는 2배가 줄게 된다 , 그리고 이분탐색을 진행하는데 , 무조건 최대치까지 진행하니까 , log2(5000) 이다.
항상 0을 찾는 것이 아닌이상 13회 정도가 최대 횟수이다 ,
이렇게 해서 어떻게 진행하냐면 for(int i = 0; i < N - 1; i++) for(int j = i + 1; j < N; j++) 이런식으로 진행하면서
이 안에서 i , j를 제외한 이분탐색을 진행하는 방법이다 , 내가 생각한 방법은 일단 이러하고 , 정렬과 , 이분탐색이 필요한 메커니즘이다 , 근데 꽤나 시간이 오래걸릴 것 같다.

그리고 사람들이 일반적으로 하는 방법은 그냥 투포인터이다.
가정은 이러하다 , 일단 3개의 용액을 골라야 하기 때문에 투 포인터로 진행할 빼고 1개의 배열을 선택한 뒤 진행하게 된다.
그러면 대충 이런식으로 생각할 수가 있는데 ,
일단 무조건 끝에서 끝까지 투포인터로 진행하는데(lp , rp) 근데 , 이제 고정할 인덱스를 하나를 주는 것이다.
그러면서 똑같이 고정 인덱스만 제외하고 , lp++ , rp-- 를 계속 진행하는 것이다,
오름 차순의 성질은 같기 때문에 (정렬을 먼저 진행함) 세개를 더한 sum > 0 이면 rp-- 를 해주고
아니면 lp++ 를 해준다 , 이런식으로 찾을 수 있는데 이 경우에는 중복되는 경우가 많다.
예를 들어서 10 12 13 14 15 이런식으로 주어졌을 때 , index 를 2번째 것을 주고
그 다음에 투포인터로 찾아서 뭐 10 12 13 이 나왔다고 가정하자
그 다음에 , index 를 세번째로 주고 또 진행한다고 하면 또 10 12 13 을 찾을 수가 있다.

그렇게 되었을 때 , 몇번만 진행하더라도 , 동일한 답이 자주 나온다는 것을 알 수 있기 떄문에
index 를 제일 시작으로 주고 1번째 ~ index 번째 까지를 제외하고 그 오른쪽에 있는 것들로만 투포인터를 진행하게 된다면
필요 없는 중복을 줄일 수가 있다 , 다시 10 12 13 14 15 를 진행하면
index = 1 로 주어지면
10 12 13 을 찾게 되고
index = 2
12 13 14 를 찾게 된다
.... 쨋든 이렇게 진행되게 된다 , 그러니까 index 를 기준으로 탐색을 하기 때문에 , index 이전애들은 다시 사용하지 않고,
그렇기 때문에 중복된 답이 나올 수가 없다 , 적어도 값은 같더라도 , index 까지 완벽히 같은 중복된 답은 나오지 않는다 , 그리고 답을 정렬하지 않아도 된다(물론 3개밖에 없어서 별로 안걸리지만).
그래서 이러한 방법들로 진행할 수 있고 , Main , Main2 로 진행해볼 것이다.

-- 해맸던 점
모르고 정렬안함
 */
public class Main {
    public static long[] res = new long[3] , arr;
    public static int N;
    public static long min = Long.MAX_VALUE;
    public static void find(int index){
        /*
        index 가 주어지면 두 포인터를 설정하고 , 그 중에서 답을 찾아서 res 배열에다가 저장하면 된다.
         */

        int lp = index + 1;
        int rp = arr.length - 1; // 설정할 수 있는 포인터를 설정

        while(lp < rp){ // 같아지는 순간도 종료
//            System.out.println("lp : " + lp + " rp : " + rp);
            long sum = arr[lp] + arr[index] + arr[rp];

            if(Math.abs(sum) < Math.abs(min)){ // 그냥 귀찮으니까 , min 도 그냥 집어넣자 sum 값으로
                min = sum;
                res[0] = arr[index];
                res[1] = arr[lp];
                res[2] = arr[rp];
            }

            if(sum == 0) break; // 같으면 끝내주고

            if(sum < 0) lp++; // sum 이 음수이면 값을 올려주고

            else rp--; // 아니면 내려준다.
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(input.readLine());

        st = new StringTokenizer(input.readLine());
        arr = new long[N];


        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr); // sort 해주어서 정렬해준다.

        for(int i = 0; i < N - 2; i++){
            find(i);
        }

        System.out.println(res[0] + " " +  res[1] + " " + res[2]);
    }
}
