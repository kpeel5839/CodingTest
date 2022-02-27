import java.util.*;
import java.io.*;

// 5525 : IOIOI
/*
-- 전체 설계
n + 1 개의 I 그리고 , n 개의 O 로 이루어진 것을
하나의 문장이 주어졌을 때 몇개의 개수가 있냐를 본다.
-- 틀 설계
일단 매칭할 문자열을 만들고 , n을 보고
그 다음에 13 - 2n + 1 을 해준다.
그 다음에 해당 숫자까지 반복문 돌리면서 equals 를 해주면서 , 맞으면 count ++ 를 해준다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int count = 0;

        String p = "";

        int n = Integer.parseInt(input.readLine());
        int end = Integer.parseInt(input.readLine()) - (2 * n + 1);
        String problem = input.readLine();

        for(int i = 0; i <= n; i++){
            if(i == n){
                p += "I";
            }else{
                p += "IO";
            }
        }

        for(int i = 0; i <= end; i++){
            if(problem.substring(i , i + (2 * n + 1)).equals(p)) count++;
        }

        System.out.println(count);
    }
}