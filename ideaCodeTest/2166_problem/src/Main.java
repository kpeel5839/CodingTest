import java.util.*;
import java.io.*;
import java.util.function.Function;

// 2166 : 다각형의 면적

/**
 * -- 전제조건
 * 포인터가 여러개 주어지고, 그 포인터들이 이루는
 * 다각형의 면적을 구하시오
 *
 * -- 틀 설계
 * ccw 를 이용해서, 구하면 되는데, ccw 를 이용하여서, 삼각형의 면적을 구하는 방법을 사용하면된다.
 * 그 방법은, ccw 나오는 외적의 크기 * (1 / 2) 를 하게 되면, 삼각형의 면적이 나오고,
 * 그에 나오는 부호들은 양의 부호, 음의 부호를 무시하고 다 더해준 다음에,
 * 그 다음에, 절댓값을 취하면 된다라는 듯의 뉘앙스이다.
 *
 * -- 해맸던 점
 * ccw 를 구하는 과정에서, * 을 해주어야 하는데, 모르고 더하기를 해서, 한참 해맸었음
 * 그리고, 이상하게 double 로 계산하면 안되는데, long 으로 계산한다음에, 소수점 처리를 직접해주니까 맞음
 * 왜 그런지는 모르겠으나, 확실한 것은 다각형의 넓이를 구하는 방식은, 위키에서 본 대로 하니까 되었음
 * ccw 구할 때, 실수만 안했어도 훨씬 빨리 풀었을 문제인데 아쉽..
 * Main2 는 도저히 내가 왜틀리는지 모르겠어가지고, 값 비교해본 것, 저 코드 덕분에, ccw 잘못 계산한 거 알았음, 아무리 봐도 내가 한 방법이랑 똑같았는데
 * 안되가지고 이상하게 느껴서 알게되었음
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Long> fun = Long::parseLong;

        int N = Integer.parseInt(br.readLine());
        long[][] points = new long[N][2];
        long res = 0L;
        String[] input;

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            // 각각 포인트 삽입
            points[i][0] = fun.apply(input[0]);
            points[i][1] = fun.apply(input[1]);
        }

        for (int i = 2; i < N; i++) {
            // 두 벡터의 외적을 이용하여, 삼각형의 넓이를 반환 받고, 그것들을 계속 더한다.
            // 기준 Pointer 는 그대로 두고, 나머지는 (k - 1), k 로 계속 보낸다.
            res += ccw(points[0][0], points[0][1], points[i - 1][0],
                    points[i - 1][1], points[i][0], points[i][1]);
        }

        res = Math.abs(res);

        if(res % 2 == 0) {
            System.out.println(res/2+".0");
        }else
            System.out.println(res/2+".5");
    }

    // 주어지는 3개의 포인터를 이용하여서, 삼각형을 구한다.
    public static long ccw(long x1, long y1, long x2, long y2, long x3, long y3) {
        long angle = ((x1 * y2) + (x2 * y3) + (x3 * y1)) - ((y1 * x2) + (y2 * x3) + (y3 * x1));

        return angle;
    }
}
