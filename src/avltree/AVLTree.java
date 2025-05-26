package avltree;

public class AVLTree<E extends Comparable<E>> extends BSTree<E> {

    protected NodeAVL root; // Nodo raíz del árbol AVL
    private boolean height; // Bandera para controlar si cambió la altura del subárbol

    // Subclase de nodo AVL con factor de equilibrio
    class NodeAVL extends Node<E> {
        protected int bf; // Balance factor del nodo

        public NodeAVL(E data) {
            super(data);
            this.bf = 0; // Inicialmente balanceado
        }

        public String toString() {
            return super.toString() + " [bf=" + bf + "]"; // Muestra dato y factor de equilibrio
        }
    }

    // Inserción pública en el árbol AVL
    public void insert(E x) throws ItemDuplicated {
        this.height = false;
        this.root = insert(x, root);
    }

    // Inserción privada recursiva con balanceo
    protected NodeAVL insert(E x, NodeAVL node) throws ItemDuplicated {
        NodeAVL fat = node;

        if (node == null) {
            this.height = true;
            fat = new NodeAVL(x); // Crea nuevo nodo AVL
        } else {
            int resC = x.compareTo(node.data);
            if (resC == 0) {
                throw new ItemDuplicated(x + " ya está en el árbol"); // No se permiten duplicados
            } else if (resC > 0) {
                fat.right = insert(x, (NodeAVL) node.right); // Inserta a la derecha
                if (this.height) {
                    switch (fat.bf) {
                        case -1:
                            fat.bf = 0;
                            this.height = false;
                            break;
                        case 0:
                            fat.bf = 1;
                            this.height = true;
                            break;
                        case 1:
                            fat = balanceToLeft(fat); // Rebalanceo a la izquierda
                            this.height = false;
                            break;
                    }
                }
            } else {
                fat.left = insert(x, (NodeAVL) node.left); // Inserta a la izquierda
                if (this.height) {
                    switch (fat.bf) {
                        case 1:
                            fat.bf = 0;
                            this.height = false;
                            break;
                        case 0:
                            fat.bf = -1;
                            this.height = true;
                            break;
                        case -1:
                            fat = balanceToRight(fat); // Rebalanceo a la derecha
                            this.height = false;
                            break;
                    }
                }
            }
        }

        return fat; // Retorna nodo actualizado
    }

    // Rotación simple a la izquierda (SL)
    private NodeAVL rotateSL(NodeAVL node) {
        NodeAVL p = (NodeAVL) node.right;
        node.right = p.left;
        p.left = node;
        return p;
    }

    // Rotación simple a la derecha (SR)
    private NodeAVL rotateSR(NodeAVL node) {
        NodeAVL p = (NodeAVL) node.left;
        node.left = p.right;
        p.right = node;
        return p;
    }

    // Balanceo cuando hay desbalance a la izquierda
    private NodeAVL balanceToLeft(NodeAVL node) {
        NodeAVL hijo = (NodeAVL) node.right;

        switch (hijo.bf) {
            case 1:
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSL(node); // Rotación simple izquierda
                break;

            case -1:
                NodeAVL nieto = (NodeAVL) hijo.left;
                switch (nieto.bf) {
                    case -1:
                        node.bf = 0;
                        hijo.bf = 1;
                        break;
                    case 0:
                        node.bf = 0;
                        hijo.bf = 0;
                        break;
                    case 1:
                        node.bf = -1;
                        hijo.bf = 0;
                        break;
                }
                nieto.bf = 0;
                node.right = rotateSR(hijo); // Rotación doble: derecha + izquierda
                node = rotateSL(node);
                break;
        }

        return node;
    }

    // Balanceo cuando hay desbalance a la derecha
    private NodeAVL balanceToRight(NodeAVL node) {
        NodeAVL hijo = (NodeAVL) node.left;

        switch (hijo.bf) {
            case -1:
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSR(node); // Rotación simple derecha
                break;

            case 1:
                NodeAVL nieto = (NodeAVL) hijo.right;
                switch (nieto.bf) {
                    case 1:
                        node.bf = 0;
                        hijo.bf = -1;
                        break;
                    case 0:
                        node.bf = 0;
                        hijo.bf = 0;
                        break;
                    case -1:
                        node.bf = 1;
                        hijo.bf = 0;
                        break;
                }
                nieto.bf = 0;
                node.left = rotateSL(hijo); // Rotación doble: izquierda + derecha
                node = rotateSR(node);
                break;
        }

        return node;
    }

    // Recorrido inOrder: izquierda - raíz - derecha
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node<E> node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }

    // Recorrido preOrder: raíz - izquierda - derecha
    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node<E> node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    // Recorrido por niveles (BFS)
    public void bfs() {
        int h = height(root);
        for (int i = 0; i <= h; i++) {
            printLevel(root, i); // Imprime cada nivel
        }
        System.out.println();
    }

    // Imprime nodos en un nivel específico
    private void printLevel(Node<E> node, int level) {
        if (node == null) return;
        if (level == 0) {
            System.out.print(node.data + " ");
        } else {
            printLevel(node.left, level - 1);
            printLevel(node.right, level - 1);
        }
    }

    // Calcula la altura del árbol
    private int height(Node<E> node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
