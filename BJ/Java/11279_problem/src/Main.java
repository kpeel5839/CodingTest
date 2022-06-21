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
import java.io.*;

public class Main {
	public static class Node{
		private int value;
		private Node left_Node;
		private Node right_Node;
		private Node parent_Node;
		public Node(int value) {
			this.value = value;
			this.left_Node = null;
			this.right_Node = null;
			this.parent_Node = null;
		}
		public void setLeft_Node(Node node) {
			this.left_Node = node;
		}
		public void setRight_Node(Node node) {
			this.right_Node = node;
		}
		public Node getLeft_Node() {
			return this.left_Node;
		}
		public Node getRight_Node() {
			return this.right_Node;
		}
		public int getValue() {
			return this.value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public void setParent_Node(Node node) {
			this.parent_Node = node;
		}
		public Node getParent_Node() {
			return parent_Node;
		}
	}
	public static class Tree{
		Node root_Node;
		Node max_Node;
		public Tree() {
			this.root_Node = null;
			this.max_Node = null;
		}
		public int max_Heap() { // 값을 출력하고 가장 큰 값을 remove 해야 
			if (root_Node == null) {
				return 0;
			}
			else {
				if (max_Node == root_Node) {
					int value = root_Node.getValue();
					if (root_Node.getLeft_Node() != null) {
						root_Node = root_Node.getLeft_Node();
						root_Node.setParent_Node(null);
						Node current_Node = root_Node;
						while(true) {
							if (current_Node.getRight_Node() == null) {
								max_Node = current_Node.getRight_Node();
								break;
							}
							current_Node = current_Node.getRight_Node();
						}
					}
					else {
						root_Node = null;
						max_Node =null;
					}
					return value;
				}
				else {
					int value = max_Node.getValue();
					if (max_Node.getLeft_Node() != null) {
						max_Node.getParent_Node().setRight_Node(max_Node.getLeft_Node());
						Node current_Node = max_Node.getLeft_Node();
						while(true) {
							if (current_Node.getRight_Node() == null) {
								max_Node = current_Node.getRight_Node();
								break;
							}
							current_Node = current_Node.getRight_Node();
						}
					}
					else {
						max_Node = max_Node.getParent_Node();
					}
					return value;
				}
			}
		}
		public void insertValue(int number) {
			if (root_Node == null) {
				root_Node = new Node(number);
				max_Node = root_Node;
			}
			else {
				Node current_Node = root_Node; //value 가 같은 경우에는 오른쪽으로 가도록 하
				while (true) {
					if (number >= current_Node.getValue()) {
						if (current_Node.getRight_Node() == null) {
							current_Node.setRight_Node(new Node(number));
							max_Node = current_Node.getRight_Node();
							max_Node.setParent_Node(current_Node);
							break;
						}
						else {
							current_Node = current_Node.getRight_Node();
							continue;
						}
					}
					else {
						if (current_Node.getLeft_Node() == null) {
							current_Node.setLeft_Node(new Node(number));
							current_Node.getLeft_Node().setParent_Node(current_Node);
							break;
						}
						else {
							current_Node = current_Node.getLeft_Node();
							continue;
						}
					}
				}
			}
		}
	}
	public static void main(String[] args) throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
		Tree tree = new Tree();
		int size = Integer.parseInt(input.readLine());
		int number;
		for (int i = 0; i < size; i++) {
			number = Integer.parseInt(input.readLine());
			if (number == 0) {
				output.write(tree.max_Heap() + "\n");
				continue;
			}
			tree.insertValue(number);
		}
		output.flush();
		output.close();
	}
}
