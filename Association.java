import java.awt.Graphics;
import java.awt.Graphics2D;
//otros imports …
import java.awt.Point;


public class Association {

	// Atributos
	private Class clase1;
    private Class clase2;
    private Diagram diagram;

	
	// Constructores
	public Association(Class clase1, Class clase2) {
        this.clase1 = clase1;
        this.clase2 = clase2;
    }
	
	public void draw(Graphics graphics) {
		// Dibuja la asociación
		Graphics2D graphics2d = (Graphics2D)graphics;

		Point point1 = calculateConnectionPoint(clase1, clase2);
        Point point2 = calculateConnectionPoint(clase2, clase1);

        if (clase1 == clase2) {
            point1.translate(clase1.getBounds().width, 0);
            point2.translate(clase2.getBounds().width, 0);

            graphics2d.drawRect(clase1.getX() - 5, clase1.getY() - 5,
            clase1.getBounds().width + 10, clase1.getBounds().height + 10); // Dibujar rectángulo de auto-asociación

        } else {
           if(diagram.getCreandoAsociacion()) {
                graphics2d.drawLine(point1.x, point1.y, point2.x, point2.y); // Dibujar línea de asociación 
            }
        }

	}

	// Más métodos
	
	// Método para calcular el punto de conexión entre la asociación y la clase
    private Point calculateConnectionPoint(Class c1, Class c2) {
        int x1 = c1.getX() + c1.getBounds().width / 2;
        int y1 = c1.getY() + c1.getBounds().height / 2;

        int x2 = c2.getX() + c2.getBounds().width / 2;
        int y2 = c2.getY() + c2.getBounds().height / 2;

        int x,y;
        // Verificar si estamos conectando a la misma clase
        if (c1 == c2) {
            double angle = Math.toRadians(45);  // Ángulo arbitrario para la conexión consigo misma
            x = (int) (x1 + (c1.getBounds().width / 2) * Math.cos(angle));
            y = (int) (y1 + (c1.getBounds().height / 2) * Math.sin(angle));
    
        }
        else{
            // Calcular el punto de intersección con el borde de la clase1
            int deltaX = x2 - x1;
            int deltaY = y2 - y1;
            double angle = Math.atan2(deltaY, deltaX);
            x = (int) (x1 + (c1.getBounds().width / 2) * Math.cos(angle));
            y = (int) (y1 + (c1.getBounds().height / 2) * Math.sin(angle));
        }

        return new Point(x, y);
    }

    // Métodos para obtener las clases asociadas
    public Class getClase1() {
        return clase1;
    }

    public Class getClase2() {
        return clase2;
    }

}
