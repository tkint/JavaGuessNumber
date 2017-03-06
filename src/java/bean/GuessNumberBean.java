/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    private ResourceBundle msg = ResourceBundle.getBundle("bean.message.message");

    private String phrase;
    private int number;
    private int propose;
    private int step = 0;
    private int min;
    private int max;
    private int essais;
    private int maxEssais;
    private List<Integer> history;

    @PostConstruct
    public void init() {
        this.step = 0;
        this.min = 0;
        this.max = 100;
        this.propose = 1;
        this.essais = 0;
        this.maxEssais = 10;
        this.history = new ArrayList<Integer>();
    }

    public String initMinMax() {
        this.step = 1;
        this.phrase = msg.getString("guessPhrase") + " " + this.min + " " + msg.getString("andWord") + " " + this.max + ".";
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

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String nextStep() {
        this.step++;
        return "index";
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

    public List<Integer> getHistory() {
        return history;
    }

    public void setHistory(List<Integer> history) {
        this.history = history;
    }

    public String renewSession() {
        this.step = 0;
        this.essais = 0;
        this.propose = 1;
//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }

    public String comparer() {
        if (getEssais() == getMaxEssais()) {
            setPhrase(msg.getString("outOfChances"));
        } else {
            if (getPropose() == getNumber()) {
                setPhrase(msg.getString("guessRight"));
                this.history.add(essais + 1);
            } else if (getPropose() > getNumber()) {
                setPhrase(msg.getString("guessTooHigh"));
            } else {
                setPhrase(msg.getString("guessTooLow"));
            }
            incrementeEssais();
        }
        return "guess";
    }
    
    public String goToHistory() {
        return "history";
    }
}
