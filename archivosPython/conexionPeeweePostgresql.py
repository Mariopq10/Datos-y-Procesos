from peewee import PostgresqlDatabase, AutoField,DateTimeField,CharField,Model,ForeignKeyField
import datetime
import re

# Configuración de la conexión a la base de datos
db = PostgresqlDatabase('tienda', user='mario', password='12345', host='143.47.32.113', port=5432)


# Definición del modelo de la tabla técnicos
class Tecnicos(Model):
    codigo = AutoField()
    nombre = CharField()
    contrasena = CharField()

    class Meta:
        database = 'tienda'

# Definición del modelo de la tabla partes
class Partes(Model):
    id_parte = AutoField()
    descripcion = CharField()
    lugar = CharField()
    fecha_parte = DateTimeField(default=datetime.datetime.now)
    codigo_tecnico = ForeignKeyField(Tecnicos, to_field='codigo' , backref='partes', column_name='codigo_tecnico')
    fecha_finalizacion = DateTimeField(null=True)

    class Meta:
        database = 'tienda'

#Funcion que comprueba si la id del tecnico asignado es valido o no.
def comprobarTecnico():
    while True:
        tecnico = (input("Introducir el codigo del tecnico asignado (1-5): "))

        if not re.search('[0-5]', tecnico):
            print('Valor de tecnico no valido, pruebe otra vez.')
            continue
        break
    return tecnico

#Funcion que recoge los datos para introducirlos dentro de la base de datos en la tabla partes..
def insertarParte():
    # Insertar un nuevo parte
    tecnicoAsignado = comprobarTecnico()
    descripcionParte = input('Descripcion del parte: ')
    lugarParte = input('Lugar del parte: ')

    nuevo_parte = Partes.create(
        descripcion=descripcionParte,
        lugar=lugarParte,
        codigo_tecnico=tecnicoAsignado,
    )
    # Imprimir el número de parte generado
    print(f'Nuevo parte generado, descripcion: {nuevo_parte.descripcion}, ID del tecnico asignado: {nuevo_parte.codigo_tecnico}')

# Conexión a la base de datos
db.connect()
#Ejecutamos codigo de insercion de datos
insertarParte()
# Cerrar la conexión a la base de datos
db.close()

