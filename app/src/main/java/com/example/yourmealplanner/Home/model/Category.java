package com.example.yourmealplanner.Home.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category")
public class Category {


    @PrimaryKey
    @ColumnInfo(name = "idCategory")
    @NonNull
    private String idCategory;


    @ColumnInfo(name = "strCategory")
    private String strCategory;

    @ColumnInfo(name = "strCategoryThumb")
    private String strCategoryThumb;

    @ColumnInfo(name = "strCategoryDescription")
    private String strCategoryDescription;

    public String getIdCategory() { return idCategory; }
    public void setIdCategory(String idCategory) { this.idCategory = idCategory; }

    public String getStrCategory() { return strCategory; }
    public void setStrCategory(String strCategory) { this.strCategory = strCategory; }

    public String getStrCategoryThumb() { return strCategoryThumb; }
    public void setStrCategoryThumb(String strCategoryThumb) { this.strCategoryThumb = strCategoryThumb; }

    public String getStrCategoryDescription() { return strCategoryDescription; }
    public void setStrCategoryDescription(String strCategoryDescription) { this.strCategoryDescription = strCategoryDescription; }
}
