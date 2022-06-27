import java.util.*;
import java.io.*;
import java.util.function.Function;

// 16639 : 괄호 추가하기 3

/**
 * -- 전제조건
 * 그냥 괄호를 추가해서 연산을 진행하면 된다.
 * 그래서 얻을 수 있는 최대값을 출력하면 되고
 * 해당 결과값은 int 형을 벗어나지 않는다.
 *
 * 하지만 분명하게 연산과정 중은 int 를 벗어날 수 있기 때문에 long 으로 선언하자.
 *
 * -- 틀 설계
 * 일단 내 생각에 괄호가 중복으로 가능한 순간부터는
 * 연산자의 우선순위 따위?
 * 중요하지 않다.
 *
 * 그렇다라는 것은 연산자의 개수만큼의 연산 순서를 명시하는 것이 중요하다라는 것이다.
 * 그러면 첫째로 왼쪽에서부터 하는 연산? 중요하지 않음
 * 둘째, 곱하기 연산? 의미 없어진다.
 *
 * 그렇기 때문에 그냥 브루트 포스로 중복되지 않게 1 ~ 9 까지 순서를 주면 된다.
 *
 * 그러면 이제 순서가 정해졌을 때 연산을 하여서 올바른 해답을 찾아내는 것이 중요하다.
 *
 * 어떻게 할 수 있을까?
 *
 * 예를 들어서 8 + 2 * 3 + 4 가 있다라고 가정해보자.
 * 그리고 연산 순서는 1, 3, 2 로 정해졌다고 해보자.
 * 그러면 순서대로
 * 10 * 3 + 4 가 될 테고
 * 그 다음에 10 * 7 이 될 테고
 * 그 다음에 70이 될 것이다.
 * 그러면 이것을 괄호로서 표현하면 ?
 * (8 + 2) * (3 + 4) 이런식으로 표현이 된다라는 것이다
 * 그러면 또 다르게 한번해보자.
 * 3 1 2 이런식으로 연산이 진행된다고 가정해보자.
 *
 * 분명히 불필요한 중복이지만 이것이 수행시간에 있어서 지대한 영향을 끼치지는 않을 것 같다.
 * 8 + ((2 * 3) + 4) 이런식의 연산이 된다.
 * 즉, 위와 같이 연산자에 순번만 주어지면 중복된 괄호도 처리할 수 있다라는 것을 의미한다.
 *
 * 그러면 계속해서 원래 하려던 연산순서가 주어졌을 때, 연산을 어떻게 진행할지에 대해서 생각해보자.
 *
 * 정답을 찾은 것 같다.
 * 나보다 연산자 우선순위가 높다?
 * 즉, 순번이 나보다 낮다
 * 그러면 먼저 실행된 것이니까
 * 그냥 해당 연산을 실행한 뒤에 낮은 것들을 계속 이어서 현재 연산한 결과로 변경해준다.
 *
 * 그러면 예를 들어서
 * 8 + 2 * 3 * 4 + 5
 * 가 있고
 * 1 3 4 2 라고 해보자.
 *
 * 그러면 괄호로서 보면 ((8 + 2) * 3) * (4 + 5) 이런식으로 표현이 가능하다.
 *
 * 그러면 각각의 값들이
 * [8, 2, 3, 4, 5] 이렇게 있다
 *
 * 첫번째 연산을 진행한다
 * [10, 10, 3, 4, 5] 주변에 본인보다 낮은 놈이 없다.
 *
 * 두번째 연산을 진행한다
 * [10, 10, 3, 9, 9] 주변에 본인보다 낮은놈이 없다.
 *
 * 세번째 연산을 진행한다.
 * [30, 30, 30, 9, 9] 왼쪽에 본인보다 낮은놈이 있어서 연산결과를 거기까지 바꿔줬다.
 *
 * 네번째 연산을 진행한다.
 * [270, 270, 270, 270, 270] 왼쪽 오른쪽을 다 바꿔줬다.
 *
 * 그러면 연산 진행 결과는 아무 요소나 뽑아도 다 결과로 채택할 수 있다.
 * 그래서 실제 연산과 맞는지 비교해보자.
 *
 * 30 * 9 = 270 맞다.
 *
 * 이런식으로 연산을 진행하면 될 것 같다.
 *
 * 그러면 메소드 부는
 * 연산자 우선순위를 brute force 로 진행해주는 메소드부 하나
 *
 * 그리고 연산자 우선순위 대로 연산을 진행해줄 메소드 하나
 * 그리고 연산을 진행한 뒤 연산자 우선순위가 본인보다 높은 놈들을 다 바꿔줄 메소드 하나
 *
 * 이렇게 해서 3개의 메소드가 필요할 것 같다.
 *
 * -- 해맸던 점
 * 생각보다 어려운 문제는 전혀 아니였음
 * 근데 약간 해맸던 것이
 * 일단, clone 하고서 모두 calculate, translate 를 전부 cloneValue 로 뿌려줬어야 했는데 그렇지 않았었음
 * calculate 에다가 그냥 value 넘기고서 왜 안돼지 이러면서 바보짓 하고 있었고
 *
 * 그 다음에 또 myPriority, 즉 현재 내 우선보다 높은 애들은 나보다 먼저 연산이 진행된 애들임
 * 그러니까 만약 내가 지금 연산을 해서 값이 바꼈으면 나와 인접해 있는 이미 연산한 놈들은 내가 현재 연산한 값으로 변경해주어야함
 *
 * 그래서 그런식으로 오른쪽 왼쪽을 탐색할 수 있도록 하였는데 여기서 실수했던 것이 먼저 연산한 놈들을 찾아서 바꿔야 하니 sequence[i] > myPriority 이렇게 해야 하는데
 * 처음에 sequence[i] < myPriority 이러고 있었음
 *
 * 그래서 해맸었고
 * 이거 두개를 제외하고서는 해맸던 점은 없었음
 */
