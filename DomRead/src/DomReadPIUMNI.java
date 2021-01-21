import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DomReadPIUMNI {

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

	       try {
	   		File xmlFile = new File("XMLPIUMNI.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();		
			System.out.println("Donga Daniel beadando");
			Action(doc);	   
	       }catch (ParserConfigurationException pce) {
	           pce.printStackTrace();
	       } catch (TransformerException tfe) {
	        tfe.printStackTrace();
	       } catch (IOException ioe) {
	        ioe.printStackTrace();
	       } catch (SAXException sae) {
	        sae.printStackTrace();
	       }
	}
//Eldöntheted, hogy módosítani, vagy olvasni szeretnéd az xml-t
	public static void Action(Document doc) throws TransformerException {
	
		System.out.println("Press 1 to read data");
		System.out.println("Press 2 to edit data");
		int action = ReadCategory();

		switch (action) {
		case 1:
			//1-es gomb megnyomása után olvas, az a methodus van meghivva
			Read(doc);
			break;
		case 2:
			//2-es gomb esetén update-el
			Update(doc);
			break;
		default:
			Action(doc);
			break;
		}

	}
//Bekér egy számot, amely annak a sorszámát, amelyik tag-en belül te olvasni szeretnéd az adatokat
	public static int ReadCategory() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Adja meg a sorszamot:");
		int readCategory = scan.nextInt();
		return readCategory;
	}
	//bekéri az adatot, hogy milyen tipusu részt szeretne modositani majd meghiv egy methodust
	//benne egy switch case-el , ami  meghiv szintén egy másik osztályban lévõ methodust 
	public static void Update(Document doc) throws TransformerException {
		System.out.println(" XML Editing.. \n");
		System.out.println("What do you want to edit ? ");
		System.out.println("1. Car\n2. Manufacture \n3. owner \n 4.Cégvezetõ \n 5. motor ");
		int category = 0;
		category = ReadCategory();
		ShowElementUpdates(category, doc);
	}
	//olvaso methodus
	public static void Read(Document doc) {
		System.out.println("XML reading.. \n");
		System.out.println("What do you want to read? ");
		System.out.println("1. Car \n 2. Manufacture \n 3. owner \n 4. Motor \n 5. Cégvezetõ ");
		int category = 0;
		category = ReadCategory();
		ShowCategoryElements(category, doc);
	}
//itt donti el a program, hogy beadott input alapján mit hivjon meg.
	public static void ShowCategoryElements(int category, Document doc) {
		switch (category) {
		case 1:
			ReadCar(doc);
			break;
		case 2:
			ReadGyar(doc);
			break;
		case 3:
			Readtulajdonos(doc);
			break;
		case 4:
			Readmotor(doc);
			break;
		case 5:
			Readvezeto(doc);
			break;
		default:
			int newCategory = ReadCategory();
			ShowCategoryElements(newCategory, doc);
			break;
		}
	}
