import threading
import time
import collections
class BrokerDeMensajes:
    _instance = None

    @staticmethod
    def getInstance():
        if BrokerDeMensajes._instance is None:
            BrokerDeMensajes._instance = BrokerDeMensajes()
        return BrokerDeMensajes._instance

    def __init__(this):
        this.colas = {}
        this.mensajes = collections.defaultdict(list)
        this.consumidores = collections.defaultdict(list)
        this.consumidoresRegistrados = {}
        this.ultimo_consumidor = {} # Para llevar un registro de los consumidores que han consumido mensajes
        this.lock = threading.Lock()

    def declarar_cola(this, queue_name):
        with this.lock:
            if queue_name not in this.colas: 
                # Si la cola no está en this.colas, se agrega con una lista vacía cuyo índice es el nombre de la cola
                this.colas[queue_name] = []
                this.mensajes[queue_name] = []
                print("cola declarada")
    
    def registrar(this, queue_name, callback):
        with this.lock:
            if queue_name not in this.consumidores:
                this.consumidores[queue_name] = []
            this.consumidores[queue_name].append(callback)
            print(f"Consumidor registrado en la cola '{queue_name}'")
            if queue_name not in this.ultimo_consumidor:
                this.ultimo_consumidor[queue_name] = -1 # aún no se ha enviado ningún mensaje a ningún consumidor para esa cola

    def publicar(this, queue_name, message):
        with this.lock:
            if queue_name in this.colas:
                print("se va a publicar en la cola: ", queue_name)
                this.mensajes[queue_name].append({'message': message, 'timestamp': time.time()})
            else:
                print(f"La cola '{queue_name}' no existe. El mensaje se perderá.")
            
    def consumir(this, queue_name):
        with this.lock:
            print("consumir")
            if queue_name in this.mensajes and len(this.mensajes[queue_name]) > 0:
                print("hay mensajes")
                message_info = this.mensajes[queue_name][-1]
                message_timestamp = message_info['timestamp']
                current_timestamp = time.time()
                time_elapsed = current_timestamp - message_timestamp
                print("tiempo")
                if time_elapsed <= 300:  # Si han pasado menos de 5 minutos, es decir, 300 segundos
                    print("ahora callback")
                    if queue_name in this.consumidores:  # Verificar si aún hay un consumidor registrado
                        this.ultimo_consumidor[queue_name] = (this.ultimo_consumidor[queue_name] + 1) #Esta línea implementa la política de round robin. Incrementa el índice del último consumidor que recibió un mensaje y usa el operador de módulo para asegurarse de que el índice no exceda el número de consumidores. Si el índice alcanza el número de consumidores, se reinicia a 0, lo que permite que los mensajes se distribuyan de manera equitativa entre los consumidores.
                        #if this.ultimo_consumidor[queue_name] > len(this.consumidores[queue_name]):
                        #    this.ultimo_consumidor[queue_name] = 0
                        callback = this.consumidores[queue_name][this.ultimo_consumidor[queue_name]] # Se obtiene la función de devolución de llamada (callback) del próximo consumidor en la lista.
                        message_info = this.mensajes[queue_name].pop(0) # Se extrae el primer mensaje de la cola.
                        callback(message_info['message']) # simula la entrega del mensaje al consumidor.
                        print("callback hecho")
                    else:
                        print(f"No hay consumidores registrados para la cola '{queue_name}'. Ignorando el mensaje.")
                else:
                    del this.mensajes[queue_name]
                    print(f"Mensaje eliminado de la cola '{queue_name}' después de 5 minutos.")

    def mostrar_mensajes_en_cola(this, queue_name):
        if queue_name in this.mensajes:
            print(f"Mensajes en la cola '{queue_name}':")
            for message_info in this.mensajes[queue_name]:
                print(message_info['message'])
        else:
            print(f"No hay mensajes en la cola '{queue_name}'.")