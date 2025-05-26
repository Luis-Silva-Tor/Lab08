package avltree;

//clase BSTree que implementa un Árbol Binario de Búsqueda (Binary Search Tree).
 
public class BSTree<E extends Comparable<E>> {

    // Nodo raíz del árbol
    protected Node<E> root;

    
    // Constructor que inicializa el árbol vacío.
     
    public BSTree() {
        root = null;
    }

  
    // Método público para insertar un nuevo dato en el árbol.
     
    public void insert(E data) throws ItemDuplicated {
        root = insert(root, data);
    }

  
     // auxiliar recursivo que inserta un nuevo dato en el árbol.

    protected Node<E> insert(Node<E> node, E data) {
        if (node == null) return new Node<>(data);

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            // Insertar en el subárbol izquierdo
            node.left = insert(node.left, data);
        } else if (cmp > 0) {
            // Insertar en el subárbol derecho
            node.right = insert(node.right, data);
        }
        // Si cmp == 0, no se permite insertar elementos duplicados (no hace nada)
        return node;
    }

   // Realiza un recorrido inorden izquierda, raíz, derecha del árbol.

     
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    /**
     * auxiliar recursivo para el recorrido inorden.
    
     */
    private void inOrder(Node<E> node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }
}
