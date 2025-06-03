package sword_offer;

//不用加减乘除做加法
public class JZ48 {

    public int add(int num1, int num2) {
        int result = num1 ^ num2; //不带进位的加法
        int carry = (num1 & num2) << 1;//进位
        // 进位不为0则继续执行加法处理进位
        while (carry != 0) {
            result = num1 ^ num2;
            carry = (num1 & num2) << 1;
            num1 = result;
            num2 = carry;
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(new JZ48().add(45, 56));
    }
}
