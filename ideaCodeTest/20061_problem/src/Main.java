import java.util.*;
import java.io.*;

//20061 : 모노미노도미노2
/*
- 전제조건
1. 일단 모노미노도미노는 그냥 테트리스와 같고
2. 테트리스이지만 블럭의 모양은 총 3가지이다.
3. 1개짜리 , 가로로 2개 , 세로로 2개 짜리 이렇게 3가지가 존재한다.
4. 딱 하나의 블럭을 놓게 되면 열이면 열쪽, 행이면 행쪽으로 가게된다.
5. 행이나 열을 한 줄을 다 차지하게 되면은 그 줄은 사라지고 줄이 사라진 만큼 해당 블럭의 위에 있던 블럭들은 아래로 움직인다.
6. 그리고 각 초록색, 파란색다 0 , 1 칸은 연한색을 띄는데 연한색에 블럭이 하나라도 차게 되면 해당 블럭이 있는 칸만큼 맨 아래에 있는 칸이 사라지고
7. 사라진 만큼 블럭들은 내려가게 된다.
8. 주의해야 할 것은 사라진 행 ,열 위에 있는 칸들만 움직이게 되며 , 그렇지 않은 것들은 움직이지 않는다.
9. 연한칸에 차게 되었을 때에도 맨 아래칸이 사라진다는 사실을 인지하면 될 듯하다.

- 설계
-- 어떤 칸을 검사할지 결정하는 방법
1. 일단 order들을 type과 좌표를 저장한다.
2. 입력으로 들어오는 type들은 다 유효한 입력들만 들어온다고 생각하자.
3. order[0] == 1 -> (order[1],order[2])
-> 하나인 것은 blue는 order[1]을 따라서 해당 열만 검사하면 되고
-> green은 order[2]를 따라서 해당열만 검사하면 된다. -> 애초에 내가 배열 설정을 그렇게 했기 때문에 똑같은 걸 검사하면 된다. order만 달라진다.
4. order[0] == 2 -> (order[1],order[2]) , (order[1], order[2] + 1) 가로 2칸 짜리
-> blue는 order[1] 만 검사하면 되고 , 검사한 뒤에 끝까지 파고 들어간다음 그 row 와 row - 1까지 채워넣는다 결국은 열을 검사하면 된다.
-> green은 order[2] 와 order[2] + 1 열을 검사하면 된다. 이거는 그냥 둘중 하나라도 걸리면 거기다가 [row][col] , [row][col + 1]을 채워넣으면 된다.
5. order[0] == 3 -> (order[1],order[2]) , (order[1] + 1, order[2]) 세로 2칸 짜리
-> blue는 order[1]과 order[1] + 1을 검사하면 되고 , 해당 열을 쫙 훑으면 된다 그냥 모든 배열을 검사할 때 해당 열을 쫙 훑으면 된다.
-> green은 order[2]만 검사하면 된다 그런다음 찾으면 row , row - 1을 채워넣으면 된다.
-- 칸을 검사하는 방법
7.
8.
9.

-- 칸을 없애는 방법
1.

-- 칸을 땡기는 방법
1.

-- 최종 설계
 */

public class Main {
    public static int[][] order , blue = new int[6][4] , green = new int[6][4];
    public static void main(String[] args) throws IOException{
        /*
        -- 블럭을 입력받는 조건
        1. 일단은 당연히 블럭의 type과 block의 좌표를 저장해야하기 때문에
        2. order를 2차원 배열로 선언을 하고 하나의 인덱스마다 3개의 정보를 저장해놓는다 , type , y , x 좌표
        3. 근데 이제 이 순서를 어떻게 할 것이냐 , 즉 실제로 처음에 map에다가 그려놓고서 시작해야 할 것이냐
        4. 그럴 필요는 없을 듯 딱 좌표를 받고 type을 보고 생각해서 어떤 칸들을 검사해야할지를 결정하면 될 것 같다.
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());
        int orderSize = Integer.parseInt(st.nextToken());
        order = new int[orderSize][3];

        for(int i = 0; i < orderSize; i++){
            st = new StringTokenizer(input.readLine());
            order[i][0] = Integer.parseInt(st.nextToken());
            order[i][1] = Integer.parseInt(st.nextToken());
            order[i][2] = Integer.parseInt(st.nextToken());
        }
    }
}
