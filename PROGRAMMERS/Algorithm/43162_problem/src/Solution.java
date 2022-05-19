import java.util.*;
import java.io.*;
/*
그냥 딱봐도 find union 쓰는 문제

자꾸 find, union 문제에서 헷갈릴 때가 있는데,
자꾸 find(i) 를 해서 최종 부모를 찾아야 하는데 단편적으로 부모를 보고 결정하게 함

지금도 hashSet.add(parent[i]); 이러면 같은 집합이여도, 본인 최종 조상이 아니기에 다르게 나올 수가 있음
그래서 hashSet.add(find(i)); 이렇게 바꿔줬음 (최종 조상을 보고 판단할 수 있도록)

그랬더니 바로 맞음
*/
class Solution {
    static int[] parent;
    static int N;
    static int answer;

    static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            parent[a] = find(parent[a]);
            return parent[a];
        }
    }

    static void union(int a, int b) {
        parent[b] = a;
    }

    public int solution(int n, int[][] computers) {
        // computer[i][i] 즉, 본인은 무조건 1이라고 생각
        answer = 0;
        N = n;
        parent = new int[n];

        for (int i = 0; i < N; i++) {
            parent[i] = i; // 부모를 본인으로 설정
        }

        for (int i = 0; i < N; i++) { // 배열 전체를 순환
            for (int j = 0; j < N; j++) {
                if (i != j && computers[i][j] == 1) { // 배열을 순환하면서, 본인을 제외하고 다른 배열과 연결된 간선이 있다면, 집합을 동일하게 만든다.
                    int a = find(i);
                    int b = find(j);

                    if (a != b) { // 집합을 동일하게 만들어준다.
                        union(a, b);
                    }
                }
            }
        }

        HashSet<Integer> hashSet = new HashSet<>(); // 서로 다른 집합들 개수 세주는데, 세기 귀찮으니까 그냥 set으로 읽고 사이즈 반환
        for (int i = 0; i < N; i++) {
            hashSet.add(find(i));
        }

        answer = hashSet.size();
        return answer;
    }
}