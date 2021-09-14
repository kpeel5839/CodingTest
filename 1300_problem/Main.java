import java.util.Scanner;
public class Main {
	public static void swap(int[] number_List, int first, int second) {
		int tmp = number_List[first];
		number_List[first] = number_List[second];
		number_List[second] = tmp;
	}
	public static int partition(int[] number_List, int f_Idx, int l_Idx) {
		int pivot = number_List[l_Idx];
		int swap_Index = 0;
		
		for (int i = f_Idx; i < l_Idx; i++) {
			if (pivot > number_List[i]) {
				swap(number_List,i,swap_Index);
				swap_Index++;
			}
		}
		swap(number_List,l_Idx,swap_Index);
		return swap_Index;
	}
	public static int select(int[] number_List, int f_Idx, int l_Idx, int find_Number) {
        if (f_Idx == l_Idx){
            return number_List[f_Idx];
        }
		int q = partition(number_List, f_Idx, l_Idx);
		int k = q - f_Idx + 1;
		if (k == find_Number) {
			return number_List[q];
		}
		else if(k < find_Number) {
			return select(number_List,q+1 ,l_Idx, find_Number - k);
		}
		else {
			return select(number_List,f_Idx, q - 1, find_Number);
		}
	}
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int size = input.nextInt();
		int find_Number = input.nextInt();
		int number_List_Index = 0;
		int[] number_List = new int[size * size];
		for (int i = 1; i < size + 1; i++) {
			for (int j = 1; j < size + 1; j++) {
				number_List[number_List_Index] = input.nextInt();
				number_List_Index++;
			}
		}
		int result = select(number_List, 0 , size * size - 1 , find_Number);
		System.out.println(result);
	}
}
