import java.util.*;
import java.io.*;

class Solution {
    static PriorityQueue<Stone> queue = new PriorityQueue<>((o1, o2) -> o2.value - o1.value);

    static class Stone {
        int value;
        int index;

        Stone(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    public int solution(int[] stones, int k) {
        int ans = Integer.MAX_VALUE;
        int[] arr = new int[stones.length + 1];
        arr[0] = Integer.MAX_VALUE;

        for (int i = 0; i < stones.length; i++) {
            arr[i + 1] = stones[i];
        }

        for (int i = arr.length - 1; i != -1; i--) {
            if (queue.size() >= k) { // k 이상일 때에만 실행                            
                while (i + k < queue.peek().index) { // queue.peek 의 index 가 i + k 를 계속 초과한다면 poll 해서 빼줌
                    queue.poll();
                }

                if (arr[i] > queue.peek().value) { // 현 탐색하는 애가 얘보다 큰 경우, 현 탐색하는 애보다 크지 않으면 그냥 집어넣음
                    ans = Math.min(ans,queue.poll().value);
                }
            }

            queue.add(new Stone(arr[i], i));
        }

        return ans;
    }
}