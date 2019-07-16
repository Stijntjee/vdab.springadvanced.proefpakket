package be.vdab.proefpakket.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OndernemingsNummerValidator implements ConstraintValidator<OndernemingsNummer, Long> {
   public void initialize(OndernemingsNummer constraint) {
   }

   public boolean isValid(Long nummer, ConstraintValidatorContext context)
   {
      if (nummer == null)
      {
         return true;
      }
      return ((nummer / 100) % 97) + (nummer % 100) == 97 ;
   }
}
