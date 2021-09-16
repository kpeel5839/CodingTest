package practice;

public class Practice {
	public static class MyThread extends Thread{
		private char my_Word;
		private int my_Number;
		public MyThread(char my_Word, int my_Number) {
			this.my_Word = my_Word;
			this.my_Number = my_Number;
		}
		public void run() {
			for (int i = 0; i < this.my_Number; i++) {
				System.out.print(my_Word);
			}
		}
	}
	public static void main(String[] args) {
		Thread job1 = new MyThread('a', 3);
		Thread job2 = new MyThread('b', 3);
		job1.start();
		job2.start();
		for (int i = 0; i < 5; i++) {
			System.out.print('c');
		}

	}

}
