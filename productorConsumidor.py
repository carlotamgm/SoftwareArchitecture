from broker import BrokerDeMensajes
import threading
import time

# Uso del Singleton
broker = BrokerDeMensajes.getInstance()

# Declarar la cola antes de que el consumidor intente consumir mensajes
broker.declarar_cola("mi_cola")

# Bloqueo para sincronización
lock = threading.Lock()

publicar_evento = threading.Event()

def callback(message):
    print("Mensaje recibido por consumidor1:", message)

def callback2(message):
    print("Mensaje recibido por consumidor2:", message)

def callback3(message):
    print("Mensaje recibido por consumidor3:", message)

def consumidor1():
    global lock
    publicar_evento.wait()

    # Consumidor
    with lock:
        # Registrar el consumidor en la cola
        broker.registrar("mi_cola", callback)
        broker.consumir("mi_cola")

def consumidor2():
    global lock
    publicar_evento.wait()

    # Consumidor
    with lock:
        # Registrar el consumidor en la cola
        broker.registrar("mi_cola", callback2)
        broker.consumir("mi_cola")

def consumidor3():
    global lock
    publicar_evento.wait()

    # Consumidor
    with lock:
        # Registrar el consumidor en la cola
        broker.registrar("mi_cola", callback3)
        broker.consumir("mi_cola")

def productor():
    global lock

    # Productor
    with lock:
        broker.publicar("mi_cola", "Hola desde el productor!") 
        broker.publicar("mi_cola", "Hola desde el productor2!")
        broker.publicar("mi_cola", "Hola desde el productor3!")
        broker.publicar("mi_coa", "Hola desde el productorX!") # Esta cola no existe

        publicar_evento.set()
        #broker.consumir("mi_cola")

# Iniciar el thread de consumidor
consumidor_thread = threading.Thread(target=consumidor1)
consumidor_thread.start()

# Iniciar el thread de consumidor
consumidor2_thread = threading.Thread(target=consumidor2)
consumidor2_thread.start()

# Iniciar el thread de consumidor
consumidor3_thread = threading.Thread(target=consumidor3)
consumidor3_thread.start()

# Iniciar el thread de productor
productor_thread = threading.Thread(target=productor)
productor_thread.start()

# Esperar a que ambos hilos terminen antes de salir
consumidor_thread.join()
consumidor2_thread.join()
consumidor3_thread.join()
productor_thread.join()
