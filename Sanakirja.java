import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Sanakirja {

	// ohjelmassa ei k�sitell� poikkeusia joten k�ytet��n "throws Exception"
	public static void main(String[] args) throws Exception {
		
		Scanner lukija = new Scanner(System.in);
		
		String vastaus = "", lisaa = "", sana1 = "", sana2 = "";
		boolean lopeta = false;
		String[] suomi = { "kissa", "koira", "hevonen", "auto", "vene" };
		String[] englanti = { "cat", "dog", "horse", "car", "boat" };
		
		// Luodaan hashmap sanat ja muodostetaan sanaparit for silmukan avulla
		Map<String, String> sanat = new HashMap<String, String>();
		for (int i = 0; i < suomi.length; i++) {
			sanat.put(suomi[i], englanti[i]);
		}
		
		// do-while rakenne toistuu kunnes k�ytt�j� vastaa ensimm�iseen kysymykseen ei
		do {
			System.out.println("Haluatko lis�t� sanoja sanakirjaan? (kylla/ei)");
			lisaa = lukija.nextLine();
			
			// Jos k�ytt�j� vastaa kylla suoritetaan if-lause
			if (lisaa.equals("kylla")) {
				
				// Suoritetaan do-while lause kunnes sana1 tai sana2 saa tyhj�n arvon
				do {
					System.out.println("Sana alkukielell� eli suomeksi? (tyhj� lopettaa)");
					sana1 = lukija.nextLine();
					
					// Kysyt��n k�ytt�j�lt� toinen sana vain jos k�ytt�j� antoi ensimm�isen sanan
					if (sana1.isEmpty() == false) {
						System.out.println("Sana k��nnettyn� eli englanniksi? (tyhj� lopetaa)");
						sana2 = lukija.nextLine(); 
						
						// Lis�t��n k�ytt�j�n antama sanapari sana1 ja sana2 Hashmappiin
						sanat.put(sana1, sana2);  
						
						// Esitell��n olio ja tiedosto johon tullaan tallentamaan tietoa
						FileOutputStream tiedosto = new FileOutputStream("uusiSanakirja.ser");
				        ObjectOutputStream talteen = new ObjectOutputStream(tiedosto);
				        // Kirjoitetaan sanat tiedostoon
				        talteen.writeObject(sanat);
				        // Suljetaan tiedosto
				        tiedosto.close();
				        
				    // Jos k�ytt�j� ei anna ensimm�ist� sanaa, toista sanaa ei kysyt� ja lopeta saa arvon true 
					} else {
						break;
					}
				} while (sana1.isEmpty() == true || sana2.isEmpty() == true);
				
			} else if (lisaa.equals("ei") || (lopeta = true)) {
				break;
			} else {
				System.out.println("Kysymykseen voi vastata vain kyll� tai ei.");
			}
		} while (lisaa != "ei" || lopeta != true);
		
		// Tulostetaan k�ytt�j�lle sanakirjan sis�lt�
		System.out.println("Sanakirjan sis�lt�: ");
		
		// Luodaan uusi HashMap sisalto joka saa arvon null
		HashMap<Integer, String> sisalto = null;
	    
		// Esitell��n olio talteen2 ja tiedosto2 jonka sis�lt� tulee tiedostosta uusiSanakirja.ser johon tallennettiin sis�lt�� aiemmin ohjelmassa
	    FileInputStream tiedosto2 = new FileInputStream("uusiSanakirja.ser");
	    ObjectInputStream talteen2 = new ObjectInputStream(tiedosto2);
	    // Tehd��n tyyppimuunnos
	    sisalto = (HashMap) talteen2.readObject();
	    // Suljetaan tiedosto2
	    tiedosto2.close();
	       
	    // Luodaan Set setti ja iterator iteraattori 
	    // K�ytet��n set-metodia muuttamaan sisalto muotoon jota iterator voi k�ytt�� 
	    Set setti = sisalto.entrySet();
	    Iterator iteraattori = setti.iterator();
	    // Tehd��n while looppi hasNext-metodin ja iteraattorin avulla
		while (iteraattori.hasNext()) {
			// Luodaan HashMap alkio ja annetaan sille sis�lt� iteraattorin avulla
			HashMap.Entry<String, String> alkio = (HashMap.Entry<String, String>) iteraattori.next();
			// Etsit��n sanaparit HashMap alkiosta ja muotoillaan tulostuksen ulkoasu miellytt�v�ksi
			System.out.println(alkio.getKey() + " = " + alkio.getValue());
		}
		
		// Tulostuslause jota k�ytettiin teht�v�n A ja B osiossa: System.out.println(sisalto);
		
		// do-while lausetta toistetaan kunnes k�ytt�j� vastaa tyhj�ll� sanalla
		do {
			System.out.println("Mink� sanan k��nn�ksen haluat tiet��? (tyhj� sana lopettaa) ");
			vastaus = lukija.nextLine();
			
			// Jos sana l�ytyy se ja sen pari tulostetaan
			if (sanat.containsKey(vastaus) == true) {
				System.out.println("Sanan \""+vastaus+"\" k��nn�s on \"" + sisalto.get(vastaus) + "\".");
			// Kun sy�te on tyhj� siirryt��n takaisin loopin alkuun
			} else if (vastaus.isEmpty()){
				break;
			// Jos sanaa ei l�ydy annetaan seuraava sy�te 
			} else {
				System.out.println("Sanan \""+vastaus+"\" k��nn�s on tuntematon!");
			}
			
		} while (vastaus.isEmpty() == false);
		
		System.out.println("Ohjelma lopetetaan, kiitos k�ynnist�!");
	}
}