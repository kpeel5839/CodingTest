import java.util.*;
import java.io.*;

public class Main2 {
    public static int getMax(String s) {
        int[] pi = new int[s.length()];
        int j = 0;
        int res = 0;

        for (int i = 1; i < s.length(); i++) {
            while(j > 0 && s.charAt(i) != s.charAt(j)) {
                j = pi[j - 1];
            }

            if (s.charAt(i) == s.charAt(j)) {
                pi[i] = ++j; // 돌아갈 수 있는 위치 등록해주고
                res = Math.max(res, pi[i]); // 반복되는 문자열 개수를 세어준 것이니까, res 에다가 저장(두번 반복)
            }
        }

        return res;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();
        int res = 0;

        for (int i = 0; i < s.length(); i++) {
           res = Math.max(res, getMax(s.substring(i)));
        }

        System.out.println(res);
    }
}
