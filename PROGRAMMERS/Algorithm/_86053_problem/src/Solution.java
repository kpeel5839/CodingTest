class Solution {
    /*
    일단 이 문제는 동시에 이동할 수 있다.
    즉, 동시에 움직여서 a, b 를 채울 수 있다라는 말이다.
    
    그러면 어떻게 채울 수 있을까?
    
    일단 g, s, w, t 의 길이가 10 의 5 승이다.
    그렇다라는 의미는 한번에 100000 개의 동작을 행할 수도 있다라는 말이다.
    
    이 문제는 답 안봤으면 평생 못풀었을 것 같다.
    
    걍 수학 문제였음
    이 문제의 핵심은 이것이다.
    a <= goldMax (gold 를 최대한 우선적으로 옮겼을 때)
    b <= silverMax (silver 를 최대한 우선적으로 옮겼을 때)
    a + b <= goldMax + silverMin = goldMin + silverMax = add
    이 조건 3개가 만족하면 주어진 단위 시간당 T 가 있으면 다 옮기는 것이 가능하다라는 것이다.
    
    이 생각을 솔직히 어떻게하냐...
    그래서 goldMax, silverMax 는 그냥 해당 시간당 몇번 왕복할 수 있는지 구한 뒤,
    W[i] * 왕복 횟수 를 곱해주면 된다.
    
    그래서 각 goldMax, silverMax 를 구해주고 (물론 W[i] * 왕복횟수가 본인이 가지고 있는 gold, silver 보다 많으면 그것을 채택해야함)
    옮길 수 있는 total 에다가 계속 더해준다.
    
    그 다음에 그 연산을 실행하고
    a <= goldMax && b <= silverMax && a + b <= add 가 맞으면 시간을 줄여도 되는 것이고
    안된다면 시간을 높여야 한다. (당연한 것, 시간을 높여야지 더 많이 옮길 수 있는 것이니까)
    
    내가 여기서 이해가 안가는 과정은 add 를 구하는 과정인데
    그냥 now_g + now_s 가 W[i] * 왕복횟수보다 작으면 now_g + now_s 아니면 W[i] * 왕복횟수 로 가는데
    이거는 그냥 goldMax + silverMin = goldMin + silverMax 이니, 그냥 총 가져갈 수 있는 양을 본다라는 것이 맞는 것 같다.
    골드를 최우선적으로 가져가든, 실버를 최우선적으로 가져가든 둘다 똑같으니 (총 양은) 그래서 그냥 저것을 더해주는 것 같다.
    
    진짜 이해가 안가서 솔직히 이 문제를 푸는 것은 의미가 없는 것 같다.
    
    뭔가 달달 외우고 푼 느낌이라서 기분 더러움
    
    */
    public long solution(int a, int b, int[] g, int[] s, int[] w, int[] t) {
        long answer = ((long) 1e9 * 2) * ((long) 1e5 * 2); // 최대 1e9 의 크기의 a, b 가 있고 w[i] 가 모두 1 그리고 time 이 모두 1e5 일 때 발생할 수 있는 가장 큰 값
        long right = answer;
        long left = 0;

        while (left <= right) { // 이분 탐색 시작
            long mid = (left + right) / 2;
            long goldMax = 0;
            long silverMax = 0;
            long add = 0;

            for (int i = 0; i < g.length; i++) {
                long nowGold = g[i];
                long nowSilver = s[i];
                long nowWeight = w[i];
                long nowTime = t[i];

                long moveCount = mid / (nowTime * 2); // 왕복 횟수
                if (mid % (nowTime * 2) >= nowTime) { // 왕복하고 그냥 가기만도 할 수 있음, 즉 한번 더 옮길 수 있는 것
                    moveCount++;
                }

                goldMax += Math.min(nowGold, nowWeight * moveCount);
                silverMax += Math.min(nowSilver, nowWeight * moveCount);
                add += Math.min(nowGold + nowSilver, nowWeight * moveCount); // 그냥 금, 은 합쳐서 얻을 수 있는 가장 큰 값을 얻는 것임, 이렇게 해도 가능한 이유가 a <= goldMax, b <= silverMax 일 때 (a + b) <= add 라는 가정이 있기 때문이다. (그냥 gold + silver 를 합쳐서 T 시간에 최대한 많이 가져올 때 얼마나 가져올 수 있는지 확인하는 것이 add 변수임)
            }

            if (a <= goldMax && b <= silverMax && (a + b) <= add) {
                right = mid - 1;
                answer = mid;
            } else {
                left = mid + 1; // 값을 높여서 가능하게끔 한다.
            }
        }
        return answer;
    }
}