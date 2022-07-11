import java.util.*;
import java.io.*;
import java.util.function.Function;

// 18808 : 스티커 붙이기

/**
 * -- 전제조건
 * 주어진 모눈종이 대로 스티커를 순서대로 붙히면 되고
 * 스티커는 최대한 왼쪽 위를 우선적으로 붙혀야 한다.
 *
 * 그리고, 되게 쉽게 그냥 스티커는 붙힐 수 있을 때, 붙히면 되고, 붙히지 못하는 스티커는 그냥 넘어가면 된다.
 *
 * -- 틀 설계
 * 일단 스티커들을 저장해놓을 3차원 배열을 만든다.
 * 그리고 90도 회전 시킬 수 있는 rotate 함수를 만든다.
 * 그리고 matching 메소드를 만든다.
 * 그리고 범위를 나갔을 때, true 를 반환해주는 outOfRange 메소드를 만든다.
 *
 * 그리고서 순서대로 스티커를 진행해나간다.
 * 총 4번의 matching 을 시도할 것이다.
 * 만일 붙히지 못한다라면? 그냥 넘어간다
 * 만일 붙힌다면? copy() 메소드를 이용해서 노트북에다가 스티커를 붙혀준다.
 * 그러면서 count 도 높여준다.
 *
 * 그러면 쉽게 맞을 수 있을 것 같다.
 *
 * -- 해맸던 점
 * rotate 시 sticker[stickerNumber][j][i] 여야 하는데 [i][j] 로 했고
 * x == copyH 일 떄, x = 0, y++ 해주었어야 했는데 반대로 했었고
 *
 * 그 다음에 matching 되면 다음 스티커로 넘어갔어야 했는데 그것도 안해주었었고,
 * 맵을 돌면서 딱 3번만 rotate 가 되었어야 했는데, 그것도 모르고 map 순환에다가 rotate 넣어가지고 H * W 만큼 rotate 되었었음
 * 그래서 rotate 밖으로 뺌
 *
 * 이렇게 했더니 맞았음
 */
public class Main {
    static int H;
    static int W;
    static int K;
    static int ans = 0;
    static int[][] noteBook;
    static int[][][] sticker;

    static void rotate(int stickerNumber) {
        /**
         * 회전 할 때, 계속 sticker[i] 를 계속 임시 선언해주어야 함
         * 그렇지 않으면, 사이즈가 안됨
         *
         * 예를 들어서 2 * 5 배열도, rotate 를 진행하고 나면 5 * 2 배열이 되기 떄문임
         *
         * 일단 현재 sticker[i] 를 copy 해주고
         * sticker[i] = new int[sticker[i][0].length][sticker[i].length] 이런식으로 선언해준다.
         *
         * 그런 다음에 그냥 원래 읽는대로 읽어주면서
         * 넣는 부분은 copy 한 배열의 오른쪽 열, 제일 윗 행 부터 copy 를 진행해나가면 된다.
         */
        int copyH = sticker[stickerNumber][0].length; // W -> H 가 되어야 함
        int copyW = sticker[stickerNumber].length; // H -> W
        int[][] copySticker = new int[copyW][copyH];

        for (int i = 0; i < copyW; i++) {
            for (int j = 0; j < copyH; j++) {
                copySticker[i][j] = sticker[stickerNumber][i][j];
            }
        }

        sticker[stickerNumber] = new int[copyH][copyW];
        int y = 0;
        int x = 0;

        for (int i = copyW - 1; i != -1; i--) {
            for (int j = 0; j < copyH; j++) {
                sticker[stickerNumber][j][i] = copySticker[y][x];

                x++;
                if (x == copyH) { // 이런식으로 로테이트를 진행한다.
                    x = 0;
                    y++;
                }
            }
        }
    }

    static boolean match(int y, int x, int stickerNumber) {
        boolean possible = true;

        for (int i = 0; i < sticker[stickerNumber].length; i++) {
            for (int j = 0; j < sticker[stickerNumber][0].length; j++) {
                if (sticker[stickerNumber][i][j] == 1) { // 1 일 때만 확인
                    if (outOfRange(y + i, x + j) || noteBook[y + i][x + j] == 1) { // 범위를 나가거나, 이미 스티커가 붙어있는 경우에는 false 를 반화할 수 있도록 한다.
                        possible = false;
                        break;
                    }
                }
            }
        }

        return possible;
    }

    static void copy(int y, int x, int stickerNumber) {
        for (int i = 0; i < sticker[stickerNumber].length; i++) {
            for (int j = 0; j < sticker[stickerNumber][0].length; j++) {
                if (sticker[stickerNumber][i][j] == 1) { // sticker 가 있는 부분에 대해서만 붙혀야지 outOfRange 연산 안해도 된다.
                    noteBook[y + i][x + j] = 1;
                    ans++;
                }
            }
        }
    }

    static boolean outOfRange(int y, int x) {
        if (y < 0 || y >= H || x < 0 || x >= W) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        String[] input = br.readLine().split(" ");
        H = fun.apply(input[0]);
        W = fun.apply(input[1]);
        K = fun.apply(input[2]);
        noteBook = new int[H][W];
        sticker = new int[K][][];

        for (int k = 0; k < K; k++) {
            input = br.readLine().split(" ");
            int h = fun.apply(input[0]);
            int w = fun.apply(input[1]);

            sticker[k] = new int[h][w];
            for (int i = 0; i < h; i++) {
                input = br.readLine().split(" ");
                for (int j = 0; j < w; j++) {
                    sticker[k][i][j] = fun.apply(input[j]);
                }
            }
        }

        for (int i = 0; i < K; i++) { // 모든 스티커를 진행한다.
            Loop:
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < H; k++) {
                    for (int c = 0; c < W; c++) {
                        if (match(k, c, i)) { // match 하면 붙히고 나가준다.
                            copy(k, c, i);
                            break Loop;
                        }
                    }
                }

//                stickerPrint(i);
                rotate(i);
            }
        }

//        for (int i = 0; i < H; i++) {
//            for (int j = 0; j < W; j++) {
//                System.out.print(noteBook[i][j] + " ");
//            }
//            System.out.println();
//        }

        System.out.println(ans);
    }

    static void stickerPrint(int stickerNumber) {
        System.out.println("sticker!");
        for (int i = 0; i < sticker[stickerNumber].length; i++) {
            for (int j = 0; j < sticker[stickerNumber][0].length; j++) {
                System.out.print(sticker[stickerNumber][i][j] + " ");
            }
            System.out.println();
        }
    }
}
