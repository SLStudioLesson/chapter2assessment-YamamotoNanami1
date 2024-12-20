package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;

import data.RecipeFileHandler;

public class RecipeUI {
    private BufferedReader reader;
    private RecipeFileHandler fileHandler;

    public RecipeUI() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        fileHandler = new RecipeFileHandler();
    }

    public RecipeUI(BufferedReader reader, RecipeFileHandler fileHandler) {
        this.reader = reader;
        this.fileHandler = fileHandler;
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        // 設問1: 一覧表示機能
                        displayRecipes();
                        break;
                    case "2":
                        // 設問2: 新規登録機能
                        addNewRecipe();
                        break;
                    case "3":
                        // 設問3: 検索機能
                        searchRecipe();
                        break;
                    case "4":
                        System.out.println("Exit the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    /**
     * 設問1: 一覧表示機能
     * RecipeFileHandlerから読み込んだレシピデータを整形してコンソールに表示します。
     */
    private void displayRecipes() {
        ArrayList<String> recipesData = new ArrayList<>();
        // RecipeFileHandlerからレシピデータを読み込む
        recipesData = fileHandler.readRecipes();
        // レシピデータを整形してコンソールに表示する
        for (String recipe : recipesData) {
            System.out.println("-----------------------------------");
            String[] keyValue = recipe.split(",",2);
            System.out.println("Recipe Name: " + keyValue[0]);
            System.out.println("Main Ingredients: " + keyValue[1]);
        }
    }

    /**
     * 設問2: 新規登録機能
     * ユーザーからレシピ名と主な材料を入力させ、RecipeFileHandlerを使用してrecipes.txtに新しいレシピを追加します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    private void addNewRecipe() throws IOException {
        // ユーザーからレシピ名を入力させる
        System.out.print("Enter recipe name: ");
        String recipeName = reader.readLine();
        // ユーザーから材料を入力させる
        System.out.print("Enter main ingredients (comma separated): ");
        String ingredients = reader.readLine();
        // RecipeFileHandlerを使用してrecipes.txtに新しいレシピを追加
        fileHandler.addRecipe(recipeName, ingredients);
    }

    /**
     * 設問3: 検索機能
     * ユーザーから検索クエリを入力させ、そのクエリに基づいてレシピを検索し、一致するレシピをコンソールに表示します。
     *
     * @throws java.io.IOException 入出力が受け付けられない
     */
    private void searchRecipe() throws IOException {
        System.out.print("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
        String query = reader.readLine();

        String name = "null";
        String ingredient = "null";

        // 入力されたクエリを分割しそれぞれレシピ名はnameに材料はingredientに代入
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue[0].equals("name")){
                name = keyValue[1];
            } else if (keyValue[0].equals("ingredient")){
                ingredient = keyValue[1];
            }
        }

        ArrayList<String> recipesData = new ArrayList<>();
        // RecipeFileHandlerからレシピデータを読み込む
        recipesData = fileHandler.readRecipes();

        ArrayList<String> recipesResult = new ArrayList<>();

        for (String recipe : recipesData) {
            String[] keyValue = recipe.split(",",2);
                if ((keyValue[0].indexOf(name) != -1) && (ingredient.equals("null"))) {
                    recipesResult.add(recipe);
                } else if ((name .equals("null")) && (keyValue[1].indexOf(ingredient) != -1)){
                    recipesResult.add(recipe);
                } else if ((keyValue[0].indexOf(name) != -1) && (keyValue[1].indexOf(ingredient) != -1)){
                    recipesResult.add(recipe);
                }
        }

        System.out.println("Search Results:");

        if(!recipesResult.isEmpty()){
            for (String result : recipesResult){
            System.out.println(result);
            }
        } else {
            System.out.println("No recipes found matching the criteria.");
        }
    }
}

