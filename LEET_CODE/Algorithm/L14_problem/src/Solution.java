import java.util.*;

// 14 : Longest common prefix

class Solution {
    public String longestCommonPrefix(String[] strs) {
        // 순서대로 아래로 내려가면서 동일한 것들만 가져가면 됨
        // 앞에서 부터
        // 그러면 선형시간 안에 해결 가능하다.
        String previousPrefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            String nowPrefix = "";
            for (int index = 0; index < Math.min(previousPrefix.length(), strs[i].length()); index++) {
                if (previousPrefix.charAt(index) == strs[i].charAt(index)) {
                    nowPrefix += previousPrefix.charAt(index);
                    continue;
                }

                break;
            }
            previousPrefix = nowPrefix;
        }

        return previousPrefix;
    }
}