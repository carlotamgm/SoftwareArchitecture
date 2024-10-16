

public class Cafeteria extends Subject{

    private int cantCafe; //cantidad de café en la cafetería (sujeto)
    
    public int getCafe(){
        return this.cantCafe;
    }

    /*Actualiza el estado de la cafeteria, en concreto las unidades de café*/
    public void setCafe(int cafe){
        if(this.cantCafe >= cafe){
            this.cantCafe -= cafe;
            Notify(Producto.CAFE);

        }
    }

    private int cantLeche; //cantidad de leche en la cafetería (sujeto)

    public int getLeche(){
        return this.cantLeche;
    }

    /*Actualiza el estado de la cafeteria, en concreto las unidades de leche*/
    public void setLeche(int leche){
        if(this.cantLeche >= leche){
            this.cantLeche -= leche;
            Notify(Producto.LECHE);

        }
    }

    private int cantAgua; //cantidad de agua en la cafetería (sujeto)

    public int getAgua(){
        return this.cantAgua;
    }
    
    /*Actualiza el estado de la cafeteria, en concreto las unidades de agua*/
    public void setAgua(int agua){
        if(this.cantAgua >= agua){
            this.cantAgua -= agua;
            Notify(Producto.AGUA);
        }
    }

    private int cantGalletas; //cantidad de galletas en la cafetería (sujeto)

    public int getGalletas(){
        return this.cantGalletas;
    }

    /*Actualiza el estado de la cafeteria, en concreto las unidades de galletas*/
    public void setGalletas(int galletas){
        if(this.cantGalletas >= galletas){
            this.cantGalletas -= galletas;
            Notify(Producto.GALLETAS);
        }
    }
    
    public Cafeteria(int cantCafe, int cantLeche, int cantAgua, int cantGalletas){
        

        this.cantCafe = cantCafe;
        this.cantLeche = cantLeche;
        this.cantAgua = cantAgua;
        this.cantGalletas = cantGalletas;
    }

    

    enum Producto {
        CAFE,
        LECHE,
        AGUA,
        GALLETAS;
    }

}