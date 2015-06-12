package rackspace.openstack;
 
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
public class DisasterRecovery {
      
       private static final String SUFFIX = "/";
      
       public static void main(String[] args)
       {
              AWSCredentials credentials = new BasicAWSCredentials("AKIAJCDVFGC425YXTY5Q","dGT7/DDUXzNpyaiC+fS6re3747W1C16/CZK3iC3O");
             
              // create a client connection based on credentials
              AmazonS3 s3client = new AmazonS3Client(credentials);
             
              // create bucket - name must be unique for all S3 users
              String bucketName = "test273";
             
              String folderName = "cmpe273";
             
             
              String fileName = folderName + SUFFIX + "cmpe283_test2_snapshot.raw";
              s3client.putObject(new PutObjectRequest(bucketName, fileName,
                           new File("//Users//anuragbhardwaj//Documents//Images//cmpe283_test2_snapshot.raw"))
                           .withCannedAcl(CannedAccessControlList.PublicRead));
             
       }
 
      
}