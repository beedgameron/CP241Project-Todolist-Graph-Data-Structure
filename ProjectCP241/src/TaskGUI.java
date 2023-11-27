import java.awt.*;
import javax.swing.*;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;


public class TaskGUI {

    private JFrame frame;
    private JButton button;
    private JPanel panel;
    private JTextField textField;
    private JLabel label;
    private JTextArea outputArea2;
    private Graph graph;
    private JTextField vertexField;
    private JTextField vertexField2;
    private JTextArea outputArea;
    private String dataFileName = "data.txt";



    private void saveData() {
        try {
            ArrayList<String> lines = new ArrayList<>();
            for (LinkedList<Node> currentList : graph.alist) {
                StringBuilder line = new StringBuilder();
                for (Node node : currentList) {
                    line.append(node.data).append(" -> ");
                }
                lines.add(line.toString());
            }
            Files.write(Paths.get(dataFileName), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFileName));
            // Process the loaded lines and update the graph as needed
            for (String line : lines) {
                String[] nodeNames = line.split(" -> ");
                for (String nodeName : nodeNames) {
                    if (!nodeName.trim().isEmpty()) {
                        graph.addNode(new Node(nodeName.trim()));
                    }
                }
            }

            // Check if outputArea is not null before calling update methods
            if (outputArea != null) {
                updateGraphDisplay();
            }

            if (outputArea2 != null) {
                updateNodeDisplay();
            }
        } catch (IOException e) {
            // Handle the case where the file doesn't exist or other IO issues
            e.printStackTrace();
        }
    }



    public TaskGUI() {
        graph = new Graph();


        //ปรับขนาดหน้าGUI
        frame = new JFrame();
        frame.setTitle("list Do It");
        frame.setSize(1180, 720);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveData(); // Save data when closing the program
                System.exit(0);
            }

        });



        //ภาพพื้นหลัง
        ImageIcon image = new ImageIcon("bg.2.png");
        JLabel backgroundLabel = new JLabel(image);
        backgroundLabel.setSize(1180, 720);

        // JLabel สำหรับข้อความ "TO DO List"
        JLabel label1 = new JLabel("TO DO List");

        // ตั้งตำแหน่งของ JLabel ใน JFrame
            label1.setBounds(150, 100, 300, 40);
            int borderThickness = 4;
            label1.setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness)); // ตั้งค่าสีขอบเป็นสีดำเเละขอบหนา
            label1.setBackground(Color.white);
            label1.setHorizontalAlignment(JLabel.CENTER);
            label1.setOpaque(true);

        // panel ใน Jframe
        JPanel panel = new JPanel();
            panel.setBounds(150, 150, 300, 450);
            panel.setLayout(null);
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness));
            panel.setBackground(Color.white);

        //ข้อความ task
        JLabel label2 = new JLabel("Task:");
        label2.setBounds(20, 20, 40, 25);

        //กรอกข้อความ
        vertexField = new JTextField(15);
        vertexField.setBounds(60, 20, 170, 25);
        vertexField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //เเสดงผลที่listเข้าไป
        outputArea2 = new JTextArea();
        outputArea2.setFont(new Font("Arial", Font.PLAIN, 18));
        outputArea2.setBounds(30,60,250,300);
        int borderThickness1 = 2;
        outputArea2.setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness1));
        JScrollPane scrollPane2 = new JScrollPane(outputArea2);
        panel.add(scrollPane2, BorderLayout.EAST);
        loadData();


        //ปุ่มAdd
        ImageIcon icon = new ImageIcon("add.png");    // สร้าง ImageIcon
        JButton createVertexButton = new JButton(icon);  // สร้าง JButton ด้วย ImageIcon
        createVertexButton.setBackground(Color.white);
        createVertexButton.setBorder(BorderFactory.createLineBorder(Color.white));
        createVertexButton.setBounds(245, 16, 35, 35);


        createVertexButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vertexName = vertexField.getText();
                if (!vertexName.isEmpty()) {
                    graph.addNode(new Node(vertexName));
                    updateGraphDisplay();
                    updateNodeDisplay();
                    vertexField.setText("");
                }
            }
        });



        //ใส่ข้อความที่ต้องการลบ
        vertexField2 = new JTextField(15);
        vertexField2.setBounds(50, 400, 170, 25);
        vertexField2.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        //ปุ่มremove node
        ImageIcon icon2 = new ImageIcon("remove.png");
        JButton removeNodeButton = new JButton(icon2);
        removeNodeButton.setBounds(0,0,20,20);
        removeNodeButton.setBackground(Color.white);
        removeNodeButton.setBorder(BorderFactory.createLineBorder(Color.white));
        removeNodeButton.setBounds(240, 400, 35, 35);

        removeNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vertexName = vertexField2.getText();
                if (!vertexName.isEmpty()) {
                    graph.removeNode(vertexName);
                    updateGraphDisplay();
                    updateNodeDisplay();
                    vertexField2.setText("");
                }
            }
        });


        //เเสดงกราฟ
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Arial", Font.PLAIN, 18));
        outputArea.setBounds(550,95,500,500);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane, BorderLayout.CENTER);



         //ใส่ข้อความลบedge
        JTextField srcVertexField = new JTextField(7);
        srcVertexField.setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness1));
        srcVertexField.setBounds(600,630,120,25);
        //เพิ่มedge
        JTextField dstVertexField = new JTextField(7);
        dstVertexField.setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness1));
        dstVertexField.setBounds(820,630,120,25);



        JLabel label3 = new JLabel("Edge Source:");
        label3.setBounds(490, 630, 100, 20);
        label3.setBackground(Color.white);
        label3.setOpaque(true);

        JLabel label4 = new JLabel("Edge Destination:");
        label4.setBounds(720, 630, 100, 20);
        label4.setBackground(Color.white);
        label4.setOpaque(true);

        //ปุ่มเพิ่มEdge
        JButton createEdgeButton = new JButton("Create Edge");
        createEdgeButton.setBounds(950,630,90,25);
        createEdgeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness1));
        createEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String srcVertexName = srcVertexField.getText();
                String dstVertexName = dstVertexField.getText();

                if (!srcVertexName.isEmpty() && !dstVertexName.isEmpty()) {
                    graph.addEdge(srcVertexName, dstVertexName);
                    updateGraphDisplay();
                    updateNodeDisplay();
                    srcVertexField.setText("");
                    dstVertexField.setText("");
                }
            }
        });




        JButton removeEdgeButton = new JButton("Remove Edge");
        removeEdgeButton.setBounds(1050,630,90,25);
        removeEdgeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, borderThickness1));

        removeEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String srcVertexName = srcVertexField.getText();
                String dstVertexName = dstVertexField.getText();

                if (!srcVertexName.isEmpty() && !dstVertexName.isEmpty()) {
                    graph.removeEdge(srcVertexName, dstVertexName);
                    updateGraphDisplay();
                    updateNodeDisplay();
                    srcVertexField.setText("");
                    dstVertexField.setText("");
                }
            }
        });

        frame.add(label4);
        frame.add(label3);
        frame.add(dstVertexField);
        frame.add(srcVertexField);
        frame.add(createEdgeButton);
        frame.add(removeEdgeButton);
        panel.add(removeNodeButton);
        panel.add(outputArea2);
        panel.add(scrollPane2, BorderLayout.EAST);
        panel.add(createVertexButton);
        panel.add(vertexField2);
        panel.add(vertexField);
        panel.add(label2);

        frame.add(outputArea);
        frame.add(panel);
        frame.add(label1);
        frame.add(backgroundLabel);
        frame.setVisible(true);


    }


    private void updateGraphDisplay() {
        outputArea.setText("Graph:\n");
        for (LinkedList<Node> currentList : graph.alist) {
            for (Node node : currentList) {
                outputArea.append(node.data + " -> ");
            }
            outputArea.append("\n");
        }
    }

    private void updateNodeDisplay() {
        outputArea2.setText("List:\n");
        Set<String> displayedNodes = new HashSet<>(); // Set to keep track of displayed nodes

        for (LinkedList<Node> nodeshow : graph.alist) {
            for (Node node : nodeshow) {
                String nodeName = node.data;
                if (!displayedNodes.contains(nodeName)) {
                    outputArea2.append(nodeName + "\n");
                    displayedNodes.add(nodeName);
                }
            }
        }
    }
    private void updateGraphDisplay(JTextArea targetArea) {
        if (targetArea != null) {
            targetArea.setText("Graph:\n");
            for (LinkedList<Node> currentList : graph.alist) {
                for (Node node : currentList) {
                    targetArea.append(node.data + " -> ");
                }
                targetArea.append("\n");
            }
        }
    }
    private void updateGraphDisplay(List<String> lines) {
        if (outputArea != null) {
            outputArea.setText("Graph:\n");
            for (String line : lines) {
                outputArea.append(line + "\n");
            }
        }
    }



//    private void updateNodeDisplay() {
//        outputArea2.setText("List:\n");
//        Set<String> displayedNodes = new HashSet<>(); // Set to keep track of displayed nodes
//
//        for (LinkedList<Node> nodeshow : graph.alist) {
//            for (Node node : nodeshow) {
//                String nodeName = node.data;
//                if (!displayedNodes.contains(nodeName)) {
//                    outputArea2.append(nodeName + "\n");
//                    displayedNodes.add(nodeName);
//                }
//            }
//        }
//    }



}



