package devinmoney.entidades;

public enum Agencia {

	FLORIANOPOLIS(001, "Florian�polis"), SAO_JOSE(002, "S�o Jos�");

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
