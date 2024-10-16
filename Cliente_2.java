// imports necesarios
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;


public class Cliente
{
    public static void main(String[] args) {
        //Fijar el directorio donde se encuentra el java.policy
        
        System.setProperty("java.security.policy", "./java.policy");//El segundo argumento es la ruta al java.policy
        // Crear administrador de seguridad
        if (System.getSecurityManager() == null) {
            // Crear administrador de seguridad
            System.setSecurityManager(new SecurityManager());
        }
        try
        {
           
           // String hostname = "155.210.154.195:32005"; //se puede usar "IP:puerto"
            
            Broker broker = (Broker) Naming.lookup("//" + "155.210.154.191:32001" + "/MyBrokerPC"); //nombre del objeto remoto (broker) que se busca
  	    Scanner lector = new Scanner(System.in);
            
                System.out.println("Selecciona uno de los siguientes servicios y escríbelo a continuación: ");
                
                ArrayList<String[]> servicios1 = broker.servicios_registrados("MyCollection");

                if (servicios1.isEmpty()) {

                    System.out.println("La lista de servicios está vacía.");
                }

                for(String[] servicio : servicios1) {
                    System.out.println(servicio[0]); //nombre del servicio
                }



                ArrayList<String[]> servicios2 = broker.servicios_registrados("MyCollection2");

                if (servicios2.isEmpty()) {

                    System.out.println("La lista de servicios está vacía.");
                }

                for(String[] servicio : servicios2) {
                    System.out.println(servicio[0]); //nombre del servicio
                }
                System.out.println("Seleccione la letra A para actualizar los servicios de la lista: ");

                    String entrada = lector.nextLine(); //metodo elegido por el usuario
                    // Separar el método y sus parámetros
                    String[] partes = entrada.split(" "); // Dividir la entrada en partes utilizando el espacio en blanco como delimitador
                    String metodo = partes[0]; // El primer elemento es el método

                    // Si hay más de una parte, entonces el segundo y siguientes elementos son los parámetros
                    Vector<String> parametros = new Vector<>();
                    for (int i = 1; i < partes.length; i++) {
                        parametros.add(partes[i]); // Agregar cada parámetro al vector
                    }
                    while (metodo.equals("A")){
                        servicios1 = broker.servicios_registrados("MyCollection");

                        if (servicios1.isEmpty()) {

                            System.out.println("La lista de servicios está vacía.");
                        }

                        for(String[] servicio : servicios1) {
                            System.out.println(servicio[0]); //nombre del servicio
                        }


                        servicios2 = broker.servicios_registrados("MyCollection2");

                        if (servicios2.isEmpty()) {

                            System.out.println("La lista de servicios está vacía.");
                        }

                        for(String[] servicio : servicios2) {
                            System.out.println(servicio[0]); //nombre del servicio
                        }
                        System.out.println("Seleccione la letra A para actualizar los servicios de la lista: ");

                        entrada = lector.nextLine(); //metodo elegido por el usuario
                        // Separar el método y sus parámetros
                        partes = entrada.split(" "); // Dividir la entrada en partes utilizando el espacio en blanco como delimitador
                        metodo = partes[0]; // El primer elemento es el método

                        // Si hay más de una parte, entonces el segundo y siguientes elementos son los parámetros
                        parametros = new Vector<>();
                        for (int i = 1; i < partes.length; i++) {
                            parametros.add(partes[i]); // Agregar cada parámetro al vector
                        }
                    }

		    boolean encontrado=false;

		    //El cliente pide al Broker la ejecuci´on de ese m´etodo y muestra el
                    //resultado por pantalla. Vuelve a mostrar la lista de servicios.
                   
                            String resultado = broker.ejecutar_servicio(metodo, parametros);
                            System.out.println("Resultado devuelto de ejecutar el servicio: " + resultado);
			       
                    if(!encontrado) {
                        System.out.println("El servicio solicitado no está en la lsta de servicios.");
                    }

            
          lector.close();  
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
