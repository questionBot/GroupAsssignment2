package Examples;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Examples.AppleStore;
import Examples.Product;
import Examples.Services;
import Examples.Discounts;
import Examples.GeniusAppt;

import Examples.SimpleGUI;

public class Reasoner {

	// The main Class Object holding the Domain knowledge

	// Generate the classes automatically with: Opening a command console and
	// type:
	// Path to YOUR-PROJECTROOT-IN-WORKSPACE\xjc.bat yourschemaname.xsd -d src
	// -p yourclasspackagename

	public AppleStore theapplestores; //This is a candidate for a name change

	public SimpleGUI Myface;

	// The lists holding the class instances of all domain entities

	public List theAppleStoreList = new ArrayList(); //This is a candidate for a name change
	public List theProductList = new ArrayList();    //This is a candidate for a name change
	public List theServiceList = new ArrayList();  //This is a candidate for a name change
	public List theDiscountList = new ArrayList(); //This is a candidate for a name change
	public List theGeniusApptList = new ArrayList(); //This is a candidate for a name change
	public List theRecentThing = new ArrayList();  // <<<<<<< CHECK!!!

	// Gazetteers to store synonyms for the domain entities names

	public Vector<String> applestoresyn = new Vector<String>();  //This is a candidate for a name change
	public Vector<String> productsyn = new Vector<String>();     //This is a candidate for a name change
	public Vector<String> servicesyn = new Vector<String>();   //This is a candidate for a name change
	public Vector<String> discountsyn = new Vector<String>();  //This is a candidate for a name change
	public Vector<String> geniusapptsyn = new Vector<String>();  //This is a candidate for a name change
	public Vector<String> recentobjectsyn = new Vector<String>(); // <<<<<<< CHECK!!!

	public String questiontype = "";         // questiontype selects method to use in a query
	public List classtype = new ArrayList(); // classtype selects which class list to query
	public String attributetype = "";        // attributetype selects the attribute to check for in the query

	public Object Currentitemofinterest; // Last Object dealt with
	public Integer Currentindex;         // Last Index used

	public String tooltipstring = "";
	public String URL = "";              // URL for Wordnet site
	public String URL2 = "";             // URL for Wikipedia entry

	public Reasoner(SimpleGUI myface) {

		Myface = myface; // reference to GUI to update Tooltip-Text
		// basic constructor for the constructors sake :)
	}

	public void initknowledge() { // load all the library knowledge from XML 

		JAXB_XMLParser xmlhandler = new JAXB_XMLParser(); // we need an instance of our parser

		//This is a candidate for a name change
		File xmlfiletoload = new File("AppleStore.xml"); // we need a (CURRENT)  file (xml) to load  

		// Init synonmys and typo forms in gazetteers

		applestoresyn.add("apple store");   	//This is a candidate for a name change
		applestoresyn.add("place");		//This is a candidate for a name change
		applestoresyn.add("store");	//This is a candidate for a name change
		applestoresyn.add("mac store"); 	//This is a candidate for a name change
		applestoresyn.add("new apple products");		//This is a candidate for a name change
		applestoresyn.add("apple shop");		//This is a candidate for a name change
		applestoresyn.add("shop with apple products");		//This is a candidate for a name change

		productsyn.add("product");    //All of the following is a candidate for a name change
		productsyn.add("computer");
		productsyn.add("ipad");
		productsyn.add("ipod");
		productsyn.add("mac");
		productsyn.add("macintosh");
		productsyn.add("laptop");
		productsyn.add("apple tv");
		productsyn.add("accessories");
		productsyn.add("cable");

		servicesyn.add("services"); //All of the following is a candidate for a name change
		servicesyn.add("apple care");
		servicesyn.add("insurance");
		servicesyn.add("camp");
		servicesyn.add("training");
		servicesyn.add("workshop");

		discountsyn.add("discounts");  //All of the following is a candidate for a name change
		discountsyn.add("money off");
		discountsyn.add("student");

		geniusapptsyn.add("genius"); 
		geniusapptsyn.add("appointment");   //All of the following is a candidate for a name change
		geniusapptsyn.add("broken");       
		geniusapptsyn.add("fix");   
		geniusapptsyn.add("help");     

		recentobjectsyn.add(" this");   //All of the following is a candidate for a name change
		recentobjectsyn.add(" that");
		recentobjectsyn.add(" him");
		recentobjectsyn.add(" her");	// spaces to prevent collision with "wHERe"	
		recentobjectsyn.add(" it");

		try {
			FileInputStream readthatfile = new FileInputStream(xmlfiletoload); // initiate input stream

			theapplestores = xmlhandler.loadXML(readthatfile);

			// Fill the Lists with the objects data just generated from the xml

			theProductList = theapplestores.getProduct();  		//This is a candidate for a name change
			theServiceList = theapplestores.getServices(); 	//This is a candidate for a name change
			theDiscountList = theapplestores.getDiscounts(); 	//This is a candidate for a name change
			theGeniusApptList = theapplestores.getGeniusAppt(); 	//This is a candidate for a name change
			theAppleStoreList.add(theapplestores);             // force it to be a List, //This is a candidate for a name change

			System.out.println("List reading");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("error in init");
		}
	}

