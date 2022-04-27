import java.util.Scanner;
public class Main{
	public static void swap(int[] number_List , int first, int second) {
		int tmp = number_List[first];
		number_List[first] = number_List[second];
		number_List[second] = tmp;
	}
	public static int partition(int[] number_List, int f_Idx, int l_Idx) {
		int pivot = number_List[l_Idx];
		int swap_Index = f_Idx;
		for (int i = f_Idx; i < l_Idx; i++) {
			if (pivot > number_List[i]) {
				swap(number_List, i, swap_Index);
				swap_Index++;
			}
		}
		swap(number_List, swap_Index, l_Idx);
		return swap_Index;
	}
	public static int select(int[] number_List , int f_Idx, int l_Idx, int find_Index) {
		int q = partition(number_List,f_Idx, l_Idx);
		int k = q - f_Idx + 1;
		if (find_Index == k) {
			return number_List[q];
		}
		else if (find_Index > k) {
			return select(number_List, q + 1, l_Idx, find_Index - k);
		}
		else {
			return select(number_List,f_Idx, q - 1, find_Index);
		}
	}
	public static int linear_Select(int[] number_List, int f_Idx, int l_Idx, int find_Index) {
		System.out.println("f_Idx : " + f_Idx + " l_Idx : " + l_Idx + " find_Index : " + find_Index);
		if (l_Idx - f_Idx < 5) {
			return select(number_List, f_Idx, l_Idx, find_Index);
		}
		int size = l_Idx - f_Idx + 1;
		int tmp_Size = 0;
		if (size % 5 == 0) {
			tmp_Size = size / 5;
		}
		else {
			tmp_Size = (size / 5) + 1;
		}
		int[] tmp_List = new int[tmp_Size];
		int tmp_Index = 0;
		int first = f_Idx;
		int last = f_Idx + 4;
		while (true) {
			if (last >= l_Idx) {
				last = l_Idx;
				tmp_List[tmp_Index] = select(number_List, first, last, (last - first) / 2 + 1);
				break;
			}
			tmp_List[tmp_Index] = select(number_List, first , last, (last - first) / 2 + 1);
			tmp_Index ++;
			first += 5;
			last += 5;
		}
		int m_Number = linear_Select(tmp_List, 0 , tmp_Size - 1, (tmp_Size - 1) / 2 + 1);
		switching(number_List, f_Idx, l_Idx, m_Number);
		int q = partition(number_List, f_Idx, l_Idx);
		int k = q - f_Idx + 1;
		if (k == find_Index) {
			return number_List[q];
		}
		else if(k < find_Index) {
			return linear_Select(number_List, q + 1, l_Idx, find_Index - k);
		}
		else {
			return linear_Select(number_List, f_Idx, q - 1, find_Index);
		}
	}
	public static void switching(int[] number_List,int f_Idx, int l_Idx,int target_Number) {
		int target_Index = 0;
		for(int i = f_Idx; i <= l_Idx; i++) {
			if (target_Number == number_List[i]) {
				target_Index = i;
			}
		}
		swap(number_List, target_Index, l_Idx);
	}
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int size = input.nextInt();
		int find_Index = input.nextInt();
		int[] number_List = new int[size * size];
		int number_List_Index = 0;
		for (int i = 1; i < size + 1; i++) {
			for (int j = 1; j < size + 1; j++) {
				number_List[number_List_Index++] = i * j;
			}
		}  
		int result = linear_Select(number_List, 0 , size * size - 1, find_Index);
		System.out.println(result); 
	}
}