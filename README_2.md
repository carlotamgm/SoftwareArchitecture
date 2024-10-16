#843710, Paula Soriano Sánchez
#839841, Carlota Moncasi Gosá

En primer lugar, se abren tres máquinas cuyas ips están definidas en el código de Broker, Cliente y Servidor respectivamente.
-Se compila el broker con "javac Broker.java" y "javac BrokerImpl.java", después se registra el broker en el puerto correspondiente al hostname definido en el código con el comando "rmiregistry puerto&" y por último se ejecuta el broker con "java BrokerImpl".
-Se compila el servidor con "javac Collection.java" y "javac CollectionImpl.java", después se registra el servidor en el puerto correspondiente al hostname definido en el código con el comando "rmiregistry puerto&" y por último se ejecuta el servidor con "java CollectionAImpl".
-Se compila el servidor con "javac CollectionBImpl.java", después se registra el servidor en el puerto correspondiente al hostname definido en el código con el comando "rmiregistry puerto&" y por último se ejecuta el servidor con "java CollectionBImpl".
-Se compila el cliente con "javac Cliente.java" y se ejecuta el cliente con "java Cliente".