import java.util.*;
import java.io.*;
import java.util.function.Function;

// 10800 : 컬러볼

/**
 * -- 전제조건
 * 컬러볼의 특성은 본인보다 낮은 애들을 잡을 수 있지만
 * 본인과 색깔이 같은 애는 못잡는다.
 * 그 때, 각 i 번째 줄에 i 번째 ball 이 잡을 수 있는 컬러볼의 합을 구해라. (크기)
 *
 * -- 틀 설계
 * 와 고민 많이 했는데
 * 갑자기 확 지나갔다.
 *
 * 일단 첫번째 입력을 받으면서 공의 순서와, 공의 색깔, 공의 크기를 배열로 입력받는다.
 * 그 다음에 정렬을 진행한다. (크기로)
 *
 * 배열을 두개를 선언한다 n 크기의
 * 하나는 i 번째 볼이 얼마나 공을 잡을 수 있는지와
 * 하나는 현재까지 볼을 진행하면서 어떤 색깔의 ball 이 얼마의 크기 동안 포함이 되었냐이다.
 *
 * 그러면 우리에게 필요한 정보는 일단 Si 가 최대한 몇이 존재하냐
 * 그리고, 누적합이다.
 *
 * 이거 2개면 끝이다.
 *
 * -- 해맨점
 * 같은 값일 경우를 고려하지 못했음
 * 같은 값이 연속으로 오는 경우를 전혀 고려 못했음...
 *
 * HashMap 과 innerSum 으로 구분하면서
 * 값이 같은 것들이 연속해서 오는 경우에 대해서 예외처리를 해주었음
 *
 * 어거지로 구현했는데 맞긴 했음..
 * 하하하
 */
public class Main {
    static class Ball {
        int index;
        int color;
        int size;

        Ball(int index, int color, int size) {
            this.index = index;
            this.color = color;
            this.size = size;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Function<String, Integer> fun = Integer::parseInt;

        int N = fun.apply(br.readLine());
        int[] res = new int[N];
        Ball[] ball = new Ball[N];
        int colorMax = 0;

        for (int i = 0; i < N; i++) {
            String[] input = br.readLine().split(" ");
            ball[i] = new Ball(i, fun.apply(input[0]), fun.apply(input[1]));
            colorMax = Math.max(colorMax, fun.apply(input[0]));
        }

        int[] diff = new int[colorMax + 1];
        Arrays.sort(ball, (o1, o2) -> o1.size - o2.size);
        int sum = 0;

//        for (int i = 0; i < N; i++) {
//            Ball b = ball[i];
//            res[b.index] = sum - diff[b.color];
//            sum += b.size;
//            diff[b.color] += b.size;
//        }

        int index = 0;
        while (index < N) {
            int innerSum = sum;
            HashMap<Integer, Integer> map = new HashMap<>();

            while (index != N - 1 && ball[index].size == ball[index + 1].size) { // 같을 때 까지 계속 진행한다.
                if (!map.containsKey(ball[index].color)) {
                    map.put(ball[index].color, ball[index].size);
                } else {
                    map.put(ball[index].color, ball[index].size + map.get(ball[index].color));
                }

                res[ball[index].index] = sum - diff[ball[index].color];
                innerSum += ball[index++].size;
            }

            res[ball[index].index] = sum - diff[ball[index].color];
            sum = innerSum + ball[index].size;
            diff[ball[index].color] += ball[index].size;

            for (Integer number : map.keySet()) {
                diff[number] += map.get(number);
            }

            index++;
        }

        for (int i = 0; i < N; i++) {
            sb.append(res[i]).append("\n");
        }

        System.out.print(sb);
    }
}
