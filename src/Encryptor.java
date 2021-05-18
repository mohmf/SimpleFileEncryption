import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Encryption Main Class
 * @author Mohammed Maasher
 * @since 2006
 */
class Encryptor {

    // 2KB Buffered
    static final int MAXBLOCK=2048;

    /**
    * Encrypt a File that encrypted with EncBlock method
    *
    * @param strSrc the source file to be encrypted
    * @param strDes the destination encrypted file.
    * @param strPw the password that will be used to encrypt the file
    */
    static void FileEnc(String strSrc,String strDes,String strPw) throws IOException
    {
        int intI=0;
        long lngI=0;

        byte[] intPw =new byte[strPw.length()];
        for (intI=0;intI<=strPw.length()-1;intI++)
            intPw[intI]=(byte) strPw.charAt(intI);

        File fSrc=new File(strSrc);
        FileInputStream fisSrc=new FileInputStream(fSrc);
        FileOutputStream fosDes=new FileOutputStream(strDes);

        byte[] bytEncPw =new byte[intPw.length];
        for(intI=0;intI<=bytEncPw.length-1;intI++)
            bytEncPw[intI]=(byte) (intPw[intI] ^ 200);

        Integer intLen= strPw.length();
        byte bytPL[]=new byte[2];

        if (intLen.toString().length()<2){
            bytPL[0]=(byte) 48;				//48 is Ascii of 0
            bytPL[1]=(byte) (intLen.toString().charAt(0));
        }
        else{
            bytPL[0]=(byte) (intLen.toString().charAt(0));
            bytPL[1]=(byte) (intLen.toString().charAt(1));
        }

        fosDes.write(bytPL);
        fosDes.write(bytEncPw);

        byte[] bytBlock =new byte[MAXBLOCK];

        for (lngI=1;lngI<=(fSrc.length()/MAXBLOCK);lngI++){
            fisSrc.read(bytBlock);
            EncBlock(bytBlock,intPw);
            fosDes.write(bytBlock);
        }

        int intRemind=(int) (fSrc.length() % MAXBLOCK);
        if (intRemind !=0 ){
            bytBlock=new byte[intRemind];
            fisSrc.read(bytBlock);
            EncBlock(bytBlock,intPw);
            fosDes.write(bytBlock);
        }

        fisSrc.close();
        fosDes.close();
    }


    //**********************************************************
    //*** FileDec Method: Used To Dencrypt a File ***
    //**********************************************************
    static int FileDec(String strSrc,String strDes,String strPw1) throws IOException
    {
        int intI=0;
        long lngI=0;
        int intPL=0;
        byte[] bytPL =new byte[2];
        byte[] bytEncPw;
        byte[] intPw;

        File fSrc=new File(strSrc);
        FileInputStream fisSrc=new FileInputStream(fSrc);

        fisSrc.read(bytPL);
        intPL=Integer.parseInt(new String(bytPL));

        bytEncPw=new byte[intPL];
        fisSrc.read(bytEncPw);

        intPw=new byte[intPL];
        for (intI=0;intI<=intPL-1;intI++)
            intPw[intI]=(byte) (bytEncPw[intI] ^ 200); //200 is Encrypt

        // Key of Password
        String strPw2=new String(intPw);

        //If Password doesn't Match
        if (! strPw2.equals(strPw1))
            return -1;

        FileOutputStream fosDes=new FileOutputStream(strDes);

        long lngFileSize=fSrc.length()-(2+intPL);
        byte bytBlock[]=new byte[MAXBLOCK];
        for (lngI=1;lngI<=(lngFileSize/MAXBLOCK);lngI++){
            fisSrc.read(bytBlock);
            EncBlock(bytBlock,intPw);
            fosDes.write(bytBlock);
        }

        int intRemind=(int) (lngFileSize % MAXBLOCK);
        if (intRemind !=0 ){
            bytBlock=new byte[intRemind];
            fisSrc.read(bytBlock);
            EncBlock(bytBlock,intPw);
            fosDes.write(bytBlock);
        }

        fisSrc.close();
        fosDes.close();

        return 0;
    }


    /**
     * EncBlock Method: Used To Encrypt a Byte Block using xor
     * @param bytA
     * @param intB
     */
    private static void EncBlock(byte[] bytA, byte[] intB)
    {
        int intL=0;
        for(int intI=0;intI<=bytA.length-1;intI++)
        {
            bytA[intI]=(byte) (bytA[intI] ^ intB[intL]);

            if (intL>=intB.length-1)
                intL=0;
            else
                intL++;

        }
    }
}
