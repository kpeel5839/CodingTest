import java.util.PriorityQueue;
import java.io.*;
public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
		int size = Integer.parseInt(input.readLine());
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>();
		int number = 0;
		for (int i =0; i< size; i++) {
			number = Integer.parseInt(input.readLine());
			if (number == 0) {
				if (heap.size() == 0) {
					output.write(0 + "\n");
					continue;
				}
				else {
					output.write(heap.poll() + "\n");
					continue;
				}
			}
			heap.add(number);
		}
		output.flush();
		output.close();
	}
}
