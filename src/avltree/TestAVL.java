package avltree;

public class TestAVL {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();

        try {
            // RSR #1
            tree.insert(50);
            tree.insert(40);
            tree.insert(30); // Rotación simple derecha en 50

            // RSR #2
            tree.insert(25);
            tree.insert(20); // Rotación simple derecha en 30

            // RSL #1
            tree.insert(60);
            tree.insert(70); // Rotación simple izquierda en 60

            // RSL #2
            tree.insert(80); // Rotación simple izquierda en 70

            // RDL #1
            tree.insert(22); // Rotación doble izquierda-derecha en 25

            // RDL #2
            tree.insert(35); // Rotación doble izquierda-derecha en 40

            // RDR #1
            tree.insert(65); // Rotación doble derecha-izquierda en 60

            // RDR #2
            tree.insert(75); // Rotación doble derecha-izquierda en 70

        } catch (ItemDuplicated e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Recorrido inOrder:");
        tree.inOrder(); // Debería imprimir los valores ordenados
    }
}
