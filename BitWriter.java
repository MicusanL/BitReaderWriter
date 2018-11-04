package bitreaderwriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
public class BitWriter {

    OutputStream outputStream;
    private int bufferWriter;
    private int numberOfWriteBits = Constants.WORD_BITS_NUMBER;

    /*    numberOfWriteBits = number of bits that need to be fill   */
    public BitWriter(String outputFile) {
        try {
            outputStream = new FileOutputStream(outputFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BitWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void WriteNewWord(int word) {
        try {
//            System.out.println("wr: " + Integer.toBinaryString(word));
            outputStream.write(word);
            numberOfWriteBits = Constants.WORD_BITS_NUMBER;
            bufferWriter = 0;
        } catch (IOException ex) {
            Logger.getLogger(BitWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void WriteBit(int bit) {

        bufferWriter <<= 1;
        bufferWriter += bit;
        numberOfWriteBits--;

        if (numberOfWriteBits <= 0) {
            WriteNewWord(bufferWriter);
        }
    }

    public void WriteNBits(int bitsSequence, int n) {

        int countBits = 0;

//        while (countBits < n) {
//            if (numberOfWriteBits >= n) {
//                bitsReaded = (bufferReader >>> (16 - n)) % Constants.POWER_OF_2[n];
//                bufferReader <<= n;
//                numberOfReadBits -= n;
//                countBits+=n;
//            } else {
//                WriteBit((bitsSequence >>> numberOfWriteBits) % Constants.POWER_OF_2[numberOfWriteBits]);
//                countBits+=numberOfWriteBits;
//            }
//        }
        while (countBits < n) {
            if (numberOfWriteBits >= n - countBits) {
                bufferWriter <<= n - countBits;
                bufferWriter += bitsSequence % Constants.POWER_OF_2[n - countBits];
                numberOfWriteBits -= n - countBits;
//                System.out.println("countBits: "+ countBits+ " n= " + n + "    seq= " + bitsSequence+" "+Integer.toBinaryString(bitsSequence % Constants.POWER_OF_2[n]) + "    new value= " + Integer.toBinaryString(bufferWriter));

                if (numberOfWriteBits <= 0) {
                    WriteNewWord(bufferWriter);
                }

                countBits += n - countBits;
            } else {
//                System.out.println("b: " + (bitsSequence >>> (n - countBits - 1)) % 2);
                WriteBit((bitsSequence >>> (n - countBits - 1)) % 2);
                countBits++;
            }
        }

//while (countBits < n) {
//                WriteBit((bitsSequence >>> (n - countBits - 1)) % 2);
//                countBits++;
//            
//        }
    }

}
