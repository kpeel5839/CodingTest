import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1079 : 마피아

/**
 * -- 전제 조건
 * 게임이 진행되는데, 인원이 홀수이면, 낮
 * 짝수이면 밤이다.
 *
 * 밤에는 마피아가, 사람을 죽일 수 있고,
 * 낮에는 유죄지수가 가장 높은 애가 죽는다.
 *
 * 시민이 다 죽으면 끝나는 것이고,
 * 마피아가 죽으면 게임이 끝난다.
 *
 * 유죄지수와, R 배열이 주어졌을 때, 항상 최적의 선택을 하여서, 가장 밤을 여러번 맞는 경우를 구하시오
 * R 배열은 i 가 죽었을 때, 모든 애들은(j) R[i][j] 만큼 유죄지수가 추가 된다.
 * -- 틀 설계
 * 그냥 일단, 인월을 세는 것이 필요할 것 같다.
 * 그래야지, 낮인지 밤인지를 결정할 수 있으니까,
 * 그리고 밤의 횟수를 세야한다.
 * 내가 봤을때는, 마피아가 절대 못이기는 경우도 있을 것 같다.
 * 그렇기에, 마피아가 이기고, 지는 것은 그냥 게임의 종료조건일 뿐이니까,
 * 그냥 밤의 횟수를 최대로 갱신화만 시키자, 게임만 제대로 종료시키면 될 것 같다.
 *
 * 일단, dfs 를 통해서, R 배열은 그대로두고, 유죄지수를 clone 을 계속 진행해야 할 것 같다.
 * 그래서, 죽은 애들은 유죄지수를 INTEGER.MIN_VALUE 로 지정하자
 * 그래야지, 죽은 애들을 고르지 않을 수 있다.
 *
 * 간단하게 유죄지수를 max 값을 뽑아낼 수 있도록
 * max() 그리고, 누군가를 정하면, 다른 아이들의 유죄지수를 올려주는 cal 그리고 dfs 까지
 * 이렇게 진행하면 될 것 같다.
 *
 * 그래서, 먼저 서순은 dfs 로 진행을 하고, 하나씩 순서대로 죽여본다.
 * 그런데, 당연히 본인의 배열에서 죽이면 안되니까,
 * 죽일 애들을 인자로 넘겨준다.
 *
 * 그리고, 무조건 밤이 될 수 있도록
 * 홀수이면, 먼저 max 로 한명 죽이고 시작하고
 *
 * 짝수이면, 그냥 바로 dfs를 호출한다.
 *
 * -- 해맸던 점
 * 문제는 정말 빨리 풀었음
 * 근데 문제는 메모리 초과였음...
 *
 * 일단 내가 이전에 사용했던 방법인, array 를 clone 해서 하는 것은 절대 안됐음
 * 그래서, 다시 돌려놔야 겠다 해서, 봤는데, 너무 어려웠음
 * 그래서, isLive 라는 boolean 변수를 통해서, 실제로 score 를 변동시키는 것이 아닌
 * 죽었을 때, isLive 를 false 로 변경하여서, 죽은 것을 처리하였음
 *
 * 그리고, restore 이라는 method 를 만들어서, 다시 살리는 것을 처리하였고, 그것은 굉장히 쉬웠음
 * 근데, 이제 문제가 자꾸 원래대로 돌아와야 하는데, 잘 안돌아왔었음
 *
 * 근데, 내가 간과하고 있었던 것이 있음
 * 일단, slay 할때, restore 할때, isLive 하지 않은 즉, 죽은 애들은 처리하지 않으면, 너무나도 문제가 복잡해짐
 * 그리고, 두번째, 짜피 내가 다음 함수를 호출할 때에는, kill 을 넘겨서 주고, dead 하든 말든, 낮에 죽은애의 값은 변하지 않음
 * 그리고, 무엇보다 낮에 죽은애도 score 의 값은 계속 변동이 있기에, 딱히 처리를 해주지 않아도 됨
 *
 * 즉, 이런식으로 해결하였음
 * 위에서 말한 점을 이용하여서, 짜피 kill 할 애를 넘겨주니까, 그리고 낮에 죽인 애는 값의 변동도 없고, 죽더라도 score 값은 계속 갱신이 되니까,
 * 그냥 dfs 호출 한 다음에, restore method 를 한번 부르면 되겠다.
 * 해서, 그렇게 진행하였는데, 역시나 맞았었음
 *
 * 내가 return 하는 경우, 끝나는 경우 너무 많은 상황들을 고려하여서, 괜히 복잡하게 생각했던 문제였음
 * 그래서 결국은, isLive 는, 딱 max 메소드와 다음에 죽일 kill 을 넘길때에만 사용을 하고, 나머지는 딱히 신경쓰지 않았음
 * 그리고, kill 은 짜피 함수 호출부에서, 호출한 뒤에 수정해주니, dead 들만 false -> true 로 변겨해주어서
 * 낮에 죽은 애들만 따로 처리해주었음
 */
