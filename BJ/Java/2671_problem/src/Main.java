import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2671 : 잠수함식별

/**
 * -- 전제조건
 * 그냥 솔직히 문제 읽어볼 필요도 없음
 * 정규식 사용하는 것임
 *
 * -- 틀 설계
 * 그냥 String.matchers 이용해가지고 하면 된다.
 * (100+1+|01)+ 로 하면 된다.
 *
 * -- 해맸던 점
 * (100~1~|01)~ 로 계속 했는데
 * 알고보니까 정규표현식에서 반복은 ~ 가 아니라 + 였음
 * 그래서 ~ 를 + 로 바꿔주니까 바로 맞음
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String regex = "(100+1+|01)+";
        System.out.println(br.readLine().matches(regex) ? "SUBMARINE" : "NOISE");
    }
}
