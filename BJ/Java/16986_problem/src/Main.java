import java.util.*;
import java.io.*;
import java.util.function.Function;

// 16986 : 인싸들의 가위바위보

/**
 * -- 전제조건
 * 인싸들의 가위바위보는 인싸들이 하는 가위바위보이다.
 * 인싸들은 확실히 사서 고생하는 모습을 보인다.
 *
 * 일단 그냥 비긴 경우는 정해진 순서의 뒤에 있는 애가 이기는 것으로 판정을 해야 한다.
 * 그렇게 해서, 모든 가위바위보를 지우가 이기는 경우
 * 모든 손 동작을 다르게 할 수 있다라면 1
 * 아니면 0을 출력하면 된다.
 *
 * 입력은 손동작 수를 타타내는 N 과 필요한 승수 K 가 주어진다.
 * 그리고 상성에 대한 정보 Aij 가 주어진다.
 * 이 의미는 무엇이냐
 *
 * i 번째 줄에 있는 손동작은 j 번째 있는 동작을 2번은 이긴다라는 경우
 * 1번은 비긴다, 0번은 지게 된다라는 경우이다.
 *
 * 경기는 20경기가 주어지고
 * 경희, 민호 순으로 20경기에 얘내들이 낼 손동작이 정해진다.
 *
 * 이때 문제에서 원하는 답을 출력하라.
 *
 * -- 틀 설계
 * 일단 모든 경기 손동작을 다르게 하여서 이기려면 적어도 우승의 횟수가 손동작의 개수보다 적어야 한다.
 * 그 점은 솔직히 고려 안해도 될게 구현 잘하면 이 경우 알아서 0을 뱉어낼 것이다
 * 굳이 귀찮게 이 처리는 하지 않을 것이다.
 *
 * 비둘기 집의 원리로 인해, 3(K - 1) + 1 번의 경기를 치르면 당연하게 누군가가 K 번 승리한다.
 * 예를 들어서 K = 6 이라면 당연하게 5, 5, 5 번 이긴 상태에서
 * 1번 더이기면 16번, 적어도 16번 경기만 진행하게 되면, 이미 승패는 결정된다.
 *
 * 그냥 문제는 빡구현 문제인 것 같다.
 * 일단, 어떤 손동작이 어떤 손동작을 이기는지에 대해서, 2차원 배열로서 관리해야 할 부분인 것 같다.
 * 같은 손동작이면 당연히 비긴다.
 *
 * 그런 다음에, 순서는 정해져 있다
 * 지우, 경희, 민호
 * 그러면 지우, 경희, 민호 순으로 진행해야 하고
 * 그러면 지우 - 경희, 경희 - 민호, 민호 - 지우 이런순의 경쟁이 계속 진행이 된다.
 *
 * 그래서 필요한 인자로는 지금 현재 1, 2, 3 중 몇번째 순서인지와
 * 현재 몇번째 경기를 진행하는지에 대한 정보이다.
 *
 * 그러면 함수 외적으로 구성해야 하는 정보는 무엇이 있을까
 *
 * 일단 손동작의 상성, 이긴 횟수, 상대방의 동작이다.
 * 그리고, 현재까지 어떤 손동작을 냈는지도 저장해놓으면 된다.
 *
 * 솔찬히 쉬운 문제인 것 같아서 이정도 설계하고 들어가면 될 것 같다.
 *
 * 사실 그리고 지우 입장에서는 내가 이길 수 있는 것을 못내는 경우는
 * 그냥 나머지 2개중 아무거나 내면 된다.
 *
 * 비기는 경우 짜피 뒤에 있는 애가 이기기 때문에 이걸로 고심할 이유는 없음
 *
 * -- 해맸던 점
 * 내가 진짜 이 문제는 대충 읽어서 너무 간과하는 것이 많았음
 * 첫번째, 무조건 위에서 언급한 것처럼의 순서로 진행되는 것이 아님
 * 규칙에도 적혀있지만, 이긴사람과 이전에 참가하지 않은 사람이 붙는다고 되어있음
 * 두번째, 진짜 이건 개 어이없는 실수였는데
 * 라운드별로 가위바위보가 진행되는 순서가 아니라
 * 경희와 민호의 주어진 info가
 * 그것이 아닌, 그냥 쟤내들이 낼 순서였음
 * 즉, 얘내들은 여기서 반은 거의 안씀
 *
 * 아주 잦같은 문제였음
 * 대충 읽어서 내 생각을 많이 잡아먹었음
 * 나머지 조건들은 다 뭐 처음부터 괜찮았지만
 *
 * 진짜 이상한 것들 때문에 시간을 많이 잡아먹었었음
 * 또 이외로 round[a]++ 만 해놓는다던가
 * 그런 사소한 실수들도 많이 했음
 *
 * 문제 읽는 거는 너무 귀찮지만
 * 다시 한번 꼼꼼히 읽어야 한다는 사실을 상기하였음
 */
