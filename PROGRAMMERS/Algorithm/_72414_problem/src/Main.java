import java.util.*;
import java.io.*;

// 3시 50분까지 풀고, 답 안나오면 답 보자.

/**
 * 답 안보고 그냥 풀었고,
 * 엄청 빨리 풀었는데, 광고시간을 잘못 측정함
 *
 * 예를 들어서 2초인데 1 ~ 3 초를 계산했음
 * 1 ~ 2 초 이런식으로가 아니라
 *
 * 이거 알아내느라 한 30분정도 소요되었고, 이거 해결하자말자 바로 풀렸음
음*/
class Solution {
    static int play;
    static int adv;
    static long[] sum;
    static long[] log;

    static int convertTime(String s) {
        String[] time = s.split(":");

        // 0 = 시, 1 = 분, 2 = 초
        return (Integer.parseInt(time[0]) * 60 * 60)
                + (Integer.parseInt(time[1]) * 60) + (Integer.parseInt(time[2]));
    }

    static void cal(String s) {
        // String s 가 주어지면 parsing 하여 log 에 시작시간에는 +1, 종료시간에는 -1 을 기록해놓는다.
        String[] time = s.split("-");

        log[convertTime(time[0])]++;
        log[convertTime(time[1])]--;

        // if (end != play) { // play 와 같지 않은 경우만
        //     log[end + 1]--;
        // }
    }

    static String turnString(int index) { // int 로 type casting 해서 받아온다.
        String hour = Integer.toString(index / (60 * 60));
        index %= (60 * 60);
        String minute = Integer.toString(index / 60);
        index %= 60;
        String second = Integer.toString(index);

        return (hour.length() == 1 ? "0" + hour : hour)
                + ":" + (minute.length() == 1 ? "0" + minute : minute) + ":"
                + (second.length() == 1 ? "0" + second : second);
    }

    public String solution(String play_time, String adv_time, String[] logs) {
        // playtime, advtime 을 초로 환산한다.
        play = convertTime(play_time);
        adv = convertTime(adv_time);

        sum = new long[play + 1];
        log = new long[play + 1];

        for (int i = 0; i < logs.length; i++) {
            cal(logs[i]);
        }

        long add = 0;
        // sum 처리를 해야함
        for (int i = 0; i <= play; i++) {
            add += log[i]; // 현재 누적시청자를 더해준다.
            sum[i] = add; // 현재 시각에 시청자를 구해준다.
        }

        // if (play_time.equals("00:00:10")) {
        //     System.out.println(Arrays.toString(sum));
        // }

        for (int i = 1; i <= play; i++) {
            sum[i] += sum[i - 1]; // 누적시청자를 구해준다.
        }

        long maxIndex = 0;
        long max = 0;

        for (int i = adv - 1; i <= play; i++) {
            if (i == adv - 1) {
                max = sum[i];
                maxIndex = 0;
                // System.out.println((i - adv + 1) + " ~ " + i + " 까지 누적시청자수 : " + sum[i]);
            } else {
                long audience = sum[i] - sum[i - adv];

                if (max < audience) {
                    max = audience;
                    maxIndex = i - adv + 1;
                }

                // if (play == 10) {
                //     System.out.println((i - adv + 1) + " ~ " + i + " 까지 누적시청자수 : " + (sum[i] - sum[i - adv]));   
                // }            
            }
        }

        // if (play_time.equals("00:00:10")) {
        //     System.out.println(play);
        //     System.out.println(adv);
        //     System.out.println(Arrays.toString(log));
        //     System.out.println(Arrays.toString(sum));
        // }

        String answer = turnString((int) maxIndex);
        return answer;
    }
}