import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        // 일단, N 개를 찍고 그것을 N 개 찍는데
        // 최대 2개 찍는다.

        // 2로 나눈 것에서 홀 수 이면 위에 하나더, 혹은 아니면 그냥 똑같이 출력이다.
        if (N == 1) {
            System.out.println("*");
        } else {
            int first = N / 2;
            int second = first;

            if (N % 2 != 0) {
                first++;
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < first; j++) {
                    System.out.print("* ");
                }

                System.out.print("\n ");

                for (int j = 0; j < second; j++) {
                    System.out.print("* ");
                }

                System.out.println();
            }
        }
    }
}
