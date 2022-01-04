import java.util.*;
import java.io.*;

// 21609 : 상어 중학교
/*
-- 전제조건
1. 그냥 상하좌우 칸을 인접한 칸으로 표현한다.
2. -1은 검은색 블록 , 무지개 블록은 0 그리고 나머지 양수는 그냥 일반블록이고 각 숫자가 색깔을 의미한다.
3. 그리고 M이 주어지는데 M가지의 색상이 있다 , 즉 M이 주어지면 그 이하의 숫자들이 주어진다는 것이다.
4. 블록 그룹은 무조건 일반 블록이 적어도 하나는 존재하는 그룹이여야 한다. 그러니까 무조건 블록 그룹의 시작은 일반 블록인 것이다.
5. 블록 그룹에 무지개 블록은 얼마나 들어있든 상관이 없다 , 그룹에 속한 블록의 개수는 무조건 2개 이상이여야 한다. (검은색 블록은 그냥 벽이라고 생각하면 된다.)
6. 그리고 블록의 그룹은 모두 인접하게 연결이 되어있어야 한다.
7. 블록 그룹의 기준 블록은 그냥 블록을 순회했을 때의 가장 왼쪽 위에있는 블록이 기준 블록이다. collection sort하면 될 듯하다.
8. 이제 게임이 시작하면 크기가 가장 큰 블록 그룹을 찾고, 그게 여러개이면 무지개 블록이 가장 많은 블록을 찾는다.
9. 그것도 여러개이면 그 중 기준 블록이 가장 행의 첫번째에 있는 것을 해야한다 이건 무조건 Collections.sort를 사용해야 할 듯
10. 이제 그 블록 그룹을 찾으면 제거하고 해당 블록 그룹에 포함된 블록의 수의 제곱만큼을 점수를 획득한다.
11. 중력이 작용하고 rotate 되고 중력이 또 작동한다.
12. 그 다음에 계속 이 행동을 반복하는데 블록 그룹이 하나라도 있으면 계속 시도한다.
-- 틀 설계
1.
 */

public class Main {
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static int n , m , score = 0;
    public static void bfs(){

    }
    public static void rotate(){

    }
    public static void rainbowClean(){

    }
    public static void clean(){

    }
    public static void getScore(){

    }
    public static void gravity(){

    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

    }
}
