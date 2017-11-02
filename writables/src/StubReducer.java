import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StubReducer extends Reducer<StringPairWritable, IntWritable, StringPairWritable, IntWritable> {

  @Override
  public void reduce(StringPairWritable key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {

    int total = 0;
    
    for(IntWritable value : values){
    	total += value.get();
    	
    }
    context.write( new StringPairWritable(key), new IntWritable(total) );
    
  }
}