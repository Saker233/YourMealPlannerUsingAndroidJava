package com.example.yourmealplanner.Home.model;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal")
public class Meal {

    @PrimaryKey
    @ColumnInfo(name = "idMeal")
    @NonNull
    private String idMeal;

    @ColumnInfo(name = "strMeal")
    private String strMeal;

    @ColumnInfo(name = "strCategory")
    private String strCategory;
    @ColumnInfo(name = "strArea")
    private String strArea;
    @ColumnInfo(name = "strInstructions")
    private String strInstructions;


    @ColumnInfo(name = "strMealThumb")
    private String strMealThumb;
    @ColumnInfo(name = "strYoutube")
    private String strYoutube;


    @ColumnInfo(name = "MealIngredient1")
    public String strIngredient1;

    @ColumnInfo(name = "MealIngredient2")
    public String strIngredient2;

    @ColumnInfo(name = "MealIngredient3")
    public String strIngredient3;

    @ColumnInfo(name = "MealIngredient4")
    public String strIngredient4;

    @ColumnInfo(name = "MealIngredient5")
    public String strIngredient5;

    @ColumnInfo(name = "MealIngredient6")
    public String strIngredient6;

    @ColumnInfo(name = "MealIngredient7")
    public String strIngredient7;

    @ColumnInfo(name = "MealIngredient8")
    public String strIngredient8;

    @ColumnInfo(name = "MealIngredient9")
    public String strIngredient9;

    @ColumnInfo(name = "MealIngredient10")
    public String strIngredient10;

    @ColumnInfo(name = "MealIngredient11")
    public String strIngredient11;

    @ColumnInfo(name = "MealIngredient12")
    public String strIngredient12;

    @ColumnInfo(name = "MealIngredient13")
    public String strIngredient13;

    @ColumnInfo(name = "MealIngredient14")
    public String strIngredient14;

    @ColumnInfo(name = "MealIngredient15")
    public String strIngredient15;

    @ColumnInfo(name = "MealIngredient16")
    public String strIngredient16;

    @ColumnInfo(name = "MealIngredient17")
    public String strIngredient17;

    @ColumnInfo(name = "MealIngredient18")
    public String strIngredient18;

    @ColumnInfo(name = "MealIngredient19")
    public String strIngredient19;

    @ColumnInfo(name = "MealIngredient20")
    public String strIngredient20;

    @ColumnInfo(name = "isFav")
    public boolean isFav = false;


    @ColumnInfo(name = "weekday")
    public String weekday = null;

