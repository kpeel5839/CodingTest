import java.util.*;
import java.io.*;

// 1062 : 가르침
/*
-- 전제조건
학생들에게 그냥 K개의 글자만을 가르킴
무조건 글자들은 anta로 시작되고 tica로 끝난다.
그래서 antic는 무조건 배워야지 하나의 단어라도 알 수 있음
그래서 남극언어에 단어는 N개 밖에 없다고 가정한다.
학생들이 읽을 수 있는 단어의 최댓값을 구하는 프로그램을 작성하시오.
-- 틀 설계
소문자 ascii code - (97 ~ 122)
k가 5미만이다? 그러면 무조건 0을 출력한다.
그 다음에 k가 딱 5인 경우에는 공백인 string만을 세서 count 해서 출력한다.
이제 그 이상인 경우를 생각해보면
일단 ascii code를 고려해서 0 ~ 25까지의 배열을 만든다.
그럴려면 배열을 26짜리로 선언해야함
그리고 무조건 들어갈 수 있는 letter 배열을 다 -1 처리를 한다.
그리고 string들을 읽으면서 앞 4글자 뒤 4글자를 빼서 받는다 그럴려면
string.substring(4 , string.length() - 4) 이렇게 받으면 된다.
그 다음에 그 글자들을 하나하나씩 읽으면서 letter 배열에 0으로 처리한다.

그 다음에 dfs에서 letter 배열에서 -1인 것들은 그냥 바로 depth + 1 하면서 넘어간다 , 그러면서 당연히 return 도 해주어야함
그리고 나머지 0인 배열들은 1을 넣으면 k - 1 로 넘기고 (letter[depth] = 1 해줘야 함) 안넣는 경우는 그냥 depth + 1 만 해서 넘긴 다음에
depth == 26가 되면 거기서 이제 letter 가지고 몇개의 단어를 읽을 수 있나 한다 그러면서 ans 를 계속 구한다.
--해맸던 점
그냥 출력문을 잘못해서 틀렸었었음
그리고 가정자체를 살짝 잘못해서 틀렸었었음
 */
public class Main {
    public static int n , k , letterSize = 26 , ans = 0;
    public static char[] basic = {'a' , 'n' , 't' , 'i' , 'c'};
    public static int[] letter = new int[letterSize];
    public static String[] word;
    public static void dfs(int depth , int remain){
        /*
        먼저 depth 가 26이 되면 그니까 알파벳 z까지 각각 임무를 할당 했다면
        remain == 0 즉 , 알파벳을 k개 만큼 배운 경우에만 check를 해준다 , 읽을 수 있는 단어를
        그냥 이제 letter[depth] == -1 즉 , a , n , t , i , c 의 순서를 만난다
        그러면 그냥 넘어간다 5개나 줄이면서 경우의 수를 훨씬 줄일 수가 있다.
        그리고 이제 for(int i = 0; i < 2; i++) 로 선택하는 경우 , 아닌 경우를 해서
        선택하는 경우에는 visited[depth] = 1 끝내고 돌아왔을 때도 고려해서 visited[depth] = 0까지 해주고
        dfs 를 호출하면서 depth +  1 를 해주고 , remain - 1 도 해준다 , 왜냐하면 배우는 경우니까
        아닌 경우는 너무 쉽게 그냥 depth + 1 만 해줘서 dfs호출해주면 된다.

        근데 이전에 잘 못 생각했었던 게 이미 배운 단어들을 제외하고 모두 배울 수 있었어야 했는데
        생각을 잘 못해서 나온 문자들만 배울 수 있게 설정을 했었다
        그러면 이제 뭐 7개의 문자들만 들어왔는데 20 개를 배우라고 하면 못배우는 것이 되는 것이다.
        그러면 안되기 때문에 , 그냥 애초에 기본으로 배운 단어만 다시 안배우게끔 하고
        딱 k개의 수만큼 배웠을 때에만 연산을 처리하였다.
         */
        if(depth == letterSize){
            if(remain == 0) check();
            return;
        }

        if(letter[depth] == -1){
            dfs(depth + 1 , remain);
            return;
        }

        for(int i = 0; i < 2; i++){
            if(i == 0) { // 선택하지 않음
                dfs(depth + 1, remain);
            }
            else{ // 선택함
                if(remain > 0) {
                    letter[depth] = 1;
                    dfs(depth + 1, remain - 1);
                    letter[depth] = 0;
                }
            }
        }
    }
    public static void check(){
        /*
        word를 하나하나씩 읽으면서
        letter[string.charAt(j)] == 1 이 아니면 못 읽는 단어이니까
        넘어가고 계속 하나하나씩 읽고
        읽을 수 있는 단어개수를 ans 와 비교해서 갱신한다.
         */
        int count = 0;
        for(int i = 0; i < n; i++){
            String string = word[i];
            boolean able = true;
            for(int j = 0; j < string.length(); j++){
                if(letter[string.charAt(j) - 97] != 1){ // 1이 아니면 넘어가야함 , 끝임
                    able = false;
                    break;
                }
            }
            if(able) count++;
        }

        ans = Math.max(ans , count);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        word = new String[n];
        for(int i = 0; i < 5; i++){
            letter[basic[i] - 97] = -1;
        }

        if(k < 5){ // k가 5 미만이 들어올 경우
            System.out.println(0);
            return;
        }

        if(k == 26){
            System.out.println(n);
            return;
        }

        for(int i = 0; i < n; i++){
            String string = input.readLine();
            String tempString = string.substring(4 , string.length() - 4);
            String inputString = "";
            for(int j = 0; j < tempString.length(); j++){
                char character = tempString.charAt(j);
                boolean insert = true;
                for(int c = 0; c < 5; c++){
                    if(basic[c] == character){
                        insert = false;
                        break;
                    }
                }
                if(insert) {
                    inputString += character;
                }
            }
            word[i] = inputString;
        }
        System.out.println(Arrays.toString(word));
//        System.out.println(Arrays.toString(letter));
        dfs(0 , k - 5);
        System.out.println(ans);
    }
}
