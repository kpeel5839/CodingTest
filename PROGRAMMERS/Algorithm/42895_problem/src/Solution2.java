import java.util.*;

// N으로 표현

class Solution2 {
    public void union(Set<Integer> res, Set<Integer> o1, Set<Integer> o2) {
        for (Integer a : o1) {
            for (Integer b : o2) { // o1, o2 를 사칙연산들을 이용해서 추가해준다.
                res.add(a + b);
                res.add(a - b);
                res.add(a * b);
                if (b != 0) {
                    res.add(a / b);
                }
            }
        } // 이런식으로 연산들을 진행해주고
    }

    public int solution(int N, int number) {
        List<Set<Integer>> setList = new ArrayList<>();

        for (int i = 0; i <= 8; i++) {
            setList.add(new HashSet());
        } // HashSet으로 선언함 인덱스 8까지

        if (N == number) {
            return 1; // N == number 이면, 그냥 바로 return 1
        }

        setList.get(1).add(N); // 1개로 이루어진 N 은 N 하나 뿐임

        for (int i = 2; i <= 8; i++) {
            for (int j = 1; i - j > 0; j++) { // i - j 를 했을 때, 0이 만들어지면은 안됨
                /**
                 * 1 = N 하나
                 * 2 = (1 + 1) union 연산
                 * 3 = (1 + 2)(2 + 1) union 연산
                 * 4 = (1 + 3)(3 + 1)(2 + 2) union 연산.... 쭉쭉
                 * ...
                 *
                 * 그리고, 각각에 N으로만 이루어진 i 자릿수의 숫자들을 추가해주어야 한다.
                 */
                union(setList.get(i), setList.get(i - j), setList.get(j));
                union(setList.get(i), setList.get(j), setList.get(i - j)); // j 와 i - j 의 조합으로 이루어져야지 i 를 만들 수 있음
                // 왜냐하면 (j + i - j == i) 이니까

                String n = Integer.toString(N);
                setList.get(i).add(Integer.parseInt(n.repeat(i))); // N 으로만 이루어진 i 자리 수 삽입

                for (int a : setList.get(i)) { // 추가한 숫자들 중, Number 와 같은 것이 있나 확인
                    if (number == a) {
                        return i;
                    }
                }
            }
        }

        return -1;
    }
}