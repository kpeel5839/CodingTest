import java.util.*;
import java.io.*;

// 25501 : 재귀의 귀재

/**
 * 5
 * AAA
 * ABBA
 * ABABA
 * ABCA
 * PALINDROME
 */
public class Main {
    static int cnt = 0;
    public static int recursion(String s, int l, int r){
        cnt++;
        if(l >= r) return 1;
        else if(s.charAt(l) != s.charAt(r)) return 0;
        else return recursion(s, l+1, r-1);
    }
    public static int isPalindrome(String s){
        return recursion(s, 0, s.length()-1);
    }

    public static void main(String[] args) throws IOException{
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_25501_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            System.out.print(isPalindrome(br.readLine()) + " " + cnt + "\n");
            cnt = 0;
        }
    }
}
