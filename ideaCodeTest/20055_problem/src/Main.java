import java.util.*;
import java.io.*;

// 20055 : 컨베이어 벨트 위의 로봇
/*
-- 전제 조건
길이가 N인 컨베이어 벨트(책상이라고 생각하자)
이것을 돌아야 하니 2N의 길이인 벨트가 있다. 위 아래를 감싸고 있고 , 벨트는 길이 1 간격으로 2N개의 칸으로 나뉘어져 있다.
벨트가 돌면서 각각 벨트들은 다음 위치로 이동하고 2N 위치의 벨트는 1로 N위치의 벨트는 N + 1로 움직인다.
그래서 여기서 벨트를 1번은 올리는 위치  , 그리고 N번칸이 있는 위치는 내리는 위치라고 한다.
왜냐하면 1번에다가 올려서 N번에서 내려야하니까 1(물건 올림) -> N(물건 내림)
컨베이어 벨트에 박스 모양 로봇을 하나씩 올리려고 하고 , 이 로봇 박스는 올리는 위치에만 올릴 수 있다.
언제든지 로봇이 내리는 위치에 도달하면 그 즉시 내린다 , 로봇은 컨베이어 벨트 위에서 스스로 이동할 수 있으며 , 로봇을 올리는 위치에 올리거나 로봇이 어떠한 칸으로 이동하면
그 칸의 내구도는 즉시 1만큼 감소한다 , 즉 로봇이 올라가면 그 칸의 내구도가 감소한다는 것이다.
그래서 컨베이어 벨트를 이용해서 로봇들을 건너편으로 옮기려고 한다 , 로봇을 옮기는 과정에서 순서대로 일어나는 일은
1. 벨트가 각 칸위에 있는 로봇과 함께 한 칸 회전한다. -> 2. 가장 먼저 벨트에 올라간 로봇부터 , 벨트가 회전하는 방향으로 한 칸 이동할 수 있다면 이동한다
, 만일 이동할 수 없다면 가만히 있는다. 부록 1) 로봇이 이동하기 위해서는 이동하려는 칸에 로봇이 없으며 , 그 칸의 내구도가 1 이상 남아 있어야 한다. (아마 끝에서도 못 움직이지 않을까 (내리는 위치))
-> 3.올리는 위치에 있는 칸의 내구도가 0이 아니면 올리는 위치에 로봇을 올린다 (컨베이어 벨트 망가질 때까지 계속 올린다.)
-> 4 내구도가 0인 칸의 개수가 K 이상이라면 과정을 종료한다. 그렇지 않으면 1번으로 돌아간다.
내구도가 0인 칸의 개수가 K 이상이라면 과정을 종료할 때 몇 번째 단계가 진행 중이었는지 구해본다. (가장 처음에 수행되는 단계가 1번째 단계이다.)

첫째 줄에 n , k 가 주어지고
둘째 줄부터 컨베이어 벨트의 내구도가 A1 ~ A2n 까지 주어진다.
몇 번째 단계가 진행 중일때 종료되었는지 출력한다. (그니까 이게 몇번째에서 끝났냐를 구하는 것)
벨트 회전 -> 가장 먼저 벨트에 올라간 로봇부터 벨트가 회전하는 방향으로 한칸 이동 -> 올리는 위치에 로봇을 올리는 것(내구도가 0이 아니라면) -> 검사 이것을 반복
그러고서 이 과정들을 몇번 반복했는지 출력하면 되는 문제이다.
-- 틀 설계
컨베이어 벨트의 내구도를 처음에 받는다 belt[2 * n] 으로 받아서
컨베이어 벨트의 끝 부분은 n - 1이라는 것을 잊으면 안된다.(인덱스)
일단 벨트를 beltRotate() 함수를 만들고 belt를 회전시키는 그냥 , 이때는 로봇도 움직이게 하면 됨
그 다음에 robotMove() 함수를 만들어서 robot을 움직일 수 있도록 한다. (이 때 다음칸에 로봇이 있으면 안되며 가려는 칸의 내구도가 1 이상이 남아있어야 한다.
그 다음에 로봇을 올리는 것은 그냥 중간에 내가 robot에다가 1 올리면 된다. (0 == robot 없는 상태 , 1 == robot이 있는 상태
그리고 아직 진행 가능한지 체크하는 check()하는 함수를 만든다.
time 변수를 두어서 한번씩 진행 될 때마다 증감시킨다.

-- 해맸던 점
설계는 다 좋았음
얼마 안 해맸지만 beltMove에서 robot을 앞으로 옮기는 과정에서 robot[0] 을 고려하지 않았음
옮겨지면 robotMove 지나고 나면 무조건 robot[0] 은 로봇이 없다는 것을 고려하지 않았었음
 */
