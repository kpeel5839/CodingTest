import java.util.*;
import java.io.*;
import java.util.function.Function;

// 10875 : 뱀

/**
 * -- 전제 조건
 * 2L + 1 의 2차원 격자판이 있다.
 * 이 격자판은 각 칸을 x, y 좌표로서 표현한다.
 *
 * 그리고 계속 머리의 회전을 통해서 방향을 꺾는다.
 * 그리고 무엇보다 일반적인 뱀 게임과는 다르게 몸이 따라가지 않고
 * 계속 몸이 늘어난다.
 *
 * 이렇게 상황이 주어지고 처음에는 L, 그리고 회전의 개수 N (최대 1000) 이 주어진다라면
 * 몇초뒤에 뱀이 숨을 거둘 것인가?
 *
 * -- 틀 설계
 * 처음에는 단순한 뱀게임 구현 문제인 줄 알았다.
 * 근데, 정수 사이즈가 말이 안된다 이것을 배열로서 구현한다?
 * 그러면 가로 최대 가로 2억, 세로 2억에 그냥 1차원 배열로만 구현하여도 겁나게 오래걸리거나 Stack over flow 가 날 정도의 사이즈를 가지고 있다.
 *
 * 그렇다라는 것은 절대로 이것은 배열로서 그려내는 것이 아니라는 것이다.
 *
 * 나의 아이디어는 이러하다
 * 일단 outOfRange 는 어떠냐
 * 0, 0 을 기준으로 선분들을 생성하고
 * 하나의 요소라도 절댓값 L을 초과하게 되면 바깥으로 나가서 죽게 된다.
 *
 * 그리고 회전할 때 본인의 몸에 박는 경우
 * 만일 뱀이 본인의 몸을 계속 거두어 간다?
 * 절대로 이 방법으로 불가능하다
 *
 * 하지만 이 문제가 의도하는 것은 사실 뱀게임이 아니라
 * 그냥 선분 그리기이다.
 *
 * 그래서 선분들이 교차하는지 안하는지 검사한다면?
 * 그러면 쉽게 문제를 맞출 수 있을 것 같다.
 *
 * 그래서 선분이 교차하는지 판단하는 메소드
 * outOfRange 메소드
 * 그리고 실질적으로 배열을 받아서 선분을 계속 그려주는 메소드가 필요하다.
 *
 * 그리고 계속 해서 2중 포문을 이용해서 현재 뱀이 가려는 위치가, 즉 머리가 박는 경우가 있는지
 * 즉, 이전에 온 경로 중 교차하는 것이 있는지 판단만 하면 된다.
 *
 * 하지만 여기서 조심해야 할 점은
 * 회전할 때 당시에 충돌 검사를 하려면
 * 현재 회전하는 지점 그 지점은 빼놓고 계산을 진행해야 한다.
 * 사실, 그냥 시작지점을 회전 방향으로 한 칸 진행한 뒤에 선분을 그려내주면 된다.
 *
 * 선분을 담을 List 도 만들자.
 * 선분은 첫 지점과 끝지점으로 하면 된다.
 *
 * 따로 그냥 담아버렷
 *
 * -- 해결
 * 
 */

public class Main {
    static int L; // 범위
    static int N; // 쿼리의 개수
    static long live = 0; // 살아있는 시간
    static int dir = 1; // 현재의 dir 을 나타낸다 처음 dir 은 1이다.
    static Point head = new Point(0, 0); // 현재 위치를 나타낸다, 계속 갱신될 예정이다.
    static List<Path> snake = new ArrayList<>(); // 시작지점과 끝지점을 저장하자
    static long[] dx = {0, 1, 0, -1};
    static long[] dy = {-1, 0, 1, 0}; // 방향 배열이다
    static boolean gameOver = false;

    static class Point {
        long y;
        long x;

        Point(long y, long x) {
            this.y = y;
            this.x = x;
        }

        public Point copy() {
            return new Point(y, x);
        }

        @Override
        public String toString() {
            return "point : (" + y + ", " + x + ")";
        }
    }

    static class Path {
        Point p1;
        Point p2;

        Path(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        public Path copy() { // 혹시 모르니까 copy 를 만들어놓자 shallow copy로
            return new Path(new Point(this.p1.y, this.p1.x), new Point(this.p2.y, this.p2.x));
        }

        @Override
        public String toString() {
            return "Path : (" + p1.toString() + ", " + p2.toString() + ")";
        }
    }

    static boolean outOfRange(long y, long x) { // 뱀의 머리가 벗어나면 끝이다.
        if (y < -L || L < y || x < -L || L < x) {
            return true;
        } else {
            return false;
        }
    }

