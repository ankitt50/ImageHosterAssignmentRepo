package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public boolean registerUser(User newUser) {
        if (checkPasswordStrength(newUser.getPassword())) {
            userRepository.registerUser(newUser);
            return true;
        }
        else {
            return false;
        }
    }

    //Since we did not have any user in the database, therefore the user with username 'upgrad' and password 'password' was hard-coded
    //This method returned true if the username was 'upgrad' and password is 'password'
    //But now let us change the implementation of this method
    //This method receives the User type object
    //Calls the checkUser() method in the Repository passing the username and password which checks the username and password in the database
    //The Repository returns User type object if user with entered username and password exists in the database
    //Else returns null
    public User login(User user) {
        User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        } else {
            return null;
        }
    }

    // checks for all the password requirements : password entered by the user
    // must contain at least 1 alphabet (a-z or A-Z), 1 number (0-9)
    // and 1 special character (any character other than a-z, A-Z and 0-9)
    public boolean checkPasswordStrength(String password) {
        try {
            char[] passwordCharacters = password.toCharArray();
            int count=0;
            int count2=0;
            int count3=0;
            int count4=0;

            // first for-loop checks for the numbers (0-9)
            for (char c:
                 passwordCharacters) {
                for (int i = 48; i < 58; i++) {
                    if (i == c) {
                        count++;
                        break;
                    }
                }
                if (count>0) {
                    break;
                }
            }
            // second for-loop checks for the Capital alphabets (A-Z)

            for (char c:
                    passwordCharacters) {
                for (int i = 65; i<91; i++) {
                    if (i == c) {
                        count2++;
                        break;
                    }
                }
                if (count2>0) {
                    break;
                }

                // Third for-loop checks for the Small alphabets (a-z)

                for (int i = 97; i<123; i++) {
                    if (i == c) {
                        count2++;
                        break;
                    }
                }
                if (count2>0) {
                    break;
                }
            }

            // Last for-loop checks for the Special Characters

            for (char c:
                    passwordCharacters) {
                for (int i = 65; i<91; i++) {
                    if (i != c) {
                        count3++;
                    }
                }
                for (int i = 97; i<123; i++) {
                    if (i != c) {
                        count3++;
                    }
                }
                for (int i = 48; i < 58; i++) {
                    if (i != c) {
                        count3++;
                    }
                }
                if (count3==62) {
                    count4++;
                    break;
                }
                else {
                    count3 = 0;
                }
            }

            // if all types of characters are present in the password, return
            // true, since password is valid
            if (count>0&&count2>0&&count4>0) {
                return true;
            }
            else { // else return false, i.e password is invalid
                return false;
            }

        }
        catch (Exception e){
        }
        return false;
    }

}
