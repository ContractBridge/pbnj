package nl.tistis.pbn;/*
 * File   :     PbnVerify.java
 * Author :     Tis Veugen
 * Date   :     2001-09-20
 * PBN    :     2.1
 *
 * History
 * -------
 * 1999-06-13 Added SEV_REMARK
 * 1999-10-02 Added version
 * 2001-09-20 Use PbnInputStream
 * 2011-09-12 Export PBN Version 2.1
 */

import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class PbnVerify
{
  private PbnVeriStat mVeriStat;

  public                        PbnVerify()
  {
    mVeriStat = new PbnVeriStat();
  }

  public PbnError Exec(
  RandomAccessFile              oImportRaf,
  FileOutputStream              oExportFos,
  FileOutputStream              oLoggingFos,
  boolean                       bNoCR,
  boolean                       bPrint )
  {
    PbnImport                   lImport = new PbnImport();
    PbnExport lExport = new PbnExport();
    PbnError lVerifyError = new PbnError();

    long                        FilePosIn = 0;
    int                         GameIndex = 0;
    boolean                     bReady = false;
    boolean                     bExport = ( oExportFos != null );
    PbnInherit lInherit = new PbnInherit();

    lImport.SetInputFile( new PbnInputStream( oImportRaf ) );
    lImport.SetLogFile( oLoggingFos );
    lImport.SetInherit( lInherit );

    if ( bExport )
    {
      lExport.SetExportFile( oExportFos );
      lExport.NoCR( bNoCR );

      switch ( PbnGen.GetVersion() )
      {
      case PbnGen.VERSION_10 :
        lExport.ExportLine( "% PBN 1.0" );
        break;
      case PbnGen.VERSION_20 :
        lExport.ExportLine( "% PBN 2.0" );
        break;
      case PbnGen.VERSION_21 :
        lExport.ExportLine( "% PBN 2.1" );
        break;
      }
      lExport.ExportLine( "% EXPORT" );
      lExport.ExportLine( "%" );
    }

    while ( ! bReady )
    {
      PbnError lError;
      PbnGameData               lGameData = new PbnGameData();
      PbnGameTags lGameTags = new PbnGameTags();
      PbnUseTagIds              lUseTagIds = new PbnUseTagIds();

      /*
       * Put the '##' tags already in the game's GameTags.
       */
      lInherit.GetCopyTags( lGameTags );
      /*
       * Store the game numbers of the '##' tags in the game's UseTagIds.
       */
      lInherit.GetCopyGameNrs( lUseTagIds );
      lInherit.SetGame( GameIndex + 1 );

      lImport.LogSetGameNr( GameIndex + 1 );
      lError = lImport.Read( FilePosIn
                           , lGameData
                           , lGameTags );

      if ( lError.Is( PbnError.NO_TAG ) )
      { /*
         * No game anymore.
         */
        lError.SetOK();
      }
      else
      {
        /*
         * Store the game numbers of the '#' tags in the game's UseTagIds.
         */
        lInherit.GetPrevGameNrs( lUseTagIds );

        mVeriStat.Add( lError );

        if ( bPrint )
        {
          System.out.println( "FilePosBegin[" + GameIndex + "] = " +
                              lImport.mFilePosBegin );
        }
        GameIndex++;

        if ( ! lError.HasSeverity( PbnError.SEV_WARNING ) )
        {
          if ( bExport )
          {
            lExport.Write( lGameData
                         , lGameTags );
          }
        }

        lVerifyError.SetWorst( lError );

        FilePosIn = lImport.mFilePosOut;
      }

      if ( lImport.IsEof() )
      {
        bReady = true;
      }
    }

    lImport.CloseLogFile();

    if ( bExport )
    {
      lExport.Flush();
    }

    return lVerifyError;
  }

  public String                 GetStat()
  {
    return mVeriStat.toString();
  }
}

class PbnVeriStat
{
  private int []                maNrErrors;
  private int                   mNrGames;

  public                        PbnVeriStat()
  {
    maNrErrors = new int[ PbnError.SEV_NUMBER ];
    for (int i = 0; i < PbnError.SEV_NUMBER; i++ )
    {
      maNrErrors[ i ] = 0;
    }
    mNrGames = 0;
  }

  public void                   Add(
  PbnError oError )
  {
    maNrErrors[ oError.GetSeverity() ]++;
    mNrGames++;
  }

  public String                 toString()
  {
    boolean                     bOk = true;
    String                      lString = new String();

    lString = "Total number of games read : " + mNrGames;
    if ( maNrErrors[ PbnError.SEV_FATAL ] > 0 )
    {
      bOk = false;
      lString += "\nGames with fatal error : "
               + maNrErrors[ PbnError.SEV_FATAL ];
    }
    if ( maNrErrors[ PbnError.SEV_SEVERE ] > 0 )
    {
      bOk = false;
      lString += "\nGames with severe PBN error : "
               + maNrErrors[ PbnError.SEV_SEVERE ];
    }
    if ( maNrErrors[ PbnError.SEV_ERROR ] > 0 )
    {
      bOk = false;
      lString += "\nGames with PBN error : "
               + maNrErrors[ PbnError.SEV_ERROR ];
    }
    if ( maNrErrors[ PbnError.SEV_WARNING ] > 0 )
    {
      bOk = false;
      lString += "\nGames with PBN warning : "
               + maNrErrors[ PbnError.SEV_WARNING ];
    }
    if ( maNrErrors[ PbnError.SEV_REMARK ] > 0 )
    {
      lString += "\nGames with PBN remark : "
               + maNrErrors[ PbnError.SEV_REMARK ];
    }

    if ( bOk )
    {
      lString += "\n\nImport file is OK";
    }

    return lString;
  }
}
