package dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ListagemClientesAction;
import action.ListagemProdutosAction;
import entidades.Cliente;
import entidades.ItemProposta;
import entidades.PropostaComercial;


public class PropostaComercialDAO extends MetodosComumDAO {	
	private ArrayList<String> listClienteNamesConsultaListagem;
	private ArrayList<Double> listTotalPropostaConsultaListagem;
	

	public PropostaComercialDAO(String empresa_subdominio) {
		super(empresa_subdominio);
		// TODO Auto-generated constructor stub
	}


	/**
	 * O nome do cliente de cada proposta comercial está na tabela de clientes, por isso foi separado, não há a coluna com o nome do cliente no objeto Proposta Comercial. 
	 * 
	 * Buscar esse valor somente após executar o procedimento 'consultaListagemPropostaCliente()', então para cada indice da lista resultante desse procedimento existirá um nome de cliente nessa lista.
	 * 
	 * @return Lista de nomes dos clientes relativo a função 'consultaListagemPropostaCliente()'. 
	 */
	public ArrayList<String> getListClienteNamesConsultaListagem() {
		return listClienteNamesConsultaListagem;
	}


	/**
	 * O valor total de cada proposta comercial é formado pela soma de seus itens, por isso o total foi separado, não há a coluna valor total no objeto Proposta Comercial. 
	 * 
	 * Buscar esse valor somente após executar o procedimento 'consultaListagemPropostaCliente()', então para cada indice da lista resultante desse procedimento existirá um total nessa lista.
	 *
	 *@return Lista da soma total dos itens de cada proposta comercial relativo a função 'consultaListagemPropostaCliente()'. 
	 */
	public ArrayList<Double> getListTotalPropostaConsultaListagem() {
		return listTotalPropostaConsultaListagem;
	}

