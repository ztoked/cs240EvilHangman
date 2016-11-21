package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;

import hangman.IEvilHangmanGame.GuessAlreadyMadeException;


public class Main 
{
	public static void main(String[] args) throws IOException, GuessAlreadyMadeException
	{
		
		String dictionaryName = args[0];
		int wordLength = Integer.parseInt(args[1]);
		int guesses = Integer.parseInt(args[2]);
		File dictionary = new File(dictionaryName);
		EvilHangman game = new EvilHangman();
		game.startGame(dictionary, wordLength);
		
		StringBuilder usedLetters = new StringBuilder();
		StringBuilder word = new StringBuilder();
		for(int i = 0; i < wordLength; i++)
		{
			word.append("-");
		}
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(int i = guesses; i > 0; i--)
		{
			System.out.println("You have " + i + " guesses left");
			System.out.print("Used Letters: ");
			for (int j = 0; j < usedLetters.length(); j++)
			{
				System.out.print(usedLetters.charAt(j) + " ");
			}
			System.out.println("\nWord: " + word);
			System.out.print("Enter guess: ");
			String input = br.readLine().toLowerCase();
			while(input.length() > 1||!alpha.contains(input)||usedLetters.toString().contains(input))
			{
				System.out.println("INVALID INPUT");
				System.out.print("Enter guess: ");
				input = br.readLine().toLowerCase();
			}
			Set<String> currentSet = game.makeGuess(input.charAt(0));
			Iterator<String> it = currentSet.iterator();
		    while(it.hasNext())
		    {
		    	String currentWord = it.next();
		    	for (int j = 0; j < currentWord.length(); j++)
				{
		    		if(currentWord.charAt(j) == input.charAt(0))
		    		{
		    			word.setCharAt(j, input.charAt(0));
		    		}
				}
		    }
			usedLetters.append(input);
			System.out.println("\n");
			int count = 0;
			for(int j = 0; j < word.length(); j++)
			{
				if(word.charAt(j) == '-')
				{
					count++;
				}
			}
			if(count == 0)
			{
				System.out.println("YOU WON! I guess it wasn't too evil");
				System.out.print("Your word was: " + word);
				break;
			}
		}
	}
}
