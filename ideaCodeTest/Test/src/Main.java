import java.util.*;
import java.io.*;

public class Main {
    public static class Pair implements Comparable<Pair>{
        int x;
        int y;
        public Pair(int x , int y){
            this.x = x;
            this.y = y;
        }

        public int compareTo(Pair o){ //각 이 숫자로 우선순위가 적용 되나봄 , -1 , 1만 반환할 수 있는 것이 아니라
            if(this.y > o.y){
                return -1;
            }
            else if(this.y == o.y){
                return o.x - this.x;
            }
            else{
                return 1;
            }
        }

        @Override
        public String toString(){
            return "x : " + x + " y : " + y;
        }
    }
    public static void main(String[] args) throws IOException{ //Comparable 시험해보기 객체 정렬 두가지로도
        double number = 0;
        System.out.print(number / 9D);
    }
}
















































