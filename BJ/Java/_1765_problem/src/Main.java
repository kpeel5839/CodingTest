import java.util.*;
import java.io.*;
import java.util.function.Function;

// 1765 : 닭싸움 팀 정하기

/**
 * -- 전제조건
 * 이 문제는 그냥 이거 하나이다.
 *
 * 1. 내 친구의 친구는 내 친구이다.
 * 2. 내 원수의 원수도 내 친구이다.
 *
 * 이럴 때, 두 학생이 친구이면, 같은 팀에 속해있어야 하며, 같은 팀에 속해 있는 사람들끼리는 전부 친구여야 한다라는 가정이 주어지면
 * 닭싸움을 위한 팀 정하기를 할 때, 최대 얼마나 많은 팀이 만들어지는지 구하시오.
 *
 * -- 틀 설계
 * 일단 친구관계를 정립하기 이전에
 * 먼저 원수를 통한 친구관계를 정립해야 할 필요성이 있다.
 *
 * 그렇기 때문에 일단, friend 배열을 만든다. 2차원 배열로 (인접 행렬 방법)
 * 그리고 noFriend 배열도 만든다. 이것도 2차원 배열로
 *
 * 그 다음에, noFriend 를 dfs 를 진행하며, depth 를 2로 제한한다.
 * 일단 원수이면 진행, 끝까지 원수이면 이전 사람과 현재 사람을 친구로서 이어준다.
 * friend 배열에다가 입력한다. (양방향 간선으로)
 *
 * 그 다음에, friend 배열을 순차적으로 돌면서, 친구인 애들을 find, union 을 통해서 집합을 만들고
 * 마지막으로 parent 배열을 돌면서, 집합이 몇개가 형성되었는지 판단한다.
 *
 * HashSet 을 통해서, 이미 나온 친구 집합이면, cnt++ 을 하지 않고, 이미 나온 친구 집합이 아니라면 cnt++ 을 해준다.
 * visited 배열을 만들어서 할 수도 있지만 귀찮으니까 HashSet 으로 하자.
 *
 * -- 결과
 * 뭐야 이거 골드 1 맞나?
 * 난이도 조작한 수준인데..
 *
 * 그냥 설계 그대로 진행했음.
 * 해맨 점도 없고, 별다른 특이사항도 없는 문제였음
 *
 * 이 문제의 핵심은 이러했음
 * 1. 원수의 원수를 친구로 만든다. (noFriend 를 이용해서 friend 를 갱신한다)
 * 2. 두 명이 친구이면 무조건 같은 팀이여야 하고, 같은 팀은 모두 친구여야 하니까 그냥 너무 분리집합 문제였음
 * 3. 그래서 친구 그룹을 만들어서 친구 관계를 돌면서 find, union 을 통해서 친구 그룹을 형성시킨다.
 * 4. HashSet 을 이용해서, 그룹의 개수를 세어준다.
 */
public class Main {
    static int N;
    static int M;
    static int ans = 0;
    static int[] parent;
    static boolean[][] friend;
    static boolean[][] noFriend;

    static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            parent[a] = find(parent[a]); // 반환 받은 것을 본인 부모로 갱신
            return parent[a];
        }
    }

    static void union(int a, int b) {
        parent[b] = a; // a 를 대표 원소로 만들어준다.
    }

    static void dfs(int sta, int cur, int cnt) {
        if (cnt == 2) {
            friend[sta][cur] = true;
            friend[cur][sta] = true; // 간선 추가
            return;
        }

        for (int i = 1; i <= N; i++) {
            if (noFriend[cur][i] && i != sta) { // noFriend 이면, depth 가 최대 2이기에, 왔던길로만 안돌아가면 됨
                dfs(sta, i, cnt + 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        N = fun.apply(br.readLine());
        M = fun.apply(br.readLine());
        friend = new boolean[N + 1][N + 1];
        noFriend = new boolean[N + 1][N + 1];
        parent = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < M; i++) {
            String[] input = br.readLine().split(" ");
            int a = fun.apply(input[1]);
            int b = fun.apply(input[2]); // 연결될 a, b 원소

            if (input[0].equals("E")) { // 원수
                noFriend[a][b] = true;
                noFriend[b][a] = true; // 양방향으로
            } else { // 친구
                friend[a][b] = true;
                friend[b][a] = true;
            }
        }

        for (int i = 1; i <= N; i++) { // dfs 를 진행해준다. (noFriend를 이용해서 friend 배열을 갱신)
            dfs(i, i, 0);
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (friend[i][j]) { // 둘이 친구이면
                    int a = find(i);
                    int b = find(j); // i, j 가 친구이니까, 친구 집합이 아니라면 union 해준다.

                    if (a != b) {
                        union(a, b);
                    }
                }
            }
        }

        HashSet<Integer> set = new HashSet<>();

        for (int i = 1; i <= N; i++) {
            int a = find(i); // 부모 집합

            if (!set.contains(a)) { // 처음 보는 집합이면
                set.add(a);
                ans++;
            }
        }

        System.out.println(ans);
    }
}
