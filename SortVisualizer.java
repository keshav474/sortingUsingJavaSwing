package com.company;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class SortVisualizer extends JPanel {
    //    private static final long serialVersionUID = 1L;
    private final int WIDTH = 1000, HEIGHT = WIDTH * 9 / 16;
    private final int SIZE = 150;
    private final float BAR_WIDTH = (float)WIDTH / SIZE;
    private float[] bar_height = new float[SIZE];
    private SwingWorker<Void, Void> shuffler, sorter;
    private int current_index, traversing_index;

    private SortVisualizer() throws InterruptedException {
//        setBackground(new Color(150,50,225));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        initBarHeight();

        insrtSorter();

        initShuffler();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(new Color(150,120,225));
        Rectangle2D.Float bar;
        for(int i = 0; i < SIZE; i++) {
            bar = new Rectangle2D.Float(i * BAR_WIDTH, HEIGHT-bar_height[i], BAR_WIDTH, bar_height[i]);
            g2d.fill(bar);
        }

        g2d.setColor(Color.LIGHT_GRAY);
        bar = new Rectangle2D.Float(current_index * BAR_WIDTH,
                HEIGHT-bar_height[current_index],
                BAR_WIDTH,
                bar_height[current_index]);
        g2d.fill(bar);

        g2d.setColor(Color.GREEN);
        bar = new Rectangle2D.Float(traversing_index * BAR_WIDTH,
                HEIGHT-bar_height[traversing_index],
                BAR_WIDTH,
                bar_height[traversing_index]);
        g2d.fill(bar);
    }

    private void insrtSorter() {
        sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                for(current_index = 1; current_index < SIZE; current_index++) {
                    traversing_index = current_index;
                    while(traversing_index > 0 &&
                            bar_height[traversing_index] < bar_height[traversing_index - 1]) {

                        swap(traversing_index, traversing_index - 1);
                        traversing_index--;

                        Thread.sleep(5);
                        repaint();
                    }
                }
                current_index = 0;
                traversing_index = 0;

                return null;
            }
        };
    }

    private void initShuffler() {
        shuffler = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                int middle = SIZE / 2;
                for(int i = 0, j = middle; i < middle; i++, j++) {
                    int random_index = new Random().nextInt(SIZE);
                    swap(i, random_index);

                    random_index = new Random().nextInt(SIZE);
                    swap(j, random_index);

                    Thread.sleep(1);
                    repaint();
                }

                return null;
            }

            @Override
            public void done() {
                super.done();
                sorter.execute();
            }
        };
        shuffler.execute();
    }

    private void initBarHeight() {
        float interval = (float)HEIGHT / SIZE;
        for(int i = 0; i < SIZE; i++)
        {
            int random_height = new Random().nextInt(HEIGHT-100);

            bar_height[i] = 25+random_height;

        }
    }

    private void swap(int indexA, int indexB) {
        float temp = bar_height[indexA];
        bar_height[indexA] = bar_height[indexB];
        bar_height[indexB] = temp;
    }

    private void selectSorter()
    {
        sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                for(current_index = 0; current_index < SIZE; current_index++) {
                    int min_idx=current_index;
                    for(traversing_index=current_index+1;traversing_index<SIZE;traversing_index++)
                    {
                        if(bar_height[traversing_index]<bar_height[min_idx])
                            min_idx=traversing_index;
                        Thread.sleep(2);
                        repaint();
                    }
                    swap(current_index,min_idx);
                    repaint();
                }
                current_index = 0;
                traversing_index = 0;

                return null;
            }
        };

    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Insertion Sort Visualizer");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                frame.setContentPane(new SortVisualizer());
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("srry");
            }
            frame.validate();
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}