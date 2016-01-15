package com.zachy.ssc;

import javax.swing.JFrame;

public class Frame extends JFrame {

    public Frame() {
        setTitle("Sprite Sheet Splicer - Zach Lindner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        add(new Panel());
        setVisible(true);
    }
}
