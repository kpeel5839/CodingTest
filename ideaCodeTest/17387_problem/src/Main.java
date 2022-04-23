import java.util.*;
import java.io.*;
import java.util.function.*;

// 17387 : 선분교차2

/*
-- 전제조건
-- 틀설계
 */
public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // String -> Integer 해주는 function 저장 , 앞으로 Integer.parseInt 이런식으로 하지말고 그냥 fun.apply(input[0]) 이런식으로 진행하면 된다.
        Function<String , Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");

        int x1 = fun.apply(input[0]);
        int y1 = fun.apply(input[1]);
        int x2 = fun.apply(input[2]);
        int y2 = fun.apply(input[3]); // L1 입력 받음

        input = br.readLine().split(" ");

        int x3 = fun.apply(input[0]);
        int y3 = fun.apply(input[1]);
        int x4 = fun.apply(input[2]);
        int y4 = fun.apply(input[3]); // L2 입력 받음
    }
}
