package action;

import hibernate.ServicosGeral;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import entidades.Cliente;
import entidades.Entidade;
import entidades.Usuario;

public class CadastrarClienteAction implements ActionProcess {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		try{
			Integer resp;
			Usuario u = (Usuario) request.getSession().getAttribute("usuario");
			Cliente c = new Cliente();
			ClienteDAO cDAO = new ClienteDAO(u.getUsu_ds_subdominio());
			Entidade e = new Entidade();
			ServicosGeral sg = new ServicosGeral(u.getUsu_ds_subdominio());

			e = (Entidade) sg.carregaEntidade( e, Integer.parseInt(request.getParameter("CLI_CD_ENTIDADE")) );


			c.setCli_nm_cliente(request.getParameter("cli_nm_cliente"));
			c.setCli_ds_logradouro(request.getParameter("cli_ds_logradouro"));
			c.setCli_ds_bairro(request.getParameter("cli_ds_bairro"));
			c.setCli_ds_uf(request.getParameter("cli_ds_uf"));
			c.setCli_nr_tel(request.getParameter("cli_nr_tel"));
			c.setCli_nr_cpf(request.getParameter("cli_nr_cpf"));
			c.setCli_ds_entidade(e.getEnt_nm_entidade());
			c.setCli_ds_referencia(request.getParameter("cli_ds_referencia"));
			c.setCli_cd_usuario(u.getUsu_cd_usuario());

			resp = cDAO.inserirRegistro(c);

			if (resp == null){
				request.getSession().setAttribute("msg_erro", "Erro ao cadastrar cliente... entre em contato com o suporte");
				response.sendRedirect("processoErroMsg.jsp");
			}else{
				request.getSession().setAttribute("msg_cadastro",	"Dados :<br><br>"+
																	"Nome: "+c.getCli_nm_cliente()+"<br>"+
																	"CPF: "+c.getCli_nr_cpf()+"<br>"+
																	"Logradouro: "+c.getCli_ds_logradouro()+"<br>"+
																	"Referência: "+c.getCli_ds_referencia()+"<br>"+
																	"Bairro: "+c.getCli_ds_bairro()+"<br>"+
																	"UF: "+c.getCli_ds_uf()+"<br>"+
																	"Telefone: "+c.getCli_nr_tel()+"<br>"+
																	"Entidade: "+c.getCli_ds_entidade()+"<br>"+
																	"<br><br>Cadastro efetuado com sucesso!");
				
				response.sendRedirect("cadastroCliente.jsp");
			};

		}catch(Exception e){
			request.getSession().setAttribute("msg_erro", "Erro ao cadastrar cliente... entre em contato com o suporte");
			response.sendRedirect("processoErroMsg.jsp");
		}

	}

}
