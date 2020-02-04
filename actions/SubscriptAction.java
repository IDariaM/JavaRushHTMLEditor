package com.javarush.task.task32.task3209.actions;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;

public class SubscriptAction extends StyledEditorKit.StyledTextAction{
    public SubscriptAction() {
        super(StyleConstants.Subscript.toString());
    }



    public SubscriptAction(String nm) {
        super(nm);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JEditorPane editorPane = getEditor(actionEvent);
        if (editorPane!=null){
            MutableAttributeSet mutableAttributeSet=getStyledEditorKit(editorPane).getInputAttributes();
            SimpleAttributeSet simpleAttributeSet=new SimpleAttributeSet();
            StyleConstants.setSubscript(simpleAttributeSet,!StyleConstants.isSubscript(mutableAttributeSet));
            setCharacterAttributes(editorPane,simpleAttributeSet,false);
        }

    }
}

////12.3. Изучи реализацию класса StrikeThroughAction, которую ты получил вместе с заданием и реализуй аналогичным образом классы:
////12.3.1. SubscriptAction
////12.3.2. SuperscriptAction
////Запусти программу и убедись, что пункты меню Подстрочный знак, Надстрочный знак и Зачеркнутый работают.
////Пункты, отвечающие за отмену и возврат действия пока не подключены к контроллеру и мы не сможем их проверить.

