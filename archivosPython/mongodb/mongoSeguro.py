from pymongo import MongoClient

username = input("Ingrese el nombre de usuario: ")
password = input("Ingrese la contraseña: ")

# Construye la cadena de conexión con el nombre de usuario y la contraseña
connection_string = f"mongodb://{username}:{password}@143.47.54.181:27017/?tls=true&tlsAllowInvalidCertificates=true"

try:
    # Conéctate al servidor MongoDB
    with MongoClient(connection_string) as client:
        # Conexión exitosa
        print("Conexión exitosa a MongoDB")

        # Obtener la base de datos
        database = client["facturas"]
        print("Conectado a la base de datos:", database.name)

        # Obtener las colecciones
        collection_names = database.list_collection_names()
        
        if collection_names:
            print("Colecciones en la base de datos:")
            # Iterar sobre las colecciones
            for collection_name in collection_names:
                print(collection_name)
        else:
            print("No se encontraron colecciones en la base de datos.")

except Exception as e:
    print("Error al conectar a MongoDB:", e)
