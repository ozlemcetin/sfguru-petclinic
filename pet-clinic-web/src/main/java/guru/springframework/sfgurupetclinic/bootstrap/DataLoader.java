package guru.springframework.sfgurupetclinic.bootstrap;

import guru.springframework.sfgurupetclinic.model.*;
import guru.springframework.sfgurupetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    private final PetTypeService petTypeService;

    private final SpecialityService specialityService;

    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
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


        PetType catPetType = new PetType();
        catPetType.setName("Cat");


        {
            Owner owner1 = new Owner();
            owner1.setFirstName("Michael");
            owner1.setLastName("Weston");
            owner1.setAddress("123 Brickerel");
            owner1.setCity("Miami");
            owner1.setTelephone("1231231234");

            //pets
            {
                Pet rosco = new Pet();
                rosco.setName("Rosco");
                rosco.setBirthDate(LocalDate.now());
                rosco.setOwner(owner1);
                rosco.setPetType(savedDogPetType);

                //visits

                //add
                owner1.getPets().add(rosco);
            }

            ownerService.save(owner1);
        }

        Pet fionasCat = null;
        {
            Owner owner2 = new Owner();
            owner2.setFirstName("Fiona");
            owner2.setLastName("Glenanne");
            owner2.setAddress("123 Brickerel");
            owner2.setCity("Miami");
            owner2.setTelephone("1231231234");

            {

                fionasCat = new Pet();
                fionasCat.setName("Ray");
                fionasCat.setBirthDate(LocalDate.now());
                fionasCat.setOwner(owner2);
                fionasCat.setPetType(catPetType);

                //visits
                {
                    Visit visit1 = new Visit();
                    visit1.setDate(LocalDate.now());
                    visit1.setDescription("Sneezy Kitty");
                    visit1.setPet(fionasCat);

                    fionasCat.getVisits().add(visit1);
                }

                //add
                owner2.getPets().add(fionasCat);
            }

            ownerService.save(owner2);
        }

        {

            Visit visit2 = new Visit();
            visit2.setDate(LocalDate.now());
            visit2.setDescription("Kitty Washing");
            visit2.setPet(fionasCat);

            visitService.save(visit2);
        }

        Speciality savedRadiology = null;
        {
            Speciality radiology = new Speciality();
            radiology.setDescription("Radiology");

            savedRadiology = specialityService.save(radiology);
        }

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");

        Speciality dentistry = new Speciality();
        dentistry.setDescription("dentistry");

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

        System.out.println("Loaded owners, pets, pet types, visits, vets and specialities");
    }

}