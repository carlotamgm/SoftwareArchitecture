import java.util.ArrayList;

public abstract class Subject {

    public ArrayList<Observer> obsCafe; //lista de observadores para café
    public ArrayList<Observer> obsLeche; //lista de observadores para leche
    public ArrayList<Observer> obsAgua; //lista de observadores para agua
    public ArrayList<Observer> obsGalletas; //lista de observadores para galletas

    public Subject() {
        obsCafe = new ArrayList<Observer>(); 
        obsLeche = new ArrayList<Observer>(); 
        obsAgua = new ArrayList<Observer>();
        obsGalletas = new ArrayList<Observer>();
    }
    
    public void Attach(Observer obs, Cafeteria.Producto prod){
        
        switch(prod) {
            case CAFE:
                if(!obsCafe.contains(obs)){
                    obsCafe.add(obs);
                }
                break;

            case LECHE:
                if(!obsLeche.contains(obs)){
                    obsLeche.add(obs);
                }
                break;

            case AGUA:
                if(!obsAgua.contains(obs)){
                    obsAgua.add(obs);
                }
                break;

            case GALLETAS:
                if(!obsGalletas.contains(obs)){
                    obsGalletas.add(obs);
                }
                break;
        }
        
    }

    
    /*Elimina el observer obs de la lista observers*/
    public void Detach(Observer obs, Cafeteria.Producto prod){
        
        switch(prod) {
            case CAFE:
                obsCafe.remove(obs);
                break;

            case LECHE:
                obsLeche.remove(obs);
                break;

            case AGUA:
                obsAgua.remove(obs);
                break;

            case GALLETAS:
                obsGalletas.remove(obs);
                break;
        }
        
    }

    /*Notifica de la actualización del producto prod a todos los observers que están suscritos a él*/
    public void Notify(Cafeteria.Producto prod){
        
        switch (prod) {
            case CAFE:
                for (Observer obs : obsCafe) {
                    obs.Update(this, prod);
                }
                break;

            case LECHE:
                for (Observer obs : obsLeche) {
                    obs.Update(this, prod);
                }
                break;

            case AGUA:
                for (Observer obs : obsAgua) {
                    obs.Update(this, prod);
                }
                break;

            case GALLETAS:
                for (Observer obs : obsGalletas) {
                    obs.Update(this, prod);
                }
                break;
        } 
    }

}

