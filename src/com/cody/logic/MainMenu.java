package com.cody.logic;

import org.osier.ui.ImageLabel;
import org.osier.ui.TextButton;
import org.osier.ui.Window;

public class MainMenu {

    private static TextButton create;
    private static TextButton load;
    private static ImageLabel banner;
    public static void init(Window window) {
        create = new TextButton();
        create.setText("Create");
        create.setName("CreateButton");
        create.setSize(100,0,24,0);
        create.setPosition(-100,0.5f,-16,0.75f);
        create.setParent(window);

        load = new TextButton();
        load.setName("LoadButton");
        load.setText("Load");
        load.setSize(100,0,24,0);
        load.setPosition(4,0.5f,-16,0.75f);
        load.setParent(window);

        banner = new ImageLabel();
        banner.setName("Banner");
        banner.setSize(200,0,100,0);
        banner.setPosition(-98,0.5f,-116,0.75f);
        banner.setImage(banner.loadImageFromFile("src/data/icons/Banner.png"));
        banner.setParent(window);
    }

    
}
