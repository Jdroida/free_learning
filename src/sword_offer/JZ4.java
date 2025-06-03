package sword_offer;

public class JZ4 {
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        /**
         * 确定根节点（前序遍历的第一个值） 找到根节点在中序遍历中的位置 中序遍历中根节点的左边就是左子树的集合，右边就是右子树的集合
         * 中序遍历 左右子树为空就是递归终点
         */
        if (pre.length == 0 && in.length == 0)
            return null;
        TreeNode root = new TreeNode(pre[0]);
        int rootIndex = 0, length = pre.length;//rootIndex也可以理解为左子树集合数
        for (int i = 0; i < length; i++) {
            if (in[i] == root.val)
                rootIndex = i;
        }
        int leftPre[] = new int[rootIndex], leftIn[] = new int[rootIndex],
                rightPre[] = new int[length - rootIndex - 1], rightIn[] = new int[length - rootIndex - 1];
        for (int i = 0; i < rootIndex; i++) {
            leftPre[i] = pre[i + 1];
            leftIn[i] = in[i];
        }
        for (int i = 0; i < length - rootIndex - 1; i++) {
            //都是偏移一个根节点+左子树长度
            rightPre[i] = pre[i + rootIndex + 1];
            rightIn[i] = in[i + rootIndex + 1];
        }
        root.left = reConstructBinaryTree(leftPre, leftIn);
        root.right = reConstructBinaryTree(rightPre, rightIn);
        return root;
    }

    public static void main(String[] args) {
        int pre[] = {1, 2, 4, 7, 3, 5, 6, 8}, in[] = {4, 7, 2, 1, 5, 3, 8, 6};
        TreeNode tree = new JZ4().reConstructBinaryTree(pre, in);
        System.out.println(tree.val);
    }
}
