package com.android.citizens.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Citizen implements Parcelable {

    private static final String[] FIRST_NAMES = new String[]{"Кирилл", "Никита", "Александр", "Артём", "Андрей", "Даниил", "Илья", "Ярослав", "Егор", "Максим"};
    private static final String[] LAST_NAMES = new String[]{"Иванов", "Кузнецов", "Попов", "Смирнов", "Васильев", "Петров", "Соколов", "Михайлов", "Новиков", "Фёдоров"};
    private static final String[] WORK_PLACES = new String[]{"Лукойл", "Уралкалий", "Газпром", "ПермЭнергосбыт", "Эр-Телеком", "Уралхим", "Метафракс", "Новатэк", "Новомет", "Пермцветмет"};
    private static final String[] GENDERS = new String[]{"М", "Ж"};
    private static final String[] LIVING_AREAS = new String[]{"Дзержинский", "Ленинский", "Свердловский", "Мотовилихинский", "Орджоникидзевский", "Кировский", "Индустриальный"};
    private static final boolean[] CARS = new boolean[]{true, false};

    private String firstName;
    private String lastName;
    private Long age;
    private String workPlace;
    private String gender;
    private String livingArea;
    private Boolean car;

    public Citizen() {
    }

    public Citizen(String firstName, String lastName, Long age, String workPlace, String gender, String livingArea, Boolean car) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.workPlace = workPlace;
        this.gender = gender;
        this.livingArea = livingArea;
        this.car = car;
    }

    protected Citizen(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        if (in.readByte() == 0) {
            age = null;
        } else {
            age = in.readLong();
        }
        workPlace = in.readString();
        gender = in.readString();
        livingArea = in.readString();
        byte tmpCar = in.readByte();
        car = tmpCar == 0 ? null : tmpCar == 1;
    }

    public static final Creator<Citizen> CREATOR = new Creator<Citizen>() {
        @Override
        public Citizen createFromParcel(Parcel in) {
            return new Citizen(in);
        }

        @Override
        public Citizen[] newArray(int size) {
            return new Citizen[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(String livingArea) {
        this.livingArea = livingArea;
    }

    public Boolean getCar() {
        return car;
    }

    public void setCar(Boolean car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Citizen citizen = (Citizen) o;
        return Objects.equals(firstName, citizen.firstName) &&
                Objects.equals(lastName, citizen.lastName) &&
                Objects.equals(age, citizen.age) &&
                Objects.equals(workPlace, citizen.workPlace) &&
                Objects.equals(gender, citizen.gender) &&
                Objects.equals(livingArea, citizen.livingArea) &&
                Objects.equals(car, citizen.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, workPlace, gender, livingArea, car);
    }

    public static Citizen getRandom(int minAge, int maxAge) {
        return new Citizen(
                FIRST_NAMES[(int) (Math.random() * FIRST_NAMES.length)],
                LAST_NAMES[(int) (Math.random() * LAST_NAMES.length)],
                (long) ((Math.random() * (maxAge - minAge + 1)) + minAge),
                WORK_PLACES[(int) (Math.random() * WORK_PLACES.length)],
                GENDERS[(int) (Math.random() * GENDERS.length)],
                LIVING_AREAS[(int) (Math.random() * LIVING_AREAS.length)],
                CARS[(int) (Math.random() * CARS.length)]
        );
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", workPlace='" + workPlace + '\'' +
                ", gender='" + gender + '\'' +
                ", livingArea='" + livingArea + '\'' +
                ", car=" + car +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        if (age == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(age);
        }
        parcel.writeString(workPlace);
        parcel.writeString(gender);
        parcel.writeString(livingArea);
        parcel.writeByte((byte) (car == null ? 0 : car ? 1 : 2));
    }
}
