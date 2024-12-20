package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RecipeFileHandler {
    private String filePath;

    public RecipeFileHandler() {
        filePath = "app/src/main/resources/recipes.txt";
    }

    public RecipeFileHandler(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 設問1: 一覧表示機能
     * recipes.txtからレシピデータを読み込み、それをリスト形式で返します。 <br> 
     * IOExceptionが発生したときは<i>Error reading file: 例外のメッセージ</i>とコンソールに表示します。
     *
     * @return レシピデータ
     */

    public ArrayList<String> readRecipes() {
        ArrayList<String> recipesData = new ArrayList<>();
        // recipes.txtからレシピデータを読み込む
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line = "";
            while((line = reader.readLine()) != null){
                recipesData.add(line);
            }
        //IOExceptionが発生したときはError reading file: 例外のメッセージとコンソールに表示します。
        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
        return recipesData;
    }

    /**
     * 設問2: 新規登録機能
     * 新しいレシピをrecipes.txtに追加します。<br>
     * レシピ名と材料はカンマ区切りで1行としてファイルに書き込まれます。
     *
     * @param recipeName レシピ名
     * @param ingredients 材料名
     */
     // 
    public void addRecipe(String recipeName, String ingredients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true)))  {
            // 改行
            writer.newLine();
            // 新しいレシピ(レシピ名、材料)をrecipes.txtに追加
            writer.write(recipeName + "," + ingredients);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Recipe added successfully.");
    }
}
