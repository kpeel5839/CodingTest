import java.util.*;
import java.io.*;
import java.util.function.Function;

// 3678 : 카탄의 개척자

/**
 * -- 전제 조건
 * 일단 한칸에 1 ~ 5 까지의 자원이 들어갈 수 있다. (점토, 재목, 양모, 곡물, 광석)
 * 우리가 랜덤으로 맵을 형성하는데
 * 이러한 조건이 필요하다.
 *
 * 1.새로운 타일은 이미 채워진 인접한 타일에 들어있는 자원과 다른 자원이어야 한다.
 * 2.가능한 자원이 여러 가지인 경우에는, 보드에 가장 적게 나타난 자원을 선택한다.
 * 3.그러한 경우도 여러 가지라면, 번호가 작은 것을 선택한다.
 *
 * 이런 다음에 N 이 주어졌을 때 정답을 그 N 번째의 육각형의 숫자를 출력하시오
 * -- 틀 설계
 * 일단, 인접한 자원을 찾는 것이 제일 중요하다.
 * 근데, 이것은 내가 엄청나게 생각한 결과 답을 구하였다
 * 육각형의 면은 6면이다.
 * 그러면 보통은 인접한 육각형이라고 함은, 한 면이 닿고 있어야 한다.
 * 그렇다라는 것은?
 * 면이 닿으면서, 더 이상 닿을 면이 없으면 다른 육각형은 이 육각형을 만나지 못한다.
 * 그렇다라는 것은, 육각형의 남은 면의 개수를 세준다라면?
 * 어떤 육각형과 인접하였는지 그 개수를 구할 수 있다.
 *
 * 그리고 어떠한 육각형이든 새로 놓이게 되면 최대 3면 밖에 닿아있지 못한다.
 * 그래서 이런 점들을 고려하여서, 1 -> 2 이 부분만 조심해주고 나머지는
 * 원래 세던 육각형의 면이 1면만 남았다면?
 *
 * 그러면 다음 육각형을 세주면 된다.
 * 그것은 index 로서 관리하고
 * 일단 최대 사이즈가 10000 이니까
 * 배열을 10000 + 1 size 로 관리하면서 먼저 다 만들어놓은다음
 * 쿼리가 들어오면 출력하기만 하면 된다.
 *
 * 그리고 2, 3 번째 조건은 그냥 가능한 자원이 여러가지인 경우 이딴 거 다 재껴두고
 * 가능한 자원중 가장 min 값을 취하면 된다.
 *
 * for (int i = 0; i < 5; i++) if (matrix[i] < matrix[min]) 이면 그냥 바꿔주고 그거 넣어주면
 * 자연스레 2, 3번 조건을 만족하게 된다.
 *
 * 위의 조건을 만족하면서 빠르게 한번 짜보자.
 */
public class Main {
    static final int SIZE = 10000;
    static final int RESOURCE_SIZE = 5; // 상수들
    static int T;
    static int[] map = new int[SIZE + 1]; // map
    static int[] remain = new int[SIZE + 1];
    static int[] count = new int[RESOURCE_SIZE + 1];
    static StringBuilder sb = new StringBuilder();

    static void createMap() {
        /**
         * 일단 맵을 만들어넣는 method 이다.
         * 여기서 신경써야 할 부분은? 육각형의 남은 부분이다.
         * 그리고 인덱스는 ? 1부터 시작한다.
         * 그게 가장 중요하다.
         * count[1]++ 를 먼저 해주고
         * Arrays.fill(remain, 6); 모든 육각형들의 남은 면을 6으로 해준다.
         * 그리고, 2까지는 손수 하드 코딩을 해준다.
         *
         */
        int adj = 1; // adj 는 현재 건너편의 인접한 육각형이다.
        int now = 3; // now 는 현재 이제 채워야 할 index 이다.

        Arrays.fill(remain, 6);

        for (int i = 1; i <= 2; i++) { // 2 까지는 하드 코딩 해준다.
            count[i]++;
            remain[i]--;
            map[i] = i;
        }

        while (now != SIZE + 1) { // size + 1 이 되면 끝내준다.
            boolean[] impossible = new boolean[SIZE + 1];
            /**
             * 바로 이전 것은 수는 remain 도 줄여주고 들어갈 수도 없음
             * 그리고 인접한 것을 판단해야 하는데
             * 인접한 것을 판단할 때, 두가지의 조건이 발동한다.
             * 첫째, remain[adj] == 1 이다?
             * 인접한 거 이거랑 adj ++ 도 인접했음
             * 그러면 remain[adj]-- 도 해주고 remain[adj + 1]-- 도 해주고
             * impossible[map[adj]], impossible[map[adj + 1]] 도 true 로 만들어줌
             *
             * 이런작업들을 다 진행한다음에 들어갈 수를 정하고
             * now ++ 해주는 작업 반복
             */
            impossible[map[now - 1]] = true;
            remain[now - 1]--; // 이전것도 남은 면 줄어듦

            if (remain[adj] == 1) { // 남은 면이 하나다? adj ++ 해주면서 처리
                impossible[map[adj]] = true;
                impossible[map[adj + 1]] = true;
                remain[adj]--;
                remain[++adj]--; // adj 를 증가시켜주면서, 그것도 remain 줄여준다.
                remain[now] -= 3; // 3 면이 닿아있는 경우
            } else { // 남은면이 하나가 아니다? 그러면 그냥
                impossible[map[adj]] = true;
                remain[adj]--;
                remain[now] -= 2; // 2면이 닿아있는 경우
            }

            int min = Integer.MAX_VALUE;
            int res = 0;
            // 이제 들어갈 수 있는 수를 선택할 차례이다.
            for (int i = 1; i <= RESOURCE_SIZE; i++) { //여기서 impossible[i] == true 인 것들은 넘어가야 함
                if (!impossible[i]) { // 가능한 것들만
                    if (min > count[i]) { // min 보다 작으면, 가능한 것들 중 가장 적게 들어간 애를 걸러내는 작업
                        min = count[i];
                        res = i;
                    }
                }
            }

            count[res]++; // 들어간 수의 개수를 증가시켜준다.
            map[now] = res; // 들어갈 수 있는 수 넣어주고
            now++;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        createMap();

        T = fun.apply(br.readLine());

        for (int i = 0; i < T; i++) { // 이미 map 을 만들어놓은 상태
            sb.append(map[fun.apply(br.readLine())]).append("\n");
        }

//        for (int i = 1; i <= 13; i++) {
//            System.out.print(map[i] + " ");
//        }

        System.out.println(sb);
    }
}
