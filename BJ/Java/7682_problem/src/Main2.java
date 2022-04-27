import java.util.*;
import java.io.*;

// 7682 : 틱택토

/*
-- 전제조건
틱택토 게임은 두 명의 사람이 번갈아가면서 말을 놓는 게임이고,
게임판은 3 * 3 격자판이고 , 처음에는 비어있다.

각 세로 , 가로 , 대각선 방향으로 3칸을 잇는 데 성공하면 게임이 끝난다.

무조건적으로 X 를 먼저 놓고 , O를 놓는 형식의 게임이다.
게임판의 상태가 각각 주어졌을 때 , 해당 게임이 정상적인 게임이였는지 , 즉 실제로 가능한 경우인지 판별하여
가능하면 "valid" , 가능하지 않으면 "invalid" 를 출력하면 된다.
-- 틀 설계
총 9개의 입력이 주어지며

1 2 3
4 5 6
7 8 9

이런식의 순서로 들어온 순서대로 배당이 된다고 생각하면 된다.
입력의 마지막에는 end 가 들어온다.

먼저 X , O 의 개수가 정상적인지 확인을 진행하는데 , 만일
O가 더 많다라거나 , 혹은 X 와 O 의 개수가 2개 이상 차이가 난다라면 끝

그리고 입력으로 들어온 맵을 확인하여 , 게임이 끝났는 지를 확인을 한다.
만일 여기서도 , 둘다 게임이 끝났다면 , invalid 하다라고 바로 출력하면 된다.

그리고 여기서 게임이 만일 끝났다면 , 검사해야 할 것이 누가 끝냈냐이고 , 만일 O 가 끝냈다면 , 서로의 개수가 같아야 하고
X 가 끝냈다면 X가 하나가 더 많아야 한다.

그리고 게임이 끝나지 않은 경우를 고려하는데 , '.' 이 있다면 무조건 invalid ,
그리고 만일 X , O 의 개수가 X 가 하나가 더 많지 않다면 invalid 이다.

이것을 다 통과한다라면 ? valid이다.

틀렸다고 나왔다...
내가 놓친 게 무엇이 있을까?

솔직히 이전 풀이방법 뭐가 틀린지 모르겠으나,
당연히 틀렸으니까 틀렸다고 나올 것이다.
그렇기 때문에 사람들이 많이 하는 방법으로 진행해보자.

일단 9 개가 다 차있는 경우는
O 가 이기면 안되고 , X 가 하나가 더 많아야 한다.

그리고 o == x 인 경우는 o 가 이겨야하고
x + 1 == o 인 경우는 x 가 무조건 이겨야 한다.

당연하게도 둘이 다 안 끝난 경우는 끝나지 않는다.

그냥 이렇게 처리하니까 맞았다 , 근데 찝찝하다 겨우 이런 문제 하나를 이렇게 쩔쩔매서
그냥 이렇다 , 9개 일때에는 x 가 무조건 o 보다 하나가 더 많아야 하고
그리고 O 가 이긴 상태이면 안된다.

그리고 , 다 차있는게 아닌 상황일 떄 , 즉 dot 이 있을 때에는 o == x , x - 1 == o 인 경우로 나뉜다.
o == x 일때에는 o 가 무조건 빙고여야 하고 ,
x - 1 == o 일 때에는 x 가 무조건 빙고여야 한다.
만일 이 상황에 어긋나면 , 다 게임판의 상태가 이상한 것이다.
 */
public class Main2 {
    public static final int SIZE = 3;
    public static char[][] map;
    public static int o , x , dot;
    public static final char O = 'O' , X = 'X';

    public static boolean check(char target){
        /*
        해당 target 으로 빙고가 있는지 확인한다.
        그냥 가로 , 세로 , 대각 확인하면 된다.
         */
        boolean end = false;

        // 가로 세로 , 확인
        for(int c = 0; c < 2; c++) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if(c == 0) {
                        // 만일 같지 않으면 break;
                        if (target != map[i][j]) break;

                        // 여기까지 왔다라는 것은 지금까지 다 같았다라는 것이다 , end = true 로 만들어준다.
                        if (j == SIZE - 1) end = true;
                    }
                    else{
                        if (target != map[j][i]) break;

                        // 여기까지 왔다라는 것은 지금까지 다 같았다라는 것이다 , end = true 로 만들어준다.
                        if (j == SIZE - 1) end = true;
                    }
                }
            }
        }

        // 이제 대각만 확인해주면 된다.
        for(int i = 0; i < 2; i++) {
            for (int j = 0; j < SIZE; j++) {
                // 처음에는 왼쪽에서부터 촤르륵 , 두번째는 오른쪽에서부터 촤르륵
                if(i == 0){
                    if(map[j][j] != target) break;
                    if(j == SIZE - 1) end = true;
                }

                else{
                    if(map[2 - j][j] != target) break;
                    if(j == SIZE - 1) end = true;
                }
            }
        }

        return end;
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));

        String problem;

        while(!(problem = input.readLine()).equals("end")){ // end 와 같으면 끝
            map = new char[3][3];
            o = 0;
            x = 0;
            dot = 0;

            for(int i = 0; i < SIZE; i++){
                for(int j = 0; j < SIZE; j++){
                    map[i][j] = problem.charAt(i * SIZE + j);
                    if(map[i][j] == O) o++;
                    else if(map[i][j] == X) x++;
                    else dot++;
                }
            }

            output.write((getRes() ? "valid" : "invalid") + "\n");
        }

        output.flush();
        output.close();
    }
    public static boolean getRes(){
        /*
        여기서는 해당 값이 invalid 하면 false
        valid 하면 true 를 반환하는 함수이다.
         */
        // o , x로 가득 차있으면 , 1개 차이 인 경우
       boolean xBingo = check(X);
       boolean oBingo = check(O);

       if(xBingo && oBingo) return false;

       if(x < o || x - o >= 2) return false;

       if(dot == 0){
           if(x - 1 != o || oBingo) return false;
           return true;
       }
       if(x == o) {
           if (oBingo) return true;
           return false;
       }
       if(xBingo) return true;
       return false;
//       return false;
    }
}

