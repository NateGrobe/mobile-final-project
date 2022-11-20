package com.example.finalproject;

public class Volumes {
    private int volumeSize;

    private String VolumeUnit;

    public Volumes(int size, String unit){
        this.volumeSize = size;
        this.VolumeUnit = unit;

    }

    public Volumes(){

    }

    public String getVolumeUnit(){
        return VolumeUnit;
    }

    public int getVolumeSize(){
        return volumeSize;
    }

    public void setVolumeUnit(String VolumeUnit) {
        this.VolumeUnit = VolumeUnit;
    }

    public void setVolumeSize(int volumeSize) {
        this.volumeSize = volumeSize;
    }
}
