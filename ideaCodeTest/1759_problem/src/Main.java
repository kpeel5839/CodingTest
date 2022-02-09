import java.util.*;
import java.io.*;

// 1759 : 암호 만들기

/*
-- 전제조건
L 과 C가 주어지며 L은 암호의 길이
C는 암호를 이루고 있는 문자들이다.
꼭 최소 자음 두개와 , 한개의 모음으로 이루어져 있어야 하고 그럴 때 나올 수 있는
암호를 다 출력하시오.

문자열은 정리된 상태로 나오며 암호에 동일한 알파벳은 들어가지 않는다.
-- 틀 설계
받은 문자열을 정렬하고
dfs를 통해서 조합을 제시하고
제시한 조합으로 암호로 사용가능한지 확인한다.
암호로 사용 가능하다면 , output에 추가한다
-- 해맸던 점
또 반복문에서 이상한 짓거리 해서 해맸음
 */
public class Main {
    public static BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
    public static int l , c;
    public static char[] vowel = {'a' , 'e' , 'i' , 'o' , 'u'} , com , letter;
    public static void dfs(int idx , int count) throws IOException{
        /*
        count는 현재까지 포함된 알파벳을 의미하며 , count == l이 되면 확인하고 output에 추가한다.
        for 문에서는 무조건 idx 보다 이상인 것을 선택해야 한다.
         */
        if(count == l){
//            System.out.println(Arrays.toString(com));
            int vowelCount = 0;
            int remain = 0;
            for(int i = 0; i < l; i++) {
                for (int j = 0; j < 5; j++) {
                    if (vowel[j] == com[i]){
                        vowelCount++;
                        break;
                    }
                }
            }
            remain = l - vowelCount;
            if(remain >= 2 && vowelCount >= 1) {
                for(int i = 0; i < l; i++){
                    output.write(com[i] + "");
                }
                output.write("\n");
            }
            return;
        }
        for(int i = idx; i < c; i++){
            com[count] = letter[i];
            dfs(i + 1 , count + 1);
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        l = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        com = new char[l];
        letter = new char[c];
        st = new StringTokenizer(input.readLine());

        for(int i = 0; i < c; i++){
            letter[i] = st.nextToken().charAt(0);
        }

        Arrays.sort(letter);
        dfs(0 , 0);
        output.flush();
        output.close();
    }
}
