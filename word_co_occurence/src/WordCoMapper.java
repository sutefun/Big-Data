import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCoMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	  Text outputkey = new Text();
	  IntWritable one = new IntWritable(1);
	  
    /*
     * Convert the line, which is received as a Text object,
     * to a String object, then convert it to lower case.
     */
	  
	  String koma = new String(",");
    String s = value.toString().toLowerCase();
    
    /*
     * Split the line into words. For each word on the line
     * except for the first word, emit an output record that has
     * "previous word, this word" as the key and 1 as the value.
     * Hint: for each word in the line, emit a String as the key. 
     * Build this string by concatenating the last word in the line,
     * a comma, and the "current" word.
     */
    String[] fields = s.split("\\W+");
    
    // 0 ACT I
    if(fields.length >2){
    	
    	for(int i=1 ; i<fields.length-2 ; i++){
    		s = fields[i] +koma +fields[i+1] ;
    		outputkey.set(s);
    		context.write( outputkey , one );
    	}
    		s = fields[fields.length-2] +koma +fields[fields.length-1];
    		outputkey.set(s);
    		context.write( outputkey , one );
    }

  }
}
