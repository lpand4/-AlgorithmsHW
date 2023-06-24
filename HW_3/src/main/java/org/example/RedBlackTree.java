package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RedBlackTree {
    Node root;

    public boolean add(int value){
        if(root != null){
            boolean res = addNode(root, value);
            root = rebalance(root);
            root.color = Color.BLACK;
            return res;
        }else {
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }
    private boolean addNode(Node node, int value){
        if(node.value == value){
            return false;
        }else {
            if(node.value > value){
                    if(node.left != null){
                        boolean result = addNode(node.left, value);
                        node.left = rebalance(node.left);
                        return result;
                    }else {
                        node.left = new Node();
                        node.left.value = value;
                        node.left.color = Color.RED;
                        return true;
                    }
                }else{
                    if(node.right != null){
                        boolean result = addNode(node.right, value);
                        node.right = rebalance(node.right);
                        return result;
                    }else {
                        node.right = new Node();
                        node.right.value = value;
                        node.right.color = Color.RED;
                        return true;
                    }
            }
        }

    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.right != null && result.right.color == Color.RED &&
                    (result.left == null || result.left.color == Color.BLACK)) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.left != null && result.left.color == Color.RED &&
                    result.left.left != null && result.left.left.color == Color.RED) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.left != null && result.left.color == Color.RED &&
                    result.right != null && result.right.color == Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }
        } while (needRebalance);
        return result;
    }
    private Node leftSwap(Node node){
        Node left = node.left;
        node.left = left.right;
        left.right = node;
        left.color = node.color;
        node.color = Color.RED;
        return left;
    }
    private Node rightSwap(Node node){
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        right.color = node.color;
        node.color = Color.RED;
        return right;
    }
    private void colorSwap(Node node){
        node.left.color = Color.BLACK;
        node.right.color = Color.BLACK;
        node.color = Color.RED;
    }
    public void print(){
        print(root);
    }

    private void print(Node node){
        if(node == null)
            return;
        System.out.println(node.value);

        if(node.left != null)
            System.out.println("L:" + node.left.value);

        if(node.right != null)
            System.out.println("R:" + node.right.value);

        print(node.left);
        print(node.right);
    }

    class Node implements TreePrinter.PrintableNode {
        int value;
        Color color;
        Node left;
        Node right;

        @Override
        public TreePrinter.PrintableNode getLeft() {
            return left;
        }

        @Override
        public TreePrinter.PrintableNode getRight() {
            return right;
        }

        @Override
        public String getText() {
            StringBuilder sb = new StringBuilder();
            sb.append(value).append(" ").append(color);
            return sb.toString();
        }
    }
    private enum Color{
        RED, BLACK
    }
    public static class TreePrinter
    {
        /** Node that can be printed */
        public interface PrintableNode
        {
            /** Get left child */
            PrintableNode getLeft();


            /** Get right child */
            PrintableNode getRight();


            /** Get text to be printed */
            String getText();
        }


        /**
         * Print a tree
         *
         * @param root
         *            tree root node
         */
        public static void print(PrintableNode root)
        {
            List<List<String>> lines = new ArrayList<List<String>>();

            List<PrintableNode> level = new ArrayList<PrintableNode>();
            List<PrintableNode> next = new ArrayList<PrintableNode>();

            level.add(root);
            int nn = 1;

            int widest = 0;

            while (nn != 0) {
                List<String> line = new ArrayList<String>();

                nn = 0;

                for (PrintableNode n : level) {
                    if (n == null) {
                        line.add(null);

                        next.add(null);
                        next.add(null);
                    } else {
                        String aa = n.getText();
                        line.add(aa);
                        if (aa.length() > widest) widest = aa.length();

                        next.add(n.getLeft());
                        next.add(n.getRight());

                        if (n.getLeft() != null) nn++;
                        if (n.getRight() != null) nn++;
                    }
                }

                if (widest % 2 == 1) widest++;

                lines.add(line);

                List<PrintableNode> tmp = level;
                level = next;
                next = tmp;
                next.clear();
            }

            int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
            for (int i = 0; i < lines.size(); i++) {
                List<String> line = lines.get(i);
                int hpw = (int) Math.floor(perpiece / 2f) - 1;

                if (i > 0) {
                    for (int j = 0; j < line.size(); j++) {

                        // split node
                        char c = ' ';
                        if (j % 2 == 1) {
                            if (line.get(j - 1) != null) {
                                c = (line.get(j) != null) ? '┴' : '┘';
                            } else {
                                if (j < line.size() && line.get(j) != null) c = '└';
                            }
                        }
                        System.out.print(c);

                        // lines and spaces
                        if (line.get(j) == null) {
                            for (int k = 0; k < perpiece - 1; k++) {
                                System.out.print(" ");
                            }
                        } else {

                            for (int k = 0; k < hpw; k++) {
                                System.out.print(j % 2 == 0 ? " " : "─");
                            }
                            System.out.print(j % 2 == 0 ? "┌" : "┐");
                            for (int k = 0; k < hpw; k++) {
                                System.out.print(j % 2 == 0 ? "─" : " ");
                            }
                        }
                    }
                    System.out.println();
                }

                // print line of numbers
                for (int j = 0; j < line.size(); j++) {

                    String f = line.get(j);
                    if (f == null) f = "";
                    int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                    int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                    // a number
                    for (int k = 0; k < gap1; k++) {
                        System.out.print(" ");
                    }
                    System.out.print(f);
                    for (int k = 0; k < gap2; k++) {
                        System.out.print(" ");
                    }
                }
                System.out.println();

                perpiece /= 2;
            }
        }
    }

    public static void main(String[] args) {
        final RedBlackTree rbt = new RedBlackTree();
        TreePrinter tp = new TreePrinter();
        for (int i = 1; i <= 8; i++) {
            rbt.add(i);
        }
        tp.print(rbt.root);
    }
}
