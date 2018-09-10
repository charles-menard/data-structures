/**
 * @author Charles M�nard
 */

public class SparseVector {
    private Node first;
    private int length;

    /**
     * Classe interne pour les noeuds
     */
    public class Node {

        public Object value;
        private int index;
        public Node next;

        /**
         *
         * @param index L'index de l'élement
         * @param value L'objet qui est la valeur de l'élement
         */
        public Node(int index, Object value){
            this.index = index;
            this.value = value;

        }
    }
    /**
     * Constructeur par défaut
     */
    public SparseVector() {
        this(10);
    }

    public SparseVector(int length) {
        this.length = length;
    }

    /**
     *
     * @param index l'index de l'élement à retourner
     * @return la valeur de l'élement à l'index choisi
     */
    public Object get(int index) {
        if(index >= this.length) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        Node currentNode = this.first;
        while (currentNode != null) {
            if (currentNode.index == index) {
                return currentNode.value;
            }
            currentNode = currentNode.next;

        }
        return null; //si on n'a pas trouvé l'index, c'est que l'élement est nul
    }

    // Ajouter ou mettre à jour l'élément à la position index
    public void set(int index, Object value) {
        if(index >= this.length) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        if (value == null) return ; //si l'objet a une valeur nulle, on ne fait rien
        else if (this.first == null) { //le cas où la liste est vide
            this.first = new Node (index, value);
        } else {
            if(find(index)){ //si l'élement existe déjà

                replace(index, value);
            }
            else{
                add(index, value); //si il faut ajouter l'élement
            }

        }
    }

    /**
     * Remplace l'élement à l'index index par un noeud de valeur value
     * @param index Index de l'élement à remplacer
     * @param value nouvelle valeur de l'élement
     */
    public void replace(int index, Object value){
        if(index >= this.length) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        Node currentNode = this.first;
        while(currentNode.index != index){
            currentNode = currentNode.next;
        }
        currentNode.value = value;

    }

    /**
     * Ajoute un noeud de valeur value à l'index index
     * @param index Index de l'élement à ajouter
     * @param value Valeur de l'élement à ajouter
     */
    public void add(int index, Object value){
        if(index >= this.length) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");
        if(value == null) return;

        Node currentNode = this.first;
        Node newNode = new Node(index, value);

        while(currentNode.next != null && currentNode.next.index < index){
            currentNode = currentNode.next; //on se positionne à la bonne place
        }
        if(currentNode.index > index && currentNode == this.first){ //cas où il faut ajouter à l'avant de la liste
            this.first = newNode;
            newNode.next = currentNode;

        } else if(currentNode.index < index && currentNode.next == null){ //cas où il faut ajouter à la fin de la liste
            currentNode.next = newNode;
        } else{ // cas où il faut ajouter au milieu de la liste
            Node tmp = currentNode.next;
            currentNode.next = newNode;
            currentNode.next.next = tmp;
        }
    }

    /**
     *
     * @param index index de l'élement (non null) à trouver
     * @return vrai si on trouve l'index, faux si on ne trouve pas l'élement
     */
    public boolean find(int index){
        if(index >= this.length) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");

        Node currentNode = this.first;
        Boolean itemFound = false;

        while (currentNode != null) { //on se rend à la bonne place
            if (currentNode.index == index) {
                itemFound = true;
                break;
            }
            currentNode = currentNode.next;
        }
        return itemFound;
    }

    // Supprimer l'élément à la position index
    public void remove(int index) {
        if(index >= this.length) throw new IndexOutOfBoundsException("Index " + index + " is out of bounds!");

        Node currentNode = this.first;

        while(currentNode.next != null && currentNode.next.index < index){
            currentNode = currentNode.next;
        }
        if(currentNode == this.first){ //le cas au début de la liste
            this.first = currentNode.next;

        } else if(currentNode.next == null){ //le cas à la fin de la liste
            currentNode.next = null;
        } else{
            //le cas au milieu de la liste
            currentNode.next = currentNode.next.next;

        }

    }

    // Longueur du vecteur creux
    public int length() {
        return length;
    }

    // Nombre d'éléments non nuls
    public int size()
    {
        Node currentNode = this.first;
        int s = 0;
        while(currentNode != null)
        {
            s++;
            currentNode = currentNode.next;
        }

        return s;
    }

    public void print(){
        Node currentNode = this.first;
        while(currentNode != null){
            System.out.println("La valeur à la position : " +  currentNode.index + " est : " + currentNode.value);
            currentNode = currentNode.next;
        }

    }
}
