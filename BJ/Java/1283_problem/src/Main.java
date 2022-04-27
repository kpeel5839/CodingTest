import java.util.*;
import java.io.*;

// 1283 : 단축키 지정
/*
-- 전제조건
1. 총 n개의 옵션이 주어진다.
2. 각 옵션들은 한 개 또는 여러개의 단어로 옵션의 기능을 설명하여 놓았음
3. 대표 알파벳을 지정해서 단축키를 지정하기로 하였다.
4. 일단 제일 우선순위는 왼쪽에서 오른쪽부터 각 단어의 첫 글자가 우선순위가 제일 높고
5. 만일 그게 다 이미 존재하는 단축키이면 그냥 순서대로 다 탐색한다.
6. 대소문자는 구분하지 않는다.
7. 그래서 단축키들이 겹치지 않게 출력하라
-- 틀 설계
1. 입력을 받는다.
2. 각 단축키를 나타내는 char[] shortCut 배열을 만든다.
3. 그리고 HashSet을 이용해서 현재까지 해당 알파벳이 단축키로 지정된적이 있나 본다.
4. 그렇게 해서 단축키가 다 지정이 되면
5. 출력할 때 해당 배열의 단축키가 나올때까지는 그냥 출력하고 해당 단축키가 나오면 []로 감싼다.
 */
public class Main {
    public static void main(String[] args) throws IOException{
        /*
        1. 일단 각 option들을 다 받는다 , 단어 별로 나눠서
        2. 그리고서 5개의 단어가 최대이니 , 5개의 단어가 아닌 것들은 배열의 끝에 null로 찼을 것임
        3. 그럼 null 나오면 그냥 break; 하면 된다.
        4. 일단 그래서 하나하나씩 한 option에 단어들을 돌면서 계속 shortCutList 에 없는 것을 찾는다.
        5. 일단 첫번째 글자에서 찾는다? 그러면 바로 그냥 char hotKey 에다가 저장하고 바로 빠져나온다.
        6. 근데 이제 계속 단어가 넘어갈 텐데 , 일단은 왼쪽에서 오른쪽으로 오면서 찾을 때 그냥 첫번째 글자에서 찾으면 find = true로 만들고 바로 break 하고
        7. 첫번째 글자가 아니면 find = true로 만들고 계속 그래도 null인 것을 만나기 전까지 돈다 , (왜냐하면 다른 단어의 첫글자에서 hotKey를 찾을 수 있어서)
        8. 그니까 find = true이면 첫번째 글자 아니면 그냥 다 넘어가는 것임
        9. 그래서 find = true이면 hotKey 를 shortCutList.add 하고 해당 index 의 shortCut 배열에다가 집어넣는다.
        10. 그리고서 나중에 다 찾고 나면 shorCut 이 null 인 인덱스는 그냥 넘어가고 , 아니면 계속 찾다가 (이때도 find 재사용) 찾으면 앞 뒤에다가 [] 출력하고 그냥 끝까지 출력하면 됨, 단어 사이사이에 공백 출력은 잊으면 x
        11. 그리고 해당 단축키가 단어의 첫번째인지 아닌지 판단할 수 있도록 firstLetter[] 배열도 추가한다.
         */
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = Integer.parseInt(input.readLine());
        String[][] option = new String[n][5];
        char[] shortCut = new char[n];
        HashSet<Character> shortCutList = new HashSet<>();
        int[] firstLetter = new int[n];

        for(int i = 0; i < n; i++){
            int index = 0;
            st = new StringTokenizer(input.readLine());
            while(st.hasMoreTokens()){
                option[i][index++] = st.nextToken();
            }
        }

        boolean find;
        char hotKey = 0;

        for(int i = 0; i < n; i++){
            find = false;
            Loop:
            for(int j = 0; j < 5; j++){
                if(option[i][j] == null){
                    break;
                }
                for(int c = 0; c < option[i][j].length(); c++){
                    char character = Character.toLowerCase(option[i][j].charAt(c));
                    if(c == 0){
                        if(!shortCutList.contains(character)){
                            find = true;
                            hotKey = character;
                            firstLetter[i] = 1;
                            break Loop;
                        }
                    }else{
                        if(find){
                            continue;
                        }
                        else if(!shortCutList.contains(character)){
                            find = true;
                            hotKey = character;
                        }
                    }
                }
            }
            if(find){
                find = false;
                shortCutList.add(hotKey);
                shortCut[i] = hotKey;
//                System.out.println(hotKey);
            }
        }

        for(int i = 0; i < n; i++){
            find = false;
            for(int j = 0; j < 5; j++){
                if(option[i][j] == null){
                    break;
                }
                for(int c = 0; c < option[i][j].length(); c++){
                    char character = Character.toLowerCase(option[i][j].charAt(c));
                    if(find){
                        System.out.print(option[i][j].charAt(c));
                    }
                    else if(character == shortCut[i]){
                        if(firstLetter[i] == 1){
                            if(c == 0){
                                System.out.print("[" + option[i][j].charAt(c) + "]");
                                find = true;
                            }else{
                                System.out.print(option[i][j].charAt(c));
                            }
                        }else {
                            System.out.print("[" + option[i][j].charAt(c) + "]");
                            find = true;
                        }
                    }else{
                        System.out.print(option[i][j].charAt(c));
                    }
                }
                if(j != 4){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
