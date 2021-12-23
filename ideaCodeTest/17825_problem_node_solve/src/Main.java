import java.util.*;
import java.io.*;

//17825 : 주사위 윷놀이
/* 설계

*/
public class Main {
    public static class Node{

    }
    public static int[] order = new int[10]; // 일단 주사위들 순서
    public static int ans = 0;
    public static void dfs(int depth){
        if(depth == 10){ //여기서 이제 10까지 다 모이면 return;
            ans = Math.max(ans , gameStart());
            return;
        }
        for(int i = 1; i < 5; i++){
            order[depth] = i; //짜피 해당 요소를 뺄 필요가 없음 다시 집어넣기 때문에 이런 것도 생각할 줄 알아야함
            dfs(depth + 1);
        }
    }
    public static void main(String[] args) throws IOException{
        //노드로 풀어보자
        dfs(0);
    }
    public static int gameStart(){ //다 돌고나서 결과 반환하기 , order 여기다가 표시
        return 1; //test 전 임시 return value
    }
}
