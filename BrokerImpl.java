import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;

import java.util.Iterator; // Replace the import statement for javax.swing.text.html.HTMLDocument.Iterator

import java.lang.reflect.Method; 
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Iterator;

public class BrokerImpl extends UnicastRemoteObject implements Broker {

    public BrokerImpl() throws RemoteException {
        
        super(); //Llama al constructor de UnicastRemoteObject
    }   

    ArrayList<String[]> servidores = new ArrayList<String[]>(); //nombre_servidor, host_remoto_IP_puerto
    ArrayList<String[]> serviciosDisponibles = new ArrayList<String[]>(); //nom_servicio, nombre_servidor

    //API para los servidores: dar de alta un servidor (añadir a lista de servidores disponibles)
    public void registrar_servidor(String nombre_servidor, String host_remoto_IP_puerto) throws RemoteException {
        servidores.add(new String[]{nombre_servidor, host_remoto_IP_puerto});

    }

    //API para los clientes: ejecutar el servicio nom_servicio con los parámetros parametros_servicio
    public String ejecutar_servicio(String nom_servicio, Vector<String> parametros_servicio) throws RemoteException {
        //Respuesta respuesta = new Respuesta("");
    
        Iterator<String[]> serviciosIterator = serviciosDisponibles.iterator();
        while (serviciosIterator.hasNext()) {
            String[] servicio = serviciosIterator.next();
    
            if (servicio[0].equals(nom_servicio)) {
                Iterator<String[]> servidoresIterator = servidores.iterator();
                while (servidoresIterator.hasNext()) {
                    String[] servidor = servidoresIterator.next();
    
                    if (servidor[0].equals(servicio[1])) {
                        try {
                            Collection server = (Collection) Naming.lookup("//" + servidor[1] + "/" + servicio[1]);
                            Collection serverB = (Collection) Naming.lookup("//" + servidor[2] + "/" + servicio[1]);
                            System.out.println("Ejecutandose: " + nom_servicio);
                            if (nom_servicio.equals("number_of_books")) {
                                int result = server.number_of_books();
                                return String.valueOf(result);
                            } else if (nom_servicio.equals("modify_name_of_collection")) {
                                server.name_of_collection(parametros_servicio.get(0));
                                return "name_of_collection ahora se llama: " + server.name_of_collection();
                            } else if (nom_servicio.equals("name_of_collection")) {
                                return server.name_of_collection();
                            } else if (nom_servicio.equals("name_of_collection2")) {
                                return serverB.name_of_collection();
                            }else if (nom_servicio.equals("modify_name_of_collection2")) {
                                serverB.name_of_collection(parametros_servicio.get(0));
                                return "name_of_collection2 ahora se llama: " + server.name_of_collection();
                            }else if (nom_servicio.equals("number_of_sagas")) {
                                int result = serverB.number_of_sagas();
                                return String.valueOf(result);
                            }
                            else {
                                return "Ningun servicio realizado.";
                            }
                            
                            
                            //Method method = server.getClass().getMethod(nom_servicio, Vector.class);
                            //Object result = method.invoke(server, parametros_servicio);
                            //respuesta.setMensaje(result.toString());
                            
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        }
        return "Ningun servicio realizado.";
    
        //return respuesta;
    }
    

    //Registrar servicio: dar de alta un servicio (añadir a lista de servicios disponibles)
    public void alta_servicio(String nombre_servidor, String nom_servicio, Vector<String> lista_param, String tipo_retorno) throws RemoteException {
        serviciosDisponibles.add(new String[]{nom_servicio, nombre_servidor});
        System.out.println("Servicio dado de alta: "+nom_servicio);
    }

    //Dar de baja servicio: eliminar de lista de servicios disponibles
    public synchronized void baja_servicio(String nombre_servidor, String nom_servicio) throws RemoteException {
        Iterator<String[]> iterator = serviciosDisponibles.iterator();
        while (iterator.hasNext()) {
            String[] servicio = iterator.next();
            if (servicio[0].equals(nom_servicio)) {
                iterator.remove(); // Eliminar el elemento de manera segura utilizando el iterador
            }
        }
    }

    public ArrayList<String[]> servicios_registrados(String nombre_servidor) throws RemoteException {
        ArrayList<String[]> servicios = new ArrayList<String[]>();
    
        Iterator<String[]> iterator = serviciosDisponibles.iterator();
        while (iterator.hasNext()) {
            String[] servicio = iterator.next();
            if (servicio[1].equals(nombre_servidor)) {
                servicios.add(servicio);
            }
        }
    
        return servicios;
    }
    
    public static void main(String args[]) {
        //Fijar el directorio donde se encuentra el java.policy
        System.setProperty("java.security.policy", "./java.policy");//El segundo argumento es la ruta al java.policy
        //Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());
        //nombre o IP del host donde reside el objeto servidor
        String hostName = "155.210.154.191:32001"; //se puede usar "IPhostremoto:puerto"
        try {
            BrokerImpl obj = new BrokerImpl();
            Naming.rebind("//" + hostName + "/MyBrokerPC", obj);

            //Naming.lookup("//" + hostName + "/MyCollection");
            System.out.println("Broker registrado!");
            
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    /*Extra: persistencia, servidor no necesita registrar servicios porque se guardan en un fichero
    * y el broker los coge cada vez que se inicia
    */
    
}
