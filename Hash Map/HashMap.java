import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

/**
 * Your implementation of a HashMap, using external chaining as your collision
 * policy.  Read the PDF for more instructions on external chaining.
 *
 * @author Ziyu Zhang
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries.
     */
    public HashMap() {
        // Initialize your hashtable here.
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
    }

    @Override
    public V add(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("The input(s) is null!");
        }
        MapEntry<K, V> newEntry = new MapEntry<>(key, value);
        if (((double) (size + 1)) / table.length > MAX_LOAD_FACTOR) {
            MapEntry<K, V>[] tmp = table;
            table = (MapEntry<K, V>[]) new MapEntry[tmp.length * 2 + 1];
            size = 0;
            for (MapEntry<K, V> entry : tmp) {
                if (entry != null) {
                    MapEntry<K, V> e = new MapEntry<>(entry.getKey(),
                            entry.getValue());
                    addHelper(e);
                    while (entry.getNext() != null) {
                        entry = entry.getNext();
                        MapEntry<K, V> ee = new MapEntry<>(entry.getKey(),
                                entry.getValue());
                        addHelper(ee);
                    }
                }
            }
        }
        return addHelper(newEntry);
    }

    /**
     * Helper method that adds the new MapEntry into the table
     *
     * @param entry the entry to be added to table
     * @return null if the key was not already in the map.  If it was in the
     * map, return the old value associated with it.
     */
    private V addHelper(MapEntry<K, V> entry) {
        int index = Math.abs(entry.getKey().hashCode()) % table.length;
        V answer = null;
        if (table[index] == null) {
            table[index] = entry;
            size++;
        } else {
            boolean go = true;
            MapEntry<K, V> tmp = table[index];
            MapEntry<K, V> prev = null;
            while (go) {
                if (tmp == null) {
                    prev.setNext(entry);
                    go = false;
                    answer = null;
                    size++;
                } else if (tmp.getKey().equals(entry.getKey())) {
                    answer = tmp.getValue();
                    tmp.setValue(entry.getValue());
                    go = false;
                } else {
                    prev = tmp;
                    tmp = tmp.getNext();
                }
            }
        }
        return answer;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null!");
        }
        V answer = null;
        int index = Math.abs(key.hashCode()) % table.length;
        boolean go = true;
        MapEntry<K, V> tmp = table[index];
        MapEntry<K, V> prev = null;
        while (go && tmp != null) {
            if (tmp.getKey().equals(key)) {
                answer = tmp.getValue();
                size--;
                go = false;
                if (tmp == table[index]) {
                    table[index] = tmp.getNext();
                } else {
                    prev.setNext(tmp.getNext());
                }
            } else {
                prev = tmp;
                tmp = tmp.getNext();
            }
        }
        if (answer == null) {
            throw new NoSuchElementException("no such key!");
        } else {
            return answer;
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null!");
        }
        V answer = null;
        int index = Math.abs(key.hashCode()) % table.length;
        boolean go = true;
        MapEntry<K, V> tmp = table[index];
        while (go && tmp != null) {
            if (tmp.getKey().equals(key)) {
                answer = tmp.getValue();
                go = false;
            } else {
                tmp = tmp.getNext();
            }
        }
        if (answer == null) {
            throw new NoSuchElementException("no such key!");
        } else {
            return answer;
        }
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null!");
        }
        boolean answer = false;
        int index = Math.abs(key.hashCode()) % table.length;
        boolean go = true;
        MapEntry<K, V> tmp = table[index];
        while (go && tmp != null) {
            if (tmp.getKey().equals(key)) {
                answer = true;
                go = false;
            } else {
                tmp = tmp.getNext();
            }
        }
        return answer;
    }

    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                set.add(entry.getKey());
                while (entry.getNext() != null) {
                    entry = entry.getNext();
                    set.add(entry.getKey());
                }
            }
        }
        return set;
    }

    @Override
    public List<V> values() {
        List<V> list = new ArrayList<>();
        for (MapEntry<K, V> entry : table) {
            if (entry != null) {
                list.add(entry.getValue());
                while (entry.getNext() != null) {
                    entry = entry.getNext();
                    list.add(entry.getValue());
                }
            }
        }
        return list;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE.  IT IS FOR TESTING ONLY
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] toArray() {
        return table;
    }

}