import java.util.ArrayList;

class HashNode<K,V>{
	K key;
	V value;
	HashNode<K,V> next;
	public HashNode(K key, V Value){
		this.key = key;
		this.value = value;
	}
}

class Map<K,V>{
	private ArrayList<HashNode<K,V>> bucketArray;
	private int numBuckets;
	private int size;
	public Map(){
		bucketArray = new ArrayList<>();
		numBuckets = 10;
		size = 0;
		//create empty chains
		for(int i=0;i<numBuckets;i++){
			bucketArray.add(null);
		}
	}
	
	
	//size of the hashTable
	public int size(){
		return size;
	}
	
	
	//check if the hashTable isEmpty
	public boolean isEmpty(){
		return size==0;
	}
	
	
	//get the index corresponding to a key
	public int getBucketIndex(K key){
		int hashCode = key.hashCode();
		int index = hashCode % numBuckets;
		return index;
	}
	
	public V get(K key){
		int index = getBucketIndex(key);
		HashNode<K,V> headNode = bucketArray.get(index);
		while(headNode!=null){
			if(headNode.key.equals(key)){
				return headNode.value;
			}
			headNode = headNode.next;
		}
		//if key not found
		return null;
	}
	
	public V remove(K key){
		int index = getBucketIndex(key);
		HashNode<K,V> headNode = bucketArray.get(index);
		HashNode<K,V> prev = null;
		while(headNode!=null){
			if(headNode.key.equals(key)){
				break;
			}
			prev = headNode;
			headNode = headNode.next;
		}
		//if key not found
		if(headNode == null){
			return null;
		}
		//else remove key
		size--;
		if(prev!=null){
			prev.next = headNode.next;
		}
		//if node to be removed is the first node in the chain
		else{
			bucketArray.set(index, headNode.next);
		}
		return headNode.value;
	}
	
	//add an element to the hashTable
	public void add(K key, V value){
		int index = getBucketIndex(key);
		HashNode<K,V> head = bucketArray.get(index);
		//if hashtable already contains the key update the value
		while(head!=null){
			if(head.key.equals(key)){
				head.value = value;
				return;
			}
			head = head.next;
		}
		//add key node pair to the hashTable
		size++;
		head = bucketArray.get(index);
		HashNode<K,V> newNode = new HashNode<K,V>(key,value);
		newNode.next = head;
		bucketArray.set(index, newNode);
		
		//check if load factor gets beyond threshold
		if((1.0*size)/numBuckets >= 0.7){
			ArrayList<HashNode<K,V>> temp = bucketArray;
			bucketArray = new ArrayList<>();
			numBuckets *= 2;
			size = 0;
			for(int i=0;i<numBuckets;i++){
				bucketArray.add(null);
			}
			for(HashNode<K,V> headNode: temp){
				while(headNode!=null){
					add(headNode.key, headNode.value);
					headNode = headNode.next;
				}
			}
		}
	}
	
}

public class HashTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,Integer> map = new Map<>(); 
		map.add("this", 1);
		map.add("coder", 2);
		map.add("this", 4);
		map.add("hi", 5);
		System.out.println(map.size());
        System.out.println(map.remove("this"));
        System.out.println(map.remove("this"));
        System.out.println(map.size());
        System.out.println(map.isEmpty());
	}

}
