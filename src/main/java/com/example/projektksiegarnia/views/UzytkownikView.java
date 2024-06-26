package com.example.projektksiegarnia.views;

import com.example.projektksiegarnia.DataBaseManager;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;

/**
 * Klasa reprezentująca widok encji Uzytkownik.
 * Reprezentuje tabelę uzytkownik w bazie danych.
 */

@Entity
@Table(name="uzytkownicy")
public class UzytkownikView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imie;

    @Column(nullable = false)
    private String nazwisko;

    @Column(nullable = false)
    private String email;
    /**
     *  metoda ta dodaje nowego uzytkownika do bazy
     *       @param imie imię użytkownika
     *       @param nazwisko nazwisko użytkownika
     *       @param email email użytkownika
     *
     */
    public static void AddNew(String imie, String nazwisko, String email){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        UzytkownikView user = new UzytkownikView();
        user.setImie(imie);
        user.setNazwisko(nazwisko);
        user.setEmail(email);

        s.merge(user);
        t.commit();
        s.close();
    }

    /**
     * Usuwa bieżącego użytkownika z bazy danych.
     */

    public void RemoveThis(){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.remove(this);
        t.commit();
        s.close();
    }
    /**
     *  metoda ta aktualziuje bierzacy uzytkownika w bazie
     *      @param newValues nowe wartosci w tablicy (id,imie,nazwisko,emial)
     *
     */
    public void UpdateThis(List<String> newValues){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        UzytkownikView g = s.get(UzytkownikView.class,getId());
        g.setId(Long.parseLong(newValues.get(0)));
        g.setImie(newValues.get(1));
        g.setNazwisko(newValues.get(2));
        g.setEmail(newValues.get(3));

        s.merge(g);
        t.commit();
        s.close();
    }

    /**
     *  metoda ta zwraca znormalizowane informacje na temat Uzytkownika
     *  @return String zawierajacy informacje o uzytkowniku (id,imie,nazwisko,email)
     */

    public String GetNormalizedInfo(){
        return getId() + "\t\t\t" + getImie() + "\t\t\t" + getNazwisko() + "\t\t\t" + getEmail();
    }

    /**
     * Konstruktor domyślny ustawiający wartości domyślne dla pol
     * id (minimalna wartosc long)
     * imie,nazwisko,emial = null
     *
     */
    public UzytkownikView(){
        this.id = Long.MIN_VALUE;
        this.imie = "null";
        this.nazwisko = "null";
        this.email = "null";
    }
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *  metoda ta zwraca wszystkie informacje na temat Uzytkownika
     *  @return String zawierajacy wszystkie informacje o uzytkowniku (id,imie,nazwisko,email)
     */
    public String GetFullInfo(){
        String id = getId() == Long.MIN_VALUE ? "null" : getId().toString();
        return id + "\t\t\t" + getImie() + "\t\t\t" + getNazwisko() + "\t\t\t" + getEmail() ;
    }
    public List<String> CurrentValuesAsString() {
        return Arrays.asList(getId().toString(),getImie(),getNazwisko(),getEmail());
    }
}
