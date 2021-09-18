import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        PriorityQueue<Integer> queue_Positive = new PriorityQueue<>();
        PriorityQueue<Integer> queue_Negative = new PriorityQueue<>(Collections.reverseOrder());
        int size = Integer.parseInt(input.readLine());
        int input_Value = 0;
        Integer positive_Value = 0;
        Integer negative_Value = 0;
        for (int i = 0; i < size; i++) {
        	input_Value = Integer.parseInt(input.readLine());
        	if (input_Value == 0) {
        		positive_Value = queue_Positive.peek();
        		negative_Value = queue_Negative.peek();
        		if (positive_Value == null && negative_Value == null) {
        			output.write(0 + "\n");
        		}
        		else if(positive_Value == null) {
        			output.write(queue_Negative.poll() + "\n");
        		}
        		else if(negative_Value == null) {
        			output.write(queue_Positive.poll() + "\n");
        		}
        		else {
        			positive_Value = Math.abs(positive_Value);
        			negative_Value = Math.abs(negative_Value);
        			if (negative_Value > positive_Value) {
        				output.write(queue_Positive.poll() + "\n");
        			}
        			else if(negative_Value < positive_Value) {
        				output.write(queue_Negative.poll() + "\n");
        			}
        			else {
        				output.write(queue_Negative.poll() + "\n");
        			}
        		}
        	}
        	else {
        		if(input_Value > 0) {
        			queue_Positive.add(input_Value);
        		}
        		else {
        			queue_Negative.add(input_Value);
        		}
        	}
        }
        output.flush();
        output.close();
    }
}