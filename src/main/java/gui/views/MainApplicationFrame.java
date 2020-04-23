package gui.views;

import com.google.gson.JsonSerializer;
import gui.FrameListener;
import gui.RobotsProgram;
import gui.WindowListener;
import gui.serialization.MainFrameSerializer;
import gui.serialization.SavableInternalFrame;
import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class MainApplicationFrame extends JFrame {
    public final JDesktopPane desktopPane = new JDesktopPane();
    public HashMap<String, SavableInternalFrame> frames;

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        frames = new HashMap<>();
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);
        addWindowListener(new WindowListener(this));

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow, "logWindow");

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400, 400);
        addWindow(gameWindow, "gameWindow");

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    public void addWindow(JInternalFrame frame, String name) {
        frames.put(name, (SavableInternalFrame) frame);
        desktopPane.add(frame);
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        FrameListener listener = new FrameListener(frame);
        frame.addInternalFrameListener(listener);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        HashMap<String, ActionListener> lookAndFeelItems = new HashMap<>();
        lookAndFeelItems.put("Системная схема", (event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelItems.put("Универсальная схема", (event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });
        lookAndFeelItems.put("Выход", (event) -> {
            if (FrameListener.dialogAnswer(this) == JOptionPane.YES_OPTION) {
                RobotsProgram.save(this);
                System.exit(0);
            }
        });
        JMenu lookAndFeelMenu = createMenu("Режим отображения",
                KeyEvent.VK_V,
                "Управление режимом отображения приложения",
                lookAndFeelItems);
        HashMap<String, ActionListener> testItems = new HashMap<>();
        testItems.put("Сообщение в лог",
                (event) -> Logger.debug("Новая строка"));
        JMenu testMenu = createMenu("Тесты", KeyEvent.VK_T, "Тестовые команды", testItems);
        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        return menuBar;
    }

    private JMenu createMenu(String name, int mnemonic, String descriptor, HashMap<String, ActionListener> menuItems) {
        JMenu menu = new JMenu(name);
        menu.setMnemonic(mnemonic);
        menu.getAccessibleContext().setAccessibleDescription(descriptor);
        for (Map.Entry<String, ActionListener> entry : menuItems.entrySet()) {
            JMenuItem item = createMenuItem(entry.getKey(), entry.getValue());
            menu.add(item);
        }
        return menu;
    }

    private JMenuItem createMenuItem(String text, ActionListener listener) {
        Logger.debug("Create Menu Item. Text: " + text);
        JMenuItem item = new JMenuItem(text, KeyEvent.VK_S);
        item.addActionListener(listener);
        return item;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }

    public static JsonSerializer getSerializer() {
        return new MainFrameSerializer();
    }
}