	public  Vector<String> generateAnswer(String input) { // Generate an answer (String Vector)

		Vector<String> out = new Vector<String>();
		out.clear();                 // just to make sure this is a new and clean vector
		
		questiontype = "none";

		Integer Answered = 0;        // check if answer was generated

		Integer subjectcounter = 0;  // Counter to keep track of # of identified subjects (classes)
		
		// Answer Generation Idea: content = Questiontype-method(classtype class) (+optional attribute)

		// ___________________________ IMPORTANT _____________________________

		input = input.toLowerCase(); // all in lower case because thats easier to analyse
		
		// ___________________________________________________________________

		String answer = "";          // the answer we return

		// ----- Check for the kind of question (number, location, etc)------------------------------

		if (input.contains("how many")){questiontype = "amount"; input = input.replace("how many", "<b>how many</b>");} 
		if (input.contains("number of")){questiontype = "amount"; input = input.replace("number of", "<b>number of</b>");}
		if (input.contains("amount of")){questiontype = "amount"; input = input.replace("amount of", "<b>amount of</b>");} 
		if (input.contains("count")){questiontype = "amount"; input = input.replace("count", "<b>count</b>");}

		if (input.contains("what kind of")){questiontype = "list"; input = input.replace("what kind of", "<b>what kind of</b>");}
		if (input.contains("list all")){questiontype = "list"; input = input.replace("list all", "<b>list all</b>");}
		if (input.contains("diplay all")){questiontype = "list"; input = input.replace("diplay all", "<b>diplay all</b>");}

		if (input.contains("is there a")){questiontype = "checkfor"; input = input.replace("is there a", "<b>is there a</b>");}
		if (input.contains("i am searching")){questiontype = "checkfor"; input = input.replace("i am searching", "<b>i am searching</b>");}
		if (input.contains("i am looking for")){questiontype = "checkfor"; input = input.replace("i am looking for", "<b>i am looking for</b>");}
		if (input.contains("do you have")&&!input.contains("how many")){questiontype = "checkfor";input = input.replace("do you have", "<b>do you have</b>");}
		if (input.contains("i look for")){questiontype = "checkfor"; input = input.replace("i look for", "<b>i look for</b>");}
		if (input.contains("is there")){questiontype = "checkfor"; input = input.replace("is there", "<b>is there</b>");}

		if (input.contains("where") 
				|| input.contains("can't find")
				|| input.contains("can i find") 
				|| input.contains("way to"))

		{
			questiontype = "location";
			System.out.println("Find Location");
		}
		
		if (input.contains("can i buy") 
				|| input.contains("can i get")
				|| input.contains("can i find")
				|| input.contains("am i able to")
				|| input.contains("could i source") 
				|| input.contains("i want to buy")
				|| input.contains("i want to purchase"))

		{
			questiontype = "intent";
			System.out.println("Find Product Availability");
		}
		
		if (input.contains("thank you") 
				|| input.contains("bye")
				|| input.contains("thanks")
				|| input.contains("cool thank")) 			

		{
			questiontype = "farewell";
			System.out.println("farewell");
		}


		// ------- Checking the Subject of the Question --------------------------------------

		for (int x = 0; x < productsyn.size(); x++) {   //This is a candidate for a name change
			if (input.contains(productsyn.get(x))) {    //This is a candidate for a name change
				classtype = theProductList;             //This is a candidate for a name change
				
				input = input.replace(productsyn.get(x), "<b>"+productsyn.get(x)+"</b>");
				
				subjectcounter = 1;
				System.out.println("Class type Product recognised.");
			}
		}
		for (int x = 0; x < servicesyn.size(); x++) {  //This is a candidate for a name change
			if (input.contains(servicesyn.get(x))) {   //This is a candidate for a name change
				classtype = theServiceList;            //This is a candidate for a name change
				
				input = input.replace(servicesyn.get(x), "<b>"+servicesyn.get(x)+"</b>");
				
				subjectcounter = 1;
				System.out.println("Class type Service recognised.");
			}
		}
		for (int x = 0; x < discountsyn.size(); x++) {  //This is a candidate for a name change
			if (input.contains(discountsyn.get(x))) {   //This is a candidate for a name change
				classtype = theDiscountList;            //This is a candidate for a name change
				
				input = input.replace(discountsyn.get(x), "<b>"+discountsyn.get(x)+"</b>");
				
				subjectcounter = 1;	
				System.out.println("Class type Discount recognised.");
			}
		}
		for (int x = 0; x < geniusapptsyn.size(); x++) {  //This is a candidate for a name change
			if (input.contains(geniusapptsyn.get(x))) {   //This is a candidate for a name change
				classtype = theGeniusApptList;            //This is a candidate for a name change
				
				input = input.replace(geniusapptsyn.get(x), "<b>"+geniusapptsyn.get(x)+"</b>");
				
				subjectcounter = 1;	
				System.out.println("Class type Lending recognised.");
			}
		}
		
		if(subjectcounter == 0){
			for (int x = 0; x < recentobjectsyn.size(); x++) {  
				if (input.contains(recentobjectsyn.get(x))) {
					classtype = theRecentThing;
					
					input = input.replace(recentobjectsyn.get(x), "<b>"+recentobjectsyn.get(x)+"</b>");
					
					subjectcounter = 1;
					System.out.println("Class type recognised as"+recentobjectsyn.get(x));
				}
			}
		}

		// More than one subject in question + Library ...
		// "Does the Library has .. Subject 2 ?"

		System.out.println("subjectcounter = "+subjectcounter);

		for (int x = 0; x < applestoresyn.size(); x++) {  //This is a candidate for a name change

			if (input.contains(applestoresyn.get(x))) {   //This is a candidate for a name change

				// Problem: "How many Books does the Library have ?" -> classtype = Library
				// Solution:
				
				if (subjectcounter == 0) { // Library is the first subject in the question
					
					input = input.replace(applestoresyn.get(x), "<b>"+applestoresyn.get(x)+"</b>");
					
					classtype = theAppleStoreList;        //This is a candidate for a name change

					System.out.println("class type Apple Store recognised");		

				}
			}
		}

		// Compose Method call and generate answerVector

		if (questiontype == "amount") { // Number of Subject

			Integer numberof = Count(classtype);

			answer=("The number of "
					+ classtype.get(0).getClass().getSimpleName() + "s is "
					+ numberof + ".");

			Answered = 1; // An answer was given

		}

		if (questiontype == "list") { // List all Subjects of a kind

			answer=("You asked for the listing of all "
					+ classtype.get(0).getClass().getSimpleName() + "s. <br>"
					+ "We have the following "
					+ classtype.get(0).getClass().getSimpleName() + "s:"
					+ ListAll(classtype));
			Answered = 1; // An answer was given

		}

		if (questiontype == "checkfor") { // test for a certain Subject instance

			Vector<String> check = CheckFor(classtype, input);
			answer=(check.get(0));
			Answered = 1; // An answer was given
			if (check.size() > 1) {
				Currentitemofinterest = classtype.get(Integer.valueOf(check
						.get(1)));
				System.out.println("Classtype List = "
						+ classtype.getClass().getSimpleName());
				System.out.println("Index in Liste = "
						+ Integer.valueOf(check.get(1)));
				Currentindex = Integer.valueOf(check.get(1));
				theRecentThing.clear(); // Clear it before adding (changing) the
				// now recent thing
				theRecentThing.add(classtype.get(Currentindex));
			}
		}

		// Location Question in Pronomial form "Where can i find it"

		if (questiontype == "location") {   // We always expect a pronomial question to refer to the last
											// object questioned for

			answer=("You can find the "
					+ classtype.get(0).getClass().getSimpleName() + " " + "at "
					+ Location(classtype, input));

			Answered = 1; // An answer was given
		}

		if ((questiontype == "intent" && classtype == theProductList) 
				||(questiontype == "intent" && classtype == theRecentThing)) {

			// Can I lend the book or not (Can I lent "it" or not)
			answer=("You "+ BookAvailable(classtype, input));
			Answered = 1; // An answer was given
		}

		if (questiontype == "farewell") {       // Reply to a farewell
			
			answer=("You are welcome.");

			Answered = 1; // An answer was given
		}
		
		
		if (Answered == 0) { // No answer was given

			answer=("Sorry I didn't understand that.");
		}

		out.add(input);
		out.add(answer);
		
		return out;
	}

