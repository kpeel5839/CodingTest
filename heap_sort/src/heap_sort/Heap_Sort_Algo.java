package heap_sort;

import java.io.*;
import java.util.StringTokenizer;

public class Heap_Sort_Algo {
	public static void swap(int[] number_List, int first, int second) {
		int tmp = 0;
		tmp = number_List[first];
		number_List[first] = number_List[second];
		number_List[second] = tmp;
	}

	public static void heap_Sort(int[] number_List, int f_Idx, int l_Idx) {
		int build_Start_Index = (l_Idx - f_Idx) / 2 - 1;
		for (int i = build_Start_Index; i >= 0; i--) {
			heapfy(number_List, i, l_Idx);
		}
		for (int i = l_Idx; i > 0; i--) {
			swap(number_List, i, 0);
			heapfy(number_List, 0, i - 1);

		}
	}

	public static void heapfy(int[] number_List, int index, int last_Index) {
		int left_Index = index * 2 + 1;
		int right_Index = index * 2 + 2;
		if (left_Index == last_Index) {
			if (number_List[left_Index] > number_List[index]) {
				swap(number_List, index, left_Index);
				heapfy(number_List, left_Index, last_Index);
			}
		}
		if (last_Index >= right_Index) {
			if (number_List[left_Index] > number_List[right_Index]) {
				if (number_List[left_Index] > number_List[index]) {
					swap(number_List, index, left_Index);
					heapfy(number_List, left_Index, last_Index);
				}
			} else {
				if (number_List[right_Index] > number_List[index]) {
					swap(number_List, index, right_Index);
					heapfy(number_List, right_Index, last_Index);
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
		int size = Integer.parseInt(input.readLine());
		int[] number_List = new int[size];
		StringTokenizer st = new StringTokenizer(input.readLine());
		for (int i = 0; i < size; i++) {
			number_List[i] = Integer.parseInt(st.nextToken());
		}
		heap_Sort(number_List, 0, size - 1);
		for (int i = 0; i < size; i++) {
			output.write(number_List[i] + " ");
		}
		output.flush();
		output.close();
	}
}
