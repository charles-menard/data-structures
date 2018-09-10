/**
 * @author R�my Lapointe - 20073467
 * @author Charles M�nard - 984594
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import java.util.PriorityQueue;
import java.util.ArrayDeque;

class Node implements Comparable<Node> {

	private char symbol; //caract�re ASCII
	private int frequency; //Nombre d'apparitions du caract�re

	public Node left;
	public Node right;

    // Leaf Node
    Node(char symbol, int frequency) {

    	this.symbol = symbol;
    	this.frequency = frequency;
    }

    // Internal Node
    Node(Node left, Node right) {

    	this.left = left;
    	this.right = right;
    	int addLeft = 0; //fr�quences des noeuds enfants
    	int addRight = 0;

    	if (left != null) {
    		addLeft = left.getFrequency();
    	}
    	if (right != null) {
    		addRight = right.getFrequency();
    	}
    	
    	this.frequency = addLeft + addRight; //combiner les fr�quences des enfants
    }

    boolean isLeaf() {

    	if (left == null && right == null) {
    		return true;
    	} else {
    		return false;
    	}
    }

    public char getSymbol(){
        return symbol;
    }

    @Override
    public int compareTo(Node node) {

    	if (frequency < node.getFrequency()) {
    		return -1;
    	} else if (frequency > node.getFrequency()){
    		return 1;
    	} else {
    		return 0; //si les deux sont �gaux
    	}
    }

    @Override
    public String toString() {
    	
    	if (isLeaf()) {
    	return	this.hashCode() + " [label=\"{{" + "'" + symbol + "'" + "|" + frequency+ "}}\", shape=record]";
    	}
    	else {
    		
    		String fullString = this.hashCode() + " [label=" + frequency + ", shape=rectangle, width=.5]";
    		
    		//Ajuter les ar�tes gauche et droite selon le cas
    		if (left != null) {
    			String toAdd = this.hashCode() + " -- " + left.hashCode() + " [label=0]";
    			fullString = fullString.concat("\n" + toAdd);
    		}
    		if (right != null) {
    			String toAdd = this.hashCode() + " -- " + right.hashCode() + " [label=1]";
    			fullString = fullString.concat("\n" + toAdd);
    		}
    		return fullString;
    	}
    }

    public int getFrequency() {
    	return frequency;
    }
}

class HuffmanCode {
    private static final int numberOfBits = 255; //pour tous les caract�res ASCII 8-bit
    /**
     * @param text Texte � analyser
     * @return Fr�quence de chaque caract�re ASCII sur 8 bits
     */
    private static int[] getCharacterFrequencies(String text) {
        int[] tab = new int[numberOfBits];

    	for (int i = 0; i < text.length(); i++) {
    		tab[((int)text.charAt(i))]++; // incr�mente la valeur � l'index correspondant au caract�re de 1
    	}
        return tab;
    }

    /**
     * @param charFreq Fr�quence des caract�res
     * @return Node Racine de l'arbre Huffman
     */
    private static Node getHuffmanTree(int[] charFreq) {

    	// G�n�rer un monceau qui contiendra tous les �l�ments
    	PriorityQueue<Node> monceau = new PriorityQueue<Node>();
    	
    	// Construire notre monceau selon les fr�quences des caract�res
    	for (int i = 0; i < charFreq.length; i++) {
    		
    		if (charFreq[i] == 0) {continue;} //ignore les caract�re non-utilis�s
    		
    		Node temp = new Node((char) i, charFreq[i]); //cr�er le noeud
    		monceau.add(temp);
    	}
    	
    	// Maintenant, il faut combiner tous les noeuds jusqu'� ce que l'on ait un seul arbre
    	
    	int size = monceau.size();
    	//On prend size-1 pour ne pas avoir 2 fois la racine
    	for (int i = 0; i < size-1; i++) {
    		// retirer les deux plus petits et les combiner
    		Node node1 = monceau.poll();
    		Node node2 = monceau.poll();
    		Node nouveau = new Node(node1,node2); //cr�er un arbre avec un nouveau noeud comme racine
    		monceau.add(nouveau); //ins�rer dans le monceau et r�p�ter
    	}
        return monceau.peek(); // peek retourne une r�f�rence � la racine sans la retirer
    }

    
    /**
     * @param node Noeud courant
     * @param code Code Huffman
     */

    private static void printTable(Node node, String code) {

    	if (node.isLeaf()) {
    		System.out.println("| " + node.getSymbol() + " | "
                    + node.getFrequency() + " | "
                    + code + " |");
    	}
    	if (node.left != null) printTable(node.left, code + "0");
    	if (node.right != null) printTable(node.right, code + "1");

    }

    /**
     * @param node Noeud de d�part
     */
    private static void printGraph(Node node) {

    	// On garde une queue pour visiter tous les noeuds de gauche � droite sur chaque niveau
    	ArrayDeque<Node> toVisit = new ArrayDeque<Node>();

    	toVisit.add(node); //commencer par la racine
    	System.out.println(node.toString());
    	
    	// Traverser tout l'arbre gauche � droite, haut en bas
    	while (toVisit.isEmpty() == false) {
    		
    		Node current = toVisit.poll();
    		
        	if (current.left != null) {
        		toVisit.add(current.left);
        		System.out.println(current.left.toString());
        	}
        	
        	if (current.right != null) {
        		toVisit.add(current.right);
        		System.out.println(current.right.toString());
        	}       	
    	}
    }

    // Ne pas modifier
    public static void main(String[] args) throws IOException {
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Node root = getHuffmanTree(getCharacterFrequencies(reader.readLine()));

        // Table
        if (args.length == 0 || Arrays.asList(args).contains("table")) {
            System.out.println("Char Freq Code\n---- ---- ----");
            printTable(root, "");
        }
        
        // Graphe
        if (args.length == 0 || Arrays.asList(args).contains("graph")) {
            printGraph(root);
        }

    }
}
