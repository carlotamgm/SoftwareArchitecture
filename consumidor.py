from broker import BrokerDeMensajes
import time, threading

# Uso del Singleton
broker = BrokerDeMensajes.getInstance()


def consumidor():
    global lock
    # Esperar un poco para que el consumidor pueda recibir mensajes
    time.sleep(5)

    # Consumidor
    broker.consumir( "mi_cola")


#def start_consumidor():
    # Crear el hilo y asignarle la función consumidor como objetivo
   # consumidor_thread = threading.Thread(target=consumidor)
    #consumidor_thread.start()

# Iniciar el thread de consumidor
#start_consumidor()


