package sword_offer;

import java.util.ArrayList;
import java.util.Collections;

//输入一个链表，按链表从尾到头的顺序返回一个ArrayList
public class JZ3 {
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> result = new ArrayList<>();
        while (listNode != null) {
            result.add(listNode.val);
            listNode = listNode.next;
        }
        Collections.reverse(result);
        return result;
    }
}



