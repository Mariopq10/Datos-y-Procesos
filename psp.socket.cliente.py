import socket

def start_client():
    host = '192.168.6.67'  # Puedes cambiar esto con la dirección IP del servidor
    port = 12345         # Asegúrate de usar el mismo puerto que el servidor

    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((host, port))

    print(f"Conectado al servidor en {host}:{port}")

    while True:
        message = input("Escribe un mensaje para enviar al servidor (o 'exit' para salir): ")
        client_socket.send(message.encode('utf-8'))

        if message.lower() == 'exit':
            break

        response = client_socket.recv(1024).decode('utf-8')
        print(f"Respuesta del servidor: {response}")

    client_socket.close()

if __name__ == "__main__":
    start_client()
