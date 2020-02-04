package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();

    }

    public void init() {
        createNewDocument();
    }

    public void exit() {
        System.exit(0);
    }

    public void resetDocument() {
        if (document != null) {
            document.removeUndoableEditListener(view.getUndoListener());
        }
        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        document.addUndoableEditListener(view.getUndoListener());
        view.update();
    }

    public void setPlainText(String text) {
        resetDocument();
        StringReader stringReader = new StringReader(text);
        try {
            HTMLEditorKit kit = new HTMLEditorKit();
            kit.read(stringReader, document, 0);


        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public String getPlainText() {
        StringWriter stringWriter = new StringWriter();
        //HTMLEditorKit kit = new HTMLEditorKit();
        try {
            new HTMLEditorKit().write(stringWriter, document, 0, document.getLength());
            return stringWriter.toString();
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }

        return stringWriter.toString();
    }


    public HTMLDocument getDocument() {
        return document;
    }

    public void createNewDocument() {
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile = null;
    }


    public void saveDocument() {
        if (currentFile == null) {
            saveDocumentAs();
            return;
        }
        view.selectHtmlTab();
        try (FileWriter writer = new FileWriter(currentFile)) {
            new HTMLEditorKit().write(writer, document, 0, document.getLength());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public void saveDocumentAs() {
        view.selectHtmlTab();
        JFileChooser jFileChooser = new JFileChooser();
        HTMLFileFilter fileFilter = new HTMLFileFilter();
        try {
            jFileChooser.setFileFilter(fileFilter);
            int result = jFileChooser.showSaveDialog(view);
            if (result == JFileChooser.APPROVE_OPTION) {
                currentFile = jFileChooser.getSelectedFile();
                view.setTitle(currentFile.getName());
                FileWriter writer = new FileWriter(currentFile);
                new HTMLEditorKit().write(writer, document, 0, document.getLength());
                // JOptionPane.showMessageDialog(view, "Файл '" + jFileChooser.getSelectedFile() + " сохранен");
                writer.close();
            }
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }

    }

    public void openDocument() {
        view.selectHtmlTab();
        JFileChooser jFileChooser = new JFileChooser();
        HTMLFileFilter fileFilter = new HTMLFileFilter();
        jFileChooser.setFileFilter(fileFilter);
        int result = jFileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = jFileChooser.getSelectedFile();
            resetDocument();
            view.setTitle(currentFile.getName());
            try (FileReader reader = new FileReader(currentFile)) {
                HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
                htmlEditorKit.read(reader, document, 0);
                view.resetUndo();
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }
}

