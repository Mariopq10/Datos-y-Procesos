package ad.crud.mongodb.mpq;


import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class Funciones {
	Scanner sc = new Scanner(System.in);

	public static void crearDisco(MongoCollection<Document> collection, String titulo, String banda, int ano, List<String> estilosMusicales, List<String> mejoresTemas) {
			        Document nuevoDisco = new Document("titulo", titulo)
	                .append("banda", banda)
	                .append("ano", ano)
	                .append("estilo", estilosMusicales)
	                .append("mejoresTemas", mejoresTemas);

	        collection.insertOne(nuevoDisco);
	        System.out.println("Disco creado: " + titulo);
	    }

	    private static void consultarDisco(MongoCollection<Document> collection, String titulo) {
	        Document discoEncontrado = collection.find(Filters.eq("titulo", titulo)).first();
	        System.out.println("Consulta de un solo disco:\n" + discoEncontrado);
	    }

	    private static void consultarGrupoDiscos(MongoCollection<Document> collection, String estilo) {
	        // Consulta todos los discos del mismo estilo
	        System.out.println("Consulta de un grupo de discos (estilo " + estilo + "):");
	        collection.find(Filters.eq("estilo", estilo)).forEach(System.out::println);
	    }

	    private static void actualizarCampoDisco(MongoCollection<Document> collection, String titulo, String campo, String antiguoValor, String nuevoValor) {
	        // Actualiza un campo específico en un disco
	        collection.updateOne(Filters.and(Filters.eq("titulo", titulo), Filters.eq(campo, antiguoValor)),
	                Updates.set(campo, nuevoValor));

	        System.out.println("Campo actualizado en el disco " + titulo + ": " + campo + " - " + antiguoValor + " => " + nuevoValor);
	    }

	    private static void eliminarDisco(MongoCollection<Document> collection, String titulo) {
	        // Elimina un disco por título
	        collection.deleteOne(Filters.eq("titulo", titulo));
	        System.out.println("Disco eliminado: " + titulo);
	    }
	}



