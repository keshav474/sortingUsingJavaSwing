package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.*;

class sortingArea extends JPanel {
//    private static final long serialVersionUID = 1L;
    private final int WIDTH = 1000, HEIGHT = WIDTH * 8 / 16;
    private final int SIZE = 10;
    private final float BAR_WIDTH = (float)WIDTH / SIZE;
    private float[] bar_height = new float[SIZE];
    private SwingWorker<Void,Void> shuffler, sorter;
    private int current_index, traversing_index;

    sortingArea() throws InterruptedException {
        setBackground(Color.WHITE);
        setBounds(0,HEIGHT/8,WIDTH,HEIGHT);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        initBarHeight();
        initShuffler();
//        insrtSorter();

    }
    public void sor (int x) throws InterruptedException {
//        setBackground(Color.WHITE);
//        setBounds(0,HEIGHT/8,WIDTH,HEIGHT);
//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        initBarHeight();
//        initShuffler();

         if(x==1)
            insrtSorter();
        else if(x==2)
            selectSorter();
        else if(x==3)
            bubbleSorter();
        else if(x==4)
            iterativeMergeSorter();

        initShuffler();




//      initBarHeight();
//        insrtSorter();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;
        Rectangle2D.Float bar;
        g2d.setColor(new Color(150,120,225));
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

    private void insrtSorter() throws InterruptedException {
        sorter = new SwingWorker<>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                for(current_index = 1; current_index < SIZE; current_index++) {
                    traversing_index = current_index;
                    while(traversing_index > 0 &&
                            bar_height[traversing_index] < bar_height[traversing_index - 1]) {

                        swap(traversing_index, traversing_index - 1);
                        traversing_index--;

                        Thread.sleep(3);
                        repaint();
                    }
                }
                current_index = 0;
                traversing_index = 0;

                return null;
            }
        };
    }

    private void initShuffler() throws InterruptedException {
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

    public void initBarHeight() {
        float interval = (float)HEIGHT / SIZE;
        for(int i = 0; i < SIZE; i++)
        {
            int random_height = new Random().nextInt(HEIGHT-100);

            bar_height[i] = 25+random_height;

        }
    }
    public void newBarHeight() throws InterruptedException {
        float interval = (float)HEIGHT / SIZE;
        for(int i = 0; i < SIZE; i++)
        {
            int random_height = new Random().nextInt(HEIGHT-100);

            bar_height[i] = 25+random_height;
            repaint();

        }
        initShuffler();
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

    private void bubbleSorter()
    {
        System.out.println("sorting");
    }
    private void iterativeMergeSorter()
    {
        System.out.println("sorting2");
    }


}


class navPanel extends JPanel implements ActionListener
{
    private final int WIDTH = 1000, HEIGHT = WIDTH / 16;
    JButton sortButton1=new JButton();
    JButton sortButton2=new JButton();
    JButton sortButton3=new JButton();
    JButton sortButton4=new JButton();
    private sortingArea sortaa;


    navPanel(sortingArea sora) throws InterruptedException {
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBounds(10,0,WIDTH,HEIGHT);
        setLayout(new GridLayout(1,4,15,15));
        this.sortaa=sora;
        sortButton1.setText("Insertion Sort");
        sortButton1.addActionListener(this);
        sortButton2.setText("Selection Sort");
        sortButton2.addActionListener(this);
        sortButton3.setText("Bubble Sort");
        sortButton3.addActionListener(this);
        sortButton4.setText("koi aur Sort");
        sortButton4.addActionListener(this);

        add(sortButton1);
        add(sortButton2);
        add(sortButton3);
        add(sortButton4);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==sortButton1) {
            try {
                 sortaa.sor(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource()==sortButton2) {
            try {
                sortaa.sor(2);

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource()==sortButton3) {
            try {
                sortaa.sor(3);
//                repaint();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        else if(e.getSource()==sortButton4) {
            try {
                sortaa.newBarHeight();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
//            repaint();

        }

    }
}
public class SortVisualizer {
    public static void main(String args[]) throws InterruptedException {

            JFrame frame = new JFrame("Sort Visualizer");
            frame.setLayout(null);
//            frame.setSize(1000,1000*9/16);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sortingArea sor=new sortingArea();

        frame.setContentPane(sor);
        frame.add(new navPanel(sor));
            frame.validate();
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

    }
}
