import socket

#Cliente para conectar al server que se introducirá dentro del servidor de oracle.
def main():
    nombre = input("Ingrese su nombre: ")
    #Se añade "soy " al mensaje.
    mensaje = f"soy {nombre}"
    
    socketCliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    #Realizamos la conexion a la ip que esperamos con el puerto 2002.
    socketCliente.connect(('143.47.38.3', 2002))
    #Enviamos el mensaje que recogerá el servidor "soy +nombre"
    socketCliente.send(mensaje.encode())
    
    respuesta = socketCliente.recv(1024).decode()
    print(respuesta)
    
    socketCliente.close()

if __name__ == "__main__":
    main()