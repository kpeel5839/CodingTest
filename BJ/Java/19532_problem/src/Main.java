import java.util.*;
import java.io.*;

// 19532 : 수학은 비대면강의입니다.

/*
-- 전제조건
ax + by = c
dx + ey = f
이 두 개의 식에서 a , b , c , d , e , f 가 순서대로 주어질때의
x , y 를 구하여라
-- 틀설계
계산하기 귀찮으니까 그냥 for문으로 때려넣자.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(System.in);

        int a = input.nextInt() , b = input.nextInt() , c = input.nextInt() , d = input.nextInt() ,e = input.nextInt() , f = input.nextInt();

        Loop:
        for(int i = -999; i <= 999; i++){
            for(int j = -999; j <= 999;j ++){
                if(a * i + b * j == c && d * i + e * j == f){
                    System.out.println(i + " " + j);
                    break Loop;
                }
            }
        }
    }
}


