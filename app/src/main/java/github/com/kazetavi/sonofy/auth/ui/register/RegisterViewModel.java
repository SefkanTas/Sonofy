package github.com.kazetavi.sonofy.auth.ui.register;

import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.auth.data.model.User;

public class RegisterViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }


    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7;
    }

    // Checking the user's name
    private boolean isNameValid(String name) {
        return name != null;
    }

    // Checking the user's firstname
    private boolean isFirstameValid(String firstname) {
        return firstname != null;
    }

    // Checking the user's pseudo
    private boolean isPseudoValid(String pseudo) {
        return pseudo != null;
    }

    //Checking all fields of the register form
    public void registerDataChanged(String name, String firstname, String pseudo,String username, String password) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(null, null,null,R.string.invalid_username,null));
        }else if (!isNameValid(name)) {
            registerFormState.setValue(new RegisterFormState(null,R.string.invalid_username,null,null, null));
        }else if (!isFirstameValid(firstname)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username,null,null,null, R.string.invalid_password));
        }else if (!isPseudoValid(pseudo)) {
            registerFormState.setValue(new RegisterFormState(null,null,R.string.invalid_username,null, R.string.invalid_password));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null,null,null,null, R.string.invalid_password));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // Ajoute un utilisateur dans la BD Firestore
    void addUser(String nom, String prenom, String pseudo, String email, String role){
        User user = new User(nom, prenom, pseudo, email, role);

        
    }
}
