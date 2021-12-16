import java.util.*;
import java.io.*;

public class Main {
    public static class Pair implements Comparable<Pair>{
        int x;
        int y;
        public Pair(int x , int y){
            this.x = x;
            this.y = y;
        }

        public int compareTo(Pair o){ //각 이 숫자로 우선순위가 적용 되나봄 , -1 , 1만 반환할 수 있는 것이 아니라
            if(this.y > o.y){
                return -1;
            }
            else if(this.y == o.y){
                return o.x - this.x;
            }
            else{
                return 1;
            }
        } //이렇게 생각하면 될 듯 , 그니까 비교하였을 떄 숫자가 더 크면 뒤쪽으로 숫자가 더 작으면 앞쪽으로 오는 것이다 , 그러니까 오름차순으로 정렬하고 싶을때에는
        //self 즉 , this 의 값이 더 큰 경우에 양수의 숫자 즉 더 큰 숫자를 부여하면 되는 것이고 , 반대의 경우인 내림차순일 경우에는
        //this 의 값이 더 큰 경우에 음수의 숫자를 주면 되는 것이다.
        //이 때 만약 값이 여러개다 , 여러개의 값을 보고 정렬해야 하는 상황이라면, 그럴 때에는 위에서 했던 식으로 this.y == o.y 이런 식으로 처리하면 된다.
        //위의 상황을 예로 들면 this.y == o.y 즉 y 값이 같은 경우에는 x 의 값을 내림 차순으로 정렬하고 싶은 경우이다.
        //그래서 내가 더 큰 경우에는 ? 더욱 낮은 숫자를 받아야 하는 것이다.
        //그렇기 때문에 더 낮은 숫자를 받을 수 있도록 this.x 가 더 큰 경우에는 o.x - this.x 하게 되면 음수의 값이 나온다 , 그러면 this 가 더 큰 경우에
        //앞쪽에 오도록 정렬이 가능한 것이다 이런식으로 return o.x - this.x 와 같은 트릭을 쓰게 된다면 , 그렇게 된다면 값을 내가 원하는 대로 정렬할 수 있다.
        //comparable 을 implements 한 객체를 가지고서 Collections.sort할 수 있는 것이다.
        //Integer , String 이러한 것들은 Comparable 을 기본적으로 implements하기 때문에 그냥 collections.sort할 수 있는 것이다.

        @Override
        public String toString(){
            return "x : " + x + " y : " + y;
        }
    }
    public static void main(String[] args) throws IOException{ //Comparable 시험해보기 객체 정렬 두가지로도
        List<Pair> sortList = new ArrayList<>();
        Random random = new Random();
        int number = 6;
        for(int i = 12; i != -1; i--){
            if(i % 3 == 0){
                number--;
            }
            sortList.add(new Pair(random.nextInt(11) , number));
        }
        for(Pair pair : sortList){
            System.out.println(pair);
        }
        Collections.sort(sortList);
        System.out.println();
        for(Pair pair : sortList){
            System.out.println(pair);
        }
    }
}
