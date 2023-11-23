import socket
import argparse

# Creamos el parser para parsear el argumento
parser = argparse.ArgumentParser(description='Description of your script.')

# Definimos argumentos
parser.add_argument('arg1', type=str, help='Ip del server a conectar')


# Parseamos el argumento
args = parser.parse_args()

# Accedemos a los valores de los argumentos
arg1_value = args.arg1


# Definimos la ip usando argumentos
server_address = (arg1_value, 12345)

# Creamos un objeto socket
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Conectamos al servidor
client_socket.connect(server_address)
print('Connected to {}:{}'.format(*server_address))

# Enviamos un mensaje Hola server y recibimos la respuesta.
message = "Hola server"
client_socket.send(message.encode('utf-8'))
response = client_socket.recv(1024).decode('utf-8')
print(response)

# Cerramos el socket
client_socket.close()