import java.util.Collections;
import java.util.PriorityQueue;
import java.io.*;
public class Main{
	public static void main(String[] args) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>(Collections.reverseOrder());
		int size = Integer.parseInt(input.readLine());
		int number = 0;
		for (int i =0; i<size; i++) {
			number = Integer.parseInt(input.readLine());
			if (number == 0) {
				if (heap.size() == 0) {
					output.write(0 + "\n");
					output.flush();
				}
				else {
					output.write(heap.poll() + "\n");
					output.flush();
					continue;
				}
			}
			heap.add(number);
		}
		output.close();
	}
}
