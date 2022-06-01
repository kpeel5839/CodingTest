import java.util.*;
import java.io.*;

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        for (int i = 0; i < scoville.length; i++) {
            queue.add(scoville[i]);
        }

        while (true) {
            if (queue.peek() >= K) {
                break;
            }

            if (queue.size() <= 1) {
                answer = -1;
                break;
            }

            answer++;
            int a = queue.poll();
            int b = queue.poll();

            queue.add(a + (b * 2));
        }

        return answer;
    }
}