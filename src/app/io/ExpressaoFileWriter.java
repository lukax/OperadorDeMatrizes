package app.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import app.mat.base.Expressao;

public class ExpressaoFileWriter {

    private String arquivo;

	public ExpressaoFileWriter(String arquivo) {
		this.arquivo = arquivo;
    }
    
    public void write(List<Expressao<?>> expressoes) throws IOException{
    	BufferedWriter writer = null;
    	try {
    		writer = new BufferedWriter(new FileWriter(arquivo));
           
            for (Expressao<?> e : expressoes) {
                writer.write(e.toString());
                writer.newLine();
            }

        } catch (IOException e) {
        	throw e; 
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
            	throw e;
            }
        }

    }
}
