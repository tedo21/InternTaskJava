import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainClass {

	private static Scanner in;


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	//Call check function
	checkAndCreateDictionary();
		
	//Display menu
	System.out.println("Hangman");
	System.out.println("Welcome to the hangman game. Please select a category:");
	System.out.println("1.Football teams");
	System.out.println("2.Books");
	System.out.println("3.Programming principles");
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    int selectionNumber = 0;
    
    //Validate user selection
    try {
    	while (selectionNumber < 1 || selectionNumber > 3){
    		System.out.print("Please enter category number: ");
    		String input = in.readLine();
    		if (input.matches("1") || input.matches("2") || input.matches("3")){
				selectionNumber = Integer.parseInt(input);
    		}else{
    			System.out.println("Error! Invalid number!");
    		}
    	}
	} catch (NumberFormatException | IOException e) {
		System.out.print("Invalid input! Please type in a number!");
	}
    
    //Initialize score and start game. Keep playing until user lost.
    int score = 0;
    boolean wonGame = playGame(selectionNumber);
	    while(wonGame){
	    	score++;
	    	selectionNumber = 0;
	    	System.out.println("\nScore: " + String.valueOf(score));
	    	System.out.println("Well done! Please choose category for the next round:");
	    	System.out.println("1.Football teams");
	    	System.out.println("2.Books");
	    	System.out.println("3.Programming principles");
	    	try {
	        	while (selectionNumber < 1 || selectionNumber > 3){
	        		System.out.print("Please enter category number: ");
	        		String input = in.readLine();
	        		if (input.matches("1") || input.matches("2") || input.matches("3")){
	    				selectionNumber = Integer.parseInt(input);
	        		}else{
	        			System.out.println("Error! Invalid number!");
	        		}
	        	}
	    	} catch (NumberFormatException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (IOException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	//Start game again
	    	wonGame = playGame(selectionNumber);
	    }
    	System.out.println("\nScore: " + String.valueOf(score));
	}
	
	//Check if dictionary exists and if it doesn't, create it.
	static void checkAndCreateDictionary(){
		File dictionary = new File("dictionary.txt");
		if (!dictionary.exists()){
			try (PrintWriter out = new PrintWriter("dictionary.txt")){
				out.println("_Football teams");
				out.println("Manchester United");
				out.println("Arsenal");
				out.println("Chelsea");
				out.println("Liverpool");
				out.println("FC Bayern Munchen");
				out.println("Juventus");
				out.println("Real Madrid");
				out.println("FC Barcelona");
				out.println("Olympique de Marseille");
				out.println("Paris Saint-Germain");
				out.println("_Books");
				out.println("In Search of Lost Time");
				out.println("Don Quixote");
				out.println("The Great Gatsby");
				out.println("Moby Dick");
				out.println("Hamlet");
				out.println("War and Peace");
				out.println("The Odyssey");
				out.println("_Programming principles");
				out.println("Encapsulation");
				out.println("Abstraction");
				out.println("Inheritance");
				out.println("Polymorphism");
				out.println("KISS");
				out.println("DRY");
				out.println("Open Closed");
				out.println("Single Responsibility");
				out.println("Composition over inheritance");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//Start game, initialize words and return result from round, whether user won or not.
	static boolean playGame(int category){
	
		//get dictionary and words, based on user's selection
		File dictionary = new File("dictionary.txt");
		int count = 0; //get words count
		Scanner scanner;
		ArrayList<String> categoryWordsList = new ArrayList<String>();
		ArrayList<String> lettersUsed = new ArrayList<String>();
		categoryWordsList.clear();
		lettersUsed.clear();
		//clear all lists
		try {
			scanner = new Scanner(dictionary);
			System.out.print("\nCategory: ");
			String categoryWordFootballTeams = "_Football teams";
			String categoryWordBooks = "_Books";
			String categoryWordProgrammingPrinciples = "_Programming principles";
			//distinguish category by name
			boolean categoryCorrect = false; // determine if this is the right category
			boolean categoryDisplayed = false;  // determine if category has been displayed to user once throughout cycle.
			while (scanner.hasNextLine()) {
				String token = scanner.nextLine();
				switch (category){
					case 1:
						if (!categoryDisplayed){
							System.out.println("Football Teams");
							categoryDisplayed = true;
						}
						if (token.equalsIgnoreCase(categoryWordFootballTeams)){
							categoryCorrect = true; // confirm this is the right category, same method for other 2 cases.
						}else if (token.equalsIgnoreCase(categoryWordBooks) ||
								token.equalsIgnoreCase(categoryWordProgrammingPrinciples)){
							categoryCorrect = false; // confirm this is not the right category, same as other 2 cases.
						}
						if (categoryCorrect && 
								!(token.equalsIgnoreCase(categoryWordFootballTeams))){
							count++; // if this is the right category add word to the words list from which to choose randomly later on. Expand count size.
							categoryWordsList.add(token);
						}
					break;
					case 2:
						if (!categoryDisplayed){
							System.out.println("Books");
							categoryDisplayed = true;
						}
						if (token.equalsIgnoreCase(categoryWordBooks)){
							categoryCorrect = true;
						}else if (token.equalsIgnoreCase(categoryWordFootballTeams) ||
								token.equalsIgnoreCase(categoryWordProgrammingPrinciples)){
							categoryCorrect = false;
						}
						if (categoryCorrect && 
								!(token.equalsIgnoreCase(categoryWordBooks))){
							count++;
							categoryWordsList.add(token);
						}
						break;
					case 3:
						if (!categoryDisplayed){
							System.out.println("Programming principles");
							categoryDisplayed = true;
						}
						if (token.equalsIgnoreCase(categoryWordProgrammingPrinciples)){
							categoryCorrect = true;
						}else if (token.equalsIgnoreCase(categoryWordBooks) ||
								token.equalsIgnoreCase(categoryWordProgrammingPrinciples)){
							categoryCorrect = false;
						}
						if (categoryCorrect && 
								!(token.equalsIgnoreCase(categoryWordProgrammingPrinciples))){
							count++;
							categoryWordsList.add(token);
						}
						break;
				}
			}
			categoryCorrect = false; // finish cycle, restore default values.
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error! Could not find dictionary!");
			e.printStackTrace();
		}
		
		Random rand = new Random(); // Generate random numbers
		int  n = rand.nextInt(count); //Based on word count size
		String wordToGuess = categoryWordsList.get(n); //Pick random word or phrase and memorize it
		String hiddenWord = wordToGuess; // Get word in another var and hide it
		hiddenWord = hiddenWord.replaceAll("[a-zA-Z]", "_"); // ...
	
		
		boolean gameWon = false; //Initialize bool to help determine if game has been won or not.
		int attempts = 10;
		boolean doubleLetter=false; // Confirm if there are 2 letters next to each other
		boolean repeatLetter = false; // Determine if letter has been guessed before or not.
		String letterRepeating = null; // Store current letter which is being repeated
		for (int j = 0; j < wordToGuess.length()-1; j++) {
            if (wordToGuess.charAt(j) == wordToGuess.charAt(j+1)) {
            	doubleLetter=true; // Determine that there are 2 of the same letters next to one another
            	letterRepeating = String.valueOf(wordToGuess.charAt(j)); // Save letter that is repeating itself
                break;
            }
        }
		
		while (!gameWon && attempts > 0){ // While the game has not ended or was lost Display available attempts
		    System.out.println("\nAttempts Left: " + String.valueOf(attempts));
		    //System.out.println("Actual word/phrase: " + wordToGuess); // Test code to check what actual word is
		    System.out.println("Current word/phrase: " + hiddenWord.replaceAll(".", "$0 ")); // Add spacing to letters
		    System.out.print("Please enter a letter: "); // Input request
		    in = new Scanner (System.in);
		    char inLetter = in.next().charAt(0);
		    String letter = String.valueOf(inLetter); // Get letter and check if it has been already input
		    for (int i=0; i<lettersUsed.size(); i++){
		    	if (letter.equals(lettersUsed.get(i))){
		    		repeatLetter = true;
		    	}
		    }
		    if (!repeatLetter){ // if not, carry on with game and check if the word user is trying to guess, contains it.
		    if (wordToGuess.contains(letter)){ // If it does, make sure capitalization of the letter does not prevent user to make a good guess of a letter.
		    	for (int i = -1; (i = wordToGuess.indexOf(letter, i + 1)) != -1; i++) {
		    	    hiddenWord = hiddenWord.substring(0,i) + letter + hiddenWord.substring(i+1);
		    	    if (doubleLetter &&
		    	    		(letter.toLowerCase().equals(letterRepeating) || 
		    	    				letter.toUpperCase().equals(letterRepeating) )){
		    	    	hiddenWord = hiddenWord.substring(0,i+1) + letter + hiddenWord.substring(i+2);
		    	    	letterRepeating = null;
		    	    	doubleLetter=false;
		    	    }
		    	}
		    	if (wordToGuess.contains(letter.toUpperCase())) {
			    	for (int i = -1; (i = wordToGuess.indexOf(letter.toUpperCase(), i + 1)) != -1; i++) {
			    	    hiddenWord = hiddenWord.substring(0,i) + letter.toUpperCase() + hiddenWord.substring(i+1);
			    	    if (doubleLetter &&
			    	    		(letter.toLowerCase().equals(letterRepeating) || 
			    	    				letter.toUpperCase().equals(letterRepeating) )){
			    	    	hiddenWord = hiddenWord.substring(0,i+1) + letter.toUpperCase() + hiddenWord.substring(i+2);
			    	    	letterRepeating = null;
			    	    	doubleLetter=false;
			    	    }
			    	}
		    	}
			    if (hiddenWord.matches(wordToGuess)){ // If the word has been revealed, then the game is won, and the user can go to the next round.
			    	gameWon = true;
			    }
		    }else if (wordToGuess.contains(letter.toUpperCase())) { // Check capitalization again.
		    	for (int i = -1; (i = wordToGuess.indexOf(letter.toUpperCase(), i + 1)) != -1; i++) {
		    	    hiddenWord = hiddenWord.substring(0,i) + letter.toUpperCase() + hiddenWord.substring(i+1);
		    	    if (doubleLetter &&
		    	    		(letter.toLowerCase().equals(letterRepeating) || 
		    	    				letter.toUpperCase().equals(letterRepeating) )){
		    	    	hiddenWord = hiddenWord.substring(0,i+1) + letter.toUpperCase() + hiddenWord.substring(i+2);
		    	    	letterRepeating = null;
		    	    	doubleLetter=false;
		    	    }
		    	}
		    	if (wordToGuess.contains(letter.toLowerCase())) { // and again
			    	for (int i = -1; (i = wordToGuess.indexOf(letter.toLowerCase(), i + 1)) != -1; i++) {
			    	    hiddenWord = hiddenWord.substring(0,i) + letter.toLowerCase() + hiddenWord.substring(i+1);
			    	    if (doubleLetter &&
			    	    		(letter.toLowerCase().equals(letterRepeating) || 
			    	    				letter.toUpperCase().equals(letterRepeating) )){
			    	    	hiddenWord = hiddenWord.substring(0,i+1) + letter.toLowerCase() + hiddenWord.substring(i+2);
			    	    	letterRepeating = null;
			    	    	doubleLetter=false;
			    	    }
			    	}
		    	}
			    if (hiddenWord.matches(wordToGuess)){
			    	gameWon = true;
			    }
		    }else{ // And if user made a bad guess, then take off one attempt and let him know he was wrong.
		    	System.out.println("\nWrong!");
		    	attempts = attempts - 1;
		    }
		    String letterUpperCase = letter.toUpperCase(); // Add letters to a list of used letters, lower case and capitals for check of usage.
		    if (!letter.equals(letterUpperCase)){
		    	lettersUsed.add(letter);
			    lettersUsed.add(letterUpperCase);
		    }else{
		    	lettersUsed.add(letter.toLowerCase()); // This means user used a capital letter and it needs to be diminished.
			    lettersUsed.add(letterUpperCase);
			}
	    }else{
	    	System.out.println("You have already used this letter. Try again!");
	    	repeatLetter = false;
	    }
		} 
	    if (gameWon){ // The game has been won, notify main that the user won
	    	System.out.println("\nCongratulations! You won!");
	    	System.out.println("Word/phrase: " + wordToGuess);
	    	return true;
	    }else{ // The game has been lost, notify main that the user lost
	    	System.out.println("\nSorry! You lost.");
	    	System.out.println("The word/phrase was: " + wordToGuess);
	    	return false;
	    }
		
	}
}