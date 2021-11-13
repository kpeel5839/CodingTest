import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.PriorityQueue;
import java.util.Collection;
import java.util.Collections;
import java.lang.Math;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
        PriorityQueue queue_Postive = new PriorityQueue(Collections.reverseOrder());
        PriorityQueue queue_Negative = new PriorityQueue();
        int size = Integer.parseInt(input.nextLine());
        int input_Value = 0;
        Integer negative_Value = 0;
        Integer positive_Value = 0;
        for (int i =0; i <size; i++){
            input_Value = Integer.parseInt(input.nextLine());
            if (input_Value == 0){
                negative_Value = Math.abs(queue.Negative.peek());
                positive_Value = Math.abs(queue.Positive.peek());
                if (negative_Value == null && positive_Value == null){
                    output.write(0 + "\n");
                }
                else if(negative_Value == null){
                    output.write(queue_Negative.poll() + "\n");
                }
                else if(negative_Value == null){
                    output.write(queue_Positive.poll() + "\n");
                }
                else{
                    if (negative_Value > positive_Value){
                        output.write(queue_Negative.poll() + "\n");
                    }
                    else if(negative_Value < positive_Value){
                        output.write(queue_Positive.poll() + "\n");
                    }
                    else{
                        output.write(queue_Negative.poll() + "\n");
                    }
                }
            }
            else{
                if (input_Value > 0){
                    queue_Postive.add(input_Value);
                }
                else{
                    queue_Negative.add(input_Value);
                }
            }
        }
        output.flush();
        output.close();
    }
}