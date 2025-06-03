package sword_offer;

//二维数组中的查找
public class JZ1 {
    /**
     * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，
     * 判断数组中是否含有该整数。
     *
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        if (rows == 0 || cols == 0)
            return false;
        //从左下角开始找 左下角是每一行做小的数也是每一列最大的数
        int row = rows - 1;
        int col = 0;
        while (row >= 0 && col < cols) {
            if (target > array[row][col]) {
                col++;
            } else if (target < array[row][col]) {
                row--;
            } else {
                return true;
            }
        }
        return false;
    }
}
