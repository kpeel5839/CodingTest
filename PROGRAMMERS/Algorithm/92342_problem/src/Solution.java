import java.util.*;
import java.io.*;

class Solution {
    /*
    일단 어피치가 더 유리하게 흘러간다.
    그리고 라이언이 똑같은 발 수를 쐈을 때,
    어피치를 이길 경우의 수는 많이 존재한다.
    
    그리고 가장 큰 점수차로 결정해야 한다.
    만약 이기는 경우가 전혀 없다라면 -1 을 출력해야 한다.
    그럼 그냥 n 개를 전부 배분해보면 된다.
    
    그러면 오래 걸리더라도 확실히 정답이 나올 것이다.
    순서는 상관이 없다.
    현재 남은 점수를 각자 배분만 할 수 있으면 된다.
    
    그리고서 결정된 배열을 가지고 어떤 놈이 승리했는지만 체크하면 된다.

    해맸던 이유는..
    조건문 잘 못 처리하고 있었음
    lion[i] != 0 이라고 했었어야 했었는데
    answer 도 마찬가지고
    근데 그게 아니라, lion[i] == 1 ... 이런식으로 진행해서
    자꾸 이상하게 답이 갱신되었었음
    res, diff 이런 것들은 다 연산 잘 되었었는데 나머지가 이상했었음
    */
    static int res = 0; // 라이언이 우승한 경우, 여기다가 몇점차로 우승하였는지 기록해놓는다. 
    static int[] lion; // 계속 여기다가 집어넣으면서 진행
    static int[] answer;
    static int count = 0;

    static void check(int[] info) { // 라이언이 이겼는지 어피치가 이겼는지 판단을 하여서, 이긴 경우를 저장할 수 있어야 함
        int score = 10;
        int appeachScore = 0;
        int lionScore = 0; // 각각의 score 를 저장할 것이다.

        for (int i = 0; i < info.length; i++) { // lion 과 info 를 비교할 것이다.
            // 총 3가지의 경우가 존재한다.
            // 1. 둘다 쏘지 않은 경우
            // 2. 어피치 라이언이 같은 개수를 쏜 경우
            // 3. 그렇지 않다면 누군가가 이긴 경우이다.
            if (!(info[i] == 0 && lion[i] == 0)) { // 둘다 못 맞춘 경우가 아니면
                if (info[i] == lion[i]) { // 이 경우는 appeachScore 를 증가
                    appeachScore += (score - i);
                } else if (info[i] < lion[i]) { // 라이언이 이긴 경우
                    lionScore += (score - i);
                } else { // 라이언이 진 경우
                    appeachScore += (score - i);
                }
            }
        }

        int diff = lionScore - appeachScore; // 점수차이

        if (diff > 0) { // 라이언이 이긴 경우이다.
            if (res < diff) { // 그냥 이긴 경우, 다 갱신 해주어야 한다, 이 경우는 다 갱신해주어야 함이 변함이 없음
                res = diff;
                answer = lion.clone();
            } else if (res == diff) { // res == diff 인 경우 더 낮은 점수를 많이 쏜놈을 찾아야 한다.        
                for (int i = lion.length - 1; i != -1; i--) { // 이 경우 거꾸로 거슬러 올라가면서, 비교 해야한다.
                    // 그럼 이 경우가 있음 res 가 먼저 1이 나온 경우
                    // 혹은 lion 이 먼저 1이 나온 경우
                    // 혹은 둘다 1이 같이 나온 경우 (개수를 비교)
                    if ((answer[i] > lion[i]) || (answer[i] < lion[i])) { // 이 경우는 무조건 끝난다 여기서
                        if (answer[i] < lion[i]) { // lion 이 더 많이 쏜 경우
                            answer = lion.clone();
                        }
                        break;
                    }
                }
            }
        }
    }

    static void dfs(int depth, int remain, int[] info) { // dfs 를 실시하면서, lion 에다가 채워넣어야 함
        if (depth == lion.length) { // 끝까지 도달한 경우            
            if (remain == 0) {
                check(info);
            }
            return;
        }

        for (int j = 0; j <= remain; j++) { // 현재 depth를 선택할 수도 선택하지 않을 수도 있음
            lion[depth] = j;
            dfs(depth + 1, remain - j, info);
            lion[depth] = 0;
        }
    }

    public int[] solution(int n, int[] info) {
        lion = new int[info.length]; // 선언
        dfs(0, n, info); // 아직 아무 발을 쏘지 않은 경우

        if (res == 0) { // 실패한 경우
            answer = new int[1];
            answer[0] = -1;
        }

        return answer;
    }
}