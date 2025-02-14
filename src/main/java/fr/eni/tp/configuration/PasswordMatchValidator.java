package fr.eni.tp.configuration;
 
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import fr.eni.tp.bo.Utilisateur;
import fr.eni.tp.bo.Utilisateur.PasswordMatch;
 
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Utilisateur> {
 
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
    }
 
    @Override
    public boolean isValid(Utilisateur user, ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