public class Main {
    static int L; // 수식의 길이
    static long ans = Integer.MIN_VALUE;
    static char[] operation;
    static int[] sequence; // 연산자 우선순위 기록
    static long[] value; // 실제로 값들
    static boolean[] visited;
    static void translate(int index, long res, long[] value) {
        // res 로 다 바꿔줄 것임 본인 index 보다 낮은 놈들을 다
        int myPriority = sequence[index];
        value[index] = res;
        value[index + 1] = res;

        for (int i = index - 1; i != -1; i--) { // 왼쪽으로 탐색
            if (sequence[i] > myPriority) { // 생각해보니까 반대였음, 이미 연산을 진행한 애들만 바꿔주면 되는 것이였음
                value[i] = res;
                value[i + 1] = res;
            } else {
                break;
            }
        }

        for (int i = index + 1; i < operation.length; i++) { // 오른쪽으로 탐색
            if (sequence[i] > myPriority) {
                value[i] = res;
                value[i + 1] = res;
            } else {
                break;
            }
        }
    }

    static void brute(int depth) {
        if (depth == operation.length) {
            start();
            return;
        }

        for (int i = 1; i <= operation.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                sequence[depth] = i; // 처리
                brute(depth + 1);
                visited[i] = false;
                sequence[depth] = 0; // 복구
            }
        }
    }

    static void start() {
        int s = operation.length; // 해당 우선순위부터 시작함
        long[] cloneValue = value.clone();

        while (s != 0) {
            for (int i = 0; i < sequence.length; i++) {
                if (s == sequence[i]) {
                    translate(i, calculate(i, cloneValue), cloneValue); // 연산 결과를 받아주면서 주변 결과를 바꿔준다.
                    break;
                }
            }

            s--;
        }

        ans = Math.max(ans, cloneValue[0]);
    }

    static long calculate(int index, long[] value) { // index 를 주면 연산을 해서 넘겨준다.
        char c = operation[index];
        long a = value[index];
        long b = value[index + 1];
        long res = 0;

        switch (c) {
            case '*' :
                res = a * b;
                break;
            case '+' :
                res = a + b;
                break;
            case '-' :
                res = a - b;
                break;
        }

        return res;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        L = fun.apply(br.readLine());

        operation = new char[L / 2];
        sequence = new int[L / 2];
        value = new long[(L / 2) + 1];
        visited = new boolean[(L / 2) + 1]; // operation 의 개수만큼, 근데 1부터 시작할 것이니까 + 1 해준다

        String string = br.readLine();
        for (int i = 0; i < L; i++) {
            if (i % 2 == 0) { // value
                value[i / 2] = string.charAt(i) - '0';
            } else { // operation
                operation[i / 2] = string.charAt(i);
            }
        }

        brute(0);
        System.out.println(ans); // 그냥 아무거나 출력하면 됨
    }
}
