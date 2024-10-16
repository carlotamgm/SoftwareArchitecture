import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
//otros import
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

public class Class extends JPanel {
	
	//Atributos
	private String nombre;
    private String atributos; 
    private String metodos; 
	private int contClase;
    private int x, y; // Posición de la clase en la pantalla
    private boolean estaSeleccionada; // Indica si la clase está seleccionada	
	private boolean ratonEncima; // Indica si el ratón está encima de la clase
	
	public Class(String name, int num, String atributos, String metodos, int x, int y) {
		this.nombre = name;
		this.contClase = num;
        this.atributos = atributos;
        this.metodos = metodos;
        this.x = x;
        this.y = y;
        this.estaSeleccionada = false;
	}
	
	public void draw(Graphics g){
		//Dibuja la clase
		Graphics2D g2 = (Graphics2D)g;
		
		// Dibujar el rectángulo de la clase
        g2.setColor(estaSeleccionada ? Color.CYAN : Color.WHITE);
        g2.fillRect(x, y, 100, 80);

        // Dibujar el borde del rectángulo
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, 100, 80);

		//Dibujar líneas de separación entre zonas de nombre, atributos y métodos
		g2.drawLine(x, y + 25, x + 100, y + 25);
		g2.drawLine(x, y + 45, x + 100, y + 45);

        // Dibujar el nombre de la clase
        g2.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 12);
        g2.setFont(font);
        g2.drawString(nombre, x + 10, y + 20);

        // Dibujar "atributos" y "métodos"
		g2.setColor(Color.BLACK);
        g2.drawString(atributos, x + 10, y + 40);

        g2.setColor(Color.BLACK);
        g2.drawString(metodos, x + 10, y + 60);
		
	}
	
	//Otros metodos	

	public void setNombre(String nombre){
		//Establece el nombre de la clase
		this.nombre = nombre;
	}
	public void setSeleccion(boolean seleccion){
		//Selecciona o deselecciona la clase
		estaSeleccionada = seleccion;
	}
	public void setX(int x){
		//Establece la coordenada x de la clase
		this.x = x;
	}
	public void setY(int y){
		//Establece la coordenada y de la clase
		this.y = y;
	}
	public void setRatonEncima(boolean estaEncima) {
        this.ratonEncima = estaEncima;
    }

	public String getNombre(){
		//Devuelve el nombre de la clase
		return nombre;
	}

	public int getContClase() {
		return contClase;
	}
	public boolean getSeleccion(){
		//Devuelve si la clase está seleccionada
		return estaSeleccionada;
	}
	public int getX(){
		//Devuelve la coordenada x de la clase
		return x;
	}
	public int getY(){
		//Devuelve la coordenada y de la clase
		return y;
	}
	public boolean getRatonEncima() {
		return ratonEncima;
	}
	public Rectangle getBounds(){
		//Devuelve el rectángulo que contiene la clase
		return new Rectangle(x, y, 100, 80);
	}
	
}
