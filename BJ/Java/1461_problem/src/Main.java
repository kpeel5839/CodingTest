import java.util.*;
import java.io.*;

// 1461 : 도서관

/*
-- 전제 조건
사람들이 도서관에서 책을 마구 놓고 갔다.
그래서 , 이 책들을 모두 , 다시 원래 자리로 돌려놔야 한다.
그래서 한번에 들 수 있는 책과 , 책의 개수 , 좌표들이 주어졌을 때 ,
가장 최소거리로 책을 갖다놓을 수 있는 경우를 구해서 , 걸음 수를 계산하여라.
-- 틀 설계
7 2
-37 2 -6 -39 -29 11 -28
이 주어졌다.

보다 보기 쉽게 정렬해보자.

7 2
-39 -37 -29 -28 -6 2 11 이 있다고 가정하자.

그러면 2권씩 들어서 0번의 위치에서 움직인다고 했을 때 , 양수에 있는 애들은 한번 처리가 가능하니
22,
그 다음에 아래부터 차근차근 정리하면 28 + 29 + 39
그러면 22 + 12 + 58 + 39 = 34 + 97 = 131

느낌은 확실히 알겠다.
이것도 약간 priorityQueue 를 써야할 것 같은데
결국 각 좌표들은 거리를 의미한다.

왜냐하면 시작점이 0이기 떄문에 , 거리를 의미하고 ,
맨 마지막에는 당연하게도 가장 먼 값을 처리해야한다.

그리고 또 하나 , 어떤 부분에 책을 갖다가 놓을 때 , 갖다 끝에다가 갖다 놓는데 딱 떨어지지 않는다.
그러면 그 때 상황을 보고 끝까지 갖다 놓냐 안 갖다 놓냐를 결정할 것 같은데
한번 그러면 -39 를 없애고 한번 봐보자.
ㅎ

-37 -29 -28 -6 2 11
2 와 11은 같이 안없애면 손해이다 , 쓸데없이 두번을 왔다가야 하니까
그래서 22 ,
그리고 -28 까지 갔다가 , -37 까지 가냐
혹은 -6 까지만 갔다가 돌아오고 -29 -37을 가냐
계산해보나마나 전자가 더 낮을 것이다.

한번 그래도 해보자.
22 + 12 + 58 + 37 = 44 + 95 = 139

22 + 54 + 37 = 76 + 37 = 113

무려 26이나 줄어들었다. 여기서 숨겨진 규칙이 어떤 것이 있을 까 확실한 것 , 직관적으로 보이는 것은
맨 마지막까지 도달하는 경우를 보고서 했다라는 것이다 , 즉 마지막에 깔끔하게 도달할 수 있냐를 본 것 같다.

다음 예제를 보면서 한번 더 생각해보자.

8 3
-45 -26 -18 -9 -4 22 40 50

50 까지 가는 것이(한번에) 개 이득이다. 근데 이게 가장 높은 수였다.
그래서 100

100 + 18 + 45 = 163

이것은 50을 먼저가서 돌아오는 비용에서 까지 써버렸기 때문에 이러한 결과가 나온 것 같다.
다시 -45 를 먼저 가서 돌아오는 방향으로 검토해보자.

18 + 90 + 50 = 158

이 예제를 통해서 하나의 조건은 확실하게 결정이 되었음 , 가장 높은 숫자를 가장 마지막에 가야한다는 것

다음 예제를 한번 해보자.
6 2
-1 3 4 5 6 11

당연하게 1을 먼저 처리하고 ,
2
여기서 만약 더 큰 숫자를 먼저 간다?
그러면 4 6 11 이 순으로 가게 된다.
그러면 결과는

2 + 8 + 12 + 11 == 33

만일 여기서 또 딱 마지막에 나누어 떨어지게만 가면?

2 + 6 + 10 + 11 == 29

내가 정렬을 한 상태이기 때문에,
두가지의 조건은 확립이 되었다.

첫 째 , 가장 처음의 값과 , 가장 마지막의 값을 확인해서 , 먼저 갈 방향을 정한다. (음수가 없다면? 그냥 양수 방향으로 ㄲ)

둘 째 , 나누어 떨어지게 가는 것이 오름차순으로 정렬을 했기 때문에 , 무조건 유리하다.
근데 , 여기서 어떻게 나누어 떨어지게 가는지도 중요할 듯 하다.

내 생각은 이러하다.

횟수는 적게(맨 마지막 이동에서는 무조건 다 움직여야함 , 어떠한 경우더라도 , 오름차순으로 정렬하였기에 , 그게 항상 작거나 같음) , 그리고 항상 가는 거리는 더 짧은 거리로
그리고 음수와 양수는 따로 나눠서
음수가 없는 경우 , 혹은 양수가 없는 경우
그리고 맨 마지막 움직임 때에는 그 수를 한번만 더함
 */
