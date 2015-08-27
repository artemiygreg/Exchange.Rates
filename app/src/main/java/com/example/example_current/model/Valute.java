package com.example.example_current.model;

/**
 * Created by Admin on 16.08.15.
 */
public class Valute {
    private String name;
    private int numCode;
    private String id;
    private float value;
    private String charCode;
    private int nominal;
    private boolean favorite;

    public Valute(String name, int numCode, String id, float value, String charCode, int nominal, boolean favorite) {
        this.name = name;
        this.numCode = numCode;
        this.id = id;
        this.value = value;
        this.charCode = charCode;
        this.nominal = nominal;
        this.favorite = favorite;
    }

    private Valute(Builder builder) {
        this.name = builder.name;
        this.numCode = builder.numCode;
        this.id = builder.id;
        this.value = builder.value;
        this.charCode = builder.charCode;
        this.nominal = builder.nominal;
        this.favorite = builder.favorite;
    }

    public String getName() {
        return name;
    }

    public int getNumCode() {
        return numCode;
    }

    public String getId() {
        return id;
    }

    public float getValue() {
        return value;
    }

    public String getCharCode() {
        return charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void resetFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public static class Builder {
        private String name;
        private int numCode;
        private String id;
        private float value;
        private String charCode;
        private int nominal;
        private boolean favorite;

        public Builder() {

        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setNumCode(int numCode){
            this.numCode = numCode;
            return this;
        }

        public Builder setId(String id){
            this.id = id;
            return this;
        }

        public Builder setValue(float value){
            this.value = value;
            return this;
        }

        public Builder setCharCode(String charCode){
            this.charCode = charCode;
            return this;
        }

        public Builder setNominal(int nominal){
            this.nominal = nominal;
            return this;
        }

        public Builder setFavorite(boolean favorite){
            this.favorite = favorite;
            return this;
        }

        public Valute create(){
            return new Valute(this);
        }
    }
}
