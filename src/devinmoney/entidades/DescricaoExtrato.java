package devinmoney.entidades;

public enum DescricaoExtrato {

	DEPOSITO("Dep�sito recebido"), SAQUE("Saque efetuado"), TRANSFERENCIA_EFETUADA("Transfer�ncia efetuada"),
	TRANSFERENCIA_RECEBIDA("Transfer�ncia recebida"), RESGATE("Resgate"),
	APLICACAO("Aplica��o");

	private String tipo;

	private DescricaoExtrato(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

}
