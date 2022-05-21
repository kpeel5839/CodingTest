import java.util.*;
import java.io.*;

class Solution {
    /*
    Character.isAlphabetic()
    Character.toLowerCase()
    이 두개가 알파벳인지 판단과, 소문자로 반환을 해주게 된다.
    
    일단, 교집합 합집합을 계산하기 이전에 두 String 을 2개씩 끊어주어야 한다.
    그 다음에, 그 list 에서 겹치는 것들을 체크해야 한다.
    일단 집합으로 만든 것들을 정렬할 수도 있다.
    
    정렬한 뒤 같은 것들은 묶어서 체크도 가능하다.
    일단 각각의 map 으로 같은 문자열이 있는지 확인을 다 해야 하니까
    두 문자 -> 개수 로 맵핑한다.
    이렇게 각각의 str1, 2 에 대해서 map 을 만들고
    str1 map 을 쫘르륵 돌면서 str2 map 에 대해서도 본인의 key 에 있는 것이 있나 확인한다.
    만일 있다라면 min 값으로 하고 intersection 에다가 추가해준다
    합집합 연산은 평소에도 계속 더하다가 교집합이 나오는 순간 union 에다가 더해준다.
    그 다음에 str2 의 map 을 쫘르륵 돌아주면서 교집합 나오는 것들은 안더해주고 쭈루륵 간다.
    
    그런 다음 둘다 0인 경우
    intersection 만 0 인 경우는 그냥 0으로 출력하면 되고
    union 이 0 인 경우는 무조건 당연히 intersection 도 0이니 1로 결정하면 된다.
    
    확실히 이 문제는 방금 그곡보다는 쉬웠음
    */
    static void makeMap(String str, HashMap<String, Integer> strMap) {
        for (int i = 0; i < (str.length() - 1); i++) {
            String res = "";
            char c1 = str.charAt(i);
            char c2 = str.charAt(i + 1);

            if (!Character.isAlphabetic(c1) || !Character.isAlphabetic(c2)) { // 둘중 하나라도 알파벳이 아니라면 넘어간다.
                continue;
            }

            c1 = Character.toUpperCase(c1);
            c2 = Character.toUpperCase(c2);

            res = Character.toString(c1) + Character.toString(c2);
            if (strMap.containsKey(res)) {
                strMap.put(res, strMap.get(res) + 1);
            } else {
                strMap.put(res, 1);
            }
        }
    }

    public int solution(String str1, String str2) {
        int answer = 0;
        int mul = 65536;

        HashMap<String, Integer> str1Map = new HashMap<>();
        HashMap<String, Integer> str2Map = new HashMap<>();

        makeMap(str1, str1Map);
        makeMap(str2, str2Map);

        float intersection = 0f;
        float union = 0f;

        // map 은 구성했으니까 이제 교집합과 합집합을 찾아주면 된다.
        for (String key : str1Map.keySet()) { // 일단 교집합을 우선으로 찾는다.
            int a = str1Map.get(key); // 일단 개수 얻어 놓는다.
            int b = 0; // str2

            if (str2Map.containsKey(key)) { // str2 에도 있는 경우, 겹치는 것이다.
                b = str2Map.get(key);
                intersection += Math.min(a, b); // 교집합에는 작은 것
                union += Math.max(a, b); // 합집합에는 큰 것을 더해준다.
            } else {
                union += a; // 겹치는 게 없는 경우는 그냥 a 만 더해준다.
            }
        }

        for (String key : str2Map.keySet()) { // 여기서는 합집합만을 찾아서 더해준다.
            if (!str1Map.containsKey(key)) { // str1Map 에 없는 것만 union 에다가 더해준다.
                union += str2Map.get(key);
            }
        }

        if (union == 0 && intersection == 0) {
            answer = 1 * mul;
        } else {
            answer = (int) ((intersection / union) * mul) ;
        }

        return answer;
    }
}