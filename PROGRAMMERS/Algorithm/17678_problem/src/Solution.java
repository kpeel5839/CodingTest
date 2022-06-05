import java.util.*;
import java.io.*;

class Solution {
    /*
    일단 이 문제에서 중요한 점은...
    근데 너무 귀찮네 내일하자!

    일단, 나는 이번에 프로그래머스 문제의 레벨이 굉장히 애매하게 먹어있다라는 것을 알 수 있었다.

    이 문제는 이렇게 푸는 문제인 것 같다.
    일단, 셔틀 운행은 언제 되냐
    그것이 중요하다.

    셔틀 버스 운행은 일단 09:00 시에 시작이 된다.
    그 다음에, t 간격 단위로 오고
    그리고 m 명을 태워간다.

    그래서 절대적인 값으로 변경을 해보면
    9 * 60 = 540 이다.
    즉, 시작시간은 무조건 540 인 것이다.

    여기서 세워야 할 가정, 문제 풀이 과정은 이러하다. (내가 생각한)

    일단 셔틀버스는 무조건 적으로 마지막 버스를 타야한다.
    즉, 그렇다라는 것은, 버스를 태울 수 있는 애들만 태운 다음에 마지막 버스를 태울 때,
    그때 셔틀버스를 탈 수 있냐 없냐에 따라서 선택해야 하는 것이다.

    일단, 셔틀버스에 태우는 과정은 이러하다 (마지막 버스가 아니라는 가정하에)
    일단 Pointer 를 하나 둔 다음에
    현재 버스 시간 이하에 부터 선 놈들을 다 뺀다 (일단 대기 시간 순으로 정렬을 진행해야 한다.)

    그 다음에, 맨 마지막 버스일 때,
    즉, for (int i = 0; i < n; i++)  if (i == n - 1) 이라든가 while 로 처리할 때에 있어서
    마지막 버스라면, 처리를 따로 해주어야 한다.

    이떄, 두가지의 상황이 주어진다.
    이미 대기하던 사람들로 인해서, 버스를 못타는 경우
    혹은 대기하던 사람이 별로 없어서 버스가 꽉 차지 않는 경우
    후자는 너무 쉽다 그냥, 해당 버스의 시간을 출력하면 된다.

    첫번째의 경우는 가장 대기시간이 높은 애를 기준으로
    -1 시간을 하여 버스에 탑승하면 된다.

    이렇게 하면 문제를 풀수 있을 것 같다.

    역시 생각보다 너무 쉬운 문제여서 점수 족므 준다.
    이거 3레벨이였는데 분명히..
    */

    public String solution(int n, int t, int m, String[] timetable) {
        int answer = 0;
        int BASIC = 9 * 60; // 버스 운행 시작 시간
        int[] table = new int[timetable.length];

        for (int i = 0; i < timetable.length; i++) { // 일단 배열을 int 형으로 받아서 정렬을 진행해야 한다.
            String[] input = timetable[i].split(":"); // : 를 기준으로 시간과 분을 나눔

            table[i] = (Integer.parseInt(input[0]) * 60) + Integer.parseInt(input[1]); // 시간을 파싱해서 보여준다.
        }

        Arrays.sort(table); // 오름차순으로 정렬
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1); // 내림차순으로 정렬해줌

        int bus = BASIC;
        int pointer = 0; // 현재 탑승할

        for (int i = 0; i < n; i++) {
            int board = 0; // 현재까지 탑승한 사람
            if (i != n - 1) { // 그냥 pointer 만 증가시켜줄 부분
                while (pointer != table.length && board != m && table[pointer] <= bus) {
                    pointer++; // 탑승한 사람들은 건너뜀
                    board++; // 탑승한 사람 수 올리고
                }
            } else { // 맨 마지막
                while (pointer != table.length && board != m && table[pointer] <= bus) { // 또 똑같이 해준다.
                    queue.add(table[pointer]); // 근데 똑같이 다 해주는데 여기에서는 queue 를 add 해줄 것이다.
                    pointer++;
                    board++;
                }

                if (board != m) { // 탑승한 사람이 m 명이 아니라면, 즉 자리가 남아있으면
                    answer = bus;
                } else { // 자리가 남아있지 않으면
                    answer = queue.peek() - 1; // 제일 높은 시간을 가진 아이에서 -1 을 해준다.
                }
            }

            bus += t;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(((answer / 60) + "").length() == 1 ? ("0" + (answer / 60)) : ((answer / 60) + ""));
        answer -= (answer / 60) * 60;
        sb.append(":").append((answer + "").length() == 1 ? ("0" + answer) : (answer + ""));

        return sb.toString();
    }
}