import java.util.Scanner;
public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int size = input.nextInt();
		int[] dp = new int[size];
		int[] number_List = new int[size];
		int max = 0;
		for (int i = 0; i<size; i++) {
			number_List[i] = input.nextInt();
		}
		if (size == 1) {
			System.out.println(number_List[0]);
		}
		else {
			dp[0] = 1;
			for (int i = 1; i < size; i++) {
				max = 0;
				for (int j = i - 1; j >= 0; j--) {
					if (number_List[i] > number_List[j]){
						if (max < dp[j]) {
							max = dp[j];
						}
					}
				}
				dp[i] = max + 1;
			}
		}
		if (size > 1) {
			max = 0;
			for(int i =0 ;i<size; i++) {
				System.out.print(dp[i] + " ");
				if (max < dp[i]) {
					max = dp[i];
				}
			}
			System.out.println();
			System.out.println(max);
		}
	}

}
