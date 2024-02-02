from pymongo import MongoClient

# Conectar a la base de datos MongoDB
client = MongoClient('localhost', 27017)
db = client['ticket']

# Crear una colección llamada 'tickets'
tickets_collection = db['ticket']

# Crear 50 nuevos ticket
for i in range(1, 51):
    nuevo_ticket = {
        "id": i,
        "nombre": "Sin asignar",
        "estado": "Abierto"
    }
    tickets_collection.insert_one(nuevo_ticket)

print("Se añadieron 50 tickets a la base de datos.")