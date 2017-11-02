/*
 * TO DO implement
 * Provide additional import statements here
 */
//import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import org.apache.hadoop.mapreduce.Counter;

public class ImageCounterDriver extends Configured implements Tool {
	
	
	
	
  @Override
  public int run(String[] args) throws Exception {

    if (args.length != 2) {
      System.out.printf("Usage: ImageCounter <input dir> <output dir>\n");
      return -1;
    }

    Job job = new Job(getConf());
    job.setJarByClass(ImageCounterDriver.class);
    job.setJobName("Image Counter");

    /*
     * Set the input and output paths.
     */
    FileInputFormat.addInputPath(job, new Path(args[0]) );
    FileOutputFormat.setOutputPath(job, new Path(args[1])  );
    

    /*
     * Set the mapper class.
     * This is a map-only job, so we do not call setReducerClass.
     */
    job.setMapperClass(ImageCounterMapper.class);

    /*
     * Set the output key and value classes.
     */
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    /*
     * Set the number of reduce tasks to 0. 
     */
     job.setNumReduceTasks(0);

    boolean success = job.waitForCompletion(true);
    if (success) {
      /*
       * Print out the counters that the mappers have been incrementing.
       * Use a call similar to job.getCounters().findCounter("ImageCounter","jpg").getValue()
       * to get the JPG counter.
       * Use System.out.println to print out each counter.
       */
     
    	Counter jpg = job.getCounters().findCounter("ImageCounter","jpg");
    	System.out.println("jpg " +jpg.getValue());
    	
    	Counter gif = job.getCounters().findCounter("ImageCounter","gif");
    	System.out.println("gif " +gif.getValue());
    	
    	Counter other = job.getCounters().findCounter("ImageCounter","other");
    	System.out.println("other " +other.getValue());
    	

      return 0;
    } else
      return 1;
  }

  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new Configuration(), new ImageCounterDriver(), args);
    System.exit(exitCode);
  }
}