package guru.springframework.sfgurupetclinic.bootstrap;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.model.Vet;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import guru.springframework.sfgurupetclinic.services.VetService;
import guru.springframework.sfgurupetclinic.services.map.OwnerServiceMap;
import guru.springframework.sfgurupetclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;

    private final VetService vetService;

    public DataLoader() {
        ownerService = new OwnerServiceMap();
        vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        {
            Owner owner1 = new Owner();
            owner1.setId(1L);
            owner1.setFirstName("Michael");
            owner1.setLastName("Weston");

            ownerService.save(owner1);
        }


        {
            Owner owner2 = new Owner();
            owner2.setId(2L);
            owner2.setFirstName("Fiona");
            owner2.setLastName("Glenanne");

            ownerService.save(owner2);
        }

        {
            Vet vet1 = new Vet();
            vet1.setId(1L);
            vet1.setFirstName("Sam");
            vet1.setLastName("Axe");


            vetService.save(vet1);
        }

        {
            Vet vet2 = new Vet();
            vet2.setId(2L);
            vet2.setFirstName("Jessie");
            vet2.setLastName("Porter");

            vetService.save(vet2);
        }

        System.out.println("Loaded owners and vets");
    }
}
