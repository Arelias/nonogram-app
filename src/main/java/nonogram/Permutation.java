package nonogram;

import java.util.ArrayList;
import java.util.List;

public class Permutation {



    public void rowPermute(int[] row){

    }


    public void permute(int[] elements, List<int[]> outputCollection){
        permute(0, elements.length, elements, outputCollection);
    }


    public void permute(int start, int end, int[] elements, List<int[]> outputCollection) {

        if(end == 1) {
            outputCollection.add(elements);
            printArray(elements);
        } else {
            for(int i = start; i < end-1; i++) {
                permute(start, end - 1, elements, outputCollection);
                if(end % 2 == 0) {
                    swap(elements, i, end-1);
                } else {
                    swap(elements, start, end-1);
                }
            }
            permute(start,end - 1, elements, outputCollection);
        }
    }
    private void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
    public void printArray(int[] input) {
        System.out.print('\n');
        for(int i = 0; i < input.length; i++) {
            System.out.print(input[i]);
        }
    }



}