    static Point outOfRangePoint(long y, long x) { // 벽에 충돌하였을 때, 충돌지점을 구함
        Point res;

        if (y < -L) {
            res = new Point(-L - 1, x);
        } else if (L < y) {
            res = new Point(L + 1, x);
        } else if (x < -L) {
            res = new Point(y, -L - 1);
        } else {
            res = new Point(y, L + 1);
        }

        return res;
    }

    static boolean cross(Path p1, Path p2) { // 교차하였는지 판단을 한다 (박은지 안박은지)
        /**
         * 교차하는 경우를 다 정리해보자.
         *
         * 일단 평행하는 경우에서 교차하는 경우
         * 그리고 서로 정말 크로스하는 경우가 있다.
         *
         * 일단 두개의 선분을 받는다.
         * p1, p2 는 각각의 포인터들을 가지고 있다.
         *
         * 평행해서 교차하는 경우를 생각해보자.
         * A -> B
         * C -> D 라고 했을 떄
         * A C B D 이런 경우가 있다.
         * 근데 생각해보니 교차하지 않는 경우를 생각하는 것이 훨씬
         * 쉽다.
         *
         * A B C D
         * C D A B 이것들의 공통점이 무엇이 있을까?
         *
         * 첫번째 경우만 생각해보면 A B 중 큰 것 B
         * C D 중 작은 것 C 가 C 가 더 크다.
         * 그리고 A B 중 작은 것 A 보다 C D 중 큰 것 D 가 더 크다.
         * 즉, 방향이 같다라는 것이다.
         *
         * 그러면 식을 이런식으로 정리할 수 있을 것 같다.
         * 일단, 평행하다라는 조건을 가지려면 어디로 증가하는 지 알아야 한다.
         *
         * x 축으로서 증가한다? 그러면 똑같이 x 축으로서 증가하면서 x 값이 같다라면 평행하다
         * y 축으로서 증가하는 경우도 똑같다. 그러면 이것을 증명하여서, Math.min, Math.max 를 골라낸다.
         * 그것을 작은 것은 A, C 큰 것은 B, D 라고 정의해보자.
         *
         * 그러면 이 경우만 아니라면 교차한다라고 할 수 있다.
         * B < C && A < D || D < A && C < B 이 것에 걸린다라면 교차하지 않는다라는 것이다.
         *
         * 그러면 이제 평행하는 경우의 교차는 구했다.
         * 근데 dir 를 측정하는 메소드도 필요할 것 같다.
         *
         * 그리고 교차하는 경우는 어떻게 계산할까
         * 일단 교차하는 경우는 p1Dir, p2Dir 이 달라야 한다.
         *
         * 그리고 만약에 그렇다면 swap 을 진행해서 p1 이 무조건 Dir == 'y' 를 가질 수 있도록 하자.
         *
         * 그러면 교차의 조건 중 하나
         * x 축으로 증가하는 놈의 x 범위에 y 축으로 증가하는 놈의 x 범위가 들어가야 한다.
         * 그리고 y 축으로 증가하는 놈의 y 의 범위에 x 축으로 증가하는 놈의 y 범위에 들어가야 한다.
         *
         * 그러면 교차 판정이 가능하다.
         *
         * 이런식으로 교차 판정을 진행해보자.
         */
        char p1Dir = calPathDir(p1);
        char p2Dir = calPathDir(p2); // 두 개의 방향을 얻어냈다.

        if (p1Dir == p2Dir) { // 두개의 방향이 같은 경우
            long a;
            long b;
            long c;
            long d;
            if (p1Dir == 'x') { // x 축으로 증가하는 경우
                if (p1.p1.y != p2.p1.y) {
                    return false; // y 축으로서 평행한 경우인데 y 축이 같지 않다? 그러면 나가리 데스네
                }

                a = Math.min(p1.p1.x, p1.p2.x); // 작은 것
                b = Math.max(p1.p1.x, p1.p2.x); // 큰 것
                c = Math.min(p2.p1.x, p2.p2.x); // 작은 것
                d = Math.max(p2.p1.x, p2.p2.x); // 큰 것
            } else { // y 축으로 증가하는 경우
                if (p1.p1.x != p2.p1.x) {
                    return false; // x 축으로서 평행한 경우인데 x 축이 같지 않다? 그러면 나가리 데스네
                }

                a = Math.min(p1.p1.y, p1.p2.y); // 작은 것
                b = Math.max(p1.p1.y, p1.p2.y); // 큰 것
                c = Math.min(p2.p1.y, p2.p2.y); // 작은 것
                d = Math.max(p2.p1.y, p2.p2.y); // 큰 것
            }

            if ((b < c && a < d) || (d < a && c < b)) { // 교차하지 않는 경우를 걸러냈음 (a < d, c < b 안들어가도 될 것 같은데 불안)
                return false;
            }
        } else { // 두개의 방향이 다른 경우
            if (p1Dir != 'y') { // p1 의 방향을 y 로 유지할 수 있게 하기 위해서 swap 을 진행해준다.
                Path tmp = p1;
                p1 = p2;
                p2 = tmp;
            }

//            System.out.println(p1);
//            System.out.println(p2);

            long p1StartY = Math.min(p1.p1.y, p1.p2.y);
            long p1EndY = Math.max(p1.p1.y, p1.p2.y);
            long p2StartX = Math.min(p2.p1.x, p2.p2.x);
            long p2EndX = Math.max(p2.p1.x, p2.p2.x);

//            System.out.println(p1StartY + " <= " + p2.p1.y + " <= " + p1EndY + " : " + (p1StartY <= p2.p1.y && p2.p1.y <= p1EndY));
//            System.out.println(p2StartX + " <= " + p1.p1.x + " <= " + p2EndX + " : " + (p2StartX <= p1.p1.x && p1.p1.x <= p2EndX));

            if (!((p1StartY <= p2.p1.y && p2.p1.y <= p1EndY) // p1 은 y 축으로 움직이니까 p2의 y 가 요 안에 있냐
                    && p2StartX <= p1.p1.x && p1.p1.x <= p2EndX)) { // p2 는 x 축으로 움직이니까 p1 의 x 가 여기 있냐
                return false;
            }
        }

        return true;
    }

