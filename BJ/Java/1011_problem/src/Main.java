import java.io.*;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		int count = 0;
		int add = 0;
		int now_Number = 0;
		int size = Integer.parseInt(input.readLine());
		int distance = 0;
		for (int i = 0; i < size; i++) {
			StringTokenizer st = new StringTokenizer(input.readLine());
			int start = Integer.parseInt(st.nextToken());
			int destination = Integer.parseInt(st.nextToken());
			distance = destination - start;
			count = 0;
			add = 1;
			now_Number = 0;
			while (now_Number < distance) {
				System.out.println("now_Number : " + now_Number);
				count++;
				now_Number += add;
				if(count % 2 == 0) {
					add++;
				}
			}
			System.out.println(count);
		}
		input.close();
	}
}