	public ArrayList<PropostaComercial> consultaPropostaClienteBackup(){
		ArrayList<PropostaComercial> listPropostas = new ArrayList<>();
		listClienteNamesConsultaListagem = new ArrayList<>();
		listTotalPropostaConsultaListagem = new ArrayList<>();
		PropostaComercial propostaComercial ;
		Object[] row;
		String sql;


		try{

			sql = 
					" select PRC_CD_PROPOSTA_COMERCIAL, 1 "+
							" from PROPOSTA_COMERCIAL  ";

		
	
			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				propostaComercial = new PropostaComercial();

				row = q.get(i);
				
				propostaComercial = (PropostaComercial) sg.carregaEntidade(propostaComercial, Integer.parseInt(row[0].toString()));

				
				listPropostas.add(propostaComercial);
			}

			return listPropostas;
		}catch(Exception e){
			return null;
		}
	};	
	
	
	public ArrayList<PropostaComercial> consultaListagemPropostaCliente(String id_cliente_proposta,String codigo_cliente, String codigo, String dataCadastro, String dataPrevista,String situacaoProposta, String ds_entidade,boolean bCheck_situacao_null, Integer cd_usuario){
		ArrayList<PropostaComercial> listPropostas = new ArrayList<>();
		listClienteNamesConsultaListagem = new ArrayList<>();
		listTotalPropostaConsultaListagem = new ArrayList<>();
		PropostaComercial propostaComercial ;
		Object[] row;
		String sql;
		String strSqlImprimir_Inicio = " ";
		String strSqlImprimir_Fim = " ";
		String strConsulta = " ";


		try{

			strSqlImprimir_Inicio = 
					" select PRC_CD_PROPOSTA_COMERCIAL, (SELECT CONCAT(MAX(CLI_NM_CLIENTE), CONCAT(' (', CONCAT(CLI_CD_CLIENTE, ')') ))  FROM CLIENTE WHERE CLI_CD_CLIENTE = PRC_CD_CLIENTE ) CLI_NM_CLIENTE, "+
							" DATE_FORMAT(PRC_DT_CADASTRO, '%d/%m/%Y'), DATE_FORMAT(PRC_DT_PREVISTA, '%d/%m/%Y'), PRC_VL_FRETE, 	"+
							"	     ifnull((( "+
							"(SELECT 	ifnull(SUM(((IPC_VL_ITEM_PROPOSTA*IPC_NR_QUANTIDADE)-IPC_VL_DESCONTO)),0) FROM ITEM_PROPOSTA WHERE IPC_CD_PROPOSTA_COMERCIAL = PRC_CD_PROPOSTA_COMERCIAL)"+
							" 					+ PRC_VL_FRETE) - (PRC_VL_DESCONTO)),0)	PRC_VL_TOTAL, PRC_DS_SITUACAO	"+
							" from PROPOSTA_COMERCIAL WHERE PRC_CD_USUARIO = "+cd_usuario+"  ";

			if(  (!codigo_cliente.equalsIgnoreCase("")) || (!codigo.equalsIgnoreCase("")) || (!dataPrevista.equalsIgnoreCase("")) || (!dataCadastro.equalsIgnoreCase(""))){
				strConsulta = " ";


				if (!dataPrevista.equalsIgnoreCase("")) 
					strConsulta = " AND DATE_FORMAT(PRC_DT_PREVISTA, '%d/%m/%Y') = '" + dataPrevista + "'  " ;
				
				if (!dataCadastro.equalsIgnoreCase("")) 
					strConsulta = " AND DATE_FORMAT(PRC_DT_CADASTRO, '%d/%m/%Y') = '" + dataCadastro + "'  " ;

				if(bCheck_situacao_null == true){
					if (!situacaoProposta.equalsIgnoreCase("")) 
						strConsulta += " AND (PRC_DS_SITUACAO = '"+situacaoProposta+"'  OR PRC_DS_SITUACAO IS NULL OR PRC_DS_SITUACAO = '' )";
				}else{
					if (!situacaoProposta.equalsIgnoreCase("")) 
						strConsulta += " AND (PRC_DS_SITUACAO = '"+situacaoProposta+"'   )";
				}
				
				
				if(!ds_entidade.equalsIgnoreCase("") && !ds_entidade.equalsIgnoreCase("-1"))
					strConsulta += " AND EXISTS ( SELECT * FROM CLIENTE WHERE CLI_CD_CLIENTE = PRC_CD_CLIENTE AND CLI_DS_ENTIDADE = '"+ds_entidade+"'    )"; 	

				
				if (!codigo_cliente.equalsIgnoreCase("")) 
					strConsulta += " AND PRC_CD_CLIENTE = "+codigo_cliente+"  ";	

				if (!codigo.equalsIgnoreCase("")) 
					strConsulta += " AND PRC_CD_PROPOSTA_COMERCIAL = '"+codigo+"'  ";	

				strSqlImprimir_Fim = " ORDER BY PRC_CD_PROPOSTA_COMERCIAL ; ";

			} else {
				strConsulta = " ";
				strSqlImprimir_Fim = " ORDER BY PRC_CD_PROPOSTA_COMERCIAL LIMIT 10 ; ";
			};		


			if((!id_cliente_proposta.equalsIgnoreCase(""))){												
				strConsulta = "  AND PRC_CD_CLIENTE = "+id_cliente_proposta+ "  ";
				strSqlImprimir_Fim = " ";

			};			


			sql = strSqlImprimir_Inicio + strConsulta + strSqlImprimir_Fim;


			List<Object[]> q = sg.consultaGeral(sql);


			for(int i = 0; i<q.size();i++){
				propostaComercial = new PropostaComercial();

				row = q.get(i);
				propostaComercial.setPrc_cd_proposta_comercial(Integer.parseInt(row[0].toString()));

				if(row[1] == null) row[1] = "";	
				listClienteNamesConsultaListagem.add(row[1].toString());

				propostaComercial.setPrc_dt_cadastro(row[2].toString());

				if(row[3] == null) row[3] = "";				
				propostaComercial.setPrc_dt_prevista(row[3].toString());

				if(row[4] == null) row[4] = "";	
				propostaComercial.setPrc_vl_frete(Double.parseDouble(row[4].toString()));

				listTotalPropostaConsultaListagem.add(Double.parseDouble(row[5].toString()));

				if(row[6] == null) row[6] = "";	
				propostaComercial.setPrc_ds_situacao(row[6].toString());
				
				listPropostas.add(propostaComercial);
			}

			return listPropostas;
		}catch(Exception e){
			return null;
		}
	};


	public void carregaFramesPropostaComercial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//carrega o frame de listagem de clientes 
		ListagemClientesAction listagemClientesFrame = new ListagemClientesAction();
		request.getSession().setAttribute("carregarFrameClientes", "carregarFrame") ;
		listagemClientesFrame.process(request, response);
		request.getSession().setAttribute("carregarFrameClientes", null) ;

		//carrega o frame de listagem de produtos
		ListagemProdutosAction listagemProdutosFrame = new ListagemProdutosAction();
		request.getSession().setAttribute("carregarFrameProdutos", "carregarFrame") ;
		listagemProdutosFrame.process(request, response);
		request.getSession().setAttribute("carregarFrameProdutos", null) ;
		

	};

	public void carregaDadosPropostaComercial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PropostaComercial proposta_editando;
		PropostaComercialDAO propostaDAO;
		ArrayList <ItemProposta> proposta_itens;
		ItemPropostaDAO itemPropostaDAO ;
		
		
		//busca os nomes das situacoes para o combobox
		SituacaoPropostaDAO sitDAO = new SituacaoPropostaDAO(sg.empresa);
		ArrayList<String> listNomesSituacao;
		listNomesSituacao = sitDAO.buscaTodosTabelaSitucaoPropostaListagemNomes();
		if(listNomesSituacao == null){
			request.getSession().setAttribute("listNomesSituacao","");
		}else{
			request.getSession().setAttribute("listNomesSituacao",listNomesSituacao);
		}
		
		//busca os nomes das formas de pagamento para o combobox
		FormaPagamentoDAO fopDAO = new FormaPagamentoDAO(sg.empresa);
		ArrayList<String> listNomesFormaPagamento;
		listNomesFormaPagamento = fopDAO.buscaTodosTabelaFormaPagamentoListagemNomes("PR");
		if(listNomesFormaPagamento == null){
			request.getSession().setAttribute("listNomesFormaPagamento","");
		}else{
			request.getSession().setAttribute("listNomesFormaPagamento",listNomesFormaPagamento);
		}

		
		//verifica se já estava sendo editada alguma proposta para carregar os dados
		if (request.getSession().getAttribute("proposta_editando") == null){ 	
			request.getSession().setAttribute("proposta_itens",null);
			request.getSession().setAttribute("proposta_cliente",null);
			
			request.getSession().setAttribute("proposta_editando_mostrar_codigo", "");
		}else{
			
			propostaDAO = new PropostaComercialDAO(sg.empresa);
			proposta_editando = (PropostaComercial) request.getSession().getAttribute("proposta_editando");
			proposta_editando = (PropostaComercial) propostaDAO.carregaEntidade(proposta_editando, proposta_editando.getPrc_cd_proposta_comercial());
			
			//caso não exista mais no banco de dados o objeto carregado na sessão
			if(proposta_editando != null){
				//reajusta datas para mostrar
				proposta_editando.setPrc_dt_cadastro( propostaDAO.dataConverteDataBrasilMysqlData(proposta_editando.getPrc_dt_cadastro(), "-", "/") );
				if(proposta_editando.getPrc_dt_prevista() != null) proposta_editando.setPrc_dt_prevista( propostaDAO.dataConverteDataBrasilMysqlData(proposta_editando.getPrc_dt_prevista(), "-", "/") );


				ClienteDAO cliDAO = new ClienteDAO(sg.empresa);
				Cliente cliente = new Cliente();
				cliente = (Cliente) cliDAO.carregaEntidade(cliente, proposta_editando.getPrc_cd_cliente());
				//tratamento caso a proposta ainda não tenha cliente atribuido
				if (proposta_editando.getPrc_cd_cliente() != null){
					cliente = (Cliente) cliDAO.carregaEntidade(cliente, proposta_editando.getPrc_cd_cliente() );
				}else{
					cliente = null;
				};

				itemPropostaDAO = new ItemPropostaDAO(sg.empresa);
				proposta_itens = itemPropostaDAO.buscaItensPropostaComercial(proposta_editando.getPrc_cd_proposta_comercial());


				request.getSession().setAttribute("proposta_itens",proposta_itens);
				request.getSession().setAttribute("proposta_cliente",cliente);
				request.getSession().setAttribute("proposta_editando", proposta_editando);

				request.getSession().setAttribute("proposta_editando_mostrar_codigo", proposta_editando.getPrc_cd_proposta_comercial());
			}else{
				request.getSession().setAttribute("proposta_editando",null);
				request.getSession().setAttribute("proposta_itens",null);
				request.getSession().setAttribute("proposta_cliente",null);
				
				request.getSession().setAttribute("proposta_editando_mostrar_codigo", "");
			}
		};

	};

}
