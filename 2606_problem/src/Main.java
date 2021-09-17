import java.util.Scanner;
public class Main {
	public static int count = 0;
	public static void bfs(int[][] path_List, int vertex, int[] visited_List) {
		count++;
		visited_List[vertex] = 1;
		for (int i = 1; i < visited_List.length; i ++) {
			if (i == vertex) {
				continue;
			}
			if (visited_List[i] == 1) {
				continue;
			}
			if (path_List[vertex][i] == 1) {
				bfs(path_List, i,visited_List);
			}
		}
	}
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int vertex_Size = input.nextInt();
		int edge_Size = input.nextInt();
		int[][] path_List = new int[vertex_Size+1][vertex_Size+1];
		int[] visited_List = new int[vertex_Size + 1];
		int first_Vertex = 0;
		int second_Vertex = 0;
		for (int i = 0; i < edge_Size; i++) {
			first_Vertex = input.nextInt();
			second_Vertex = input.nextInt();
			path_List[first_Vertex][second_Vertex] = 1;
			path_List[second_Vertex][first_Vertex] = 1;
		}
		bfs(path_List, 1, visited_List);
		System.out.println(count - 1);
		input.close();
	}
}
