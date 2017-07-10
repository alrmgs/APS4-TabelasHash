
import java.util.Hashtable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alexia-gomes
 */
public class TabelasHash<K, V> {

    private static class Pair<K, V> {

        public final K key;
        public final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int maxSize = 3;
    private Object[] dados = new Object[maxSize];

    private void put(K key, V value) {
        int hash = key.hashCode();
        int indice = Math.abs(hash % dados.length);
        for (int i = 0; i < dados.length; i++) {
            if (dados[indice] == null) {
                dados[indice] = new Pair<>(key, value);
                return;
            } else {
                indice = (indice + 1) % dados.length;
            }

            if (dados.length == maxSize) {
                resize();
                System.out.println("RESIZING HASH TABLE: New size: " + maxSize);
            }
        }
    }

    private void resize() {
        int newMaxSize = maxSize * 2;
        maxSize = newMaxSize;
        Object[] oldDados = dados;
        dados = new Object[newMaxSize];
        for (int i = 0; i < oldDados.length; i++) {
            if (oldDados[i] != null) {
                Pair<K, V> p = (Pair<K, V>) oldDados[i];
                put(p.key, p.value);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private V get(K key) {
        int hash = key.hashCode();
        int indice = Math.abs(hash % dados.length);
        for (int i = 0; i < dados.length; i++) {
            if (dados[indice] == null) {
                throw new RuntimeException("Chave inexistente");
            }
            Pair<K, V> kv = (Pair<K, V>) dados[indice];
            if (kv.key.equals(key)) {
                return kv.value;
            }
            indice = (indice + 1) % dados.length;
        }
        throw new RuntimeException("Chave inexistente");
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        Hashtable<String, Integer> hash = new Hashtable<>();

        hash.put("Rafael", 1);
        hash.put("Ivonei", 2);
        hash.put("Lucia", 3);
        hash.put("Guilherme", 4);
        hash.put("Aline", 50);
        hash.put("Conterato", 30);
        hash.put("Alexia", 5);

        for (Object o : hash.dados) {
            if (o == null) {
                System.out.println("(null)");
                continue;
            }
            Pair<String, Integer> p = (Pair<String, Integer>) o;
            System.out.println(p.key + ": " + p.value);
        }

        System.out.println("---------------");
        System.out.println(hash.get("Rafael"));
        System.out.println(hash.get("Ivonei"));
        System.out.println(hash.get("Alexia"));
    }

}