    public boolean isFav() {
        return isFav;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getStrIngredients() {
        StringBuilder ingredients = new StringBuilder();

        // Add each ingredient if it's not null or empty
        if (strIngredient1 != null && !strIngredient1.isEmpty()) ingredients.append(strIngredient1).append("\n");
        if (strIngredient2 != null && !strIngredient2.isEmpty()) ingredients.append(strIngredient2).append("\n");
        if (strIngredient3 != null && !strIngredient3.isEmpty()) ingredients.append(strIngredient3).append("\n");
        if (strIngredient4 != null && !strIngredient4.isEmpty()) ingredients.append(strIngredient4).append("\n");
        if (strIngredient5 != null && !strIngredient5.isEmpty()) ingredients.append(strIngredient5).append("\n");
        if (strIngredient6 != null && !strIngredient6.isEmpty()) ingredients.append(strIngredient6).append("\n");
        if (strIngredient7 != null && !strIngredient7.isEmpty()) ingredients.append(strIngredient7).append("\n");
        if (strIngredient8 != null && !strIngredient8.isEmpty()) ingredients.append(strIngredient8).append("\n");
        if (strIngredient9 != null && !strIngredient9.isEmpty()) ingredients.append(strIngredient9).append("\n");
        if (strIngredient10 != null && !strIngredient10.isEmpty()) ingredients.append(strIngredient10).append("\n");
        if (strIngredient11 != null && !strIngredient11.isEmpty()) ingredients.append(strIngredient11).append("\n");
        if (strIngredient12 != null && !strIngredient12.isEmpty()) ingredients.append(strIngredient12).append("\n");
        if (strIngredient13 != null && !strIngredient13.isEmpty()) ingredients.append(strIngredient13).append("\n");
        if (strIngredient14 != null && !strIngredient14.isEmpty()) ingredients.append(strIngredient14).append("\n");
        if (strIngredient15 != null && !strIngredient15.isEmpty()) ingredients.append(strIngredient15).append("\n");
        if (strIngredient16 != null && !strIngredient16.isEmpty()) ingredients.append(strIngredient16).append("\n");
        if (strIngredient17 != null && !strIngredient17.isEmpty()) ingredients.append(strIngredient17).append("\n");
        if (strIngredient18 != null && !strIngredient18.isEmpty()) ingredients.append(strIngredient18).append("\n");
        if (strIngredient19 != null && !strIngredient19.isEmpty()) ingredients.append(strIngredient19).append("\n");
        if (strIngredient20 != null && !strIngredient20.isEmpty()) ingredients.append(strIngredient20).append("\n");


        return ingredients.toString().trim();
    }

    public boolean getFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrIngredient10() {
        return strIngredient10;
    }

    public void setStrIngredient10(String strIngredient10) {
        this.strIngredient10 = strIngredient10;
    }

    public String getStrIngredient11() {
        return strIngredient11;
    }

    public void setStrIngredient11(String strIngredient11) {
        this.strIngredient11 = strIngredient11;
    }

    public String getStrIngredient12() {
        return strIngredient12;
    }

    public void setStrIngredient12(String strIngredient12) {
        this.strIngredient12 = strIngredient12;
    }

    public String getStrIngredient13() {
        return strIngredient13;
    }

    public void setStrIngredient13(String strIngredient13) {
        this.strIngredient13 = strIngredient13;
    }

    public String getStrIngredient14() {
        return strIngredient14;
    }

    public void setStrIngredient14(String strIngredient14) {
        this.strIngredient14 = strIngredient14;
    }

    public String getStrIngredient15() {
        return strIngredient15;
    }

    public void setStrIngredient15(String strIngredient15) {
        this.strIngredient15 = strIngredient15;
    }

    public String getStrIngredient16() {
        return strIngredient16;
    }

    public void setStrIngredient16(String strIngredient16) {
        this.strIngredient16 = strIngredient16;
    }

    public String getStrIngredient17() {
        return strIngredient17;
    }

    public void setStrIngredient17(String strIngredient17) {
        this.strIngredient17 = strIngredient17;
    }

    public String getStrIngredient18() {
        return strIngredient18;
    }

    public void setStrIngredient18(String strIngredient18) {
        this.strIngredient18 = strIngredient18;
    }

    public String getStrIngredient19() {
        return strIngredient19;
    }

    public void setStrIngredient19(String strIngredient19) {
        this.strIngredient19 = strIngredient19;
    }

    public String getStrIngredient1() {
        return strIngredient1;
    }

    public void setStrIngredient1(String strIngredient1) {
        this.strIngredient1 = strIngredient1;
    }

    public String getStrIngredient20() {
        return strIngredient20;
    }

    public void setStrIngredient20(String strIngredient20) {
        this.strIngredient20 = strIngredient20;
    }

    public String getStrIngredient2() {
        return strIngredient2;
    }

    public void setStrIngredient2(String strIngredient2) {
        this.strIngredient2 = strIngredient2;
    }

    public String getStrIngredient3() {
        return strIngredient3;
    }

    public void setStrIngredient3(String strIngredient3) {
        this.strIngredient3 = strIngredient3;
    }

    public String getStrIngredient4() {
        return strIngredient4;
    }

    public void setStrIngredient4(String strIngredient4) {
        this.strIngredient4 = strIngredient4;
    }

    public String getStrIngredient5() {
        return strIngredient5;
    }

    public void setStrIngredient5(String strIngredient5) {
        this.strIngredient5 = strIngredient5;
    }

    public String getStrIngredient6() {
        return strIngredient6;
    }

    public void setStrIngredient6(String strIngredient6) {
        this.strIngredient6 = strIngredient6;
    }

    public String getStrIngredient7() {
        return strIngredient7;
    }

    public void setStrIngredient7(String strIngredient7) {
        this.strIngredient7 = strIngredient7;
    }

    public String getStrIngredient8() {
        return strIngredient8;
    }

    public void setStrIngredient8(String strIngredient8) {
        this.strIngredient8 = strIngredient8;
    }

    public String getStrIngredient9() {
        return strIngredient9;
    }

    public void setStrIngredient9(String strIngredient9) {
        this.strIngredient9 = strIngredient9;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }
}

