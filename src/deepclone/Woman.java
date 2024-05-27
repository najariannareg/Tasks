package deepclone;

import java.util.Arrays;

public class Woman implements Human {

    private String name;
    private int age;
    private String[] favoriteMovies;

    public Woman(String name, int age, String[] favoriteMovies) {
        this.name = name;
        this.age = age;
        this.favoriteMovies = favoriteMovies;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(String[] favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    @Override
    public String toString() {
        return "Woman{" +
                "name='" + getName() +
                ", age=" + getAge() +
                ", favoriteBooks=" + Arrays.toString(getFavoriteMovies()) +
                '}';
    }
}
