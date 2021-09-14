import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int size = input.nextInt();
		int[] number_List = new int[size];
		List<Integer> list = new ArrayList<>();
		list.add(0);
		for (int i = 0; i < size; i++) {
			number_List[i] = input.nextInt();
		}
		for (int i = 0; i < size; i++) {
			int value = number_List[i];
			if (list.get(list.size() - 1) < value) {
				list.add(value);
			}
			else {
				int left = 0;
				int right = list.size() - 1;
				int mid = 0;
				while (left < right) {
					mid = (left + right) / 2;
					if(list.get(mid) >= value) {
						right = mid; //이렇게 하는 이유는 얘보다 작으면 그 수가 바꿔야 하는 수일 확률이 높아서 같으면 말할 것도 없
					}
					else {
						left = mid + 1;
					}
				}
				list.set(right, value); //이렇게 하는 이유는 짜피 작은 수가 뒤에 와서 바뀐다 하더라도 그 수보다 크면 분명히 대체 될것이니
			}
		}
		System.out.println(list.size() - 1);
	}
}
//그니깐 이 방법은 확률을 많이 남겨 놓는 것임 왜냐하면 짜피 더 작은 수가 나왔다고 해서 그것을 추가하는게 아니라 교체하는 것이기 때문에그 교체한 수부터 다시 시작할 수 있는 여력을 주는 것임
//만일 여력이 되지 않으면 이전에 교체 되기 전으로 계속 남아 있을 것이고 계속 남아 있다면 이전에 선택했던 최대개수가 그게 진짜가 되는 거고
//그리고 만일 내가 새로 한 것부터 최대 개수가 나오기 시작하면 그걸로 다시 시작하는 것이고 이것은 그니까 list 안에 값이 중요한 것이 아니라 list의 size 가 important 한 
//list.size() - 1 을 하는 이유는 처음에 0 이 들어가기 떄문이다. 쨋든 개수를 추가하는 것이 아니라교체 하는 것이기 때문에 그것도 적절한 위치에 개수에 영향을 주지 않는 다는 것이다.
//그렇기 때문에 개수가 정확히 나올 수 있는 것
