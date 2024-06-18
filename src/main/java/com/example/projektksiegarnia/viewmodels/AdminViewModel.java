package com.example.projektksiegarnia.viewmodels;

import com.example.projektksiegarnia.DataBaseManager;
import com.example.projektksiegarnia.SceneManager;
import com.example.projektksiegarnia.views.*;
import constants.AdminOptions;
import constants.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
/**
 * klasa AdminViewModel odpowiada za logikę interfejsu Administatora
 */
public class AdminViewModel {
    @FXML
    VBox MainContainer;

    @FXML
    Button FooterButton;
    /**
     * metoda ta odpowiada za inicjalizuje AdminViewModel.
     */
    @FXML
    void initialize(){
        FooterButton.setOnAction(_->Logout());
        FooterButton.setText("Wyloguj");
    }
    /**
     * metoda ta obsługuje zdarzenie kliknięcia przycisku "Dodaj".
     */
    @FXML
    void OnAddClicked(){
        ShowOptions(AdminOptions.Add);
    }
    /**
     * metoda ta obsługuje zdarzenie kliknięcia przycisku "Edytuj".
     */
    @FXML
    void OnEditClicked(){
        ShowOptions(AdminOptions.Edit);
    }
    /**
     * metoda ta obsługuje zdarzenie kliknięcia przycisku "Usun".
     */
    @FXML
    void OnRemoveClicked(){
        ShowOptions(AdminOptions.Remove);
    }


    /**
     * Metoda ta wyświetla opcje w zależności od wybranej opcji AdminOptions.
     * @param options wybrana opcja AdminOptions (dodaj,edytuj,usun).
     */
    void ShowOptions(AdminOptions options){
        MainContainer.getChildren().clear();
        FooterButton.setText("Powrót");
        FooterButton.setOnAction(_->Return());
        VBox box = new VBox();
        box.setSpacing(30d);
        Button[] buttons = new Button[6];

        for(int i=0;i<buttons.length;i++){
            buttons[i] = new Button();
        }

        buttons[0].setText("Gatunki");
        buttons[1].setText("Jezyki");
        buttons[2].setText("Ksiazki");
        buttons[3].setText("Tytuly");
        buttons[4].setText("Uzytkownicy");
        buttons[5].setText("Wydawnictwa");

        switch (options) {
            case Add -> {
                for(int i=0;i<buttons.length;i++){
                    Class type = GetDataType(buttons[i].getText());
                    buttons[i].setOnAction(_ -> AddData(type));
                }
            }
            case Edit -> {
                for(int i=0;i<buttons.length;i++){
                    Class type = GetDataType(buttons[i].getText());
                    buttons[i].setOnAction(_ -> EditData(type));
                }
            }
            case Remove -> {
                for(int i=0;i<buttons.length;i++){
                    Class type = GetDataType(buttons[i].getText());
                    buttons[i].setOnAction(_ -> RemoveData(type));
                }
            }
        }

        for(int i=0;i<buttons.length;i++){
            box.getChildren().add(buttons[i]);
        }


        MainContainer.getChildren().add(box);

    }
    /**
     * metoda ta przygotowuje pola do dodania nowych danych okreslonego typu
     * @param type typ danych do dodania
     */
    void AddData(Class type){
        GenerateTextFieldsForNewData(type);
    }

