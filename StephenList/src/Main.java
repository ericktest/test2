
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**

Using HashMap, build a contact list with the following information:
•	Last Name
•	First Name
•	Phone Number
•	Email Address
The list should be stored in the same file directory as the program and entries sorted by last name. 
The program should have the following functionality 
1.	Prompt the user for the name of the file where the information is stored
2.	Display a menu with add, delete, display list and exit
a.	Allow the user to add a new contact
b.	Allow the user to display the list of contacts
c.	Allow the user to delete a contact
d.	And allow user to exit the program

**/
public class Main {
	
	private static void addContact(BufferedReader br,  Map<Integer, Contact> unsortMap) throws IOException {
		String firstName = null;
		String lastName = null;
		String phone = null;
		String email = null;
		
    	System.out.println("Enter the first name: ");
    	firstName = br.readLine();
    	System.out.println("Enter the last name: ");
    	lastName = br.readLine();
    	System.out.println("Enter the phone: ");
    	phone = br.readLine();
    	System.out.println("Enter the email: ");
    	email = br.readLine();
    	
    	Contact c = new Contact();
    	c.setFirstName(firstName);
    	c.setLastName(lastName);
    	c.setPhone(phone);
    	c.setEmail(email);
    	
    	unsortMap.put(unsortMap.size()+1, c);
    	System.out.println("Contact added succesfully... ");
	}
	
	private static void displayContacts(Map<Integer, Contact> unsortMap) throws IOException {
        Map<Integer, Contact> sortedMap = sortByValue(unsortMap);
        printMap(sortedMap);
	}
	
	private static void deleteContact(BufferedReader br, Map<Integer, Contact> unsortMap) throws IOException {
		String name = null;
    	System.out.println("Enter the first name of the contact to delete: ");
    	name = br.readLine();
		Integer indexToDelete = null;
		boolean found = false;
		for (Map.Entry<Integer, Contact> entry : unsortMap.entrySet()) {
            Contact c = entry.getValue();
            if (name.equals(c.getFirstName())) {
            	indexToDelete = entry.getKey();
            	System.out.println("Item found ... deleting ... ");
            	found = true;
            	break;
            }
        }
		if (found) {
			unsortMap.remove(indexToDelete);
		} else {
        	System.out.println("Item not found ... Nothing deleted");
		}
	}
	
	private static void createFile(String fileName, Map<Integer, Contact> unsortMap) throws IOException {
		String csvFile = fileName + ".csv";
	    FileWriter writer = new FileWriter(csvFile);
	    CSVUtils.writeLine(writer, Arrays.asList("contactId", "lastName", "firstName", "phone", "email"));
		for (Map.Entry<Integer, Contact> entry : unsortMap.entrySet()) {
            Contact c = entry.getValue();
    	    CSVUtils.writeLine(writer, Arrays.asList(entry.getKey().toString(), c.getLastName(), c.getFirstName(), c.getPhone(), c.getEmail()));
        }
	    writer.flush();
	    writer.close();	
	}
	
    public static void main(String[] args) throws IOException {
    	
    	String outputFileName = null;
    	
    	boolean exit = false;
    	
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<Integer, Contact> unsortMap = new HashMap<Integer, Contact>();

    	System.out.println("Enter the name of the output file: ");
    	outputFileName = br.readLine();
    	
    	while (!exit) {
    		
            System.out.println("Please select from where do you want to get information:");

            System.out.println("1. Add Contact");

            System.out.println("2. Display Contacts");

            System.out.println("3. Delete Contact");

            System.out.println("4. Exit");
            
            System.out.println("");

            try {

                int input = Integer.parseInt(br.readLine());
                
                switch (input) {
                	case 1: 	addContact(br, unsortMap);
                				break;
                	case 2: 	displayContacts(unsortMap);
                				break;
                	case 3: 	deleteContact(br, unsortMap);
    							break;
                	case 4: 	createFile(outputFileName, unsortMap);
                				System.out.println("Writing to file and Exiting\r\n");
                				exit = true;
    							break;
                	default: 	System.out.println("Wrong option\r\n");
                }

            } catch (IOException ioe) {

                System.out.println("IO error trying to read your input!\r\n");

                System.exit(1);

            }    		
    	}    	

        
    	/*Contact c = new Contact();
    	c.setFirstName("erick");
    	c.setLastName("torres");
    	c.setPhone("2342423");
    	c.setEmail("erick@algo.com");
    	
        unsortMap.put(1, c);
        
    	c = new Contact();
    	c.setFirstName("patrick");
    	c.setLastName("perez");
    	c.setPhone("998844");
    	c.setEmail("patrix@algo.com");
        
        unsortMap.put(2, c);
        
    	c = new Contact();
    	c.setFirstName("beatriz");
    	c.setLastName("aranda");
    	c.setPhone("63636454");
    	c.setEmail("beatriz@algo.com");
        
        unsortMap.put(3, c);
        
        System.out.println("Unsort Map......");
        printMap(unsortMap); 

        System.out.println("\nSorted Map......By Value");
        Map<Integer, Contact> sortedMap = sortByValue(unsortMap);
        printMap(sortedMap);
    	 */
    }

    private static Map<Integer, Contact> sortByValue(Map<Integer, Contact> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<Integer, Contact>> list = new LinkedList<Map.Entry<Integer, Contact>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<Integer, Contact>>() {
            public int compare(Map.Entry<Integer, Contact> o1,
                               Map.Entry<Integer, Contact> o2) {
            	Contact c1 = o1.getValue();
            	Contact c2 = o2.getValue();
                return (c1.getLastName()).compareTo(c2.getLastName());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, Contact> sortedMap = new LinkedHashMap<Integer, Contact>();
        for (Map.Entry<Integer, Contact> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            Contact c = (Contact)entry.getValue();
            System.out.println(c.getFirstName() + "/" + c.getLastName() + "/" + c.getPhone() + "/" + c.getEmail());
        }
    }

}
