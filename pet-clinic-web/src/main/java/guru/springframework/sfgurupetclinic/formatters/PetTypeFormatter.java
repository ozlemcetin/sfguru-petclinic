package guru.springframework.sfgurupetclinic.formatters;

import guru.springframework.sfgurupetclinic.model.PetType;
import guru.springframework.sfgurupetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {

        if (text != null && !text.isEmpty()) {

            Collection<PetType> findPetTypes = petTypeService.findAll();
            for (PetType type : findPetTypes) {
                if (type.getName().equals(text)) {
                    return type;
                }
            }//for loop
        }

        throw new ParseException("type not found: " + text, 0);
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
