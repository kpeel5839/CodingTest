import java.util.*;
import java.io.*;

class IsWar {
    static class War {
        int s;
        int e;
        int w;
        int r;

        War(int s, int e, int w, int r) {
            this.s = s;
            this.e = e;
            this.w = w;
            this.r = r;
        }
    }

    public int solution(int distance, int[][] scope, int[][] times) {
        List<War> list = new ArrayList<>();

        for (int i = 0; i < scope.length; i++) {
            int s = scope[i][0];
            int e = scope[i][1];
            int w = times[i][0];
            int r = times[i][1];

            if (s > e) { // s 가 더크면 swap 을 해준다.
                int tmp = s;
                s = e;
                e = tmp;
            }

            list.add(new War(s, e, w, r));
        }

        Collections.sort(list, (o1, o2) -> o1.s - o2.s); // 오름차순 정렬 (시작 범위)

        for (War war : list) {
            int n = (war.w + war.r) * (war.s / (war.w + war.r)); // 시작 위치를 찾아줌

            for (int i = 1; i <= war.w; i++) {
                n++;

                if (war.s <= n && n <= war.e) {
                    return n;
                }
            }

            n += war.r + 1;

            if (war.s <= n && n <= war.e) {
                return n;
            }
        }

        return distance;
    }
}