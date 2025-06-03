package sword_offer;

import java.util.ArrayList;
import java.util.Collections;

//输入一个链表，反转链表后，输出新链表的表头。
public class JZ15 {
    @SuppressWarnings("DuplicatedCode")
    public ListNode ReverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ArrayList<Integer> list = new ArrayList<>();
        list.add(head.val);
        while (head.next != null) {
            list.add(head.next.val);
            head = head.next;
        }
        Collections.reverse(list);
        ListNode iterator = head;
        for (int i = 1; i < list.size(); i++) {
            ListNode temp = new ListNode(list.get(i));
            iterator.next = temp;
            iterator = temp;
        }
        return head;
    }

}
