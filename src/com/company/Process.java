package com.company;

/**
 * Created by Mati on 2017-05-02.
 */
public class Process {
    int load;

    public Process (int load){
        this.load=load;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    @Override
    public String toString() {
        return ("Wymagana moc obliczeniowa procesora: "+load);
    }
}
