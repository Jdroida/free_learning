package sword_offer;

import java.util.Stack;

//两个栈实现队列
public class JZ5 {
    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        int size1 = stack1.size();
        for (int i = 0; i < size1; i++) {
            stack2.push(stack1.pop());
        }
        int result = stack2.pop();
        int size2 = stack2.size();
        for (int i = 0; i < size2; i++) {
            stack1.push(stack2.pop());
        }
        return result;
    }

    public static void main(String[] args) {
        JZ5 solution = new JZ5();
        solution.push(1);
        solution.push(2);
        solution.push(3);
        System.out.println(solution.pop());
        System.out.println(solution.pop());
        solution.push(4);
        System.out.println(solution.pop());
        solution.push(5);
        System.out.println(solution.pop());
        System.out.println(solution.pop());
    }
}