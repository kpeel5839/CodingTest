import java.util.*;
import java.io.*;

/**
 * 9
 * 0
 * 12345678
 * 1
 * 2
 * 0
 * 0
 * 0
 * 0
 * 32
 */
public class Main {
    static class MinHeap {
        List<Integer> list = new ArrayList<>();

        MinHeap() {
            list.add(0);
        }

        public void insert(int v) {
            // list 끝에다가 정수 삽입
            list.add(v);
            // 끝에 위치 얻어낸다.
            int c = list.size() - 1;
            int p = c / 2;
            // parent 가 0 이거나, 혹은 parent 보다, 본인이 클 때, 즉 작지 않을 때 멈춘다.

            while (true) {
                if (p == 0 || list.get(p) <= list.get(c)) {
                    break;
                }

                // 이 경우까지 왔다라는 것은 바꿔줭 ㅑ한다.
                // swap 을 진행해주고 p, c 를 바꿔주자.
                int tmp = list.get(p);
                list.set(p, list.get(c));
                list.set(c, tmp);

                c = p;
                p = c / 2;
            }
        }

        public int delete() {
            // delete 연산이 들어오면 일단 list size 가 안된다면 0을 반환해준다.
            if (list.size() == 1) { // list 에 0이 기본적으로 들어있기 때문에, (index 연산을 위해) 이 경우 비어있다고 봐야 한다.
                return 0;
            }

            // 첫번째 것을 제거해주고, 가장 마지막에 있는 아이를 top 으로 가져와준다.
            int top = list.get(1);
            list.set(1, list.get(list.size() - 1));
            list.remove(list.size() - 1); // 삭제까지 진행해준다.

            int c = 1;
            // 이제 while 문으로 더 이상 자식이 없거나, 조건을 만족할 때 까지 재귀적으로 바꿔준다.
            while (true) {
                int left = c * 2;
                int right = c * 2 + 1;

                // 완전 이진트리이기 때문에, left 가 없다면? 아얘 없는 것이다.
                if (list.size() - 1 < left) {
                    break;
                }

                int minPos = left;

                if (right <= list.size() - 1 && list.get(right) < list.get(minPos)) {
                    minPos = right;
                }

                if (list.get(minPos) < list.get(c)) {
                    int tmp = list.get(c);
                    list.set(c, list.get(minPos));
                    list.set(minPos, tmp);
                    c = minPos;
                } else {
                    break;
                }
            }

            return top;
        }

    }
    public static void main(String[] args) throws IOException{
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1927_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        MinHeap queue = new MinHeap();

        for (int i = 0; i < N; i++) {
            int v = Integer.parseInt(br.readLine());

            if (v == 0) {
                sb.append(queue.delete()).append("\n");
            } else {
                queue.insert(v);
            }
        }

        System.out.print(sb);
    }
}
