import java.util.*;
import java.io.*;

class Solution {
    public String[] solution(String[] record) {
        // 그냥 간단하게 Hash Map 을 사용하는 문제인 것 같다.
        // 사실 하나하나 나눌 필요 자체가 없다.
        // Enter answer 은 나중에 저장하고
        // Enter 인지, 아닌지 확인한 다음에, 계속 해당 uid 를 가진 유저의 닉네임, 즉 value 값만 변동시켜주면 된다.
        List<String[]> info = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < record.length; i++) {
            String[] parse = record[i].split(" ");

            if (!parse[0].equals("Change")) { // change 일 때에는 그냥 변경한 것임, 나갔다 들어온게 아니니까
                info.add(new String[] {parse[0], parse[1]}); // res 에다가 Enter, Leave 인지 구분 하고, userid 를 입력해놓는다.        
            }

            if (parse[0].equals("Enter") || parse[0].equals("Change")) {
                map.put(parse[1], parse[2]);
            }
        }

        String[] res = new String[info.size()];
        // System.out.println(map);

        int index = 0;
        for (String[] s : info) {
            String suffix = s[0].equals("Leave") ? "나갔습니다." : "들어왔습니다.";
            res[index++] = map.get(s[1]) + "님이 " + suffix;
        }

        return res;
    }
}