import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("pageNumber : ");
        int pageNumber = sc.nextInt();
//            System.out.println();

        System.out.print("totalSize : ");
        int totalSize = sc.nextInt();
//            System.out.println();

        while (true) {
            System.out.print("jump : ");
            int jump = sc.nextInt();
            /**
             * 지금 해야할 것은
             * 1 ... 10
             * 11 ... 20 이런식으로 가야한다.
             * 근데, 실질적으로는 pageNumber 는 0부터 시작함을 유의하자.
             *
             * 그러면 jump null 이 아니라면?
             * 음수가 되거나 사이즈를 넘거나이다.
             *
             * 음수가 되는 경우 상관할 바 없다.
             * 그냥 jump 해서 pageNumber를 구했을 떄, 그러면 된다.
             */
            int tempPageNumber = pageNumber;

            tempPageNumber = ((tempPageNumber + jump) / 10) * 10 + 1; // 현재 그렇게 만들어놓은 상태이다.

            if (tempPageNumber < 0 || tempPageNumber <= totalSize) { // jump 한 page Number 가 음수가 된 경우는 무조건 1 페이지로 이동해야 한다.
                pageNumber = tempPageNumber;
            }

            // tempPageNumber 까지도 없다라는 것은 그냥 다음 페이지가 없느 ㄴ것이니까
            // pageNumber 는 유지해준다.

            int iStart = (pageNumber - 1) / 10 * 10 + 1;
            int iEnd = (pageNumber - 1) / 10  * 10 + 10;

            if (iEnd > totalSize) {
                iEnd = totalSize;
            }

            System.out.println("pageNumber : " + pageNumber);
            System.out.println("startNumber : " + iStart);
            System.out.println("endNumber : " + iEnd);
        }
    }
}
