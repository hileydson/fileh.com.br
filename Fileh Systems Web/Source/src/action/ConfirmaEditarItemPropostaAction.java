package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ItemPropostaDAO;
import dao.PropostaComercialDAO;
import entidades.ItemProposta;
import entidades.Usuario;

public class ConfirmaEditarItemPropostaAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			
			ItemProposta itemProposta = new ItemProposta();
			ItemPropostaDAO itemPropostaDAO = new ItemPropostaDAO(u.getUsu_ds_subdominio());
			
			int id_item_remover = Integer.parseInt(request.getParameter("produto_edt"));
			
			itemProposta = (ItemProposta) itemPropostaDAO.carregaEntidade(itemProposta, id_item_remover);
			
			String name_input_ds_item = request.getParameter("name_input_ds_produto");
			String name_input_qt_item = request.getParameter("name_input_qt_produto");
			String name_input_un_item = request.getParameter("name_input_un_produto");
			String name_input_vd_item = request.getParameter("name_input_vd_produto");
			String name_input_vu_item = request.getParameter("name_input_vu_produto");
			
			//name_input_qt_produto = name_input_qt_produto.replace(",", ".");
			name_input_vd_item = name_input_vd_item.replace(".", "").replace(".", "").replace(".", "").replace(".", "").replace(",", ".");
			name_input_vu_item = name_input_vu_item.replace(".", "").replace(".", "").replace(".", "").replace(".", "").replace(",", ".");
			
			itemProposta.setIpc_ds_item_proposta(name_input_ds_item);
			itemProposta.setIpc_nr_quantidade(Double.parseDouble(name_input_qt_item.replace(",", ".")));
			itemProposta.setIpc_ds_unidade(name_input_un_item);
			itemProposta.setIpc_vl_desconto(Double.parseDouble(name_input_vd_item));
			itemProposta.setIpc_vl_item_proposta(Double.parseDouble(name_input_vu_item));
			

			if (itemPropostaDAO.updateEntidade(itemProposta) == true){
				PropostaComercialDAO propostaDAO = new PropostaComercialDAO(u.getUsu_ds_subdominio());
				
				propostaDAO.carregaDadosPropostaComercial(request, response);
				
				request.getSession().setAttribute("proposta_comercial_criada","S");
				
				
				//carrega o frame de listagem de clientes e produtos
				propostaDAO.carregaFramesPropostaComercial(request, response);
				
				response.sendRedirect("cadastroPropostaComercial.jsp");

			}else{
				request.getSession().setAttribute("msg_erro", 	"Erro ao atualizar item da proposta, favor verificar se o iten existe, caso contrário entrar em contato com o suporte!");
				response.sendRedirect("processoErroMsg.jsp");
			}

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", 	"Erro ao atualizar item da proposta, favor verificar se o iten existe, caso contrário entrar em contato com o suporte!");
			response.sendRedirect("processoErroMsg.jsp");
		}
	}

}
