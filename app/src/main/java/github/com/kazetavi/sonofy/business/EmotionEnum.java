package github.com.kazetavi.sonofy.business;

import androidx.annotation.NonNull;

public enum EmotionEnum {
    VERY_HAPPY,
    HAPPY,
    SAD,
    VERY_SAD;

    /**
     * Permet de différencier si une émotion est dans la même catégorie qu'une autre.
     */
    public int getCategorie(){
        int rtr;
        switch (this) {
            case VERY_HAPPY:
                rtr = 1;
                break;
            case HAPPY:
                rtr = 1;
                break;
            case SAD:
                rtr = 2;
                break;
            case VERY_SAD:
                rtr = 2;
                break;
            default:
                rtr = 0;
        }
        return rtr;
    }

    @NonNull
    @Override
    public String toString() {
        String rtr;
        switch (this){
            case VERY_HAPPY:
                rtr= "veryHappy";
                break;
            case HAPPY:
                rtr= "happy";
                break;
            case SAD:
                rtr = "sad";
                break;
            case VERY_SAD:
                rtr = "verySad";
                break;
            default:
                rtr= "";
        }
        return rtr;
    }

    public static EmotionEnum stringToEnum(String str){
        EmotionEnum rtr;
        switch (str){
            case ("veryHappy"):
                rtr = VERY_HAPPY;
                break;
            case ("happy"):
                rtr = HAPPY;
                break;
            case ("sad"):
                rtr = SAD;
                break;
            case ("verySad"):
                rtr = VERY_SAD;
                break;
            default:
                rtr = null;
        }
        return rtr;
    }
}


