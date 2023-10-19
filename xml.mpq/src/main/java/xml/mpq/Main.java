package xml.mpq;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class Main {

	public static void main(String[] args) {

		try {
			File file = new File(".//fichero.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document fichero = dBuilder.parse(file);
			fichero.getDocumentElement().normalize();
			System.out.println("Root element :" + fichero.getDocumentElement().getNodeName());
			NodeList nuevoNodo = fichero.getElementsByTagName("user");
			System.out.println("----------------------------");

			for (int i = 0; i < nuevoNodo.getLength(); i++) {
				Node nNode = nuevoNodo.item(i);
				

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Usuario : " + eElement.getAttribute("id"));
					System.out.println(
							"Nombre : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
					System.out.println(
							"Apellido : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
					System.out.println(
							"Ocupacion : " + eElement.getElementsByTagName("occupation").item(0).getTextContent()+"\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
