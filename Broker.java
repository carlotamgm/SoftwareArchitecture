
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

public interface Broker extends Remote {

    void registrar_servidor(String nombre_servidor, String host_remoto_IP_puerto) throws RemoteException;
    String  ejecutar_servicio(String nom_servicio, Vector<String> parametros_servicio) throws RemoteException;
    void alta_servicio(String nombre_servidor, String nom_servicio, Vector<String> lista_param, String tipo_retorno) throws RemoteException;
    void baja_servicio(String nombre_servidor, String nom_servicio) throws RemoteException;
    ArrayList<String[]> servicios_registrados(String nombre_servidor) throws RemoteException;

}
