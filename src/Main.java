import Model.Filter;
import Model.FilterEngine;

import java.awt.Image;
import javax.imageio.ImageIO;

import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Main extends JFrame {
    
    private JPanel Panel;
    private BufferedImage image;
    private BufferedImage processedImage;
    private Image scaledImage;
    private Image scaledProcessedImage;

    public static void main(String[] args) 
    {
        Main frame = new Main();
        frame.setVisible(true);
    }

    public Main() 
    {
        setResizable(false);
        setTitle("Xử lý ảnh");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);

        Panel = new JPanel();
        Panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        Panel.setLayout(null);
        setContentPane(Panel);

        JPanel panelFilter = new JPanel();
        panelFilter.setBorder(BorderFactory.createTitledBorder("Filter"));
        panelFilter.setBounds(30, 50, 250, 215);
        panelFilter.setLayout(null);
        
        JButton btnRed = new JButton("RED COLORING");
        JButton btnGreen = new JButton("GREEN COLORING");
        JButton btnBlue = new JButton("BLUE COLORING");
        JButton btnGrayscale = new JButton("GRAYSCALE");
        JButton btnNegative = new JButton("NEGATIVE");
        btnRed.setBounds(25, 30, 200, 20);
        btnGreen.setBounds(25, 65, 200, 20);
        btnBlue.setBounds(25, 100, 200, 20);
        btnGrayscale.setBounds(25, 135, 200, 20);
        btnNegative.setBounds(25, 170, 200, 20);
        
        JButton btnBrowse = new JButton("Browse...");
        btnBrowse.setBounds(110, 20, 90, 20);
        
        JButton btnSave = new JButton("Save Image");
        btnSave.setBounds(80, 280, 150, 20);
        
        JPanel panelImage = new JPanel();
        panelImage.setBorder(BorderFactory.createTitledBorder("Image"));
        panelImage.setBounds(340, 20, 300, 300);
         
        
        btnBrowse.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
                final JFileChooser fileChooser = new JFileChooser();
                final int dialogResult = fileChooser.showOpenDialog(Panel);
                try 
                {
                    File selectedFile = fileChooser.getSelectedFile().getAbsoluteFile();
                    image = ImageIO.read(selectedFile);
                    scaledImage = image.getScaledInstance(panelImage.getWidth(),
                        panelImage.getHeight(), BufferedImage.SCALE_SMOOTH);
                    panelImage.getGraphics().drawImage(scaledImage, 0, 0, null);
                } 
                catch (IOException exception) {}
            }
        });
        
        btnSave.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                final int dialogResult = fileChooser.showOpenDialog(Panel);
		if (dialogResult == JFileChooser.APPROVE_OPTION) 
                {
                    try 
                    {
			ImageIO.write(processedImage, "PNG",
                            new File(fileChooser.getSelectedFile() + "after.png"));
                    } 
                    catch (IOException exception) {}
		}
            }
	});
        
        btnRed.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
                FilterEngine filterEngine = new FilterEngine();
                processedImage = filterEngine.processImage(image, Filter.RED);
                scaledProcessedImage = processedImage.getScaledInstance(panelImage.getWidth(),
                    panelImage.getHeight(), BufferedImage.SCALE_SMOOTH);
                panelImage.getGraphics().drawImage(scaledProcessedImage, 0, 0, null);
            }
        }); 
                
        btnGreen.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
                FilterEngine filterEngine = new FilterEngine();
                processedImage = filterEngine.processImage(image, Filter.GREEN);
                scaledProcessedImage = processedImage.getScaledInstance(panelImage.getWidth(),
                    panelImage.getHeight(), BufferedImage.SCALE_SMOOTH);
                panelImage.getGraphics().drawImage(scaledProcessedImage, 0, 0, null);
            }
        }); 
                
        btnBlue.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
                FilterEngine filterEngine = new FilterEngine();
                processedImage = filterEngine.processImage(image, Filter.BLUE);
                scaledProcessedImage = processedImage.getScaledInstance(panelImage.getWidth(),
                    panelImage.getHeight(), BufferedImage.SCALE_SMOOTH);
                panelImage.getGraphics().drawImage(scaledProcessedImage, 0, 0, null);
            }
        }); 
                
        btnGrayscale.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
                FilterEngine filterEngine = new FilterEngine();
                processedImage = filterEngine.processImage(image, Filter.GRAYSCALE);
                scaledProcessedImage = processedImage.getScaledInstance(panelImage.getWidth(),
                    panelImage.getHeight(), BufferedImage.SCALE_SMOOTH);
                panelImage.getGraphics().drawImage(scaledProcessedImage, 0, 0, null);
            }
        }); 
                
        btnNegative.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent arg0) 
            {
                FilterEngine filterEngine = new FilterEngine();
                processedImage = filterEngine.processImage(image, Filter.NEGATIVE);
                scaledProcessedImage = processedImage.getScaledInstance(panelImage.getWidth(),
                    panelImage.getHeight(), BufferedImage.SCALE_SMOOTH);
                panelImage.getGraphics().drawImage(scaledProcessedImage, 0, 0, null);
            }
        }); 
                
        panelFilter.add(btnRed);
        panelFilter.add(btnGreen);
        panelFilter.add(btnBlue);
        panelFilter.add(btnGrayscale);
        panelFilter.add(btnNegative);

        Panel.add(btnBrowse);
        Panel.add(btnSave);
        
        Panel.add(panelFilter);
        Panel.add(panelImage);
    }
}
