package avltree;

// Excepción personalizada para manejar intentos de inserción duplicada en el árbol
public class ItemDuplicated extends Exception {

    // Constructor que recibe un mensaje de error y lo pasa a la superclase Exception
    public ItemDuplicated(String msg) {
        super(msg);
    }
}
