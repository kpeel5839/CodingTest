import java.util.*;
import java.io.*;
import java.util.function.Function;

// 10253 : 헨리

/**
 * -- 전제조건
 * 1보다 작은 분수를 단위 분수로 표현할 수가 있다.
 * 이때, 단위 분수로 표현할 때에
 * 무조건 (1 / x1) < (a / b) 를 만족하는 것들을 순서대로 정하였을 때,
 * 가장 마지막 단위분수에 분모를 출력하라.
 * -- 틀 설계
 * 확실히 생각할 필요가 있어보인다.
 * 현재 a / b 에 걸맞는 가장 큰 x1 을 찾을 수 있다?
 *
 * 그러면 그 다음부터는 정말 쉬움
 * 일단, 첫번째, 최대공배수가 존재해야 한다.
 *
 * 그 다음에, 최대공배수를 이용하여서 계산하여서 그 수를 변경시켜줄 수 있으면?
 * 금방 끝낼 수 있다.
 *
 * 일단, 2 부터 쭈우욱
 * 진행하면서, 최대공배수 되면 바로, 바로, 바로 가면 된다.
 * 그러면 결국 차가 0이 되는 순간 그때 반복을 끝내면 된다.
 * 일단 최소공배수를 구하는 알고리즘 LCM 을 공부하고 해야할 듯 하다.
 *
 * GCD 알고리즘을 이용해서 최소공배수도 구할 수 있었음
 * 유클리드 호제법으로 인해서, 나오는 최대공약수를 두 수를 곱한 것에 나누면 된다.
 *
 * 유클리드 호제법은, a b 가 있다고 했을 때 a % b = r 이라고 하자, a 와 b 의 최대 공약수는 b 와 r 의 최대공약수와 같음
 * 그리고, a 와 b 중 0 이 되는 수가 있으면 나누는 수가, 최대공약수이다.
 *
 * -- 해맸던 점
 * 일단 설계 자체는 완벽했음
 * 그리고 오버플로우를 피하기 위해서 최소공배수를 구할 때, (big * small) / gcdNumber 가 아닌, big * (small / gcdNumber) 를 하였음
 *
 * 그래서 설계는 그냥 유클리드 호제법 이용해서, 최소공배수 빠르게 구해준다음에,
 * 서로 분모를 똑같이 했을 때, 분자가 원래 주어지는 수의 분자보다 단위분수의 분자가 더 크면 그냥 continue 그렇지 않으면 빼주는 형식으로 해결하였음
 *
 * 근데, 다 잘됐음 근데, 이전에는 if (a == 0) 이 되었을 때, 즉 주어진 입력을 단위분수로 완벽하게 0을 만들었을 때, 끝내는 형식으로 했는데
 * 그러면 문제가 1 / (진짜 쌉 개큰수) 하면, 무조건 똑같은 수를 빼줘야 하기 때문에, 너무나도 큰 수를 탐색해야 하는 문제가 있었음
 *
 * 그래서, 서로 빼준다음에 다시 약분해준뒤에, 그 다음에 분자가 1이라면? 그러면 짜피 저 쌉 개큰수를 빼주면 되니까
 * res = b 를 해주면서 끝냈음 (break)
 *
 * 근데, 이제 문제는 입력을 받았을 때, 애초에 1 / (진짜 쌉 개큰수) 인 경우를 생각안했었어서
 * 또 다시, 입력 받고나서도 분자 1이라면 그냥 res = b 해서 끝냈더니
 * 아주 깔끔하게 맞았음
 */
public class Main {
    static int T;
    static long big;
    static long small;
    static long res;

    public static void swap() { // 무조건 큰 수가 a 로 갈 수 있도록
        if (big < small) { // big 이 더 작은 경우 swap 해줌
            long tmp = small;
            small = big;
            big = tmp;
        }
    }

    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b); // 계속해서 역으로 구한 a 를 반환
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        T = fun.apply(br.readLine());

        while (T-- > 0) {
            String[] input = br.readLine().split(" ");

            long a = fun.apply(input[0]);
            long b = fun.apply(input[1]);

            long num = 2;

            if (a == 1) {
                res = b;
            } else {
                while (true) {
                    big = b;
                    small = num;
                    swap(); // 더 큰 수를 big 에다가 옮김

                    long gcdNumber = gcd(big, small); // 최대 공약수를 얻어냄
                    long lcm = big * (small / gcdNumber); // 이걸로, 최소공배수 구함

                    long numMole = lcm / num;
                    long mole = a * (lcm / b);

                    if (numMole <= mole) { // 원래 수의 분자가 더 크거나 같은 경우만 진행한다.
                        a = mole - numMole; // a 최신화 해주고
                        b = lcm; // 최소공배수로 변경

                        gcdNumber = gcd(b, a); // b 가 무조건 크니까
                        b /= gcdNumber;
                        a /= gcdNumber; // 약분을 먼저 해주고 진행해야 함

                        if (a == 1) { // 1 이 되면, 끝내준다. 이렇게 하면 시간은 훨씬 줄일 수 있음
                            res = b;
                            break;
                        }
                    }

                    num++;
                }
            }

            sb.append(res + "\n");
        }

        System.out.print(sb);
    }
}
