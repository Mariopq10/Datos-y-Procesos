import socket
import threading

def recibir_mensajes(socketCliente):
    while True:
        try:
            mensaje = socketCliente.recv(1024).decode()
            if not mensaje:
                break
            print(f"Mensaje del servidor: {mensaje}")
        except ConnectionError:
            print("Conexión cerrada por el servidor.")
            break

def main():
    nombre = input("Ingrese su nombre: ")
    mensaje = f"soy {nombre}"

    socketCliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    socketCliente.connect(('192.168.6.68', 2002))

    # Envía el mensaje de identificación al servidor
    socketCliente.send(mensaje.encode())

    # Inicia un hilo para recibir mensajes del servidor
    threading.Thread(target=recibir_mensajes, args=(socketCliente,), daemon=True).start()

    # Ciclo principal para enviar mensajes al servidor
    while True:
        enviarMensaje = input("Ingrese un mensaje (o 'salir' para cerrar): ")
        socketCliente.send(enviarMensaje.encode())
        if enviarMensaje.lower() == 'salir':
            break

    socketCliente.close()

if __name__ == "__main__":
    main()
