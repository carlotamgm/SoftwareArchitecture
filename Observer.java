
public abstract class Observer {

    protected Subject sujeto;

    /* Actualiza el estado del observador con las unidades de producto (del estado del sujeto) */
    abstract void Update(Subject sujeto, Cafeteria.Producto producto); 

}