public class Main {
    public static int N , M;
    public static int ans = 0;
    public static void calc(List<Integer> list , boolean end){
        /*
        end == true 이면
        ans 에다가 마지막 것은 * 2 를 해서 더하는 것이 아닌 그냥 넣는다.
        그리고 처음에는 M = 3 이라고 하면 , 그리고 size == 5 라고 하면 처음에는
        1번째 인덱스 까지 가야한다.
        즉 size % 3 - 1 까지 가야한다.
        그러면 이제 M = 1 이고 ,
        size = 5 이면 처음에는 list.get(0) 을 빼야한다.
        그럼 그냥 나누어 떨어지는 경우는 걍 직진이고
        즉 1부터 차례대로 가면 된다.
        j = M 으로 하고서 시작하면 된다.

        그게 아니면 위의 같은 경우는 (size - 1) % 3 = 2 이다.
        그러면 그냥 그대로 2를 get 하고 j 에다가 M 만큼 더해가면서 하면 된다.
        그러면 정리하면 M 으로 나누어 떨어지는 경우에는 그냥 j를 처음에 넣고
        그게 아니라면 (size - 1) % M 값을 넣는다.

        그 다음에 end == true 라면 마지막 값 즉
        for(int i = value; i < list.size(); i++) 로 진행을하고,
        그 다음에 if(i == list.size() - 1 && end) 일 때 , 두가지의 기로에 서면 된다.
         */
//        System.out.println("end : " + end);
        int start;

        if((list.size() - 1) % M == 0) start = M; // 나누어 떨어지는 경우
        else start = (list.size() - 1) % M; // 나누어 떨어지지 않는 경우

        for(int i = start; i < list.size(); i += M){
//            System.out.println(list.get(i));
            if(i == list.size() - 1){
                if(end) ans += list.get(i);
                else ans += list.get(i) * 2;
            }
            else
                ans += list.get(i) * 2;
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        List<Integer> negative = new ArrayList<>() , positive = new ArrayList<>();
        negative.add(0);
        positive.add(0);

        st = new StringTokenizer(input.readLine());

        for(int i = 0; i < N; i++){
            int number = Integer.parseInt(st.nextToken());

            if(number < 0) negative.add(number * -1);
            else positive.add(number);
        }

        Collections.sort(positive);
        Collections.sort(negative);

//        System.out.println(positive + " \n"+ negative);

        /*
        이제 여기서부터 , positive 와 , negative 의 값을 비교해서 각각 맞게 호출해주어야 함
        일단 positive 의 , negative 의 양 끝단 값을 확인하고,
        positive 가 더 크면 , positive 에다가 end = true
        그리고 negative 가 더 크면 negative 에다가 end = true
        를 해주는데 , 여기서 문제는 분명히 하나가 존재하지 않을 수도 있다.

        그 경우는 end 처리를 해주긴 해야 하니까,
        예외로 처리를 해준다
        그러니까 , 두개를 먼저 처리를 해주고 , 그 다음에 , 양 끝단의 값이 더 큰 애들을 따로 처리해준다.
         */
        if(negative.isEmpty()){
            calc(positive , true);
        }
        else if(positive.isEmpty()){
            calc(negative , true);
        }
        else if(positive.get(positive.size() - 1) > negative.get(negative.size() - 1)){
            calc(negative , false);
            calc(positive , true);
        }
        else{
            calc(positive , false);
            calc(negative , true);
        } // 음수나 양수가 없는 경우와 , 그게 아닌 경우 처리

        System.out.println(ans);
    }
}