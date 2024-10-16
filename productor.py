from broker import BrokerDeMensajes

def callback(message):
    print("Mensaje recibido:", message)

# Uso del Singleton
broker = BrokerDeMensajes.getInstance()

# Productor
broker.declarar_cola("mi_cola")
broker.publicar("mi_cola", "Hola desde el productor!", callback)
broker.mostrar_mensajes_en_cola("mi_cola")


