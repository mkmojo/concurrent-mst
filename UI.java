// Class UI is the user interface.  It displays a Surface canvas above
// a row of buttons and a row of statistics.  Actions (event handlers)
// are defined for each of the buttons.  Depending on the state of the
// UI, either the "run" or the "pause" button is the default (highlighted in
// most window systems); it will often self-push if you hit carriage return.
//

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.util.concurrent.ConcurrentSkipListSet;
class UI extends JPanel {
    private final Coordinator coordinator;
    private final Surface surface;
    private final Animation animation;

    private final JRootPane root;
    private static final int externalBorder = 6;

    private static final int stopped = 0;
    private static final int running = 1;
    private static final int paused = 2;

    private int state = stopped;
    private long elapsedTime = 0;
    private long startTime;

    private final JLabel time = new JLabel("time: 0");

    public void updateTime() {
        Date d = new Date();
        elapsedTime += (d.getTime() - startTime);
        time.setText(String.format("time: %d.%03d", elapsedTime/1000,
                                                    elapsedTime%1000));
    }

    // Constructor
    //
    public UI(Coordinator C, Surface S,
            Animation A, long SD, RootPaneContainer pane) {
        final UI ui = this;
        coordinator = C;
        surface = S;
        animation = A;

        final JPanel buttons = new JPanel();   // button panel
            final JButton runButton = new JButton("Run");
            final JButton pauseButton = new JButton("Pause");
            final JButton resetButton = new JButton("Reset");
            final JButton randomizeButton = new JButton("Randomize");
            final JButton quitButton = new JButton("Quit");

        final JPanel stats = new JPanel();   // statistics panel

        final JLabel seed = new JLabel("seed: " + SD + "   ");

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (state == stopped) {
                    state = running;
                    root.setDefaultButton(pauseButton);
                    Worker w = new Worker(surface, coordinator,
                                          ui, animation);
                    Date d = new Date();
                    startTime = d.getTime();
                    w.start();
                } else if (state == paused) {
                    state = running;
                    root.setDefaultButton(pauseButton);
                    Date d = new Date();
                    startTime = d.getTime();
                    coordinator.toggle();
                }
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (state == running) {
                    updateTime();
                    state = paused;
                    root.setDefaultButton(runButton);
                    coordinator.toggle();
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state = stopped;
                coordinator.stop();
                root.setDefaultButton(runButton);
                surface.reset();
                animation.reset();
                elapsedTime = 0;
                time.setText("time: 0");
            }
        });
        randomizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                state = stopped;
                coordinator.stop();
                root.setDefaultButton(runButton);
                long v = surface.randomize();
                animation.reset();
                seed.setText("seed: " + v + "   ");
                elapsedTime = 0;
                time.setText("time: 0");
            }
        });
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // put the buttons into the button panel:
        buttons.setLayout(new FlowLayout());
        buttons.add(runButton);
        buttons.add(pauseButton);
        buttons.add(resetButton);
        buttons.add(randomizeButton);
        buttons.add(quitButton);

        // put the labels into the statistics panel:
        stats.add(seed);
        stats.add(time);

        // put the Surface canvas, the button panel, and the stats
        // label into the UI:
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(externalBorder,
            externalBorder, externalBorder, externalBorder));
        add(A);
        add(buttons);
        add(stats);

        // put the UI into the Frame
        pane.getContentPane().add(this);
        root = getRootPane();
        root.setDefaultButton(runButton);
    }
}

