import java.util.LinkedList;
import java.util.Queue;

public class AVL {
    public static final int DELETED_NODE_DATA = -1;
    int data, height;//结点数据和树的高度
    AVL left, right;//左子树右子树

    public AVL() {
    }

    public AVL(int data) {
        this.data = data;
    }

    //查找最小值 不断找左子树就行
    AVL findMin(AVL node) {
        AVL iteration = node;
        while (iteration.left != null) {
            iteration = iteration.left;
        }
        return iteration;
    }

    //查找最大值就是找右子树
    AVL findMax(AVL node) {
        AVL iteration = node;
        while (iteration.right != null) {
            iteration = iteration.right;
        }
        return iteration;
    }

    int getHeight(AVL node) {
        if (node == null)
            return 0;
        return node.height;
    }

    /**
     * @param node 根节点
     * @param data 要插入的节点（根据data生成）
     * @return 被旋转的最小子树根节点
     */
    AVL insert(AVL node, int data) {
        if (node == null) {
            node = new AVL();
            node.data = data;
            node.height = 0;
            node.left = null;
            node.right = null;
        }

        //目标节点小于当前节点 则插入当前节点的左子树
        if (data < node.data) {
            node.left = insert(node.left, data);//递归到最后只更新了一个节点高度
            //左右子树高度 计算平衡因子
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            //插入左子树导致失衡
            if (leftHeight - rightHeight > 1) {
                //节点比左子树小 就是在左子树的左子树 LL操作
                if (data < node.left.data) {
                    node = rotationLL(node);
                } else {
                    //旋转lR
                    node = rotationLR(node);
                }
            }
        } else if (data > node.data) {
            //目标节点大于当前节点 则插入当前节点的右子树
            node.right = insert(node.right, data);
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            //如果插入导致右子树失衡，即右子树比左子树高2
            if (rightHeight - leftHeight > 1) {
                if (data > node.right.data) {
                    //插入的结点比右孩子的键值大
                    //那么一定是插入到右孩子的右子树上，故进行RR旋转
                    node = rotationRR(node);
                } else {//否则是插入到右孩子的左子树上，故要进行RL旋转
                    node = rotationRL(node);
                }
            }
        }
        node.height = (getHeight(node.left) > getHeight(node.right) ? getHeight(node.left) : getHeight(node.right)) + 1;
        return node;
    }

    /**
     * @param node 根节点
     * @param data 要被删除的节点数据
     * @return 旋转后的根节点
     */
    AVL delete(AVL node, int data) {
        if (node == null)
            return null;
        //比当前节点小就去左子树找
        if (data < node.data) {
            node.left = delete(node.left, data);
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            //右子树比较高 打破平衡
            if (rightHeight - leftHeight > 1) {
                if (data > node.right.data) {
                    //旋转RR
                    node = rotationRR(node);
                } else {
                    //旋转RL
                    node = rotationRL(node);
                }
            }
        } else if (data > node.data) {
            node.right = delete(node.right, data);
            int leftHeight = getHeight(node.left);
            int rightHeight = getHeight(node.right);
            //左子树比较高 打破平衡
            if (leftHeight - rightHeight > 1) {
                if (data < node.left.data) {
                    //旋转LL
                    node = rotationLL(node);
                } else {
                    //旋转LR
                    node = rotationLR(node);
                }
            }
        } else {
            //左右子树都不为空时用右子树最小节点代替被删除节点
            if (node.left != null && node.right != null) {
                AVL tmp = findMin(node.right);
                node.data = tmp.data;
                //删除这个节点
                tmp.data = DELETED_NODE_DATA;
                tmp.left = null;
                tmp.right = null;
                //delete(node.right, data);
            } else if (node.left == null) {
                node = node.right;
            } else if (node.right == null) {
                node = node.left;
            }
        }
        return node;
    }


    /**
     * 1.	左子树变成新的根节点
     * 2.	旧的根节点变成右子树
     * 3.	左子树的右子树变成原本根节点的左子树
     *
     * @param node
     */
    AVL rotationLL(AVL node) {
        System.out.println("LL run");
        AVL root = node.left;
        node.left = root.right;
        root.right = node;
        node.height = (getHeight(node.left) > getHeight(node.right) ? getHeight(node.left) : getHeight(node.right)) + 1;
        root.height = (getHeight(root.left) > getHeight(root.right) ? getHeight(root.left) : getHeight(root.right)) + 1;
        return root;
    }

    //LR旋转：
    //1.	根节点的左子树进行右旋转
    //2.	根节点进行左旋转
    AVL rotationLR(AVL node) {
        System.out.println("LR run");
        node.left = rotationRR(node.left);
        return rotationLL(node);
    }

    AVL rotationRR(AVL node) {
        System.out.println("RR run");
        AVL root = node.right;
        node.right = root.left;
        root.left = node;
        node.height = (getHeight(node.left) > getHeight(node.right) ? getHeight(node.left) : getHeight(node.right)) + 1;
        root.height = (getHeight(root.left) > getHeight(root.right) ? getHeight(root.left) : getHeight(root.right)) + 1;
        return root;
    }

    //RL旋转：
    //1.	根节点的右子树进行左旋转
    //2.	根节点进行右旋转
    AVL rotationRL(AVL node) {
        System.out.println("RL run");
        node.right = rotationLL(node.right);
        return rotationRR(node);
    }


    void preOrderTraversal(AVL node) {
        if (node == null || node.data == DELETED_NODE_DATA) {
            return;
        }
        System.out.print(node.data + " ");
        node.preOrderTraversal(node.left);
        node.preOrderTraversal(node.right);
    }

    void inOrderTraversal(AVL node) {
        if (node == null || node.data == DELETED_NODE_DATA) {
            return;
        }
        node.inOrderTraversal(node.left);
        System.out.print(node.data + " ");
        node.inOrderTraversal(node.right);
    }

    void postOrderTraversal(AVL node) {
        if (node == null || node.data == DELETED_NODE_DATA) {
            return;
        }
        node.postOrderTraversal(node.left);
        node.postOrderTraversal(node.right);
        System.out.print(node.data + " ");
    }

    //层序遍历
    void layerOrder(AVL node) {
        Queue<AVL> queue = new LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            AVL iterator = queue.poll();
            if (iterator != null && iterator.data != DELETED_NODE_DATA) {
                System.out.print(iterator.data + "\t");
            }
            if (iterator.left != null) {
                queue.offer(iterator.left);
            }
            if (iterator.right != null) {
                queue.offer(iterator.right);
            }
        }
    }

    AVL create(int[] data) {
        AVL node = null;
        for (int i = 0; i < data.length; i++) {
            node = insert(node, data[i]);
            System.out.println("insert returns node data " + node.data);
        }
        return node;
    }

    /**
     * 计算免息期数在预期收益下的折扣率。如预期收益年化10%，12期免息，计算折扣率
     *
     * @param originIncomeRate 预期年化收益
     * @param count            免息期数
     * @return 折扣率
     */
    private float discount(float originIncomeRate, int count) {
        float monthIncomeRate = originIncomeRate / 12f;
        float expectIncome = 0f;
        int originPrice = 1000;
        for (int i = 0; i < count; i++) {
            expectIncome += (originPrice - i * originPrice / count) * monthIncomeRate;
        }
        return expectIncome / originPrice;
    }

    public static void main(String[] args) {
        int data[] = {10, 20, 30, 40, 50, 60, 70};
        AVL node = new AVL().create(data);
        new AVL().delete(node, 40);
        new AVL().layerOrder(node);
        System.out.println(new AVL().discount(0.1f, 24));
    }
}