public class Main {
    public static int N;
    public static int mafia;
    public static int[][] R;
    public static int res = 0; // 밤의 횟수를 셈
    public static boolean[] isLive;
    public static boolean gameOver = false;

    public static int max(int[] score) {
        // 여기서 그냥 바로 죽여버리자, 근데 죽인 사람이 누구인지 알아야 게임을 끝내니까
        // 마피아가 죽으면 게임이 바로 끝나는 것이니까, index 를 return 해준다.
        int max = Integer.MIN_VALUE;
        int index = 0;

        for (int i = 0; i < score.length; i++) {
            if (isLive[i] && max < score[i]) {
                max = score[i];
                index = i;
            }
        }

        isLive[index] = false; // 죽임
        return index;
    }

    public static void slay(int[] score, int kill) {
        /**
         * kill 할 사람의 번호를 받고, score 를 R 배열을 이용해서 수정한다.
         */
        isLive[kill] = false;

        for (int i = 0; i < score.length; i++) {
            score[i] += R[kill][i]; // 죽인 사람이 가진 R 배열을 이용하여, score 들을 조정
        }
    }

    public static void restore(int[] score, int kill) {
        /**
         * 메모리 초과 문제로 인해서, clone 을 사용하면 안될 듯함
         * 그래서, restore 를 통해서, 이 놈을 kill 하지 않았을 때로 돌아가야함
         */
        isLive[kill] = true;

        for (int i = 0; i < score.length; i++) {
            score[i] -= R[kill][i];
        }
    }

    public static void dfs(int[] score, int remain, int kill, int count) {
        /**
         * kill 할 사람을 정해서 넘어옴
         * 그리고 무조건 적으로 처음에 넘어올 때, 밤임, 내가 그렇게 넘길 것임
         * 그러면 서순이 죽일 사람 정해서 넘어왔으니까
         *
         * 걔 죽이고, 낮이니까 max 이용해서 죽이고,
         * 순서대로 쭉쭉 넘기면 됨
         *
         * count == 밤 횟수
         * kill == 죽일 사람
         * remain == 남은 사람 수
         *
         * remain 이 0으로 오는 경우는 없도록 해야 함
         */
        res = Math.max(res, count);

        slay(score, kill); // 이제, 밤이니까 일단 kill 하고 시작
        remain--; // 죽였으니까, 사람 줄어듦

        if (remain == 1) { // 죽였을 때는, 무조건 밤이다, 그러니까 이긴 경우라면 remain 이 1인 경우밖에 없음
            return;
        }

        int dead = max(score); // 끝나지 않았다면, 유죄지수 가장 높은애 죽인다
        remain--; // 또 한명 죽였으니까 remain 낮춤

        if (dead == mafia) { // 근데, mafia 가 죽었다? 그러면 게임 끝 (진 경우)
            isLive[dead] = true;
            return;
        }

        for (int i = 0; i < score.length; i++) {
            if (mafia != i && isLive[i]) { // mafia 가 아니면서, 이미 죽은애가 아닌 애만 넘겨줌
                dfs(score, remain, i, count + 1);
                isLive[i] = true;
                restore(score, i); // 돌아오면 다시 살림
            }
        }

        isLive[dead] = true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        R = new int[N][N];
        int[] score = new int[N];
        isLive = new boolean[N];
        String[] input = br.readLine().split(" ");
        Arrays.fill(isLive, true); // 일단 다 살아있다는 표식으로 Arrays.fill 을 진행

        for (int i = 0; i < N; i++) {
            score[i] = fun.apply(input[i]);
        } // 유죄지수 입력 완료

        for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < N; j++) {
                R[i][j] = fun.apply(input[j]);
            }
        } // R 배열 입력 완료

        mafia = fun.apply(br.readLine()); // mafia 입력 완료

        if (!(N % 2 == 0)) { // 짝수가 아닐 때, 즉 낮으로 시작할 때
            N--;
            int dead = max(score); // score 실제로 죽이고 시작하자.
            isLive[dead] = false;

            if (dead == mafia) { // 근데 죽였는데, mafia 가 죽었어? 그럼 끝
                gameOver = true;
            }
        }

        if (!gameOver) {
            for (int i = 0; i < score.length; i++) {
                if (mafia != i && isLive[i]) { // mafia 를 죽일 애로 넘겨서는 안됨, 그리고 이미 낮에 죽은애를 넘기면 안됨
                    dfs(score, N, i, 1); // 첫 밤이기 때문에
                    isLive[i] = true;
                    restore(score, i);
                }
            }
        }

        System.out.println(res);
    }
}