    static char calPathDir(Path p1) { // 현재 선분의 방향을 측정
        if (p1.p1.x == p1.p2.x) { // 두 point 의 x 값이 같다 그러면 y 값으로 증가하는 것
            return 'y';
        } else {
            return 'x'; // 증가하는 축의 방향을 던져준다.
        }
    }

    static Point crossPoint(Path p1, Path p2) { // 교차하였으면 crossPoint 를 반환한다.
        /**
         * 크로스 포인트를 계산을 어떻게 할까
         * 일단 확실한 것은 교차한 두개의 선분이 주어질 것이다.
         *
         * 그러면 여기서 이 선분이 어디서 교차하는지만 판단하면 된다.
         * 오 너무 쉽다
         *
         * 생각보다 그냥 아까 위에서 처럼 두개의 dir 을 얻어낸다음에
         * 교차한다라는 확증이 있기 때문에
         * 그냥 p2.y, p1.x 가 교차 지점이다.
         *
         * 근데 교차하는 경우는 그런데
         * 평행하는 경우가 문제다
         *
         * 근데 생각해보니까 이 게임의 특성상
         * 평행하는 경우 A C D B 이런 경우는 없다.
         *
         * 저러기 이전에 게임이 끝났을 것이니까
         *
         * 그러면 평행하는 경우도 A C B D 이 경우와
         * C A D B 이 경우만 계산하면 된다.
         *
         * 첫번째 경우는 교차지점이 어떻게 될까
         * 바로 C.좌표 이 부분이 교차 지점이다.
         *
         * 두번째 경우는 ? A.좌표 이 부분이 교차지점이다.
         *
         * 이 두 경우를 고려해서 짜면 된다.
         * A 가 더 작은 경우와 큰 경우로 나누면 된다.
         *
         * 짜피 교차하지 않는다라는 가정 따위 하지 않는다 cross 한 애들만 넘어오니까
         */
        char p1Dir = calPathDir(p1);
        char p2Dir = calPathDir(p2);
        Point res;

        if (p1Dir == p2Dir) {
            if (p1Dir == 'y') {
                long a = Math.min(p2.p1.y, p2.p2.y); // 작은 것
                long b = Math.max(p2.p1.y, p2.p2.y); // 큰 것

                if (p1.p1.y < p1.p2.y) { // 내려가는 것
                    res = new Point(a, p1.p1.x);
                } else { // 올라가는 것
                    res = new Point(b, p1.p1.x);
                }
            } else {
                long a = Math.min(p2.p1.x, p2.p2.x); // 작은 것
                long b = Math.max(p2.p1.x, p2.p2.x); // 큰 것

                if (p1.p1.x < p1.p2.x) { // 오른쪽으로
                    res = new Point(p1.p1.y, a);
                } else { // 왼쪽으로
                    res = new Point(p1.p1.y, b);
                }
            }
        } else {
            if (p1Dir != 'y') { // p1 의 방향을 y 로 유지할 수 있게 하기 위해서 swap 을 진행해준다.
                Path tmp = p1;
                p1 = p2;
                p2 = tmp;
            }

            res = new Point(p2.p1.y, p1.p1.x);
        }

        return res;
    }

