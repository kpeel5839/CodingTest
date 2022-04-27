import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
		int size = Integer.parseInt(input.readLine());
		int index = 0;
		String[][] result_String = new String[size][1000];
		StringTokenizer st;
		StringBuffer sb;
		for (int i = 0; i < size; i++) {
			index = 0;
			st = new StringTokenizer(input.readLine());
			while(st.hasMoreTokens()) {
				sb = new StringBuffer(st.nextToken());
				result_String[i][index] =  sb.reverse().toString();
				index++;
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < 1000; j++) {
				if (result_String[i][j] == null)
					break;
				output.write(result_String[i][j] + " ");
			}
			output.write("\n");
		}
		output.flush();
		output.close();
	}
}
