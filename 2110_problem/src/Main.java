import java.util.Scanner;
import java.util.Arrays;
public class Main {
	public static void router_Search(int[] house_List,int size, int router_Count) {
		int high = house_List[house_List.length - 1] - house_List[0];
		int low = 0;
		int count = 1;
		int gap = 0;
		int now_Number = 1;
		int answer = 0;
		int distance = 0;
		while (low <= high){
			count = 1;
			now_Number = house_List[0];
			gap = (high + low) / 2; 
			for (int i = 1; i < size; i++) {
				distance = house_List[i] - now_Number;
				if (distance >= gap) {
					count++;
					now_Number = house_List[i];
				}
			}
			if (count >= router_Count) {
				low = gap + 1;
			}
			else {  
				high = gap - 1;
			}
		}
		System.out.println(high); 
	}
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int size = input.nextInt();
		int router_Count = input.nextInt();
		int[] house_List = new int[size];
		for (int i = 0 ; i < size; i++) {
			house_List[i] = input.nextInt();
		}
		Arrays.sort(house_List);
		router_Search(house_List,size, router_Count);
	}
}
