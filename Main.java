public class Main {
    public static void main(String[] args) {
        
        // Crear una instancia de Cafeteria
        Cafeteria cafeteria = new Cafeteria(20,20,20,20);

        // Crear un encargado y agregarlo como observador para café y galletas
        Encargado encargado = new Encargado(cafeteria);
        cafeteria.Attach(encargado, Cafeteria.Producto.CAFE);
        cafeteria.Attach(encargado, Cafeteria.Producto.GALLETAS);
        cafeteria.Attach(encargado, Cafeteria.Producto.AGUA);

        // Crear un cliente interesado en leche
        Cliente cliente = new Cliente(cafeteria);
        cafeteria.Attach(cliente, Cafeteria.Producto.LECHE);
        cafeteria.Attach(cliente, Cafeteria.Producto.AGUA);
        
        // Realizar algunas actualizaciones en los productos de la cafetería
        cafeteria.setCafe(10);
        cafeteria.setLeche(5);
        cafeteria.setAgua(3);
        cafeteria.setGalletas(15);
        cafeteria.Detach(cliente, Cafeteria.Producto.AGUA);
        //cafeteria.Detach(encargado, Cafeteria.Producto.AGUA);
        cafeteria.setAgua(10);

    }
}
