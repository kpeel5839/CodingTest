import java.util.*;
import java.io.*;

class Solution {
    /*
    그냥 이 문제는 평범하게 dfs 로 가다가
    경로가 막히면 다른 지점으로 돌려주면 될 것 같은데
    문제는 예제와 같은 3이 연속으로 있는 경우이다.
    
    여기서는 바로 이전의 위치를 조정해주게 되는데
    오히려 역으로 진행해야 할 것 같다.
    
    역으로 풀어나가야지 조금 더 쉬울 것 같은게
    그래야지 다음 것이 또 틀렸을 때, 계속해서 진행할 수 이싿.
    
    그러면 자동적으로 퀸 문제처럼 백트래킹이 들어가지 않을까?
    
    그래서 총 내 설계는 이러하다.
    쭉 원래 주어진 위치대로 가다가, 막히면 해당 두 정점과 연결된 위치로 이동하는 것이다.
    하지만 여기서 중요한 것은 막힌 위치, 즉 다음 곳으로 가지 못하는 위치의 다음 위치와
    이전 위치를 연결해주는 것이 중요하다라는 것이다.
    
    만일 그것도 잇지 못하면? 목적 지점까지 이을 수 있도록 재귀적으로 파고 들어가야 할 것 같다.
    
    근데 이렇게 하면 너무 말이 안될 것 같다.
    
    일단 확실한 것, gps_log 의 시작 거점과 도착 거점이 변경될 수 없다라는 사실이다.
    
    즉, 그 중간은 무조건 바뀔 수 있다라는 것이다.
    모두다.
    하지만, 정상적인 경로를 굳이 꼴 필요는 없다.
    
    그렇다라는 것은 올바른 경로가 아닌 것이 확인이 되었을 시에 경로를 수정하면 된다라는 것이다.
    예제는 총 4, 5 번을 바꿔보는 선택을 했다.
    
    어떠한 근거로 이러한 선택을 할 수 있었을까?
    일단, 여기서 뚜렷히 보이는 사실은 4 번을 바꾸거나 5번을 바꾸게 되면 올바른 경로가 되었다라는 것이다 굳이 다른 경로들을 바꿀 필요가 전혀없었다.
    
    그리고 경로가 막히는 경우 수정을 진행했다라는 것이다.
    3 -> 4 번으로 가는 경우는 막히지 않았다.
    하지만 4 -> 5 번으로 가는 경우가 막혔다.
    
    그래서 여기서는 4 번과 5 번을 바꿔보는 선택을 했다.
    처음에는 4 번을 바꿔보고
    그 다음은 5번을 바꿔보았다.
    
    분명히 이러한 메커니즘이 필요할 것 같다.
    그리고 시간안에 통과하기 위해서는 분명하게 막히는 경우에만 진행해야 할 듯하다.
    
    하지만 그렇다라면 너무 문제가 쉬워지게 된다.
    분명히 무엇인가 더 있을 것 같다.
    
    막힌 지점으로 부터 수정을 한다라고 가정했을 때, 여기서 바꾼다고 해결되지 않아 depth가 깊어진다라고 하면
    무조건 수정을 많이할 수 밖에 없다.
    
    가장 수정을 적게 하는 부분은 불가능한 경로가 주어졌을 때 거기서 마무리 짓는 것이다.
    그렇지 않으면 이전으로 이전으로 넘어가게 되어도
    
    현재 충돌난 지점이 변하지 않으면 전혀 의미가 없다.
    
    그렇기 때문에 현재 충돌이 난 곳의 해결을 중점으로 봐야할 것 같다.
    하지만 경로가 아얘 잘못되었을 수도 있다.
    예를 들어 4개가 주어졌는데
    1 2 4 7 이라고 해보자.
    그러면 4번만에 7에 도달해야 하기 때문에
    1 3 5 7 로 변경하는 방법 밖에 없다.
    
    그러면 여기서 어떤식으로 변경하게 될까?
    일단 4 에서 7로 갈 수 없다.
    그래서 2 에서 3으로 갈 수 있는 경로가 있기 때문에
    4 -> 3 으로 바꿔본다.
    
    그래도 도달할 수 없다.
    그러면 오히려 더 역으로 가서 1 -> .. 를 바꾼다.
    1 -> 3 으로 바꾼다.
    그러면 1 -> 3 -> 4 -> 7 이다.
    그러면 4를 또 5로 바꾸면 해결할 수 있다.
    
    그러면 수정을 2번 진행하고 경로를 수정할 수 있다.
    
    이 메커니즘을 코드로 어떻게 풀어낼 수 있을까?
    
    일단 경로에 이상이 생긴 부분에서 해결해야 할 것 같다.
    1 -> 2 -> 4 -> 7 은 
    더 이상 갈 수 없는 위치가 7이다.
    그러면 4 를 변경할 수도 7을 변경할 수도 있다.
    
    하지만 7은 맨 마지막 부분이다. 위의 규칙에 따라 gps_log 는 시작과 끝 지점은 변경할 수 없다.
    그렇기 때문에 2가 갈 수 있는 위치
    즉, 4번 위치에 어떤 숫자들이 올 수 있는지 봐야 한다.
    그 다음에 또 바꾼뒤에 진행을 한다.
    
    또 안됐다.
    
    그러면 또 재귀적으로 거꾸로 돌아가서 2번의 위치를 바꾸어야 한다.
    2번의 위치는 1번이 정할 수 있다.
    
    그러면 이런식으로 식이 정립될 것 같다.
    일단 막힌다 그러면 본인 다음것을 본인이 갈 수 있는 위치로 조정을 한다.
    
    그 다음에 안되면 재귀적으로 자꾸 타고 내려가면서 계속해서 수정해본다.
    */
    static int ans;
    static int[][] graph;

