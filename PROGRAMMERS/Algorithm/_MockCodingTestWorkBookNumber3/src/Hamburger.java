import java.util.*;
import java.io.*;

class Hamburger {
    public int solution(int[] ingredient) {
        int answer = 0;
        List<Integer> list = new ArrayList<>();

        for (int number : ingredient) {
            list.add(number);

            if (list.size() >= 4) {
                boolean possible = false;

                if (list.get(list.size() - 4) == 1
                        && list.get(list.size() - 3) == 2
                        && list.get(list.size() - 2) == 3
                        && list.get(list.size() - 1) == 1) {
                    possible = true;
                    answer++;
                }

                if (possible) {
                    for (int i = 0; i < 4; i++) {
                        list.remove(list.size() - 1);
                    }
                }
            }
        }

        return answer;
    }
}