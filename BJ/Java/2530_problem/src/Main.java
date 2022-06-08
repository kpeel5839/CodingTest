import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2530 : 인공지능 오븐시계

/**
 * -- 전제 조건
 * H, M, S 가 주어지고
 * 조리시간이 초로 주어지면, 끝나는 시각을 계산해라.
 *
 * -- 틀 설계
 * 초를 60 * 60 으로 먼저 나누고
 * 그 다음에 60 으로 나누고
 * 60 으로 나눈 나머지를 더해주면 된다.
 *
 * 그리고, 초 부터 더하면서 초는 / 60 을 해서 1이 나오게 되면
 * 뒤로 minute ++ 를 해준다.
 *
 * 그리고 그 이후로도 똑같이 해준다.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        int H = fun.apply(input[0]);
        int M = fun.apply(input[1]);
        int S = fun.apply(input[2]);

        int during = fun.apply(br.readLine()); // 조리시간

        int divH = during / 3600;
        H += divH;
        during -= divH * 3600; // 이 구문을 추가함으로써 반례를 해결
        M += (during / 60);
        S += (during % 60);

        if (S >= 60) {
            S %= 60;
            M++;
        }

        if (M >= 60) { // 이전에 틀렸던 이유는 이 구문이였음, 왜냐하면 minute 이 실제로는 hour 를 증가 시키지 않아도 during 을 빼지 않아서, 증가시키는 일이 발생하였었음
            M %= 60;
            H++;
        }

        if (H >= 24) {
            H %= 24;
        }

        System.out.println(H + " " + M + " " + S);
    }
}
