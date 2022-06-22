import java.util.*;
import java.io.*;

class Solution {
    /*
    내 생각에 이 문제는 포인터를 이용해서 해결해야 할 것 같다.
    순서대로 진행하면서 O(N) 의 시간에 해결해야 하는 문제로 보인다.
    
    이제, 그러면 이런식으로 가정을 할 수 있다
    일단은 모든 다이아가 수집될때까지 포인터를 증가시켜야 한다.
    
    해답이 떠올랐다.
    HashMap 과 HashSet 을 사용하면 되지 않을까?
    
    일단 입력받은 것을 쭉 돌면서 HashSet 에다가 입력한다 그러면 총 보석의 개수를 알 수 있다.
    현재로서는 보석이 어떤 보석인지는 전혀 중요하지 않다.
    
    그렇게 사이즈를 저장해놓고서
    그냥 0 번째부터 쭉 포인터를 오른쪽으로 옮겨가면서 저장해간다.
    
    그러면서 set 사이즈가 사이즈와 같아지면 저장한다.
    무조건으로 set 사이즈가 같은 경우를 100 번 유지하고
    
    무조건 그 사이즈가 유지되는 상태를 유지하기 위해서
    set 사이즈가 같아진 순간 왼쪽 포인터가 가르키는 값을 HashMap 에서 찾아본다
    
    그래서 value 가 1이 아니다? 그러면 왼쪽 포인터도 증가시키면서
    계속 답을 바꿔간다.
    
    절대로 min 사이즈가 같은 경우 답을 교체해서는 안된다.
    
    그렇게 해서 왼쪽 포인터를 증가시키지 못하는 경우에서는 다시 오른쪽 포인터를 증가시키기 싲가한다.
    근데, 만일 오른쪽 포인터가 사이즈를 넘어간 경우 더 이상 반복을 끝내면 된다.
    오른쪽 포인터가 사이즈를 넘어갔다라는 의미는 더 이상 왼쪽 포인터도 증가시키지 못했기 때문에 사이즈를 증가시키지 못했던 것이다.
    */
    public int[] solution(String[] gems) {
        int[] answer = new int[2];
        HashMap<String, Integer> map = new HashMap<>();
        HashSet<String> set = new HashSet<>();

        for (int i = 0; i < gems.length; i++) {
            set.add(gems[i]);
        }

        int total = set.size(); // set 에 도달하여야 할 사이즈
        set = new HashSet<>(); // 다시 선언하면서 비워준다.
        int left = 0;
        int right = 0; // 왼쪽, 오른쪽 포인터를 둔다.
        int min = Integer.MAX_VALUE; // 구간의 거리를 min 변수에다가 저장한다.

        while (true) {
            while (set.size() != total && right < gems.length) {
                set.add(gems[right]);

                if (map.containsKey(gems[right])) { // 이미 key 를 가지고 있으면
                    map.put(gems[right], map.get(gems[right]) + 1); // 하나를 증가시켜준다.
                } else {
                    map.put(gems[right], 1); // 없는 경우는 1로 생성을 해준다.
                }

                right++; // 포인터 증가
            }

            // 이제 set size 만큼 담았으니까 Left 를 올리면서 적절한 사이즈를 찾아야 한다.
            while (left < right) { // 그냥 조건만 걸어놓자, 이렇지 않은 경우가 없기는 한데
                /*
                현재 left 에 있는 것을 빼도 되는지 확인한다.
                만약 확인했는데 해당 값이 1 이다? 그러면 left 증가 못시킴
                
                근데 만약 해당 값이 1이 아니다 맘대로
                줄인다 그대신 1이 되면 break; 해준다.
                */
                int cnt = map.get(gems[left]); // 현재 남은 보석

                if (cnt == 1) { // cnt 가 1이면 못뺀다.
                    break;
                } else { // 그렇지 않으면 1 감소 시키고 left 증가시킨다.
                    map.put(gems[left], cnt - 1);
                }

                left++;
            }

            if (right - left + 1 < min && set.size() == total) { // 구간의 거리가 min 보다 작은 경우에만 answer 로 저장해준다.
                answer[0] = left + 1;
                answer[1] = right;
                min = right - left + 1;
            }

            if (right == gems.length) { // right 가 length 에 도달했다면 할 거 다하고 온거임, right 가 여기까지 증가했다? 보석 다 못채움
                break;
            }

            // 근데 여기서 set 에서 삭제해주고, HashMap 값 다운시켜야지 right 도 증가하고 그럴 듯
            set.remove(gems[left]); // 삭제해준다.
            map.put(gems[left], map.get(gems[left]) - 1); // 감소, 여기까지 왔다라는 것은 현재 gems[left] 의 value == 1 이라는 것, 그러면 이거 삭제하면 보석 잃는다.
            left++;
        }

        return answer;
    }
}