// Jia-Jia Lin
// CSE 143 AP with Zachary Chun
// Homework 5
// A GrammarSolver class reads an input file with Backus-Naur Form and
// randomly generates sentences based off the file contents.

import java.util.*;

public class GrammarSolver
{
   private SortedMap<String, String[]> grammarMap; 
   
   // Pre: Takes in a list of Strings representing a grammar.
   // Post: Stores the grammar without modifying it.
   //       Throws an IllegalArgumentException if the list is
   //       empty or if there are two or more entries (case-sensitive)
   //       in the grammar for the same nonterminal. 
   public GrammarSolver(List<String> grammar)
   {
      if (grammar.isEmpty())
      {
         throw new IllegalArgumentException("Grammar is empty.");
      }
      grammarMap = new TreeMap<String, String[]>();
      for (String line: grammar)
      {
         String[] parts = line.split("::=");
         if (grammarMap.containsKey(parts[0]))
         {
            throw new IllegalArgumentException("Already in the grammar.");
         }
         grammarMap.put(parts[0], parts[1].split("\\|")); 
      }
   }
   
   // Pre: Takes in a String representing a symbol.
   // Post: Returns true if the symbol is a nonterminal. Returns 
   //       false otherwise.
   public boolean grammarContains(String symbol)
   {
      return grammarMap.containsKey(symbol);
   }
   
   // Pre: Takes in a String representing a non-terminal and an int 
   //      representing the number of times it occurs.
   // Post: Returns a randomly generated array of strings.
   //       Throws an IllegalArgumentException if the grammar does not
   //       contain the given non-terminal or if the given number of times
   //       is less than zero.
   public String[] generate(String symbol, int times)
   {
      if (times < 0 || !grammarContains(symbol))
      {
         throw new IllegalArgumentException("");
      }
      String[] generatedArray = new String[times];
      for (int i = 0; i < times; i++)
      {
         generatedArray[i] = produceRandomString(grammarMap.get(symbol)).trim() + " ";
      }
      return generatedArray;
   }
   
   // Pre: Takes in a String array representing a grammar rule.
   // Post: Returns a randomly generated string.
   private String produceRandomString(String[] rules)
   {
      int random = (int) (Math.random()*(rules.length) - 0.5);
      String rule = rules[random];
      if (!grammarContains(rule.trim()) && !rule.trim().contains(" "))  
      {
         return rule.trim();
      }
      String result = "";
      String[] subRules = rule.split("\\s+"); 
      for (int i = 0; i < subRules.length-1; i++)
      {
         if (grammarContains(subRules[i]))
         {
            result += produceRandomString(grammarMap.get(subRules[i])).trim() + " ";
         }
         else
         {
            result += subRules[i].trim() + " ";
         }
      }
      if (grammarContains(subRules[subRules.length-1].trim()))
      {
         result += produceRandomString(grammarMap.get(subRules[subRules.length-1])).trim();
      }
      else
      {
         result += subRules[subRules.length-1].trim();
      }
      return result;
   }
   
   // Post: Returns a String of sorted and comma-separated nonterminal 
   //       symbols enclosed in square brackets.
   public String getSymbols()
   {
      return grammarMap.keySet().toString();
   }
}
