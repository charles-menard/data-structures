/**
 * @author Remy Lapointe (20073467)
 * @author Charles Menard (984594)
 */
public class BitSet {
    private int[] bits;
    private final int capacity;
    private final int lengthOfArr;
    /**
     * Cre un ensemble de bits, d'une certaine taille. Ils sont initialisés
     * à {@code false}.
     *  Pour améliorer l'efficacité en mémoire, on implémente le bitSet comme un array de integer
     * pour avoir 32 bits par integer. Nous utilisons des opérations bit  bit pour accéder aux bits individuels.
     *
     * @param nbits taille initiale de l'ensemble
     */
    public BitSet(int nbits) {

        lengthOfArr = (nbits%32 == 0) ? (nbits/32) : (nbits/32) + 1;

        if(lengthOfArr*32 > Integer.MAX_VALUE) throw new IllegalArgumentException("Trop gros");
        bits = new int[lengthOfArr];
        capacity = lengthOfArr * 32; //possibilité d'overflow

    }

    /**
     * Retourne la valeur du bit  l'index spécifié.
     * on sélectionne le bon int dans l'array bits, on bitshift à droite pour
     * atteindre le bon bit et on fait modulo deux pour extraire sa valeur
     * @param bitIndex l'index du bit
     * @return la valeur du bit à l'index spécifié
     */
    public boolean get(int bitIndex) {
        if(bitIndex >= capacity|| bitIndex < 0 ) throw new IndexOutOfBoundsException();
        int bit = (bits[bitIndex/32]) >> (bitIndex % 32);


        if (bit % 2 == 1) return true;
        else return false;


    }

    /**
     * Définit le bit à l'index spécifié à {@code true}.
     * on fait cela avec un bitmask de 0 sauf un 1 à l'index que l'on veut modifier
     * et on OR le int dans bits avec le bitmask
     *
     * @param bitIndex l'index du bit
     */
    public void set(int bitIndex) {
        if(bitIndex >= capacity|| bitIndex < 0 ) throw new IndexOutOfBoundsException();

        int intIndex = bitIndex/ 32;

        int bitMask = 1 << (bitIndex % 32);

        bits[intIndex] = bits[intIndex] | bitMask;
    }

    /**
     * Définit le bit à l'index spcifié comme {@code false}.
     * on fait cela avec un bitmask de 0 sauf un 1 à l'index que l'on veut modifier
     * et on AND le int dans bits avec le NOT du bitmask
     * @param bitIndex l'index du bit
     */
    public void clear(int bitIndex) {
        if(bitIndex >= capacity|| bitIndex < 0 ) throw new IndexOutOfBoundsException();

        int intIndex = bitIndex/ 32;
        int bitMask = 1 << (bitIndex % 32);
        bits[intIndex] = bits[intIndex] & ~bitMask;
    }

    /**
     * 
     * @return La capacité du BitSet (le nombre de bits)
     */
    public int getCapacity () {
    	
    	return capacity;
    }
    
    /**
     *
     * @return une representation textuelle du bitSet sous forme d'un array de 32 bits par ligne
     */
    public String toString(){

        String s = "";
        for(int i =0; i < lengthOfArr; i++){

            String arrOfBits = "";
            int numberToConvert = bits[i];

            for(int j=0; j < 32; j++){

                arrOfBits = arrOfBits.concat(Integer.toString(numberToConvert % 2));
                numberToConvert >>= 1;

            }

            arrOfBits = new StringBuilder(arrOfBits).reverse().toString();
            arrOfBits = arrOfBits.concat("\n");
            s = s.concat(arrOfBits);
        }
        return s;
    }

    /**
     * Remet tous les bits du BitSet  zéro
     */
    public void reset(){

        for(int i = 0; i < lengthOfArr; i++){

            bits[i] = 0;

        }

    }
}
