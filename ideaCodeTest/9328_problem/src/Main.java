import java.util.*;
import java.io.*;

// 9328 : 열쇠

/*
-- 전제조건
상근이는 빌딩에 침입해서 문서를 훔치려고 한다.
이때 , 상근이가 평면도를 가지고 있고 , 문서의 위치가 모두 나타나 있다고 가정하자.
문을 열려면 열쇠가 필요하고 , 상근이가 일부 열쇠를 이미 가지고 있는 상태이다.
일부 열쇠는 빌딩의 바닥에 놓여있어서 , 상근이가 주울 수 있다.
상근이가 훔칠 수 있는 문서의 최대 개수를 구하는 프로그램을 작성하여라ㅏ.
'.' == 빈공간
'*' == 벽
'$' == 문서
대문자 == 벽 (해당 알파벳으로 딸 수 있는)
소문자 == 열쇠

맵을 입력으로 주고 , 상근이기 미리 가지고 있는 열쇠를 입력으로 준다.
만약에 갖고 있지 않다면 , 0으로 준다.
그리고 상근이는 가장자리의 벽이 아닌 곳을 통해서 드나들 수 있고,
키가 있다라면 , 각각의 열쇠를 이용하여서 여러개 딸 수 있다.

-- 틀 설계
ASCII 로
65 ~ 90 까지 대문자,
97 ~ 122 까지가 소문자이다.

26개 짜리의 배열을 만들어놓고서, 소문자가 나오게 되면 -97 을 해서 배열에다가 집언허고
대문자가 오게 되면 , 즉 65 ~ 90 사이의 대문자가 오게 되면 , 배열에다가 넣어준다.

그리고 map에는 가장자리에 한줄을 더 추가해주어서 , 0 , 0 에서 시작하면 밖에서 연결되는 지점을 그냥 들어갈 수 있다.
이제 솔직히 그냥 끝났다.
지금 해결해야 할 문제는 일단 하나이다 , 내가봤을 때는 열쇠를 찾을 때마다 재귀적으로 bfs 호출을 반복하면?
무조건적으로 시간초과가 날 것 같다.

그래서 다른 방법을 고안해내야 한다.
만일 새로 생긴 열쇠가 하나라도 있으면 , 진행이 되는 것은 어떨까?
bfs 를 호출할 때마다 , 새로운 열쇠가 존재했으면 진행하는 것이다.
그렇게 한번해보자.
이것은 boolean 변수를 만들어놓고서 진행하면 굉장히 쉽게 문제를 해결할 수 있을 것 같다.

그리고 빠르게 진행하기 위해서는 달러를 먹을 때 마다 , . 으로 바꾸고 res ++ 를 해준다.
그리고 먹은 열쇠 혹은 딴 문 이것들은 다 . 처리 해준다.

-- 결과
아니 이렇게 쉽게 맞는다고?
진짜 실력이 늘긴한건가...
심지어 시간도 엄청 빠르게 해결이 되었음
 */
public class Main {
    // 방문처리와 열쇠
    public static int H , W , T , res = 0;
    public static int[] dy = {-1 , 0 , 1 , 0} , dx = {0 , 1 , 0 , -1};
    // map 은 한칸을 더 만들어서 저장해야 한다.
    // 그래서 실제로는 H + 1 , W + 1 이다.
    public static char[][] map;
    public static boolean[] keys;
    public static boolean[][] visited;

