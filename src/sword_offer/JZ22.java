package sword_offer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class JZ22 {
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode iterator = queue.poll();
            if (iterator != null) {
                result.add(iterator.val);
            }
            if (iterator.left != null) {
                queue.offer(iterator.left);
            }
            if (iterator.right != null) {
                queue.offer(iterator.right);
            }
        }
        return result;
    }

}
