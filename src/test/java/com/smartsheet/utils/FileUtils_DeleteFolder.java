package com.smartsheet.utils;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for FileUtils.deleteFolder().
 * @author isim
 *
 */
public class FileUtils_DeleteFolder {

    private final static String TEST_FOLDER_PATH = System.getProperty("java.io.tmpdir") + File.separator + "smartsheet-tmp";
    private final static String TEST_FILE_PREFIX = "smartsheet-test";
    private final static String TEST_FILE_SUFFIX = ".tmp";
    private File testFolder;
  
    @Before
    public void setUp(){
        try{
            createTempFolder();
        } catch(IOException e){
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
  
    @After
    public void tearDown(){
        if(testFolder != null && testFolder.exists())
            testFolder.delete();
    }
  
    @Test
    public void testDeleteFolder_EmptyFolderNotDeleted() throws IOException{
        FileUtils.deleteFolder(testFolder);
        Assert.assertTrue(!testFolder.exists());
    }
  
    @Test(expected=NullPointerException.class)
    public void testDeleteFolder_NullInput() throws IOException{
        FileUtils.deleteFolder(null);
    }
  
    @Test
    public void testDeleteFolder_FolderWithOneFile() throws IOException{
        File testFile = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testFolder);
        FileUtils.deleteFolder(testFolder);
        
        Assert.assertTrue(!testFolder.exists());
        Assert.assertTrue(!testFile.exists());
    }
  
    @Test
    public void testDeleteFolder_FolderWithSixFiles() throws IOException{
        File testFile1 = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testFolder);
        File testFile2 = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testFolder);
        File testFile3 = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testFolder);
        File testFile4 = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testFolder);
        File testFile5 = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testFolder);
        File testFile6 = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testFolder);
        
        FileUtils.deleteFolder(testFolder);
        
        Assert.assertTrue(!testFolder.exists());
        Assert.assertTrue(!testFile1.exists());
        Assert.assertTrue(!testFile2.exists());
        Assert.assertTrue(!testFile3.exists());
        Assert.assertTrue(!testFile4.exists());
        Assert.assertTrue(!testFile5.exists());
        Assert.assertTrue(!testFile6.exists());
    }
  
    @Test
    public void testDeleteFolder_FolderWithOneFileAndEmptySubFolder() throws IOException{
        File testFile = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testFolder);
        File testSubFolder = new File(testFolder.getAbsoluteFile() + File.separator + "docs");
        
        FileUtils.deleteFolder(testFolder);
        
        Assert.assertTrue(!testFolder.exists());
        Assert.assertTrue(!testSubFolder.exists());
        Assert.assertTrue(!testFile.exists());
    }
  
    @Test
    public void testDeleteFolder_FolderWithContentInSubFolder() throws IOException{
        File testSubFolder = new File(testFolder.getAbsoluteFile() + File.separator + "docs");
        testSubFolder.mkdir();
        File testFileInSubFolder = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testSubFolder);
        
        FileUtils.deleteFolder(testFolder);
        
        Assert.assertTrue(!testFolder.exists());
        Assert.assertTrue(!testSubFolder.exists());
        Assert.assertTrue(!testFileInSubFolder.exists());
    }
  
    @Test
    public void testDeleteFolder_FolderWithContentInTwoSubFolder() throws IOException{
        File testDocsSubFolder = new File(testFolder.getAbsoluteFile() + File.separator + "docs");
        testDocsSubFolder.mkdir();
        File testDoc = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testDocsSubFolder);
        
        File testHTMLSubFolder = new File(testFolder.getAbsoluteFile() + File.separator + "html");
        testHTMLSubFolder.mkdir();
        File testHTMLDoc = File.createTempFile(TEST_FILE_PREFIX, TEST_FILE_SUFFIX, testHTMLSubFolder);
        
        FileUtils.deleteFolder(testFolder);
        
        Assert.assertTrue(!testFolder.exists());
        Assert.assertTrue(!testDocsSubFolder.exists());
        Assert.assertTrue(!testHTMLSubFolder.exists());
        Assert.assertTrue(!testDoc.exists());
        Assert.assertTrue(!testHTMLDoc.exists());
    }
  
    private void createTempFolder() throws IOException{
        testFolder = new File(TEST_FOLDER_PATH);
        if(!testFolder.exists() && !testFolder.mkdir()){
            Assert.fail("Fail to create a temp folder.");
        }
    }
}
