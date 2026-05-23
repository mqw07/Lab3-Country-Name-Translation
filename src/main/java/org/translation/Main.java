package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator();
        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            String quit = "quit";
            if (country.equals(quit)) {
                break;
            }

            CountryCodeConverter ccc = new CountryCodeConverter();
            String countryCode = ccc.fromCountry(country).toLowerCase();
            String language = promptForLanguage(translator, countryCode);
            if (language.equals(quit)) {
                break;
            }

            LanguageCodeConverter lcc = new LanguageCodeConverter();
            String code = lcc.fromLanguage(language).toLowerCase();

            System.out.print(country + " in " + language + " is " + translator.translate(countryCode, code) + "\n");
            System.out.print("Press enter to continue or quit to exit.\n");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (quit.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        CountryCodeConverter ccc = new CountryCodeConverter();
        for (int i = 0; i < countries.size(); i++) {
            countries.set(i, ccc.fromCountryCode(countries.get(i)));
        }
        Collections.sort(countries);
        for (int i = 0; i < countries.size(); i++) {
            System.out.print(countries.get(i) + "\n");
        }

        System.out.print("select a country from above:\n");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {

        LanguageCodeConverter lcc = new LanguageCodeConverter();

        List<String> codes = translator.getCountryLanguages(country);
        for (int i = 0; i < codes.size(); i++) {
            codes.set(i, lcc.fromLanguageCode(codes.get(i)));
        }
        Collections.sort(codes);
        for (String lang : codes) {
            System.out.print(lang + "\n");
        }
        System.out.print("select a language from above:\n");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
