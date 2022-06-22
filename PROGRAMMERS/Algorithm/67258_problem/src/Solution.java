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

    -- 해맸던 점
    left, right 를
    현재 여기서 1번부터 시작하기 때문에 0 번째 인덱스로 시작하는 나의 입장에서
    값을 변화해줘야 했음
    근데 right + 1 까지 해버리니까 값이 이상했음

    근데 당연하게 생각해보니까 right 는 현재 보석을 다 채우고 그 다음에 하나 더 증가해있는 상태
    즉, 다음 탐색할 보석을 보고 있는 상태이기 때문에 right - 1 인덱스로 표현했을 때, 구간을 나타내게 된다.
    하지만 여기서 1번 부터 시작하기 때문에 right - 1 + 1 이 되어 그냥 right 를 답으로 저장했고

    그리고, 나는 무조건 while 문을 거치고 나면 내가 left 포인터를 증가시킬 때 보석을 빼면 안되는 상황에서는 빼지 않아
    set.size == total 로 고정되어 있을 거라고 생각했었는데

    그게 아니였음
    왜냐하면 right < gems.length 조건에 안맞아, 빠져나온 경우 set.size() == total 이 아닐 수도 있다라는 것
    즉, 보석을 다 채우기 이전에 범위가 끝나버린 상황이 있었던 것임

    그래가지고 이 부분을 보완하기 위해서
    if (right - left + 1 < min) 에서
    if (right - left + 1 < min && set.size() == total) 로 변경하니 (set.size() == total 조건을 추가)
    맞았음

    그래서 썩 어려운 문제는 아니였음
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
            if (right == gems.length) { // right 가 length 에 도달했다면 할 거 다하고 온거임, right 가 여기까지 증가했다? 보석 다 못채움
                break;
            }

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

            // 근데 여기서 set 에서 삭제해주고, HashMap 값 다운시켜야지 right 도 증가하고 그럴 듯
            set.remove(gems[left]); // 삭제해준다.
            map.put(gems[left], map.get(gems[left]) - 1); // 감소, 여기까지 왔다라는 것은 현재 gems[left] 의 value == 1 이라는 것, 그러면 이거 삭제하면 보석 잃는다.
            left++;
        }

        return answer;
    }
}