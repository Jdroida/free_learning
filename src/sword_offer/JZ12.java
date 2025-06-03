package sword_offer;

public class JZ12 {
    //给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
    //保证base和exponent不同时为0
    public double Power(double base, int exponent) {
        double result = 1;
        for (int i = 0; i < Math.abs(exponent); i++) {
            result *= base;
        }
        return exponent > 0 ? result : 1 / result;
    }

    /**
     * 请设计一个函数，用来判断在一个n乘m的矩阵中是否存在一条包含某长度为len的字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，
     * 每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。
     *          a   b   c   e
     * 例如     s   f   c   s
     *          a   d   e   e
     * 矩阵中包含一条字符串"bcced"的路径，但是矩阵中不包含"abcb"路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，
     * 路径不能再次进入该格子。
     */
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param matrix char字符型二维数组
     * @param word   string字符串
     * @return bool布尔型
     */
    public boolean hasPath(char[][] matrix, String word) {
        char[] words = word.toCharArray();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                //从[i,j]这个坐标开始查找
                if (dfs(matrix, words, i, j, 0))
                    return true;
            }
        }
        return false;
    }

    boolean dfs(char[][] matrix, char[] word, int i, int j, int index) {
        //边界的判断，如果越界直接返回false。index表示的是查找到字符串word的第几个字符，
        //如果这个字符不等于matrix[i][j]，说明验证这个坐标路径是走不通的，直接返回false
        if (i >= matrix.length || i < 0 || j >= matrix[0].length || j < 0 ||
                matrix[i][j] != word[index])
            return false;
        //如果word的每个字符都查找完了，直接返回true
        if (index == word.length - 1)
            return true;
        //把当前坐标的值保存下来，为了在最后复原
        char tmp = matrix[i][j];
        //然后修改当前坐标的值
        matrix[i][j] = '.';
        //走递归，沿着当前坐标的上下左右4个方向查找
        boolean res = dfs(matrix, word, i + 1, j, index + 1)
                || dfs(matrix, word, i - 1, j, index + 1)
                || dfs(matrix, word, i, j + 1, index + 1)
                || dfs(matrix, word, i, j - 1, index + 1);
        //递归之后再把当前的坐标复原
        matrix[i][j] = tmp;
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new JZ12().Power(2, -3));
    }
}
