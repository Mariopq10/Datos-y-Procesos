import re
import socket
import pickle

HOST = '127.0.0.1'
PORT = 2002

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect((HOST, PORT))

#Funcion que comprueba si la id del tecnico asignado es valido o no.
def comprobarTecnico():
    while True:
        tecnico = (input("Introducir el codigo del tecnico asignado (1-5): "))

        if not re.search('[0-5]', tecnico):
            print('Valor de tecnico no valido, pruebe otra vez.')
            continue
        break
    return tecnico


# Enviar el número de técnico al servidor
tecnico_numero = comprobarTecnico()
client_socket.sendall(tecnico_numero.encode('utf-8'))

# Recibir la respuesta del servidor
respuesta = pickle.loads(client_socket.recv(1024))

if respuesta['descripcion']:
    print(f"Descripción del parte de reparación: {respuesta['descripcion']}")
else:
    print("No hay partes de reparación para este técnico.")

client_socket.close()
