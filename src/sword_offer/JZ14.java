package sword_offer;

import java.util.ArrayList;
import java.util.Collections;

//输入一个链表，输出该链表中倒数第k个结点。
public class JZ14 {
    public ListNode FindKthToTail(ListNode head, int k) {
        if (head == null || k == 0) {
            return null;
        }
        ArrayList<ListNode> list = new ArrayList<>();
        list.add(head);
        while (head.next != null) {
            list.add(head.next);
            head = head.next;
        }
        Collections.reverse(list);
        if (k > list.size())
            return null;
        return list.get(k - 1);
        //或者可以快慢指针，快指针fast先走k步，慢指针slow再和快指针fast一起走，但fast==null时，slow就是所求的值
    }
}
