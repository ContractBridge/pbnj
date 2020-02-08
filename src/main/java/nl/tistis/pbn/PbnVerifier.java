package nl.tistis.pbn;

/*
 * File   :     PbnVerifier.java
 * Author :     Tis Veugen
 * Date   :     2012-04-28
 * PBN    :     2.1
 *
 * History
 * -------
 * 1998-12-21 Added Ex=Im
 * 1999-03-06 Remember directory
 * 1999-04-05 Version 1.4
 * 1999-04-12 Version 1.5
 * 1999-04-26 Added SetVerify
 * 1999-04-26 Version 1.6
 * 1999-05-13 Version 1.7
 * 1999-06-05 Version 1.8
 * 1999-06-06 Only export PBN 1.0
 * 1999-06-13 Version 1.9
 * 1999-08-09 Version 2.0
 * 2001-09-14 Version 2.1
 * 2001-09-20 Version 2.2
 * 2002-08-25 Version 2.3
 * 2007-06-24 Version 2.4
 * 2009-10-03 Version 2.5
 * 2011-05-18 Version 2.6
 * 2011-09-12 Version 2.7
 * 2012-04-14 Version 2.8
 * 2012-04-28 Version 2.9
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class PbnVerifier extends Frame
        implements ActionListener, ItemListener, TextListener {
    private Button mImport;
    private Button mExport;
    private Button mLogging;
    private Button mVerify;

    private Checkbox mVerbose;
    private Checkbox mNoCR;
    private Checkbox mExIsIm;
    private Choice mPBN10;

    private TextField mImportTextField;
    private TextField mExportTextField;
    private TextField mLoggingTextField;

    private boolean mbFileExport;
    private boolean mbFileLogging;

    private RandomAccessFile mImportRaf;
    private FileOutputStream mExportFos;
    private FileOutputStream mLoggingFos;

    private String mDirectory;

    private String PbnVersions[] = {" PBN 2.1 ", " PBN 2.0 ", " PBN 1.0 "};


    public PbnVerifier(
            String[] args,
            boolean bApplet) {
        if (!((args == null)
                || (args.length == 0))) {
            MainVerifier(args);
            return;
        }

        mbFileExport = false;
        mbFileLogging = false;

        mImportRaf = null;
        mExportFos = null;
        mLoggingFos = null;

        mDirectory = "";

        setTitle("PBN Verifier (PBN 1.0 + PBN 2.0 + PBN 2.1)");

        GridBagLayout lGbl = new GridBagLayout();
        setLayout(lGbl);

        mImport = addButton("Import");
        mExport = addButton("Export");
        mLogging = addButton("Logging");

        mImportTextField = addTextField();
        mExportTextField = addTextField();
        mLoggingTextField = addTextField();

        mExIsIm = addCheckbox("Ex=Im");
        mVerbose = addCheckbox("Verbose");
        mNoCR = addCheckbox("only LF");
        mPBN10 = addChoice(PbnVersions);
        mVerify = addButton("Verify");

        GridBagConstraints lGbc = new GridBagConstraints();

        lGbc.anchor = GridBagConstraints.CENTER;
        lGbc.fill = GridBagConstraints.HORIZONTAL;
        lGbc.weightx = 20;
        lGbc.weighty = 10;

        add(mImport, lGbc, 5, 0, 10, 1);
        add(mExport, lGbc, 5, 1, 10, 1);
        add(mLogging, lGbc, 5, 2, 10, 1);

        lGbc.fill = GridBagConstraints.NONE;
        add(mImportTextField, lGbc, 15, 0, 45, 1);
        add(mExportTextField, lGbc, 15, 1, 45, 1);
        add(mLoggingTextField, lGbc, 15, 2, 45, 1);
        add(mExIsIm, lGbc, 60, 1, 10, 1);

        add(mVerbose, lGbc, 5, 3, 10, 1);
        add(mNoCR, lGbc, 20, 3, 10, 1);
        add(mPBN10, lGbc, 35, 3, 10, 1);
        add(mVerify, lGbc, 50, 3, 10, 1);
        mExIsIm.setState(false);
        mPBN10.select(0);

        addWindowListener(new WindowAdapter() {
                              public void windowClosing(
                                      WindowEvent evt) {
                                  setVisible(false);
                                  System.exit(0);
                              }
                          }
        );

        if (!bApplet) {
            PbnDialog lDialog = new PbnDialog(this, "PBN Verifier (PBN 1.0 + PBN 2.0 + PBN 2.1)"
                    , "Program version 2.9 (2012.04.28)\n"
                    + "by Tis Veugen\n"
                    + "http://www.tistis.nl/pbn");
        }
        setLocation(100, 100);
        setSize(500, 150);
    }

    public void add(
            Component oComponent,
            GridBagConstraints oGbc,
            int x,
            int y,
            int w,
            int h) {
        oGbc.gridx = x;
        oGbc.gridy = y;
        oGbc.gridwidth = w;
        oGbc.gridheight = h;
        add(oComponent, oGbc);
    }

    public Choice addChoice(
            String[] oNames) {
        Choice lChoice = new Choice();

        for (int i = 0; i < oNames.length; i++) {
            lChoice.add(oNames[i]);
        }

        return lChoice;
    }

    public Checkbox addCheckbox(
            String oName) {
        Checkbox lCheckbox = new Checkbox(oName);

        lCheckbox.addItemListener(this);

        return lCheckbox;
    }

    public Button addButton(
            String oName) {
        Button lButton = new Button(oName);

        lButton.addActionListener(this);

        return lButton;
    }

    public TextField addTextField() {
        TextField lTextField = new TextField("", 40);

        lTextField.addTextListener(this);

        return lTextField;
    }

    public void ActionImport() {
        String lFilename;
        String lExtension;

        lFilename = SetFile("*.pbn", "Import file", FileDialog.LOAD);
        if (lFilename != null) {
            mImportTextField.setText(lFilename);

            if (!mbFileExport) {
                if (mExIsIm.getState()) {
                    mExportTextField.setText(lFilename);
                }
            }
            if (!mbFileLogging) {
                int iDot = lFilename.lastIndexOf(".");

                if (iDot >= 0) {
                    if (lFilename.substring(iDot).toLowerCase().equals(".pbn")) {
                        lFilename = lFilename.substring(0, iDot) + ".log";
                        mLoggingTextField.setText(lFilename);
                    }
                }
            }
        }
    }

    private void CloseFiles() {
        if (mImportRaf != null) {
            try {
                mImportRaf.close();
            } catch (Exception e) {
                System.err.println("Can't close import file");
            }
        }

        if (mExportFos != null) {
            try {
                mExportFos.close();
            } catch (Exception e) {
                System.err.println("Can't close export file");
            }
        }

        if (mLoggingFos != null) {
            try {
                mLoggingFos.close();
            } catch (Exception e) {
                System.err.println("Can't close logging file");
            }
        }

        mImportRaf = null;
        mExportFos = null;
        mLoggingFos = null;
    }

    private String OpenFiles(
            String oImportFilename,
            String oExportFilename,
            String oLoggingFilename) {
        if ((oImportFilename == null)
                || (oImportFilename.equals(""))) {
            return " No import file";
        }
        try {
            mImportRaf = new RandomAccessFile(oImportFilename, "r");
        } catch (Exception e) {
            return "Can't open import file " + oImportFilename;
        }

        if (!((oExportFilename == null)
                || (oExportFilename.equals("")))) {
            try {
                mExportFos = new FileOutputStream(oExportFilename);
            } catch (Exception e) {
                return "Can't open export file " + oExportFilename;
            }
        }

        if ((oLoggingFilename == null)
                || (oLoggingFilename.equals(""))) {
            return "No logging file";
        }
        if (oLoggingFilename.equals(oImportFilename)) {
            return "Logging file and import file have same names";
        }
        if (oLoggingFilename.equals(oExportFilename)) {
            return "Logging file and export file have same names";
        }
        try {
            mLoggingFos = new FileOutputStream(oLoggingFilename);
        } catch (Exception e) {
            return "Can't open logging file " + oLoggingFilename;
        }

        return null;
    }

    private String SetFile(
            String oFilter,
            String oHeader,
            int iMode) {
        FileDialog lFd;
        String lFilename;

        lFd = new FileDialog(this      // mFrame
                , oHeader
                , iMode);
        lFd.setDirectory(mDirectory);
        lFd.setFile(oFilter);
        lFd.show();

        lFilename = lFd.getFile();
        if (lFilename != null) {
            mDirectory = lFd.getDirectory();
            lFilename = mDirectory + lFilename;
        }

        return lFilename;
    }

    private void PrintDialog(
            boolean bDialog,
            Frame oFrame,
            String oHeader,
            String oText) {
        if (bDialog) {
            new PbnDialog(oFrame, oHeader, oText);
        } else {
            System.out.println(oText);
        }
    }

    private void ActionVerify(
            String oImportFilename,
            String oExportFilename,
            String oLoggingFilename,
            boolean bNoCR,
            boolean bVerbose,
            int oPBN10,
            boolean bDialog) {
        PbnVerify lVerify = new PbnVerify();
        PbnError lError = new PbnError(PbnError.ERROR);
        String lString;
        boolean bImpIsExp = false;
        String lImpIsExpName = "pbnJveri.tmp";

        if ((oExportFilename != null)
                && (oImportFilename.equals(oExportFilename))) {
            bImpIsExp = true;
            lImpIsExpName = "pbnJveri.tmp";
            if (lImpIsExpName.equals(oExportFilename)) {
                lImpIsExpName = "pbnJveri.tmq";
            }
            oExportFilename = lImpIsExpName;
        }

        lString = OpenFiles(oImportFilename
                , oExportFilename
                , oLoggingFilename);
        if (lString != null) {
            PrintDialog(bDialog, this, "Files", lString);
            lError = new PbnError(PbnError.ERROR);
        } else {
            if (bDialog) {
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            }

            PbnGen.SetVersion(oPBN10);
            PbnGen.SetVerbose((bVerbose) ? 1 : 0);
            PbnGen.SetVerify();
            PbnGen.SetParsing(PbnGen.PARSE_FIRST);

            try {
                lError = lVerify.Exec(mImportRaf
                        , mExportFos
                        , mLoggingFos
                        , bNoCR
                        , !bDialog);
            } catch (Exception e) {
                PrintDialog(bDialog, this, "Program error"
                        , "You have found a bug !\n"
                                + "Please, send an e-mail with the PBN file to:\n"
                                + "tis.veugen@gmail.com");
            }
            if (bDialog) {
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            PrintDialog(bDialog, this, "Verifier Result"
                    , lVerify.GetStat());
        }

        CloseFiles();
        if ((bImpIsExp)
                && (lError.IsOK())) { /*
         * Rename the temporary file to the export file.
         */
            File lSourceFile = new File(lImpIsExpName);
            File lTargetFile = new File(oImportFilename);

            try {
                lTargetFile.delete();
                lSourceFile.renameTo(lTargetFile);
            } catch (Exception e) {
                PrintDialog(bDialog, this, "File error"
                        , "Can't export to import file");
            }
        }
    }

    public void actionPerformed(
            ActionEvent evt) {
        String lName = evt.getActionCommand();
        String lFilename;

        if (lName.equals("Import")) {
            ActionImport();
        } else if (lName.equals("Export")) {
            lFilename = SetFile("*.pbn", "Export file", FileDialog.LOAD);
            if (lFilename != null) {
                mExportTextField.setText(lFilename);
                mbFileExport = true;
            }
        } else if (lName.equals("Logging")) {
            lFilename = SetFile("*.log", "Logging file", FileDialog.LOAD);
            if (lFilename != null) {
                mLoggingTextField.setText(lFilename);
                mbFileLogging = true;
            }
        } else if (lName.equals("Verify")) {
            ActionVerify(mImportTextField.getText()
                    , mExportTextField.getText()
                    , mLoggingTextField.getText()
                    , mNoCR.getState()
                    , mVerbose.getState()
                    , GetPbnVersion()
                    , true);     // Dialog
            mbFileExport = false;
            mbFileLogging = false;
        }
    }

    private int GetPbnVersion() {
        int v;

        switch (mPBN10.getSelectedIndex()) {
            case 0:
                v = PbnGen.VERSION_21;
                break;
            case 1:
                v = PbnGen.VERSION_20;
                break;
            default:
            case 2:
                v = PbnGen.VERSION_10;
                break;
        }
        return v;
    }

    public void itemStateChanged(
            ItemEvent evt) {
    }

    public void textValueChanged(
            TextEvent evt) {
    }

    private void MainHelp() {
        String[] HelpTexts =
                {
                        "java PbnVerifier [options] <filename> [options]",
                        "<filename>       filename of import PBN file",
                        "-e               export the import PBN file",
                        "-E<export_name>  filename of export PBN file (default PbnJveri.pbn)",
                        "-h               this help",
                        "-i               information about program version",
                        "-L               only LF at line end",
                        "-O<output_name>  filename of verification output (default PbnJveri.log)",
                        "-v               verbose: echo the import PBN file",
                        "-10              PBN version 1.0",
                        "-20              PBN version 2.0",
                        "-21              PBN version 2.1 (default)"
                };

        for (int i = 0; i < HelpTexts.length; i++) {
            System.out.println(HelpTexts[i]);
        }
    }

    private void MainVerifier(
            String[] args) {
        String arg;
        String lImportFilename = null;
        String lExportFilename = null;
        String lLoggingFilename = null;
        boolean bExportGame = false;
        boolean bNoCR = false;
        boolean bVerbose = false;
        int lPBN10 = 0;

        for (int i = 0; i < args.length; i++) {
            arg = args[i];
            if (arg.startsWith("-e")) {
                bExportGame = true;
            } else if (arg.startsWith("-E")) {
                lExportFilename = arg.substring(2);
            } else if (arg.startsWith("-h")) {
                MainHelp();
            } else if (arg.startsWith("-i")) {
                System.out.println("INFO: Program version 2.4 for  PBN 1.0 + PBN 2.0 + PBN 2.1");
            } else if (arg.startsWith("-L")) {
                bNoCR = true;
            } else if (arg.startsWith("-O")) {
                lLoggingFilename = arg.substring(2);
            } else if (arg.startsWith("-v")) {
                bVerbose = true;
            } else if (arg.startsWith("-21")) {
                lPBN10 = PbnGen.VERSION_21;
            } else if (arg.startsWith("-20")) {
                lPBN10 = PbnGen.VERSION_20;
            } else if (arg.startsWith("-1")) {
                lPBN10 = PbnGen.VERSION_10;
            } else if (lImportFilename == null) {
                lImportFilename = args[i];
            }
        }

        if (lLoggingFilename == null) {
            lLoggingFilename = "PbnJveri.log";
        }

        if (bExportGame && (lExportFilename == null)) {
            lExportFilename = "PbnJveri.pbn";
        }

        if (lImportFilename != null) {
            ActionVerify(lImportFilename
                    , lExportFilename
                    , lLoggingFilename
                    , bNoCR
                    , bVerbose
                    , lPBN10
                    , false);
        }
    }

    public static void main(
            String[] args) {
        Frame lVerifier;

        lVerifier = new PbnVerifier(args, false);
        if ((args == null)
                || (args.length == 0)) {
            lVerifier.show();
        }
    }
}

class PbnDialog extends Dialog {
    public PbnDialog(
            Frame oParent,
            String oHeader,
            String oText) {
        super(oParent, oHeader, true);

        Panel lPanel = new Panel();
        int NrLines;
        int Index;

        for (NrLines = 0; ; NrLines++) {
            Index = oText.indexOf("\n");
            if (Index < 0) {
                lPanel.add(new Label(oText));
                break;
            }
            lPanel.add(new Label(oText.substring(0, Index)));
            oText = oText.substring(Index + 1);
        }
        add(lPanel, "Center");

        Button mOk = new Button("OK");
        lPanel = new Panel();
        lPanel.add(mOk);
        add(lPanel, "South");

        mOk.addActionListener(new ActionListener() {
                                  public void actionPerformed(
                                          ActionEvent evt) {
                                      setVisible(false);
                                  }
                              }
        );

        addWindowListener(new WindowAdapter() {
                              public void windowClosing(
                                      WindowEvent evt) {
                                  setVisible(false);
                              }
                          }
        );

        setLocation(150, 150);
        setSize(300, 120 + NrLines * 30);
        show();
    }
}
