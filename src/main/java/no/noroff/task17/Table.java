package no.noroff.task17;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/* Table class
 * Must be able to read update and delete entries
 *
 *
 */
public class Table<K, V>  {
    private Hashtable<K, V> table;
    private String tableName;

    public Table(){
        table = new Hashtable<>();
        tableName = "Unnamed Table";
    }

    public Table(String tableName){
        table = new Hashtable<>();
        this.tableName = tableName;
    }

    public boolean contains(Object query){
        return table.containsKey(query) || table.containsValue(query);
    }

    public V get(K key){
        return table.get(key);
    }

    public void read(){
        // Prints key/value pairs to console
        System.out.println(tableName + ": ");
        for (Map.Entry<K, V> entry : table.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue().toString());
        }
    }

    public boolean add(K key, V value){

        if (!table.containsKey((key))){
            table.put(key, value);
            return true;
        }
        return false;
    }

    public boolean update(K key, V newValue){
        if (table.containsKey(key)){
            table.replace(key, newValue);
            return true;
        }
        return false;
    }

    public boolean remove(K key){
        if (table.containsKey(key)){
            table.remove(key);
            return true;
        }
        return false;
    }
    public Set<Map.Entry<K,V>> entrySet(){
        return table.entrySet();
    }
}
