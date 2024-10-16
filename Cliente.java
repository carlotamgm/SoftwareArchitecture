
public class Cliente extends Observer{
    
    int cafe, leche, agua, galletas;

    public Cliente(Cafeteria cafeteria) {
        this.sujeto = cafeteria;
    }
    
    @Override
    void Update(Subject sujeto, Cafeteria.Producto producto) {
        if (sujeto instanceof Cafeteria) {
            Cafeteria cafeteria = (Cafeteria) sujeto;

            switch (producto) {
                case CAFE:
                    cafe = cafeteria.getCafe(); //actualiza su cantidad de café a las unidades que hay en la cafetería
                    System.out.println("Soy cliente y actualizo el café a: " + cafe + " unidades");
                    break;
                case LECHE:
                    leche = cafeteria.getLeche(); //actualiza su cantidad de leche a las unidades que hay en la cafetería
                    System.out.println("Soy cliente y actualizo la leche a: " + leche + " unidades");
                    break;
                case AGUA:
                    agua = cafeteria.getAgua(); //actualiza su cantidad de agua a las unidades que hay en la cafetería
                    System.out.println("Soy cliente y actualizo el agua a: " + agua + " unidades");
                    break;
                case GALLETAS:
                    galletas = cafeteria.getGalletas(); //actualiza su cantidad de galletas a las unidades que hay en la cafetería
                    System.out.println("Soy cliente y actualizo las galletas a: " + galletas + " unidades");
                    break;
            }

        }
    }

}