    static boolean dfs(int depth, int cnt, int[] gpsLog) {
        if (depth == gpsLog.length - 1) { // 목적지에 도달한 것임
            ans = Math.min(ans, cnt);
            return true; // 성공의 의미
        }

        int now = gpsLog[depth]; // 현재 정점

        if (graph[now][gpsLog[depth + 1]] != 1) { // 다음이 연결되지 않으면, false 를 반환해준다.
            return false;
        }
        
        /*
        진행하면서 막히는 구간이 있다?
        그러면 본인의 다음 구간을 변경한다.
        근데, 만일 내가 depth == gps.Log.length - 2 이다?
        
        그러면 다음 지점이 gpsLog 이니까 변경 못한다.
        그냥 실패의 의미로 return false 을 해준다.
        
        그래서 이전으로 넘어가서 만일 실패하게 되면 재귀적으로 그 행위를 반복해주면 될 것 같은데...
        */
        boolean res = dfs(depth + 1, cnt, gpsLog);

        if (!res && depth != 0) { // 이게 트루이면 False 이면 다시 진행하는 것이다.
            if (depth != gpsLog.length - 2) { // 마지막 것은 못바꾸니까
                for (int i = 1; i < graph.length; i++) {
                    if (graph[now][i] == 1 && gpsLog[depth + 1] != i) { // i 가 다음거랑 같으면 진행할 필요 x                    
                        int origin = gpsLog[depth + 1];

                        gpsLog[depth + 1] = i; // 변경하고
                        res = dfs(depth + 1, cnt + 1, gpsLog); // 보냄                         
                        gpsLog[depth + 1] = origin; // 복구

                        if (res) {
                            return res;
                        }
                    }
                }
            }
        }

        return res; // 여기까지 왔다라는 것은 실패한 것임
    }

    public int solution(int n, int m, int[][] edgeList, int k, int[] gpsLog) {
        ans = Integer.MAX_VALUE;
        graph = new int[n + 1][n + 1];

        for (int i = 1; i < graph.length; i++) { // 본인이 본인으로 갈 수 있음
            graph[i][i] = 1;
        }

        for (int i = 0; i < edgeList.length; i++) {
            int a = edgeList[i][0];
            int b = edgeList[i][1];

            graph[a][b] = 1;
            graph[b][a] = 1;
        }

        dfs(0, 0, gpsLog); // 시작 지점 gps[0] 으로 줌, 그리고 수정횟수도 0으로 준다.
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}