public class Main {
    static int N;
    static int K;
    static int res = 0;
    static int[] win = new int[4]; // 이긴 횟수
    static boolean[] visited; // 해당 손동작을 냈는지
    static int[][] info = new int[4][20]; // 손동작, 2 == 경희, 3 == 민호씌
    static int[][] syna; // 손동작의 상성, 0 = 진다, 1 = 비긴다, 2 = 이긴다.
    static int[] round = new int[4]; // 알고보니까 경희, 민호는 라운드별로 저렇게 내는 게 아니였음....

    static void bruteForce(int a, int b) {
        if (res == 1) { // 이거면 아얘 게임 끝난겨
            return;
        }


        if (check()) { // 누군가가 이긴 경우
            if (win[1] >= K) { // 하나가 K 이상인게 끝나버린겨, 근데 1 번이 이겼으면 끝나부린 것이제
                res = 1;
            }

            return;
        }

        if (a == 1 || b == 1) { // 선택해야 하는 경우는 솔직히, now == 1 인 경우 지우 - 경희

            if (b == 1) { // a 를 무조건 지우로 만들어준다.
                int tmp = a;
                a = b;
                b = tmp;
            }

            for (int i = 1; i <= N; i++) {
                if (!visited[i]) { // 아직 안낸 것만 가능
                    int decide = syna[i][info[b][round[b]]]; // 현재 이게 2 면 a 가 이긴 것, 0 이면 진 것, 1이면 비긴 것
                    int winner;

                    if (decide == 2) { // a 가 이긴 경우, 즉 지우가 이긴 경우
                        winner = a;
                    } else if (decide == 1) {
                        winner = Math.max(a, b);
                    } else {
                        winner = b;
                    }

                    win[winner]++;
                    visited[i] = true;
                    round[b]++;
                    boolean[] player = new boolean[4];
                    player[a] = true;
                    player[b] = true;

                    for (int j = 1; j <= 3; j++) {
                        if (!player[j]) {
                            bruteForce(winner, j);
                        }
                    }

                    round[b]--;
                    win[winner]--;
                    visited[i] = false;
                }
            }
        } else {
            int decide = syna[info[a][round[a]]][info[b][round[b]]]; // 현재 이게 2 면 a 가 이긴 것, 0 이면 진 것, 1이면 비긴 것
            int winner;

            if (decide == 0) { // b 가 이긴 경우
                winner = b;
            } else if (decide == 1) { // a, b 가 비긴 경우, 사실 이것도 큰 숫자를 뒤에다가 두는 식으로 초기화하면 그냥 해결, 근데 고치기 귀찮(배열)
                winner = Math.max(a, b);
            } else { // a 가 이긴 경우
                winner = a;
            }

            win[winner]++;
            round[a]++;
            round[b]++;
            boolean[] player = new boolean[4];
            player[a] = true;
            player[b] = true;

            for (int j = 1; j <= 3; j++) {
                if (!player[j]) {
                    bruteForce(winner, j);
                }
            }

            round[a]--;
            round[b]--;
            win[winner]--; // 끝내고 돌아온 경우 다시 돌려내줌
        }
    }

    static boolean check() {
        for (int i = 1; i < 4; i++) {
            if (win[i] >= K) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        N = fun.apply(input[0]);
        K = fun.apply(input[1]);

        syna = new int[N + 1][N + 1];
        visited = new boolean[N + 1];

        for (int i = 1; i <= N; i++) {
            input = br.readLine().split(" ");
            for (int j = 1; j <= N; j++) {
                syna[i][j] = fun.apply(input[j - 1]);
            }
        }

        for (int i = 2; i <= 3; i++) {
            input = br.readLine().split(" ");
            for (int j = 0; j < 20; j++) {
                info[i][j] = fun.apply(input[j]);
            }
        }

        bruteForce(1, 2);

        System.out.println(res);
    }
}