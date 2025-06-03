package sword_offer;

//输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，
// 所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
public class JZ13 {
    public void reOrderArray(int[] array) {
        int[] result = new int[array.length];
        int index = 0;
        for (int i : array) {
            if (i % 2 != 0) {
                result[index] = i;
                index++;
            }
        }
        for (int i : array) {
            if (i % 2 == 0) {
                result[index] = i;
                index++;
            }
        }
        for (int i = 0; i < result.length; i++) {
            array[i] = result[i];
        }
    }

    //地上有一个 rows 行和 cols 列的方格。坐标从 [0,0] 到 [rows-1,cols-1] 。一个机器人从坐标 [0,0] 的格子开始移动，
    // 每一次只能向左，右，上，下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于 threshold 的格子。
    // 例如，当 threshold 为 18 时，机器人能够进入方格   [35,37] ，因为 3+5+3+7 = 18。但是，它不能进入方格 [35,38] ，
    // 因为 3+5+3+8 = 19 。请问该机器人能够达到多少个格子？
    public int movingCount(int threshold, int rows, int cols) {
        if (rows <= 0 || cols <= 0 || threshold < 0)
            return 0;

        boolean[][] isVisited = new boolean[rows][cols];//标记
        int count = movingCountCore(threshold, rows, cols, 0, 0, isVisited);
        return count;
    }

    private int movingCountCore(int threshold, int rows, int cols,
                                int row, int col, boolean[][] isVisited) {
        if (row < 0 || col < 0 || row >= rows || col >= cols || isVisited[row][col]
                || cal(row) + cal(col) > threshold)
            return 0;
        isVisited[row][col] = true;
        return 1 + movingCountCore(threshold, rows, cols, row - 1, col, isVisited)
                + movingCountCore(threshold, rows, cols, row + 1, col, isVisited)
                + movingCountCore(threshold, rows, cols, row, col - 1, isVisited)
                + movingCountCore(threshold, rows, cols, row, col + 1, isVisited);
    }

    private int cal(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] a = {2, 4, 6, 1, 3, 5};
        new JZ13().reOrderArray(a);
        for (int i : a) {
            System.out.println(i);
        }
    }
}
