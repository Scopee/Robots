package gui.serialization;

import javax.swing.*;

public abstract class SavableInternalFrame extends JInternalFrame implements Savable {
    public SavableInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
    }
}