public class Main {
    public static int n ,k;
    public static int[] belt , robot;

    public static void robotMove(){
        /*
        로봇이 이동하는 상황에서만 내구도가 감소한다는 것을 꼭 인식하고 있어야함
        로봇이 내리는 위치에 도달하면 그 즉시 내린다.
        robot들을 움직이게 하면서 belt의 내구도를 감소시킨다.
        일단 for문으로 싹 돌면서 먼저 탄 로봇을 탐색해야 한다.
        그럴려면 어쩔 수 없이 배열의 끝에서부터 탐색을 해야한다.
        마지막에는 로봇이 존재할 수 없으니 beltRotate에서 없앴기 때문에
        n - 2 에서 시작한다.
        n - 2 에서 시작해서 끝으로 거슬러 올라가는데 i + 1 위치에 만일 로봇이 있다면 움직이지 않고
        있다면 내구도를 검사해야한다.
        그래서 내구도를 검사해서 옮기면 거기의 내구도를 감소시켜야한다.
        짜피 뒤에서 부터 하는 것이니까 딱히 temp 이런 것도 필요 없이 배열의 값만 이동시키면 된다.
         */
        for(int i = n - 2; i != -1; i--){
            if(robot[i] == 1){
                if(robot[i + 1] != 1 && belt[i + 1] != 0){
                    robot[i + 1] = robot[i];
                    robot[i] = 0;
                    belt[i + 1]--;
                    if(i + 1 == n - 1){
                        robot[i + 1] = 0;
                    }
                }
            }
        }
    }

    public static void beltRotate(){
        /*
        로봇이 내리는 위치에 도달하면 그 즉시 내린다.
        belt의 내구도를 앞으로 옮기면서 꼭 robot도 같이 옮겨줘야 하는데 robotMove는 비교적으로 쉽다.
        belt에서 다 앞으로 옮기면서 belt의 내구도는 2n - 1 -> 0 으로 가야한다는 것을 인식
        robot은 그냥 다 앞으로 옮기면서 robot이 내리는 위치에 가면 즉 n - 1에 도달하면 그 즉시 0으로 만든다.
        그냥 robot[n - 1] = 0; 은 항상 해주면 된다.
        1 2 3 4 -> 4만 기억하고 뒤에서부터 앞으로 옮겨주면서 맨 마지막에 넣어주면 된다.
        */
        int temp = belt[2 * n - 1];
        for(int i = 2 * n - 2; i != -1; i--){
            belt[i + 1] = belt[i];
        }
        belt[0] = temp;
        for(int i = n - 2; i != -1; i--){
            robot[i + 1] = robot[i];
        }
        robot[0] = 0;
        robot[n - 1] = 0; //마지막에 도착한 robot은 무조건 없애기
    }

    public static boolean check(){
        /*
        belt가 k 이상이 남아 있는지 확인하는 역할
         */
        int count = 0;
        for(int i = 0; i < 2 * n; i++){
            if(belt[i] == 0){
                count++;
            }
        }
        if(k <= count){
            return true;
        }
        return false;
    }


    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());


        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        belt = new int[2 * n];
        robot = new int[n];

        st = new StringTokenizer(input.readLine());

        for(int i = 0; i < 2 * n; i++){
            belt[i] = Integer.parseInt(st.nextToken());
        }

        int time = 0;
        while(true){
            time++;
            beltRotate();
            robotMove();
            if(belt[0] != 0){ //0이 아니면 올리고 그 즉시 내구도 감소
                robot[0] = 1;
                belt[0]--;
            }
            if(check()) break;
        }

        System.out.println(time);
    }
}
