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

	// ohjelmassa ei käsitellä poikkeusia joten käytetään "throws Exception"
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
		
		// do-while rakenne toistuu kunnes käyttäjä vastaa ensimmäiseen kysymykseen ei
		do {
			System.out.println("Haluatko lisätä sanoja sanakirjaan? (kylla/ei)");
			lisaa = lukija.nextLine();
			
			// Jos käyttäjä vastaa kylla suoritetaan if-lause
			if (lisaa.equals("kylla")) {
				
				// Suoritetaan do-while lause kunnes sana1 tai sana2 saa tyhjän arvon
				do {
					System.out.println("Sana alkukielellä eli suomeksi? (tyhjä lopettaa)");
					sana1 = lukija.nextLine();
					
					// Kysytään käyttäjältä toinen sana vain jos käyttäjä antoi ensimmäisen sanan
					if (sana1.isEmpty() == false) {
						System.out.println("Sana käännettynä eli englanniksi? (tyhjä lopetaa)");
						sana2 = lukija.nextLine(); 
						
						// Lisätään käyttäjän antama sanapari sana1 ja sana2 Hashmappiin
						sanat.put(sana1, sana2);  
						
						// Esitellään olio ja tiedosto johon tullaan tallentamaan tietoa
						FileOutputStream tiedosto = new FileOutputStream("uusiSanakirja.ser");
				        ObjectOutputStream talteen = new ObjectOutputStream(tiedosto);
				        // Kirjoitetaan sanat tiedostoon
				        talteen.writeObject(sanat);
				        // Suljetaan tiedosto
				        tiedosto.close();
				        
				    // Jos käyttäjä ei anna ensimmäistä sanaa, toista sanaa ei kysytä ja lopeta saa arvon true 
					} else {
						break;
					}
				} while (sana1.isEmpty() == true || sana2.isEmpty() == true);
				
			} else if (lisaa.equals("ei") || (lopeta = true)) {
				break;
			} else {
				System.out.println("Kysymykseen voi vastata vain kyllä tai ei.");
			}
		} while (lisaa != "ei" || lopeta != true);
		
		// Tulostetaan käyttäjälle sanakirjan sisältö
		System.out.println("Sanakirjan sisältö: ");
		
		// Luodaan uusi HashMap sisalto joka saa arvon null
		HashMap<Integer, String> sisalto = null;
	    
		// Esitellään olio talteen2 ja tiedosto2 jonka sisältö tulee tiedostosta uusiSanakirja.ser johon tallennettiin sisältöä aiemmin ohjelmassa
	    FileInputStream tiedosto2 = new FileInputStream("uusiSanakirja.ser");
	    ObjectInputStream talteen2 = new ObjectInputStream(tiedosto2);
	    // Tehdään tyyppimuunnos
	    sisalto = (HashMap) talteen2.readObject();
	    // Suljetaan tiedosto2
	    tiedosto2.close();
	       
	    // Luodaan Set setti ja iterator iteraattori 
	    // Käytetään set-metodia muuttamaan sisalto muotoon jota iterator voi käyttää 
	    Set setti = sisalto.entrySet();
	    Iterator iteraattori = setti.iterator();
	    // Tehdään while looppi hasNext-metodin ja iteraattorin avulla
		while (iteraattori.hasNext()) {
			// Luodaan HashMap alkio ja annetaan sille sisältö iteraattorin avulla
			HashMap.Entry<String, String> alkio = (HashMap.Entry<String, String>) iteraattori.next();
			// Etsitään sanaparit HashMap alkiosta ja muotoillaan tulostuksen ulkoasu miellyttäväksi
			System.out.println(alkio.getKey() + " = " + alkio.getValue());
		}
		
		// Tulostuslause jota käytettiin tehtävän A ja B osiossa: System.out.println(sisalto);
		
		// do-while lausetta toistetaan kunnes käyttäjä vastaa tyhjällä sanalla
		do {
			System.out.println("Minkä sanan käännöksen haluat tietää? (tyhjä sana lopettaa) ");
			vastaus = lukija.nextLine();
			
			// Jos sana löytyy se ja sen pari tulostetaan
			if (sanat.containsKey(vastaus) == true) {
				System.out.println("Sanan \""+vastaus+"\" käännös on \"" + sisalto.get(vastaus) + "\".");
			// Kun syöte on tyhjä siirrytään takaisin loopin alkuun
			} else if (vastaus.isEmpty()){
				break;
			// Jos sanaa ei löydy annetaan seuraava syöte 
			} else {
				System.out.println("Sanan \""+vastaus+"\" käännös on tuntematon!");
			}
			
		} while (vastaus.isEmpty() == false);
		
		System.out.println("Ohjelma lopetetaan, kiitos käynnistä!");
	}
}