import java.util.*;
import java.io.*;

class Solution2 {
    int calDis(int[] pos1, int[] pos2) {
        return Math.abs(pos1[0] - pos2[0]) + Math.abs(pos1[1] - pos2[1]); // 둘 사이의 거리를 맨해튼 거리로 계산해서 넘겨준다.
    }

    public int solution(int n, int m, int[][] timetable) {
        int answer = 0;
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
            int maxDistance = (n - 1) * 2;
            int maxPeople = n % 2 == 0 ? (n / 2) * n : ((n / 2) * (n / 2)) + ((n / 2 + 1) * (n / 2 + 1));

            if (max == 2) {
                answer = maxDistance;
            } else if (maxPeople < max) { // N 이 maxPeople 보다 크다면? 초과한 것이니까 이미 거리는 1로 확정이다.
                answer = 1;
            } else {
                // 사람들이 한 방법을 따라해보자.
                // 사람들이 한 방법은 시작점을 for (int i = 0; i < ...) 로 정해서
                // 거기서 부터 시작하고 계속 지금까지 건너온 곳을 체크하는 것이다.
                // 그리고 넣을 수 있다면? 바로 넣어주는 모습을 볼 수도 있었다.
                // 너무나도 쉬운 경우이고, 모든 경우를 고려하지 못할 것 같은데 어떻게 이게 되는지는 모르겠다.
                // 하지만 내가 짠 것보다 훨씬 앞선다.
                // 문제를 풀고서 한번 왜 이렇게 풀었는지, 사람들은 .. 이해해보자.
                for (int distance = maxDistance - 1; distance != 2; distance--) {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            List<int[]> list = new ArrayList<>();
                            list.add(new int[] {i, j});

                            for (int y = i; y < n; y++) {
                                for (int x = 0; x < n; x++) {
                                    if (i == y && x <= j) { // 즉 이말은 이전꺼나 같은 거 라는 소리다.
                                        continue;
                                    }

                                    boolean canPush = true;
                                    for (int[] p : list) {
                                        if (calDis(p, new int[] {y, x}) < distance) {
                                            canPush = false;
                                            break;
                                        }
                                    }

                                    if (canPush) { // 넣기가 가능할 때
                                        list.add(new int[] {y, x});
                                        if (list.size() == max) {
                                            return distance;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                answer = 2;
            }
        }

        return answer;
    }
}