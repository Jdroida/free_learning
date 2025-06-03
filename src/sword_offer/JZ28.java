package sword_offer;

//数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
// 例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，
// 超过数组长度的一半，因此输出2。如果不存在则输出0。
public class JZ28 {
    public int MoreThanHalfNum_Solution(int[] array) {
        int length = array.length / 2;
        int count;
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            count = 1;
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j])
                    count++;
            }
            if (count > length) {
                result = array[i];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new JZ28().MoreThanHalfNum_Solution(new int[]{1, 3, 4, 5, 2, 2, 2, 2, 2}));
    }

}
