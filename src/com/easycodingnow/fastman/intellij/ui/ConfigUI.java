package com.easycodingnow.fastman.intellij.ui;

import com.easycodingnow.fastman.intellij.common.ConfigData;
import com.easycodingnow.fastman.intellij.common.GlobalConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author lihao
 * @since 2020-01-16
 */
public class ConfigUI extends JDialog {

    private JTextField textField1;

    private JPanel contentPane;



    private MutableBoolean modified;

    public ConfigUI(MutableBoolean modified) {
        this.modified = modified;
        setContentPane(contentPane);
        setModal(true);


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        addListener();

        init();

        this.setSize(this.getWidth() + 1, this.getHeight() + 1);
    }

    private void init() {
        ConfigData config = GlobalConfig.getConfig();

        if (config != null && StringUtils.isNotBlank(config.getPath())) {
            this.textField1.setText(config.getPath());
        }
    }

    private DocumentListener listener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            modified.setTrue();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            modified.setTrue();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            modified.setTrue();
        }
    };

    private void addListener() {
        textField1.getDocument().addDocumentListener(listener);
    }


    private void onCancel() {
        dispose();
    }


    public JTextField getTextField1() {
        return textField1;
    }

    @Override
    public JPanel getContentPane() {
        return contentPane;
    }

    public MutableBoolean getModified() {
        return modified;
    }

}
