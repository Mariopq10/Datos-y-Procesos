import socket
import ssl

class Funciones:
    @staticmethod
    def displayMenu(reader, writer):
        while True:
            print("Elige una opcion:")
            print("1. Listar")
            print("2. Comando Stat")
            print("3. Leer correo")
            print("4. Exit")

            opcion = input()
            Funciones.seleccion(int(opcion), reader, writer)

    @staticmethod
    def seleccion(opcion, reader, writer):
        switcher = {
            1: Funciones.listar,
            2: Funciones.mostrar_datos,
            3: Funciones.leer_correo,
            4: Funciones.salir
        }
        func = switcher.get(opcion, lambda: print("Elige una opcion correcta."))
        func(reader, writer)

    @staticmethod
    def listar(reader, writer):
        print("Listar correos")
        writer.write("list\r\n")
        writer.flush()

        respuesta = reader.readline().strip()
        while respuesta != ".":
            print(respuesta)
            if respuesta == "." or respuesta == "-ERR Invalid message number.":
                break
            respuesta = reader.readline().strip()

    @staticmethod
    def mostrar_datos(reader, writer):
        print("Mostrando datos")
        writer.write("stat\r\n")
        writer.flush()

        respuesta = reader.readline().strip()
        cantidadCorreos = int(respuesta.split(" ")[1])
        print("Cantidad de correos:", cantidadCorreos)

    @staticmethod
    def leer_correo(reader, writer):
        print("Dime el correo que quieres leer")
        eleccion = input()
        writer.write("top " + eleccion + " 20 \r\n")
        writer.flush()

        respuesta = reader.readline().strip()
        while respuesta:
            print(respuesta)
            if respuesta == "." or respuesta == "-ERR Invalid message number.":
                break
            respuesta = reader.readline().strip()

    @staticmethod
    def salir(reader, writer):
        print("Saliendo del programa")
        exit(0)


def main():
    server_address = "damplaya.hopto.org"
    server_port = 995

    try:
        context = ssl.create_default_context()
        context.check_hostname = False
        context.verify_mode = ssl.CERT_NONE

        with context.wrap_socket(socket.socket(socket.AF_INET), server_hostname=server_address) as s:
            s.connect((server_address, server_port))

            reader = s.makefile("r")
            writer = s.makefile("w")

            conexion = reader.readline().strip()
            print(conexion)

            if "+" in conexion:
                print("Dime el nombre de usuario")
                usuario = "user " + input()
                writer.write(usuario + "\r\n")
                writer.flush()

                respuesta_usuario = reader.readline().strip()
                print(respuesta_usuario)

                print("Introduce contrasena")
                password = "pass " + input()
                writer.write(password + "\r\n")
                writer.flush()

                respuesta_password = reader.readline().strip()
                print(respuesta_password)

                if "logged in" in respuesta_password:
                    print("Inicio de sesión exitoso. ¿Qué deseas hacer?")
                    Funciones.displayMenu(reader, writer)
                else:
                    print("Inicio de sesión fallido.")
            else:
                print("Conexion erronea")

    except Exception as e:
        print("Error:", e)

if __name__ == "__main__":
    main()
