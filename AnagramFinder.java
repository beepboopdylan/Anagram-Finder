import java.io.*;
import java.util.Iterator;

/**
 * A command-line anagram finder that takes user input and
 * searches an inputted dictionary file to return words that
 * are anagrams. User can also specify whether to use a BST,
 * AVL, or HashMap to carry out this algorithm.
 * java AnagramFinder <word> <dictionary file> <bst|avl|hash>
 *
 * @author Dylan Tran
 * @version 1.0 December 17, 2023
 */

public class AnagramFinder {
    public static void main(String[] args) throws IOException {
        //First Step: Validating Input Arguments
        if (args.length != 3) {
            System.err.print("Usage: java AnagramFinder <word> <dictionary file> <bst|avl|hash>"+"\n");
            System.exit(1);
        }
        File dictionary = new File(args[1]);
        if (!(args[2].equals("bst") || args[2].equals("avl") || args[2].equals("hash"))) {
            System.err.print("Error: Invalid data structure '" + args[2] + "' received."+"\n");
            System.exit(1);
        }
        //Second Step: Parsing dictionary into data structure!
        if (args[2].equals("bst")) {
            //load all words in the dictionary into the binary search tree
            MyMap<String, MyList<String>> map = new BSTMap<>();
            try {
                //Source: https://www.baeldung.com/java-buffered-reader
                BufferedReader it = new BufferedReader(new FileReader(dictionary));
                String data = it.readLine(); //read the line
                while (data != null) {
                    String sorted = insertionSort(data.toLowerCase()); //sort the line
                    MyList<String> val = map.get(sorted);
                    if (val == null) { //if not there
                        val = new MyLinkedList<>(); //make new MyLinkedList
                    }
                    val.add(data); //add the word to that value of that key
                    map.put(sorted, val); //place it into the tree
                    data = it.readLine(); //iterate to next line
                }
                it.close();
            } catch (FileNotFoundException e) {
                System.err.print("Error: Cannot open file '" + args[1] + "' for input."+"\n");
                System.exit(1);
            } catch (IOException e) { 
                System.err.print("Error: An I/O error occurred reading '" + args[1] + "'."+"\n");
                System.exit(1);
            }
            MyList<String> values = map.get(insertionSort(args[0].toLowerCase())); //turn word into default form to search
            if (values == null) { //if value is null, it means that there are no anagrams
                System.out.print("No anagrams found."+"\n");
            } else {
                Iterator<String> link = values.iterator(); //use iterator
                String[] array = new String[values.size()]; //make new array
                int index = 0;
                while (link.hasNext()) { //copy everything over to an array
                    String word = link.next();
                    array[index] = word;
                    index++;
                }
                insertionSort(array); //sort in lexigraphical order
                for (String i : array) {
                    if (!i.equals(args[0])) {
                        System.out.print(i+"\n");
                    } else if (array.length == 1 && i.equals(args[0])) {
                        System.out.print("No anagrams found."+"\n");
                    }
                }
            }
        } else if (args[2].equals("avl")) { //same code as before, just with new map 
            MyMap<String,MyList<String>> map = new AVLTreeMap<>();
            try {
                BufferedReader it = new BufferedReader(new FileReader(dictionary));
                String data = it.readLine();
                while (data != null) {
                    String sorted = insertionSort(data.toLowerCase()); //sorted
                    MyList<String> val = map.get(sorted);
                    if (val == null) { 
                        val = new MyLinkedList<>();
                    }
                    val.add(data);
                    map.put(sorted, val);
                    data = it.readLine();
                }
                it.close();
            } catch (FileNotFoundException e) {
                System.err.print("Error: Cannot open file '" + args[1] + "' for input."+"\n");
                System.exit(1);
            } catch (IOException e) {
                System.err.print("Error: An I/O error occurred reading '" + args[1] + "'."+"\n");
                System.exit(1);
            }
            MyList<String> values = map.get(insertionSort(args[0].toLowerCase()));
            if (values == null) {
                System.out.print("No anagrams found."+"\n");
            } else {
                Iterator<String> link = values.iterator();
                String[] array = new String[values.size()];
                int index = 0;
                while (link.hasNext()) {
                    String word = link.next();
                    array[index] = word;
                    index++;
                }
                insertionSort(array);
                for (String i : array) {
                    if (!i.equals(args[0])) {
                        System.out.print(i+"\n");
                    } else if (array.length == 1 && i.equals(args[0])) {
                        System.out.print("No anagrams found."+"\n");
                    }
                }
            }
        } else {
            MyMap<String,MyList<String>> map = new MyHashMap<>();
            try {
                BufferedReader it = new BufferedReader(new FileReader(dictionary));
                String data = it.readLine();
                while (data != null) { //iterate through data
                    String sorted = insertionSort(data.toLowerCase()); //sorted
                    MyList<String> val = map.get(sorted);
                    if (val == null) { //if not there
                        val = new MyLinkedList<>(); //make new MyLinkedList
                    }
                    val.add(data);
                    map.put(sorted, val);
                    data = it.readLine();
                }
                it.close();
            } catch (FileNotFoundException e) {
                System.err.print("Error: Cannot open file '" + args[1] + "' for input."+"\n");
                System.exit(1);
            } catch (IOException e) { //handle I/O exception
                System.err.print("Error: An I/O error occurred reading '" + args[1] + "'."+"\n");
                System.exit(1);
            }
            MyList<String> values = map.get(insertionSort(args[0].toLowerCase()));
            if (values == null) {
                System.out.print("No anagrams found."+"\n");
            } else {
                Iterator<String> link = values.iterator(); //use iterator
                String[] array = new String[values.size()];
                int index = 0;
                while (link.hasNext()) {
                    String word = link.next();
                    array[index] = word;
                    index++;
                }
                insertionSort(array); //sort in lexigraphical order
                for (String i : array) {
                    if (!i.equals(args[0])) {
                        System.out.print(i+"\n");
                    } else if (array.length == 1 && i.equals(args[0])) {
                        System.out.print("No anagrams found."+"\n");
                    }
                }
            }
        }
    }

   /**
     * Returns String that has been sorted using Insertion Sort by
     * first splicing it into an array of chars, performing the
     * sorting algorithm on it, and merged back into a String.
     * @param String to be sorted
     * @return String sorted using Insertion Sort
     */
    private static String insertionSort(String s){ //must receive lower case
        char[] arr = s.toCharArray();
        for (int i = 1; i < arr.length; ++i) {
            int k;
            char current = arr[i];
            for (k = i - 1; k >= 0 && arr[k] > current; --k) {
                arr[k + 1] = arr[k];
            }
            arr[k + 1] = current;
        }
        return String.valueOf(arr); //turn back into string
    }

   /**
     * Returns array of Strings that has been sorted using Insertion Sort
     * @param array of Strings to be sorted
     */
    private static void insertionSort(String[] arr) {
        for (int i = 1; i < arr.length; ++i) {
            int k;
            String current = arr[i];
            for (k = i - 1; k >= 0 && arr[k].compareTo(current)>0; --k) {
                arr[k + 1] = arr[k]; //use compareTo() to sort
            }
            arr[k + 1] = current;
        }
    }
}