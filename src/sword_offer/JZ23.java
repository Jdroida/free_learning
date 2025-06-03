package sword_offer;

//输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则返回true,否则返回false。
// 假设输入的数组的任意两个数字都互不相同。
public class JZ23 {
    public boolean VerifySquenceOfBST(int[] sequence) {
        //对于空数列的结果 题目要求和我的递归终点还矛盾 NND
        if (sequence.length == 0)
            return false;
        return normalAnswer(sequence);
    }

    private boolean normalAnswer(int[] sequence) {
        if (sequence.length == 0)
            return true;
        int left[], right[];
        int leftSize = 0, rightSize = 0;
        for (int i = 0; i < sequence.length - 1; i++) {
            if (sequence[i] < sequence[sequence.length - 1]) {
                leftSize++;
            } else {
                rightSize++;
            }
        }
        left = new int[leftSize];
        right = new int[rightSize];
        //左子树必须全比根节点小 右子树必须全比根节点大
        for (int i = 0; i < leftSize; i++) {
            left[i] = sequence[i];
            if (left[i] > sequence[sequence.length - 1])
                return false;
        }
        for (int i = 0; i < rightSize; i++) {
            right[i] = sequence[leftSize + i];
            if (right[i] < sequence[sequence.length - 1])
                return false;
        }
        return normalAnswer(left) && normalAnswer(right);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 0; i++) {
            System.out.println(111);
        }
    }
}
