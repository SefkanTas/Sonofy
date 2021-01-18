package github.com.kazetavi.sonofy.ui.register;

import androidx.annotation.Nullable;
    /**
     * Data validation state of the register form.
     */
    class RegisterFormState {
        @Nullable
        private Integer firstnameError;
        @Nullable
        private Integer nameError;
        @Nullable
        private Integer pseudoError;
        @Nullable
        private Integer roleError;
        @Nullable
        private Integer usernameError;
        @Nullable
        private Integer passwordError;
        private boolean isDataValid;

        RegisterFormState(@Nullable Integer firstnameError,@Nullable Integer nameError,@Nullable Integer pseudoError,@Nullable Integer roleError,@Nullable Integer usernameError, @Nullable Integer passwordError) {
            this.firstnameError = firstnameError;
            this.nameError = nameError;
            this.pseudoError = pseudoError;
            this.roleError = roleError;
            this.usernameError = usernameError;
            this.passwordError = passwordError;
            this.isDataValid = false;
        }

        RegisterFormState(boolean isDataValid) {
            this.firstnameError = null;
            this.nameError = null;
            this.pseudoError = null;
            this.roleError = null;
            this.usernameError = null;
            this.passwordError = null;
            this.isDataValid = isDataValid;
        }

        @Nullable
        Integer getFirstameError() {
            return firstnameError;
        }

        @Nullable
        Integer getNameError() {
            return nameError;
        }

        @Nullable
        Integer getPseudoError() {
            return pseudoError;
        }

        @Nullable
        Integer getRoleError() {
            return roleError;
        }

        @Nullable
        Integer getUsernameError() {
            return usernameError;
        }

        @Nullable
        Integer getPasswordError() {
            return passwordError;
        }

        boolean isDataValid() {
            return isDataValid;
        }
    }
