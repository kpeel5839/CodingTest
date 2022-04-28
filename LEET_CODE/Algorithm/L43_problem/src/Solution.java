import java.util.*;
import java.io.*;

class Solution {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] input = br.readLine().split(" ");
        System.out.println(multiply(input[0], input[1]));
    }

    public static String multiply(String num1, String num2) {
        if (num1.length() < num2.length()) { // num1이 더 커야하는데, num2가 더 크면
            String tmp = num1;
            num1 = num2;
            num2 = tmp;
        } // swap

        String res = "0";
        String[] cal = new String[num2.length()]; // 이걸로, 계산을 하면서, 한자리 한자리의 곱 값들을 저장

        Arrays.fill(cal, "0");
        int digit = 0;

        for (int i = num2.length() - 1; i != -1; i--) { // 이제부터 계산을 시작할 것임
            int mul = num2.charAt(i) - '0';

            for (int j = 0; j < mul; j++) { // 곱할 수 만큼 더해준다, 이건 당연한 것
                cal[i] = add(cal[i], num1); // 계속 계산해서 집어넣음
            }

            for (int j = 0; j < digit; j++) {
                cal[i] += "0";
            }

            digit++;
        }

        for (int i = cal.length - 1; i != -1; i--) {
            res = add(cal[i], res);
        }

        return res;
    }

    public static String add(String num1, String num2) {
        boolean presen = false; // 올림이 발생하였을 때, true 로 만들어준다.
        String res = "";

        if (num1.length() < num2.length()) { // num1이 더 커야하는데, num2가 더 크면
            String tmp = num1;
            num1 = num2;
            num2 = tmp;
        } // swap

        int diff = num1.length() - num2.length();
        for (int i = 0; i < diff; i++) {
            num2 = "0" + num2;
        }

        // 나는 무조건 num1 을 크게 넘길 것임, 자릿수는 적어도 num1 이 무조건 많거나, 같음
        for (int i = num1.length() - 1; i != -1; i--) {
            int number1 = num1.charAt(i) - '0';
            int number2 = num2.charAt(i) - '0';

            int add = number1 + number2; // 더해주고

            if (presen) {
                add++;
            } // 이전에 올림수가 설정이 되어 있었다면, 1을 더 더해줌

            presen = false; // 올림수 더해주었으니까, 이제 presen 사용했으니 false 로 만들어준다.

            if (add >= 10) { // add 가 10 이상이다. presen = true 로 만들어주고, 첫째자리만 가져감
                presen = true;
                add %= 10;
            }

            res = add + res;
        }

        if (presen) {
            res = "1" + res; // 하나 추가, 끝났는데, presen 남아있으면
        }

        return res;
    }
}