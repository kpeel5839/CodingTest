import java.util.Scanner;
import java.lang.Math;
public class Main {
	public static double power(int number , int factor) {
		if (factor == 1) {
			return number;
		}
		if (factor % 2 == 0) {
			return Math.pow(power(number , factor / 2) , 2);
		}
		else {
			return number * Math.pow(power(number, factor / 2), 2);
		}
	}
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int number = input.nextInt();
		int factor = input.nextInt();
		System.out.println((long)power(number ,factor));
	}
}
