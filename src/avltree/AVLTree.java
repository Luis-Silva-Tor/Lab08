package avltree;

// Clase AVLTree que extiende de BSTree 
public class AVLTree<E extends Comparable<E>> extends BSTree<E> {

    // Se redefine la raíz como un nodo AVL 
    protected NodeAVL root;

    // Variable auxiliar que indica si la altura del árbol cambió
    private boolean height;

    // Clase interna que representa un nodo AVL, hereda de Node
    class NodeAVL extends Node<E> {
        protected int bf; // Factor de equilibrio (balance factor)

        public NodeAVL(E data) {
            super(data);
            this.bf = 0; // Al insertar un nodo, su balance inicial es 0
        }

        public String toString() {
            return super.toString() + " [bf=" + bf + "]";
        }
    }

    // Método público de inserción: inicia la variable height y llama a la inserción privada
    public void insert(E x) throws ItemDuplicated {
        this.height = false;
        this.root = insert(x, root);
    }

    // Método privado de inserción balanceada
    protected NodeAVL insert(E x, NodeAVL node) throws ItemDuplicated {
        NodeAVL fat = node;

        if (node == null) {
            // Si el nodo es nulo, se inserta y se indica que la altura cambió
            this.height = true;
            fat = new NodeAVL(x);
        } else {
            int resC = x.compareTo(node.data);
            if (resC == 0) {
                // Si el elemento ya existe, se lanza una excepción
                throw new ItemDuplicated(x + " ya está en el árbol");
            } else if (resC > 0) {
                // Inserción en el subárbol derecho
                fat.right = insert(x, (NodeAVL) node.right);
                if (this.height) {
                    // Verifica el factor de equilibrio y aplica balanceo si es necesario
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
                            fat = balanceToLeft(fat);
                            this.height = false;
                            break;
                    }
                }
            } else {
                // Inserción en el subárbol izquierdo
                fat.left = insert(x, (NodeAVL) node.left);
                if (this.height) {
                    // Verifica el factor de equilibrio y aplica balanceo si es necesario
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
                            fat = balanceToRight(fat);
                            this.height = false;
                            break;
                    }
                }
            }
        }

        return fat;
    }

    // Rotación simple a la izquierda RSL
    private NodeAVL rotateSL(NodeAVL node) {
        NodeAVL p = (NodeAVL) node.right;
        node.right = p.left;
        p.left = node;
        return p;
    }

    // Rotación simple a la derecha RSR
    private NodeAVL rotateSR(NodeAVL node) {
        NodeAVL p = (NodeAVL) node.left;
        node.left = p.right;
        p.right = node;
        return p;
    }

    // Balanceo hacia la izquierda: se ejecuta cuando bf 
    private NodeAVL balanceToLeft(NodeAVL node) {
        NodeAVL hijo = (NodeAVL) node.right;

        switch (hijo.bf) {
            case 1: // Caso rotación simple izquierda
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSL(node);
                break;

            case -1: // Caso rotación doble derecha-izquierda
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
                node.right = rotateSR(hijo);
                node = rotateSL(node);
                break;
        }

        return node;
    }

    // Balanceo hacia la derecha: se ejecuta cuando bf = -2 creció subárbol izquierdo
    private NodeAVL balanceToRight(NodeAVL node) {
        NodeAVL hijo = (NodeAVL) node.left;

        switch (hijo.bf) {
            case -1: // Caso rotación simple derecha
                node.bf = 0;
                hijo.bf = 0;
                node = rotateSR(node);
                break;

            case 1: // Caso rotación doble izquierda-derecha
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
                node.left = rotateSL(hijo);
                node = rotateSR(node);
                break;
        }

        return node;
    }

    // Recorrido inOrder: imprime los elementos de menor a mayor
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
}
