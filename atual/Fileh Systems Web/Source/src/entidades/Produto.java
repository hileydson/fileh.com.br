package entidades;

public class Produto {

	private Integer Prd_cd_produto;
	private Integer Prd_cd_usuario;
    private String Prd_ds_produto;
    private Double Prd_vl_preco;
	private String Prd_ds_unidade;
    private Integer Prd_nr_estoque;
    
	public Produto() {
		// TODO Auto-generated constructor stub
	}

	public Integer getPrd_cd_produto() {
		return Prd_cd_produto;
	}

	public void setPrd_cd_produto(Integer prd_cd_produto) {
		Prd_cd_produto = prd_cd_produto;
	}

	public Integer getPrd_cd_usuario() {
		return Prd_cd_usuario;
	}

	public void setPrd_cd_usuario(Integer prd_cd_usuario) {
		Prd_cd_usuario = prd_cd_usuario;
	}

	public String getPrd_ds_produto() {
		return Prd_ds_produto;
	}

	public void setPrd_ds_produto(String prd_ds_produto) {
		Prd_ds_produto = prd_ds_produto;
	}

	public Double getPrd_vl_preco() {
		return Prd_vl_preco;
	}

	public void setPrd_vl_preco(Double prd_vl_preco) {
		Prd_vl_preco = prd_vl_preco;
	}

	public String getPrd_ds_unidade() {
		return Prd_ds_unidade;
	}

	public void setPrd_ds_unidade(String prd_ds_unidade) {
		Prd_ds_unidade = prd_ds_unidade;
	}

	public Integer getPrd_nr_estoque() {
		return Prd_nr_estoque;
	}

	public void setPrd_nr_estoque(Integer prd_nr_estoque) {
		Prd_nr_estoque = prd_nr_estoque;
	}




}

