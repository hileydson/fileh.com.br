package entidades;

public class FormaPagamento {
	private Integer Fop_cd_forma_pagamento;
    private String Fop_ds_forma_pagamento;
    private String Fop_fl_tipo;

	public FormaPagamento() {
		// TODO Auto-generated constructor stub
	}

	
	
	public String getFop_fl_tipo() {
		return Fop_fl_tipo;
	}



	public void setFop_fl_tipo(String fop_fl_tipo) {
		Fop_fl_tipo = fop_fl_tipo;
	}



	public Integer getFop_cd_forma_pagamento() {
		return Fop_cd_forma_pagamento;
	}

	public void setFop_cd_forma_pagamento(Integer fop_cd_forma_pagamento) {
		Fop_cd_forma_pagamento = fop_cd_forma_pagamento;
	}

	public String getFop_ds_forma_pagamento() {
		return Fop_ds_forma_pagamento;
	}

	public void setFop_ds_forma_pagamento(String fop_ds_forma_pagamento) {
		Fop_ds_forma_pagamento = fop_ds_forma_pagamento;
	}




}
