package sword_offer;

import java.util.HashMap;

//输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，
// 另一个特殊指针random指向一个随机节点），请对此链表进行深拷贝，并返回拷贝后的头结点。
// （注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
public class JZ25 {
    //仔细一看这好像就是二叉树……
    public RandomListNode Clone(RandomListNode pHead) {
        HashMap<RandomListNode, RandomListNode> map = new HashMap<>();
        RandomListNode p = pHead;
        //第一次遍历 新建立节点
        while (p != null) {
            RandomListNode newNode = new RandomListNode(p.label);
            map.put(p, newNode);
            p = p.next;
        }
        //第二次遍历 赋值映射关系
        p = pHead;
        while (p != null) {
            RandomListNode node = map.get(p);
            node.next = (p.next == null) ? null : map.get(p.next);
            node.random = (p.random == null) ? null : map.get(p.random);
            p = p.next;
        }
        //最后的返回值
        return map.get(pHead);
    }
}
