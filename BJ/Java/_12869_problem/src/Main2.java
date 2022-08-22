import jdk.jfr.Enabled;

import java.util.*;
import java.io.*;

// 12869 : 뮤탈리스크

/**
 * 예제
 * 3
 * 12 10 4
 */
public class Main {
    static int N;
    static int cnt = 0;
    static int INF = 10000;
    static boolean[] visited = new boolean[10];
    static int[][] attack;
    static HashMap<Equal, Integer> dp = new HashMap<>();
    static HashMap<HashSet<Integer>, HashSet<Integer>> tracking = new HashMap<>();

    static class Equal {
        int[] hp;

        Equal(int[] hp) {
            this.hp = new int[hp.length];

            for (int i = 0; i < hp.length; i++) {
                this.hp[i] = hp[i];
            }
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;

            Equal other = (Equal)o;

            boolean equal = true;

            for (int i = 0; i < this.hp.length; i++) {
                if (this.hp[i] != other.hp[i]) {
                    equal = false;
                }
            }

            return equal;
        }

        @Override
        public int hashCode() {
            return Objects.hash(hp[0], hp[1], hp[2]);
        }
    }

    static void comb(int depth, List<Integer> list) {
        if (depth == N) {
            for (int i = 0; i < list.size(); i++) {
                attack[cnt][i] = list.get(i);
            }
            cnt++;
            return;
        }

        for (int i = 9, j = 0; j < N; i /= 3, j++) {
            if (!visited[i]) {
                visited[i] = true;
                list.add(i);
                comb(depth + 1, list);
                visited[i] = false;
                list.remove(list.size() - 1);
            }
        }
    }


    static int dfs(int[] hp) {
        boolean end = true;
        HashSet<Integer> set = new HashSet<>();
        System.out.println(Arrays.toString(hp));

        for (int i = 0; i < N; i++) {
            if (hp[i] != 0) {
                end = false;
            }

            set.add(hp[i]);
        }

        Equal equal = new Equal(hp);

        if (end) {
            return 0;
        }

        if (dp.containsKey(equal)) {
            return dp.get(equal);
        }

        int res = INF;
        dp.put(equal, res);

//        Loop:
        for (int i = 0; i < attack.length; i++) {
            int[] nHp = hp.clone();

            for (int j = 0; j < attack[i].length; j++) {
                nHp[j] = cal(nHp[j] - attack[i][j]);
            }

            res = Math.min(res, 1 + dfs(nHp));
//            int res2 = 1 + dfs(nHp);

//            HashSet<Integer> trackingSet = new HashSet<>();
//            if (res > res2) {
//                for (int j = 0; j < nHp.length; j++) {
//                    trackingSet.add(nHp[j]);
//                }
//
//                res = res2;
//                tracking.put(set, trackingSet);
//            }
        }

        dp.put(equal, res);
        return dp.get(equal);
    }

    static void track(int[] hp) {
        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < hp.length; i++) {
            set.add(hp[i]);
        }

        System.out.println(set);

        HashSet<Integer> get = tracking.get(set);
        int index = 0;
        int[] nHp = new int[get.size()];

        for (Integer value : get) {
            nHp[index++] = value;
        }

        track(nHp);
    }

    static int cal(int v) {
        return Math.max(0, v);
    }

    static int fac(int n) {
        int sum = 1;

        for (int i = n; i > 0; i--) {
            sum *= i;
        }

        return sum;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_12869_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        attack = new int[fac(N)][N];
        int[] hp = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            hp[i] = Integer.parseInt(st.nextToken());
        }

        comb(0, new ArrayList<>());
        System.out.println(dfs(hp.clone()));
//        track(hp.clone());
    }
}
