import java.util.*;
import java.io.*;

// 1022 : 소용돌이 예쁘게 출력하기

/*
-- 전제 조건
일단 소용돌이가 존재하고 ,
거기서 소용돌이를 순차대로 출력하는데,
이쁘게 출력하는 것이 목적이다.

0 , 0 에서 1로 시작하고 , 그 다음에 0 , 1 에서 2로 가면서 , 순차적으로 반시계 방향으로 진행된다.
그래서 , 이제 범위가 주어지면 그것을 공백을 맞추면서 , 이쁘게 출력하면 된다.
-- 틀 설계
일단 소용돌이를 만드는 것이 우선일 듯 하다.
-5000 ~ 5000 이라는 것은 일단 , 범위가 포함된 인덱스는 10001 이다.
그렇다는 것은 , 10001 , 10001 의 크기인 1억짜리의 배열을 만들어야 한다.

근데 , 1억 번의 연산은 생각보다 오버헤드가 크지는 않다
그래서 소용돌이를 만들어놓은 다음에,
여기서 범위를 주어지면 가장 큰 숫자의 길이를 측정하고
숫자들을 출력할 때 , 그 숫자의 길이와 맞춰서 출력한다.

예를 들어서
15 14 13
9 8 6
1 2 3

대충 이렇게 있다고 하면 가장 긴 숫자는 2자리 숫자이다.
그러면 나머지 숫자들은 길이를 가장 긴 숫자에 맞춰서 공백을 출력하고
그 다음에
System.out.println(map[i][j] + " "); 이렇게 하면 될 것 같다.

그러면 이제 소용돌이만 구하면 된다.
소용돌이는 일단
5000 ~ 5000 이니까
-5000 도 구해야 한다.

그러면 일단 크기가 3인 배열을 구한다고 가정해보자.
-3 ~ 3 이다
그러면 총 7개의 인덱스를 가지게 된다.
그러면 총 배열의 크기는
-3 = 0 .... 3 = 6 까지 가지게 된다.

그러면 배열의 크기는 5000에 대입하였을 때 , 10001 이라고 가정하고 시작하면 될 것 같다.
그리고 0 , 0 이 1로 시작하니까 이것은
0 = 5000 으로 잡고 시작하면 된다.
그러면 map[5000][5000] = 1 넣고 , map[5000][5001] = 2 넣고 범위 벗어날 때 까지 진행하면 된다.

소용돌이의 패턴을 분석하면 1 , 1 , 2 , 2 , 3 , 3 이런식으로 증가하게 된다.
즉 방향은 1 , 0 , 3 , 2 ... 이렇게 가면서 1 1 2 2 3 3 이렇게 증가되게 된다.
그리고 이건 무조건 홀 수에 정사각형인 소용돌이기에 , 정확히 끝난다.
그렇기 때문에 , 범위를 벗어나면 ? 그 때 삽입을 멈추면 된다.

메모리 초과 나옴
배열을 다 만들지 말고 , 살짝 더 추가적인 생각을 해야할 것 같음

아니면 만들지 말고 , map을 그냥 해당 범위에 들어가면 값을 넣으면 되지 않을까??
메모리 초과나서 , 그냥 그 범위만큼의 array만 만들고 , 범위에 맞으면 집어넣었음
 */
public class Main {
    public static final int H = 10001 , W = 10001;
    public static int diffY , diffX;
    public static int[][] map;
    public static int r1 ,r2 , c1 , c2;
    public static int[] dx = {0 , 1 , 0 , -1} , dy = {-1 , 0 , 1 , 0};
    public static void generateTornado(){
        /*
        토네이도를 만드는 함수
        그냥 요기서는 기계적으로 map에다가 값을 채워주면 된다.

        처음에 시작 방향은 1 , 0 , 3 , 2 이렇게 가야한다.
        그리고 , 범위를 벗어나면 끝내야한다. (outOfRange 함수를 호출해서 계속 확인한다.)

        그리고 두번 진행될 때 마다 진행하는 칸의 개수가 1씩 늘어난다.

        토네이도 방향으로 어떻게 진행할 수 있을까
        0 , 0 -> 0 , 1 -> -1 , 1 -> -1 , 0 -> ... 이런식으로 진행한다.

        방향을 계속 1씩 빼가면서 순차적으로 진행하도록 하고,
        반복문에서 , count 만 수정하면 될 것 같다.

         */

        int move = 1;
        int dir = 1;
        int count = 1;

        int y = 5000 , x = 5000;
        if((r1 - 5000 <= 0 && 0 <= r2 - 5000) && (c1 - 5000 <= 0 && 0 <= c2 - 5000)) map[y - 5000 - diffY][x - 5000 - diffX] = count;

        Loop:
        while(true){
            for(int i = 0; i < 2; i++){
                for(int j = 0; j < move; j++){
                    y += dy[dir];
                    x += dx[dir];
                    count++;

                    if(outOfRange(y , x)) break Loop;

                    if((r1 <= y && y <= r2) && (c1 <= x && x <= c2)) map[y - 5000 - diffY][x - 5000 - diffX] = count;
                }
                dir = --dir == -1 ? 3 : dir;
            }
            move++;
        }
    }
    public static boolean outOfRange(int y, int x){
        if(y < 0 || y >= H || x < 0 || x >= W) return true; // range 벗어남
        return false;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(input.readLine());

        // (r1 , c1) 이런형태이다. 1 == 왼쪽 위 , 2 == 오른쪽 아래
        r1 = Integer.parseInt(st.nextToken()) + 5000;
        c1 = Integer.parseInt(st.nextToken()) + 5000;
        r2 = Integer.parseInt(st.nextToken()) + 5000;
        c2 = Integer.parseInt(st.nextToken()) + 5000;

        // r1 , c1 을 0으로 만드는 수를 찾아야함 그럴려면 그냥
        diffY = r1 - 5000;
        diffX = c1 - 5000; // 원래대로의 r1 , r2를 만드는 diff이다.

        map = new int[r2 - r1 + 1][c2 - c1 + 1];
        generateTornado();

        int max = 0;

        for(int i = 0; i < r2 - r1 + 1; i++){
            for(int j = 0; j < c2 - c1 + 1; j++){ // 가장 큰 값을 찾자
                max = Math.max(max , map[i][j]);
            }
        }

        int length = Integer.toString(max).length();

        for(int i = 0; i < r2 - r1 + 1; i++){
            for(int j = 0; j < c2 - c1 + 1; j++){
                // 길이에 맞춰서 출력해야함
                int nowLength = Integer.toString(map[i][j]).length();
                if(length > nowLength){ // 제일 긴 length 와 현재 length 를 ㅣㅂ교함
                    // 더 작으면 작은 만큼 공백을 출력해주어야 함
                    for(int c = 0; c < length - nowLength; c++){
                        output.write(" ");
                    }
                }
                output.write(map[i][j] + " ");
            }
            output.write("\n");
        }

        output.flush();
        output.close();
    }
}