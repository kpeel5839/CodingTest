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

- 전체적인 설계 구도
1. 입력을 받는다.
2. order[i][0] = type , order[i][1] = row , order[i][2] = col
3. 입력을 for(int i = 0; i < order.length; i++)로 계속 입력을 준다.
4. 그러면 실제로 이 order에 맞춰서 행 , 열을 검사하는 함수 put() 를 호출한다.
5. put에서는 해당 열을 검사하면서 들어가야 할 칸을 검사하고 찾으면 거기다가 1을 채워 넣는다.
6. 1을 채워넣은 다음에 check() 함수를 호출해서 0 , 1 번째 행에 1이 생겼나 혹은 한 행 , 한 열에 1이 가득 찼나 확인한다.
7. check() 안에다가 만약 그런 행을 발견하면 그것을 맨 아래 행이나 열을 없애거나 아니면 해당 행 , 열을 없애는 remove 함수를 만든다.
8. remove가 호출 될때마다 0으로 다 바꿔주고 점수를 1점 올려준다.
9. 마지막에는 score 과 blue , green 의 1들을 세어서 score , count 를 한줄에 하나씩 출력하면 된다.
*/

public class Main {
    public static int[][] order , blue = new int[6][4] , green = new int[6][4];
    public static int count , score = 0;
    public static void put(int type , int y , int x){
        /*
        1. 해당 type에 맞게 green , blue를 검사한다.
        2. 검사를 순차적으로 하는데 1이 존재하면 type에 맞게 처리하여야 한다.
        3. 타입에 맞게 처리해서 1을 채워넣은 다음에 check 함수를 호출한다.
        4. type이 1이면 그냥 blue , green 둘다 green 은 x 값으로 열을 검사하고 , blue는 y 값으로 열을 검사한다.
        5. 이제 이게 문제인데 type 2, 3 은 둘이 하나씩 동시에 검사해야하는데 for문으로 for(int i = 0; i < 6; i++)로 한번에 검사하면 될 듯하다.
        6. 당연히 green 과 blue 따로 검사해서 넣어야 할 듯
         */
        int row = 0;
        if(type == 1){
            for(int i = 0; i < 6; i++){ //blue는 y값으로 x값을 검사
                if(blue[i][y] == 1){
                    row = i - 1;
                    break;
                }else if(i == 5){
                    row = i;
                }
            }
            blue[row][y] = 1;
            for(int i = 0; i < 6; i++){
                if(green[i][x] == 1){
                    row = i - 1;
                    break;
                }else if(i == 5){
                    row = i;
                }
            }
            green[row][x] = 1;
        }
        else if(type == 2){ // 가로 2칸
            for(int i = 0; i < 6; i++){ //blue는 y값으로 x값을 검사
                if(blue[i][y] == 1){
                    row = i - 1;
                    break;
                }else if(i == 5){
                    row = i;
                }
            }
            blue[row][y] = 1;
            blue[row - 1][y] = 1;
            for(int i = 0; i < 6; i++){
                if(green[i][x] == 1 || green[i][x + 1] == 1){
                    row = i - 1;
                    break;
                }else if(i == 5){
                    row = i;
                }
            }
            green[row][x] = 1;
            green[row][x + 1] = 1;
        }
        else{ //세로 2칸
            for(int i = 0; i < 6; i++){ //blue는 y값으로 x값을 검사
                if(blue[i][y] == 1 || blue[i][y + 1] == 1){
                    row = i - 1;
                    break;
                }else if(i == 5){
                    row = i;
                }
            }
            blue[row][y] = 1;
            blue[row][y + 1] = 1;
            for(int i = 0; i < 6; i++){
                if(green[i][x] == 1){
                    row = i - 1;
                    break;
                }else if(i == 5){
                    row = i;
                }
            }
            green[row][x] = 1;
            green[row - 1][x] = 1;
        }
        check();
    }
    public static void check(){
        /*
        1. 가득 찬 열이나 행이 있는지 확인
        2. 0 , 1번째 행에 1이 있는지 확인
        3. 확인 결과를 remove를 호출하여서 해당 line 값을 준다. (즉 행 값을 주면된다.)
        4. line은 0도 주는 것을 보니까 인덱스값을 의미한다. 따로 처리를 안해도 될 듯
        5. 만일 0 , 1에도 블럭이 존재하고 , 행이 가득찬 것들도 존재한다면?
        6. 그 경우에는 먼저 행이 가득 찬것을 먼저 처리하고 그 다음에도 차있을 때 그때 처리해야함
        7. 그럼 먼저 끝에단에서 먼저 검사를 한뒤에 remove를 진행하면서 그 다음에도 1, 0행에 있는지 확인해야할 듯
        8. 6번째부터 검사를 하니까 만약에 6 ,5가 가득 찼다고 하자 그러면 당연히 6 , 6 이렇게 line을 제거하는 것이 맞다.
        9. 그렇기 때문에 이전에 line에서 제거된게 존재한다면 removeCount를 더해준 것을 넣어주면 된다. 물론 0 , 1일때에는 그냥 무조건 remove 5이다.
         */
        List<Integer> rmList = new ArrayList<>();
        int removeCount = 0;
        int stack = 0;
        for(int c = 0; c < 2; c++) { //일단은 5 ~ 2까지의 라인 검사 0은 blue 그리고 1은 green remove명령
            removeCount = 0;
            for (int i = 5; i != 1; i--) {
                stack = 0;
                for (int j = 0; j < 4; j++) {
                    if (c == 0) {
                        if (blue[i][j] == 1) {
                            stack++;
                        }
                    }
                    else{
                        if (green[i][j] == 1){
                            stack++;
                        }
                    }
                }
                if(stack == 4){
                    rmList.add(i + removeCount);
                    removeCount++;
                }
            }
            for(Integer line : rmList){
                remove(line , c);
                score++;
            }
            rmList = new ArrayList<>();
        }
        for(int c = 0; c < 2; c++){
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < 4; j++){
                    if(c == 0){
                        if(blue[i][j] == 1){
                            rmList.add(5);
                            break;
                        }
                    }
                    else{
                        if(green[i][j] == 1){
                            rmList.add(5);
                            break;
                        }
                    }
                }
            }
            for(Integer line : rmList){
                remove(line , c);
            }
            rmList = new ArrayList<>();
        }
    }
    public static void remove(int line , int color){
        /*
        1. check에서 제거해야 할 line 정보를 주면 line 을 제거하고 score를 올리면 된다.
        2. 제거한 다음에는 해당 행보다 아래에 있던 것들을 올려주면 된다. 제거된 line 개수만큼
        3. color 는 blue에 대한 line remove 명령인지 , green에 대한 line remove 명령인지 분단하기 위해서 넣은 것이다. 0 == blue , 1 == green
        4. 제거 명령을 받은 line을 제거하고 해당 line보다 값이 낮은 행에 있는 것들을 다 떙겨온다.
        5. 그 땡겨오는 과정에서는 높은 행에서 부터 시작해서 그니까 사라진 행에서 부터 시작해서 그냥 떙겨오기만 하면 될듯
         */
        for(int i = 0; i < 4; i++){
            if(color == 0){
                blue[line][i] = 0;
            }
            else{
                green[line][i] = 0;
            }
        }
        for(int i = line - 1; i != -1; i--){
            for(int j = 0; j < 4; j++) {
                if (color == 0) {
                    if(blue[i][j] == 1){
                        blue[i][j] = 0;
                        blue[i + 1][j] = 1;
                    }
                } else {
                    if(green[i][j] == 1) {
                        green[i][j] = 0;
                        green[i + 1][j] = 1;
                    }
                }
            }
        }
    }
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

        for(int i = 0; i < orderSize; i++){
            put(order[i][0] , order[i][1] , order[i][2]);
        }

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 4; j++){
                if(blue[i][j] == 1){
                    count++;
                }
                if(green[i][j] == 1){
                    count++;
                }
            }
        }
//        for(int c = 0; c < 2; c++){
//            if (c == 0){
//                System.out.println("blue");
//            }
//            else{
//                System.out.println("green");
//            }
//            for(int i = 0; i < 6; i++){
//                for(int j = 0; j < 4; j++){
//                    if(c == 0){
//                        System.out.print(blue[i][j] + " ");
//                    }
//                    else{
//                        System.out.print(green[i][j] + " ");
//                    }
//                }
//                System.out.println();
//            }
//        }
        System.out.println(score);
        System.out.println(count);
    }
}
