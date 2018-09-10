/**
 * 
 * @author Remy Lapointe (20073467)
 * @author Charles Menard (984594)
 *
 */
public class Hash {

	/**
	 * Génère une valeur de hash à l'aide de la méthode "one at a time" de Bob Jenkins
	 * 
	 * @param key l'élément à hasher
	 * @param arrayLength la taille de l'array utilisé (hash table)
	 * @see http://eternallyconfuzzled.com/tuts/algorithms/jsw_tut_hashing.aspx
	 * @return l'index dans l'array résultant du hash
	 */
	public static int jenkins(byte[] key, int arrayLength) {

		int hash = 0;
		int length = key.length;
		
		for (int i = 0; i < length; i++) {
			
			hash += key[i];
			hash += hash << 10;
			hash ^= hash >> 6;
			
		}
		
		hash += hash << 3;
		hash ^= hash >> 11;
		hash += hash << 15;
		
		// Faire le modulo pour obtenir un index dans le bitset
		return Integer.remainderUnsigned(hash, arrayLength);
		
	}
	/**
	 * Génère une valeur de hash à l'aide de la méthode "djb2" de Dan Bernstein
	 * 
	 * @param keys l'élément à hasher
	 * @param arrayLength la taille de l'array utilisé (hash table)
	 * @see http://www.cse.yorku.ca/~oz/hash.html
	 * @return l'index dans l'array résultant du hash
	 */
    public static int djb2(byte[] keys, int arrayLength) {

        int hash = 5381;
        int length = keys.length;

        for (int i = 0; i < length; i++) {

            hash = ((hash << 5) + hash) + keys[i];

        }
        // Faire le modulo pour obtenir un index dans le bitset
        return Integer.remainderUnsigned(hash, arrayLength);
    }

    /**
     * Génère une valeur de hash en combinant les fonctions de hashage "Jenkins one at a time" et "djb2" en appliquant
     * la méthode de Kirsch-Mitzenmacher afin d'avoir plus de deux fonctions de hashage.
     * @param keys l'élément à hasher
     * @param numHashes nombre de fonctions de hashage à utiliser
     * @param arrayLength la taille de l'array utilisé (hash table)
     * @return les hash values
     */
    public static int[] combinedHash(byte[] keys, int numHashes, int arrayLength){

	    int[] hashValues = new int[numHashes];

	    for(int i=0; i < numHashes; i++){

	        hashValues[i] = (jenkins(keys, arrayLength) + i * djb2(keys, arrayLength))% arrayLength;

        }

        return hashValues;
    }
	
}
