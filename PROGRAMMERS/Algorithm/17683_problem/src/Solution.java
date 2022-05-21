import java.util.*;
import java.io.*;

class Solution {
    static void parsing(String m, List<String> list) { // 문자열을 파싱해주는 method
        for (int i = 0; i < m.length(); i++) { // m length 로 다 돌아봄
            String c = Character.toString(m.charAt(i)); // String 으로 넣어줌

            if (c.equals("#")) { // 앞에서 # 은 이미 같이 해서 list에다가 넣어놓을 것임
                continue;
            }

            if (i != (m.length() - 1)) { // 이게 아니면 뒤에 문자를 검사해서 # 도 같이 넣어주어야 함
                if (m.charAt(i + 1) == '#') { // 이게 맞으면 c 에다가 추가해줌
                    c += "#";
                }
            }

            list.add(c); // 위의 조건들이 다 아니다? 그러면 그냥 그 알파벳임 list 에다가 추가하자
        }
    }

    public String solution(String m, String[] musicinfos) {
        /*
        일단 사용되는 음계는 
        C, C#
        D, D#
        E
        F, F#
        G, G#
        A, A#
        B
        가 있다. 이것을 보고서 각각 한 음계를 파싱하면 될 것 같다.
        사실 그렇게 효율성을 중시하지 않으면 굳이 kmp 알고리즘을 사용하지 않아도 될 것 같고
        그리고 무엇보다 한글자가 이상한 것들도 많아서, 그 부분들을 고려해야 한다.
        
        m 을 먼저 파싱해야 할 것 같다.
        m 을 파싱하여서 어떤식으로 관리해야 할까?
        Map 에다가 이름 = List 로 관리하여서, 실제 노래들을 관리하는 것이 좋을 듯하다. (음계들의 모음)
        
        그리고 List 로 m 도 파싱해서 놓는 것이 좋을 것 같다.
        일단, 그냥 글자가 나왔을 때에 무조건 뒤에 문자까지 확인해준다.    
        */
        String answer = "(None)";
        List<Object[]> res = new ArrayList<>();
        List<String> mList = new ArrayList<>();
        parsing(m, mList); // 파싱 완료

        for (int i = 0; i < musicinfos.length; i++) {
            String[] input = musicinfos[i].split(",");
            Object[] put = new Object[3];

            int during = ((Integer.parseInt(input[1].split(":")[0]) * 60)
                    + Integer.parseInt(input[1].split(":")[1]))
                    - ((Integer.parseInt(input[0].split(":")[0]) * 60)
                    + Integer.parseInt(input[0].split(":")[1])); // 시간계산

            List<String> parsingList = new ArrayList<>();
            List<String> putList = new ArrayList<>();

            parsing(input[3], parsingList);

            int index = 0;
            for (int j = 0; j < during; j++) {
                putList.add(parsingList.get(index));
                index = (index + 1) % parsingList.size();
            }

            put[0] = during;
            put[1] = input[2]; // 이름도 집어넣음
            put[2] = putList;

            res.add(put);
        }

        Collections.sort(res, (o1, o2) -> ((int) o2[0] - (int) o1[0]));
        boolean finish = false;

        for (Object[] o : res) {
            // 이제 여기서 비교만 해주면 됨
            int index = 0;
            List<String> compareList = (List) o[2];
            int start = 0;

            while (true) { // 비교를 시작해준다.
                if ((start + mList.size()) > compareList.size()) { // 검사할 수 있는 범위를 넘어가면 나가리데스네
                    break;
                }

                if (!(mList.get(index).equals(compareList.get(index + start)))) { // 틀린 경우
                    index = 0; // 다시 첫번째 문자부터 비교해주고
                    start++; // start++ 해줘서 compareList 는 다음 문자부터
                    continue;
                }

                index++; // 이번 
                if (index == mList.size()) {
                    finish = true;
                    break;
                }
            }

            if (finish) { // 끝이 났다.
                answer = (String) o[1]; // 답을 넣어준다.
                break;
            }
        }

        return answer;
    }
}