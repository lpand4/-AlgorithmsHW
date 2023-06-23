package org.example;

public class ListOne {
    Node head;
    //List.Node tail;


    public void push(int value){
        Node node = new Node();
        node.value = value;
        node.next = head;
        head = node;
    }

    public Integer pop(){
        Integer result = null;
        if (head != null){
            result= head.value;
            head = head.next;
        }
        return result;
    }

    public void revert(){
        if(head != null && head.next != null){
            revert(head.next, head);
        }
    }
    private void revert(Node currentNode, Node previousNode){
        if(currentNode.next == null){
            head = currentNode;
        }else {
            revert(currentNode.next, currentNode);
        }
        currentNode.next = previousNode;
        previousNode.next = null;
    }

    public class Node{
        int value;
        Node next;
        //List.Node previous;
    }
}
