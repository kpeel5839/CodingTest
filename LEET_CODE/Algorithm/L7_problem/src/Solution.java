import java.util.*;
import java.io.*;

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(reverse(Integer.parseInt(br.readLine())));
    }
    public static int reverse(int x) {
        String s = Integer.toString(x); // String 으로 일단 변환

        String resOfString = "";
        int res = 0;
        boolean nega = false;

        if (s.charAt(0) == '-') {
            s = s.substring(1);
            nega = true;
        }

        for (int i = s.length() - 1; i != -1; i--) {
            char let = s.charAt(i);

            if (resOfString.equals("") && let == '0') {
                continue;
            }

            resOfString += let;
        }

        if (nega) {
            resOfString = "-" + resOfString;
        }

        if (resOfString.isBlank()) {
            res = 0;
        } else {
            if (Long.parseLong(resOfString) < Integer.MIN_VALUE || Long.parseLong(resOfString) > Integer.MAX_VALUE) {
                res = 0;
            } else {
                res = Integer.parseInt(resOfString);
            }
        }

        return res;
    }
}