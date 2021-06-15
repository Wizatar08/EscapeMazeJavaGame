package com.wizatar08.escapemaze.visuals;

import java.io.File;
import java.util.ArrayList;

public class Cluster {
    private ArrayList<Tex> texes;
    private ArrayList<Float> xDisplacements, yDisplacements;
    private int amount, radius, rotation;

    public Cluster(Tex tex, int amount, int radius) {
        this(new Tex[]{tex}, amount, radius);
    }

    public Cluster(Tex[] texList, int amount, int radius) {
        this(texList, amount, radius, 0);
    }

    public Cluster(Tex[] texList, int amount, int radius, int rotation) {
        this.amount = amount;
        this.radius = radius;
        this.rotation = rotation;

        this.texes = new ArrayList<>();
        this.xDisplacements = new ArrayList<>();
        this.yDisplacements = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            texes.add(texList[(int) Math.floor(Math.random() * texList.length)]);
            xDisplacements.add((float) (Math.random() * 2 * radius) - radius);
            yDisplacements.add((float) (Math.random() * 2 * radius) - radius);
        }
    }

    public void draw(float x, float y) {
        for (int i = 0; i < texes.size(); i++) {
            texes.get(i).draw(x + xDisplacements.get(i), y + yDisplacements.get(i), rotation);
        }
    }

    public static Tex[] getTexInFolder(String folderName) {
        File folder = new File("src/resources/images/" + folderName).getAbsoluteFile();
        File[] files = folder.listFiles();
        ArrayList<Tex> texArray = new ArrayList<>();
        for (File file : files) {
            if (file.toString().endsWith(".png")) {
                String fileName = file.getName().replace(".png", "");
                texArray.add(new Tex(folderName + "/" + fileName));
            }
        }
        Tex[] texList = new Tex[texArray.size()];
        for (int i = 0; i < texArray.size(); i++) {
            texList[i] = texArray.get(i);
        }
        return texList;
    }

    public int getTexAmount() {
        return amount;
    }

    public int getRadius() {
        return radius;
    }

    public ArrayList<Tex> getTextures() {
        return texes;
    }
}