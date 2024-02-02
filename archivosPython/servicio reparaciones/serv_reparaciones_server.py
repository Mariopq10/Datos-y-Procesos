import socket
import pickle
from peewee import DoesNotExist

HOST = '127.0.0.1'
PORT = 2002

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind((HOST, PORT))
server_socket.listen()

print(f"Servidor escuchando en el puerto {PORT}")

while True:
    conn, addr = server_socket.accept()
    print(f"Conexión establecida con {addr}")

    try:
        # Recibir el número de técnico desde el cliente
        tecnico_numero = conn.recv(1024).decode('utf-8')

        # Verificar si el técnico existe
        try:
            # Acceder a la BD en la tabla de partes y seleccionar los partes del nº de ese técnico
            reparacion = Reparacion.get(Reparacion.tecnico_numero == tecnico_numero)
            respuesta = {'descripcion': reparacion.descripcion}

            # Actualizar la BD en la tabla de partes (puedes modificar esta parte según tus necesidades)
            reparacion.descripcion = "Nueva descripción del parte"
            reparacion.save()

        except DoesNotExist:
            respuesta = {'descripcion': None}

        # Enviar la respuesta al cliente
        conn.sendall(pickle.dumps(respuesta))

    except Exception as e:
        print(f"Error: {e}")

    finally:
        conn.close()
