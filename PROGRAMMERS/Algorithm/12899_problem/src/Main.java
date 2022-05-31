import java.util.*;
import java.io.*;

class Solution {
    /*
    124 나라라고 한 이유가 있는 것 같다
    이거는 그냥 3진법임
    
    그냥 해당 숫자를 계속 빼 본다.
    어떻게?
    -3, -9, -27 ... 이런식으로 
    그러면 0 이하가 되는 순간
    그 순간이 바로 숫자의 자리가 정해지는 순간이다.
    
    그러면 그 숫자의 자리부터 3 ^ (숫자의 자리 - 1) 로 나눠보는 것이다.
    그러면 결국 string 으로 이어 붙히다 보면 답이 나올 수 밖에 없다.
    
    이 순으로 진행하면 될 것 같다.
    
    이 문제를 해결하는 방법으 쉬웠다...
    그냥 다 1을 채워넣고 시작했으면 됐었다.
    그러니까 바로 풀렸다
    */
    public String solution(int n) {
        String answer = "";

        int digit = 0;
        int number = n;
        while (number > 0) { // n 을 계속 빼가면서 자릿수를 구해줄 것이다.
            digit++;
            number -= Math.pow(3, digit);
        }
        

        int[] ans = new int[digit];

        for (int i = 0; i < ans.length; i++) {
            ans[i] = 1;
            n -= Math.pow(3, i);
        }

        // System.out.println(n);

        while (digit != 0) { // 이제 여기서 품앗이 가능
            int plus = n / (int) Math.pow(3, digit - 1);

            ans[digit - 1] += plus;
            n -= (int) Math.pow(3, digit - 1) * plus;
            if (ans[digit - 1] == 3) {
                ans[digit - 1]++;
            }
            digit--;
        }

        for (int i = 0; i < ans.length; i++) {
            answer = Integer.toString(ans[i]) + answer;
        }

        return answer;
    }
}