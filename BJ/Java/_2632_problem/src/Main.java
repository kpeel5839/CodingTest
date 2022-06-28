import java.io.*;
import java.util.*;
import java.util.function.Function;

// 2632 : 피자판매

/**
 * -- 전제 조건
 * 연속된 피자조각을 골라야 하고
 * 두 개의 피자가 있다고 했을 경우
 * 손님이 원하는 피자가 주어졌을 때,
 *
 * 가능한 경우의 수 모두를 구하시오
 *
 * -- 틀 설계
 * 생각을 계속 해보니까 누적합 + 두 포인터 문제라는 것을 깨닫게 되었음
 * 일단 각각 피자 조각이 1000 조각 이니고, 피자가 두 개이니까
 * 누적합의 개수는 최대 2 * (1 + (n * (n + 1)) / 2)) 라는 것을 알 수가 ㅣㅇ싿.
 *
 * 그러니 이게 무슨 소리냐
 * 쌉가능이라는 것이다.
 *
 * 그래서 누적합을 순서대로 구해준다음에
 * 두 포인터로
 * 둘 다 정렬을 하고 (오름차순으로)
 * 한 피자는 왼쪽 포인터 부터
 * 한 피자는 오른쪽 포인터 부터해서
 *
 * 두개를 더한 값이 만일 손님이 원하는 피자 크기보다 크면 오른쪽 포인터를 내리고
 * 아니면 왼쪽 포인터를 올린다.
 *
 * 그러고서 만일 맞으면 서로 같은 숫자가 몇개가 있는지 보고 그것만큼 곱해서 더해준다.
 *
 * 그리고 모두 left pointer 는 사이즈와 같아지면, right pointer 는 -1 이 되면 끝내준다. (둘 중 하나라도 벗어나면 끝내준다.)
 *
 * 문제가 있었음
 * 해당 누적합을 구하는 과정에서
 * 이건 연결된 원이라는 것을 고려하지 못했음
 *
 * -- 해맸던 점
 * 만일 내가 자료구조에 대한 이해가 전혀 없었다면
 * 죽어도 못 맞췄을 문제이다.
 *
 * List 의 get 방식은 이중 연결 리스트이기에
 * 어쩔 수 없이 처음 요소부터 탐색해서 쭉 흘러가는 형식을 띈다
 *
 * 그렇기 때문에 get 같은 연산은 굉장히 오래걸리는 연산이다.
 * 그래서 이 문제에서 해맸던 점은 딱 2개이다.
 *
 * 첫번째, 피자판이 원인 걸 생각하지 못했다.
 * 두번째, 두 포인터를 그냥 List 를 사용해 풀었다. (그러면 그냥 2중 루프랑 다를 바가 없음 개 오래 걸릴 수 밖에 없음)
 *
 * 피자판이 원인걸 생각하지 못해서 발생했기 때문에 원형일 때 어떤 식으로 누적합을 다 적용시킬 수 있을까에 대한 생각을 했다.
 * 그랬더니 전체 사이즈를 포함하지만 말고
 * 그냥 원으로 쭉 돌면서 더해주면 되겠구나 라는 생각을 했다.
 *
 * 그렇게 해서 convert 라는 method 를 만들어서 인덱스의 변화가 원형이 될 수 있도록 하였다.
 * 그렇게 해서 원으로 쭉 돌면서 누적합을 계산할 수 있었고 (전체사이즈와, 선택하지 않은 경우는 마지막에 하드코딩으로 넣어주었음)
 *
 * 그리고 두번째는 그냥 get 방식을 사용했다라는 것이다.
 * 이게 분명히 두 포인터이기에 오래 걸릴 방법이 절대 아니라는 것을 내가 알고 있었다.
 * 하지만, 시간초과가 나길래 아 이거는 get 의 문제일 수도 있겠다라는 생각에
 *
 * 한번 Array 로 변경해서 인덱싱을 진행할 때 바로 접근할 수 있는 방법으로 선택해보았다.
 * 그랬더니 마법같이 훨씬 빠른 속도로 돌아갔고
 * 정답을 맞추게 되었다.
 */
public class Main {
    static int convert(int a, int size) {
        return a == -1 ? size - 1 : a % size;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Function<String, Integer> fun = Integer::parseInt;

        int P = fun.apply(br.readLine());
        String[] input = br.readLine().split(" ");
        int[] A = new int[fun.apply(input[0])];
        int[] B = new int[fun.apply(input[1])];

        int totalA = 0;
        int totalB = 0;

        for (int i = 0; i < A.length; i++) {
            A[i] = fun.apply(br.readLine());
            totalA += A[i];
        }

        for (int i = 0; i < B.length; i++) {
            B[i] = fun.apply(br.readLine());
            totalB += B[i];
        }

        List<Integer> aList = new LinkedList<>();
        List<Integer> bList = new LinkedList<>();

        for (int i = 0; i < A.length; i++) {
            int sum = A[i];
            aList.add(sum);
            for (int j = convert(i + 1, A.length); j != convert(i - 1, A.length); j = convert(j + 1, A.length)) { // i - 1 까지 돌려버리면 전체가 포함이 되버림
                sum += A[j];
                aList.add(sum);
            }
        }

        for (int i = 0; i < B.length; i++) {
            int sum = B[i];
            bList.add(sum);
            for (int j = convert(i + 1, B.length); j != convert(i - 1, B.length); j = convert(j + 1, B.length)) { // i - 1 까지 돌려버리면 전체가 포함이 되버림
                sum += B[j];
                bList.add(sum);
            }
        }

        aList.add(0);
        aList.add(totalA);
        bList.add(0);
        bList.add(totalB);

        int[] aArr = new int[aList.size()];
        int index = 0;

        for (Integer v : aList) {
            aArr[index++] = v;
        }

        int[] bArr = new int[bList.size()];
        index = 0;

        for (Integer v : bList) {
            bArr[index++] = v;
        }

        Arrays.sort(aArr);
        Arrays.sort(bArr);

        int aPointer = 0;
        int bPointer = bList.size() - 1;
        long res = 0;

        while (aPointer != aList.size() && bPointer != -1) {
            int a = aArr[aPointer];
            int b = bArr[bPointer];

            if (a + b < P) { // 왼쪽 포인터를 올려야 하는 경우
                aPointer++;
            } else if (a + b > P) { // 오른쪽 포인터를 내려야 하는 경우
                bPointer--;
            } else { // 같은 경우
                long aCnt = 0;
                long bCnt = 0;

                while (aPointer != aList.size()) {
                    if (aArr[aPointer] != a) {
                        break;
                    }

                    aCnt++;
                    aPointer++;
                }

                while (bPointer != -1) {
                    if (bArr[bPointer] != b) {
                        break;
                    }

                    bCnt++;
                    bPointer--;
                }

                res += bCnt * aCnt;
            }
        }

        System.out.println(res);
    }
}
