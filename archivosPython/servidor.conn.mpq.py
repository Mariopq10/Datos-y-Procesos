import socket
from datetime import datetime

def registroUsuario(nombre, ip):
    # Registrar la información en un archivo de registros (Solo me funciona con ruta absoluta.)
    with open("registros.txt", "a") as file:
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        file.write(f"{timestamp} - Nombre: {nombre}, IP: {ip}\n")
        print("Registro realizado correctamente.")

def leerCliente(socketCliente, ipCliente):
    # Fase de Identificación
    identificacion = socketCliente.recv(1024).decode().lower()
    if identificacion.startswith("soy "):
        nombre = identificacion.split(" ", 1)[1]
        # Verificar si el nombre existe en el archivo
        with open("nombres.txt", "r") as file:
            if nombre in file.read().splitlines():
                socketCliente.send("Conectado con exito. Fase de registro.".encode())
                # Fase de Registro
                registroUsuario(nombre, ipCliente[0])
            else:
                socketCliente.send("Nombre desconocido. No se realizo conexion.".encode())
    else:
        socketCliente.send("Comando no reconocido. No se ha realizado conexion.".encode())

def main():
    socketServer = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    socketServer.bind(('0.0.0.0', 2002))
    socketServer.listen(5)
    print("Servidor esperando conexiones en el puerto 2002...")

    while True:
        socketCliente, ipCliente = socketServer.accept()
        print(f"Conexion establecida desde {ipCliente}")
        leerCliente(socketCliente, ipCliente)
        socketCliente.close()

if __name__ == "__main__":
    main()

