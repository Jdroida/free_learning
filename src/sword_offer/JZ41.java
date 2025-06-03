package sword_offer;

import java.util.ArrayList;

//找出所有和为S的连续正数序列
public class JZ41 {
    public ArrayList<ArrayList<Integer>> findContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int i = 1; i <= sum / 2; i++) {
            int start = i;
            int target = i;
            int counter = 0;
            ArrayList<Integer> partResult = new ArrayList<>();
            while (target < sum) {
                counter++;
                target = target + start + counter;
            }
            if (target == sum) {
                for (int j = start; j <= start + counter; j++) {
                    partResult.add(j);
                }
                result.add(partResult);
            }
        }
        System.out.println("continuous list result:");
        for (ArrayList<Integer> list : result) {
            System.out.println(list);
        }
        return result;
    }

    public static void main(String[] args) {
        new JZ41().findContinuousSequence(1000);
    }
}
