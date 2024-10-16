// imports necesarios
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;
import java.rmi.Naming;
import java.util.Iterator;

public class CollectionImpl extends UnicastRemoteObject implements Collection {
    //Private member variables
    private int m_number_of_books;
    private String m_name_of_collection;

    //Constructor
    public CollectionImpl() throws RemoteException {
        super(); //Llama al constructor de UnicastRemoteObject
        // inicializar las variables privadas aquí
        m_name_of_collection = "ColeccionA";
        m_number_of_books = 10;
    }
    
    //Implementar todos los metodos de la interface remota

    public int number_of_books() throws RemoteException {
        return m_number_of_books;
    }
    public int number_of_sagas() throws RemoteException {
        return 0;
    }

    public String name_of_collection() throws RemoteException {
        return m_name_of_collection;
    }

    public void name_of_collection(String _new_value) throws RemoteException {
        m_name_of_collection = _new_value;
    }

   public void registrarNuevoServicio(String nombreServicio, Vector<String> parametros, String tipoRetorno) throws RemoteException {
        Broker broker;
        try {
            broker = (Broker) Naming.lookup("//" + "155.210.154.191:32001" + "/MyBrokerPC");
            broker.alta_servicio("MyCollection", nombreServicio, parametros, tipoRetorno);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void darDeBajaServicio(String nombreServicio) throws RemoteException {
        Broker broker;
        try {
            broker = (Broker) Naming.lookup("//" + "155.210.154.191:32001" + "/MyBrokerPC");
            broker.baja_servicio("MyCollection", nombreServicio);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String args[]) {
        //Fijar el directorio donde se encuentra el java.policy
        System.setProperty("java.security.policy", "./java.policy");//El segundo argumento es la ruta al java.policy
        //Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());
        //nombre o IP del host donde reside el objeto servidor
        String hostName = "155.210.154.198:32008"; //se puede usar "IPhostremoto:puerto"
        //Por defecto RMI usa el puerto 1099
        try {
            // Crear objeto remoto
            CollectionImpl obj = new CollectionImpl();
            System.out.println("Creado!");
            //Registrar el objeto remoto
            Naming.rebind("//" + hostName + "/MyCollection", obj);
            System.out.println("Estoy registrado!");

            Broker broker = (Broker) Naming.lookup("//" + "155.210.154.191:32001" + "/MyBrokerPC");
	    broker.registrar_servidor("MyCollection", hostName);
            
//Los servidores registran sus servicios en el Broker
            //Vector<String> parametros =  {"Coleccion2"};
	    //broker.alta_servicio("MyCollection", "name_of_collection", parametros, "void");
            broker.alta_servicio("MyCollection", "name_of_collection", null, "String");
            broker.alta_servicio("MyCollection", "number_of_books", null, "int");
            Vector<String> parametros = new Vector<String>();
	

            //Uno de los servidores registra un nuevo servicio y da de baja otro. El broker no se recompila.
            broker.alta_servicio("MyCollection", "modify_name_of_collection", null, "int");
            ArrayList<String[]> servicios = broker.servicios_registrados("MyCollection");
	    
            /*Iterator<String[]> iterator = servicios.iterator();
            if (iterator.hasNext()) {
                String[] servicio = iterator.next();
                broker.baja_servicio("MyCollection", servicio[0]);
            }*/
            broker.baja_servicio("MyCollection", "name_of_collection");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
