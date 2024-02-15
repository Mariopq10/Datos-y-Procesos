import socket
import logging
import random

# Configuracion del componente logging, formato del archivo, nombre del archivo donde se importara esos datos, y modo de archivo.
logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s - %(threadName)s - %(processName)s - %(levelname)s - %(message)s',
                    filename='./logger/log.log',
                    filemode='a')

# Funcion que realiza tanto el envio de datos al logger como la comprobacion de datos recibida del servidor.
def numeroAdivinar(socket_server):
    numero=socket_server.recv(1024).decode()
    logging.debug("Cliente conectado, numero recibido correctamente.")
    try:
        numInsert = int(numero)
    except ValueError:
        logging.critical("No se introdujo un numero.")
        socket_server.send("No ha introducido un numero valido".encode())
        return

    if numInsert < 0 or numInsert > 9:
        logging.error("No se ha introducido un numero del 0 al 9")
        socket_server.send("No se ha introducido un numero del 0 al 9".encode())
    else:
        numero_aleatorio = random.randint(0, 9)
        if numInsert == numero_aleatorio:
            logging.info("Usuario adivina el numero.")
            socket_server.send("Adivinaste el número!".encode())
        else:
            logging.info("Usuario no adivino el numero.")
            socket_server.send("No adivinaste el numero, prueba de nuevo.".encode())

    
try:
    while True:
        socket_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        socket_server.bind(("localhost", 2025))
        socket_server.listen(1)
        conn,addr=socket_server.accept()
        print("El servidor esperando al numero")
        logging.debug("El servidor esperando al numero")
        print(numeroAdivinar(conn))
except Exception as e:
    print(f"Error: {e}")
finally:
    socket_server.close()