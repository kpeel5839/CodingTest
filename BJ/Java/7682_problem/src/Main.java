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
 */
public class Main {
    public static final int SIZE = 3;
    public static boolean endO , endX;
    public static char[][] map;

    public static boolean checkEnd(){
        /*
        해당 게임이 끝났는지 아닌지를 확인해준다.
        일단 가로로 쭉쭉
        그 다음 세로로 쭉쭉
        그 다음에 대각으로 쭉쭉 하면 된다.
        그냥 for 문으로 대충 구성해주자.
         */
        boolean end = false;

        // 가로 세로 , 확인
        for(int c = 0; c < 2; c++) {
            for (int i = 0; i < SIZE; i++) {
                char initial;
                if(c == 0) initial = map[i][0];
                else initial = map[0][i];
                for (int j = 1; j < SIZE; j++) {
                    if(c == 0) {
                        // 만일 같지 않으면 break;
                        if (initial != map[i][j]) break;

                        // 여기까지 왔다라는 것은 지금까지 다 같았다라는 것이다 , end = true 로 만들어준다.
                        if (j == SIZE - 1) {
                            end = true;
                            setEnd(initial);
                        }
                    }
                    else{
                        if (initial != map[j][i]) break;

                        // 여기까지 왔다라는 것은 지금까지 다 같았다라는 것이다 , end = true 로 만들어준다.
                        if (j == SIZE - 1) {
                            end = true;
                            setEnd(initial);
                        }
                    }
                }
            }
        }

        // 이제 대각만 확인해주면 된다.
        for(int i = 0; i < 2; i++) {
            char initial;
            if(i == 0) initial = map[0][0];
            else initial = map[2][0];
            for (int j = 1; j < SIZE; j++) {
                // 처음에는 왼쪽에서부터 촤르륵 , 두번째는 오른쪽에서부터 촤르륵
                if(i == 0){
                    if(map[j][j] != initial) break;
                    if(j == SIZE - 1) {
                        end = true;
                        setEnd(initial);
                    }
                }

                else{
                    if(map[2 - j][j] != initial) break;
                    if(j == SIZE - 1) {
                        end = true;
                        setEnd(initial);
                    }
                }
            }
        }

        return end;
    }

    public static void setEnd(char initial){
        if(initial == 'O') endO = true;
        else endX = true;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));

        String problem;

        while(!(problem = input.readLine()).equals("end")){ // end 와 같으면 끝
            map = new char[3][3];
            int o = 0;
            int x = 0;
            int dot = 0;
            endO = false;
            endX = false; // 어떤 걸로 끝났는지 , 이전 것을 초기화

            for(int i = 0; i < SIZE; i++){
                for(int j = 0; j < SIZE; j++){
                    map[i][j] = problem.charAt(i * SIZE + j);
                    if(map[i][j] == 'O') o++;
                    else if(map[i][j] == 'X') x++;
                    else dot++;
                }
            }

            // o 가 x 보다 크거나 , 혹은 x 와 o의 차이가 2보다 크면 바로 invalid
            if(o > x || x - o >= 2){
                output.write("invalid" + "\n");
                continue;
            }

            // 이제 o , x 의 개수에는 이상이 없으니 게임이 끝난 경우와 , 아닌 경우를 나눠서 판다.ㄴ
            boolean end = checkEnd();

            if(end){
                // 둘다 끝난 경우 넘김
                if(endO && endX){
                    output.write("invalid" +  "\n");
                    continue;
                }

                // o 가 끝냈는데 o == x 가 아니라면 끝
                if(endO && o != x){
                    output.write("invalid" + "\n");
                    continue;
                }

                // x 가 끝냈는데 x - o == 1 이 아니라면 끝
                if(endX && x - o != 1){
                    output.write("invalid" + "\n");
                    continue;
                }
            }
            else{
                // 끝나지 않았는데 , . 이 있다면 끝
                // 끝나지 않았는데 , x - o == 1 이 아니라면 끝
                if(dot != 0){
                    output.write("invalid" + "\n");
                    continue;
                }

                if(x - o != 1){
                    output.write("invalid" + "\n");
                    continue;
                }
            }

            output.write("valid" + "\n");
        }

        output.flush();
        output.close();
    }
}

