package sword_offer;

//输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
public class JZ16 {
    public ListNode Merge(ListNode list1, ListNode list2) {
        ListNode result = new ListNode(0);//随便一个开头 只是为了不报错
        ListNode iterator = result;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                iterator.next = list1;
                list1 = list1.next;
            } else if (list1.val > list2.val) {
                iterator.next = list2;
                list2 = list2.next;
            }
            iterator = iterator.next;
        }
        if (list1 == null) {
            while (list2 != null) {
                iterator.next = list2;
                list2 = list2.next;
                iterator = iterator.next;
            }
        }
        if (list2 == null) {
            while (list1 != null) {
                iterator.next = list1;
                list1 = list1.next;
                iterator = iterator.next;
            }
        }
        return result.next;
    }

}
