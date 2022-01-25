package devinmoney.entidades;

public enum Agencia {

	FLORIANOPOLIS(001), SAO_JOSE(002);

	private Integer codigo;

	Agencia(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

}
