package Day14;

import java.util.ArrayList;

public class Day14 {

    public static void main(String[] args) {
        ArrayList<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);
        int elf1 = 0;
        int elf2 = 1;
        int input = 110201;
        while (recipes.size() <= input * 2 + 10) {
            int newRecipe = recipes.get(elf1) + recipes.get(elf2);
            if (newRecipe >= 10) {
                recipes.add(newRecipe / 10);
                recipes.add(newRecipe % 10);
            }
            else {
                recipes.add(newRecipe);
            }
            elf1 = (elf1 + recipes.get(elf1) + 1) % recipes.size();
            elf2 = (elf2 + recipes.get(elf2) + 1) % recipes.size();
        }
        for (int i = input; i < input + 10; i++) {
            System.out.print(recipes.get(i));
        }
        System.out.println();


        recipes = new ArrayList<>();
        String recipesString = "37";
        recipes.add(3);
        recipes.add(7);
        elf1 = 0;
        elf2 = 1;
        String input2 = "110201";
        int counter = input2.length() - 8;
        while (true) {
            int newRecipe = recipes.get(elf1) + recipes.get(elf2);
            if (newRecipe >= 10) {
                recipes.add(newRecipe / 10);
                recipesString += newRecipe / 10;
                recipes.add(newRecipe % 10);
                recipesString += newRecipe % 10;
                counter += 2;
            }
            else {
                recipes.add(newRecipe);
                recipesString += newRecipe;
                counter += 1;
            }
            if (recipesString.contains(input2)) {
                System.out.println(recipesString.indexOf(input2));
                System.out.println(recipesString);
                System.out.println(recipes.size());
                break;
            }
            if (recipesString.length() - 5 > -1) {
                recipesString = recipesString.substring(recipesString.length() - 5);
            }
            elf1 = (elf1 + recipes.get(elf1) + 1) % recipes.size();
            elf2 = (elf2 + recipes.get(elf2) + 1) % recipes.size();
        }
    }
}
