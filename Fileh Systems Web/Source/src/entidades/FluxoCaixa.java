package entidades;


public class FluxoCaixa {
	private Integer Flu_cd_fluxo_caixa;
    private String Flu_ds_fluxo_caixa;
    private Integer Flu_cd_entidade;
	private Double Flu_vl_fluxo_caixa;
    private String Flu_dt_cadastro;
    private String Flu_fl_tipo; 
    private String Flu_ds_forma_pagamento;
    
	public FluxoCaixa() {
		// TODO Auto-generated constructor stub
	}

	
	
	public String getFlu_ds_forma_pagamento() {
		return Flu_ds_forma_pagamento;
	}



	public void setFlu_ds_forma_pagamento(String flu_ds_forma_pagamento) {
		Flu_ds_forma_pagamento = flu_ds_forma_pagamento;
	}



	public Integer getFlu_cd_fluxo_caixa() {
		return Flu_cd_fluxo_caixa;
	}

	public void setFlu_cd_fluxo_caixa(Integer flu_cd_fluxo_caixa) {
		Flu_cd_fluxo_caixa = flu_cd_fluxo_caixa;
	}

	public String getFlu_ds_fluxo_caixa() {
		return Flu_ds_fluxo_caixa;
	}

	public void setFlu_ds_fluxo_caixa(String flu_ds_fluxo_caixa) {
		Flu_ds_fluxo_caixa = flu_ds_fluxo_caixa;
	}

	public Integer getFlu_cd_entidade() {
		return Flu_cd_entidade;
	}

	public void setFlu_cd_entidade(Integer flu_cd_entidade) {
		Flu_cd_entidade = flu_cd_entidade;
	}

	public Double getFlu_vl_fluxo_caixa() {
		return Flu_vl_fluxo_caixa;
	}


	public void setFlu_vl_fluxo_caixa(Double flu_vl_fluxo_caixa) {
		Flu_vl_fluxo_caixa =flu_vl_fluxo_caixa;
	}


	public String getFlu_dt_cadastro() {
		return Flu_dt_cadastro ;
	}


	public void setFlu_dt_cadastro(String flu_dt_cadastro) {
		Flu_dt_cadastro = flu_dt_cadastro ;
	}

	public String getFlu_fl_tipo() {
		return Flu_fl_tipo;
	}

	public void setFlu_fl_tipo(String flu_fl_tipo) {
		Flu_fl_tipo = flu_fl_tipo;
	}


	
	
	
}
