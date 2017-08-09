package com.example.ec;

import com.example.ec.domain.Difficulty;
import com.example.ec.domain.Region;
import com.example.ec.services.TourPackageService;
import com.example.ec.services.TourService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ExplorecaliApplication implements CommandLineRunner {
	@Autowired
    TourPackageService tourPackageService;
	@Autowired
    TourService tourService;

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Create the default tour packages
		tourPackageService.createTourPackage("BC", "Backpack Cal");
		tourPackageService.createTourPackage("CC", "California Calm");
		tourPackageService.createTourPackage("CH", "California Hot springs");
		tourPackageService.createTourPackage("CY", "Cycle California");
		tourPackageService.createTourPackage("DS", "From Desert to Sea");
		tourPackageService.createTourPackage("KC", "Kids California");
		tourPackageService.createTourPackage("NW", "Nature Watch");
		tourPackageService.createTourPackage("SC", "Snowboard Cali");
		tourPackageService.createTourPackage("TC", "Taste of California");

		tourPackageService.lookup().forEach(tourPackage -> System.out.println(tourPackage));

		TourFromFile.importTours().forEach(tourFromFile -> {
		    tourService.createTour(
		            tourFromFile.title,
                    tourFromFile.description,
                    tourFromFile.blurb,
                    Integer.parseInt(tourFromFile.price),
                    tourFromFile.length,
                    tourFromFile.bullets,
                    tourFromFile.keywords,
                    tourFromFile.packageType,
                    Difficulty.valueOf(tourFromFile.difficulty),
                    Region.findByLabel(tourFromFile.region)
                    );
        });

		System.out.println("Total number of tours in database: " + tourService.count());
	}

	/*
	 * Helper class to load the records from ExploreCalifornia.json
	 */
	private static class TourFromFile {

	        private String packageType, title, description, blurb, price, length, bullets, keywords, difficulty, region;

	        static List<TourFromFile> importTours() throws IOException {
	        return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                    .readValue(TourFromFile.class.getResourceAsStream("/ExploreCalifornia.json"),
                            new TypeReference<List<TourFromFile>>(){});
        }
    }
}
