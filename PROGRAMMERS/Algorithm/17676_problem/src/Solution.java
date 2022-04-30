import java.util.*;
import java.io.*;

class Solution {
    public int solution(String[] lines) throws IOException {
        /*
        일단, 무조건 3자리이니까, 절대적인 ms 로 바꾸면 1초 = 1000
        60초 = 60000, 1시간 = 3600000, 24시간 = 86400000
        이다, 일단 1억번 안된다. 연산횟수는 다 합쳐서 1억번 정도 될 것 같다 최대의 경우는
        
        그래서, 일단 그런 식으로 할 수 있고,
        입력들을 파싱하는 것이 중요하다.
        일단, 무조건 문자열들을 다 앞에 11글자를 때주고, 뒤에 s 도 때준다.
        그 다음에 : 를 기준으로 나눈다.
        그 다음에 공백을 기준으로 나눈다.
        
        이러면 각 시간, 분, 초, 연산시간 등을 구할 수 있다.
        그리고 1초동안의 최대 처리량이다.
        그렇기 때문에, 만일 2초 길이의 연산이 있었다고 가정했을 때
        절대적인 시간 1, 2, 3, 4 가 존재했다고 가정하면
        2, 3 초에 연산이 실행되었다고 가정해보면, 이 연산을 1초 범위내로 포함시킬 수 있는 수는
        초는 1, 2, 3 이다.
        
        즉, 그러면 이렇게 된다. 시작 시간, 즉 밀리세컨드까지 포함한 시각을
        절대적으로 변경한다, 시간까지 따져서
        
        엔딩 시간 - (걸린 시간 + 1) - (1000 + 1) ~ 엔딩시간 
        
        이런식으로 진행하여서, 가능하다. 그렇게 한번 해보자.
        */
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].substring(11, lines[i].length() - 1);
        } // 필요한 정보만 남기고 다 자름

        int[] count = new int[1000 * 60 * 60 * 24];
        int res = 0;

        for (int i = 0; i < lines.length; i++) { // 이제 연산을 시작할 것임
            String parsingString = lines[i];
            String[] seperate = parsingString.split(":");

            int hour = Integer.parseInt(seperate[0]);
            int minute = Integer.parseInt(seperate[1]);

            seperate = seperate[2].split(" "); // 공백으로 구분해서, 초와, 시간을 구분

            double second = Double.parseDouble(seperate[0]);
            double runTime = Double.parseDouble(seperate[1]); // 파싱도 완벽하게 됐음

            // 이제 시각들을 다 절대적인 시간으로 바꿈
            // 그 다음에 엔딩 시간 - (걸린 시간 + 1) - (1000 + 1) ~ 엔딩시간  를 진행해주면 됨
            // 근데, 시작이 음수가 되면 걍 0부터 시작하면 된다.
            int absTime = (hour * (1000 * 60 * 60)) + (minute * (1000 * 60))
                    + ((int) (second * 1000)); // endTime
            int start = absTime - ((int) (runTime * 1000) - 1) - (1000 - 1); // 역시 내 계산이 맞았었는데, 안에서 - 1 을 해줘서 999 를 빼주어야 했는데 그렇게 안했음

            if (start < 0) {
                start = 0;
            }

            for (int j = start; j <= absTime; j++) {
                count[j]++;
            }
        }

        for (int i = 0; i < count.length; i++) {
            res = Math.max(res, count[i]);
        }

        return res;
    }
}