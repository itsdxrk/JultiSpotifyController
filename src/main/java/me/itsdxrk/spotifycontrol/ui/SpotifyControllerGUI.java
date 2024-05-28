package me.itsdxrk.spotifycontrol.ui;

import me.itsdxrk.spotifycontrol.SpotifyControllerOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class SpotifyControllerGUI extends JFrame {

    private static SpotifyControllerGUI instance = null;

    private JPanel mainPanel;
    private JButton saveButton;
    private final JRadioButton netherBtn = new JRadioButton("Nether");
    private final JRadioButton bastionBtn = new JRadioButton("Bastion");
    private final JRadioButton fortBtn = new JRadioButton("Fortress");
    private final JRadioButton fpBtn = new JRadioButton("First Portal");
    private final JRadioButton strongholdBtn = new JRadioButton("Stronghold");
    private final JRadioButton endBtn = new JRadioButton("Enter End");
    private final ArrayList<JRadioButton> buttons = new ArrayList<>();
    public static ButtonGroup buttonGroup = new ButtonGroup();
    private boolean closed = false;
    private static int triggerID = 1;
    private static String triggerEvent = "rsg.enter_bastion";

    public SpotifyControllerGUI() {
        setupWindow();
        this.setTitle("Spotify Controller");
        this.setContentPane(mainPanel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                SpotifyControllerGUI.this.onClose();
            }
        });
        saveButton.addActionListener(e -> this.save());

        netherBtn.addActionListener(e -> {
            if (netherBtn.isSelected()) SpotifyControllerGUI.triggerID = 0; SpotifyControllerGUI.triggerEvent = "rsg.enter_nether";
        });
        bastionBtn.addActionListener(e -> {
            if (bastionBtn.isSelected()) SpotifyControllerGUI.triggerID = 1; SpotifyControllerGUI.triggerEvent = "rsg.enter_bastion";
        });
        fortBtn.addActionListener(e -> {
            if (fortBtn.isSelected()) SpotifyControllerGUI.triggerID = 2; SpotifyControllerGUI.triggerEvent = "rsg.enter_fortress";
        });
        fpBtn.addActionListener(e -> {
            if (fpBtn.isSelected()) SpotifyControllerGUI.triggerID = 3; SpotifyControllerGUI.triggerEvent = "rsg.first_portal";
        });
        strongholdBtn.addActionListener(e -> {
            if (strongholdBtn.isSelected()) SpotifyControllerGUI.triggerID = 4; SpotifyControllerGUI.triggerEvent = "rsg.enter_stronghold";
        });
        endBtn.addActionListener(e -> {
            if (endBtn.isSelected()) SpotifyControllerGUI.triggerID = 5; SpotifyControllerGUI.triggerEvent = "rsg.enter_end";
        });

        SpotifyControllerOptions options = SpotifyControllerOptions.getInstance();

        buttonGroup.setSelected(buttons.get(options.triggerID).getModel(), true);

        this.revalidate();
        this.setMinimumSize(new Dimension(500, 50));
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    private void setupWindow() {
        mainPanel = new JPanel();
        saveButton = new JButton("Save");

        buttonGroup.add(netherBtn);
        buttonGroup.add(bastionBtn);
        buttonGroup.add(fortBtn);
        buttonGroup.add(fpBtn);
        buttonGroup.add(strongholdBtn);
        buttonGroup.add(endBtn);

        mainPanel.add(saveButton);
        mainPanel.add(netherBtn);
        mainPanel.add(bastionBtn);
        mainPanel.add(fortBtn);
        mainPanel.add(fpBtn);
        mainPanel.add(strongholdBtn);
        mainPanel.add(endBtn);

        buttons.add(netherBtn);
        buttons.add(bastionBtn);
        buttons.add(fortBtn);
        buttons.add(fpBtn);
        buttons.add(strongholdBtn);
        buttons.add(endBtn);
    }

    public static SpotifyControllerGUI open(Point initialLocation) {
        if (instance == null || instance.isClosed()) {
            instance = new SpotifyControllerGUI();
            if (initialLocation != null) {
                instance.setLocation(initialLocation);
            }
        } else {
            instance.requestFocus();
        }
        return instance;
    }

    private void save() {
        SpotifyControllerOptions options = SpotifyControllerOptions.getInstance();
        options.triggerID = triggerID;
        options.triggerEvent = triggerEvent;
        try {
            SpotifyControllerOptions.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    private void onClose() {
        this.closed = true;
    }
}
