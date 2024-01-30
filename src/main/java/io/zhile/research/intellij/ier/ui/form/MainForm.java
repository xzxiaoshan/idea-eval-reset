package io.zhile.research.intellij.ier.ui.form;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Disposer;
import io.zhile.research.intellij.ier.common.EvalRecord;
import io.zhile.research.intellij.ier.common.Resetter;
import io.zhile.research.intellij.ier.helper.AppHelper;
import io.zhile.research.intellij.ier.helper.PluginHelper;
import io.zhile.research.intellij.ier.helper.ResetTimeHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MainForm {
    private JPanel rootPanel;
    private JButton btnReset;
    private JList<String> lstMain;
    private JLabel lblLastResetTime;
    private JButton btnReload;
    private JLabel lblFound;
    private JLabel lblLastResetTimeLabel;
    private JCheckBox chkResetAuto;
    private JLabel lblVersion;
    private JCheckBox chkAutoLogout;
    private DialogWrapper dialogWrapper;
    private DefaultListModel<String> listModel;

    public MainForm(Disposable disposable) {
        this(disposable, null);
    }

    public MainForm(Disposable disposable, DialogWrapper wrapper) {
        this.listModel = new DefaultListModel<>();
        this.dialogWrapper = wrapper;
        Disposer.register(disposable, () -> {
            rootPanel.removeAll();
            listModel = null;
            dialogWrapper = null;
        });
    }

    public JPanel getContent(Disposable disposable) {
        Disposer.register(disposable, () -> rootPanel.removeAll());
        boldFont(lblFound);
        boldFont(lblLastResetTimeLabel);
        reloadLastResetTime();

        lblVersion.setText("v" + PluginHelper.getPluginVersion());
        this.chkAutoLogout.setSelected(Resetter.isAutoLogout());
        addActionEventListener(this.chkAutoLogout, e -> Resetter.setAutoLogout(chkAutoLogout.isSelected()), disposable);

        chkResetAuto.setSelected(Resetter.isAutoReset());
        addActionEventListener(this.chkResetAuto, e -> Resetter.setAutoReset(chkResetAuto.isSelected()), disposable);

        lstMain.setModel(listModel);
        reloadRecordItems();

        btnReload.setIcon(AllIcons.Actions.Refresh);
        addActionEventListener(this.btnReload, e -> {
            reloadLastResetTime();
            reloadRecordItems();
        }, disposable);

        btnReset.setIcon(AllIcons.General.Reset);
        addActionEventListener(this.btnReset, e -> resetEvalItems(), disposable);

        if (null != dialogWrapper) {
            dialogWrapper.getRootPane().setDefaultButton(btnReset);
            this.rootPanel.setMinimumSize(new Dimension(640, 260));
        }
        return rootPanel;
    }

    public void reloadLastResetTime() {
        lblLastResetTime.setText(ResetTimeHelper.getLastResetTimeStr());
    }

    public void reloadRecordItems() {
        listModel.clear();
        Resetter.touchLicenses();
        List<EvalRecord> recordItemList = Resetter.getEvalRecords();
        for (EvalRecord record : recordItemList) {
            listModel.addElement(record.toString());
        }
    }

    public void resetEvalItems() {
        if (Messages.YES != Messages.showYesNoDialog("Your IDE will restart after reset!\nAre your sure to reset?", PluginHelper.getPluginName(), AllIcons.General.Reset)) {
            return;
        }
        Resetter.touchLicenses();
        Resetter.reset(Resetter.getEvalRecords());
        ResetTimeHelper.resetLastResetTime();
        listModel.clear();

        if (null != dialogWrapper) {
            dialogWrapper.close(0);
        }

        AppHelper.restart();
    }

    private static void boldFont(Component component) {
        Font font = component.getFont();
        component.setFont(font.deriveFont(font.getStyle() | Font.BOLD));
    }

    private static void addActionEventListener(AbstractButton button, ActionListener listener, Disposable disposable) {
        button.addActionListener(listener);
        Disposer.register(disposable, () -> button.removeActionListener(listener));
    }

}
