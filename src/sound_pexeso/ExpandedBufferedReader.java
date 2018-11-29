/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound_pexeso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;

/**
 *
 * @author michal
 */
public class ExpandedBufferedReader extends BufferedReader{
    public ExpandedBufferedReader(InputStreamReader reader){
        super(reader);
    }
    public String readLine() throws IOException{
        final int CR = 13;
        final int LF = 10;
        int currentCharVal = super.read();
        int currentPos = 0;
        char[] data = new char[1024];
        
        while((currentCharVal != CR) && (currentCharVal != LF) && (currentCharVal >=0)){
            data[currentPos++] = (char) currentCharVal;
            //System.out.println("Actual char is: "+data[currentPos -1]);
            if(currentCharVal == 0){
                System.out.print("\0");
            }
            else if(currentCharVal == 10){
                System.out.print("\n");
            }
            if(currentPos < 1024){
                currentCharVal = super.read();
            }
            else{
                throw new StreamCorruptedException();
            }
            
        }
        
        if(currentCharVal < 0){
            if(currentPos > 0){
                return(new String(data,0,currentPos));
            }
            else{
                return null;
            }
        }
        else{
            if(currentCharVal == CR){
                super.mark(1);
                if(super.read() != LF){
                    super.reset();
                }
            }
            else if(currentCharVal != LF){
                super.mark(1);
                int nextCharVal = super.read();
                if(nextCharVal == CR){
                    super.mark(1);
                    if(super.read() != LF){
                        super.reset();
                    }
                }
                else if( nextCharVal != LF){
                    super.reset();
                }
            }
            return(new String(data,0,currentPos));
        }
    }
}
