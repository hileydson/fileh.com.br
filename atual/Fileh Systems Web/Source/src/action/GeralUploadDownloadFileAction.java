package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class GeralUploadDownloadFileAction implements ActionProcess {
	private ServletFileUpload uploader = null;
	public String upload_file_caminho_name = "";
	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
	    
		//upload
		
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		
		//String realPath = request.getSession().getServletContext().getRealPath("/");
		//File filesDir = new File(realPath+"/TestFolder", "testFIle.txt");
		File filesDir = new File(request.getSession().getServletContext().getRealPath("/"));;
		fileFactory.setRepository(filesDir);
		this.uploader = new ServletFileUpload(fileFactory);
		
		
		
		if(!ServletFileUpload.isMultipartContent(request)){
			try {
				throw new ServletException("Content type is not multipart/form-data");
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");
		try {
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			while(fileItemsIterator.hasNext()){
				FileItem fileItem = fileItemsIterator.next();
				System.out.println("FieldName="+fileItem.getFieldName());
				System.out.println("FileName="+fileItem.getName());
				System.out.println("ContentType="+fileItem.getContentType());
				System.out.println("Size in bytes="+fileItem.getSize());
				
				File file = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+fileItem.getName());
				upload_file_caminho_name = request.getSession().getServletContext().getRealPath("/")+File.separator+fileItem.getName();
				System.out.println("Absolute Path at server="+file.getAbsolutePath());
				fileItem.write(file);
				out.write("File "+fileItem.getName()+ " uploaded successfully.");
				out.write("<br>");
				out.write("<a href=\"UploadDownloadFileServlet?fileName="+fileItem.getName()+"\">Download "+fileItem.getName()+"</a>");
			}
		} catch (FileUploadException e) {
			out.write("Exception in uploading file.");
		} catch (Exception e) {
			out.write("Exception in uploading file.");
		}
		out.write("</body></html>");
		
	}
		
		

}
