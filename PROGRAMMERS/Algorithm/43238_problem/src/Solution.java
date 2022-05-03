import java.util.*;
import java.io.*;
import java.util.function.Function;

/*
일단, 상식적으로 생각해보면 당연히 최대 10억명이기에, 절대로 하나하나 진짜로 넣어보는 일?
그렇게는 불가능함, 만일 하나하나 넣어본다고 하더라도?
time 이 끝난 애들을 찾는 것과,
그리고 최적의 방법을 찾는 방법도 어려움
즉, 한명 한명을 직접적으로 처리하는 것? 그것은 무조건 시간초과 및 메모리 초과를 야기할 수도 있음

그러면 어떻게 해야 할까
일단, 현재 정말 작은, n == 6 인 상황에서만 보면 어떤식으로 해결할 수 있을까 접근해보는 것이 중요할 듯 하다.

일단 한명씩 처리를 해보자.
times size == 2 밖에 안되기 때문에, 두개만 비교해보면 될 것 같은데...
왜 이렇게 생각이 떠오르지 않지

일단 확실한 것은, 마지막까지?
심사대는 비어있지 않는 것이 최우선이다.

언제까지 그러냐면... 끝나기 직전까지?

여기서도 언제까지 계속 그냥 진행했냐면, 4명까지는 그냥 무조건 심사대를 비우지 않고 진행했음
정확히 말하면 5명까지는 심사대를 비우지 않고 진행했음

7 10 (times)
1 1
2 1
2 2
3 2

이런식의 순서로 진행을 하였음

여기서 알 수 있는 점은 무엇이 있을까?
적어도 21초까지는 5명을 심시할 수 있다는 사실을 알 수 있지 않을까??

그 사실은 어떻게 알 수 있을까?
최소 공배수로 알 수 있을까??
70 = 10 (7 times)
70 = 7 (10 times)

70초에 17 명까지 처리가 가능하다.

지금은 접근 방법이 너무 어려운 것 같다.
그냥, 처음에는 다 집어 쳐넣는다라고 하면
일단, times size 만큼의 n 을 처리가 가능하다.

근데, 생각해보니까, 만일 심사관이 1명에, 심사시간 10 억 입국심사 기다리는 사람 10억명이면
10 억 * 10 억 이다. long 범위 안에 들어가는 구나 머쓱머쓱

심사관이 1명인 경우는 심사시간 * n 이다.
음...
가장 큰놈을 찾는 것이 우선시 되어야 할 것 같긴한데..

와 결국 답을 봤는데 역시 이진 탐색은 너무 어렵다.

일단, 내가 생각했던 것과 동일한 것은 하나뿐이다.
times 의 max 값을 기준으로 잡는 것

그것외에는 다 다르다.
해결 방법은 이러했다.

최종적으로는 걸리는 시간을 찾는 것이 이 문제의 목적이다.
그렇기 때문에, right = max(times);
그리고 무조건 시간은 1분 이상이 걸리기에 left = 1; 로 설정을 진행한다.

그 다음에, 그냥 (left + right) / 2 를 진행하여서 적합한 time 을 찾는 것 같다.
그것을 찾는 방법은, 설정한 time 만큼 진행했을 때, 심사할 수 있는 사람의 수가 최적인지 보는 것이다.

그래서, 그런식으로 심사할 수 있는 사람의 수가 더 많거나 같다면, right = mid - 1 을 진행해주고
left = mid + 1 을 진행해준다.

-- 해맸던 점
Math.max(times[i] * n, max); 하는 과정에서
times[i] * n 이 둘다 int 여가지고, int 범위 벗어나는 문제가 있었음
그래서, long 타입으로 변화 시켜주니까, 바로 맞음

-- 결론
결국 답을 봤지만, 이러한 문제들의 특징을 알 수 있었음
너무 어렵게 다가갈 필요는 없음
그냥, 시간을 지정하고, 그 시간만에 이렇게 처리할 수 있는지, 그것을 판단하는 것이였었음

생각하기는 너무 어렵지만, 그래서 답을 봤지만 막상 생각하고 나면 겨우 이런거였어 하는 문제임
이런 문제 풀 때마다, 아직 많이 부족하다라고 느낌

여기서 사용한 방법은, 최악의 상황과, 최고의 상황 두 기준을 left, right 로 잡아놓고
left = 최고의 상황에 걸리는 시간
right = 최악의 상황에 걸리는 시간을 집어넣는다.

그것은 바꿔서 하면
left = 1;
right = max(times) * n 이다.
왜냐하면, 최악의 상황은 times 에서 가장 긴 심사시간을 가지는 애들이 모든 인원을 검사하는 경우일 테니까
당연히 그럴일은 없을 것이지만, 이렇게 설정하는 것이 마음이 편하다.

그래서 그런식으로 진행한다음에
이분탐색의 거의 룰인 while (left <= right) 을 진행한다.
그리고서 mid = (left + right) / 2 를 해서 mid 를 구해준다.

mid 가 최적의 시간을 찾기 위한 키워드이다.
시간 mid 를 가지고, 이 시간이라면 총 심사할 수 있는 인원의 총 수를 체크하는 것이다
그것이 만약 n 보다 높다? 그러면 시간을 줄여야 하는 것이고
n 보다 낮다 그러면 시간을 늘려야 하는 것이다.

이제, 가장 애매한 것은 n 과 count 가 같은 경우이다.
하지만, 우리가 찾아야 하는 값은? 최적의 시간이다.

그렇기 떄문에, 같은 경우라면 ? 예를 들어서 예제와 같이
6
7 10
인 경우에
mid = 29 라면?

left = 28, right = 30 이라고 했을 떄, mid 값은 29이고
n == count 이다.
하지만, mid 값은 최적의 값은 아니다. 최적의 값은 28 이기 때문이다.

이점으로 미루어 보았을 때, right = mid - 1; 을 할 수 있다라는 것을 알 수 있다.
하지만, 언제 while 문이 끝날 지 모르고, count >= n 은 적어도 n 명을 심사할 수 있기 때문에
answer = mid 를 해준다. (이 시간으로는 일단 n 명을 심사가 가능하니까 mid 값을 넣어주는 것임)

당연히 위에 설명을 통해서 n > count 는 answer = mid 를 하면 안된다라는 것을 간접적으로 알 수 있다.

그래서 만약 위의 상황에서는 left = 28, right = 28 이 된다.
그리고서 이번에는 mid = 28 로 진행이 된다.
또 count >= n 이다.
이 경우에 그러면 answer = mid == 28 이고,
right = mid - 1 이기 때문에
left = 28, right = 27이 됨으로 반복문은 끝나게 된다.

이분탐색은 이런식으로 진행하면 된다.
만일 left = mid + 1; 을 안한다면

이러한 경우에 에러가 발생하지 않을까
left = 28, right = 30 일때,
29 가 n > count 인 것이다.
그러면 left = mid 가 되어서
left = 29, right = 30 이 될 것이다.
근데, 여기서는 또 mid = 29 이다.

그렇기 때문에 여기서는 무한 루프에 빠지게 된다.
왜냐하면 left = mid 가 계속 반복이 되니까
그래서 left = mid + 1, right = mid - 1; 이 되어야 한다.
*/
class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int n = fun.apply(br.readLine());

        String[] input = br.readLine().split(" ");
        int[] times = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            times[i] = fun.apply(input[i]);
        }

        System.out.println(solution(n, times));
    }
    public static long solution(int n, int[] times) {
        long max = 0;
        long answer = 0;

        for (int i = 0; i < times.length; i++) {
            max = Math.max(((long) times[i] * (long) n), max);
        }

        long left = 1;
        long right = max;

        while (left <= right) {
            long mid = (left + right) / 2L;
            long count = 0;

            for (int i = 0; i < times.length; i++) { // 해당 시간만큼으로, 심사할 수 있는 이가 몇명인지 검사
                count += (mid / times[i]);
                if (count >= n) { // 이미, 심사한 사람이 n이 넘어버리면, 비효율 적이라는 것이 밝혀진 것이니까, 끝
                    break;
                }
            }

            if (count >= n) { // 현재 시각으로 심사할 수 있는 사람이 n 이상이면 time 을 낮춰야 함
                answer = mid;
                right = mid - 1L;
            } else { // 부족하면 현재 시각을 올려야 한다.
                left = mid + 1L;
            }
        }

        return answer;
    }
}