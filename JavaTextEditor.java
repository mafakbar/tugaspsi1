/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatexteditor;

/**
 *
 * @author USER
 */
import java.awt.*; //Abstract Windowing Toolkit yang merupakan Suatu Perintah 
//Untuk memanggil library yang ada di awt agar dapat digunakan dalam pembuatan program.
//Porses inputan dalam progam yang dijalankan
import java.awt.event.*; //adalah untuk mengambil semua file dari package
//event yang mana package event berada pada package java.awt. 
import javax.swing.*; //Merupakan perintah dalam Program Java yang berisi 
//kelas-kelas dan interface (menampilkan ataupun menerima pesan) untuk komponen GUI
//swing java yang menyediakan dukungan untuk komponen grafis yang portable pada contoh 
//disini biasanya menggunakan sebuah gambar atau membaca dan memanggil grafis pada suatu 
//gambar yang akan di deklarasikan.
//Mendeklarasikan Sebuah Komponen yang Berupa Grafis dengan perintah yang dilakukan dalam pembuatan Program.
import java.io.*; //input output berfungsi untuk memasukkan data dari peraltan input e.g keyboard 
//dan perintah untuk menampilkan data kepada pengguna
import static javax.swing.Action.MNEMONIC_KEY;


public class JavaTextEditor extends JFrame{

     //... Components 
    private JTextArea    _editArea;
    private JFileChooser _fileChooser = new JFileChooser();
    
    //... Create actions for menu items, buttons, ...
    private Action _openAction = new OpenAction();
    private Action _saveAction = new SaveAction();
    private Action _exitAction = new ExitAction(); 
    private int jumlahkarakter;
    private int jumlahkata;
    public int tempKey;
    //============================================================== constructor
    public JavaTextEditor() {
        //... Create scrollable text area.
        _editArea = new JTextArea(30, 80);//ukuran kotaknya
        _editArea.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));//buat tempat margin
        _editArea.setFont(new Font("monospaced", Font.PLAIN, 20));
      JScrollPane scrollingText = new JScrollPane(_editArea);
        tempKey = 0;
        //-- Create a content pane, set layout, add component.
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(scrollingText, BorderLayout.CENTER);
        
        //... Create menubar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = menuBar.add(new JMenu("File"));
        fileMenu.setMnemonic('F');
        fileMenu.add(_openAction);       // pilhan menu yang ada di "file".
        fileMenu.add(_saveAction);
        fileMenu.addSeparator(); 
        fileMenu.add(_exitAction);
        
        //... Set window content and menu.
        setContentPane(content);
        setJMenuBar(menuBar);
        
        _editArea.addKeyListener(new KeyListener(){
            @Override //manggil kelas Jframe
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) { //untuk 
                System.out.println(ke.getKeyCode());
                if (tempKey == 0){
                    tempKey = ke.getKeyCode();
                } else {
                    if (tempKey != ke.getKeyCode()){
                        if (tempKey == 17 && ke.getKeyCode() == 88){
                            System.exit(0);
                        } else if (tempKey == 17 && ke.getKeyCode() == 83){
                            int retval = _fileChooser.showSaveDialog(JavaTextEditor.this);
                            if (retval == JFileChooser.APPROVE_OPTION) {
                                File f = _fileChooser.getSelectedFile();
                                try {
                                    FileWriter writer = new FileWriter(f);
                                    _editArea.write(writer);  // Use TextComponent write
                                } catch (IOException ioex) { // IO untuk membaca input data string 
                                    JOptionPane.showMessageDialog(JavaTextEditor.this, ioex);
                                    System.exit(1);
                                }
                            }
                        }
                        else{
                            tempKey = ke.getKeyCode();
                        }
                    }
                }
//                int temp = ke.getKeyCode();
//                if (temp != ke.getID()){                    
//                    int id = ke.getKeyCode();
//                }
//                System.out.println(id);
//                if (id == 88){
////                    ExitAction exit = new ExitAction();
//                    System.exit(0);
//                }
            }
            @Override
            public void keyReleased(KeyEvent ke) {
                String kata = _editArea.getText();
                jumlahkarakter = 0;
                for (int i = 0; i <kata.length(); i++) {
                    char karakter = kata.charAt(i);
                       if(Character.isLetter(karakter)){
                           jumlahkarakter++;
                       }
                    
                }
                String array[] = kata.split(" ");
                jumlahkata = array.length;
                if (jumlahkarakter < 2){
                    jumlahkata = 0;
                }
                System.out.println("jumlah karakter = "+ jumlahkarakter+"\nJumlah kata = "+jumlahkata);
            }
            
        });
        
        //... Set other window characteristics.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Simple Editor");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void setTempKey(int id){
            tempKey = id;
    }
    ////////////////////////////////////////////////// inner class OpenAction
    class OpenAction extends AbstractAction {
        //============================================= constructor
        public OpenAction() {
            super("Open...");
            putValue(MNEMONIC_KEY, new Integer('O'));
        }
        
        //========================================= actionPerformed
        public void actionPerformed(ActionEvent e) {
            int retval = _fileChooser.showOpenDialog(JavaTextEditor.this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                File f = _fileChooser.getSelectedFile();
                try {
                    FileReader reader = new FileReader(f);
                    _editArea.read(reader, "");  // Use TextComponent read
                } catch (IOException ioex) {
                    System.out.println(e);
                    System.exit(1);
                }
            }
        }
    }
    
    //////////////////////////////////////////////////// inner class SaveAction
    class SaveAction extends AbstractAction {
        //============================================= constructor
        SaveAction() {
            super("Save...");
            putValue(MNEMONIC_KEY, new Integer('S'));
        }
        
        //========================================= actionPerformed
        public void actionPerformed(ActionEvent e) {
            int retval = _fileChooser.showSaveDialog(JavaTextEditor.this);
            if (retval == JFileChooser.APPROVE_OPTION) {
                File f = _fileChooser.getSelectedFile();
                try {
                    FileWriter writer = new FileWriter(f);
                    _editArea.write(writer);  // Use TextComponent write
                } catch (IOException ioex) {
                    JOptionPane.showMessageDialog(JavaTextEditor.this, ioex);
                    System.exit(1);
                }
            }
        }
    }
    
    ///////////////////////////////////////////////////// inner class ExitAction
    class ExitAction extends AbstractAction {
        
        //============================================= constructor
        public ExitAction() {
            super("Exit");
            putValue(MNEMONIC_KEY, new Integer('X'));
        }
        
        //========================================= actionPerformed
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
       new JavaTextEditor();
        
    }
}
