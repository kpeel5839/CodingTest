import java.util.*;
import java.io.*;

// 1019 : 책 페이지

/**
 * Example
 * 543212345
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("/Users/jaeyeonkim/Desktop/CodingTest/CodingTest/BJ/Java/_1019_problem/src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int number = Integer.parseInt(br.readLine());
        int MAX_DIGIT_LENGTH = 9;
        int DECIMAL = 10;
        long[][] numberOfDigit = new long[MAX_DIGIT_LENGTH + 1][DECIMAL];

        for (int digit = 1; digit <= MAX_DIGIT_LENGTH; digit++) {
            for (int i = 1; i < DECIMAL; i++) {
                numberOfDigit[digit][i - 1] += Math.pow(10, (digit - 1)) - 1;
                numberOfDigit[digit][i]++;
                numberOfDigit[digit][0] += digit - 1;

                for (int j = 0; j < DECIMAL; j++) {
                    numberOfDigit[digit][j] += numberOfDigit[digit - 1][j];
                }
            }

            numberOfDigit[digit][9] += (Math.pow(10, (digit - 1)) - 1);

            for (int j = 0; j < DECIMAL; j++) {
                numberOfDigit[digit][j] += numberOfDigit[digit - 1][j];
            }
        }

        long[] numberOfDigitInNumber = new long[DECIMAL];

        for (int digit = MAX_DIGIT_LENGTH; digit != -1; digit--) {
            int divideNumber = (int) Math.pow(10, digit);
            int divideResult = (number / divideNumber) % 10;

            for (int i = 1; i <= divideResult; i++) {
                // 여기까지는 그냥 numberOfDigit 을 이용해서 계산해준다.
                numberOfDigitInNumber[i]++;
                numberOfDigitInNumber[0] += digit;

                if (i != 1) {
                    numberOfDigitInNumber[i - 1] += (divideNumber - 1);
                }

                for (int j = 0; j < DECIMAL; j++) {
                    numberOfDigitInNumber[j] += numberOfDigit[digit][j];
                }
            }

            if (number >= divideNumber) {
                numberOfDigitInNumber[divideResult] += (number % divideNumber);
            }

            if (number >= divideNumber && divideResult == 0) {
                numberOfDigitInNumber[0] -= (divideNumber - 1);
            }
        }

        for (int i = 0; i < DECIMAL; i++) {
            System.out.print(numberOfDigitInNumber[i] + " ");
        }
    }
}
