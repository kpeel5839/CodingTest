import java.util.*;
import java.io.*;

class Solution {
    public int solution(int[] priorities, int location) {
        int answer = 0;
        int[] numberCount = new int[10];
        Queue<int[]> queue = new LinkedList<>(); // queue 에다가 순서대로 집어넣을 것이다.\

        for (int i = 0; i < priorities.length; i++) {
            numberCount[priorities[i]]++; // 요거 잘못해서 살짝 해맸음
            queue.add(new int[] {priorities[i], i}); // 실제 값과, 원래 번째수를 집어넣음, 여기서 제대로 빠지는 것을 보고, location 이 빠졌는지 확인할 것이다.
        }

        while (!queue.isEmpty()) { // queue 가 빌때까지 진행한다.
            int[] number = queue.poll();
            boolean pollAble = true; // poll 이 가능한지 확인하는 것이다.

            for (int i = number[0] + 1; i < numberCount.length; i++) {
                if (numberCount[i] != 0) {
                    pollAble = false; // 아직 안빠진놈 있으면 poll 불가능
                }
            }

            if (pollAble) { // poll 이 가능할 때
                answer++; // 하나 빠진 거니까 answer++ 해줌
                numberCount[number[0]]--; // 이거 빠졌으니까 빼준다.
                if (number[1] == location) { // number[1] == location 이면 끝내도 됨
                    break;
                }
            } else { // poll 이 불가할 때
                queue.add(number); // 다시 추가해준다.
            }
        }

        return answer;
    }
}