package  github.com.kazetavi.sonofy.auth.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private final String displayName;
    private final String pseudo;
    private final String role;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, String pseudo, String role) {
        this.displayName = displayName;
        this.pseudo = pseudo;
        this.role = role;
    }

    String getDisplayName() {
        return displayName;
    }

    String getPseudo() {
        return pseudo;
    }

    String getRole() {
        return role;
    }
}