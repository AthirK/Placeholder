package com.example.AsciiAPI.ascii;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsciiService
{
    private final AsciiRepository asciiRepository;

    /**
     * Creates an Ascii object with some parameters.
     *
     * @param artist the artist who made the Ascii-art
     * @param date the date when the art was uploaded to database.
     * @param title the name of the Ascii-art
     * @param art Ascii-art
     * @return ascii object with JSON body with all the above params.
     */
    public Ascii createAscii(
            String artist,
            LocalDate date,
            String title,
            String art
    )
    {
        if (title == null || title.isBlank())
        {
            throw new IllegalArgumentException("Title may not be null or empty");
        }

        Ascii ascii = new Ascii(artist, date, title, art);

        asciiRepository.save(ascii);

        return ascii;
    }

    /**
     * This method deletes an Ascii-art based on the title from the database.
     *
     * @param title the title of the Ascii-art being deleted
     */
    public void deleteAscii(String title)
    {
        Ascii ascii = asciiRepository.findByTitle(title).orElseThrow(() -> new IllegalArgumentException(title + " not found"));
        asciiRepository.delete(ascii);
    }

    /**
     * This method finds an Ascii-art by title.
     *
     * @param title refers to the title of the Ascii searched for.
     * @return returns the Ascii of the title searches for.
     */
    public Ascii searchByTitle(String title)
    {
        return asciiRepository.findByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("No Ascii art found for title: " + title));
     }

    /**
     * A method that displays all Ascii-artwork in the database.
     *
     * @return returns all Ascii-artworks
     */
    public List<Ascii> viewAsciis()
    {
        return asciiRepository.findAll();
    }

    // Generate and save sample Ascii artworks
    @Transactional
    public List<Ascii> generateAsciiSamples()
    {
        List<Ascii> samples = new ArrayList<>();

        samples.add(new Ascii("Alice", LocalDate.of(2022, 5, 1), "Smiley Face", ":)"));
        samples.add(new Ascii("Bob", LocalDate.of(2021, 8, 15), "Winking Face", ";)"));
        samples.add(new Ascii("Charlie", LocalDate.of(2023, 1, 10), "Heart", "<3"));
        samples.add(new Ascii("Diana", LocalDate.of(2020, 11, 25), "Cat", "^_^"));
        samples.add(new Ascii("Eve", LocalDate.of(2019, 6, 30), "Shrug", "¯\\_(ツ)_/¯"));
        samples.add(new Ascii("Frank", LocalDate.of(2023, 3, 8), "Table Flip", "(╯°□°）╯︵ ┻━┻"));
        samples.add(new Ascii("Grace", LocalDate.of(2018, 9, 22), "Flower", "@}-,-'---"));

        // group 3 ascii art

        // Athir
        //samples.add(new Ascii("Athir", LocalDate.of(2020, 11, 25), "Cat", "^_^"));

        // Johanna
        //samples.add(new Ascii("Eve", LocalDate.of(2019, 6, 30), "Shrug", "¯\\_(ツ)_/¯"));

        // Lisa
        //samples.add(new Ascii("Frank", LocalDate.of(2023, 3, 8), "Table Flip", "(╯°□°）╯︵ ┻━┻"));

        // Magnus
        //samples.add(new Ascii("Grace", LocalDate.of(2018, 9, 22), "Flower", "@}-,-'---"));

        return asciiRepository.saveAll(samples);
    }
}
