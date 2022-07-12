import java.util.*;
import java.io.*;

class Solution {
    /*
    풀이는 정해진 것이 아님으로 내가 생각한 풀이 방법은 이러하다.
    min, max priorityQueue 를 두개를 선언해놓고서
    하나는 오름차순, 하나는 내림차순으로 정렬을 진행한다.
    
    (오름 차순 = min
    내림 차순 = max)
    
    그러면 삭제 연산이 문제이다.
    그래서 이 부분을 해결하기 위해서 HashSet 을 이용할 것이다.
    
    명령이 들어오게 되면 heap 에서 하나를 제거 한다.
    D -1 인지
    D 1 인지 확인해서
    D -1 이면 min poll
    D 1 이면 max poll 해주고
    
    이거를 해주면서 무조건적으로 어떠한 것을 확인해줄 것이냐면
    일단 지금 현재 뽑은 것이 HashSet 에 존재하는 지 확인한다.
    만일 존재하지 않는다면 그냥 끝내면 되고
    
    만일 HashSet 에 존재한다라면 연산을 또 진행해주어야 함
    즉, HashSet 에서 나오지 않거나, 혹은 empty 할 떄까지 반복해서 빼면 된다.
    
    그래서 HashSet 에다가 저장하는 형식은 String 형태의
    value + " " + index (삽입 명령의 index) 를 해줄 것이다.
    이렇게 하려면 어떠한 것이 필요하냐?

    PriorityQueue 에다가 넣을 떄에도 
    int[] 를 넣어서 [0] 번쨰 내용으로 정렬을 진행하는 것이 중요하다.
    
    그래서 이런식으로 진행하면 최종적으로 연산을 다 진행한다음, min 과 max 에서 하나씩 빼면 정답을 맞출 수 있을 것 같다.

    -- 결과
    설계대로 잘 되었음
    */

    public int[] solution(String[] operations) {
        PriorityQueue<int[]> min = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        PriorityQueue<int[]> max = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        HashSet<String> set = new HashSet<>();

        for (int i = 0; i < operations.length; i++) {
            String[] input = operations[i].split(" ");

            if (input[0].equals("I")) { // 삽입을 해준다, min 과 max 에        
                min.add(new int[] {Integer.parseInt(input[1]), i});
                max.add(new int[] {Integer.parseInt(input[1]), i}); // 입력                
            } else { // 빼준다, 다음 값에 따라서
                int judge = Integer.parseInt(input[1]);
                if (judge == 1) { // 최댓값을 삭제 하는 경우
                    while (!max.isEmpty()) {
                        int[] value = max.poll();
                        String check = value[0] + " " + value[1];

                        if (!set.contains(check)) { // 없다면 빼주고 set 에다가 등록해주고 break
                            set.add(check);
                            break;
                        }
                    }
                } else { // 최솟값을 삭제하는 경우
                    while (!min.isEmpty()) {
                        int[] value = min.poll();
                        String check = value[0] + " " + value[1];

                        if (!set.contains(check)) {
                            set.add(check);
                            break;
                        }
                    }
                }
            }
        }

        while (!min.isEmpty()){
            int[] value = min.peek();
            String check = value[0] + " " + value[1];

            if (!set.contains(check)) { // 현재 peek 에 있는 값이 set 에 없음, 즉 삭제된 적이 없다.
                break;
            }

            min.poll(); // 이미 삭제된 것이니 삭제
        }

        while (!max.isEmpty()) { // 이미 삭제된 것들을 다 제거해주기 위해서
            int[] value = max.peek();
            String check = value[0] + " " + value[1];

            if (!set.contains(check)) { // 현재 peek 에 있는 값이 set 에 없음, 즉 삭제된 적이 없다.            
                break;
            }

            max.poll(); // 이미 삭제된 것이니 삭제
        }

        int[] answer = new int[2];

        if (!max.isEmpty()) {
            answer[0] = max.poll()[0];
        } else {
            answer[0] = 0;
        }

        if (!min.isEmpty()) {
            answer[1] = min.poll()[0];
        } else {
            answer[1] = 0;
        }

        return answer;
    }
}