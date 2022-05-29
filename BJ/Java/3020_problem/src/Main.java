import java.util.*;
import java.io.*;
import java.util.function.Function;

// 3020 : 개똥벌레

/**
 * -- 전제조건
 * 일단, 석순과 종유석이 번갈아가면서 있다.
 * 이것이 의미하는 것은 땅에서 솟는 것과
 * 하늘에서 솟는 것을 기준으로 할 수 있다.
 *
 * 그래서 이것들을 다 고려하였을 때, 우리의 똥벌레가 최소의 개수로 부시고서
 * 일직선으로 빠져나갈 수 있게 해보자.
 *
 * 그리고 height 는 아래에서부터 카운트하게 된다.
 * 즉, 첫번째(짝수)는 height = 0 부터 영향을 주고 두번째(홀수) 는 height = max_height 부터 영향을 준다.
 *
 * -- 틀 설계
 * 일단 석순, 종유석이 들어올 때, 높이 당 하나하나 하는 방법은?
 * 절대 시간 내에 통과할 수 없다.
 *
 * 그러면 어떻게 할 수 있을까
 *
 * 진짜 투 포인터로 머리 터질 듯 생각하면서 풀었다
 *
 * 결과는 이러하다.
 * 이렇게 푸는게 맞는지는 모르겠는데
 *
 * 일단, 동굴의 위에 달린 애들은 오름차순으로 정렬한다.
 * 그리고, 동굴의 아래에 달린 애들은 내림차순으로 정렬한다.
 * 그러면 대충
 * 1 2 3
 * 3 2 1
 * 이런식으로 될 것이다.
 * 1 1 2 2 3 3 이렇게 값이 들어왔다고 했을 때
 * 그러면 이제부터 쉽다 그냥 50 만 높이를 다 세주는데 각각의 높이에서 위에 달린 돌들은 걸리지 않는 애들을 넘어가주고 sky.length - skyPointer 해서 개수를 세어주고 (뒤에 남은 애들을 세는 것이다.)
 * ground 는 그냥 걸리는 애들을 while 문을 통해서 세주는 것이라고 생각할 수 있다
 *
 * 정렬을 진행하게 된 이유는 짜피 높이에 상관 있는 것이고 돌들의 순서는 전혀 상관이 없는 것 같아서 그랬고
 * 해보니까 바로 맞았다.
 */

public class Main {
    static int H;
    static int W;
    static int min = Integer.MAX_VALUE;
    static int count = 0;
    static Integer[] ground;
    static Integer[] sky;
    static int[] height;
    static int skyPointer = 0;
    static int groundPointer = 0; // two pointer 를 이용해서 해결해볼 것임

    static int movePointer(int h, int position) {
        if (position == 0) { // position is ground
            while (groundPointer < ground.length && h <= ground[groundPointer]) { // height 가 다시 커질 때까지
                groundPointer++;
            }

            return groundPointer; // 현재 groundPointer 가 현재 높이보다 높거나 같은애가 얼마나 있냐임, 그러니까 이게 답이지
        } else { // position is sky
            h = H - h + 1; // 실제 거꾸로 봤을 떄의 높이로 변환해줌
            while (skyPointer < sky.length && h > sky[skyPointer]) { // 범위를 벗어나지 않고, 현재 종유석의 높이가 height 보다 높아지거나 같아질 때까지
                skyPointer++;
            }

            return sky.length - skyPointer;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        W = fun.apply(input[0]);
        H = fun.apply(input[1]);
        ground = new Integer[W / 2];
        sky = new Integer[W / 2]; // 무조건 짝수임
        height = new int[H + 1];

        for (int i = 0; i < W; i++) {
            int a = fun.apply(br.readLine());

            if (i % 2 == 0) { // ground
                ground[i / 2] = a;
            } else { // sky
                sky[i / 2] = a;
            }
        }

        Arrays.sort(sky); // sky 는 오름차순
        Arrays.sort(ground, Collections.reverseOrder());

        for (int i = H; i != 0; i--) {
            height[i] = movePointer(i, 0) + movePointer(i, 1);
            min = Math.min(min, height[i]);
        }

        for (int i = 1; i <= H; i++) {
            if (min == height[i]) {
                count++;
            }
        }

        System.out.println(min + " " + count);
    }
}
