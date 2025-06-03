package sword_offer;

import java.util.Stack;

//定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。
public class JZ20 {
    Stack<Integer> all = new Stack<>();
    Stack<Integer> min = new Stack<>();

    public void push(int node) {
        all.push(node);
        if (min.empty()) {
            min.push(node);
        } else if (node < min()) {
            min.push(node);
        }
    }

    public void pop() {
        int pop = all.pop();
        if (pop == min())
            min.pop();
    }

    public int top() {
        return all.peek();
    }

    public int min() {
        return min.peek();
    }

}
