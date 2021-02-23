package github.com.kazetavi.sonofy.data.album;
//import com.wrapper.spotify.SpotifyApi;
//import com.wrapper.spotify.exceptions.SpotifyWebApiException;
//import com.wrapper.spotify.model_objects.specification.Album;
//import com.wrapper.spotify.requests.data.albums.GetAlbumRequest;
//import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import github.com.kazetavi.sonofy.data.model.Publication;

public class GetPublication {
    private static final String accessToken = "taHZ2SdB-bPA3FsK3D7ZN5npZS47cMy-IEySVEGttOhXmqaVAIo0ESvTCLjLBifhHOHOIuhFUKPW1WMDP7w6dj3MAZdWT8CLI2MkZaXbYLTeoDvXesf2eeiLYPBGdx8tIwQJKgV8XdnzH_DONk";
    private static final String id = "5zT1JLIj9E57p3e1rFm9Uq";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetPublicationRequest getAlbumRequest = spotifyApi.getAlbum(id)
//          .market(CountryCode.SE)
            .build();

    public static void getAlbum_Sync() {
        try {
            final Publication album = getAlbumRequest.execute();

            System.out.println("Name: " + album.getName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getAlbum_Async() {
        try {
            final CompletableFuture<Publication> albumFuture = getAlbumRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final Publication pub = albumFuture.join();

            System.out.println("Name: " + album.getName());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        getAlbum_Sync();
        getAlbum_Async();
    }
}