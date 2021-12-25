import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        calculate(input.readLine());
    }
    /*
    -- 설계
    1. 일단 첫번째로 공백을 다 없앰
    2. 두 번째로 곱하기와 나누기 먼저 계산
    3. 세 번째로 더하기와 빼기를 계산한다.
    4. substring을 이용해서 제거하고
    5. while을 이용해서 루프를 돈다 int index = 0;으로 초기화하면서
    6. -를 찾아내는 방법도 생각해야 할 듯
    7. 아직은 조금 미숙해서 조금 더 생각해야 할 듯
     */
    public static int calculate(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int result = 0, record = 0, multOrDiv = 0, plusOrMinus = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int num = c - '0';
                for (i++; i < s.length() && Character.isDigit(s.charAt(i)); i++) {
                    num = num * 10 + s.charAt(i) - '0';
                }
                i--;
                if (multOrDiv == 1) {
                    record *= num;
                    multOrDiv = 0;
                } else if (multOrDiv == -1) {
                    if (num == 0) {
                        return 0;
                    } else {
                        record /= num;
                    }
                    multOrDiv = 0;
                } else {
                    record = num;
                }
            } else if (c == '*') {
                multOrDiv = 1;
            } else if (c == '/') {
                multOrDiv = -1;
            } else if (c == '+') {
                result += plusOrMinus * record;
                plusOrMinus = 1;
            } else if (c == '-') {
                result += plusOrMinus * record;
                plusOrMinus = -1;
            }
        }
        return result + plusOrMinus * record;
    }
}
