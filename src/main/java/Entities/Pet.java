package Entities;

public class Pet {
    private String nameCategory;
    private String namePet;
    private String urlPhoto;
    private String nomeTag;

    public Pet(String nameCategory,
               String namePet,
               String urlPhoto,
               String nomeTag) {
        this.nameCategory = nameCategory;
        this.namePet = namePet;
        this.urlPhoto = urlPhoto;
        this.nomeTag = nomeTag;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getNamePet() {
        return namePet;
    }

    public void setNamePet(String namePet) {
        this.namePet = namePet;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getNomeTag() {
        return nomeTag;
    }

    public void setNomeTag(String nomeTag) {
        this.nomeTag = nomeTag;
    }
}
