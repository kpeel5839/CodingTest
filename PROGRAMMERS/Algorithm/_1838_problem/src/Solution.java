import java.util.*;
import java.io.*;

class Solution {
    boolean success;
    int N;
    int side;
    HashMap<Integer, int[]> pos;
    int[][] map;

    void bruteForce(int depth, int dis, int cnt, StringBuilder sb) {
        if (success) {
            return;
        }

        if (depth == side * side) { // 끝에 도달하는 경우
            return;
        }

        // mapPrint(map, sb);

        if (cnt == N) {
            success = true;
            return;
        }

        int[] add = new int[2];
        add[0] = depth / side;
        add[1] = depth % side; // y, x 값을 순서대로 구해줬음

        for (int i = depth; i < side * side; i++) {
            // depth 를 선택해서 넘어가면 pos 에다가 넣어준다.
            boolean execute = true;

            for (Integer key : pos.keySet()) { // 이전에 선택된 애들과 거리가 dis 보다 작은 경우 선택하지 않는다.
                int[] p = pos.get(key);

                if (calDis(p, add) < dis) {
                    execute = false;
                    break;
                }
            }

            if (!execute) {
                continue;
            }

            pos.put(i, add); // i 를 선택했으니까
            // map[add[0]][add[1]] = 1;
            bruteForce(i + 1, dis, cnt + 1, sb); // i 를 선택했으니까
            pos.remove(i); // 다시 돌아온 경우 i 를 삭제해야 함
            // map[add[0]][add[1]] = 0;

            bruteForce(i + 1, dis, cnt, sb);
        }
    }

    int calDis(int[] pos1, int[] pos2) {
        return Math.abs(pos1[0] - pos2[0]) + Math.abs(pos1[1] - pos2[1]); // 둘 사이의 거리를 맨해튼 거리로 계산해서 넘겨준다.
    }

    public int solution(int n, int m, int[][] timetable) {
        int answer = 0;
        side = n;
        // 이 문제의 경우 그냥 브루트 포스로 풀어야 할 것 같다.
        // 일단 동시간대에 겹치는 손님들을 구하자.
        // 배열은 600 ~ 1320 의 값을 담아야 하니
        // 총 1320 - 600 + 1 의 값을 가져야 한다.
        // 0 ~ 720 의 인덱스를 가져야 하기 떄문에
        // 일단 손님들의 수를 기록해준뒤, 거기서 가장 많은 인원이 있는 명수를 뽑으면 된다.
        int[] time = new int[1320 - 600 + 1];

        for (int i = 0; i < timetable.length; i++) {
            for (int j = timetable[i][0] - 600; j <= timetable[i][1] - 600; j++) {
                time[j]++; // 기록해준다.
            }
        }

        int max = 0;

        for (int i = 0; i < time.length; i++) {
            max = Math.max(max, time[i]);
        }

        if (max == 1) { // 1 인 경우에는 겹치는 손님이 없으니 0을 출력한다.
            answer = 0;
        } else {
            // 자 이제 이 경우에 알고리즘을 짜주어야 한다.
            // 어제 한참을 고민한 끝에 수식을 얻어냈다.
            // max = n 이라고 가정했을 때, 최대로 가능한 거리는 ? (n - 1) * 2 라는 것을 알 수 있었다. (왜냐 대각선으로 n - 1 번만큼 가야지 만나니까, 다 정사각형이라서 가능)
            // 그리고 max 가 홀수인 경우는 (n / 2) * (n / 2) + (n / 2 + 1) * (n / 2 + 1), 짝수인 경우에는 (n / 2) * n 보다 커지면? 거리가 1로 고정된다라는 사실을 알 수 있었다.
            // 그러면 위와 같이 2 인 경우에는 위의 수식을 통해서 결정, 만일 굉장히 큰 경우 즉, 홀수인 경우 짝수인 경우를 나누고, 구한 값을 초과하게 되면? 1을 반환하면 된다.
            // 이제 나머지 값을 처리해주어야 한다.
            // 이 경우는 (n - 1) * 2 미만의 값부터, 3 까지의 값을 넣어주면서 구해주면 된다.
            // 2는 구할 필요가 없다 3 까지도 안나오면 무조건 2인 거니까 그 외의 예외 사항들은 위에서 걸러줬음
            // bruteForce 로 구하다가 해당 거리로 해당 인원이 생성 가능한 경우 success = true 로 만들어서, 모든 함수를 종료한다.
            N = max;
            success = false; // 초기화

            int maxDistance = (n - 1) * 2;
            int maxPeople = n % 2 == 0 ? (n / 2) * n : ((n / 2) * (n / 2)) + ((n / 2 + 1) * (n / 2 + 1));

            if (N == 2) {
                answer = maxDistance;
            } else if (maxPeople < N) { // N 이 maxPeople 보다 크다면? 초과한 것이니까 이미 거리는 1로 확정이다.
                answer = 1;
            } else {
                StringBuilder sb = new StringBuilder();
                // System.out.println(max);

                for (int i = maxDistance - 1; i != 2; i--) {
                    pos = new HashMap<>();
                    map = new int[n][n];
                    bruteForce(0, i, 0, sb); // 해당 distance 로 만들어라 조합을 이라는 의미 (depth / n == y 를 의미하고 depth % n == x 를 의미한다.)

                    if (success) { // 성공한 경우 해당 distance 가 정답임
                        answer = i;
                        break;
                    }
                }

                if (!success) {
                    answer = 2;
                }

                System.out.println(sb);
            }
        }

        return answer;
    }

    void mapPrint(int[][] map, StringBuilder sb) {
        sb.append("next-----------------\n");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                sb.append(map[i][j] + " ");
            }
            sb.append("\n");
        }
    }
}