//meghivja a másik osztályban megirt update methodusokat, ez is a beadott input alapján
	public static void ShowElementUpdates(int category, Document doc) throws TransformerException {
		switch (category) {
		case 1:
			//A felhasználó a 1.es gombot megadva modositja a kocsit
			DOMModifyPIUMNI.UpdateCar(doc);
			break;
			// A felhasználó a 2.es gombot megadva modositja a gyárat
		case 2:
			DOMModifyPIUMNI.UpdateGyar(doc);
			break;
			//A felhasználó a 3.as gombot megadva modositja a tulajdonost
		case 3:
			DOMModifyPIUMNI.UpdateTulajdonos(doc);
			break;
			//A felhasználó a 4.as gombot megadva modositja a vezetot
		case 4:
			DOMModifyPIUMNI.Updatevezeto(doc);
			break;
		}
		
	}
	
	//kiolvassa a tulajdonost
	public static void Readtulajdonos(Document doc) {

		NodeList nList = doc.getElementsByTagName("tulajdonos");

		for (int i = 0; i < nList.getLength(); i++) {

			Node nNode = nList.item(i);

			NodeList cList = nList.item(i).getChildNodes();

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) nNode;

				String tulajid = element.getAttribute("id");

				Node node1 = element.getElementsByTagName("eletkora").item(0);
				String age = node1.getTextContent();

				Node node2 = element.getElementsByTagName("jarmuvekszama").item(0);
				String jarmuvekszama = node2.getTextContent();

				Node node4 = element.getElementsByTagName("telefonszama").item(0);
				String phonenumber = node4.getTextContent();

				String firstname = "";
				String lastname = "";
				

				for (int j = 0; j < cList.getLength(); j++) {

					Node cnode1 = element.getElementsByTagName("vezeteknev").item(0);
					firstname = cnode1.getTextContent();

					Node cnode2 = element.getElementsByTagName("keresztnev").item(0);
					lastname = cnode2.getTextContent();

				}

				System.out.println("Tulajdonos id:" + tulajid + "\tEletkor: " + age + "\tJarmuvek szama: " + jarmuvekszama
						+ "\tTelefonszam : " + phonenumber  + "\t Firstname = : " + firstname + " lastname = " + lastname 
						 );
			}
		}
	}
	
	

	//kiolvassa a gyártot
		public static void Readmotor(Document doc) {

			NodeList nList = doc.getElementsByTagName("motor");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) nNode;

					String gyartoid = element.getAttribute("id");

					Node node1 = element.getElementsByTagName("nev").item(0);
					String name = node1.getTextContent();

					Node node2 = element.getElementsByTagName("gyartasiev").item(0);
					String year = node2.getTextContent();

					Node node3 = element.getElementsByTagName("tipusa").item(0);
					String telephely = node3.getTextContent();

					System.out.println("Motor id:" + gyartoid + "\tNev: " + name + "\tÉv: " + year
							+ "\tTelephely: " + telephely);
				}
			}
		}
		
		
		public static void Readvezeto(Document doc) {

			NodeList nList = doc.getElementsByTagName("cegvezeto");

			for (int i = 0; i < nList.getLength(); i++) {

				Node nNode = nList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) nNode;

					String vezetoid = element.getAttribute("id");

					Node node1 = element.getElementsByTagName("nev").item(0);
					String name = node1.getTextContent();

					Node node2 = element.getElementsByTagName("tapasztalat").item(0);
					String tapasztalat = node2.getTextContent();

					Node node3 = element.getElementsByTagName("fizetés").item(0);
					String fizetes = node3.getTextContent();

					System.out.println("Id:" + vezetoid + "\tNev: " + name + "\tTapasztalat: " + tapasztalat
							+ "\tFizetes: " + fizetes);
				}
			}
		}
	
	
//kiolvassa a gyártot
	public static void ReadGyar(Document doc) {

		NodeList nList = doc.getElementsByTagName("gyarto");

		for (int i = 0; i < nList.getLength(); i++) {

			Node nNode = nList.item(i);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) nNode;

				String gyartoid = element.getAttribute("id");

				Node node1 = element.getElementsByTagName("nev").item(0);
				String name = node1.getTextContent();

				Node node2 = element.getElementsByTagName("ev").item(0);
				String year = node2.getTextContent();

				Node node3 = element.getElementsByTagName("telephely").item(0);
				String telephely = node3.getTextContent();

				System.out.println("Gyarto id:" + gyartoid + "\tNev: " + name + "\tÉv: " + year
						+ "\tTelephely: " + telephely);
			}
		}
	}
//Kiolvasa a jármûvet
	public static void ReadCar(Document doc) {

		NodeList nList = doc.getElementsByTagName("jarmu");

		for (int i = 0; i < nList.getLength(); i++) {

			Node nNode = nList.item(i);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) nNode;

				String rsz = element.getAttribute("rsz");
				String tulajid = element.getAttribute("tulajid");
				String gyartoid = element.getAttribute("gyartoid");
			

				Node node1 = element.getElementsByTagName("fogyasztas").item(0);
				String fogyasztas = node1.getTextContent();

				Node node2 = element.getElementsByTagName("tipus").item(0);
				String type = node2.getTextContent();

				Node node3 = element.getElementsByTagName("szine").item(0);
				String color = node3.getTextContent();

				Node node4 = element.getElementsByTagName("futottkm").item(0);
				String futottkm = node4.getTextContent();

				Node node5 = element.getElementsByTagName("gyartasiev").item(0);
				String gyartasiev = node5.getTextContent();

				System.out.println(" Rendszam :" + rsz + "\tTulajd id:" + tulajid + "\t gyarto id id:" + gyartoid
						+  "\tfogyasztas: " + fogyasztas + "\ttipus: " + type + "\tSzine: "
						+ color + "\tFutott km : " + futottkm + "\tGyartasi ev: " + gyartasiev);

			}
		}

	}
	
	
}