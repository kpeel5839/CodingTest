import java.util.*;
import java.io.*;

public class Main {
    public static int r, c, y, x, instructCount;
    public static int[][] map;
    public static int[] dice = new int[6];
    public static int[] tempDice = new int[6];
    public static int[] instruct;
    public static int[] rollC = {0 , 1, -1, 0 , 0};
    public static int[] rollR = {0 , 0 , 0 , -1 , 1}; // 1 = 동 , 2 = 서 , 3 = 북 , 4 = 남

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        instructCount = Integer.parseInt(st.nextToken());
        map = new int[r][c];
        instruct = new int[instructCount];
        for (int i = 0; i < r; i++) {
            st = new StringTokenizer(input.readLine());
            for (int j = 0; j < c; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(input.readLine());
        for (int i = 0; i < instructCount; i++) {
            instruct[i] = Integer.parseInt(st.nextToken());
        } // 입력 받기 완료
        instruct();
    }
    public static void rollDice(int direction){ //아랫면은 1 , 윗면은 3 , 0 , 1 , 2 , 3, 4 , 5 로 갈예정이고
        //map의 내용으로 바꾸는 것은 1번을 신경쓰고
        //출력하는 것은 3번을 신경쓰게끔 하면된다.
        if (direction == 3){
            dice[0] = tempDice[3];
            dice[1] = tempDice[0];
            dice[2] = tempDice[1];
            dice[3] = tempDice[2];
        }
        else if (direction == 1){
            dice[1] = tempDice[5];
            dice[3] = tempDice[4];
            dice[4] = tempDice[1];
            dice[5] = tempDice[3];
        }
        else if (direction == 4){
            dice[0] = tempDice[1];
            dice[1] = tempDice[2];
            dice[2] = tempDice[3];
            dice[3] = tempDice[0];
        }
        else{
            dice[1] = tempDice[4];
            dice[3] = tempDice[5];
            dice[4] = tempDice[3];
            dice[5] = tempDice[1];
        }
    }
    public static void move(int direction){ //지도를 움직이는 부분, 이것이 나가는 지 안나가는지를 판단해주고
        //한칸 움직일 떄마다 rollDice를 통해서 주사위를 변경시키고
        //그리고 위에 것들을 출력해준다. dice[1] 밑면 , dice[3] 윗면
        //칸과 주사위의 상호작용을 한 후에 tempDice를 dice와 일치화 시켜야한다.
        int endC , endR;
        if(direction == 1){endC = c; endR = -1;}
        else if(direction == 4){endR = r; endC = -1;}
        else{endR = -1; endC = -1;}
        if(x + rollR[direction] == endR || y + rollC[direction] == endC){
            return;
        }
        x += rollR[direction];
        y += rollC[direction];
        rollDice(direction);
        if(map[x][y] == 0){map[x][y] = dice[1];}
        else{dice[1] = map[x][y]; map[x][y] = 0;}
        for(int i = 0; i < 6; i++){tempDice[i] = dice[i];}
        System.out.println(dice[3]);
    }
    public static void instruct(){ //실제로 연산을 지시하는 부분
        //1.이동한 칸에 적혀있는 수가 0이면 주사위의 바닥면의 숫자가 칸에 복사된다 , 이것은 주사위의 숫자가 바닥으로 가는게 아니라 진짜 복사되는 것
        //2.이동한 칸에 적혀있는 수가 0이 아니면 칸의 숫자가 주사위의 바닥면으로 복사된다 , 복사되고 나면 칸에 적혀있는 숫자는 0이 된다.
        for(int i = 0; i < instructCount; i++){
            move(instruct[i]);
        }
    }
}
