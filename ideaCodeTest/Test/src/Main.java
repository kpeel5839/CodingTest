import java.util.Random;

/*
다음과 같은 스터디룸의 예약 신청 정보를 관리하는 프로그램을 작성하세요.
스터디룸은 1시에 문을 열고 6시에 문을 닫으며, 스터디룸이 문을 연 시간 내에 1시간 단위로 예약 가능하다.
각 예약은 예약 번호, 시작시간, 종료시간으로 이루어지며, 이 세가지 정보는 모두 정수형이다.

- 입력: 없음
- 출력: 전체 예약 정보, 예약 길이별 예약 건수

1. 메인 클래스
main 메소드에서  다음 작업을 차례대로 수행
(1) 크기가 n인 예약 배열을 생성 (단, n = 10)
(2) 예약 번호는 1, 2, 3..., 시작시간, 종료시간은 랜덤 값인 예약 객체를 n개 생성하여 배열에 저장
    이 스터디룸 정책에 맞도록 랜덤 값을 생성해야 함
    예약 확정이 아니라 예약 신청을 받는 것이므로 시간이 중복되어도 된다.
    힌트: 스터디룸 정책에 따르면 시작시간은 1~5이며, 종료시간은 시작시간+1~6이다.
(3) n개의 예약 각각에 대해 시작시간, 종료시간, 길이를 출력
(4) 각 길이의 예약이 몇개씩인지 구함 (중첩 반복문을 사용하지 말고 수행시간 복잡도가 O(n)이 되도록 작성해 볼 것)
    힌트: 각 길이(1~5)의 예약 갯수를 세기 위해 크기가 5인 count 배열을 이용
(5) 각 길이의 예약 수를 출력

2. Reservation 클래스 ********* lab1_2와 동일
정수형 예약 번호, 시작시간, 종료시간으로 구성된 예약을 표현하는 클래스
private 인스턴스 변수 :
       예약 번호(id), 시작시간(startTime), 종료시간(endTime)
       이 필드값의 타당성은 고려하지 말 것. 즉, 아무 정수값이라도 저장되도록 할 것
public 메소드 :
       생성자, getId, getStartTime, getEndTime를 정의하고
       길이를 조회하는 메소드, toString 오버라이드 등, 그밖의 메소드들은 각자 알아서 정의


- 실행 예:
hw1_2 : 홍길동
1 1시~3시 2시간
2 4시~6시 2시간
3 4시~5시 1시간
4 1시~6시 5시간
5 5시~6시 1시간
6 5시~6시 1시간
7 2시~6시 4시간
8 5시~6시 1시간
9 3시~6시 3시간
10 2시~6시 4시간
1시간 = 4
2시간 = 2
3시간 = 1
4시간 = 2
5시간 = 1

-----------------------------------
목적
- 알고리즘 과제 수행에 앞서 객체 배열 사용을 연습한다.
- 랜덤 넘버 생성을 연습한다.

-----------------------------------
유의사항
- 적절한 comment
- 들여쓰기 철저히 할 것
- 식별자 이름을 자바 관례에 맞게 적절히 붙일 것
- 프로그램 맨 앞에 과제코드와 본인의 이름을 출력할 것
- 입출력 형식을 실행 예와 동일하게 할 것

-----------------------------------
제출
- 소스코드파일(.java)
- 실행가능한 jar 파일(과제코드.jar)
- LMS 과제 제출시 설명란에 이번 과제 수행에 관해 다음 항목을 적으세요. (1점) -- 프로그램을 제출해야 1점
     (1) 소요 시간
     (2) 도움 받은 정도
     (3) 어려운 점
     (4) 느낀 점
 */

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("hw4_1 : 김자경");
        Random random = new Random();
        int n = 10;

        //(1) 크기가 n인 예약 배열을 생성 (단, n = 10)
        Reservation[] array = new Reservation[n];

        //(2) 예약 번호는 1, 2, 3..., 시작시간, 종료시간은 랜덤 값인 예약 객체를 n개 생성하여 배열에 저장
        for (int i = 0; i < array.length; i++) {
            int startTime = random.nextInt(5) + 1; //1~5
            int endTime = startTime + 1 + random.nextInt(6 - startTime); //2~6, 시작시간보다 최소 1 커야 함
            array[i] = new Reservation(i + 1, startTime, endTime);
        }

        //(3) n개의 예약 각각에 대해 시작시간, 종료시간, 길이를 출력
        for (int i = 0; i < array.length; i++)
            System.out.println(array[i].toString());

        //(4) 예약 길이를 기준으로 오름차순 정렬
        countingSort(array);

        //(5) 정렬된 n개의 예약 각각에 대해 시작시간, 종료시간, 길이를 출력
        System.out.println("길이 오름차순 정렬 결과 = ");
        for (int i = 0; i < array.length; i++)
            System.out.println(array[i].toString());
    }

    //Reservation형 배열을 매개변수로 받아 예약 길이(1~5시간)를 기준으로 오름차순 정렬 - 시작 복잡도가 O(n)이어야 함
    private static void countingSort(Reservation[] list) {
        // 정렬 결과는 int 가 아닌 Reservation 으로 받아야 합니다.
//        int[] result = new int[list.length]; // 정렬 결과
        Reservation[] result = new Reservation[list.length];

        // 1 부터 k 까지의 자연수는 1 ~ 5 이다 , 그렇기 때문에 5 크기의 배열을 선언하면 됩니다.
        int[] cnt = new int[5]; // 1부터 k까지의 자연수가 각각 몇 번씩 나타나는지 count

        // 0으로 초기화 , 초기화 안해도 됨 , 이미 선언하자말자 초기화 되어 있음
//        for (int i = 0; i <= list.length; i++) {
//            cnt[i] = 0;
//        }

        // 값이 i인 원소의 총 수
        for (int i = 0; i < list.length; i++) {
            cnt[list[i].getLength() - 1]++;
        }

        // i보다 작거나 같은 원소의 총 수 -> 누적 합 계산
        for (int i = 1; i < cnt.length; i++) {
            cnt[i] = cnt[i] + cnt[i - 1];
        }

        // 누적 합을 이용해서 최종적으로 정렬
        for (int i = list.length - 1; i != -1; i--) {
            result[cnt[list[i].getLength() - 1] - 1] = list[i]; // result의 cnt[list[i]]인덱스에 list[i]값 저장
            cnt[list[i].getLength() - 1]--;
        }

        // result 를 array 에 카피 (list 가 array 의 주소를 받았음 , call by reference)
        for (int i = 0; i < result.length; i++){
            list[i] = result[i];
        }
    }
}