package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GeralDownloadCompleteBackupAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub

		try{
			//coloca para download
			//----------------------------------------------------------------------
			String fileName = request.getSession().getAttribute("name_file_download").toString() ;

			if(fileName == null || fileName.equals("")){
				try {
					throw new ServletException("File Name can't be null or empty");
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			File file = new File(request.getSession().getServletContext().getRealPath("/")+File.separator+fileName);
			if(!file.exists()){
				try {
					throw new ServletException("File doesn't exists on server.");
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


			System.out.println("File location on server::"+file.getAbsolutePath());
			ServletContext ctx = request.getSession().getServletContext();
			InputStream fis = new FileInputStream(file);
			String mimeType = ctx.getMimeType(file.getAbsolutePath());
			response.setContentType(mimeType != null? mimeType:"application/octet-stream");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			ServletOutputStream os       = response.getOutputStream();
			byte[] bufferData = new byte[1024];
			int read=0;
			while((read = fis.read(bufferData))!= -1){
				os.write(bufferData, 0, read);
			}
			os.flush();
			os.close();
			fis.close();
			System.out.println("File downloaded at client successfully");

			file.delete();
			
			//----------------------------------------------------------------------
		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", 	"Erro ao processar arquivo... favor entre em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}

	}

}
