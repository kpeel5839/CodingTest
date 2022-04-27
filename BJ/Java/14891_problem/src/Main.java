import java.util.*;
import java.io.*;

public class Main {
    public static int[][] wheel;
    public static int[][] tempWheel;
    public static int c;
    public static int[][] instruct;
    public static List<Spin> spinList = new ArrayList<>();
    public static void main(String[] args) throws IOException{ //0 = 위 2 = 오른쪽 , 6 = 왼쪽
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        wheel = new int[4][8];
        tempWheel = new int[4][8];

        for(int i = 0; i < 4; i++){
            String string = input.readLine();
            for(int j = 0; j < 8; j++){
                wheel[i][j] = Integer.parseInt(Character.toString(string.charAt(j)));
                tempWheel[i][j] = wheel[i][j];
            }
        }

        c = Integer.parseInt(input.readLine());
        instruct = new int[c][2];
        for(int i = 0; i < c; i++){
            st = new StringTokenizer(input.readLine());
            for(int j = 0; j < 2; j++){
                instruct[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < c; i++){
            order(instruct[i][0] - 1 , instruct[i][1]);
        }

        System.out.println((wheel[0][0]) + (wheel[1][0] * 2) + (wheel[2][0] * 4) + (wheel[3][0] * 8)); //n극이 0임 s극이 1점이고 , 이제

    }
    public static void order(int sequence , int dir){
        //이제 여기서 딱 시퀀스를 받고 방향을 받으면 해당 톱니바퀴를 돌리면서 주변을 탐색 돌리기전에 봐야함
        int leftDir = dir;
        int rightDir = dir;
        for(int i = sequence - 1; i != -1; i--){ //왼쪽으로 탐색 왼쪽으로 갈 때에는 이전 것의 6과 현재의 2를 비교함
            if(wheel[i][2] != wheel[i + 1][6]){
                leftDir = -1 * leftDir;
                spinList.add(new Spin(i , leftDir));
            }
            else{
                break;
            }
        }
        for(int i = sequence + 1; i < 4; i++){ //오른쪽으로 탐색하는 경우 오른쪽으로 갈 때에는 이전 것의 2와 현재의 6을 비교한다.
            if(wheel[i][6] != wheel[i - 1][2]){
                rightDir = -1 * rightDir;
                spinList.add(new Spin(i , rightDir));
            }
            else{
                break;
            }
        }
        spinList.add(new Spin(sequence , dir));
        for(Spin spin : spinList){
            spinWheel(spin.direction , spin.number);
        }
        spinList = new ArrayList<>();
    }

    public static void spinWheel(int dir , int sequence){ //sequence 는 0 부터 시작함 0 부터 첫번째 바퀴
        for(int i = 0; i < 8; i++){
            wheel[sequence][i + dir < 0 ? 7 : (i + dir) % 8] = tempWheel[sequence][i];
        }
        copy(sequence);
    }

    public static void copy(int sequence){
        for(int i = 0; i < 8; i++){
            tempWheel[sequence][i] = wheel[sequence][i];
        }
    }
    public static class Spin{
        int number;
        int direction;

        public Spin(int number , int direction){
            this.number = number;
            this.direction = direction;
        }
    }
}
