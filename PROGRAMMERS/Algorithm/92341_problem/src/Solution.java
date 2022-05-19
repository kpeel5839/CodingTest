import java.util.*;
import java.io.*;

class Solution {
    /*
    HashMap 이 필요하다.
    <String, Integer> 로
    <차량번호, 비용> 이다.
    
    처음에 fees 가 주어지는데 [0] = 기본시간, [1] = 기본요금, [2] = 추가시간, [3] = 추가요금
    records 는 05:34 5961 IN 이런식으로 주어진다.
    split(" "); 으로 하면 3개로 나눠지고
    [0] 째를 split(":"); 로 한번 더하면 된다.
    
    그래서 [1] 값은 Integer 로 변경시킨다음에, 정렬해준뒤에 나중에 키 값으로 뽑아내면서 answer 에다가 담는다.    
    */
    static List<String> keyList;
    static HashMap<String, Integer> total;
    static HashMap<String, Integer> start;
    static int initTime;
    static int initCost;
    static int addTime;
    static int addCost;

    public int[] solution(int[] fees, String[] records) {
        keyList = new ArrayList<>();
        total = new HashMap<>();
        start = new HashMap<>();
        initTime = fees[0];
        initCost = fees[1];
        addTime = fees[2];
        addCost = fees[3];

        for (int i = 0; i < records.length; i++) { // 각 요소들을 파싱하여야 함
            String[] info = records[i].split(" "); // 문자열을 나눔
            String[] time = info[0].split(":");

            int minute = (Integer.parseInt(time[0]) * 60) + Integer.parseInt(time[1]); // 절대적인 시간을 계산

            if (info[2].equals("IN")) { // IN 인 경우
                start.put(info[1], minute);
            }

            if (info[2].equals("OUT")) { // OUT 인 경우
                // out 하면서, List 에다가 차 번호랑 cost 같이 집어넣음
                if (total.containsKey(info[1])) { // 이미 있는 경우와
                    int a = total.get(info[1]); // 원래 시간 얻고
                    total.put(info[1], a + (minute - start.get(info[1]))); // 그간 있던 시간 추가적으로 더해줌
                } else { // 이미 있지 않은 경우
                    total.put(info[1], minute - start.get(info[1])); // 그냥 시간 차를 넣어준다.
                }
                start.put(info[1], -1); // 나왔다고 처리
            }
        }

        for (String key : start.keySet()) { // 여기서 없던 것들은 추가적으로 total 에다가 저장
            keyList.add(key);

            if (start.get(key) != -1) { // 아직 안빠진놈들인 경우, total 에다가 추가해주어야 한다 있다면
                int additional = (23 * 60 + 59) - start.get(key);
                if (total.containsKey(key)) { // 이미 있다면 추가
                    total.put(key, additional + (total.get(key)));
                } else { // 없다면 새로 만들어줌
                    total.put(key, additional);
                }
            }
        }

        Collections.sort(keyList);
        int[] answer = new int[keyList.size()];
        int index = 0;

        for (String key : keyList) {
            int additional = total.get(key) - initTime; // 총 시간을 initTime 에서 뺀다.
            int value = initCost;

            if (additional > 0) { // 추가적인 요금 계산
                value += Math.ceil((float) additional / (float) addTime) * addCost;
            }

            answer[index++] = value;
        }

        return answer;
    }
}