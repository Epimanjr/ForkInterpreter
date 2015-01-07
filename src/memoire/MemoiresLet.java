/**
 *
 * @author Antoine NOSAL
 */
package memoire;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoiresLet {

    public static int MemoireLetCourante = 0;

    public static ArrayList<HashMap<String, String>> listeMemoiresLet = new ArrayList<>();

    private MemoiresLet() {

    }

    public static void nouvelleMemoireLet() {
        HashMap<String, String> nouvelleMemoireLet = new HashMap<>();
        // Si c'est la première MémoireLet
        if (MemoireLetCourante == 0) {
            // Copier le contenu de la Memoire principale
            nouvelleMemoireLet = (HashMap<String, String>) Memoire.getMemoire().clone();
        } else {
            // Sinon, s'il existe déjà une MémoireLet
            // Copier le contenu de la dernière MémoireLet
            nouvelleMemoireLet = listeMemoiresLet.get(listeMemoiresLet.size() - 1);
        }
        // Ajoute cette nouvelle mémoire à la liste des MémoiresLet
        listeMemoiresLet.add(nouvelleMemoireLet);
        MemoireLetCourante++;
    }

    public static void supprimerMemoireLetCourante() {
        listeMemoiresLet.remove(MemoireLetCourante);
        MemoireLetCourante--;
    }
    
    public static HashMap<String, String> getMemoireLetCourante() {
        return listeMemoiresLet.get(MemoireLetCourante);
    }

}
