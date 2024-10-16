import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Diagram 
		extends JPanel 
		implements MouseListener, 
			   MouseMotionListener, 
			   KeyListener {
	
	//atributos
	private Window window;//Ventana en la que está el diagrama
	public Class claseSeleccionada;
	
	private Vector<Class> classes = new Vector<>(); //las clases que crea el usuario
	private Vector<Association> associations = new Vector<>(); // las asociaciones entre clases que crea el usuario
	
	// ... (otros posibles atributos)
	private Point posicionRaton; 
	private boolean creandoAsociacion;
	private boolean ratonFuera;
	private Class clase1Asociacion;
	private Class claseClickada;
	private int cont = -1;

	//metodos
	public Diagram(Window theWindow) {
		window = theWindow;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		setBorder(BorderFactory.createLineBorder(Color.black));

		addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                posicionRaton = e.getPoint();
                repaint();
            }
        });
		creandoAsociacion = false;
		clase1Asociacion = null;
		claseClickada = null;

	}
	
	//Añade una clase al diagrama
	public void addClass(String nombre, int contador, String atributos, String metodos, int x, int y) {
		Class newClass = new Class(nombre, contador, atributos, metodos, x, y);
        classes.add(newClass);
		window.updateNClasses(this);
        repaint(); 

	}

	//Devuelve el número de la nueva clase a añadir
	public int getCont() {
		return cont;
	}

	//Añade 1 al contador de clases
	public void addCont() {
		cont += 1;
	}
	
	//Devuelve el número de clases
	public int getNClasses(){
		return classes.size();
	}
	
	//Devuelve el numero de asociaciones
	public int getNAssociations(){
		return associations.size();
	}

	public boolean getCreandoAsociacion() {
		return creandoAsociacion;
	}

	public void paint(Graphics g) {
		//Dibuja el diagrama de clases
		super.paint(g);
        for (Class c : classes) {
            c.draw(g);
        }
		//Dibuja la asociación entre clases
        for (Association a : associations) {
            a.draw(g);
        }

        // Dibujar la asociación en curso (si la hay)
        if (creandoAsociacion && clase1Asociacion != null && !ratonFuera) {
			int x = getMousePosition().x;
			int y = getMousePosition().y;
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);
            g2d.drawLine(clase1Asociacion.getX(), clase1Asociacion.getY(), x, y);
        }

	}

	// Método para eliminar las asociaciones relacionadas con una clase
	private void borrarAsociaciones(Class c) {
		// Eliminar asociaciones que involucren a la clase
		for (int i = associations.size() - 1; i >= 0; i--) {
			Association a = associations.get(i);
			if (a.getClase1() == c || a.getClase2() == c) {
				associations.remove(a);
				window.updateNAssociations(this);
			} 
		}
		repaint();
		
	}
	
	/********************************************/
	/** Métodos de MouseListener               **/
	/********************************************/

	// Método que nos permite saber qué clase ha sido seleccionada
	// a partir de un clic del ratón
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			for (int i = 0; i < classes.size(); i++) {
				Class c = classes.get(i);
				if (c.getBounds().contains(e.getPoint())) {
					claseClickada = c;
				}
			}
		}
   	}
    
	// Método para crear una asociación entre clases cuando el usuario suelta el botón izquiero del ratón
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e) && claseSeleccionada != null) { // Se arrastra el ratón
			
			if (creandoAsociacion) { 
				for (Class c : classes) {
					if (c.getBounds().contains(e.getPoint())) {
						Association a;
						if (clase1Asociacion == c) {
							// Crear una asociación hacia la misma clase
							a = new Association(clase1Asociacion, clase1Asociacion);
							//al crearse la asociación, se deseleccionan las clases
							clase1Asociacion.setSeleccion(false);
							
						} else {
							// Crear una asociación normal
							a = new Association(clase1Asociacion, c);
							// Al crearse la asociación, se deseleccionan las clases
							clase1Asociacion.setSeleccion(false);
							c.setSeleccion(false);

						}
						associations.add(a);
						clase1Asociacion = null;
						claseSeleccionada = null;
						window.updateNAssociations(this);
						break;
					}
				}
				creandoAsociacion = false;
				clase1Asociacion = null; 
			} 
		} 
		
	}


	public void mouseEntered(MouseEvent e) { 

		ratonFuera = false;

	}

	/*Restablecer el color de la clase cuando el ratón sale del límite de pantalla*/
	public void mouseExited(MouseEvent e) {
		
		ratonFuera = true;

	}

	/* Realizar acciones al hacer clic en una clase */
	public void mouseClicked(MouseEvent e) {

		if (SwingUtilities.isRightMouseButton(e)) {
			for (int i = 0; i < classes.size(); i++) {
				Class c = classes.get(i);
				if (c.getBounds().contains(e.getPoint())) {
					// Eliminar la clase y sus asociaciones
					classes.remove(i);
					window.updateNClasses(this);
					borrarAsociaciones(c);
					repaint();
					break;  // Si solo quieres eliminar la primera clase que encuentres
				}
			}
		} 
		
	}

	/********************************************/
	/** Métodos de MouseMotionListener         **/
	/********************************************/    
	/* Actualizar la visualización mientras el ratón se mueve */
    public void mouseMoved(MouseEvent e) {

		for (int i = classes.size() - 1; i >= 0; i--) {
			
			Class c = classes.get(i);
			
			if (c.getBounds().contains(e.getPoint())) {
				if (i < classes.size() - 1) {
					// Mover la clase al frente (eliminar y agregar al final del vector)
					classes.remove(i);
					classes.add(c);
					repaint();
				}
				c.setRatonEncima(true);

				
			} else {
				c.setRatonEncima(false);

			}
		}
		
		repaint();
	}
    
	/* Arrastre de ratón, donde se inicia el proceso de creación de la asociación,
	 * en el cual se ha seleccionado la primera clase que participa en ella.
	 * Aquí también se mueve una clase si no está seleccionada. Ambas acciones
	 * se realizan presionando el botón izquierdo del ratón 
	*/
	public void mouseDragged(MouseEvent e) {
		
		if (SwingUtilities.isLeftMouseButton(e) ) {

			if(claseClickada.getSeleccion()) { /* Clase clickada está seleccionada, se crea asociación */
				
				for (int i = 0; i < classes.size(); i++) {
					Class c = classes.get(i);

					// Clase candidata para la asociación se pone en cyan al pasar por encima
					if (c.getBounds().contains(e.getPoint()) && !c.getSeleccion()) {   

						c.setSeleccion(true); 
						repaint();

					} else if (!c.getBounds().contains(e.getPoint()) && c!=claseClickada) { //clase no clicada y ratón en otro sitio
						c.setSeleccion(false); // Pasa a color blanco
					}
				}
					
				creandoAsociacion = true;
				clase1Asociacion = claseClickada;	
				repaint();


			} else { /* Clase clickada no está seleccionada, se mueve */

				claseClickada.setX(e.getX());
				claseClickada.setY(e.getY());
				claseClickada.setBackground(Color.WHITE);
				repaint();
			}

		} 
		
	}
    
	/********************************************/
	/** Métodos de KeyListener                 **/
	/********************************************/

	/* Cambiar el nombre de la clase seleccionada con las teclas, permite borrar y escribir */
	public void keyTyped(KeyEvent e) {

		char typedChar = e.getKeyChar();

		// Verificar si hay una clase seleccionada
		if (claseSeleccionada != null) {
			// Solo procesar letras y números
			if (Character.isLetterOrDigit(typedChar) && typedChar != 'S') {
				// Agregar el carácter al nombre de la clase
				claseSeleccionada.setNombre(claseSeleccionada.getNombre() + typedChar);
			} else if (typedChar == '\b' && claseSeleccionada.getNombre().length() > 0) {
				// Borrar el último carácter del nombre de la clase con la tecla retroceso ('\b')
				claseSeleccionada.setNombre(claseSeleccionada.getNombre().substring(0, claseSeleccionada.getNombre().length() - 1));

			}
			repaint();
		}
    }


	public void keyPressed(KeyEvent e) {

        // Para manejar la tecla 'S' y la selección de clases, se ha utilizado el método keyReleased
		
    }

	/* Seleccionar o deseleccionar una clase si se libera la tecla pulsación de la tecla S */
    public void keyReleased(KeyEvent e) {
		
        // Verificar si la tecla liberada es la tecla 'S'
        if (e.getKeyChar() == 'S' && !ratonFuera) {
            // Seleccionar o deseleccionar (si ya está seleccionada) la clase más arriba (en z) que contenga el punto actual del ratón
            for (int i = classes.size() - 1; i >= 0; i--) {
                Class c = classes.get(i);

                if (c.getBounds().contains(posicionRaton)) { //quiero manejar esta clase

                    if (c.getSeleccion()) { //c está ya seleccionada
                        c.setSeleccion(false);
                        claseSeleccionada = null;
                    } else { //c no está seleccionada
						/*si hay clase ya seleccionada, deseleccionarla */
						if(claseSeleccionada != null){
							claseSeleccionada.setSeleccion(false);
						}
						//seleccionar clase c
                        c.setSeleccion(true);
                        claseSeleccionada = c;

                    }
					break;

                } else { //no hay clase en ratón
					if(claseSeleccionada != null){ //si ya hay clase seleccionada, la deselecciono
						claseSeleccionada.setSeleccion(false);
						claseSeleccionada = null;
					}
				}
            }
			repaint();
			
        }
		
    }

}
