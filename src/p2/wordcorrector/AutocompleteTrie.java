package p2.wordcorrector;

import cse332.types.AlphabeticString;
import datastructures.dictionaries.HashTrieMap;


public class AutocompleteTrie extends HashTrieMap<Character, AlphabeticString, Integer> {

    public AutocompleteTrie() {
        super(AlphabeticString.class);
    }

    public String autocomplete(String key) {
        @SuppressWarnings("unchecked")
        HashTrieNode curr = (HashTrieNode) this.root;
        for (Character item : key.toCharArray()) {

            // key not found
            if(curr.pointers.find(item)==null) {
                return null;
            }

            // keep traversing
            curr = curr.pointers.find(item);
        }

        // save where curr is found
        String result = key;


        while (curr.pointers.size() == 1) {
            if (curr.value != null) {
                return null;
            }
            result += curr.pointers.iterator().next();
            curr = curr.pointers.iterator().next().value;

        }

        curr.pointers.size();
        return result;
    }
}
