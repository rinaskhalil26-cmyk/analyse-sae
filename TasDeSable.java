package iut.sae.algo.simplicite.etu87;

public class TasDeSable {

    /** Constante qui sert à savoir la pente maximale autorisée entre deux cases voisines. */
    private static final int PENTE_MAX = 1;

    /**
     * Constante qui sert aux décalages de ligne pour chaque direction cardinale.
     * Ordre : Haut, Droite, Bas, Gauche.
     */
    private static final int[] DELTA_LIGNE = {-1, 0, 1, 0};

    /**
     * Constante qui sert aux décalages de colonne pour chaque direction cardinale.
     * Ordre : Haut, Droite, Bas, Gauche.
     */
    private static final int[] DELTA_COLONNE = {0, 1, 0, -1};


    /**
     * Fonction principal du programme
     * @param bac la matrice de départ
     * @return la matrice une fois que la simulation est effectuée
     */
    public static int[][] effondrer(int[][] bac) {
        boolean desGrainsOntBouge;

        do {
            desGrainsOntBouge = effectuerUnePasseDeStabilisation(bac);
        } while (desGrainsOntBouge);

        return bac;
    }


    /**
     * Fonction qui effectue une passe de stabilisation sur toute la matrice
     * @param bac la matrice à stabiliser
     * @return un boolean qui permet au programme de savoir si oui ou non un grain a bougé
     * pendant cette passe
     */
    private static boolean effectuerUnePasseDeStabilisation(int[][] bac) {
        boolean unGrainABouge = false;

        for (int ligne = 0; ligne < bac.length; ligne++) {
            for (int colonne = 0; colonne < bac[ligne].length; colonne++) {
                boolean grainBouge = tenterStabilisationCase(bac, ligne, colonne);
                if (grainBouge) {
                    unGrainABouge = true;
                }
            }
        }

        return unGrainABouge;
    }


    /**
     * Fonction qui tente de stabiliser une case en faisant s'écouler ses grains vers les voisins
     * @param bac la matrice à stabiliser
     * @param ligne la ligne de la case à traiter
     * @param colonne la colonne de la case à traiter
     * @return un boolean qui permet au programme de savoir si oui ou non un grain a bougé
     * depuis cette case
     */
    private static boolean tenterStabilisationCase(int[][] bac, int ligne, int colonne) {
        boolean unGrainABouge = false;

        for (int direction = 0; direction < DELTA_LIGNE.length; direction++) {
            int ligneVoisin   = ligne   + DELTA_LIGNE[direction];
            int colonneVoisin = colonne + DELTA_COLONNE[direction];

            if (estDansLeBac(bac, ligneVoisin, colonneVoisin)) {
                if (penteTropRaide(bac[ligne][colonne], bac[ligneVoisin][colonneVoisin])) {
                    transfererUnGrain(bac, ligne, colonne, ligneVoisin, colonneVoisin);
                    unGrainABouge = true;
                }
            }
        }

        return unGrainABouge;
    }

    /**
     * Fonction qui transfere un grain de sable d'une case source vers une case destination
     * @param bac la matrice à modifier
     * @param ligneSource la ligne de la case qui perd un grain
     * @param colonneSource la colonne de la case qui perd un grain
     * @param ligneDest la ligne de la case qui reçoit un grain
     * @param colonneDest la colonne de la case qui reçoit un grain
     */
    private static void transfererUnGrain(int[][] bac, int ligneSource, int colonneSource, int ligneDest, int colonneDest) {
        bac[ligneSource][colonneSource]--;
        bac[ligneDest][colonneDest]++;
    }


    /**
     * Fonction qui calcule si le grain de sable est considéré comme instable en testant la différence de hauteur
     * @param hauteurSource la hauteur de la case source
     * @param hauteurVoisin la hauteur de la case voisine
     * @return un boolean qui permet au programme de savoir si oui ou non le grain de sable est
     * instable et doit s'écouler
     */
    private static boolean penteTropRaide(int hauteurSource, int hauteurVoisin) {
        return hauteurSource - hauteurVoisin > PENTE_MAX;
    }

    /**
     * Fonction qui vérifie si une case est bien dans les limites de la matrice
     * @param bac la matrice à vérifier
     * @param ligne la ligne de la case à tester
     * @param colonne la colonne de la case à tester
     * @return un boolean qui permet au programme de savoir si oui ou non la case est dans le bac
     */
    private static boolean estDansLeBac(int[][] bac, int ligne, int colonne) {
        return ligne   >= 0 && ligne   < bac.length
            && colonne >= 0 && colonne < bac[ligne].length;
    }
}
