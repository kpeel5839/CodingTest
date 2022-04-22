import java.util.*;
import java.io.*;

// 11758 : CCW

/*
-- 전제조건
세 좌표 평면이 주어졌을 때,
P1, P2, P3 를 이었을 때, 방향성이 시계 방향인지, 혹은 반시계, 아니면 직선인지 판단해서 출력하면 된다.
-- 틀설계
기하학 알고리중에 기본인 CCW 알고리즘을 이용해서 풀면 된다.
역시 그냥 생각 안하고, 답을 보기를 잘했다 이 문제는...
안 그러면 절대 못 찾았음, 애초에 수학문제인데

외적을 구해야 한다, 이 문제의 핵심은 이 벡터들과의 수직인 벡터를 찾아서, 그 벡터의 값을 보고서, 방향을 결정한다.
그렇기 때문에, 두개의 벡터에 수직인 벡터를 찾아야 하는데, 그 방법은 외적을 구하는 방법이다.

근데, 외적을 구하는 방법은 생각보다 굉장히 쉽고, 더 쉽게 신발끈 공식과 같은 공식도 존재하기에, 굉장히 외우긴 쉽다.
하지만, 그 내부적인 원리까지 이해하려면 수학이 필요하다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] input = br.readLine().split(" ");
        int x1 = Integer.parseInt(input[0]);
        int y1 = Integer.parseInt(input[1]);

        input = br.readLine().split(" ");
        int x2 = Integer.parseInt(input[0]);
        int y2 = Integer.parseInt(input[1]);

        input = br.readLine().split(" ");
        int x3 = Integer.parseInt(input[0]);
        int y3 = Integer.parseInt(input[1]); // 입력 종료

        // CCW 알고리즘 사용, 신발끈 방법 사용하면 된다.

        // Java Code Convention 에 맞춰서 작성
        int result = ((x1 * y2) + (x2 * y3) + (x3 * y1)) - ((y1 * x2) + (y2 * x3) + (y3 * x1));

        System.out.println(result < 0 ? -1 :
                result > 0 ? 1 : result);
    }
}