    public static boolean outOfRange(int y , int x){
        //범위를 넘어가는지 판별해주는 method
        if(y < 0 || y >= H || x < 0 || x >= W) return true;
        return false;
    }
    public static boolean bigLetter(int character){
        // 대문자를 판별하는 method
        if(65 <= character && character <= 90) return true;
        return false;
    }
    public static boolean smallLetter(int character){
        // 소문자를 판별하는 method
        if(97 <= character && character <= 122) return true;
        return false;
    }
    public static void bfs(int y , int x){
        /*
        이제 여기서는 가지고 있는 키로 bfs 를 진행하면서 ,
        현재 추가적으로 얻은 키가 있냐 없냐를 따지면 되고,
        이미 있는 키를 먹은 것은 쓸데가 없음
        그래서 이미 있지 않은 키를 먹었을 때를 중점으로 bfs를 다시 진행하는 것으로하고
        달러는 먹은다음에 무조건 . 처리 해주어야 한다.
         */

        visited = new boolean[H][W];
        Queue<int[]> queue = new LinkedList<>();

        // 넣어주고 , 시작할 준비를 끝마침
        boolean newKey = false; // key를 새로 찾으면 해당 값을 true 로 만들어주어야 한다.
        queue.add(new int[]{y , x});
        visited[y][x] = true;

        while (!queue.isEmpty()) {
           int[] point = queue.poll();

           for(int i = 0; i < 4; i++){
               int ny = point[0] + dy[i];
               int nx = point[1] + dx[i];

               // 일단은 범위를 벗어나거나 이미 방문하였거나 , 벽인 경우는 넘어간다.
               if(outOfRange(ny , nx) || visited[ny][nx] || map[ny][nx] == '*') continue;

               // 그럼 이제 . , $ , 소문자 , 대문자인 경우들이 존재한다.
               if(bigLetter(map[ny][nx])){ // 대문자인 경우
                   // 대문자인 경우에는 , 해당 알파벳에 맞는 키가 있는 지 확인하고 , 있다면 . 으로 만들고 , 아니라면 continue 해준다.
                   if(keys[map[ny][nx] - 65]) map[ny][nx] = '.'; // 있다면 '.' 으로 만들고 넘어간다.
                   else continue; // 아니면 다음 걸로 넘어간다.
               }

               else if(smallLetter(map[ny][nx])) { // 소문자인 경우
                   if(!keys[map[ny][nx] - 97]) {
                       // 이미 존재하지 않는 경우 , 키를 true 로 만들어주고 해당 지점을 . 으로 만들어주고 , newKey 를 true 로만들어준다.
                       keys[map[ny][nx] - 97] = true;
                       newKey = true;
                   }
                   map[ny][nx] = '.'; // 소문자인 경우에는 무조건 . 으로 만들어줌
               }
               // 이제 달러인 경우와 , .인 경우
               else{
                   if(map[ny][nx] == '$'){ // 달러인 경우
                       res++;
                       map[ny][nx] = '.';
                   }
               }

               // 이제 다 마치고 나서 , 이동할 구간을 넣어준다.
               queue.add(new int[]{ny , nx});
               visited[ny][nx] = true;
           }
        }
        // 새로운 키를 찾았으면 다시 진행함
        if(newKey) bfs(0 , 0);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        T = Integer.parseInt(input.readLine());
        int index = 0;

        while(index++ < T){
            st = new StringTokenizer(input.readLine());

            H = Integer.parseInt(st.nextToken()) + 2;
            W = Integer.parseInt(st.nextToken()) + 2;

            map = new char[H][W];
            keys = new boolean[26];
            res = 0;

            for(int i = 0; i < H; i++){
                Arrays.fill(map[i] , '.');
            } // 인근을 다 채워넣는다.

            for(int i = 1; i < H - 1; i++){
                String string = input.readLine();
                for(int j = 1; j < W - 1; j++){
                    map[i][j] = string.charAt(j - 1);
                }
            } // 잘 채워졌다

            // 이제 해야 할 작업은 미리 채워 놓는 key 값을 채워넣는 것임

            String keyString = input.readLine();
            if(!keyString.equals("0")){ // 0 이 아닐 때에 진행
                // 소문자는 97을 마이너스
                for(int i = 0; i < keyString.length(); i++){
                    keys[keyString.charAt(i) - 97] = true;
                }
            }

            // bfs(0 , 0) 을 호출해주고 bfs 에서 이제 해결해주면 됨
            bfs(0, 0);

            output.write(res + "\n");
        }

        output.flush();
        output.close();
    }
}
