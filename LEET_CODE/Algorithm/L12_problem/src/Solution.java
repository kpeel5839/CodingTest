import java.util.*;
import java.io.*;

// 12 : Integer To Roman

class Solution {
    Map<Character, Integer> valueOfSymbol = new HashMap<>();;

    void makeValueOfSymbol() {
        valueOfSymbol.put('I', 1);
        valueOfSymbol.put('V', 5);
        valueOfSymbol.put('X', 10);
        valueOfSymbol.put('L', 50);
        valueOfSymbol.put('C', 100);
        valueOfSymbol.put('D', 500);
        valueOfSymbol.put('M', 1000);
    }

    boolean checkReverseOrdered(String stringToParse, int index) { // 순서가 뒤 바뀐놈
        int valueOfFirstLetter = valueOfSymbol.get(stringToParse.charAt(index));
        int valueOfSecondLetter = valueOfSymbol.get(stringToParse.charAt(index + 1));

        if (valueOfFirstLetter < valueOfSecondLetter) {
            return true;
        }

        return false;
    }

    public int romanToInt(String s) {
        // I, V, X, L, C, D, M 의 순서로 간다.
        // 근데 이 순서가 뒤 바뀌면 그것은 앞에 있는 것이 마이너스로 변한다고 생각하면 된다.
        int answer = 0;
        makeValueOfSymbol();

        for (int i = 0; i < s.length(); i++) {
            int nowValue = valueOfSymbol.get(s.charAt(i));

            if (i != s.length() - 1 && checkReverseOrdered(s, i)) {
                nowValue = valueOfSymbol.get(s.charAt(i + 1)) - valueOfSymbol.get(s.charAt(i));
                i++;
            }

            answer += nowValue;
        }

        return answer;
    }
}
