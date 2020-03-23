package entidades;

public class Usuario {

	private Integer Usu_cd_usuario;
	private String Usu_nm_usuario;
    private String Usu_nr_cnpj;
    private String Usu_ds_email;
    private String Usu_ds_subdominio;
    private String Usu_ds_fantasia;
    private String Usu_ds_logradouro;
    private String Usu_ds_numero;
    private String Usu_ds_bairro;
    private String Usu_ds_cidade;
    private String Usu_ds_uf;
    private String Usu_ds_tel;
    private Integer Usu_cd_plano;
    
	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Integer getUsu_cd_plano() {
		return Usu_cd_plano;
	}



	public void setUsu_cd_plano(Integer usu_cd_plano) {
		Usu_cd_plano = usu_cd_plano;
	}



	public String getUsu_ds_fantasia() {
		return Usu_ds_fantasia;
	}



	public void setUsu_ds_fantasia(String usu_ds_fantasia) {
		Usu_ds_fantasia = usu_ds_fantasia;
	}



	public String getUsu_ds_logradouro() {
		return Usu_ds_logradouro;
	}



	public void setUsu_ds_logradouro(String usu_ds_logradouro) {
		Usu_ds_logradouro = usu_ds_logradouro;
	}



	public String getUsu_ds_numero() {
		return Usu_ds_numero;
	}



	public void setUsu_ds_numero(String usu_ds_numero) {
		Usu_ds_numero = usu_ds_numero;
	}



	public String getUsu_ds_bairro() {
		return Usu_ds_bairro;
	}



	public void setUsu_ds_bairro(String usu_ds_bairro) {
		Usu_ds_bairro = usu_ds_bairro;
	}



	public String getUsu_ds_cidade() {
		return Usu_ds_cidade;
	}



	public void setUsu_ds_cidade(String usu_ds_cidade) {
		Usu_ds_cidade = usu_ds_cidade;
	}



	public String getUsu_ds_uf() {
		return Usu_ds_uf;
	}



	public void setUsu_ds_uf(String usu_ds_uf) {
		Usu_ds_uf = usu_ds_uf;
	}



	public String getUsu_ds_tel() {
		return Usu_ds_tel;
	}



	public void setUsu_ds_tel(String usu_ds_tel) {
		Usu_ds_tel = usu_ds_tel;
	}



	public String getUsu_ds_subdominio() {
		return Usu_ds_subdominio;
	}
	
	public void setUsu_ds_subdominio(String usu_ds_subdominio) {
		Usu_ds_subdominio = usu_ds_subdominio;
	}

	/** 
	 * O uso da coluna cd_usuaro nas tabelas eh importante para quando for fazer a restauracao do usuario... se for de outro usuario nao vai aparecer para ele...mas vai ocupar espaco do usuario que tentou...
	 * @return
	 */
	public Integer getUsu_cd_usuario() {
		return Usu_cd_usuario;
	}

	public void setUsu_cd_usuario(Integer usu_cd_usuario) {
		Usu_cd_usuario = usu_cd_usuario;
	}

	public String getUsu_nm_usuario() {
		return Usu_nm_usuario;
	}

	public void setUsu_nm_usuario(String usu_nm_usuario) {
		Usu_nm_usuario = usu_nm_usuario;
	}

	public String getUsu_nr_cnpj() {
		return Usu_nr_cnpj;
	}

	public void setUsu_nr_cnpj(String usu_nr_cnpj) {
		Usu_nr_cnpj = usu_nr_cnpj;
	}

	public String getUsu_ds_email() {
		return Usu_ds_email;
	}

	public void setUsu_ds_email(String usu_ds_email) {
		Usu_ds_email = usu_ds_email;
	}



}

