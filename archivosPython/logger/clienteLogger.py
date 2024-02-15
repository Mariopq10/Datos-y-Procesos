import socket
numero=input("Adivina el numero (0-9)")

#Cliente que envia un numero usando un socket_cliente que se conectará al servidor alojado en localhost puerto 2025.
try:
    socket_cliente=socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    socket_cliente.connect(("localhost", 2025))
    socket_cliente.send(numero.encode())
    mensaje_servidor = socket_cliente.recv(1024).decode()
    # Se recibe el mensaje del servidor.
    print("Mensaje del servidor: ",mensaje_servidor)
except Exception as e:
    print(f"Error: {e}")
finally:
    socket_cliente.close()
