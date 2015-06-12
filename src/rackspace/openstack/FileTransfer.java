package rackspace.openstack;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
public class FileTransfer {
 	
 static Properties props;
 public static void main(String[] args) {
  FileTransfer getMyFiles = new FileTransfer();
 
  String propertiesFilename = "";
  String fileToDownload = args[1].trim();
  String name = "cmpe283_test2_snapshot";
  getMyFiles.startFTP(name);
   
 }
 public boolean startFTP(String name){
	 name = "cmpe283_test2_snapshot";
  props = new Properties();
  StandardFileSystemManager manager = new StandardFileSystemManager();
  try {
   props.load(new FileInputStream("properties/"));
   String serverAddress = props.getProperty("192.168.49.129").trim();
   String userId = props.getProperty("root").trim();
   String password = props.getProperty("Passw0rd").trim();
   String remoteDirectory = props.getProperty("//var//lib//glance//images//" + name).trim();
   String localDirectory = props.getProperty("//users//anuragbhardwaj//Documents//images").trim();
    
   //Initializes the file manager
   manager.init();
   
   //Setup our SFTP configuration
   FileSystemOptions opts = new FileSystemOptions();
   SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
   SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
   SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);
   
   //Create the SFTP URI using the host name, userid, password,  remote path and file name
   String sftpUri = "sftp://" + userId + ":" + password +  "@" + serverAddress + "/" +
     remoteDirectory;
   
   // Create local file object
   String filepath = localDirectory;
   File file = new File(filepath);
   FileObject localFile = manager.resolveFile(file.getAbsolutePath());
   // Create remote file object
   FileObject remoteFile = manager.resolveFile(sftpUri, opts);
   // Copy local file to sftp server
   localFile.copyFrom(remoteFile, Selectors.SELECT_SELF);
   System.out.println("File download successful");
  }
  catch (Exception ex) {
   ex.printStackTrace();
   return false;
  }
  finally {
   manager.close();
  }
  return true;
}
}