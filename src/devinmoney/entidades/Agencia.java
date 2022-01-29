package devinmoney.entidades;

public enum Agencia {

	FLORIANOPOLIS(001, "Florianópolis"), SAO_JOSE(002, "São José");

	private Integer codigo;
	private String descricao;

	Agencia(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

}
