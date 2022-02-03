import java.util.*;
import java.io.*;

public class Main{
    public static int gcd(int a , int b){
        /*
        gcd를 재귀 호출 해서 b를 0으로 만들면 a가 최대공약수이니 그 때 반환하면된다.
        근데 만약에 b가 0이 안된다? 그러면 최대 공약수가 없는 것이다.
         */
        System.out.println("a : " + a + " b: " + b);
        if(b == 0){
            return a;
        }
        else{
            return gcd(b , a % b);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        int number1 = Integer.parseInt(st.nextToken());
        int number2 = Integer.parseInt(st.nextToken());
        System.out.println(gcd(Math.max(number1 , number2) , Math.min(number1 , number2)));
    }
}