package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class EvilHangman implements hangman.IEvilHangmanGame
{
	private HashSet<String> myHash;
	private StringBuilder guessedLetters;
	
	public EvilHangman()
	{
		myHash = new HashSet<String>();
		guessedLetters = new StringBuilder();
	}

	@Override
	public void startGame(File dictionary, int wordLength) throws FileNotFoundException 
	{
		FileReader read = new FileReader(dictionary);
		Scanner scan = new Scanner(read);
		scan.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)");
		while(scan.hasNext())
		{
			String newWord = scan.next();
			newWord.toLowerCase();
			if(newWord.length() == wordLength)
			{
				if(!myHash.contains(newWord))
				{
					myHash.add(newWord);
				}
			}
		}
		scan.close();
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException 
	{
		TreeMap<String,Set<String>> myMap = new TreeMap<String,Set<String>>();
		StringBuilder sb = new StringBuilder();
		sb.append(guess);
		if(guessedLetters.toString().contains(sb))
		{
			throw new GuessAlreadyMadeException();
		}
		guessedLetters.append(guess);
		Iterator<String> it = myHash.iterator();
	    while(it.hasNext())
	    {
	    	String currentWord = it.next();
	    	String myPattern = new String(makePattern(currentWord,guess));
	    	if(!myMap.containsKey(myPattern))
	    	{
	    		myMap.put(myPattern, new HashSet<String>());
	    	}
    		myMap.get(makePattern(currentWord,guess)).add(currentWord);
	    }
	    int mostWords = 0;
	    Set<String> best = null;
	    Set<String> keys = myMap.keySet();
		Iterator<String> it_myMap = keys.iterator();
	    while(it_myMap.hasNext())
	    {
	    	Set<String> currentSet = myMap.get(it_myMap.next());
	    	if(currentSet.size() > mostWords)
	    	{
	    		best = currentSet;
	    		mostWords = currentSet.size();
	    	}	
	    }
	    myHash = (HashSet<String>) best;
		return best;
	}
	
	private String makePattern(String word, char guess)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < word.length(); i++)
		{
			if(word.charAt(i) == guess)
			{
				sb.append(guess);
			}
			else
			{
				sb.append('-');
			}
		}
		return sb.toString();
	}
}