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
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Panel extends JPanel {

    Panel panel;
    JLabel lblTitle, lblSheet, lblName, lblRows, lblColumns, lblOutput;
    JTextField tfSheet, tfName, tfOutput;
    JButton btnSheet, btnConvert, btnOutput;
    JFileChooser fc;
    JSpinner spRows, spColumns;
    JProgressBar pbBar;
    String sSheetPath, sOutputDir, sFileName;
    int nRows = 1, nColumns = 1, nWidth, nHeight;
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
        initProgressBar();
    }

    private void initLabels() {
        lblTitle = new JLabel("Sprite Sheet Splicer v1.1");
        lblTitle.setBounds(125, 15, 300, 25);
        add(lblTitle);

        lblSheet = new JLabel("Sprite Sheet:");
        lblSheet.setBounds(35, 65, 300, 25);
        add(lblSheet);

        lblName = new JLabel("Image Names:");
        lblName.setBounds(35, 120, 300, 25);
        add(lblName);

        lblRows = new JLabel("Rows:");
        lblRows.setBounds(35, 175, 100, 25);
        add(lblRows);

        lblColumns = new JLabel("Columns:");
        lblColumns.setBounds(200, 175, 100, 25);
        add(lblColumns);

        lblOutput = new JLabel("Ouput Folder:");
        lblOutput.setBounds(35, 225, 300, 25);
        add(lblOutput);
    }

    private void initTextFields() {
        tfSheet = new JTextField();
        tfSheet.setBounds(125, 65, 160, 25);
        add(tfSheet);

        tfName = new JTextField();
        tfName.setBounds(125, 120, 160, 25);
        add(tfName);

        tfOutput = new JTextField();
        tfOutput.setBounds(125, 225, 160, 25);
        add(tfOutput);
    }

    private void initButtons() {
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
                load();
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

    private void initFileChoosers() {
        fc = new JFileChooser();
    }

    private void initSpinners() {
        spRows = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        spRows.setBounds(75, 175, 100, 25);
        spRows.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                nRows = (Integer) spRows.getValue();
            }
        });
        add(spRows);

        spColumns = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        spColumns.setBounds(260, 175, 100, 25);
        spColumns.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                nColumns = (Integer) spColumns.getValue();
            }
        });
        add(spColumns);
    }

    private void initProgressBar() {
        pbBar = new JProgressBar();
        pbBar.setBounds(130, 315, 150, 20);
        add(pbBar);
    }

    private void load() {
        try {
            bimgSheet = ImageIO.read(new File(sSheetPath));
            nWidth = bimgSheet.getWidth() / nColumns;
            nHeight = bimgSheet.getHeight() / nRows;
        } catch (IOException ex) {
            System.out.println(ex);
        }

        bimgImages = new BufferedImage[Integer.parseInt(nRows + "" + nColumns) + 1];
        fileImages = new File[Integer.parseInt(nRows + "" + nColumns) + 1];
        save();
    }

    private void save() {
        pbBar.setIndeterminate(true);

        sFileName = tfName.getText();
        if (sFileName.equals("")) {
            sFileName = "image";
        }

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                bimgImages[Integer.parseInt((i + 1) + "" + (j + 1))] = bimgSheet.getSubimage(nWidth * j, nHeight * i, nWidth, nHeight);
                fileImages[Integer.parseInt((i + 1) + "" + (j + 1))] = new File(sOutputDir + "\\" + sFileName + ((i + 1) + "" + (j + 1)) + ".png");
                try {
                    ImageIO.write(bimgImages[Integer.parseInt((i + 1) + "" + (j + 1))], "png", fileImages[Integer.parseInt((i + 1) + "" + (j + 1))]);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }
        pbBar.setIndeterminate(false);
    }
}
