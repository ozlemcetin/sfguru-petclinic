package guru.springframework.sfgurupetclinic.bootstrap;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.model.PetType;
import guru.springframework.sfgurupetclinic.model.Vet;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import guru.springframework.sfgurupetclinic.services.PetTypeService;
import guru.springframework.sfgurupetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        PetType savedDogPetType = null;
        {
            PetType petType = new PetType();
            petType.setName("Dog");

            savedDogPetType = petTypeService.save(petType);
        }

        PetType catPetType = null;
        {
            catPetType = new PetType();
            catPetType.setName("Cat");
        }

        {
            Owner owner1 = new Owner();
            owner1.setFirstName("Michael");
            owner1.setLastName("Weston");
            owner1.setAddress("123 Brickerel");
            owner1.setCity("Miami");
            owner1.setTelephone("1231231234");

            {
                Pet rosco = new Pet();
                rosco.setName("Rosco");
                rosco.setPetType(savedDogPetType);
                rosco.setOwner(owner1);
                rosco.setBirthDate(LocalDate.now());

                owner1.getPets().add(rosco);
            }

            ownerService.save(owner1);
        }

        {
            Owner owner2 = new Owner();
            owner2.setFirstName("Fiona");
            owner2.setLastName("Glenanne");
            owner2.setAddress("123 Brickerel");
            owner2.setCity("Miami");
            owner2.setTelephone("1231231234");

            {

                Pet fionasCat = new Pet();
                fionasCat.setName("Ray");
                fionasCat.setPetType(catPetType);
                fionasCat.setOwner(owner2);
                fionasCat.setBirthDate(LocalDate.now());

                owner2.getPets().add(fionasCat);
            }

            ownerService.save(owner2);
        }

        {
            Vet vet1 = new Vet();
            vet1.setFirstName("Sam");
            vet1.setLastName("Axe");

            vetService.save(vet1);
        }

        {
            Vet vet2 = new Vet();
            vet2.setFirstName("Jessie");
            vet2.setLastName("Porter");

            vetService.save(vet2);
        }

        System.out.println("Loaded owners, pets, vets and pet types");
    }
}
