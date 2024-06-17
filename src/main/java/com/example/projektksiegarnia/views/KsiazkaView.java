package com.example.projektksiegarnia.views;

import com.example.projektksiegarnia.DataBaseManager;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.Date;
/**
 * Klasa reprezentująca widok encji Ksiazki.
 * Reprezentuje tabelę ksiazki w bazie danych.
 */
@Entity
@Table(name="ksiazki")
public class KsiazkaView {
    /**
     * id encji (generowany automatycznie)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "tytul_id")
    private TytulView tytul;

    @ManyToOne
    @JoinColumn(name = "gatunek_id")
    private GatunekView gatunek;

    @ManyToOne
    @JoinColumn(name = "wydawnictwo_id")
    private WydawnictwoView wydawnictwo;

    @Column(name = "rok_wydania")
    private LocalDate rokWydania;

    @ManyToOne
    @JoinColumn(name = "jezyk_id")
    private JezykView jezyk;

    @ManyToOne
    @JoinColumn(name = "uzytkownik_id")
    private UzytkownikView uzytkownik;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TytulView getTytul() {
        return tytul;
    }

    public void setTytul(TytulView tytul) {
        this.tytul = tytul;
    }

    public GatunekView getGatunek() {
        return gatunek;
    }

    public void setGatunek(GatunekView gatunek) {
        this.gatunek = gatunek;
    }

    public WydawnictwoView getWydawnictwo() {
        return wydawnictwo;
    }

    public void setWydawnictwo(WydawnictwoView wydawnictwo) {
        this.wydawnictwo = wydawnictwo;
    }

    public LocalDate getRokWydania() {
        return rokWydania;
    }

    public void setRokWydania(LocalDate rokWydania) {
        this.rokWydania = rokWydania;
    }

    public JezykView getJezyk() {
        return jezyk;
    }

    public void setJezyk(JezykView jezyk) {
        this.jezyk = jezyk;
    }

    /**
     * Metoda ta dodaje nową książkę do bazy danych.
     * @param TytulID id tytulu książki
     * @param GatunekID id gatunku książki
     * @param WydawnictwoID id wydawnictwa książki
     * @param DataWydania data wydania książki
     * @param JezykID id jezyka książki
     * @param UserID id użytkownika przypisanego do książki
     */
    public static void AddNew(Long TytulID, Long GatunekID, Long WydawnictwoID, LocalDate DataWydania, Long JezykID, Long UserID){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        KsiazkaView ksiazka = new KsiazkaView();
        ksiazka.setTytul(s.get(TytulView.class,TytulID));
        ksiazka.setGatunek(s.get(GatunekView.class,GatunekID));
        ksiazka.setWydawnictwo(s.get(WydawnictwoView.class,WydawnictwoID));
        ksiazka.setRokWydania(DataWydania);
        ksiazka.setJezyk(s.get(JezykView.class,JezykID));
        if(UserID == Long.MIN_VALUE)
            ksiazka.setUzytkownik(null);
        else
            ksiazka.setUzytkownik(s.get(UzytkownikView.class,UserID));

        s.merge(ksiazka);
        t.commit();
        s.close();
    }
    /**
     *  metoda ta usuwa bierzaca ksiazke z bazy
     */
    public void RemoveThis(){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.remove(this);
        t.commit();
        s.close();
    }
    /**
     * Metoda ta zwraca uzytkownika przypisanego do ksiazki
     * jesli zaden nie jest przypisany zwraca nowego uzytkownika {@link UzytkownikView} z parametrami bazowymi
     */
    public UzytkownikView getUzytkownik() {
        if(uzytkownik == null)
            return new UzytkownikView();
        return uzytkownik;
    }
    /**
     * Metoda ta ustawia uzytkownika przypisanego do ksiazki
     * @param uzytkownik nowy uzytkownik przypisany do ksiazki
     */
    public void setUzytkownik(UzytkownikView uzytkownik) {
        this.uzytkownik = uzytkownik;
    }
    /**
     *  metoda ta zwraca znormalizowane informacje na temat tutulu
     *  @return String zawierajacy informacje o ksiazce (tutu,gatunek,jezyk,rokwydania,wydawnictwo)
     */
    public String GetNormalizedInfo(){
        return getTytul().getNazwa() + "\t\t\t" + getGatunek().getNazwa() + "\t\t\t" + getJezyk().getNazwa() + "\t\t\t" + getRokWydania().toString() + "\t\t\t" + getWydawnictwo().getNazwa();
    }

}
