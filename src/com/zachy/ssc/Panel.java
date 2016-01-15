package com.zachy.ssc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Panel extends JPanel {

    Panel panel;

    JLabel lblTitle, lblSheet, lblRows, lblColumns, lblWidth, lblHeight, lblOutput;
    JTextField tfSheet, tfOutput;
    JButton btnSheet, btnConvert, btnOutput;
    JFileChooser fc;
    JSpinner spRows, spColumns, spWidth, spHeight;

    String sSheetPath, sOutputDir, sFileName = "tank";
    int nRows, nColumns, nWidth, nHeight;

    BufferedImage bimgSheet, bimgImages[];
    File fileImages[];

    public Panel() {
        setLayout(null);

        panel = this;

        initLabels();
        initTextFields();
        initButtons();
        initFileChoosers();
        initSpinners();
    }

    public void initLabels() {
        lblTitle = new JLabel("Sprite Sheet Splicer v1.0");
        lblTitle.setBounds(125, 15, 300, 25);
        add(lblTitle);

        lblSheet = new JLabel("Sprite Sheet:");
        lblSheet.setBounds(35, 65, 300, 25);
        add(lblSheet);

        lblRows = new JLabel("Rows:");
        lblRows.setBounds(35, 125, 100, 25);
        add(lblRows);

        lblColumns = new JLabel("Columns:");
        lblColumns.setBounds(200, 125, 100, 25);
        add(lblColumns);

        lblWidth = new JLabel("Width:");
        lblWidth.setBounds(35, 175, 100, 25);
        add(lblWidth);

        lblHeight = new JLabel("Height:");
        lblHeight.setBounds(210, 175, 100, 25);
        add(lblHeight);

        lblOutput = new JLabel("Ouput Folder:");
        lblOutput.setBounds(35, 225, 300, 25);
        add(lblOutput);
    }

    public void initTextFields() {
        tfSheet = new JTextField();
        tfSheet.setBounds(125, 65, 160, 25);
        add(tfSheet);

        tfOutput = new JTextField();
        tfOutput.setBounds(125, 225, 160, 25);
        add(tfOutput);
    }

    public void initButtons() {
        btnSheet = new JButton("...");
        btnSheet.setBounds(295, 65, 30, 25);
        btnSheet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                if (fc.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
                    sSheetPath = fc.getSelectedFile().getAbsolutePath();
                    tfSheet.setText(sSheetPath);
                }
            }
        });
        add(btnSheet);

        btnConvert = new JButton("Convert");
        btnConvert.setBounds(160, 275, 100, 25);
        btnConvert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSheet();
            }
        });
        add(btnConvert);

        btnOutput = new JButton("...");
        btnOutput.setBounds(295, 225, 30, 25);
        btnOutput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                if (fc.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) {
                    sOutputDir = fc.getCurrentDirectory().getAbsolutePath();
                    tfOutput.setText(sOutputDir);
                }
            }
        });
        add(btnOutput);
    }

    public void initFileChoosers() {
        fc = new JFileChooser();
    }

    public void initSpinners() {
        spRows = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        spRows.setBounds(75, 125, 100, 25);
        spRows.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                nRows = (Integer) spRows.getValue();
            }
        });
        add(spRows);

        spColumns = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        spColumns.setBounds(260, 125, 100, 25);
        spColumns.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                nColumns = (Integer) spColumns.getValue();
            }
        });
        add(spColumns);

        spWidth = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        spWidth.setBounds(75, 175, 100, 25);
        spWidth.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                nWidth = (Integer) spWidth.getValue();
            }
        });
        add(spWidth);

        spHeight = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        spHeight.setBounds(260, 175, 100, 25);
        spHeight.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                nHeight = (Integer) spHeight.getValue();
            }
        });
        add(spHeight);
    }

    public void loadSheet() {
        try {
            bimgSheet = ImageIO.read(new File(sSheetPath));
        } catch (IOException ex) {
            System.out.println(ex);
        }

        bimgImages = new BufferedImage[Integer.parseInt(nRows + "" + nColumns) + 1];
        fileImages = new File[Integer.parseInt(nRows + "" + nColumns) + 1];
        save();
    }

    public void save() {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                bimgImages[Integer.parseInt((i + 1) + "" + (j + 1))] = bimgSheet.getSubimage(nWidth * j, nHeight * i, nWidth, nHeight);
                fileImages[Integer.parseInt((i + 1) + "" + (j + 1))] = new File(sOutputDir + "\\" + sFileName + ((i + 1) + "" + (j + 1)) + ".png");
                try {
                    ImageIO.write(bimgImages[Integer.parseInt((i + 1) + "" + (j + 1))], "png", fileImages[Integer.parseInt((i + 1) + "" + (j + 1))]);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
