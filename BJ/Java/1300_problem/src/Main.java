import java.util.Scanner;
public class Main {
	public static void swap(int[] number_List, int first , int second){
        int tmp = number_List[first];
        number_List[first] = number_List[second];
        number_List[second] = tmp;
    }
    public static int find_Pivot(int[] number_List , int f_Idx , int l_Idx){
        int pivot = number_List[l_Idx];
        int swap_Index = f_Idx - 1;
        int i;
        for (i =f_Idx; i < l_Idx; i++){
            if (pivot >= number_List[i]){
                swap_Index++;
                swap(number_List, i, swap_Index);
            }
        }
        swap_Index++;
        swap(number_List,i,swap_Index);
        return swap_Index;
    }
    public static int select(int[] number_List, int f_Idx, int l_Idx, int find_Index){
        int pivot_Index = find_Pivot(number_List, f_Idx, l_Idx); //이게 pivot의 index 값
        int pivot_Value = pivot_Index - f_Idx + 1;
        
        if (find_Index == pivot_Value){
            return number_List[pivot_Index];
        }
        else if (find_Index > pivot_Value){
            return select(number_List,pivot_Index + 1, l_Idx, find_Index - pivot_Value);
        }
        else {
            return select(number_List,f_Idx,pivot_Index - 1 , find_Index);
        }
    } //5개씩 나눠서 거기서 중간값을 찾아서 가장 끝에 옮겨 놓는 방법 실
    public static int linear_Select(int[] number_List, int f_Idx,int l_Idx, int find_Index) {
    	System.out.println("f_Idx : " + f_Idx + " l_Idx: " + l_Idx + " find_Index : " + find_Index);
    	if (l_Idx - f_Idx + 1 <= 5) {
    		return select(number_List,f_Idx,l_Idx,find_Index);
    	}
    	int size = l_Idx - f_Idx + 1;
    	int list_Size;
    	if (size % 5 == 0) {
    		list_Size = size / 5;
    	}
    	else {
    		list_Size = (size / 5) + 1;
    	}
    	int[] center_Number_List = new int[list_Size];
    	int center_Number_List_Index = 0;
    	int first_Index = f_Idx;
    	int last_Index = f_Idx + 4;
    	int m_Idx = 0;
    	while (true) {
    		if (first_Index + 4 >= l_Idx) {
    			last_Index =l_Idx;
    			m_Idx = (last_Index - first_Index) / 2 + 1;
    			center_Number_List[center_Number_List_Index++] = select(number_List,first_Index,last_Index,m_Idx);
    			break;
    		}
    		m_Idx = (last_Index - first_Index) / 2 + 1;
    		center_Number_List[center_Number_List_Index++] = select(number_List,first_Index,last_Index,m_Idx);
    		first_Index += 5;
    		last_Index += 5;
    	}
    	int pivot_Number = 0;
		m_Idx = (list_Size - 1) / 2 + 1;
		pivot_Number = linear_Select(center_Number_List,0,list_Size - 1,m_Idx);
    	switch_Pivot(number_List, f_Idx, l_Idx, pivot_Number);
    	int pivot_Index = find_Pivot(number_List,f_Idx,l_Idx);
    	int pivot_Value = pivot_Index - f_Idx + 1;
    	if (find_Index == pivot_Value) {
    		return number_List[pivot_Index];
    	}
    	else if (find_Index < pivot_Value) {
    		return linear_Select(number_List,f_Idx,pivot_Index - 1,find_Index);
    	}
    	else {
    		return linear_Select(number_List,pivot_Index + 1, l_Idx, find_Index - pivot_Value);
    	}
    }
    public static void switch_Pivot(int[] number_List , int f_Idx, int l_Idx, int find_Number) {
    	int pivot_Index = 0;
    	for (int i = f_Idx; i <= l_Idx; i++) {
    		if (find_Number == number_List[i]) {
    			pivot_Index = i;
    		}
    	}
    	swap(number_List, pivot_Index , l_Idx);
    }
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        int find_Index = input.nextInt();
        int[] number_List = new int[size * size];
        int number_List_Index = 0;
        for (int i =1; i< size+ 1; i++){
            for (int j =1; j<size+1; j++){
                number_List[number_List_Index] = i * j;
                number_List_Index ++;
            }
        }
        int result = linear_Select(number_List, 0 , size * size - 1 , find_Index);
        System.out.println(result);
        input.close();
	}
}
