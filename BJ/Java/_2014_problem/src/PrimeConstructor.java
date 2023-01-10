public class PrimeConstructor {
    public static void main(String[] args) {
        boolean[] isNotPrime = new boolean[542];
        for (int i = 2; i <= 541; i++) {
            if (!isNotPrime[i]) {
                for (int j = i * 2; j <= 541; j += i) {
                    isNotPrime[j] = true;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= 541; i++) {
            if (!isNotPrime[i]) {
                count++;
                System.out.print(i + " ");
            }
        }

        System.out.println();
        System.out.println(count);
    }
}
