package entidades;

public class PropostaComercial {

	private Integer Prc_cd_proposta_comercial;
	private Integer Prc_cd_usuario;
    private Integer Prc_cd_cliente;
    private Double Prc_vl_desconto;
	private Double Prc_vl_frete;
    private String Prc_dt_cadastro;
    private String Prc_dt_prevista;
	private String Prc_ds_obs;
	private String Prc_nm_atendente;
	private String Prc_ds_situacao;
	private String Prc_ds_forma_pagamento;
	
    
	public PropostaComercial() {
		// TODO Auto-generated constructor stub
	}
	
	

	public String getPrc_ds_forma_pagamento() {
		return Prc_ds_forma_pagamento;
	}



	public void setPrc_ds_forma_pagamento(String prc_ds_forma_pagamento) {
		Prc_ds_forma_pagamento = prc_ds_forma_pagamento;
	}



	public String getPrc_ds_situacao() {
		return Prc_ds_situacao;
	}

	public void setPrc_ds_situacao(String prc_ds_situacao) {
		Prc_ds_situacao = prc_ds_situacao;
	}

	public Integer getPrc_cd_proposta_comercial() {
		return Prc_cd_proposta_comercial;
	}

	public void setPrc_cd_proposta_comercial(Integer prc_cd_proposta_comercial) {
		Prc_cd_proposta_comercial = prc_cd_proposta_comercial;
	}

	public Integer getPrc_cd_usuario() {
		return Prc_cd_usuario;
	}

	public void setPrc_cd_usuario(Integer prc_cd_usuario) {
		Prc_cd_usuario = prc_cd_usuario;
	}

	public Integer getPrc_cd_cliente() {
		return Prc_cd_cliente;
	}

	public void setPrc_cd_cliente(Integer prc_cd_cliente) {
		Prc_cd_cliente = prc_cd_cliente;
	}

	public Double getPrc_vl_desconto() {
		return Prc_vl_desconto;
	}

	public void setPrc_vl_desconto(Double prc_vl_desconto) {
		Prc_vl_desconto = prc_vl_desconto;
	}

	public Double getPrc_vl_frete() {
		return Prc_vl_frete;
	}

	public void setPrc_vl_frete(Double prc_vl_frete) {
		Prc_vl_frete = prc_vl_frete;
	}

	public String getPrc_dt_cadastro() {
		return Prc_dt_cadastro;
	}

	public void setPrc_dt_cadastro(String prc_dt_cadastro) {
		Prc_dt_cadastro = prc_dt_cadastro;
	}

	public String getPrc_dt_prevista() {
		return Prc_dt_prevista;
	}

	public void setPrc_dt_prevista(String prc_dt_prevista) {
		Prc_dt_prevista = prc_dt_prevista;
	}

	public String getPrc_ds_obs() {
		return Prc_ds_obs;
	}

	public void setPrc_ds_obs(String prc_ds_obs) {
		Prc_ds_obs = prc_ds_obs;
	}

	public String getPrc_nm_atendente() {
		return Prc_nm_atendente;
	}

	public void setPrc_nm_atendente(String prc_nm_atendente) {
		Prc_nm_atendente = prc_nm_atendente;
	}



}

