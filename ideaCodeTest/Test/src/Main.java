import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(System.in);
        List[][] map = new ArrayList[3][3];
        for(int i =0; i < 2; i++){
            for(int j =0 ; j < 2; j++){
                map[i][j] = new ArrayList<Integer>();
                for(int c = 0; c < 8; c++){
                    map[i][j].add(input.nextInt());
                }
            }
        }

        for(int i =0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                Collections.sort(map[i][j]);
                boolean die = false;
                int index = 0;
                for(Integer number : new ArrayList<Integer>(map[i][j])){
                    System.out.print(number + " ");
                    if(number == 2){
                        die = true;
                    }
                    if(die == true) {
                        map[i][j].remove(index);
                    }
                    if(die == false){
                        index++; //삭제는 이런식으로 가면 될 듯 죽기 시작하면 index는 고정하고 그냥 다 죽여버리는 것 근데 이제 죽이면서 양분을 남기는 거지 자신의 나이만큼
                    }
                }
                System.out.println();
            }
        }

        System.out.println("remove 이후");
        for(int i =0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                for(Integer number : new ArrayList<Integer>(map[i][j])){
                    System.out.print(number + " ");
                }
                System.out.println();
            }
        }
    }
}
