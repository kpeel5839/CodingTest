import java.util.*;
import java.io.*;

public class Main{
    public static double[] l;
    public static void mergeSort(int[][] points , int low , int high){
        if(low >= high){
            return;
        }
        int middle = (low + high) / 2;
        mergeSort(points , low , middle);
        mergeSort(points , middle + 1, high);
        merge(points , low, high , middle);
    }
    public static void merge(int[][] points , int low , int high , int middle){
        int right = middle + 1;
        int left = low;
        int size = high - low + 1;
        double[] temp = new double[size];
        int[][] tempPoints = new int[size][2];
        int tempIndex = 0;
        while(left <= middle && right <= high){
            if(l[left] <= l[right]){
                temp[tempIndex] = l[left];
                tempPoints[tempIndex++] = points[left++];
            }
            else{
                temp[tempIndex] = l[right];
                tempPoints[tempIndex++] = points[right++];
            }
        }
        while(left <= middle){
            temp[tempIndex] = l[left];
            tempPoints[tempIndex++] = points[left++];
        }
        while(right <= high){
            temp[tempIndex] = l[right];
            tempPoints[tempIndex++] = points[right++];
        }
        tempIndex = 0;
        for(int i = low; i <= high; i++){
            l[i] = temp[tempIndex];
            points[i] = tempPoints[tempIndex++];
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(input.readLine() , ",");

        int index = 0;
        int size = st.countTokens() / 2;

        int[][] points = new int[size][2];

        while(st.hasMoreTokens()){
            points[index][0] = Integer.parseInt(st.nextToken());
            points[index++][1] = Integer.parseInt(st.nextToken());
        }

        int k = Integer.parseInt(input.readLine());
        int[][] result = kClosest(points , k);
        for(int i = 0; i < result.length; i++){
            System.out.print("[");
            for(int j = 0; j < result[i].length; j++){
                if(j == 1){
                    System.out.print(result[i][j] + "]");
                }
                else{
                    System.out.print(result[i][j] + ",");
                }
            }
            if(i != result.length - 1){
                System.out.print(",");
            }
        }
    }
    /*
    -- ??????
    1. ??? point?????? 1?????? ????????? 2?????? ????????? ????????? ????????? ????????? root?????? ????????????.
    2. ????????? point.length??? ?????? ????????? ????????? ??? ??? root?????? ????????????.
    3. ??? ????????? mergeSort??? ??????????????? ?????? points ????????? ???????????????.
    4. ?????? i??? ????????? ??? ??? ?????? ???
     */
    public static int[][] kClosest(int[][] points, int k){
        l = new double[points.length];
        for(int i = 0; i < points.length; i++){
            l[i] = Math.sqrt(Math.pow(points[i][0], 2) + Math.pow(points[i][1], 2));
        }
        mergeSort(points , 0 , l.length - 1);
        int[][] result = new int[k][2];
        for(int i = 0; i < result.length; i++){
            result[i][0] = points[i][0];
            result[i][1] = points[i][1];
        }
        return result;
    }
}
