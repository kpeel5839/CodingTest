import java.util.*;
import java.io.*;

// 2143 : 두 배열의 합

/*
-- 전제 조건
어떠한 배열 2개가 주어졌을 때 ,
무조건 적으로 2개의 배열의 부분합들을 더하여서
주어진 T를 만드는 경우의 수를 모두 구하여라.
-- 틀 설계
일단 부배열의 합은 누적합을 이용해서 구할 수 있다.
그리고 해당 숫자의 값도 구할 수 있다 누적합으로 저장한다고 하덜다ㅗ
그 값은

A[i] - A[i - 1] 로 하면 number[i] 를 구할 수 있다.

누적합을 이용한다고 하더라도 , A[1 ~ 3] 이런식으로 구하기란
A[3] - A[1 - 1] 즉 A[i ~ j] - A[j - 1] 이 된다라고 식이 만들어진다.

근데 , 아무리 봐도 해당 경우의 수가 엄청나게 많을 수도 있을 뿐더러
어떻게 찾느냐도 문제이다.

1000 * 1000 ..
100000 이다. 여기다가 살짝의 이분탐색?

뭔가 답이 나온 것 같다.
가능한 경우가 단 한가지도 없을 떄에는 그냥 탐색한 경우의 수를 출력하면 된다.

일단 A를 기준으로 시작한다.
A[i ~ i] 부터 해서 A[1 ~ i] 까지 진행한다.
이럴려면 당연히 A[i] - A[i - 1] 로 진행하면 된다.
일단 이 값을 던지면서 T를 넘어가면 바로 continue
아니면 이제 상대방의 B[i] 값을 하나하나 보는 것이다.

근데 이제 적절한 A[i ~ j]와
B[i ~ j] 를 찾는 과정에서 이분탐색이 들어가면 될 것 같다.

근데 이 경우에는 오름차순으로 정렬이 되어 있어야 할 것 같은데
정렬을 하게 되면 순서도 망친다.
근데 정렬을 안하자니 음수값들이 존재하기 때문에 누적합이 정렬이 된다라는 보장이 없다.

그래서 mid 값을 찾아서 적절한 값을 찾아나갈 때 크면 왼쪽 , 작으면 오른쪽
이러한 기준이 없다.

심지어 음수가 들어가게 되면
합이 A[i ~ j] + B[i ~ j] 를 찾을 때
해당 A[i ~ j] 에 맞는 B[i ~ j] 가 하나만 있으리란 보장자체가 없다
왜냐하면 -1 1 -1 1 2 이렇게 되면
1 ~ 5 == 2 , 3 ~ 5 == 2 이기 떄문이다.

내가 왜 생각을 못했지
이분탐색, 누적합에 너무 정신팔려가지고 생각을 못했다.

그냥 아얘 부분합을 전부 다 구해놓는 것이다.
그 다음에 그것을 다 정렬한다
A 는 오름차순
B 는 내림차순
그래서 A는 첫번째 부터 탐색
B는 마지막 부터 탐색을 한다.

그러면서 A + B 가 맞다면 B와 A를 같은 숫자 일때까지
진행을 해준다.
그 다음에 개수를 곱해서 answer 에다가 추가해준다.
엄청나게 많아 질 수 있기 때문에 꼭 long으로 선언해야한다.

-- 해맸던 점
진짜 미친넘 마냥 arr 한번 더 쓰면서
다시 선언안하고 그냥함
그래서 처음에 들어온 M이 두번째 M보다 작을 때 에러났었음

나머지는 처음에 ans 설정안했었음 , 그래서 아무것도 못찾을 경우에는 아얘 배당된 값이 없었다.
그 이외에는 해답을 알아서 빠르게 풀었다.
 */
public class Main {
    public static int T ,M;
    public static long ans = 0;
    public static int[] arr;
    public static List<Long> a = new ArrayList<>(),  b = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        T = Integer.parseInt(input.readLine());

        M = Integer.parseInt(input.readLine());
        arr = new int[M];

        st = new StringTokenizer(input.readLine());
        for(int i = 0; i < M; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i = 0; i < M; i++){
            long value = arr[i];
            a.add(value);
            for(int j = i + 1; j < M; j++){
                value += arr[j];
                a.add(value);
            }
        }

        M = Integer.parseInt(input.readLine());
        arr = new int[M];

        st = new StringTokenizer(input.readLine());

        for(int i = 0; i < M; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i = 0; i < M; i++){
            long value = arr[i];
            b.add(value);
            for(int j = i + 1; j < M; j++){
                value += arr[j];
                b.add(value);
            }
        }

        Collections.sort(a); // 오름차순
        Collections.sort(b , (o1 , o2) -> (int)(o2 - o1));

        int aP = 0;
        int bP = 0;

        while(true){
            // 만일 더한 값이 더 크다면 값을 줄여야 함 더한 값이 T값보다 크다 그러면 b포인터를 --
            // 아니다 그러면 a++
            // T == 이다. 그러면 a++ 다른 숫자 나올 때까지
            // b-- 다른 숫자 나올 때까지
            // 나온 수 ans += res;

            if(aP == a.size() || bP == b.size()) break;
            // aP가 끝까지 도달했는데 커졌다는 것은 T값보다 작은 것 , 즉 더 없다.
            // bP가 -1이 되었다라는 것은 T값보다 아직도 크다라는 것 , 즉 더 줄일 수 없으니 답은 없다.

            long aVal = a.get(aP);
            long bVal = b.get(bP);

            if(aVal + bVal < T){
                aP++;
            }

            else if(aVal + bVal > T){
                bP++;
            }

            else{
                long aCount = 0;
                long bCount = 0;

                while(aP != a.size() && a.get(aP) == aVal){ // a 에서 뽑아낸게 aVal 과 다르면 끝
                    aP++;
                    aCount++;
                }

                while(bP != b.size() && b.get(bP) == bVal){ // a 의 반대
                    bP++;
                    bCount++;
                }

                ans += aCount * bCount;
            }
        }
        System.out.println(ans);
    }
}
