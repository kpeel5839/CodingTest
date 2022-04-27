import java.util.*;
import java.io.*;

// 19583 : 싸이버 개강총회
/*
-- 전체 설계
사이버 개강총회가 열리고
개강총회 시작전 1시간 부터 시작까지 즉
개강총회 시작 시간 - 1hour <= 채팅 친 시간 <= 개강총회 시작시간 이다. (이러면 온 것이 인정 1단계만)
그리고서 , 또 채팅을 쳐야하는데
개강총회 끝낸 시간 <= 채팅 친 시간 <= 개강총회 종료시간
이다.
이때 출석을 완벽하게 마친 학생은 몇명인가?
-- 틀 설계
마지막 입력은 공백으로 구분이되고,

그냥 쉽게 출근 = 맞는 시간에 온거 , 퇴근 = 맞는 시간에 나간거 라고 정의하고 시작하자
일단 , 출근을 한 아이들을 보면 될 것 같다.
출근을 완료한 아이는 'A' 를 준다.
그 다음에 HashMap 에다가 A로 집어넣고
그 다음에 시간을 벗어난 애들은 아얘 주지 않는다.

채팅 시간이 오름차순으로 주어지는 것은 솔직히 그다지 상관 없을 것 같다.
계속 그냥 start <= x <= end 로 비교하면 되니까
그래서 출근을 완료한 아이는 'A' 를 주고

그리고 , 퇴근한 애들은 hashMap 에 있나보고 , 있으면 걔가 'A'인지 아니면 'C' 인지 본다.
'A' 라면 'C' 로 바꿔준다.
그래서 이렇게 바꿔주면서 , 애들을 세는데 , 그 결과값을 출력하면 된다.

시간 비교는 일단 , 시작 , 총회 종료 , 스트리밍 종료로 나눠서 받은 다음에
여기서 , 시작 시간과 , 엔딩 시간 , 그리고 현재 주어진 시각을 하면
true , false 를 반환하는 timeCheck 함수를 만든다.
 */
public class Main {
    public static boolean timeCheck(int sh, int sm , int fh , int fm , int gh , int gm){
        /*
        sh , sm -> 시작 hour , minute
        fh , fm -> 종료 hour , minute
        gh , gm -> 확인할 hour , minute

        일단 sh <= gh && sm <= gm 이여야 한다.
        일단 이 것은 무조건 만족해야 한다 , 무조건 sh 가 더 작으니까
        근데 sh 는 만족인데 , sm 이 애매하다.

        11:40 ~ 12:30
        10 시 들어오면 탈락
        11시 혹은 12 시여야 함
        정확히 하면 sh <= gh <= fh 여야 한다.
        그리고 minute 이 문제인데

        시간이 sh 와 같다? 그러면 sm 보다 커야함
        시간이 sh 보다 크다? 그러면 fh 보다 작아야함

        이런식으로 정의 가능할 듯
        (sh == gh && sh != fh && sm <= gm)
        (sh == gh && sh == fh && sm <= gm <= fm)
        (sh != gh && gh != fh) // sm 신경 x
        (sh != gh && gh == fh && gm <= fm) 이렇게 되어야함

        지금으로써는 생각해볼 수 있는 경우의 수는 이 4가지이다.
         */
        if(!(sh <= gh && gh <= fh)) return false; // 시간 범위부터 벗어남
        if(sh == gh){
            if(sh != fh && sm <= gm) return true;
            if(sh == fh && sm <= gm && gm <= fm) return true;
        }
        else{ // sh != gh 일 때
            if(gh != fh) return true;
            if(gh == fh && gm <= fm) return true;
        }
        return false;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine());

        HashMap<String ,Character> attend = new HashMap();
        int ans = 0;

        String start = st.nextToken();
        String finish = st.nextToken();
        String streamFinish = st.nextToken();

        int sh , sm , fh , fm , sth , stm;

        st = new StringTokenizer(start , ":");

        sh = Integer.parseInt(st.nextToken());
        sm = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(finish , ":");

        fh = Integer.parseInt(st.nextToken());
        fm = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(streamFinish , ":");

        sth = Integer.parseInt(st.nextToken());
        stm = Integer.parseInt(st.nextToken());

        while(true){
            String inputValue = input.readLine();

            if(inputValue == null || inputValue.isBlank()){ // BufferedReader 의 EOF 방법
                break;
            }

            st = new StringTokenizer(inputValue);

            String hour = st.nextToken();
            String name = st.nextToken();

            st = new StringTokenizer(hour , ":");

            int gh = Integer.parseInt(st.nextToken());
            int gm = Integer.parseInt(st.nextToken());

            if(timeCheck(0 , 0 , sh , sm , gh , gm)) { // 개강총회 출근 체크
                attend.put(name , 'A'); // 무지성으로 출근하면 A 주기 , 짜피 시간안에 들어가면 장떙
            }
            if(timeCheck(fh , fm , sth , stm , gh , gm)) { // 개강총회 끝나고 나서 , 퇴근 체크
                if(attend.containsKey(name)){ // name 있으면 한번 값을 봐서 'A' 면 'C' 로 다시 넣어주고
                    //ans 1 증가시키고 , 'A'가 아니면 끝
                    char value = attend.get(name); // name 이 이미 존재한다는 것은 이전에 채팅을 친것이고 , 그게 출석이라면 ? 그러면 인정인 것
                    if(value == 'A'){
                        ans++;
                        attend.put(name , 'C');
                    } // 아니면 아무것도 안해도 됨
                }
            }
        }

        System.out.println(ans);
    }
}