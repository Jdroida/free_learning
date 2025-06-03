package sword_offer;

//统计一个数字在升序数组中出现的次数。
public class JZ37 {

    public int GetNumberOfK(int[] array, int k) {
//二分查找先找到位置 然后左右方向分别统计次数
        if (array == null || array.length == 0)
            return 0;
        int left = 0, right = array.length - 1, mid = (left + right) / 2;
        while (left < right) {
            if (k > array[mid]) {
                left = mid + 1;
            } else if (k < array[mid]) {
                right = mid - 1;
            }
            mid = (left + right) / 2;
            if (array[mid] == k)
                break;
        }
        if (k != array[mid])
            return 0;
        int count = -1;
        for (int i = mid; i >= 0 && array[i] == k; i--) {
            count++;
        }
        for (int i = mid; i < array.length && array[i] == k; i++) {
            count++;
        }
        return count;
    }

}
