import java.util.Random;

public class BestStopTheory {
    //最优停止理论
    public static void main(String[] args) {
        /**
         *假设有100个人，分数在1到1000之间
         * 面试官要在这些人中找到最合适的
         * 已经面试过的 没录取的 不能再录取
         * 理论上说37%以后遇到的第一个比之前都好的人
         * 就是最合理的选择
         * 下面验证一下
         */
        for (int count = 0; count < 20; count++) {
            int candidate[] = new int[100];
            for (int i = 0; i < 100; i++) {
                candidate[i] = (int) (Math.random() * 1000);
            }
            int maxInTop37 = candidate[0];
            for (int i = 0; i < 37; i++) {
                maxInTop37 = candidate[i] > maxInTop37 ? candidate[i] : maxInTop37;
            }
            int bestMatch = maxInTop37;
            for (int i = 37; i < 100; i++) {
                if (candidate[i] > maxInTop37) {
                    bestMatch = maxInTop37;
                    break;
                }
            }
            int rank = 1;
            for (int i = 0; i < 100; i++) {
                if (candidate[i] > bestMatch)
                    rank++;
            }
            System.out.println("最优停止理论最后选出的候选人实际的排名是(一共100人):" + rank);
        }
    }
}
