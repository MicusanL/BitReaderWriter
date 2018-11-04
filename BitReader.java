package bitreaderwriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lidia
 */
public class BitReader {

    private InputStream inputStream;
    private int bufferReader;
    private int numberOfReadBits;
    public int fileLength;

    public BitReader(String inputFile) {
        try {
            inputStream = new FileInputStream(inputFile);
            fileLength = inputStream.available();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(BitReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BitReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int ReadBit() {
        int bit;
        if (numberOfReadBits == 0) {
            ReadNewWord();
        }

        bit = (bufferReader >>> Constants.WORD_BITS_NUMBER - 1) % 2;
        bufferReader <<= 1;
        numberOfReadBits--;

        return bit;

    }

    public int ReadNBits(int n) {
        int bitsReaded = 0;
        int countBits = 0;

        while (countBits < n) {
            if (numberOfReadBits >= n - countBits) {
                bitsReaded <<= n - countBits;
                bitsReaded += (bufferReader >>> (Constants.WORD_BITS_NUMBER - n + countBits)) % Constants.POWER_OF_2[n - countBits];
                bufferReader <<= n - countBits;
                numberOfReadBits -= (n - countBits);
                
                if (numberOfReadBits == 0) {
                    ReadNewWord();
                }
                countBits += (n - countBits);
            } else {
                bitsReaded <<= 1;
                bitsReaded += ReadBit();
                countBits++;
            }
        }
        return bitsReaded;

    }

    private void ReadNewWord() {
        try {

           // OutputStream outputStream = new FileOutputStream("test2.mp3");

            if ((bufferReader = inputStream.read()) != -1) { 
                numberOfReadBits = Constants.WORD_BITS_NUMBER;
//               System.out.println("R: "+Integer.toBinaryString(bufferReader));
               
            } else {
                
            }

        } catch (IOException ex) {
            Logger.getLogger(BitReader.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}
