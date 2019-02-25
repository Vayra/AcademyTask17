package no.noroff.task17;

import java.util.Hashtable;

/* Table class
 * Must be able to read update and delete entries
 *
 *
 */
public class Table {
    private Hashtable<Integer, String> table;

    public Table(){
        table = new Hashtable<>();
    }

    public boolean contains(String query)
    {
        return table.containsValue(query);
    }
    public boolean contains(int query){
        return table.containsKey(query);
    }

    public String get(int key){
        return table.get(key);
    }
}
