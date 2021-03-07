package github.com.kazetavi.sonofy.ui.recommandation;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import github.com.kazetavi.sonofy.business.EmotionEnum;
import github.com.kazetavi.sonofy.data.api.EmotionFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Emotion;
import github.com.kazetavi.sonofy.data.model.Publication;

public class RecommandationViewModel extends ViewModel {

    private static final String TAG = "RecommandationViewModel";

    private Map<String, Integer> userProximity = new HashMap<>();
    private String userId;

    private MutableLiveData<List<Publication>> recommendedPublicationsLiveData = new MutableLiveData<>();
    private List<Publication> allPublications;


    public MutableLiveData<List<Publication>> getRecommendedPublicationsLiveData() {
        return recommendedPublicationsLiveData;
    }

    // "Point d'entré"
    public void getRecommandation(String userId){
        List<Emotion> emotionList = new ArrayList<>();
        this.userId = userId;

        EmotionFirestore.getByUser(userId).addOnSuccessListener(queryDocumentSnapshots -> {
            queryDocumentSnapshots.getDocuments()
                    .forEach(doc -> emotionList.add(doc.toObject(Emotion.class)));
            getUserReactedPublication(emotionList);
        });

    }

    // Recupere les publications sur lesquels l'utilisateur a reagit
    public void getUserReactedPublication(List<Emotion> emotions){
        Log.d(TAG, "getEmotionsForPublications: ");
        List<Publication> userReactedPublication = new ArrayList<>();
        this.allPublications = new ArrayList<>();
        PublicationFirestore.getAllPublications().addOnSuccessListener(queryDocumentSnapshots -> {
           queryDocumentSnapshots.getDocuments()
                   .forEach(doc -> allPublications.add(doc.toObject(Publication.class)));

            //La liste des id des publication avec lesquelles l'utilisateur a reagit
            List<String> userReactedPubId = emotions.stream()
                    .map(Emotion::getPublicationId)
                    .collect(Collectors.toList());

            //La liste des publication avec lesquelle l'utilisateur a réagit
            userReactedPublication.addAll(allPublications.stream()
                    .filter(publication -> userReactedPubId.contains(publication.getUid()))
                    .collect(Collectors.toList()));

            getEmotionsForPublications(userReactedPublication);
        });
    }

    //Récupère les émotions des publications
    public void getEmotionsForPublications(List<Publication> publications){
        Log.d(TAG, "getEmotionsForPublications: ");
        List<Emotion> emotionList = new ArrayList<>();
        EmotionFirestore.getAll().addOnSuccessListener(queryDocumentSnapshots -> {
            queryDocumentSnapshots.getDocuments()
                    .forEach(doc -> emotionList.add(doc.toObject(Emotion.class)));

            for(Publication pub : allPublications){
                pub.setEmotions(
                        emotionList.stream()
                                .filter(emotion -> emotion.getPublicationId().equals(pub.getUid()))
                                .collect(Collectors.toList())
                );
            }
            reactionComparaison(publications);
        });
    }

    //Compare les reactions pour check à quel point les utilisateurs sont similaires
    public void reactionComparaison(List<Publication> publications){
        Log.d(TAG, "reactionComparaison: ");
        for (Publication pub : publications){
            Optional<Emotion> userEmotionOptional = pub.getEmotions().stream()
                    .filter(emotion -> emotion.getUserId().equals(this.userId))
                    .findFirst();

            if(userEmotionOptional.isPresent()){
                Emotion userEmotion = userEmotionOptional.get();
                for (Emotion emotion : pub.getEmotions()){
                    if(!emotion.getUserId().equals(userId)){
                        if(EmotionEnum.stringToEnum(emotion.getEmotion()).getCategorie() == EmotionEnum.stringToEnum(userEmotion.getEmotion()).getCategorie()){
                            userProximity.put(emotion.getUserId(), userProximity.getOrDefault(emotion.getUserId(), 0) + 1);
                        }
                        else {
                            userProximity.put(emotion.getUserId(), userProximity.getOrDefault(emotion.getUserId(), 0) - 1);
                        }
                    }
                }
            }
        }
        getOrderedSimilarUser(userProximity);
    }

    //Ordonne les utilisateurs similaire dans une liste
    public void getOrderedSimilarUser(Map<String, Integer> similarUsers){
        Log.d(TAG, "getOrderedSimilarUser: ");
        List<String> orderedSimilarUser = new ArrayList();
        int similarUsersSize = similarUsers.keySet().size();
        int i = 0;
        boolean keepGoing = true;
        while (i<similarUsersSize && keepGoing){
            String mostSimilarUser = Collections.max(similarUsers.entrySet(), Map.Entry.comparingByValue()).getKey();
            if(similarUsers.get(mostSimilarUser) > 0){
                orderedSimilarUser.add(mostSimilarUser);
            }
            else {
                keepGoing = false;
            }
            similarUsers.remove(mostSimilarUser);
            i++;
        }
        getRecommendedPublications(orderedSimilarUser);
    }

    //Retourne les publications recommandées
    public void getRecommendedPublications(List<String> orderedSimilarUsers){
        Log.d(TAG, "getRecommendedPublications: ");
        List<Publication> recommendedPublications = allPublications.stream()
               .filter(publication -> {
                   if(publication.getEmotions() != null){
                       return !publication.getEmotions().stream()
                               .filter(emotion -> orderedSimilarUsers.contains(emotion.getUserId()))
                               .collect(Collectors.toList()).isEmpty();
                   }
                   else {
                       return false;
                   }
               })
       .collect(Collectors.toList());

        recommendedPublicationsLiveData.postValue(recommendedPublications);
    }
}