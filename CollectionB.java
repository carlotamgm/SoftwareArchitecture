import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CollectionB extends Remote
{
    //metodos de la interface
    int number_of_sagas() throws RemoteException;
    String name_of_collection() throws RemoteException;
   
}
