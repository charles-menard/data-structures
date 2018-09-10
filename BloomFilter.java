/**
 * @author Remy Lapointe (20073467)
 * @author Charles Menard (984594)
 */
public class BloomFilter {
    /**
	 * Crée un filtre de Bloom basé sur la taille de l'ensemble de bits et du
	 * nombre de fonctions de hachage.
	 *
	 * @param numBits
	 *            taille de l'ensemble de bits
	 * @param numHashes
	 *            nombre de fonctions de hachage
	 */
	
	BitSet bitSet; // array de int contenant tous les bits de données
	
	int numHashes; // nb de fonctions de hashage impliquées
	int numElements = 0; //le nb total d'éléments insérés (ne peut qu'augmenter)
	int numBits; //nb de bits d'espace alloués dans le BitSet
	
    public BloomFilter(int numBits, int numHashes) {
        
    	//generer un array qui contient des int sur 32 bits
    	bitSet = new BitSet(numBits);
    	this.numHashes = numHashes;
    	this.numBits = numBits;

    }

    /**
     * Crée un filtre de Bloom basé sur le nombre d'éléments attendus et de la
     * probabilité de faux positifs desiré.
     *
     * @param numElems nombre d'éléments a insérer
     * @param falsePosProb probabilité de faux positifs
     */
    public BloomFilter(int numElems, double falsePosProb) {
        
    	
    	numBits = (int) Math.ceil(-(numElems*Math.log(falsePosProb) / Math.pow(Math.log(2),2)));
    	
    	numHashes = (int) Math.ceil((double) numBits/numElems * Math.log(2));
    	
    	bitSet = new BitSet(numBits);

    }

    /**
     * Ajoute un élément au filtre de Bloom.
     *
     * @param key l'élément a insérer
     */
    public void add(byte[] key) {

    	numElements++; //puisqu'on a fait un ajout
    	
    	int[] hashIndexes = Hash.combinedHash(key, numHashes, size());
    	
    	for (int i = 0; i < hashIndexes.length; i++) {
    		bitSet.set(hashIndexes[i]);
    	}
    	
    }

    /**
     * Cherche pour l'element dans le filtre de Bloom.
     *
     * @param key l'element a trouver
     * @return si l'element est possiblement dans le filtre
     */
    public boolean contains(byte[] key) {
    	
    	int[] hashIndexes = Hash.combinedHash(key, numHashes, size());
    	
    	for (int i = 0; i < hashIndexes.length; i++) {
    		
    		if (bitSet.get(hashIndexes[i]) == false) {
    			return false;
    		}
    		
    	}
    	
        return true; //si on arrive ici, on a trouvé aucun bit  0
    }

    /**
     * Remet a zéro le filtre de Bloom.
     */
    public void reset() {
        
		bitSet.reset(); //remet tous les bits du BitSet  zro
		
    }

    /**
     * Retourne le nombre de bits du filtre de Bloom.
     *
     * @return nombre de bits
     */
    public int size() {
        return bitSet.getCapacity();
    }

    /**
     * Retourne le nombre d'elements inserés dans le filtre de Bloom.
     *
     * @return nombre d'elements inserés
     */
    public int count() {
        return numElements;
    }

    /**
     * Retourne la probabilité actuelle de faux positifs.
     *
     * @return probabilité de faux positifs
     */
    public double fpp() {

        return 1 - Math.pow(Math.exp(-numHashes*numElements/numBits), numHashes);
    }
    

}
