package avltree;

public class TestAVL {
    public static void main(String[] args) {
        AVLTree<Integer> avl = new AVLTree<>(); // Crea árbol AVL
        BSTree<Integer> bst = new BSTree<>();   // Crea árbol binario de búsqueda normal

        try {
            //  Comparación entre AVL y BST con los mismos datos
            int[] datos = {10, 20, 30, 40, 50, 60}; // Datos en orden creciente
            for (int x : datos) {
                avl.insert(x); // Inserta en AVL se balancea automáticamente
                bst.insert(x); // Inserta en BST sin balanceo
            }

            // Muestra recorrido inOrder del BST puede estar desbalanceado
            System.out.print("BST inOrder: ");
            bst.inOrder();

            // Muestra recorrido inOrder del AVL debe estar balanceado
            System.out.print("AVL inOrder: ");
            avl.inOrder();

            //  Inserción adicional para probar 8 casos de rotación en AVL
            avl = new AVLTree<>(); // Nuevo árbol AVL vacío
            int[] pruebas = {50, 40, 30, 25, 20, 60, 70, 80, 22, 35, 65, 75}; // Datos diseñados para provocar rotaciones
            for (int x : pruebas) avl.insert(x); // Inserta en AVL

            // Muestra recorrido inOrder del AVL resultante
            System.out.print("AVL Final inOrder: ");
            avl.inOrder();

            //  Muestra recorrido preOrder (útil para ver estructura del árbol)
            System.out.print("AVL PreOrder: ");
            avl.preOrder();

            // Muestra recorrido BFS por niveles (nivel por nivel)
            System.out.print("AVL BFS por niveles: ");
            avl.bfs();

        } catch (ItemDuplicated e) {
            // Captura e imprime mensaje si se intenta insertar duplicados
            System.out.println("Error: " + e.getMessage());
        }
    }
}
