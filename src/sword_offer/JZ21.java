package sword_offer;

import java.util.ArrayList;

//输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。
// 假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，
// 序列4,5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。
// （注意：这两个序列的长度是相等的）
public class JZ21 {
    public boolean IsPopOrder(int[] pushA, int[] popA) {
        ArrayList<Integer> stack = new ArrayList<>();
        for (int i = 0; i < pushA.length; i++) {
            stack.add(pushA[i]);
        }
        for (int i : popA) {
            if (!stack.contains(i))
                return false;
        }
        int indexPushA;
        int indexPopA;
        for (int i = 1; i < popA.length; i++) {
            indexPushA = stack.indexOf(popA[i]);
            indexPopA = stack.indexOf(popA[i - 1]);
            //符合要求的情况 另外第一个元素不受这个限制
            if (Math.abs(indexPushA - indexPopA) == 1 || i == 1) {
                stack.remove(indexPopA);
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayList<Integer> stack = new ArrayList<>();
        stack.add(2);
        stack.add(4);
        stack.add(6);
        stack.add(8);
        stack.add(9);
        System.out.println(stack.indexOf(4));
    }
}
