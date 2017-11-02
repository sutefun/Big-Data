/*
 * TO DO implement
 * Provide additional import statements here:
 * import org.apache.hadoop.mapreduce.lib.input.FileSplit;
 * import org.apache.hadoop.fs.Path;
 */
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class IndexMapper extends Mapper<Text, Text, Text, Text> {

  @Override
  public void map(Text key, Text value, Context context) throws IOException,
      InterruptedException {

    /*
     * Get the FileSplit for the input file, which provides access
     * to the file's path. You need the file's path because it
     * contains the name of the play.
     * First, call the getInputSplit method on the context object to
     * get a fileSplit object. 
     * Then call getPath method on the fileSplit object to get a Path object.
     */
    FileSplit fs = (FileSplit) context.getInputSplit();
    
    
    /*
     * Call the getName method on the Path object to retrieve the
      * file's name, which is the name of the play. Then append
     * "@" and the line number to the play's name. The resulting
     * string is the location of the words on that line.
     */
    String filename = fs.getPath().getName() + "@"  + key.toString() ;
    
    
    /*
     * Convert the line to lower case.
     */
    String s = value.toString().toLowerCase();
    
    /* 
     * Split the line into words. For each word on the line,
     * emit an output record that has the word as the key and
     * the location of the word as the value. 
     */
    for(String word  : s.split("\\W+" )){
		  if(word.length()>0){
			  
			  context.write(new Text(word), new Text(filename) );
		  }
    }	
    
}
}