    static int calDir(int nowDir, char rotate) {
        // Left 는 -1, Right 는 +1
        if (rotate == 'L') { // L
            nowDir = nowDir - 1 == -1 ? 3 : nowDir - 1;
        } else { // R
            nowDir = nowDir + 1 == 4 ? 0 : nowDir + 1;
        }

        return nowDir;
    }

    static long calBeforeCollision(Point preHead, Point collisionPoint) {
        return Math.abs(collisionPoint.y - preHead.y) + Math.abs(collisionPoint.x - preHead.x);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        L = fun.apply(br.readLine());
        N = fun.apply(br.readLine());

        for (int i = 0; i < N; i++) {
            /**
             * head 를 현재 받은 칸 만큼 옮겨준다
             * dir * move 해줘서
             * 그 다음에 계산을 진행해주고
             *
             * 교차판정을 진행한다.
             * List 에다가
             *
             * 그 다음에 이제 안 끝났다.
             * 그러면 List.add 해주고
             * 현재 방향에서 한칸 진행한 상태로 바꿔준다
             * 즉, head 값을 바꿔주는 것이다. (live 값도 현재 move 값에서 더해주어야 함)
             *
             * 만일, 교차 판정이 났거나, 혹은 벽에 박았다.
             * 그러면 이전 head 값을 기억하고 있다가
             * 현재 충돌난 Point 를 비교해서 절댓값을 더해준다.
             *
             * 그러면 끝날 듯하다.
             */
            String[] input = br.readLine().split(" ");
            Point preHead = head.copy();
            Point pathStart;
//            System.out.println(head);

            if (head.y == 0 && head.x == 0) {
                pathStart = new Point(0, 0);
            } else {
                pathStart = new Point(head.y + dy[dir], head.x + dx[dir]); // 한칸 움직인 상태로 만들어주고
            }

            long move = fun.apply(input[0]);
            char rotate = input[1].charAt(0); // 문자로 변환

            long y = head.y + (move * dy[dir]);
            long x = head.x + (move * dx[dir]);

            Path p1 = new Path(pathStart, new Point(y, x)); // 이전 위치와 현재 이동한 위치로 계산
            long min = Long.MAX_VALUE;

            for (Path p2 : snake) { // 다 돌아보면서 충돌 체크를 진행한다, 이전의 선분들과
                if (cross(p1.copy(), p2.copy())) { // 충돌이 났다라면
                    min = Math.min(calBeforeCollision(preHead.copy(), crossPoint(p1.copy(), p2.copy())), min);
                    gameOver = true;
                }
            }

            if (gameOver) {
                live += min;
                break;
            }

            if (outOfRange(y, x)) { // 만일 충돌이 났으면, 세상에나... 충돌을 먼저 감지했으면 안됐음, 당연히 먼저 박을 수 있는 건데..
                live += calBeforeCollision(preHead.copy(), outOfRangePoint(y, x));
                gameOver = true;
                break;
            }

            /**
             * 이제 충돌 안함을 확인했음 그러면 해야할 일이
             * dir 변경
             * head 변경된 dir 로 한칸 이동 (y, x 를 기준으로)
             * live += move
             * snake list 에다가 현재 preHead -> int y, x 선분 추가, 즉 p1 을 추가
             *
             * head 가 저따구로 움직이니까 이동하는 게 이상해짐
             * 그래서 Path 자체를 그렇게 만들어보자.
             */

            dir = calDir(dir, rotate);
            snake.add(p1);
            live += move;
            head = new Point(y, x);
        }

//        System.out.println(gameOver);
//        System.out.println(live);
//        System.out.println(head);

        if (!gameOver) { // 아직 게임이 끝나지 않았다면?
            long move = 2L * L + 1;

            long y = head.y + (move * dy[dir]);
            long x = head.x + (move * dx[dir]);
            Point pathStart = new Point(head.y + dy[dir], head.x + dx[dir]); // 한칸 움직인 상태로 만들어주고

            Path p1 = new Path(pathStart, new Point(y, x));
            long min = Long.MAX_VALUE;

            for (Path p2 : snake) {
                if (cross(p1.copy(), p2.copy())) {
                    min = Math.min(calBeforeCollision(head, crossPoint(p1, p2)), min);
                    gameOver = true;
                }
            }

            if (!gameOver) {
                live += calBeforeCollision(head, outOfRangePoint(y, x));
            } else {
                live += min;
            }
        }

        System.out.println(live);
    }
}
