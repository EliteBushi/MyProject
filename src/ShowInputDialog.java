import org.xml.sax.SAXException;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.*;
import java.io.IOException;

public class ShowInputDialog{
    public static void main(String[] args){
        JFrame frame = new JFrame("NWSAPI");
        JButton button = new JButton("Click here to get forecast");
        Forecast forecast = new Forecast();
        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                String str = JOptionPane.showInputDialog(null, "Enter zip code: ",
                        "NWSAPI", 1);
                if(str != null) {
                    forecast.setZipcode(str);
                    forecast.setURL();
                    try {
                        forecast.getDoc();
                    }
                    catch ( SAXException | IOException | ParserConfigurationException p){
                        System.out.println(p.getMessage());
                    }
                    forecast.displayResults(forecast.getValues());
                }
                else {
                    JOptionPane.showMessageDialog(null, "You pressed cancel button.",
                            "NWSAPI", 1);
                    System.exit(0);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(button);
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
