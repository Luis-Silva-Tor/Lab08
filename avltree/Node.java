package avltree;

public class Node<E> {
    protected E data;
    protected Node<E> left, right;

    public Node(E data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}
