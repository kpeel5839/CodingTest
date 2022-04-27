import java.io.*;
import java.util.*;

// 2866 : 문자열 잘라내기

/*
-- 전제조건
맵을 받고 ,
한 행씩 제거하였을 때 , 가능할 때까지 진행한다음에
가능하지 않으면 끝낸다.

한 행을 제거할 때마다 , count++ 를 해주고
끝나면 count 를 출력하면된다.
-- 틀설계
이것은 그냥 HashMap 을 받아서 하면 될 것 같고
처음에 map 을 받고
위에서 아래로 읽으면서 각 문자열들을 넣어놓는다.
그리고서 앞에 한글자씩 제거하면서 다시 저장하고
해쉬를 초기화 한뒤에 해쉬에다가 넣어보면서 진행한다.

근데 만일 여기서 해쉬에 이미 있는 것들이 나오면
즉 , 중복이 나오면 끝낸다.
 */
public class Main {
    public static String[] arr;
    public static char[][] map;
    public static int W , H , res = 0;
    public static HashSet<String> set;
    public static void cutFirst(){
        /*
        그냥 arr 의 앞에 한 글자씩 다 짤라내면 된다.
        지금 있는 글자에서 한글자만 짤라서 다시 넣어놓는다.
         */
        for(int i = 0; i < arr.length; i++){
            arr[i] = arr[i].substring(1);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        map = new char[H][W];
        arr = new String[W];

        for(int i = 0; i < H; i++){
            String string = input.readLine();
            for(int j = 0; j < W; j++){
                map[i][j] = string.charAt(j);
            }
        }

        for(int i = 0; i < W; i++){
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < H; j++){
                sb.append(map[j][i]);
            }
            arr[i] = sb.toString();
        }

        Loop:
        while(true){
            set = new HashSet<>();
            cutFirst();
            for(int i = 0; i < arr.length; i++){
                if(set.contains(arr[i])) break Loop;
                set.add(arr[i]);
            }
            res++;
        }

        System.out.println(res);
    }
}

