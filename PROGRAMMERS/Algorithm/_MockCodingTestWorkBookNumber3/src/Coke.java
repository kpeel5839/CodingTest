class Coke {
    public int solution(int a, int b, int n) {
        int answer = 0;

        while (true) {
            if (n < a) {
                break;
            }

            int get = (n / a) * b;
            n %= a;
            n += get;
            answer += get;
        }

        return answer;
    }
}