    /**
     * metoda ta przygotowuje pola do edytowania nowych danych okreslonego typu
     * @param type typ danych do edytowania
     */
    void EditData(Class type){
        MainContainer.getChildren().clear();
        Session s = DataBaseManager.getSessionFactory().openSession();
        List<Object> q = s.createQuery("FROM " + type.getSimpleName()).list();

        for(int i=0;i<q.size();i++) {
            HBox box = new HBox();
            box.setSpacing(20d);
            Button b = new Button();
            Label t = new Label();
            b.setText("Edit");
            if (type.equals(GatunekView.class)) {
                GatunekView g = (GatunekView) q.get(i);
                t.setText(g.GetFullInfo());
                b.setOnAction(_-> GenerateTextFieldsForUpdate(g,g.getClass().getDeclaredFields(), g.CurrentValuesAsString(), type));
            }
            if (type.equals(JezykView.class)) {
                JezykView g = (JezykView) q.get(i);
                t.setText(g.GetFullInfo());
                b.setOnAction(_-> GenerateTextFieldsForUpdate(g,g.getClass().getDeclaredFields(), g.CurrentValuesAsString(), type));
            }
            if (type.equals(KsiazkaView.class)) {
                KsiazkaView g = (KsiazkaView) q.get(i);
                t.setText(g.GetFullInfo());
                b.setOnAction(_-> GenerateTextFieldsForUpdate(g,g.getClass().getDeclaredFields(), g.CurrentValuesAsString(), type));
            }
            if (type.equals(TytulView.class)) {
                TytulView g = (TytulView) q.get(i);
                t.setText(g.GetFullInfo());
                b.setOnAction(_-> GenerateTextFieldsForUpdate(g,g.getClass().getDeclaredFields(), g.CurrentValuesAsString(), type));
            }
            if (type.equals(UzytkownikView.class)) {
                UzytkownikView g = (UzytkownikView) q.get(i);
                t.setText(g.GetFullInfo());
                b.setOnAction(_-> GenerateTextFieldsForUpdate(g,g.getClass().getDeclaredFields(), g.CurrentValuesAsString(), type));
            }
            if (type.equals(WydawnictwoView.class)) {
                WydawnictwoView g = (WydawnictwoView) q.get(i);
                t.setText(g.GetFullInfo());
                b.setOnAction(_-> GenerateTextFieldsForUpdate(g,g.getClass().getDeclaredFields(), g.CurrentValuesAsString(), type));
            }
            box.getChildren().add(t);
            box.getChildren().add(b);
            MainContainer.getChildren().add(box);
            TextField tt = new TextField("");
            tt.setEditable(false);
            MainContainer.getChildren().add(tt);
        }
    }
    /**
     * metoda ta usuwa dane określonego typu.
     * @param type typ danych do usunięcia
     */
    void RemoveData(Class type){
        MainContainer.getChildren().clear();
        Session s = DataBaseManager.getSessionFactory().openSession();
        List<Object> q = s.createQuery("FROM " + type.getSimpleName()).list();

        for(int i=0;i<q.size();i++){
            HBox box = new HBox();
            box.setSpacing(20d);
            Button b = new Button();
            Label t = new Label();
            b.setText("Remove");
            if(type.equals(GatunekView.class)){
                GatunekView g = (GatunekView)q.get(i);
                t.setText(g.GetNormalizedInfo());
                b.setOnAction(_->{
                    g.RemoveThis();
                    RemoveData(type);
                });
            }
            if(type.equals(JezykView.class)){
                JezykView g = (JezykView) q.get(i);
                t.setText(g.GetNormalizedInfo());
                b.setOnAction(_->{
                    g.RemoveThis();
                    RemoveData(type);
                });
            }
            if(type.equals(KsiazkaView.class)){
                KsiazkaView g = (KsiazkaView) q.get(i);
                t.setText(g.GetNormalizedInfo());
                b.setOnAction(_->{
                    g.RemoveThis();
                    RemoveData(type);
                });
            }
            if(type.equals(TytulView.class)){
                TytulView g = (TytulView) q.get(i);
                t.setText(g.GetNormalizedInfo());
                b.setOnAction(_->{
                    g.RemoveThis();
                    RemoveData(type);
                });
            }
            if(type.equals(UzytkownikView.class)){
                UzytkownikView g = (UzytkownikView) q.get(i);
                t.setText(g.GetNormalizedInfo());
                b.setOnAction(_->{
                    g.RemoveThis();
                    RemoveData(type);
                });
            }
            if(type.equals(WydawnictwoView.class)){
                WydawnictwoView g = (WydawnictwoView) q.get(i);
                t.setText(g.GetNormalizedInfo());
                b.setOnAction(_->{
                    g.RemoveThis();
                    RemoveData(type);
                });
            }
            box.getChildren().add(t);
            box.getChildren().add(b);
            MainContainer.getChildren().add(box);
        }
    }
    /**
     * metoda ta generuje pola tekstowe do aktualizacji
     * @param o obiekt reprezentujący dane do aktualizacji
     * @param fields pola obiektu do aktualizacji
     * @param currentData bieżące wartości danych
     * @param type typ danych do aktualizacji
     */
    void GenerateTextFieldsForUpdate(Object o,Field[] fields, List<String> currentData, Class type){
        MainContainer.getChildren().clear();
        VBox box = new VBox();
        box.setSpacing(30d);

        Label text = new Label();
        //td usunac view z konca
        text.setText("Edytowanie wybranego : " + type.getSimpleName());
        box.getChildren().add(text);
        //lista danych wprowadzonych w polach tekstowych
        ArrayList<TextField> newData = new ArrayList<>();
        // sprawdź typ obiektu i generuj odpowiednie pola
        // utworz 7 box do organizacji pól
        if(type.equals(KsiazkaView.class)){
            HBox[] hboxs = new HBox[7];
            for (int i=0;i<hboxs.length;i++){
                hboxs[i] = new HBox();
                hboxs[i].setSpacing(20d);
            }
            TextField idt = new TextField();
            idt.setText(currentData.get(0));
            idt.setEditable(false);

            ComboBox<String> tids = new ComboBox<String>();
            tids.getItems().clear();
            tids.setValue(currentData.get(1));

            ComboBox<String> gids = new ComboBox<String>();
            gids.getItems().clear();
            gids.setValue(currentData.get(2));

            ComboBox<String> wids = new ComboBox<String>();
            wids.getItems().clear();
            wids.setValue(currentData.get(3));

            TextField dt = new TextField();
            dt.setText(currentData.get(4));

            ComboBox<String> jids = new ComboBox<String>();
            jids.getItems().clear();
            jids.setValue(currentData.get(5));

            ComboBox<String> uids = new ComboBox<String>();
            uids.getItems().clear();
            uids.setValue(currentData.get(6));
            // wszystkie dostępne ID dla rozwijanych list
            tids.getItems().addAll(GetAllIds(TytulView.class.getSimpleName()));
            gids.getItems().addAll(GetAllIds(GatunekView.class.getSimpleName()));
            wids.getItems().addAll(GetAllIds(WydawnictwoView.class.getSimpleName()));
            jids.getItems().addAll(GetAllIds(JezykView.class.getSimpleName()));
            uids.getItems().addAll(GetAllIds(UzytkownikView.class.getSimpleName()));
            //etukiety
            Label idtl = new Label("ID Ksiazki");
            Label tl = new Label("ID Tytulu");
            Label gl = new Label("ID Gatunku");
            Label wl = new Label("ID Wydawnictwa");
            Label dl = new Label("Data Wydania");
            Label jl = new Label("ID Jezyka");
            Label ul = new Label("ID Uzytkownika");
            // elementy do hboxow
            hboxs[0].getChildren().add(idtl);
            hboxs[0].getChildren().add(idt);
            hboxs[1].getChildren().add(tl);
            hboxs[1].getChildren().add(tids);
            hboxs[2].getChildren().add(gl);
            hboxs[2].getChildren().add(gids);
            hboxs[3].getChildren().add(wl);
            hboxs[3].getChildren().add(wids);
            hboxs[4].getChildren().add(dl);
            hboxs[4].getChildren().add(dt);
            hboxs[5].getChildren().add(jl);
            hboxs[5].getChildren().add(jids);
            hboxs[6].getChildren().add(ul);
            hboxs[6].getChildren().add(uids);

            for (int i=0;i<hboxs.length;i++){
                box.getChildren().add(hboxs[i]);
            }
            //przycisk do aktualizacji
            Button b = new Button();
            b.setText("Wykonaj");
            b.setOnAction(_->{
                ExecuteUpdate(o,type,Arrays.asList(idt.getText(),tids.getValue(),gids.getValue(),wids.getValue(),dt.getText(),jids.getValue(),uids.getValue()));
                OnEditClicked();
            });
            box.getChildren().add(b);
        }else{
            //literacja przez wszystkie pola i generowanie pol tekstowych
            for (int i=0;i<fields.length;i++){
                HBox boxh = new HBox();
                boxh.setSpacing(20d);

                Label l = new Label();
                l.setText(fields[i].getName());

                TextField t = new TextField();
                t.setText(currentData.get(i));
                if(fields[i].getName().equals("id"))
                    t.setEditable(false);
                newData.add(t);

                boxh.getChildren().add(l);
                boxh.getChildren().add(t);

                box.getChildren().add(boxh);
            }
            //dodawanie przycisku dodaj do boxa
            Button b = new Button();
            b.setText("Wykonaj");
            b.setOnAction(_->{
                ExecuteUpdate(o,type,newData.stream().map(e->e.getText()).toList());
                OnEditClicked();
            });
            box.getChildren().add(b);
        }
        //aktualizowanie glownego kontenera
        MainContainer.getChildren().add(box);
    }
    /**
     * metoda ta wykonuje operację aktualizacji dla okreslonego typu danych
     * @param o Obiekt reprezentujący dane do aktualizacji
     * @param type Typ danych do aktualizacji
     * @param values Nowe wartości dla danych
     */
    void ExecuteUpdate(Object o,Class type, List<String> values){
        if(type.equals(GatunekView.class)){
            GatunekView g = (GatunekView) o;
            g.UpdateThis(values);
            return;
        }
        if(type.equals(JezykView.class)){
            JezykView g = (JezykView) o;
            g.UpdateThis(values);
            return;
        }
        if(type.equals(KsiazkaView.class)){
            KsiazkaView g = (KsiazkaView) o;
            g.UpdateThis(values);
            return;
        }
        if(type.equals(TytulView.class)){
            TytulView g = (TytulView) o;
            g.UpdateThis(values);
            return;
        }
        if(type.equals(UzytkownikView.class)){
            UzytkownikView g = (UzytkownikView) o;
            g.UpdateThis(values);
            return;
        }
        if(type.equals(WydawnictwoView.class)){
            WydawnictwoView g = (WydawnictwoView) o;
            g.UpdateThis(values);
            return;
        }


    }
    /**
     * metoda ta generuje pola tekstowe
     * @param type typ danych do dodania
     */
    void GenerateTextFieldsForNewData(Class type){
        MainContainer.getChildren().clear();
        VBox box = new VBox();
        box.setSpacing(30d);

        Label text = new Label();
        //td usunac view z konca
        text.setText("Dodawanie: " + type.getSimpleName());
        box.getChildren().add(text);

        if(type.equals(KsiazkaView.class)){
            HBox[] hboxs = new HBox[6];
            for (int i=0;i<hboxs.length;i++){
                hboxs[i] = new HBox();
                hboxs[i].setSpacing(20d);
            }

            ComboBox<String> tids = new ComboBox<String>();
            ComboBox<String> gids = new ComboBox<String>();
            ComboBox<String> wids = new ComboBox<String>();
            TextField dt = new TextField();
            ComboBox<String> jids = new ComboBox<String>();
            ComboBox<String> uids = new ComboBox<String>();

            tids.getItems().addAll(GetAllIds(TytulView.class.getSimpleName()));
            gids.getItems().addAll(GetAllIds(GatunekView.class.getSimpleName()));
            wids.getItems().addAll(GetAllIds(WydawnictwoView.class.getSimpleName()));
            jids.getItems().addAll(GetAllIds(JezykView.class.getSimpleName()));
            uids.getItems().addAll(GetAllIds(UzytkownikView.class.getSimpleName()));

            Label tl = new Label("ID Tytulu");
            Label gl = new Label("ID Gatunku");
            Label wl = new Label("ID Wydawnictwa");
            Label dl = new Label("Data Wydania");
            Label jl = new Label("ID Jezyka");
            Label ul = new Label("ID Uzytkownika");

            hboxs[0].getChildren().add(tl);
            hboxs[0].getChildren().add(tids);
            hboxs[1].getChildren().add(gl);
            hboxs[1].getChildren().add(gids);
            hboxs[2].getChildren().add(wl);
            hboxs[2].getChildren().add(wids);
            hboxs[3].getChildren().add(dl);
            hboxs[3].getChildren().add(dt);
            hboxs[4].getChildren().add(jl);
            hboxs[4].getChildren().add(jids);
            hboxs[5].getChildren().add(ul);
            hboxs[5].getChildren().add(uids);

            for (int i=0;i<hboxs.length;i++){
                box.getChildren().add(hboxs[i]);
            }

            Button b = new Button();
            b.setText("Wykonaj");
            b.setOnAction(_->ExecuteAddData(type, Arrays.asList(tids.getValue(),gids.getValue(),wids.getValue(),dt.getText(),jids.getValue(),uids.getValue())));
            box.getChildren().add(b);
        }
        else{
            Field[] f = type.getDeclaredFields();
            ArrayList<TextField> data = new ArrayList<>();
            for (int i=0;i<f.length;i++){
                HBox boxh = new HBox();
                boxh.setSpacing(20d);

                Label l = new Label();
                l.setText(f[i].getName());

                TextField t = new TextField();
                if(f[i].getName().equals("id")){
                    t.setEditable(false);
                    t.setText("auto uzupelniane");
                }

                data.add(t);

                boxh.getChildren().add(l);
                boxh.getChildren().add(t);
                box.getChildren().add(boxh);
            }
            Button b = new Button();
            b.setText("Wykonaj");
            b.setOnAction(_->ExecuteAddData(type,data.stream().map(e->e.getText()).toList()));
            box.getChildren().add(b);
        }

        MainContainer.getChildren().add(box);
    }
    /**
     * wykonuje operację dodawania danych.
     * @param type typ danych do dodania
     * @param values nowe wartosci danych
     */
    void ExecuteAddData(Class type, List<String> values){
        if(type.equals(GatunekView.class)){
            GatunekView.AddNew(values.get(1));
            return;
        }
        if(type.equals(JezykView.class)){
            JezykView.AddNew(values.get(1));
            return;
        }
        if(type.equals(KsiazkaView.class)){
            Long uid = (values.get(5) == null ||values.get(5).equals("")) ? Long.MIN_VALUE : Long.parseLong(values.get(5));
            LocalDate data = LocalDate.parse(values.get(3));
            KsiazkaView.AddNew(Long.parseLong(values.get(0)),Long.parseLong(values.get(1)),Long.parseLong(values.get(2)),data,Long.parseLong(values.get(4)),uid);
            return;
        }
        if(type.equals(TytulView.class)){
            TytulView.AddNew(values.get(1));
            return;
        }
        if(type.equals(UzytkownikView.class)){
            UzytkownikView.AddNew(values.get(1),values.get(2),values.get(3));
            return;
        }
        if(type.equals(WydawnictwoView.class)){
            WydawnictwoView.AddNew(values.get(1));
            return;
        }
    }
    /**
     * Pobiera wszystkie id dla danego typu.
     * @param typename nazwa typu dla ktorego pobierane są id
     * @return lista id
     */
    List<String> GetAllIds(String typename){
        Session s = DataBaseManager.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        return s.createQuery("SELECT id FROM "+typename).list().stream().map(e->e.toString()).toList();
    }

    void Logout(){
        SceneManager.LoadScene(Scenes.Login);
    }
    /**
     * metoda ta wraca do poprzedniego interfejsu po edycji
     */
    void Return(){
        MainContainer.getChildren().clear();
        FooterButton.setOnAction(_->Logout());
        FooterButton.setText("Wyloguj");
    }
    /**
     * metoda ta pbiera typ danych na podstawie nazwy danych
     * @param DataName nazwa danych (np. "Gatunki", "Jezyki").
     * @return typ danych odpowiadający nazwie
     */
    Class GetDataType(String DataName){
        switch (DataName){
            case "Gatunki" -> {
                return GatunekView.class;
            }
            case "Jezyki" ->{
                return JezykView.class;
            }
            case "Ksiazki"->{
                return KsiazkaView.class;
            }
            case "Tytuly"->{
                return TytulView.class;
            }
            case "Uzytkownicy"->{
                return UzytkownikView.class;
            }
            case "Wydawnictwa"->{
                return WydawnictwoView.class;
            }
            default -> {
                System.out.println("Literowka");
                return null;
            }
        }
    }
}
