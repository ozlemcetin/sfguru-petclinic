package guru.springframework.sfgurupetclinic.bootstrap;

import guru.springframework.sfgurupetclinic.model.*;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import guru.springframework.sfgurupetclinic.services.PetTypeService;
import guru.springframework.sfgurupetclinic.services.SpecialityService;
import guru.springframework.sfgurupetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    private final PetTypeService petTypeService;

    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }


    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();
        if (count == 0) {

            loadData();
        }
    }

    private void loadData() {

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

        Speciality savedRadiology = null;
        {
            Speciality radiology = new Speciality();
            radiology.setDescription("Radiology");

            savedRadiology = specialityService.save(radiology);
        }

        Speciality surgery = null;
        {
            surgery = new Speciality();
            surgery.setDescription("Surgery");
        }

        Speciality dentistry = null;
        {
            dentistry = new Speciality();
            dentistry.setDescription("dentistry");
        }

        {
            Vet vet1 = new Vet();
            vet1.setFirstName("Sam");
            vet1.setLastName("Axe");

            {
                vet1.getSpecialities().add(savedRadiology);
                vet1.getSpecialities().add(surgery);
            }

            vetService.save(vet1);
        }

        {
            Vet vet2 = new Vet();
            vet2.setFirstName("Jessie");
            vet2.setLastName("Porter");

            {
                vet2.getSpecialities().add(surgery);
                vet2.getSpecialities().add(dentistry);
            }

            vetService.save(vet2);
        }

        System.out.println("Loaded owners, pets, pet types, vets and specialities");
    }

}