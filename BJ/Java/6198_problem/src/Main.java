import java.util.*;
import java.io.*;
import java.util.function.Function;

// 6198 : 옥상 정원 꾸미기

/**
 * -- 전제조건
 * 경비원 아저씨는 다른 빌딩을 보고 벤치마킹해서, 정원을 꾸미고 싶어한다.
 * 그럴려면, 다른 빌딩들을 봐야 한다.
 *
 * 근데, 빌딩 옥상을 봐야 하는데
 * 높이가 더 낮거나 같으면, 다음 빌딩의 옥상을 못본다.
 *
 * 높이가 같으면, 다음 빌딩의 옥상을 볼 수 있는데, 그 다음의 옥상을 보지 못한다.
 * 이럴 때, 볼 수 있는 옥상의 총 개수는 몇개일까?
 * -- 틀 설계
 * 그냥 stack 으로 풀면 될 것 같다.
 * 그냥 오큰수랑 비슷한 것 같은데
 *
 * 10  3  7  4  12 2  이렇게 들어왔을 때,
 * 3   0  1  0  1  0 개를 볼 수 있다 각 빌딩의 관리자들은 어떻게 하면 될까?
 *
 * 일단 본인이 볼 수 있는 옥상이 나올 때에는 걍 stack 에다가 추가해준다.
 * 근데, 그것을 확정할 수 있으려면? 해당 숫자의 다음 빌딩은 무조건 본인보다 낮아야 한다.
 * 그렇다라는 것은, 현재 stack.peek 에 있는 값이 들어온 입력 값보다 작다면
 * 더 stack.peek 가 더 클때까지 진행을 해야 한다.
 *
 * 그러면 굉장히 빠르게 구할 수 있음
 *
 * -- 결과
 * 역시, 처음에 틀려가지고 왜지? 했는데
 * 80000 개가 순서대로 80000 ...... 1 까지가면
 * 빌딩 볼 수 있는 총합이 32 억 정도 됨 80001 * 40000 이니까 3200040000 임
 * 32억 넘으니까 당연히 long 써야 함
 *
 * 그래서 long 써서 바로 맞음 기모찌
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        long res = 0; // 빌딩의 총합
        long[] height = new long[N];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < N; i++) {
            height[i] = fun.apply(br.readLine());
        } // 입력 받아줌

        for (int i = 0; i < N; i++) { // 이제, stack 에다가 넣어주면서 볼 수 있는 빌딩의 총합을 구해주어야 함
            if (stack.isEmpty()) { // stack 이 비어있을 땐 닥치고 넣으면 됨
                stack.push(i);
                continue;
            }

            while (!stack.isEmpty() && (height[stack.peek()] <= height[i])) { // stack 에 들어있는 빌딩의 높이가, 현재 빌딩의 높이보다
                res += (i - stack.pop() - 1);
            }

            stack.push(i);
        }

        while (!stack.isEmpty()) { // 마지막까지 남아있던 것들 다 뺴줌
            res += (N - stack.pop() - 1);
        }

        System.out.println(res);
    }
}
