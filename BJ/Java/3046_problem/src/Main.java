import java.util.*;
import java.io.*;
import java.util.function.Function;

// 3046 : R2

/**
 * -- 전제조건
 * 두 숫자 R1, R2 가 있을 때,
 * 두 수의 평균 S 는 (R1 + R2) / 2 와 같다 (당연)
 * 상근이는 정인이 생일 선물로 두 숫자 R1과 R2를 주려고 한다.
 *
 * 그래서 상근이가 정인이에게 이 두 숫자를 말해주고, 정인이는 이 숫자를 받아 적는다.
 * 그리고나서 기쁜 마음으로 1년동안 이 숫자를 외우면서 산다.
 *
 * 상근이는 R1과 R2 를 엄청난 고민 끝에 정했다.
 * 작년에는 R1과 R2를 까먹어서 아무 숫자나 정해서 줘서 올해는 까먹지 않기 위해 평균 S 도 같이 기억하려고 한다.
 *
 * R1, S 는 기억하고 R2 를 까먹었을 때, R2를 구하여라
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        double R1 = fun.apply(input[0]);
        double S = fun.apply(input[1]);

        // (R1 + R2) / 2 = S == R1 / 2 + R2 / 2 == S, R2 / 2 = S - R1 / 2
        // R2 = (S - R1 / 2) * 2 or (S * 2) - R1

        System.out.println((int) ((S - (R1 / 2)) * 2));
    }
}
