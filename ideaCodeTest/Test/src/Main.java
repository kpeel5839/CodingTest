import java.util.*;
import java.io.*;

public class Main {
    public static int r, c, y, x, instructCount;
    public static int[][] map;
    public static int[] dice = new int[6];
    public static int[] tempDice = new int[6];
    public static int[] instruct;
    public static int[] rollR = {0 , 0, 0, -1 , 1}; //Row 방향
    public static int[] rollC = {0 , 1 , -1 , 0 , 0}; //Col 방향

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        st = new StringTokenizer(input.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken()); //x가 씨발 row임
        y = Integer.parseInt(st.nextToken()); //y가 씨발 col임
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
        }
        instruct();
    }
    public static void rollDice(int direction){
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
    public static void move(int direction){
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
    public static void instruct(){
        for(int i = 0; i < instructCount; i++){
            move(instruct[i]);
        }
    }
}
