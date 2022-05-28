class Solution {
    /*
    어떻게 할 수 있을까..
    일단, 키를 움직일 수 있는 선택지는, 시계방향 회전과
    반시계 방향 회전, 그리고 상 하 좌 우로 모두 움직이느 것이다.
    
    열쇠는 밖으로 애들이 빠져나갈 수 있는 것 같다.
    즉, 열쇠의 돌기들은 버릴 수 있다.
    일단, 자물쇠가 열리는 경우는 간단하다.
    
    그냥, 열쇠의 돌기가 자물쇠의 홈과 맞으면 된다.
    
    결굽 답을 봤음
    이 문제는 그 점을 이용해야 함
    처음에 내가 생각했던 영역을 넓히는 방법을 사용했었어야 했음
    
    열쇠는 밖으로 나갈 수 있는 것이 맞음
    
    그래서 이 문제의 핵심은,
    1. 영역을 넓힌다.
    2. 현재 key 에서, 270 도 회전된 key 까지 다 구해본다.
    3. 시작 지점을 설정하여서, 넓힌 영역에서 Lock 에 딱 맞지 않아도 좋으니 Lock 의 범위내에 key 가 들어가게 맞춰본다.
    4. 또 포인트는, key 를 Lock 에다가 더하면서, 진행하는 것 (홈에 들어가지 않은 경우도 걸러낼 수 있음)
    
    일단 영역을 넓힐 때에는
    lock 에 key 가 걸친 형태가 필요하다.
    그렇다라는 것은 L + (K * 2) - 2; 로 해주면 된다. Lock 의 크기 Key 의 크기와 겹치는 부분 2부분을 빼주면 된다.
    그러고서 포인트는 0, 0 에서부터 어디까지?
    K + L - 2 로 해주면 된다.
    
    거기까지 진행하면 되고
    이제 해결해야 하는 부분은

    -- 해맸던 점
    해맸던 점은, 그냥 변수명 틀려서?
    그리고 내가 생각했던 것과 생각보다는 다르게 흘러갔다라는 것?
    조금만 다르게 흘러가서, 거의 비슷했음
    그래서 50분 정도 걸려서 풀었음
    */
    static int K; // Key 의 가로 세로
    static int L; // Lock 의 가로 세로
    static int newSize;
    static boolean answer;
    static int[][][] rotateKey;

    static void lockCopy(int[][] originLock, int[][] newKey) { // lock 을 new Key 에다가 등록
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) { // Lock 사이즈 만큼만
                newKey[i + (K - 1)][j + (K - 1)] = originLock[i][j];
            }
        }
    }

    static boolean check(int[][] map) { // 자물쇠랑 열쇠가 맞았는지 확인
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) { // Lock 사이즈 만큼만
                if (map[i + (K - 1)][j + (K - 1)] != 1) { // 1 이 아니라면 열쇠랑 자물쇠랑 맞지 않은 것
                    return false;
                }
            }
        }

        return true;
    }

    static void matchKey(int[][] lock) { // 자물쇠랑 열쇠랑 맞추는 작업을 행하는 곳
        for (int i = 0; i + (K - 1) < newSize; i++) {
            for (int j = 0; j + (K - 1) < newSize; j++) { // key 를 돌음
                for (int c = 0; c < 4; c++) {
                    int[][] newKey = new int[newSize][newSize];
                    lockCopy(lock, newKey);
                    putKey(newKey, c, i, j);

                    if (check(newKey)) { // 맞으면 끝남
                        answer = true;
                        return;
                    }
                }
            }
        }
    }

    static void putKey(int[][] newKey, int r, int y, int x) { // rotate 와 y, x 를 이용하여서 넣음
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < K; j++) {
                newKey[i + y][j + x] += rotateKey[r][i][j];
            }
        }
    }

    static void rotate(int r) {
        // 이제 여기서 key 를 받아서 회전만 시켜서 rotate 에다가 저장만 하면됨        
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < K; j++) {
                rotateKey[r][i][j] = rotateKey[r - 1][(K - 1) - j][i]; // 행 열만 바꿔주면서 처리해주면 된다.
            }
        }
    }

    public boolean solution(int[][] key, int[][] lock) {
        answer = false;
        K = key.length;
        L = lock.length; // 넓이들 넣어줌
        newSize = L + (K * 2) - 2; // 각 사이즈 계산
        rotateKey = new int[4][K][K];

        for (int i = 0; i < K; i++) {
            for (int j = 0; j < K; j++) {
                rotateKey[0][i][j] = key[i][j];
            }
        }

        for (int i = 1; i < 4; i++) { // 회전 시킨 결과값을 만들어준다, key 를 계속 실제로 회전 시키면서 진행한다.
            rotate(i);
        }

        matchKey(lock); // method 호출 (정답을 구하는)

        return answer;
    }
}