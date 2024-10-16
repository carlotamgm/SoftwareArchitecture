public class Encargado extends Observer{
    
    int cafe, leche, agua, galletas;
    
    public Encargado(Cafeteria cafeteria) {
        this.sujeto = cafeteria;
    }

    @Override
    void Update(Subject sujeto, Cafeteria.Producto producto) {
        if (sujeto instanceof Cafeteria) {
            Cafeteria cafeteria = (Cafeteria) sujeto;

            switch (producto) {
                case CAFE:
                    cafe = cafeteria.getCafe();
                    System.out.println("Soy encargado y actualizo el café a: " + cafe);
                    break;
                case LECHE:
                    leche = cafeteria.getLeche();
                    System.out.println("Soy encargado y actualizo la leche a: " + leche);
                    break;
                case AGUA:
                    agua = cafeteria.getAgua();
                    System.out.println("Soy encargado y actualizo el agua a: " + agua);
                    break;
                case GALLETAS:
                    galletas = cafeteria.getGalletas();
                    System.out.println("Soy encargado y actualizo las galletas a: " + galletas);
                    break;
            }

        }
    }

}