	// Methods to generate answers for the different kinds of Questions
	
	// Answer a question of the "Is a book or "it (meaning a book) available ?" kind

	public String BookAvailable(List thelist, String input) {

		boolean available =true;
		String answer ="";
		Product curbook = new Product();
		String productname="";

		if (thelist == theProductList) {                      //This is a candidate for a name change

			int counter = 0;

			//Identify which book is asked for 

			for (int i = 0; i < thelist.size(); i++) {

				curbook = (Product) thelist.get(i);         //This is a candidate for a name change

				if (input.contains(curbook.getProductname().toLowerCase())            //This is a candidate for a name change
						|| input.contains(curbook.getProducttype().toLowerCase())      //This is a candidate for a name change
						|| input.contains(curbook.getSerialnumber().toLowerCase())) {  //This is a candidate for a name change

					counter = i;

					Currentindex = counter;
					theRecentThing.clear(); 									//Clear it before adding (changing) the
					classtype = theProductList;                                    //This is a candidate for a name change
					theRecentThing.add(classtype.get(Currentindex));
					productname=curbook.getProductname();
										
					if (input.contains(curbook.getProductname().toLowerCase())){input = input.replace(curbook.getProductname().toLowerCase(), "<b>"+curbook.getProductname().toLowerCase()+"</b>");}          
					if (input.contains(curbook.getProducttype().toLowerCase())) {input = input.replace(curbook.getProducttype().toLowerCase(), "<b>"+curbook.getProducttype().toLowerCase()+"</b>");}     
					if (input.contains(curbook.getSerialnumber().toLowerCase())){input = input.replace(curbook.getSerialnumber().toLowerCase(), "<b>"+curbook.getSerialnumber().toLowerCase()+"</b>");}
										
					i = thelist.size() + 1; 									// force break
				}
			}
		}

		// maybe other way round or double 

		if (thelist == theRecentThing && theRecentThing.get(0) != null) {

			if (theRecentThing.get(0).getClass().getSimpleName()
					.toLowerCase().equals("book")) {                  //This is a candidate for a name change

				curbook = (Product) theRecentThing.get(0);               //This is a candidate for a name change		
				productname=curbook.getProductname();
			}
		}

		// check all lendings if they contain the books ISBN
		/**
		 * This needs editing - our code can look for Appointment Slots - not Products - may need to create a 
		 * Products in Store Class?
		 * 
		 * 
		for (int i = 0; i < theGeniusApptList.size(); i++) {

			GeniusAppt curlend = (GeniusAppt) theGeniusApptList.get(i);         //This is a candidate for a name change

			// If there is a lending with the books ISBN, the book is not available

			if ( curbook.getSerialnumber().toLowerCase().equals(curlend.getSerialnumber().toLowerCase())) {           //This is a candidate for a name change

				input = input.replace(curlend.getSerialnumber().toLowerCase(), "<b>"+curlend.getSerialnumber().toLowerCase()+"</b>");
				
				available=false;
				i = thelist.size() + 1; 									// force break
			}
		}

		if(available){
			answer="can lend the book.";
		}
		else{ 
			answer="cannot lend the book as someone else has lent it at the moment.";
		}

		URL = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http://en.wikipedia.org/wiki/"
				+ booktitle;
		System.out.println("URL = "+URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return(answer);

	}
	**/

	// Answer a question of the "How many ...." kind 
	
	public Integer Count(List thelist) { // List "thelist": List of Class Instances (e.g. theBookList)

		//URL = "http://en.wiktionary.org/wiki/"		

		URL = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http://en.wikipedia.org/wiki/"
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		System.out.println("URL = "+URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return thelist.size();
	}

	// Answer a question of the "What kind of..." kind
	
	public String ListAll(List thelist) {

		String listemall = "<ul>";

		if (thelist == theBookList) {                                  //This is a candidate for a name change
			for (int i = 0; i < thelist.size(); i++) {
				Book curbook = (Book) thelist.get(i);                  //This is a candidate for a name change
				listemall = listemall + "<li>" + (curbook.getTitle() + "</li>");    //This is a candidate for a name change
			}
		}

		if (thelist == theMemberList) {                                //This is a candidate for a name change
			for (int i = 0; i < thelist.size(); i++) {
				Member curmem = (Member) thelist.get(i);               //This is a candidate for a name change
				listemall = listemall + "<li>"                         //This is a candidate for a name change
						+ (curmem.getSurname() + " " + curmem.getLastname() + "</li>");  //This is a candidate for a name change
			}
		}

		if (thelist == theCatalogList) {                               //This is a candidate for a name change
			for (int i = 0; i < thelist.size(); i++) {
				Catalog curcat = (Catalog) thelist.get(i);             //This is a candidate for a name change
				listemall = listemall 
						+ "<li>" + (curcat.getName() + "</li>");      //This is a candidate for a name change
			}
		}
		
		if (thelist == theLendingList) {                               //This is a candidate for a name change
			for (int i = 0; i < thelist.size(); i++) {
				Lending curlend = (Lending) thelist.get(i);             //This is a candidate for a name change
				listemall = listemall + "<li>" 
						+ (curlend.getIsbn() + "</li>");                //This is a candidate for a name change
			}
		}
		
		listemall += "</ul>";

		URL = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http://en.wikipedia.org/wiki/"
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		System.out.println("URL = "+URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);
		
		return listemall;
	}

	// Answer a question of the "Do you have..." kind 
	
	public Vector<String> CheckFor(List thelist, String input) {

		Vector<String> yesorno = new Vector<String>();
		if (classtype.isEmpty()){
			yesorno.add("Class not recognised. Please specify if you are searching for a book, catalog, member, or lending?");
		} else {
			yesorno.add("No we don't have such a "
				+ classtype.get(0).getClass().getSimpleName());
		}

		Integer counter = 0;

		if (thelist == theBookList) {                         //This is a candidate for a name change

			for (int i = 0; i < thelist.size(); i++) {

				Book curbook = (Book) thelist.get(i);                           //This is a candidate for a name change

				if (input.contains(curbook.getTitle().toLowerCase())            //This is a candidate for a name change
						|| input.contains(curbook.getIsbn().toLowerCase())      //This is a candidate for a name change
						|| input.contains(curbook.getAutor().toLowerCase())) {  //This is a candidate for a name change

					counter = i;
					yesorno.set(0, "Yes we have such a Book");                  //This is a candidate for a name change
					yesorno.add(counter.toString());
					i = thelist.size() + 1; // force break
				}
			}
		}

		if (thelist == theMemberList) {                                      //This is a candidate for a name change
			for (int i = 0; i < thelist.size(); i++) {
				Member curmem = (Member) thelist.get(i);                      //This is a candidate for a name change
				if (input.contains(curmem.getSurname().toLowerCase())         //This is a candidate for a name change
						|| input.contains(curmem.getLastname().toLowerCase()) //This is a candidate for a name change
						|| input.contains(curmem.getCity().toLowerCase())) {  //This is a candidate for a name change

					counter = i;
					yesorno.set(0, "Yes we have such a Member");               //This is a candidate for a name change
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}

		if (thelist == theCatalogList) {                                    //This is a candidate for a name change
			for (int i = 0; i < thelist.size(); i++) {
				Catalog curcat = (Catalog) thelist.get(i);                  //This is a candidate for a name change
				if (input.contains(curcat.getName().toLowerCase())          //This is a candidate for a name change
						|| input.contains(curcat.getUrl().toLowerCase())) { //This is a candidate for a name change

					counter = i;
					yesorno.set(0, "Yes we have such a Catalog");           //This is a candidate for a name change
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}
		
		if (thelist == theLendingList) {                                     //This is a candidate for a name change
			for (int i = 0; i < thelist.size(); i++) {
				Lending curlend = (Lending) thelist.get(i);                  //This is a candidate for a name change
				if (input.contains(curlend.getIsbn().toLowerCase())          //This is a candidate for a name change
					|| input.contains(curlend.getMemberid().toLowerCase())){ //This is a candidate for a name change

					counter = i;
					yesorno.set(0, "Yes we have such a Lending");            //This is a candidate for a name change
					yesorno.add(counter.toString());
					i = thelist.size() + 1;
				}
			}
		}

		if (classtype.isEmpty()) {
			System.out.println("Not class type given.");
		} else {
			URL = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
			URL2 = "http://en.wikipedia.org/wiki/"
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
			System.out.println("URL = "+URL);
			tooltipstring = readwebsite(URL);
			String html = "<html>" + tooltipstring + "</html>";
			Myface.setmytooltip(html);
			Myface.setmyinfobox(URL2);
		}
	
		return yesorno;
	}

	//  Method to retrieve the location information from the object (Where is...) kind

	public String Location(List classtypelist, String input) {

		List thelist = classtypelist;
		String location = "";

		// if a pronomial was used "it", "them" etc: Reference to the recent thing

		if (thelist == theRecentThing && theRecentThing.get(0) != null) {

			if (theRecentThing.get(0).getClass().getSimpleName()
					.toLowerCase().equals("book")) {                  //This is a candidate for a name change

				Book curbook = (Book) theRecentThing.get(0);          //This is a candidate for a name change
				location = (curbook.getLocation() + " ");             //This is a candidate for a name change

			}

			if (theRecentThing.get(0).getClass().getSimpleName()
					.toLowerCase().equals("member")) {               //This is a candidate for a name change

				Member curmem = (Member) theRecentThing.get(0);      //This is a candidate for a name change
				location = (curmem.getCity() + " " + curmem.getStreet() + " " + curmem  //This is a candidate for a name change
						.getHousenumber());                                    //This is a candidate for a name change

			}

			if (theRecentThing.get(0).getClass().getSimpleName()  
					.toLowerCase().equals("catalog")) {                 //This is a candidate for a name change

				Catalog curcat = (Catalog) theRecentThing.get(0);       //This is a candidate for a name change
				location = (curcat.getLocation() + " ");                //This is a candidate for a name change

			}

			if (theRecentThing.get(0).getClass().getSimpleName()    
					.toLowerCase().equals("library")) {                  //This is a candidate for a name change

				location = (thelibrary.getCity() + " " + thelibrary.getStreet() + thelibrary   //This is a candidate for a name change
						.getHousenumber());                                           //This is a candidate for a name change
			}

		}

		// if a direct noun was used (book, member, etc)

		else {

			if (thelist == theBookList) {                         //This is a candidate for a name change

				int counter = 0;

				for (int i = 0; i < thelist.size(); i++) {

					Book curbook = (Book) thelist.get(i);         //This is a candidate for a name change

					if (input.contains(curbook.getTitle().toLowerCase())            //This is a candidate for a name change
							|| input.contains(curbook.getIsbn().toLowerCase())      //This is a candidate for a name change
							|| input.contains(curbook.getAutor().toLowerCase())) {  //This is a candidate for a name change

						counter = i;
						location = (curbook.getLocation() + " ");
						Currentindex = counter;
						theRecentThing.clear(); 									// Clear it before adding (changing) theRecentThing
						classtype = theBookList;                                    //This is a candidate for a name change
						theRecentThing.add(classtype.get(Currentindex));
						i = thelist.size() + 1; 									// force break
					}
				}
			}

			if (thelist == theMemberList) {                                         //This is a candidate for a name change

				int counter = 0;

				for (int i = 0; i < thelist.size(); i++) {

					Member curmember = (Member) thelist.get(i);         				  //This is a candidate for a name change

					if (input.contains(curmember.getSurname().toLowerCase())              //This is a candidate for a name change
							|| input.contains(curmember.getLastname().toLowerCase())      //This is a candidate for a name change
							|| input.contains(curmember.getMemberid().toLowerCase())) {   //This is a candidate for a name change

						counter = i;
						location = (curmember.getCity() + " ");
						Currentindex = counter;
						theRecentThing.clear(); 										// Clear it before adding (changing) the
						classtype = theMemberList;            	 						//This is a candidate for a name change
						theRecentThing.add(classtype.get(Currentindex));
						i = thelist.size() + 1; 				             	        // force break
					}
				}
			}

			if (thelist == theCatalogList) {                                       	 //This is a candidate for a name change

				int counter = 0;

				for (int i = 0; i < thelist.size(); i++) {

					Catalog curcatalog = (Catalog) thelist.get(i);                    //This is a candidate for a name change

					if (input.contains(curcatalog.getName().toLowerCase())            //This is a candidate for a name change						     
							|| input.contains(curcatalog.getUrl().toLowerCase())) {   //This is a candidate for a name change

						counter = i;
						location = (curcatalog.getLocation() + " ");
						Currentindex = counter;
						theRecentThing.clear();                                      // Clear it before adding (changing) the	
						classtype = theCatalogList;                                  //This is a candidate for a name change
						theRecentThing.add(classtype.get(Currentindex));
						i = thelist.size() + 1;                                      // force break
					}
				}
			}

			if (thelist == theLibraryList) {                                                  //This is a candidate for a name change

				location = (thelibrary.getCity() + " " + thelibrary.getStreet() + thelibrary  //This is a candidate for a name change
						.getHousenumber());                                                   //This is a candidate for a name change
			}
		}

		URL = "http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s="
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		URL2 = "http://en.wikipedia.org/wiki/"
				+ classtype.get(0).getClass().getSimpleName().toLowerCase();
		System.out.println("URL = "+URL);
		tooltipstring = readwebsite(URL);
		String html = "<html>" + tooltipstring + "</html>";
		Myface.setmytooltip(html);
		Myface.setmyinfobox(URL2);

		return location;
	}

	public String testit() {   // test the loaded knowledge by querying for books written by dostoyjewski

		String answer = "";

		System.out.println("Book List = " + theBookList.size());  //This is a candidate for a name change

		for (int i = 0; i < theBookList.size(); i++) {   // check each book in the List, //This is a candidate for a name change

			Book curbook = (Book) theBookList.get(i);    // cast list element to Book Class //This is a candidate for a name change												
			System.out.println("Testing Book" + curbook.getAutor());

			if (curbook.getAutor().equalsIgnoreCase("dostoyjewski")) {     // check for the author //This is a candidate for a name change

				answer = "A book written by " + curbook.getAutor() + "\n"  //This is a candidate for a name change
						+ " is for example the classic " + curbook.getTitle()      //This is a candidate for a name change
						+ ".";
			}
		}
		return answer;
	}

	public String readwebsite(String url) {

		String webtext = "";
		try {
			BufferedReader readit = new BufferedReader(new InputStreamReader(
					new URL(url).openStream()));

			String lineread = readit.readLine();

			System.out.println("Reader okay");

			while (lineread != null) {
				webtext = webtext + lineread;
				lineread = readit.readLine();				
			}

			// Hard coded cut out from "wordnet website source text": 
			//Check if website still has this structure   vvvv ...definitions...  vvvv 		
			
			webtext = webtext.substring(webtext.indexOf("<ul>"),webtext.indexOf("</ul>"));                                 //               ^^^^^^^^^^^^^^^^^              

			webtext = "<table width=\"700\"><tr><td>" + webtext
					+ "</ul></td></tr></table>";

		} catch (Exception e) {
			webtext = "Not yet";
			System.out.println("Error connecting to wordnet");
		}
		return webtext;
	}
}
