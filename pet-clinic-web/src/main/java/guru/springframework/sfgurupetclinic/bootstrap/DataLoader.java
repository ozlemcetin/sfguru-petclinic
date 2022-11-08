package guru.springframework.sfgurupetclinic.bootstrap;

import guru.springframework.sfgurupetclinic.model.*;
import guru.springframework.sfgurupetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final PetService petService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, PetService petService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.petService = petService;
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


        PetType savedCatPetType = null;
        {
            PetType petType = new PetType();
            petType.setName("Cat");

            savedCatPetType = petTypeService.save(petType);
        }


        {
            Owner owner1 = Owner.builder().firstName("Michael").lastName("Weston").address("123 Brickerel").city("Miami").telephone("1231231234").pets(new HashSet<>()).build();

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

        {
            Owner owner2 = new Owner();
            owner2.setFirstName("Fiona");
            owner2.setLastName("Glenanne");
            owner2.setAddress("123 Brickerel");
            owner2.setCity("Miami");
            owner2.setTelephone("1231231234");

            //pets
            {
                Pet fionasCat = new Pet();
                fionasCat.setName("Ray");
                fionasCat.setBirthDate(LocalDate.now());
                fionasCat.setOwner(owner2);
                fionasCat.setPetType(savedCatPetType);

                //visits
                {
                    {
                        Visit visit1 = new Visit();
                        visit1.setDate(LocalDate.now());
                        visit1.setDescription("Sneezy Kitty");
                        visit1.setPet(fionasCat);

                        fionasCat.getVisits().add(visit1);
                    }

                    {
                        Visit visit2 = new Visit();
                        visit2.setDate(LocalDate.now());
                        visit2.setDescription("Broken front paw");
                        visit2.setPet(fionasCat);

                        fionasCat.getVisits().add(visit2);
                    }
                }

                //add
                owner2.getPets().add(fionasCat);
            }

            ownerService.save(owner2);
        }

        Pet fionasDog = null;
        {
            Owner owner = ownerService.findByLastName("Glenanne");

            fionasDog = new Pet();
            fionasDog.setName("Sunny");
            fionasDog.setBirthDate(LocalDate.now());
            fionasDog.setOwner(owner);
            fionasDog.setPetType(savedDogPetType);

            petService.save(fionasDog);
        }

        {
            Visit visit = new Visit();
            visit.setDate(LocalDate.now());
            visit.setDescription("Dog Washing");
            visit.setPet(fionasDog);

            visitService.save(visit);
        }


        Speciality savedRadiology = null;
        {
            Speciality radiology = new Speciality();
            radiology.setDescription("Radiology");

            savedRadiology = specialityService.save(radiology);
        }

        Speciality savedSurgery = null;
        {
            Speciality surgery = new Speciality();
            surgery.setDescription("Surgery");

            savedSurgery = specialityService.save(surgery);
        }

        Speciality savedDentistry = null;
        {
            Speciality dentistry = new Speciality();
            dentistry.setDescription("Dentistry");

            savedDentistry = specialityService.save(dentistry);
        }

        {
            Vet vet1 = new Vet();
            vet1.setFirstName("Sam");
            vet1.setLastName("Axe");

            {
                vet1.getSpecialities().add(savedRadiology);
                vet1.getSpecialities().add(savedSurgery);
            }

            vetService.save(vet1);
        }

        {
            Vet vet2 = new Vet();
            vet2.setFirstName("Jessie");
            vet2.setLastName("Porter");

            {
                vet2.getSpecialities().add(savedSurgery);
                vet2.getSpecialities().add(savedDentistry);
            }

            vetService.save(vet2);
        }

        System.out.println("Loaded owners, pets, pet types, visits, vets and specialities");
    }

}