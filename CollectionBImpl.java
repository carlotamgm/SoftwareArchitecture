// imports necesarios
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;
import java.rmi.Naming;
import java.util.Iterator;

public class CollectionBImpl extends UnicastRemoteObject implements CollectionB {
    //Private member variables
    private int m_number_of_sagas;
    private String m_name_of_collection;

    //Constructor
    public CollectionBImpl() throws RemoteException {
        super(); //Llama al constructor de UnicastRemoteObject
        // inicializar las variables privadas aquí
        m_name_of_collection = "ColeccionB";
        m_number_of_sagas = 5;
    }
    
    //Implementar todos los metodos de la interface remota

    public int number_of_sagas() throws RemoteException {
        return m_number_of_sagas;
    }

    public String name_of_collection() throws RemoteException {
        return m_name_of_collection;
    }
    public void name_of_collection(String newName) throws RemoteException {
        m_name_of_collection = newName;
    }

    public void registrarNuevoServicio(String nombreServicio, Vector<String> parametros, String tipoRetorno) throws RemoteException {
        Broker broker;
        try {
            broker = (Broker) Naming.lookup("//" + "155.210.154.191:32001" + "/MyBrokerPC");
            broker.alta_servicio("MyCollection2", nombreServicio, parametros, tipoRetorno);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void darDeBajaServicio(String nombreServicio) throws RemoteException {
        Broker broker;
        try {
            broker = (Broker) Naming.lookup("//" + "155.210.154.191:32001" + "/MyBrokerPC");
            broker.baja_servicio("MyCollection2", nombreServicio);
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
        String hostName = "155.210.154.197:32007"; //se puede usar "IPhostremoto:puerto"
        //Por defecto RMI usa el puerto 1099
        try {
            // Crear objeto remoto
            CollectionBImpl obj = new CollectionBImpl();
            System.out.println("Creado!");
            //Registrar el objeto remoto
            Naming.rebind("//" + hostName + "/MyCollection2", obj);
            System.out.println("Estoy registrado!");

            Broker broker = (Broker) Naming.lookup("//" + "155.210.154.191:32001" + "/MyBrokerPC");
	    broker.registrar_servidor("MyCollection2", hostName);
            
//Los servidores registran sus servicios en el Broker
            
            broker.alta_servicio("MyCollection2", "name_of_collection2", null, "String");
            broker.alta_servicio("MyCollection2", "number_of_sagas", null, "int");
            Vector<String> parametros = new Vector<String>();
	

            //Uno de los servidores registra un nuevo servicio y da de baja otro. El broker no se recompila.
            broker.alta_servicio("MyCollection2", "modify_name_of_collection2", null, "int");
            ArrayList<String[]> servicios = broker.servicios_registrados("MyCollection");
	    
            /*Iterator<String[]> iterator = servicios.iterator();
            if (iterator.hasNext()) {
                String[] servicio = iterator.next();
                broker.baja_servicio("MyCollection", servicio[0]);
            }*/
            //broker.baja_servicio("MyCollection2", "name_of_collection2");

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
