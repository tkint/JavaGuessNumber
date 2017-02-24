/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author tkint
 */
@Named(value = "guessNumberBean")
@SessionScoped
public class GuessNumberBean implements Serializable {

    private Locale locale;
    private ResourceBundle translator = ResourceBundle.getBundle("bean.message.message");

    private String phrase;
    private int number;
    private int propose;
    private int min;
    private int max;
    private int essais;
    private int maxEssais;

    @PostConstruct
    public void init() {
        this.locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        this.min = -1;
        this.max = -1;
        this.propose = 1;
        this.essais = 0;
        this.maxEssais = 10;
    }

    public String initMinMax() {
        this.phrase = translator.getString("guessPhrase") + " " + this.min + " " + translator.getString("and") + " " + this.max + ".";
        this.number = (int) this.min + (int) (Math.random() * ((this.max - this.min) + 1));
        return "guess";
    }

    /**
     * Creates a new instance of GuessNumberBean
     */
    public GuessNumberBean() {
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPropose() {
        return propose;
    }

    public void setPropose(int propose) {
        this.propose = propose;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getEssais() {
        return essais;
    }

    public void setEssais(int essais) {
        this.essais = essais;
    }

    private void incrementeEssais() {
        this.essais++;
    }

    public int getMaxEssais() {
        return maxEssais;
    }

    public void setMaxEssais(int maxEssais) {
        this.maxEssais = maxEssais;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getLangue() {
        return locale.getLanguage();
    }

    public String renewSession() {
        this.min = -1;
        this.max = -1;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }

    public String switchLocale() {
        if (this.getLangue() == "fr") {
            Locale l = new Locale("en");
            setLocale(l);
            FacesContext.getCurrentInstance().getViewRoot().setLocale(l);
        } else {
            Locale l = new Locale("fr");
            setLocale(l);
            FacesContext.getCurrentInstance().getViewRoot().setLocale(l);
        }
        return "index";
    }

    public String comparer() {
        if (getEssais() == getMaxEssais()) {
            setPhrase(translator.getString("outOfChances"));
        } else {
            if (getPropose() == getNumber()) {
                setPhrase(translator.getString("guessRight"));
            } else if (getPropose() > getNumber()) {
                setPhrase(translator.getString("guessTooHigh"));
            } else {
                setPhrase(translator.getString("guessTooLow"));
            }
            incrementeEssais();
        }
        return "guess";
    }
}
