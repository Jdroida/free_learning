package sword_offer;

//把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含质因子7。
// 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。
public class JZ33 {
    public int GetUglyNumber_Solution(int index) {
        if (index < 7) return index;
        int[] ret = new int[index];
        ret[0] = 1;
        int t2 = 0, t3 = 0, t5 = 0;
        for (int i = 1; i < index; i++) {
            ret[i] = min(min(ret[t2] * 2, ret[t3] * 3), ret[t5] * 5);
            if (ret[i] == ret[t2] * 2) t2++;
            if (ret[i] == ret[t3] * 3) t3++;
            if (ret[i] == ret[t5] * 5) t5++;
        }
        return ret[index - 1];
    }

    public static int min(int a, int b) {
        return a < b ? a : b;
    }

}
